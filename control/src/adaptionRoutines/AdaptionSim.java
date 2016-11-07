package adaptionRoutines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import Jama.Matrix;
import Jama.QRDecomposition;
//import antlr.collections.List;
import mytrack.U4_Constants;


public class AdaptionSim {
	
	int NoOfTimesRoutineHasBeenCalled = 0;
	private boolean initialise;
	
	double[] parameters = {0.005};

	
	double forgettingfactor = .99;    //.99 = 50 measurements  no measurements = 1/(1-forgetting_factor^2)
	
	static Random rand = new Random();
	
	private Matrix globalSrim = new Matrix(3,3);
	double[][] globalSrimArray;
	
	public double[][] getGlobalSrim() {
		return globalSrim.getArray();
	}


	public void setGlobalSrim(double[][] globalSrim) {
		this.globalSrim = new Matrix(globalSrim);
	}

	static ArrayList<Matrix> SrimArray = new ArrayList<Matrix>();
	
	String AdaptionName;
	private int noBoxes;
	
	private boolean DEBUG = false;
	private void print(Object x){
		if (DEBUG ){
		System.out.println(this.AdaptionName + ": " + x);
		}
	}
	
	private boolean INFO = true;
	private void lprint(Object x){
		if (INFO ){
		System.out.println(this.AdaptionName + ": " + x);
		}
	}
	
	public static void main(String[] args) {
		int noBoxes = 10;
		//set up the srimarray
		for(int i=0;i<=noBoxes;i++){
			SrimArray.add(new Matrix(3,3));
		}
		boolean initialise = true;

		
		float forgettingfactor = .99f;
		
		AdaptionSim adaptModelForward = new AdaptionSim(noBoxes, "ModelTestAdaption", initialise, forgettingfactor);
		
		for (int i = 1;i<100;i++){
			long simRevTime = 20;
			long simDistancetravelled = 100;
			float speedatSENR2 = 80f;
			int min = 0;
			int max = 1000;
			adaptModelForward.processMeasurement(simRevTime , simDistancetravelled, speedatSENR2, min, max);
		}
	}
	
	public AdaptionSim(int noBoxes,String adaptionName, boolean initialise, float forgettingfactor) {
		super();
		this.initialise = initialise;
		this.noBoxes = noBoxes;
		this.AdaptionName=adaptionName;
		this.forgettingfactor = forgettingfactor;
		if (initialise==true){
			for(int i=0;i<=noBoxes;i++){
				SrimArray.add(new Matrix(3,3));
			}
			//guess some non zero adaptors
			double[] doubleAdaptors = new double[1];;
			if (AdaptionName.equals("ModelTestAdaption")){
				doubleAdaptors[0] = 0.005;
			}else{
				doubleAdaptors[0] = 0.005;
			}
			Matrix	adaptors = convert1DArrayToMatrix(doubleAdaptors);
			print("loaded adaptors");
			if(DEBUG) adaptors.print(10, 3);
			saveAdaptorsToU4Constants(adaptors);
		}else{
			//make copy of srim and adaptors in case of corruption if program is halted
			File theSource = new File(getSrimAndAdaptorsDirectory());
			File theDest = new File(getSrimAndAdaptorsDirectory() + "_copyMadeBeforeRunningProgram");
			try {
				FileUtils.copyDirectory(theSource,theDest);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i < noBoxes; i++) {
				Matrix srim;
				try {
					srim = loadSRIM(i);
					if (srim == null){
						SrimArray.add(new Matrix(3,3));
						print("srim "+ i + " initialised");
					}else{
						SrimArray.add(srim);
						print("srim "+ i + " loaded");
					}


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			try {
				Matrix	adaptors = loadAdaptors();
				print("loaded adaptors");
				if(DEBUG) adaptors.print(10, 3);
				saveAdaptorsToU4Constants(adaptors);				      
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	



	

	public boolean processMeasurement(long millis , long distancetravelled, float enginesetting,
			float min, float max){
		
		print("SRIM Analysis: " + AdaptionName + " engine setting: " + enginesetting);
		double x = (double)enginesetting;				//80 for simulation
		double y = (double)distancetravelled/(double)millis;  // 80/100 = 0.8
		print("distancetravelled = "+ distancetravelled + ", millis = "+ millis + ", y = "+y );
		double xmin = min;
		double xmax = max;
		print("millis =" + millis);
		print("distancetravelled =" + distancetravelled);
		print("enginesetting =" + enginesetting);
//		print("direction =" + direction);
		int srimoffset;
//		srimoffset = getSrimOffset(direction);
//		print("srimoffset =" + srimoffset);
		int srimbox = (int) Math.round((x-xmin)/(xmax-xmin)*noBoxes);//+ srimoffset;
		print("srimbox =" + srimbox);
		
		//must have saved the adaptors in AdaptionSim()
		double[] doubleAdaptors = new double[1];
		doubleAdaptors = load1DAdaptorsFromU4Constants();
		processMeasurement(x,y, srimbox, forgettingfactor, doubleAdaptors);
		print("finished processMeasurement");
		// the new srim is now saved in SrimArray
		// save the newly updated srimi
		try {
			saveSRIM(SrimArray.get(srimbox), srimbox);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get the global srim from SrimArray
		globalSrim = calcGlobalSrim();
		
	
		//update the adaptors if enough measurements have been taken
		NoOfTimesRoutineHasBeenCalled++;
		lprint("AdaptionSim called " + NoOfTimesRoutineHasBeenCalled);
		if(initialise == false || NoOfTimesRoutineHasBeenCalled>7){
			Matrix newAdaptors = getParameters2();
			print("about to write adaptors to U4Constants");
			saveAdaptorsToU4Constants(newAdaptors);
			lprint("AdaptionSim saved adaptors to U4Constants");
			try {
				saveAdaptorsToFile(newAdaptors);
				lprint("saved adaptors to " + getAdaptorsFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		//update the adaptors
		return false;
	}
	
	private void saveAdaptorsToFile(Matrix newAdaptors) throws IOException {
		//convert Matrix to double[][]
		double [][] doubleAdaptors = newAdaptors.getArray();
		//convert double[][] to String[][]
		int tableStringLengthcol=doubleAdaptors.length;
		int tableStringLengthrow=doubleAdaptors[0].length;
		String [][]stringAdaptors= new String [tableStringLengthcol][tableStringLengthrow];
		for(int i=0; i<tableStringLengthcol; i++) {
			for(int j=0; j<tableStringLengthrow; j++) {
				stringAdaptors[i][j]= String.valueOf(doubleAdaptors[i][j]);
			}
		}
		String filename = getAdaptorsFilename(); 
		
		//delete the file if it exists so can overwrite
		File theFile = new File(filename);
		boolean result = Files.deleteIfExists(theFile.toPath());
		//	    Path path = Paths.get("/Users/Home/");
		CSVWriter writer;

			writer = new CSVWriter(new FileWriter(filename));

			// TODO Auto-generated catch block


		for (int i=0; i < stringAdaptors.length; i++) {
			writer.writeNext(stringAdaptors[i]);
		}
		writer.close();
		lprint("wrote adaptors to " + filename);
		
	}


	private void saveAdaptorsToU4Constants(Matrix Adaptors) {
		
		float[] floatAdaptors = convertMatrixToFloatArray(Adaptors);

		if( this.AdaptionName.equals("ModelForwardAdaption")){
			U4_Constants.modelForwardAdaptors = floatAdaptors;
			print("adaptors have been saved.  U4forward=" + U4_Constants.modelForwardAdaptors[0]  );
		}else if( this.AdaptionName.equals("ModelReverseAdaption")){
			U4_Constants.modelReverseAdaptors = floatAdaptors;
			print("adaptors have been saved.  U4reverse=" + U4_Constants.modelReverseAdaptors[0]  );
		}else if( this.AdaptionName.equals("ModelTestAdaption")){
			U4_Constants.modelTestAdaptors = floatAdaptors;
			print("adaptors have been saved.  U4reverse=" + U4_Constants.modelTestAdaptors[0]  );
		}else{
			print("error in saving adaptors");
			return;
		}
		print("adaptors have been saved.  C=" + floatAdaptors[0]  );
		
	}
	
	String Left(String text, int noOfChars){
		return text.substring(text.length() - noOfChars);
	}


	private float[] convertMatrixToFloatArray(Matrix Adaptors) {
		double[] adaptors1D = convertMatrixtoDouble1DArray(Adaptors);

		float[] floatAdaptors = convertDouble1DArrayToFloat1DArray(adaptors1D);
		return floatAdaptors;
	}


	private float[] convertDouble1DArrayToFloat1DArray(double[] adaptors1D) {
		float[] floatAdaptors = new float[adaptors1D.length];
		
		for (int i = 0 ; i < adaptors1D.length; i++)
		{
			floatAdaptors[i] = (float) adaptors1D[i];
		}
		return floatAdaptors;
	}


	private double[] convertMatrixtoDouble1DArray(Matrix Adaptors) {
		double[][] adaptors2D = Adaptors.getArray();
		double[] adaptors1D = adaptors2D[0];
		return adaptors1D;
	}
	
	private double[] load1DAdaptorsFromU4Constants(){
		
		
		float[] floatAdaptors = new float[U4_Constants.modelForwardAdaptors.length]; 	//forward and reverse adaptors are the same length
		
		if( this.AdaptionName.equals("ModelForwardAdaption")){
			floatAdaptors = U4_Constants.modelForwardAdaptors;
		}else if( this.AdaptionName.equals("ModelReverseAdaption")){
			floatAdaptors = U4_Constants.modelReverseAdaptors;
		}else if( this.AdaptionName.equals("ModelTestAdaption")){
			floatAdaptors = U4_Constants.modelTestAdaptors;
			print("modelTestAdaptor " + floatAdaptors[0] );
		}else{
			print("error in loading adaptors");
			return null;
		}
		double[] adaptors1D = convertFloatArrayToDoubleArray(floatAdaptors);
//		double[][] adaptors2D = null;
//		adaptors2D[0] = adaptors1D;
//		Matrix Adaptors = new Matrix(adaptors2D);
		
		return adaptors1D;
	}


	private double[] convertFloatArrayToDoubleArray(float[] floatAdaptors) {
		double[] adaptors1D;
		adaptors1D = new double[floatAdaptors.length];
		for (int i = 0 ; i < floatAdaptors.length; i++)
		{
			adaptors1D[i] = (double) floatAdaptors[i];
		}
		return adaptors1D;
	}
	
	Matrix convert1DArrayToMatrix(double[] doubleAdaptors){
		double[][] adaptors = {doubleAdaptors};
		Matrix ans = new Matrix(adaptors);
		return ans;
	}


	private void saveSRIM (Matrix srim, int index) throws IOException{
		//convert Matrix to double[][]
		double [][] doubleAdaptors = srim.getArray();
		//convert double[][] to String[][]
		int tableStringLengthcol=doubleAdaptors.length;
		int tableStringLengthrow=doubleAdaptors[0].length;
		String [][]stringAdaptors= new String [tableStringLengthcol][tableStringLengthrow];
		for(int i=0; i<tableStringLengthcol; i++) {
			for(int j=0; j<tableStringLengthrow; j++) {
				stringAdaptors[i][j]= String.valueOf(doubleAdaptors[i][j]);
			}
		}
		String filename = getSrimFilename(index); 
		
		//delete the file if it exists so can overwrite
		File theFile = new File(filename);
		boolean result = Files.deleteIfExists(theFile.toPath());

		CSVWriter writer;

		writer = new CSVWriter(new FileWriter(filename));


		for (int i=0; i < stringAdaptors.length; i++) {
			writer.writeNext(stringAdaptors[i]);
		}
		writer.close();
	}

	private  Matrix loadSRIM(int index) throws IOException{
		String filename = getSrimFilename(index);
		File f = new File(filename);
		if(f.exists() && !f.isDirectory()) { 
			CSVReader reader;

			reader = new CSVReader(new FileReader(filename));
			List<String[]> lines;
			lines = reader.readAll();
			reader.close();
			String[][] strSRIM =  lines.toArray(new String[lines.size()][]);



			int tableStringLengthcol=strSRIM.length;
			int tableStringLengthrow=strSRIM[0].length;
			double [][]doubleSRIM= new double[tableStringLengthcol][tableStringLengthrow];

			for(int i=0; i<tableStringLengthcol; i++) {
				for(int j=0; j<tableStringLengthrow; j++) {
					doubleSRIM[i][j]= Double.valueOf(strSRIM[i][j]).doubleValue();
				}
			}
			//convert double[] to matrix
			Matrix srim = new Matrix(doubleSRIM);
			return srim;
		}
		
		return null;

	}
	
	private Matrix loadAdaptors() throws IOException {
		String filename = getAdaptorsFilename();
		File f = new File(filename);
		if(f.exists() && !f.isDirectory()) { 
			CSVReader reader;

			reader = new CSVReader(new FileReader(filename));
			List<String[]> lines;
			lines = reader.readAll();
			reader.close();
			String[][] stringAdaptors =  lines.toArray(new String[lines.size()][]);



			int tableStringLengthcol=stringAdaptors.length;
			int tableStringLengthrow=stringAdaptors[0].length;
			double [][]doubleAdaptors= new double[tableStringLengthcol][tableStringLengthrow];

			for(int i=0; i<tableStringLengthcol; i++) {
				for(int j=0; j<tableStringLengthrow; j++) {
					doubleAdaptors[i][j]= Double.valueOf(stringAdaptors[i][j]).doubleValue();
				}
			}
			//convert double[] to matrix
			Matrix adaptors = new Matrix(doubleAdaptors);
			return adaptors;
		}
		
		return null;
	}



	private String getAdaptorsFilename() {
		String filename = getSrimAndAdaptorsDirectory()+"/Adaptors_" + this.AdaptionName + ".csv";
		String directory = getSrimAndAdaptorsDirectory();
		//create the directory structure
		File theFile = new File(directory);
		theFile.mkdirs();
		return filename;
	}

	private String getSrimAndAdaptorsDirectory(){
		String directory = "srimAndAdaptors";
		return directory;
	}

	private String getSrimFilename(int index) {
		String filename = getSrimAndAdaptorsDirectory()+"/Srim_" + this.AdaptionName+ "/Srim_" + this.AdaptionName + "_" + index +".csv";
		String directory = getSrimAndAdaptorsDirectory()+"/Srim_" + this.AdaptionName;
		//create the directory structure
		File theFile = new File(directory);
		theFile.mkdirs();
		return filename;
	}






	private int getSrimOffset(String direction) {
		int srimoffset;
		if (direction.equals("forwards")){
			srimoffset = 0;
		}else{
			srimoffset = noBoxes/2;
		}
		return srimoffset;
	}
	
	public void testlong(long millis){
		
		print("millisxxx" + millis);
		
	}
	
	public void testString(String direction){
		
		print("directionxxx" + direction);
		
	}


	
	private Matrix calcGlobalSrim() {
		print("in calcGlobalSrim");
		Matrix globalSrim = new Matrix(3,3);
		for(Matrix mySrim : SrimArray){
			print("mySRIM in SrimArray");
			if (DEBUG) mySrim.print(10,2);
			print("globalSrimbefore");
			if (DEBUG) globalSrim.print(10,2);
			Matrix addsrim = addSrim(globalSrim,mySrim);
			globalSrim=addsrim;
			print("globalSrimafter");
			if (DEBUG) globalSrim.print(10,2);
		}
		return globalSrim;
	}

	private double[] getParameters() {
		
//		print ("Srim " + x);
		if (DEBUG) globalSrim.print(10,1);
		Matrix IM = globalSrim.transpose().times(globalSrim);
//		print ("IM " + x);
		if (DEBUG) IM.print(10,1);
		
		int xDim = IM.getColumnDimension() -3;
		Matrix XpX = IM.getMatrix(0, xDim, 0, xDim);
		Matrix XpY = IM.getMatrix(0, xDim, xDim+1, xDim+1);

		Matrix XpU = IM.getMatrix(0, xDim, xDim+2, xDim+2);
		if (DEBUG) print ("XpU");
		XpU.print(10,1);
		Matrix YpY = IM.getMatrix(xDim+1, xDim+1, xDim+1, xDim+1);
		Matrix YpU = IM.getMatrix(xDim+1, xDim+1, xDim+2, xDim+2);
		if (DEBUG) print ("XpX");
		XpX.print(10,1);
		if (DEBUG) print ("YpY");
		YpY.print(10,1);
		if (DEBUG) print ("YpU");
		YpU.print(10,1);
		
		Matrix UpU = IM.getMatrix(xDim+2, xDim+2, xDim+2, xDim+2);
		if (DEBUG) print ("UpU");
		UpU.print(10, 1);
		
		Matrix XpXIT = XpX.inverse().transpose();
		if (DEBUG) print ("XpXIT");
		XpXIT.print(10, 1);
		
		print ("XpY");
		if (DEBUG) XpY.print(10,1);
		
		Matrix D = XpX.inverse().transpose().times(XpY);
		print ("adaptors");
		if (DEBUG) D.print(10,1);

		double ymean = YpU.det() / UpU.det();  //mean of sensitivities
		Matrix xmean = XpU.times(1/ UpU.det());
		double[][] adaptors2D = D.getArray();
		return adaptors2D[0];
	}
	
private Matrix getParameters2() {
		
		if (DEBUG) globalSrim.print(10,1);
		Matrix RZS = globalSrim;
		
		int xDim = RZS.getColumnDimension() -3;
		Matrix R = RZS.getMatrix(0, xDim, 0, xDim);
		Matrix Z = RZS.getMatrix(0, xDim, xDim+1, xDim+1);
		Matrix sz = RZS.getMatrix(0, xDim, xDim+2, xDim+2);
		
		
		Matrix f = RZS.getMatrix(xDim+1, xDim+1, xDim+1, xDim+1);
		Matrix sy = RZS.getMatrix(xDim+1, xDim+1, xDim+2, xDim+2);
		
		Matrix sm = RZS.getMatrix(xDim+2, xDim+2, xDim+2, xDim+2);

		print ("R");
		if (DEBUG) R.print(10,1);
		print ("f");
		if (DEBUG) f.print(10,1);
		print ("sy");
		if (DEBUG) sy.print(10,1);
		
		Matrix D = R.inverse().times(Z);
		print ("adaptors");
		if (DEBUG) D.print(10,1);
//
//		double ymean = sy.det() / sm.det();  //mean of sensitivities
//		Matrix xmean = sz.times(1/ sm.det());
		double[][] adaptors2D = D.getArray();
		return D;
	}

	double est(double x, double parameters[], double adaptors[]){
		double b = parameters[0];
		double a = adaptors[0];
		double y = b*x*(1-a);
		print ("b=" + b);
		print ("a=" + a);
		print ("x=" + x);
		print ("est= ");
		print(y);
		return y;
	}
	
//	double measY (double x, double[] parameters, double[] adaptors){
//		
//		// y = y -y* + ad sens product
//		
//		double measy = meas(x, parameters)+5*(rand.nextFloat()-0.5);
//		double esty = meas(x, adaptors);
//		double adSensProd = adSensProduct(x);
//		double ans = measy - esty + adSensProd;
//		return ans;
//		
//	}
	
	void processMeasurement( 
			double x, double yy, int srimbox, double forgettingfactor, double[] doubleAdaptors){


		double esty = est(x, parameters, doubleAdaptors);
		print("x= " + x + " yy= " + yy + " esty= " + esty);

		double y = measY(x, yy, esty, doubleAdaptors);
		print("y= "+y);
		Matrix X = sensitivities(x);
		print ("sensitivities");
		if(DEBUG) X.print(10,1);
		Matrix XY1 = xy1(X,y,forgettingfactor);
		print ("XY1");
		if(DEBUG) XY1.print(10,1);

		Matrix meas_IM = meas_IM(XY1);
		print("meas_IM");
		if(DEBUG) meas_IM.print(10,1);
		//Matrix measSrim = srim(meas_IM);
		//	print("measSrim");
		//	measSrim.print(20,10);
		Matrix qSrim = getQ(meas_IM);
		print("qSrim");
		if(DEBUG) qSrim.print(10,1);
		Matrix Unity = qSrim.transpose().times(qSrim);
		print("Unity");
		if(DEBUG) Unity.print(10,1);
		print("srimbox =" + srimbox);
		Matrix addSrim = addSrim(this.SrimArray.get(srimbox),meas_IM);
		this.SrimArray.set(srimbox, addSrim);
	}


	double measY(double x, double measy, double esty, double[] doubleAdaptors){

		print("x= " + x + " measy= " + measy + " esty= " + esty);
		double adSensProd = adSensProduct(x, doubleAdaptors);
		double ans = measy - esty + adSensProd;

		print("measy= " + measy + " esty= " + esty + " adSensProd= " + adSensProd);

		print("measy - esty + adSensProd" + (measy - esty + adSensProd));
		print("measy - esty + adSensProd = " + ans);
		return ans;
	}

	Matrix sensitivities(double x){
		double b = parameters[0];
		//		double a = adaptors[0];
		//		double y =  b*x*(1+a) ;

		double sens1 = -b*x*x;
		double sens2 = -b*x;
		double sens3 = 0;
		double[][] sens = {{sens2}};
		Matrix ans = new Matrix(sens);
		return ans;
	}





	double adSensProduct(double x, double[] doubleAdaptors){
		Matrix sens = sensitivities(x);
		print ("sens");
		if(DEBUG) sens.print(10,4);
		Matrix adaptors = convert1DArrayToMatrix(doubleAdaptors);
		print ("adaptors");
		if(DEBUG) adaptors.print(10,4);
		Matrix adSensProductMat = sens.times(adaptors);
		print ("adSensProductMat");
		if(DEBUG) adSensProductMat.print(10,8);
		
		double ans = adSensProductMat.det();  //convert to double
		return ans;
	}
	
	Matrix xy1(Matrix x, double y, double forgettingFactor){
		int sizex = x.getRowDimension();
		int sizexy1 = sizex+2;
		//construct empty matrix
		Matrix my_xy1 = new Matrix(sizexy1,1);
		my_xy1.setMatrix(0,sizex-1,0,0,x.times(forgettingFactor));
		//construct matrix with constant
		Matrix Y = new Matrix(1,1,y*forgettingFactor);
		my_xy1.setMatrix(sizex,sizex,0,0,Y);
		//construct matrix with constant
		Matrix ONE = new Matrix(1,1,1*forgettingFactor);
		my_xy1.setMatrix(sizex+1,sizex+1,0,0,ONE);
		return my_xy1;
	}
	
	static Matrix meas_IM (Matrix xy1){
		
		Matrix IM = xy1.times(xy1.transpose());
		return IM;
	}
	
	static Matrix srim(Matrix IM){
		Matrix mySrim = IM.qr().getR();
		return mySrim;
	}
	
	static Matrix getQ(Matrix IM){
		Matrix mySrim = IM.qr().getQ();
		return mySrim;
	}
	
	Matrix addSrim(Matrix Srim1, Matrix Srim2){
		if(Srim1.getRowDimension()!=Srim2.getRowDimension()){
			Matrix ansSrim = null;
			return ansSrim;
		}
		else{
			print("Srim1 ColumnDimension " + Srim1.getColumnDimension() );
			print("Srim2 ColumnDimension " + Srim2.getColumnDimension() );
			
			//convert srims to ims
			//Matrix IM1 = Srim1.transpose().times(Srim1);
			//Matrix IM2 = Srim2.transpose().times(Srim2);
			
			Matrix bigIM = new Matrix(
					Srim1.getRowDimension()+Srim2.getRowDimension(),
					Srim1.getColumnDimension());
			
			bigIM.setMatrix(
					0,Srim1.getRowDimension()-1,
					0,Srim1.getColumnDimension()-1,Srim1);
			bigIM.setMatrix(
					Srim1.getRowDimension(),
					Srim1.getRowDimension()+Srim2.getRowDimension()-1,
					0,Srim1.getColumnDimension()-1,
					Srim2);
			QRDecomposition qr = bigIM.qr();
			Matrix ansSrim = qr.getR();
			return ansSrim;
		}
		
	}
	
	
}
