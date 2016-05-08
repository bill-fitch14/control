package unimod;

import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;

/*
 * The events are passed data which they store in a parameter
 * using a create parameters method e.g. createStopRequest 
 * 
 * The controls read the parameters created in the first stage and act on them
 * using a get parameters method
 * 
 */

public class P2b {
	
	private static final String STOP_REQUEST = "STOP_REQUEST";

	public static Parameter createStopRequest(StateMachineContext context,
			String word2) {
		//2//3//99System.out.print("createStopRequest " + word2);
		return new Parameter(STOP_REQUEST, word2);
	}
	
	public static String getStopRequest(StateMachineContext context){
		synchronized (context) {
    		if (context.getEventContext() == null) {
    			return null;
    		}
    		String word2 = (String) context.getEventContext().getParameter(STOP_REQUEST);
    		//2//3//99System.out.print("getStopRequest " + word2);
    		return word2;
        }
	}
	
	private static final String REMOVE_STOP_REQUEST = "REMOVE_STOP_REQUEST";
	
	public static Parameter createRemoveStopRequest(StateMachineContext context,
			String word2) {
		//2//3//99System.out.print("createStopRequest " + word2);
		return new Parameter(REMOVE_STOP_REQUEST, word2);
	}
	public static String getremoveStopRequest(StateMachineContext context){
		synchronized (context) {
    		if (context.getEventContext() == null) {
    			return null;
    		}
    		String word2 = (String) context.getEventContext().getParameter(REMOVE_STOP_REQUEST);
    		//2//3//99System.out.print("removeStopRequest " + word2);
    		return word2;
        }
	}

}
