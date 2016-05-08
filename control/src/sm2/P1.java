package sm2;

import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;
import com.evelopers.unimod.runtime.context.StateMachineContextImpl;

public class P1 {
	
	private static StateMachineContext.Context applicationContext = new StateMachineContextImpl.ContextImpl();

	
	public static StateMachineContext.Context getApplicationContext() {
		return applicationContext;
	}
	
	private static final String STACK_INSTRUCTION = "STACK_INSTRUCTION";

	
	public static Parameter createStackInstruction(StateMachineContext context,
			String stackInstruction[]){
		return new Parameter(STACK_INSTRUCTION , stackInstruction);
	}
	
	public static String[] getStackInstruction(StateMachineContext context){
		synchronized (context) {
    		if (context.getEventContext() == null) {
    			return null;
    		}
    		return ((String[]) context.getEventContext().getParameter(STACK_INSTRUCTION));
        }
	}
	
	//**************************************************************************

	// Parameters required for processing point request from buttons
	private static final String POINT_REQUEST = "POINTS_REQUEST";
	/**
	 * creates and adds to the Linked List of requests
	 * @param context
	 * @param pointno		  1 2 ...
	 * @param required_state  ON or OFF
	 * @return
	 */
	public static Parameter createPointRequest(StateMachineContext context,
			String[] pointRequest){
		//2//3//99System.out.print("creating parameter");
		return new Parameter(POINT_REQUEST, pointRequest);
	}
	
	
	public static String[] getPointRequest(StateMachineContext context){
		synchronized (context) {
    		if (context.getEventContext() == null) {
    			return null;
    		}
    		return ((String[]) context.getEventContext().getParameter(POINT_REQUEST));
        }
	}
}
