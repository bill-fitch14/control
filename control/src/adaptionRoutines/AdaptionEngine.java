package adaptionRoutines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import Jama.Matrix;
import Jama.QRDecomposition;
//import antlr.collections.List;
import mytrack.U4_Constants;


public class AdaptionEngine {

	int NoOfTimesRoutineHasBeenCalled = 0;

	static double[] parameters = {1.0,2.0,3.0};

	private Matrix matrixAdaptors;

	double forgettingfactor = 1;    //.99 = 50 measurements  no measurements = 1/(1-forgettingfactor^2)

	static Random rand = new Random();

	private Matrix globalSrim = new Matrix(5,5);
	double[][] globalSrimArray;

	public double[][] getGlobalSrim() {
		return globalSrim.getArray();
	}


	public void setGlobalSrim(double[][] globalSrim) {
		this.globalSrim = new Matrix(globalSrim);
	}

	static ArrayList<Matrix> SrimArray = new ArrayList<Matrix>();

	int noBoxes = 20;
	String AdaptionName;




	public AdaptionEngine(int noBoxes,String adaptionName, boolean initialise, float forgettingfactor) {
		super();
		this.noBoxes = noBoxes;
		this.AdaptionName=adaptionName;
		this.forgettingfactor = forgettingfactor;
		if (initialise==true){
			for(int i=0;i<=noBoxes;i++){
				SrimArray.add(new Matrix(5,5));
			}
			double[][] doubleAdaptors = {{0.5f},{0.5f},{0.5f}};
			matrixAdaptors = new Matrix(doubleAdaptors);

		}else{
			for (int i = 0; i < noBoxes; i++) {
				Matrix srim;
				try {
					srim = loadSRIM(i);
					if (srim == null){
						SrimArray.add(new Matrix(5,5));
					}else{
						SrimArray.add(srim);
					}


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


			try {
				matrixAdaptors = loadAdaptors();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}




	public static void main(String[] args) {

		//set up the srimarray
		for(int i=0;i<=80;i++){
			SrimArray.add(new Matrix(5,5));
		}
		boolean initialise = true;
		int noBoxes = 10;
		float forgettingfactor = 1;

		AdaptionEngine adaptModelForward = new AdaptionEngine(noBoxes, "ModelForwardAdaption", initialise, forgettingfactor);

		for (int i = 1;i<5;i++){
			long simRevTime = 1;
			//			i=1;
			long simDistancetravelled = (long) (2.0f*parameters[0]*i*i +2.0f*parameters[1]*i+2.0f*parameters[2]);
			float speedatSENR2 = i;
			int min = 0;
			int max = 1000;
			adaptModelForward.processMeasurement(
					simRevTime , simDistancetravelled, speedatSENR2, min, max);
		}
	}


	public boolean processMeasurement(long millis , long distancetravelled, float enginesetting,
			float min, float max){

		System.out.println("SRIM Analysis: " + AdaptionName + " engine setting: " + enginesetting);
		double x = (double)enginesetting;				//80 for simulation
		double y = (double)distancetravelled/(double)millis;  // 80/100 = 0.8
		System.out.println("distancetravelled = "+distancetravelled + "millis = "+millis + "y = "+y );
		double xmin = min;
		double xmax = max;
		System.out.println("millis =" + millis);
		System.out.println("distancetravelled =" + distancetravelled);
		System.out.println("enginesetting =" + enginesetting);
		//		System.out.println("direction =" + direction);
		int srimoffset;
		//		srimoffset = getSrimOffset(direction);
		//		System.out.println("srimoffset =" + srimoffset);
		int srimbox = (int) Math.round((x-xmin)/(xmax-xmin)*noBoxes);//+ srimoffset;
		System.out.println("srimbox =" + srimbox);

		//produce the srim

		processMeasurement(x,y, srimbox, forgettingfactor);
		System.out.println("finished processMeasurement");
		// the new srim is now saved in SrimArray
		// save the newly updated srimi
//		try {
//			saveSRIM(SrimArray.get(srimbox), srimbox);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		//get the global srim from SrimArray
		globalSrim = calcGlobalSrim();

		//		//save the srim
		//		try {
		//			saveSRIM(globalSrim);
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		//		
		//update the adaptors if enough measurements have been taken
		NoOfTimesRoutineHasBeenCalled++;
		Matrix newAdaptors = getParameters2(NoOfTimesRoutineHasBeenCalled);
		if(NoOfTimesRoutineHasBeenCalled>=4){


			saveAdaptorsToU4Constants(newAdaptors);
			try {
				saveAdaptorsToFile(newAdaptors);
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
		//	    Path path = Paths.get("/Users/Home/");
		CSVWriter writer;

		writer = new CSVWriter(new FileWriter(filename));

		// TODO Auto-generated catch block


		for (int i=0; i < stringAdaptors.length; i++) {
			writer.writeNext(stringAdaptors[i]);
		}
		writer.close();

	}


	private void saveAdaptorsToU4Constants(Matrix Adaptors) {

		double[] adaptors1D = get1DAdaptors(Adaptors);

		float[] floatAdaptors = new float[adaptors1D.length];
		for (int i = 0 ; i < adaptors1D.length; i++)
		{
			floatAdaptors[i] = (float) adaptors1D[i];
		}
		if( this.AdaptionName.equals("ModelForwardAdaption")){
			U4_Constants.modelForwardAdaptors = floatAdaptors;
		}else if( this.AdaptionName.equals("ModelReverseAdaption")){
			U4_Constants.modelReverseAdaptors = floatAdaptors;
		}else{
			System.out.println("error in saving adaptors");
			return;
		}
		System.out.println("adaptors have been saved. \n  A=" + floatAdaptors[0] + "\n  B=" + floatAdaptors[1] + "\n  C=" + floatAdaptors[2]);

	}


	private double[] get1DAdaptors(Matrix Adaptors) {
		double[][] adaptors2D = Adaptors.getArray();
		double[] adaptors1D = {0,0,0};
		for (int i=0;i<3;i++){
			adaptors1D[i] = adaptors2D[i][0];
		}
		return adaptors1D;
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
		//	    Path path = Paths.get("/Users/Home/");
		CSVWriter writer;

		writer = new CSVWriter(new FileWriter(filename));

		// TODO Auto-generated catch block


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
			String[][] stringAdaptors =  lines.toArray(new String[lines.size()][]);



			int tableStringLengthcol=stringAdaptors.length;
			int tableStringLengthrow=stringAdaptors[0].length;
			double [][]doubleAdaptors= null;

			for(int i=0; i<tableStringLengthcol; i++) {
				for(int j=0; j<tableStringLengthrow; j++) {
					doubleAdaptors[i][j]= Double.valueOf(stringAdaptors[i][j]).doubleValue();
				}
			}
			//convert double[] to matrix
			Matrix srim = new Matrix(doubleAdaptors);
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
			double [][]doubleAdaptors= null;

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
		String filename = "Adaptors_" + this.AdaptionName ;
		return filename;
	}


	private String getSrimFilename(int index) {
		String filename = "Srim_" + this.AdaptionName + "_" + index;
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

		System.out.println("millisxxx" + millis);

	}

	public void testString(String direction){

		System.out.println("directionxxx" + direction);

	}

//	private Matrix calcGlobalSrim() {
//		//int srimoffset = getSrimOffset(direction);
//		Matrix globalSrim = new Matrix(5,5);
//		for (int i = 0;i<noBoxes;i++){
//			print("SrimArray.get(i)");
//			SrimArray.get(i).print(10,2);
//			Matrix addsrim = addSrim(globalSrim,SrimArray.get(i));
//			globalSrim=addsrim;
//		}
//		return globalSrim;
//	}
	
	private Matrix calcGlobalSrim() {
		System.out.println("in calcGlobalSrim");
		Matrix globalSrim = new Matrix(5,5);
		for(Matrix mySrim : SrimArray){
			print("mySRIM in SrimArray");
			mySrim.print(10,2);
			print("globalSrimbefore");
			globalSrim.print(10,2);
			Matrix addsrim = addSrim(globalSrim,mySrim);
			globalSrim=addsrim;
			print("globalSrimafter");
			globalSrim.print(10,2);
		}
		
	return globalSrim;
}

	private double[] calculateAdaptors() {

		//		print ("Srim " + x);
		globalSrim.print(10,1);
		Matrix IM = globalSrim.transpose().times(globalSrim);
		//		print ("IM " + x);
		IM.print(10,1);

		int xDim = IM.getColumnDimension() -3;
		Matrix XpX = IM.getMatrix(0, xDim, 0, xDim);
		Matrix XpY = IM.getMatrix(0, xDim, xDim+1, xDim+1);

		Matrix XpU = IM.getMatrix(0, xDim, xDim+2, xDim+2);
		print ("XpU");
		XpU.print(10,1);
		Matrix YpY = IM.getMatrix(xDim+1, xDim+1, xDim+1, xDim+1);
		Matrix YpU = IM.getMatrix(xDim+1, xDim+1, xDim+2, xDim+2);
		print ("XpX");
		XpX.print(10,1);
		print ("YpY");
		YpY.print(10,1);
		print ("YpU");
		YpU.print(10,1);

		Matrix UpU = IM.getMatrix(xDim+2, xDim+2, xDim+2, xDim+2);
		print ("UpU");
		UpU.print(10, 1);

		Matrix XpXIT = XpX.inverse().transpose();
		print ("XpXIT");
		XpXIT.print(10, 1);

		print ("XpY");
		XpY.print(10,1);

		Matrix D = XpX.inverse().transpose().times(XpY);
		print ("adaptors");
		D.print(10,1);

		double ymean = YpU.det() / UpU.det();  //mean of sensitivities
		Matrix xmean = XpU.times(1/ UpU.det());
		double[][] adaptors2D = D.getArray();
		return adaptors2D[0];
	}

	private Matrix getParameters2(int noOfTimesRoutineHasBeenCalled2) {

		globalSrim.print(10,1);
		Matrix RZS = globalSrim;

		int xDim = RZS.getColumnDimension() -3;
		Matrix R = RZS.getMatrix(0, xDim, 0, xDim);
		Matrix Z = RZS.getMatrix(0, xDim, xDim+1, xDim+1);
		Matrix sz = RZS.getMatrix(0, xDim, xDim+2, xDim+2);


		Matrix f = RZS.getMatrix(xDim+1, xDim+1, xDim+1, xDim+1);
		Matrix sy = RZS.getMatrix(xDim+1, xDim+1, xDim+2, xDim+2);

		Matrix sm = RZS.getMatrix(xDim+2, xDim+2, xDim+2, xDim+2);

		print ("R");
		R.print(10,1);
		print ("f");
		f.print(10,1);
		print ("sy");
		sy.print(10,1);
		if (noOfTimesRoutineHasBeenCalled2>=4){
			Matrix D = R.inverse().times(Z);
			print ("adaptors");
			D.print(10,1);
			//
			//		double ymean = sy.det() / sm.det();  //mean of sensitivities
			//		Matrix xmean = sz.times(1/ sm.det());
			double[][] adaptors2D = D.getArray();
			return D;

		}else{
			return null;
		}
	}


	private static void print(String x){
		System.out.print(x);
	}






	double est(double x, double parameters[], Matrix matrixAdaptors2){
		double a = parameters[0];
		double b = parameters[1];
		double c = parameters[2];

		double[] adaptors1D = get1DAdaptors(matrixAdaptors2);
		double aa = adaptors1D[0];
		double bb = adaptors1D[1];
		double cc = adaptors1D[2];

		double y =  a*(1-aa)*x*x+ b*(1-bb)*x + c*(1-cc) ;
		print ("est= ");
		System.out.println(y);
		return y;
	}

	void processMeasurement( 
			double x, double yy, int srimbox, double forgettingfactor){
		System.out.println("processMeasurement");
		double esty = est(x, parameters, matrixAdaptors);
		System.out.println("x= " + x + " yy= " + yy + " esty= " + esty);

		double y = measY(x, yy, esty);
		print("y="+y);
		Matrix X = sensitivities(x, matrixAdaptors);
		print ("sensitivities");
		X.print(10,1);
		Matrix XY1 = xy1(X,y,forgettingfactor);
		print ("XY1");
		XY1.print(10,1);

		Matrix meas_IM = meas_IM(XY1);
		print("meas_IM");
		meas_IM.print(10,2);
		//Matrix measSrim = srim(meas_IM);
		//	print("measSrim");
		//	measSrim.print(20,10);
		//		Matrix qSrim = getQ(meas_IM);
		//			print("qSrim");
		//			qSrim.print(10,1);
		//		Matrix Unity = qSrim.transpose().times(qSrim);
		//			print("Unity");
		//			Unity.print(10,1);
		System.out.println("srimbox =" + srimbox);
		//		print("meas_IM");
		//		meas_IM.print(10,1);
		//		print("this.SrimArray.get(srimbox)");
		//		this.SrimArray.get(srimbox).print(10,1);
		Matrix temp =XY1.transpose();

		print("this.SrimArray.get(srimbox)");
		this.SrimArray.get(srimbox).print(10,1);
		print("temp");
		temp.print(10,1);
		Matrix addSrim = addSrim(this.SrimArray.get(srimbox),temp);
		//		this.SrimArray.get(srimbox).print(10,1);
		this.SrimArray.set(srimbox, addSrim);

	}


	double measY(double x, double measy, double esty){

		System.out.println("x= " + x + " measy= " + measy + " esty= " + esty);
		double adSensProd = adSensProduct(x);
		double ans = measy - esty + adSensProd;

		System.out.println("measy= " + measy + " esty= " + esty + " adSensProd= " + adSensProd);

		System.out.println("measy - esty + adSensProd" + (measy - esty + adSensProd));
		System.out.println("measy - esty + adSensProd" + ans);
		return ans;
	}

	Matrix sensitivities(double x, Matrix matrixAdaptors2){
		double a = parameters[0];
		double b = parameters[1];
		double c = parameters[2];

		double[] adaptors1D = get1DAdaptors(matrixAdaptors2);
		double aa = adaptors1D[0];
		double bb = adaptors1D[1];
		double cc = adaptors1D[2];

		//		double y =  a*(1-aa)*x*x+ b*(1-bb)*x + c*(1-cc) ;

		double sens1 = -a*x*x;
		double sens2 = -b*x;
		double sens3 = -c;
		double[][] sens = {{sens1},{sens2},{sens3}};
		Matrix ans = new Matrix(sens);
		print ("sens");
		ans.print(10,1);
		return ans;
	}



	double adSensProduct(double x){
		Matrix sens = sensitivities(x, matrixAdaptors);
		Matrix adSensProductMat = sens.transpose().times(matrixAdaptors);
		print ("adSensProductMat");
		adSensProductMat.print(10,1);

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

	static Matrix addSrim(Matrix Srim1, Matrix Srim2){
		if(Srim1.getColumnDimension()!=Srim2.getColumnDimension()){
			Matrix ansSrim = null;
			return ansSrim;
		}
		else{
//			print("\nSrim1 ColumnDimension " + Srim1.getColumnDimension() );
//			print("\nSrim2 ColumnDimension " + Srim2.getColumnDimension() );
			
			//convert srims to ims
//			Matrix IM1 = Srim1.transpose().times(Srim1);
//			Matrix IM2 = Srim2.transpose().times(Srim2);
			
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
