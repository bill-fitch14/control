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
	
	public static float simSpeedSetting = 25f;
	
	public static float[] engineForwardAdaptors = {0,0.1f/10.0f,0};
	public static float[] engineReverseAdaptors = {0,0.1f/10.0f,0};
//	speed = ax^2 + bx + c where x is speed
	public static float adaptorA ; 
	public static float adaptorB ; 
	public static float adaptorC ; 

	public static int timeInterval = 20;
	public static int a=1;
	public static int pbl_instructionNo = 0;
	
	private static String direction = "forwards";
	public static float[] modelForwardAdaptors = {0};
	public static float[] modelReverseAdaptors = {0};
	public static float[] modelTestAdaptors = {0.005f};
	
	public static float[] forwardAdaptors = {0,0.1f/10.0f,0};
	public static float[] reverseAdaptors = {0,0.1f/10.0f,0};
	
	public static float getHspeed(){
		if(getDirection().equals("forwards")){
			adaptorA = forwardAdaptors[0];
			adaptorB = forwardAdaptors[1];
			adaptorC = forwardAdaptors[2];
		}else{
			adaptorA = reverseAdaptors[0];
			adaptorB = reverseAdaptors[1];
			adaptorC = reverseAdaptors[2];
		}
		return adaptorA*simSpeedSetting*simSpeedSetting + adaptorB*simSpeedSetting + adaptorC;
	}
	
	public static float getSimSpeedSetting(){
		float engSpeedmmPerSec = getEngineSpeedmmPerSec();
		if(getDirection().equals("forwards")){
			return modelForwardAdaptors[0] * engSpeedmmPerSec;
		}else{
			return modelReverseAdaptors[0] * engSpeedmmPerSec;
		}
	}
	
	public static float getEngineSpeedmmPerSec(){
		float engSpeed = getEngineSpeedSetting();
		if(getDirection().equals("forwards")){
			adaptorA = engineForwardAdaptors[0];
			adaptorB = engineForwardAdaptors[1];
			adaptorC = engineForwardAdaptors[2];
		}else{
			adaptorA = engineReverseAdaptors[0];
			adaptorB = engineReverseAdaptors[1];
			adaptorC = engineReverseAdaptors[2];
		}
		return adaptorA*engSpeed*engSpeed+ adaptorB*engSpeed + adaptorC;
	}
	
	public static float getEngineSpeedSetting(){
		return 0.1f;
	}

	//public static float scalemultiplier = 1.0f/160f;

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
