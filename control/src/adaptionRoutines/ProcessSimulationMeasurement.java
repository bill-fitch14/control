package adaptionRoutines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.collections.functors.ForClosure;

import Jama.Matrix;
import Jama.QRDecomposition;
import antlr.collections.List;

public class ProcessSimulationMeasurement {
	
	double[] parameters = {1,2};
	double[] adaptors = {0,0};
	
	static Random rand = new Random();
	
	Matrix globalSrim = new Matrix(5,5);
	
	static ArrayList<Matrix> SrimArray = new ArrayList<Matrix>();
	
	
	int noBoxes = 10;
	
	private static boolean DEBUG = false;
	private static void print(String x){
		if (DEBUG ){
		System.out.print(x);
		}
	}
	
	public ProcessSimulationMeasurement(double millis , double distancetravelled, double simSetting){
		double x = simSetting;
		double y = distancetravelled/millis;
		double xmin = 0;
		double xmax = 1;
		int srimoffset;
//		if (direction.equals("forwards")){
//			srimoffset = 0;
//		}else{
//			srimoffset = noBoxes/2;
//		}
		int srimbox = (int) Math.round((x-xmin)/(xmax-xmin)*noBoxes);
		//produce the srim
		processMeasurement(x,y, srimbox);
		//get the global srim
		globalSrim = getGlobalSrim();
		//get the adaptors
		getParameters(x);
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


	
	private Matrix getGlobalSrim() {
			Matrix globalSrim = new Matrix(5,5);
			for(Matrix mySrim : ProcessSimulationMeasurement.SrimArray){
				Matrix addsrim = addSrim(globalSrim,mySrim);
				globalSrim=addsrim;
			}
			
		return globalSrim;
	}

	private void getParameters(double x) {
		
		print ("Srim " + x);
		globalSrim.print(10,1);
		Matrix IM = globalSrim.transpose().times(globalSrim);
		print ("IM " + x);
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
		if (x>=4){		
		Matrix D = XpX.inverse().transpose().times(XpY);
		print ("adaptors");
		D.print(10,1);
		}
		double ymean = YpU.det() / UpU.det();  //mean of sensitivities
		Matrix xmean = XpU.times(1/ UpU.det());
		
	}
	
	double meas(double x, double coeffs[]){
		double a = coeffs[0];
		double b = coeffs[1];
		double y = a*x + b;

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
		double sens1 = x;
		double sens2 = 1;
		double[][] sens = {{sens1},{sens2}};
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
	
	Matrix xy1(Matrix x, double y){
		int sizex = x.getRowDimension();
		int sizexy1 = sizex+2;
		Matrix my_xy1 = new Matrix(sizexy1,1);
		my_xy1.setMatrix(0,sizex-1,0,0,x);
		Matrix Y = new Matrix(1,1,y);
		my_xy1.setMatrix(sizex,sizex,0,0,Y);
		Matrix ONE = new Matrix(1,1,1);
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
			double x, double yy, int srimbox){
		double y = measY(x, yy);
			print("y="+y);
		Matrix X = sensitivities(x);
			print ("sensitivities");
			X.print(10,1);
		Matrix XY1 = xy1(X,y);
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
		Matrix addSrim = addSrim(this.SrimArray.get(srimbox),meas_IM);
		this.SrimArray.set(srimbox, addSrim);
	}
	
}
