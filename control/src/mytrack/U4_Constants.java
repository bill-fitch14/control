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
	
	public static float speed = 80f;

	public static int timeInterval = 20;
	public static int a=1;
	public static int pbl_instructionNo = 0;
	
	private static String direction = "forwards";

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
