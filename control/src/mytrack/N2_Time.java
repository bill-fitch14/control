package mytrack;

public class N2_Time {
	
	private static long prevFrameTime = 0;
	private static long currentFrameTime = 0;
	private static long TimeInterval;
	private static double TimeIntInSecs;
	
	
	public static void update(){

		prevFrameTime = currentFrameTime;
		currentFrameTime = System.nanoTime();
		if (prevFrameTime == 0){
			TimeInterval =  0;
			TimeIntInSecs = 0f;
		} else {
			TimeInterval =  currentFrameTime - prevFrameTime;
			TimeIntInSecs = TimeInterval/10E9f;
		}
	}

	//speed is in metres per second
	public static double getDistance(double speed){
		double distance = speed * TimeIntInSecs;
//		//99System.out.print("speed = " + speed + " TimeIntInSecs = " + TimeIntInSecs);
		return distance;
	}
}
