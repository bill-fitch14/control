package sm2;

import javax.swing.JTree;

import net.Serial_IO;
import net.net_Console;
import mytrack.A_Setup;
import mytrack.A_Threads;
import sm2.P1;
import unimod.P2b;
import A_Inglenook.CreateTrainMovementDeque;
import A_Inglenook.MoveTrainUsingDeque;

import com.evelopers.common.exception.CommonException;

import com.evelopers.unimod.runtime.EventProvider;
import com.evelopers.unimod.runtime.ModelEngine;


import com.evelopers.unimod.core.stateworks.Event;
import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;
import com.evelopers.unimod.runtime.context.StateMachineContextImpl;

public class E1 implements EventProvider {
	
	private static net_Console _eventConsole;
	private static net_Console _consoleModel;
	private static Serial_IO _serialModel;
	
	public static final String E_InitialiseStopRequest = "e_initialise";
	public static final String E_CreateStopRequest = "e_create_stop_request";
	public static final String E_TrackElementPicked = "e_track_position";
	public static final String E_StopElementToBeRemoved = "e_remove_stop";
	
	
	private static JTree _jTree = null;
	
	public static final String E_SetTrainPositionRequest = "e_setTrainPosition";

	/*
	 * Events
	 */
	/**
	 * @unimod.event.descr read from points stack
	 */
	public static final String E_POINTS_REQUEST = "e_points_request";
	/**
	 * @unimod.event.descr read from points stack
	 */
	public static final String E_READ_POINTS_STACK = "e_read_points_stack";
	/**
	 * @unimod.event.descr read from stack
	 */
	public static final String E_READ_STACK = "e_read_stack";
	
	/**
	 * @unimod.event.descr stop stack execution
	 */
	public static final String E_STOP_STACK = "e_stop_stack";
	
	/*
	 * functions to fire events
	 */
	public static void fire_read_stack_event() {

	String[] st = CreateTrainMovementDeque.readDeque0();
		

		StateMachineContext context = createContext();
		if(st==null){
			//99System.out.print("deque is null");
		}
		Event eread = new Event(
				E_READ_STACK,
				new Parameter[] { P1.createStackInstruction(context, st) });

		engine.getEventManager().handle(eread, context);
		
		//99System.out.print("read stack");
	}
	
	
	public static void fire_stop_stack_event() {

		String[] st = CreateTrainMovementDeque.readDeque0();

		StateMachineContext context = createContext();
		Event estop = new Event(
				E_STOP_STACK,
				new Parameter[0]);

		engine.getEventManager().handle(estop, context);
		
		//99System.out.print("stopped reading from stack");
	}

	/*
	 * functions to fire events
	 */
	public static void fire_read_points_stack_event() {

		//String[] st = M_TruckMovements.readDeque0();
		String[] st = C1.readPointsDeque();

		StateMachineContext context = createContext();
		if(st==null){
			//99System.out.print("points stack is null");
		}
		Event eread = new Event(
				E_READ_POINTS_STACK,
				new Parameter[] { P1.createStackInstruction(context, st) });

		engine.getEventManager().handle(eread, context);
		
		//99System.out.print("read stack");
	}

	/**
	 * @unimod.event.descr event e_process_point_request
	 */
	public static void e_process_point_request(String[] pointRequest) {

		// 2//3//99System.out.print("point request: " +
		// pointRequest[0].toString()+ " " + pointRequest[1].toString());

		StateMachineContext context = createContext();
		Event e2 = new Event(
				E_POINTS_REQUEST,
				new Parameter[] { P1.createPointRequest(context, pointRequest) });

		engine.getEventManager().handle(e2, context);
	}
	
	
	
//	public static void e_process_point_request(String[] strings) {
//		// TODO Auto-generated method stub
//		
//	}
	

	public static A_Threads threads;

	private static A_Threads threads2;


	@Override
	public void init(ModelEngine engine) throws CommonException {
		
		//99System.out.print("E1.init");
		E1.engine = engine;
		
//		final Thread thread = new Thread() {
//			public void run() {
//				while (!thread.isInterrupted()) {
//					try {
//						Thread.sleep(120000);
//					} catch (InterruptedException e) {
//						return;
//					}
//					StateMachineContext context = E1.createContext();
//					//E1.e_process_queue();
//				}
//			}
//		};
//		thread.start();
		E1.threads = new A_Threads();
		
		E1.threads.getModelSetup();
		//A_Setup.get_modelArcAndNodeLinkedList();
	}

	@Override
	public void dispose() {
	}
	
	private static StateMachineContext createContext() {
		return new StateMachineContextImpl(P1.getApplicationContext(), null,
				new StateMachineContextImpl.ContextImpl());
	}

	private static ModelEngine engine;

	public static ModelEngine getEngine() {
		if (engine == null) 
			throw new IllegalStateException();
		return engine;
	}


	public static net_Console get_eventConsole() {
		return _eventConsole;
	}

	public static Serial_IO get_serialModel() {
		return _serialModel;
	}

	// ***********************************************************************************
	/**
	 * @unimod.event.descr event set the train position
	 */
	public static void e_setTrainPosition(JTree myJTree) {
		_jTree = myJTree;

		StateMachineContext context = createContext();
		Event e0 = new Event(E_SetTrainPositionRequest, new Parameter[0]);
		engine.getEventManager().handle(e0, context);

	}
	
	/**
	 * @unimod.event.descr event new track element
	 */
	public static void e_track_element_Picked(String word2) {

		// 2//3//99System.out.print("e_track_element_Picked: " + word2);

		StateMachineContext context = createContext();
		Event e = new Event(E_TrackElementPicked,
				new Parameter[] { P2b.createStopRequest(context, word2) });

		engine.getEventManager().handle(e, context);
	}

	public static void e_stop_element_Picked(String[] stopTypeAndTrackElement) {

		String stopType = stopTypeAndTrackElement[0];
		String TrackElement = stopTypeAndTrackElement[1];

		// 3//99System.out.print("e_stop_element_Picked: " + stopType + " " +
		// TrackElement);

		if (stopType.equals("stop_none")) {
			StateMachineContext context = createContext();
			// 3//99System.out.print("STOP NONE");
			Event e = new Event(E_TrackElementPicked,
					new Parameter[] { P2b.createStopRequest(context,
							TrackElement) });

			engine.getEventManager().handle(e, context);
		}
		if (!stopType.equals("stop_none")) {
			// 3//99System.out.print("NOT STOP NONE");
			StateMachineContext context = createContext();
			Event e = new Event(E_StopElementToBeRemoved,
					new Parameter[] { P2b.createStopRequest(context,
							TrackElement) });

			engine.getEventManager().handle(e, context);
		}

	}
	

	// ***********************************************************************************


	/**
	 * @unimod.event.descr event request new stop
	 */
	public static void e_initialise(JTree jTree) {

		// 2//3//99System.out.print("stop request: ");
		_jTree = jTree;

		StateMachineContext context = createContext();
		Event e0 = new Event(E_InitialiseStopRequest, new Parameter[0]);
		engine.getEventManager().handle(e0, context);
		// JOptionPane.showMessageDialog(null, "I am happy.");
	}

}
