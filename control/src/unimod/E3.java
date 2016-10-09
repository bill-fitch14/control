package unimod;

import javax.swing.JTree;

import A_Inglenook.CreateTrainMovementDeque;

import A_Inglenook.MoveTrainUsingDeque;

import com.evelopers.common.exception.CommonException;
import com.evelopers.unimod.core.stateworks.Event;
import com.evelopers.unimod.runtime.EventProvider;
import com.evelopers.unimod.runtime.ModelEngine;
import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;
import com.evelopers.unimod.runtime.context.StateMachineContextImpl;

public class E3 implements EventProvider {
	
	private static ModelEngine engine = null;

	/**
	 * @unimod.event.descr start inglenook
	 */
	public static final String START = "start";
//	/**
//	 * @unimod.event.descr read from list
//	 */
//	public static final String SETROUTEREQUEST = "e_setroute";
	

	
	/**
	 * @unimod.event.descr run inglenook and create list
	 */
	public static void e_readList() {
		
		/*sets up the required parameter
		 * the model then runs the required command
		 */
		
		StateMachineContext context = createContext();
		Event e2 = null;
		
		//read from list that has already been set up
		String[] items = CreateTrainMovementDeque.getDeque().pollFirst();
		
		String command = items[0];
		switch(command){
		case "move":
			String from_branch = items[1]; 	//st1,st2,st3 or sth
			String to_branch = items[2];	//st1,st2,st3 or sth
			//now set up route and set flag to start the train moving
			
		
//		//interpret strings
//		String from = strings[0];
//		String to = strings[1];E
		
		
		e2 = new Event(SETROUTEREQUEST,
				new Parameter[] { P1.createRouteParameter(context, items)});
		}

		engine.getEventManager().handle(e2, context);
		
	}

	public static final String CREATELIST = "createList";

	/**
	 * @unimod.event.descr setRouteRequest
	 */
	public static final String SETROUTEREQUEST = "setRouteRequest";

	@Override
	public void init(ModelEngine engine) throws CommonException {
		this.engine = engine;

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

//	public static void e_addto_route(String[] strings) {
//		
//		//interpret strings
//		String from = strings[0];
//		String to = strings[1];
//		
//		StateMachineContext context = createContext();
//		Event e2 = new Event(SETROUTEREQUEST,
//				new Parameter[] { P1.addto_route(context, from), P1.addto_route(context, to) });
//
//		engine.getEventManager().handle(e2, context);
//		
//	}
//	
//	public static void e_stop_element_Picked(String[] stopTypeAndTrackElement) {
//
//		String stopType = stopTypeAndTrackElement[0];
//		String TrackElement = stopTypeAndTrackElement[1];
//
//		// 3//99System.out.print("e_stop_element_Picked: " + stopType + " " +
//		// TrackElement);
//
//		if (stopType.equals("stop_none")) {
//			StateMachineContext context = createContext();
//			// 3//99System.out.print("STOP NONE");
//			Event e = new Event(E_TrackElementPicked,
//					new Parameter[] { P2.createStopRequest(context,
//							TrackElement) });
//
//			engine.getEventManager().handle(e, context);
//		}
//		if (!stopType.equals("stop_none")) {
//			// 3//99System.out.print("NOT STOP NONE");
//			StateMachineContext context = createContext();
//			Event e = new Event(E_StopElementToBeRemoved,
//					new Parameter[] { P2.createStopRequest(context,
//							TrackElement) });
//
//			engine.getEventManager().handle(e, context);
//		}
//
//	}


	public static void e_traverse_route(String[] strings) {
		// TODO Auto-generated method stub
		
	}
	
	
	private static StateMachineContext createContext() {
		return new StateMachineContextImpl(P1.getApplicationContext(), null,
				new StateMachineContextImpl.ContextImpl());
	}

}
