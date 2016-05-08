package mytrack;

//public class N1_TimeBehavior {
//
//}
import java.util.Enumeration;
import javax.media.j3d.*;


public class N1_TimeBehavior extends Behavior
{
	private WakeupCondition timeOut;
	private int timeDelay;
	//private H1_Engine_Routes h1engineRoute;
	private M6_Trains_On_Routes trainsOnRoute;


	public N1_TimeBehavior(int td, //H1_Engine_Routes h1EngineRoutes, 
			M6_Trains_On_Routes trainsOnRoute)
	{ 
		timeDelay = td; 
		//h1engineRoute = h1EngineRoutes;
		this.trainsOnRoute = trainsOnRoute;
		timeOut = new WakeupOnElapsedTime(timeDelay);
	}


	public void initialize()
	{ wakeupOn(timeOut); }


	public void processStimulus(Enumeration criteria)
	{
		//3//99System.out.print("Processing stimulus");
		N2_Time.update();
		//h1engineRoute.update();      // ignore criteria
		trainsOnRoute.update();
		
		
		wakeupOn(timeOut);
	}


}  // end of TimeBehavior class
