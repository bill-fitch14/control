package Adaption;

import java.io.File;
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


public class EngineAdaption {
	
	int NoOfTimesRoutineHasBeenCalled = 0;
	
	double[] parameters = {1,2,3};
	double[] adaptors = {0,0,0};
	
	double forgettingfactor = .99;    //.99 = 50 measurements  no measurements = 1/(1-forgettingfactor^2)
	
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
	

	public EngineAdaption(int noBoxes,String adaptionName, boolean initialise, float forgettingfactor) {
		super();
		this.noBoxes = noBoxes;
		this.AdaptionName=adaptionName;
		this.forgettingfactor = forgettingfactor;
		if (initialise==true){
		for(int i=0;i<=noBoxes;i++){
			SrimArray.add(new Matrix(5,5));
		}
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
		}
	}
	
	

	public double[] processEngineMeasurement(long millis , long distancetravelled, float enginesetting){
		
		System.out.println("SRIM Analysis: " + AdaptionName + " engine setting: " + enginesetting);
		double x = (double)enginesetting;				//between 0 and 1 for engine
		double y = (double)distancetravelled/(double)millis;
		double xmin = 0;
		double xmax = 1;
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
		double forgettingfactor = .99;
		processMeasurement(x,y, srimbox, forgettingfactor);
		
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
		if(NoOfTimesRoutineHasBeenCalled>4){
			double[] newAdaptors = getParameters();
			saveAdaptors(newAdaptors);
			return newAdaptors;
		}
		//update the adaptors
		return null;
	}
	
	private void saveAdaptors(double[] doubleadaptors) {

		float[] floatAdaptors = new float[doubleadaptors.length];
		for (int i = 0 ; i < doubleadaptors.length; i++)
		{
			floatAdaptors[i] = (float) adaptors[i];
		}
		if( this.AdaptionName.equals("EngineForwardAdaption")){
			U4_Constants.forwardAdaptors = floatAdaptors;
		}else if( this.AdaptionName.equals("EngineReverseAdaption")){
			U4_Constants.reverseAdaptors = floatAdaptors;
		}else{
			System.out.println("error in saving adaptors");
			return;
		}
		System.out.println("adaptors have been saved.  A=" + floatAdaptors[0]+ " B=" + floatAdaptors[1]+ " C=" + floatAdaptors[2]  );
		
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

	
//	public 	ProcessEngineMeasurement(){
//		
//		int xmax = 150;
//		int xmin = -100;
//		int noBoxes = 10;
//		
//		direction enginedirection = direction.FORWARDS;
//		
//
//		//produce a set of measurements
//		int offset;
//		for(int i = -100; i<150; i++){
//			double x = i;
//			int srimbox = (int) Math.round((x-xmin)/(xmax-xmin)*noBoxes);
//			if(enginedirection == direction.FORWARDS){
//				offset = 0;
//			}else{
//				offset = noBoxes; 
//			}
//			srimbox = srimbox+offset;
//			
//			//produce the srim
//			processMeasurement(millis, distancetravelled, srimbox);
//			//get the global srim
//			globalSrim = getGlobalSrim();
//			//get the adaptors
//			getParameters(x);
//		}
//	}
//	


	
	private Matrix calcGlobalSrim() {
			//int srimoffset = getSrimOffset(direction);
			Matrix globalSrim = new Matrix(5,5);
			for (int i = 0;i<noBoxes;i++){
				Matrix addsrim = addSrim(globalSrim,SrimArray.get(i));
				globalSrim=addsrim;
			}
		return globalSrim;
	}

	private double[] getParameters() {
		
//		print ("Srim " + x);
		globalSrim.print(10,1);
		Matrix IM = globalSrim.transpose().times(globalSrim);
//		print ("IM " + x);
		IM.print(10,1);
		
		int xDim = IM.getColumnDimension() -3;
		Matrix XpX = IM.getMatrix(0, xDim, 0, xDim);
		Matrix XpY = IM.getMatrix(0, xDim, xDim+1, xDim+1);
		Matrix XpU = IM.getMatrix(0, xDim, xDim+2, xDim+2);
		
		Matrix YpY = IM.getMatrix(xDim+1, xDim+1, xDim+1, xDim+1);
		Matrix YpU = IM.getMatrix(xDim+1, xDim+1, xDim+2, xDim+2);
		print ("XpX");
		XpX.print(10,1);
		print ("YpY");
		YpY.print(10,1);
		print ("YpU");
		YpU.print(10,1);
		
		Matrix UpU = IM.getMatrix(xDim+2, xDim+2, xDim+2, xDim+2);
	
		Matrix D = XpX.inverse().transpose().times(XpY);
		print ("adaptors");
		D.print(10,1);

		double ymean = YpU.det() / UpU.det();  //mean of sensitivities
		Matrix xmean = XpU.times(1/ UpU.det());
		double[][] adaptors2D = D.getArray();
		return adaptors2D[0];
	}
	
	private static void print(String x){
		System.out.print(x);
	}




	

	double meas(double x, double coeffs[]){
		double a = coeffs[0];
		double b = coeffs[1];
		double c = coeffs[2];
		double y = a*x*x + b*x + c ;

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
	
	double measY(double x, double measy){
		
		double esty = meas(x, adaptors);
		double adSensProd = adSensProduct(x);
		double ans = measy - esty + adSensProd;
		return ans;
	}
	
	Matrix sensitivities(double x){
		double sens1 = x*x;
		double sens2 = x;
		double sens3 = 1;
		double[][] sens = {{sens1},{sens2},{sens3}};
		Matrix ans = new Matrix(sens);
		return ans;
	}
	
	Matrix adaptors(){
		double[][] adaptors = {this.adaptors};
		Matrix ans = new Matrix(adaptors);
		return ans;
	}
	
	double adSensProduct(double x){
		Matrix sens = sensitivities(x);
		Matrix adaptors = adaptors();
		Matrix adSensProductMat = sens.times(adaptors);
		double ans = adSensProductMat.det();
		return ans;
	}
	
	Matrix xy1(Matrix x, double y, double forgettingFactor){
		int sizex = x.getRowDimension();
		int sizexy1 = sizex+2;
		//construct empty matrix
		Matrix my_xy1 = new Matrix(sizexy1,1);
		my_xy1.setMatrix(0,sizex-1,0,0,x);
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
	
	void processMeasurement( 
			double x, double yy, int srimbox, double forgettingfactor){
		double y = measY(x, yy);
			print("y="+y);
		Matrix X = sensitivities(x);
			print ("sensitivities");
			X.print(10,1);
		Matrix XY1 = xy1(X,y,forgettingfactor);
			print ("XY1");
			XY1.print(10,1);
		
		Matrix meas_IM = meas_IM(XY1);
			print("meas_IM");
			meas_IM.print(10,1);
		//Matrix measSrim = srim(meas_IM);
		//	print("measSrim");
		//	measSrim.print(20,10);
		Matrix qSrim = getQ(meas_IM);
			print("qSrim");
			qSrim.print(10,1);
		Matrix Unity = qSrim.transpose().times(qSrim);
			print("Unity");
			Unity.print(10,1);
			System.out.println("srimbox =" + srimbox);
		Matrix addSrim = addSrim(this.SrimArray.get(srimbox),meas_IM);
		this.SrimArray.set(srimbox, addSrim);
	}
	
}
