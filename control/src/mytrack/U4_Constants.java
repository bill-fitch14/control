package mytrack;

public final class U4_Constants {
	
	//public static float scalefactor = 1.6f/(3.43f);

	//public static float scalefactor  = 1/3.43f;
	
	public static float enginelength = 140.0f;
	public static float trucklength = 70.0f;
	public static float scalefactor  = 3.0f/160f;  // was3.0f/3.43f;
	public static float checkermultiplier = 100f;
	public static float engineSpeedSetting = 0.1f;


	//public static String projectlocation = "C:\\Users\\bill\\EclipseWorkspace\\java3dbill";
	public static String projectlocation = ".";
	//public static String projectlocation = "G:\\EclipseWorkspace\\java3dbill";
	//public static String projectlocationUnix = "C:/Users/bill/EclipseWorkspace/java3dbill";
	public static String projectlocationUnix = ".";
	//public static String projectlocationUnix = "C:/Users/user/EclipseWorkspace/java3dbill"
	
	public static float[] engineForwardAdaptors = {0.6851203338f,0.8246834774f,0.9979327364f};
	public static float[] engineReverseAdaptors = {1.2364993972f,0.7152182457f,1.0047031753f};
	public static float[] engineParameters = {1.0f,2.0f,3.0f};
//	speed = ax^2 + bx + c where x is speed
	public static float coeffA ; 
	public static float coeffB ; 
	public static float coeffC ; 

	public static int timeInterval = 1;
	public static int a=1;
	public static int pbl_instructionNo = 0;
	
	private static String direction = "forwards";
	public static float[] modelForwardAdaptors = {0.0700429020431013f};
	public static float[] modelReverseAdaptors = {0.0726797037101639f};
	public static float[] modelTestAdaptors = {0.005f};
	
	public static float[] forwardAdaptors = {0,0.1f/10.0f,0};
	public static float[] reverseAdaptors = {0,0.1f/10.0f,0};
	private static float[] modelParameters = {0.005f};
	
	public static float getHspeed(){
//		if(getDirection().equals("forwards")){
//			adaptorA = forwardAdaptors[0];
//			adaptorB = forwardAdaptors[1];
//			adaptorC = forwardAdaptors[2];
//		}else{
//			adaptorA = reverseAdaptors[0];
//			adaptorB = reverseAdaptors[1];
//			adaptorC = reverseAdaptors[2];
//		}
//		return adaptorA*simSpeedSetting*simSpeedSetting + adaptorB*simSpeedSetting + adaptorC;
		return getEngineSpeedSetting();
	}
	
	public static float getSimSpeedSetting(){
		float engSpeedmmPerSec = getEngineSpeedmmPerSec();
		if(getDirection().equals("forwards")){
			coeffA = modelParameters[0] * (1.0f - modelForwardAdaptors[0]);
		}else{
			coeffA = modelParameters[0] * (1.0f - modelReverseAdaptors[0]);
		}
		// simSpeedmmPerSec = (1-adaptor)*parameter*simSpeed = coeff*simSpeed
		//hence simSpeed = simSpeedmmPerSec/coeff
		return  engSpeedmmPerSec/coeffA;
	}
	
	public static float getEngineSpeedmmPerSec(){
		float engSpeed = getEngineSpeedSetting();
		if(getDirection().equals("forwards")){
			coeffA = engineParameters[0] * (1.0f - engineForwardAdaptors[0]);
			coeffB = engineParameters[1] * (1.0f - engineForwardAdaptors[1]);
			coeffC = engineParameters[2] * (1.0f - engineForwardAdaptors[2]);
		}else{
			coeffA = engineParameters[0] * (1.0f - engineReverseAdaptors[0]);
			coeffB = engineParameters[1] * (1.0f - engineReverseAdaptors[1]);
			coeffC = engineParameters[2] * (1.0f - engineReverseAdaptors[2]);
		}
		return coeffA*engSpeed*engSpeed+ coeffB*engSpeed + coeffC;
	}
	
	public static float getEngineSpeedSetting(){
		return U4_Constants.engineSpeedSetting;
	}
	
	public static void setEngineSpeedSetting(float engineSpeedSetting) {
		U4_Constants.engineSpeedSetting = engineSpeedSetting;
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
