package unimod;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.evelopers.unimod.runtime.StateMachineConfig;
import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;
import com.evelopers.unimod.runtime.context.StateMachineContextImpl;

public class P1 {


	private static final String ADDTO_ROUTE = "add_to_route";

	private static final String POINT_REQUEST = "POINTS_REQUEST";
//
//	private static final String POINT_REQUEST_QUEUE1 = "POINT_REQUEST_QUEUE1";;
	

	private static StateMachineContext.Context applicationContext = new StateMachineContextImpl.ContextImpl();

	private static final String STACK_INSTRUCTION = "STACK_INSTRUCTION";

    private static final String STOP_LOCATION = "STOP_LOCATION";

	public static StateMachineContext.Context getApplicationContext() {
		return applicationContext;
	}
	
	//**************************************************************************

	// Parameters required for processing point request from buttons
	/**
	 * creates and adds to the Linked List of requests
	 * @param context
	 * @param pointno		  1 2 ...
	 * @param required_state  ON or OFF
	 * @return
	 */
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
	
	
	//**************************************************************************
	
	// For processing the stop requests

	public static String[] getStopLocation(StateMachineContext context){
		synchronized (context) {
    		if (context.getEventContext() == null) {
    			return null;
    		}
    		return ((String[]) context.getEventContext().getParameter(STOP_LOCATION));
        }
	}
	
	
	

	
	public static String[] getPointRequest(StateMachineContext context){
		synchronized (context) {
    		if (context.getEventContext() == null) {
    			return null;
    		}
    		return ((String[]) context.getEventContext().getParameter(POINT_REQUEST));
        }
	}
	
	
	
	
//	public static Parameter addto_route(StateMachineContext context,
//			String fromorto) {
//		
//		return new Parameter(ADDTO_ROUTE, fromorto);
//	}
	
	public static Parameter createRouteParameter(StateMachineContext context,
			String[] fromandto) {
		
		return new Parameter(ADDTO_ROUTE, fromandto);
	}
	
	public static String[] getRouteParameter(StateMachineContext context){
		synchronized (context) {
    		if (context.getEventContext() == null) {
    			return null;
    		}
    		return ((String[]) context.getEventContext().getParameter(ADDTO_ROUTE));
        }
	}
	
	
//	public static Parameter createPointRequestQueue1(
//			StateMachineContext context, String[] pointRequest) {
//		
//			//2//3//99System.out.print("creating parameter");
//			return new Parameter(POINT_REQUEST_QUEUE1, pointRequest);
//	}
//
//	public static String[] getPointRequestQueue1(StateMachineContext context){
//		synchronized (context) {
//			if (context.getEventContext() == null) {
//				return null;
//			}
//			return ((String[]) context.getEventContext().getParameter(POINT_REQUEST_QUEUE1));
//		}
//	}
//
//	
//	
//	/**
//	 * creates and adds to the Linked List of requests
//	 * @param context
//	 * @return
//	 */
//	public static Parameter createPointRequestQueue(StateMachineContext context){
//		//initialise the queue with a empty string
//		//2//3//99System.out.print("creating pointsrequestqueue");
//		
//		return new Parameter(POINTS_REQUEST_QUEUE, "xxxx");
//	}
//		
//	public static Parameter createPointRequestQueue(
//			StateMachineContext context, String string) {
//		// TODO Auto-generated method stub
//		return new Parameter(POINTS_REQUEST_QUEUE, string);
//		
//	}
//	
//	public static String getPointRequestQueue(StateMachineContext context) {
//		synchronized (context) {
//			if (context.getEventContext() == null) {
//				return null;
//			}
//			//2//3//99System.out.print("getting pointsrequestqueue ");
//			return (String) context.getEventContext().getParameter(POINTS_REQUEST_QUEUE);
//		}
//	}
//	
//
//	// Parameters required for processing the queue when the timer clicks in
//	
//
//	
//	
//
//
//
//
//
//
//	
//	public static String pollLatestPointRequestQueue(StateMachineContext context) {
//		synchronized (context) {
//			
//			String new_q = null;
//			String first = null;
//			
//			if (context.getEventContext() == null) {
//				//String[] S = {"0","OFF"};
//				return null ;
//			}
//			
//			String q = (String) context.getEventContext().getParameter(POINTS_REQUEST_QUEUE);
//			if (q.equals("")){
//				first = "nothing to switch";
//			} else if(q.matches(".*_.*")){
//				String[] items = q.split("_");
//				first = items[0];
//				new_q = StringUtils.substringAfter(q,first);
//				if (items.length>1){
//					new_q = StringUtils.substringAfter(new_q,"_");
//				}
//			}
//			new Parameter(POINTS_REQUEST_QUEUE, new_q);
//			
//			return first;
//		}
//	}
//	
//	public static String peekLatestPointRequestQueue(StateMachineContext context) {
//		synchronized (context) {
//			String new_q = null;
//			String first = null;
//			
//			if (context.getEventContext() == null) {
//				//String[] S = {"0","OFF"};
//				return null ;
//			}
//			
//			String q = (String) context.getEventContext().getParameter(POINTS_REQUEST_QUEUE);
//			if (q.equals("")){
//				first = "nothing to switch";
//			} else if(q.matches(".*_.*")){
//				String[] items = q.split("_");
//				first = items[0];
//			}
//
//			
//			return first;
//		}
//	}
//	
//
//	
//
//	
////	public static void addtoLinkedList(StateMachineContext context,
////			String pointno, String required_state) {
////		
////		Queue<String[]> Q = getPointRequestQueue(context);
////		String[] item = {pointno,required_state};
////		Q.add(item);
////		
////	}
//	
//	public static void addtoPointRequestQueue(StateMachineContext context) {
//		synchronized (context) {
//			if (context.getEventContext() == null) {
//			}
//			//ensure the pointrequestqueue exists
//			String Q = getPointRequestQueue(context);
//			
//			if (Q == null){
//				//2//3//99System.out.print("Q is null");
//			}
//			//get the point request to be added
//			String[] pointRequest = getPointRequest(context);
//			//2//3//99System.out.print("pointRequest =" + pointRequest[0] + " " + pointRequest[1]);
//			//add to the string
//			Q = Q + "_" + pointRequest.toString();
//			
//			//2//3//99System.out.print("Q " + Q);
//		}
//	}	
		
	/**
	 * Fetches points request queue config from <b>application</b> context.
	 * @return  points request' configs
	 */
	public static StateMachineConfig[] getQueue(StateMachineContext context) {
		synchronized (context) {
			return ((StateMachineConfig[]) context.getApplicationContext().getParameter(
					ADDTO_ROUTE));
		}
	}







}
