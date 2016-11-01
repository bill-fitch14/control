package mytrack;

public final class U4_Constants {
	
	//public static float scalefactor = 1.6f/(3.43f);

	//public static float scalefactor  = 1/3.43f;
	
	public static float enginelength = 140.0f;
	public static float trucklength = 70.0f;
	public static float scalefactor  = 3.0f/160f;  // was3.0f/3.43f;
	public static float checkermultiplier = 100f;
	
	//public static String projectlocation = "C:\\Users\\bill\\EclipseWorkspace\\java3dbill";
	public static String projectlocation = ".";
	//public static String projectlocation = "G:\\EclipseWorkspace\\java3dbill";
	//public static String projectlocationUnix = "C:/Users/bill/EclipseWorkspace/java3dbill";
	public static String projectlocationUnix = ".";
	//public static String projectlocationUnix = "C:/Users/user/EclipseWorkspace/java3dbill"
	
	public static float simSpeedSetting = 50f;
	
	public static float[] forwardAdaptors = {0,0.1f/10.0f,0};
	public static float[] reverseAdaptors = {0,0.1f/10.0f,0};
//	speed = ax^2 + bx + c where x is speed
	public static float termA ; 
	public static float termB ; 
	public static float termC ; 
	public static float mmPerSecSpeedForwards; 
	public static float mmPerSecSpeedReverse; 

	public static int timeInterval = 20;
	public static int a=1;
	public static int pbl_instructionNo = 0;
	
	private static String direction = "forwards";
	public static float[] modelForwardAdaptors = {0};
	public static float[] modelReverseAdaptors = {0};
	
	public static float[] modelParameters ={0.005f};
	private static float[] engineParameters;
	public static float getMmPerSecSpeed(){
		
		if(getDirection().equals("forwards")){
			
			mmPerSecSpeedForwards = modelParameters[0] *(1-modelForwardAdaptors[0])*simSpeedSetting;
		}else{
			mmPerSecSpeedReverse = modelParameters[0] *(1-modelReverseAdaptors[0])*simSpeedSetting;
		}
		return mmPerSecSpeedForwards;
	}
	
	public static float getHspeed(){
		if(getDirection().equals("forwards")){
			termA = engineParameters[0]*(1-forwardAdaptors[0]);
			termB = engineParameters[1]*(1-forwardAdaptors[1]);
			termC = engineParameters[2]*(1-forwardAdaptors[2]);
		}else{
			termA = engineParameters[0]*(1-reverseAdaptors[0]);
			termB = engineParameters[1]*(1-reverseAdaptors[1]);
			termC = engineParameters[2]*(1-reverseAdaptors[2]);
		}
		
		float hspeed = solnOfQuadratic(termA,termB,termC,mmPerSecSpeedForwards);
		return hspeed;
	}

	//public static float scalemultiplier = 1.0f/160f;

	private static float solnOfQuadratic(float a, float b, float c, float x) {
		double y = Math.max((-b + Math.sqrt(b*b-4*a*c))/2*a,(-b - Math.sqrt(b*b-4*a*c))/2*a);
		float yy = (float) y;
		return yy;
	}

	public static String getDirection() {
		return direction;
	}

	public static void setDirection(String direction) {
		U4_Constants.direction = direction;
	}

	public static  String swapDirection() {
		if(U4_Constants.direction.equals("forwards")){
			U4_Constants.setDirection("reverse");
		}else{
			U4_Constants.setDirection("forwards");
		}
		return U4_Constants.direction;
	}
}
