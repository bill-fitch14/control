package unimod;

import java.awt.Point;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import unimod.E1;

import myscene.A_MyScene;
import mytrack.A_Setup;
import mytrack.A_Threads;
import mytrack.B0_Toplevel_Gui;
import mytrack.B2_LinkedLists;
import mytrack.B3_Hashmaps;
import mytrack.B_myGUI;
import mytrack.C1_BranchGroup;
import mytrack.C2_DJGraph;
//import mytrack.H1_Engine_Routes;
import net.Serial_IO;
import net.net_Console;

import A_Inglenook.Inglenook;
import A_Inglenook.M_TruckMovements;
import A_Inglenook.Myfunctions;

import com.evelopers.common.exception.CommonException;
import com.evelopers.unimod.core.stateworks.Event;
import com.evelopers.unimod.runtime.EventProvider;
import com.evelopers.unimod.runtime.ModelEngine;
import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;
import com.evelopers.unimod.runtime.context.StateMachineContextImpl;

public class E1 implements EventProvider {
	private boolean started = false;

	private static ModelEngine engine = null;

	private static int e1Count = 0;

	private Thread thread;
	private Thread thread0;

	public static final String E_SetTrainPositionRequest = "e_setTrainPosition";

	public static final String E_InitialiseStopRequest = "e_initialise";
	public static final String E_CreateStopRequest = "e_create_stop_request";
	public static final String E_TrackElementPicked = "e_track_position";
	public static final String E_StopElementToBeRemoved = "e_remove_stop";




	private static JTree _jTree = null;


	/**
	 * @unimod.event.descr read stack event
	 */
	public static final String E_READ_STACK = "e_read_stack";

	/**
	 * @unimod.event.descr event e_process_point_request
	 */
	public static void e_read_stack() {

		String[] st = M_TruckMovements.readDeque0();

		StateMachineContext context = createContext();
		Event e2 = new Event(
				E_PointsRequest,
				new Parameter[] { P1.createStackInstruction(context, st) });

		engine.getEventManager().handle(e2, context);

		//99System.out.print("read stack");
	}
	/**
	 * @unimod.event.descr event e_PointsRequest
	 */
	public static final String E_PointsRequest = "e_process_point_request";
	/**
	 * @unimod.event.descr event e_process_point_request
	 */
	public static void e_process_point_request(String[] pointRequest) {

		// 2//3//99System.out.print("point request: " +
		// pointRequest[0].toString()+ " " + pointRequest[1].toString());

		StateMachineContext context = createContext();
		Event e2 = new Event(
				E_PointsRequest,
				new Parameter[] { P1.createPointRequest(context, pointRequest) });

		engine.getEventManager().handle(e2, context);
	}

	/**
	 * @unimod.event.descr event e_switch_point
	 */
	public static void e_process_queue() {

		StateMachineContext context = createContext();
		Event e2 = new Event(E_ProcessQueue, new Parameter[0]);
		// //2//3//99System.out.print("about to process the queue");

		engine.getEventManager().handle(e2, context);
	}



	private static net_Console _eventConsole;
	private static net_Console _consoleModel;
	private static Serial_IO _serialModel;

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

	/**
	 * @unimod.event.descr event request new stop
	 */
	public static void e_create_stop_request(String[] stopRequest) {

		// 2//3//99System.out.print("stop request: ");

		StateMachineContext context = createContext();
		Event e = new Event(E_CreateStopRequest, new Parameter[0]);

		engine.getEventManager().handle(e, context);
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

	public static JTree get_jTree() {
		return _jTree;
	}

	// ***********************************************************************************

	/**
	 * @unimod.event.descr event e_process_queue
	 */
	public static final String E_ProcessQueue = "e_process_queue";





	private static StateMachineContext createContext() {
		return new StateMachineContextImpl(P1.getApplicationContext(), null,
				new StateMachineContextImpl.ContextImpl());
	}

	//	private static B_myGUI gui;
	//private static JTree myJTree;
	//private static A_MyScene _sceneModel;// = new MyScene(null);
	// private static A_Toplevel toplevel = new A_Toplevel();
	//private A_MyScene _sceneAdaption;
	// private net_Console _consoleModel;
	//private net_Console _consoleAdaption;
	// private Serial_IO _serialModel;
	//private Serial_IO _serialAdaption;

	//	public static B2_LinkedLists _modelArcAndNodeLinkedList;
	//	private B3_Hashmaps _modelArcAndNodeHashmaps;
	//	public static B2_LinkedLists _adaptionArcAndNodeLinkedList;
	//	private B3_Hashmaps _adaptionArcAndNodeHashmaps;
	//
	//	private C1_BranchGroup _modelBranchGroup;
	//	private C1_BranchGroup _adaptionBranchGroup;
	//
	//	private C2_DJGraph _modelDJGraph;
	//	private C2_DJGraph _adaptionDJGraph;

	static A_Setup modelSetup;

	public static boolean mybreak = false;

	public static boolean mybreak1 = false;

	private Thread thread2;
	
	public static A_Threads threads;

	private static A_Threads threads2;

	@Override
	public void init(ModelEngine engine) throws CommonException {
		
		//99System.out.print("E1.init");
		E1.engine = engine;
		

		E1.threads = new A_Threads();
		

	}
//	@Override
//	public void init(ModelEngine engine) throws CommonException {
//		this.engine = engine;
//		thread0 = new Thread() {
//
//			public void run() {
//				//99System.out.print("running init");
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				modelSetup = new A_Setup(_serialModel, "Model");
//
//
//				//				// share the variables between all the programs
//				//				_modelArcAndNodeLinkedList = new B2_LinkedLists();
//				//				_adaptionArcAndNodeLinkedList = new B2_LinkedLists();
//				//				_modelArcAndNodeHashmaps = new B3_Hashmaps();
//				//				_adaptionArcAndNodeHashmaps = new B3_Hashmaps();
//				//
//				//				_modelBranchGroup = new C1_BranchGroup();
//				//				_adaptionBranchGroup = new C1_BranchGroup();
//				//
//				//				_modelDJGraph = new C2_DJGraph();
//				//				_adaptionDJGraph = new C2_DJGraph();
//				//				// _BranchGroup2 = new C1_BranchGroup();
//				//
//				//				// gui
//				//
//				//				gui = new B_myGUI(_modelArcAndNodeLinkedList,
//				//						_modelArcAndNodeHashmaps);
//				//				Point p = B0_Toplevel_Gui.SetFrameLocation(5, 30);
//				//				gui.setLocation(p);
//				//				gui.setVisible(true);
//				//				myJTree = gui.GetJTree();
//				//				//99System.out.print("SIZE - " + myJTree.size());
//				//				// Model
//				//				// Calculate Lists for nodes arcs points etc
//				//				_modelArcAndNodeLinkedList.set_jTree(myJTree);
//				//				_modelArcAndNodeLinkedList
//				//						.traverseTreeSettingUpTrackNodesAndArcs();
//				//				// 2//3//99System.out.print(_ArcAndNodeLinkedList.toString());
//				//				myJTree = get_modelArcAndNodeLinkedList().get_jTree();
//				//				B_myGUI.set_jTree(myJTree);
//				//
//				//				// Calculate Hashmaps pointing to nodes and arcs parameters
//				//				// 1. set up the link to the lists
//				//				_modelArcAndNodeHashmaps
//				//						.setArcAndNodeLinkedLists(get_modelArcAndNodeLinkedList());
//				//				// 2. Calculate the hashmaps and the parameters
//				//				_modelArcAndNodeHashmaps.calculateHashmapsAndParameters();
//				//
//				//				// Calculate BackgroundGroup for track
//				//				_modelBranchGroup.set_HM(get_modelArcAndNodeLinkedList(),
//				//						_modelArcAndNodeHashmaps);
//				//				_modelBranchGroup.set_BG();
//				//				gui.set_modelBranchGroup(_modelBranchGroup);
//				//
//				//				// Calculate DJGraph
//				//				_modelDJGraph.set_HM(get_modelArcAndNodeLinkedList(),
//				//						_modelArcAndNodeHashmaps);
//				//				_modelDJGraph.get_linkedLists().set_jTree(myJTree);
//				//				_modelDJGraph.ProduceDJGraph();
//				//
//				//				// Model Engines and Routes
//				//				// this call gets the routes for each train and adds to linked
//				//				// list
//				//				// 	_modelArcAndNodeLinkedList.traverseTreeObtainingTrainParameters();
//				//				Long init = 104273685l;
//				//				Long[] layout = new Long[] { init, init };
//				//
//				//				Myfunctions.openGraphics(8, layout);
//				//
//				//				_modelArcAndNodeLinkedList.addStopPositions();
//				//				
//				//				_modelArcAndNodeLinkedList
//				//						.getTrainParametersfromLong(new long[] {
//				//								Myfunctions.getInit(), Myfunctions.getInit() });
//				//				H1_Engine_Routes _model_engine_routes = _modelArcAndNodeLinkedList
//				//						.get_H1_EngineRoutes();
//				//
//				//				_modelDJGraph.set_EngineRoutes(_model_engine_routes);
//				//				_modelDJGraph.generate_shortest_routes(); // this at moment
//				//															// plots the train
//				//				_modelBranchGroup.set_BG_train();
//				//				// _modelDJGraph.
//
//				// **********************************
//
//				// we no have the route and the segment containing the train
//				// position
//				// need to generate the path and find the position of the train
//				// on the path
//				// then in the branchgroup plot the train
//
//				// in the last action of traverseTreeObtainingTrainParameters
//				// we:
//				// get the shortest route for each train
//
//				// put the train on the track by generating the branchgroup
//
//				// //Adaption
//				// _adaptionArcAndNodeLinkedList.set_jTree(myJTree);
//				// _adaptionArcAndNodeLinkedList.traverseTreeSettingUpTrackNodesAndArcs();
//				//
//				// //Calculate Hashmaps pointing to nodes and arcs parameters
//				// //1. set up the link to the lists
//				// _adaptionArcAndNodeHashmaps.setArcAndNodeLinkedLists(_adaptionArcAndNodeLinkedList);
//				// //2. Calculate the hashmaps and the parameters
//				// _adaptionArcAndNodeHashmaps.calculateHashmapsAndParameters();
//				//
//				// //2//3//99System.out.print(_AdaptionArcAndNodeLinkedList.toString());
//				// _adaptionBranchGroup.set_HM(_adaptionArcAndNodeLinkedList,
//				// _adaptionArcAndNodeHashmaps);
//				// //_adaptionBranchGroup.set_BG();
//
//				// BranchGroup adaptiontrackBG =
//				// toplevel._adaptionBranchGroup.get_BG();
//
//				// Now calculate the scenes for the Model and Adaption modes
//
//				//				// Model
//				//				try {
//				//					Boolean showSceneModel = true;
//				//					if (showSceneModel) {
//				//
//				//						// _consoleModel = new net_Console("Model Console");
//				//						// Point p2 = SetFrameLocation(5,5);
//				//						// _consoleModel.setLocation(p2);
//				//						// _consoleModel.setVisible(true);
//				//						//
//				//						// _serialModel = new Serial_IO("Model",_consoleModel);
//				//						// _serialModel.SIO_connect(1);
//				//
//				//						// _serialModel = null;
//				//
//				//						// _sceneModel = new
//				//						// A_MyScene(_ArcAndNodeLinkedList,"Model");
//				//						// 2//3//99System.out.print ("showscenemodelstart " +
//				//						// _modelBranchGroup.getTest());
//				//						_sceneModel = new A_MyScene(_modelBranchGroup,
//				//								_serialModel, "Model");
//				//						// _sceneModel = new A_MyScene(trackBG,"Model");
//				//						Point p1 = B0_Toplevel_Gui.SetFrameLocation(45, 5);
//				//						_sceneModel.setLocation(p1);
//				//						gui.setSceneModel(_sceneModel);
//				//						_sceneModel.setVisible(true);
//				//					}
//				//				} finally {
//				//
//				//				}
//				// //Adaption
//				// try{
//				// Boolean showSceneAdaption = false;
//				// if(showSceneAdaption){
//				//
//				// _consoleAdaption = new net_Console("Adaption Console");
//				// Point p2 = A_Toplevel.SetFrameLocation(25,5);
//				// _consoleAdaption.setLocation(p2);
//				// _consoleAdaption.setVisible(true);
//				//
//				// _serialAdaption = new Serial_IO("Adaption",_consoleAdaption);
//				// _serialAdaption.SIO_connect(2);
//				//
//				// _sceneAdaption= new
//				// A_MyScene(_adaptionBranchGroup,_serialAdaption,"Adaption");
//				// Point p1 = A_Toplevel.SetFrameLocation(45,50);
//				// _sceneAdaption.setLocation(p1);
//				// gui.setSceneModel(_sceneAdaption);
//				// _sceneAdaption.setVisible(true);
//				//
//				// }
//				// }
//				// finally{
//				// }
//
//				// _modelDJGraph.generate_shortest_routes(); //this at moment
//				// plots the train
//				// _modelBranchGroup.set_BG();
//				// _modelBranchGroup.set_BG();
//
//				// Finite State Machine
//				// Main.runFSM();
//			}
//		};
//		thread0.start();
//
//
//
//		thread = new Thread() {
//			public void run() {
//				while (!thread.isInterrupted()) {
//					try {
//						Thread.sleep(120000);
//					} catch (InterruptedException e) {
//						return;
//					}
//					// StateMachineContext context = createContext();
//					e_process_queue();
//				}
//			}
//		};
//		thread.start();
//
//		if (!started) {
//			SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
//					MMI inst = new MMI();
//
//					inst.setLocationRelativeTo(null);
//					inst.setVisible(true);
//
//					_eventConsole = new net_Console("Events");
//					Point p2 = B0_Toplevel_Gui.SetFrameLocation(25, 5);
//					_eventConsole.setLocation(p2);
//					_eventConsole.setVisible(true);
//				}
//			});
//		}
//
//		if (!started) {
//			SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
//					_consoleModel = new net_Console("Model Console");
//					Point p2 = B0_Toplevel_Gui.SetFrameLocation(5, 5);
//					_consoleModel.setLocation(p2);
//					_consoleModel.setVisible(true);
//
//					_serialModel = new Serial_IO("Model", _consoleModel);
//					_serialModel.SIO_connect(1);
//				}
//			});
//		}
//
//		thread2 = new Thread() {
//
//			public void run() {
//				//99System.out.print("running Inglenook");
//				//while (!thread.isInterrupted()) {
//				try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					return;
//				}
//
//
//
//				// StateMachineContext context = createContext();
//				//e_process_queue();
//				//}
//			}
//
//		};
//		thread2.start();
//	}


	/**
	 * Sends next event
	 */
	private void tick() {
		StateMachineContext context = createContext();
	}

	@Override
	public void dispose() {
		if (thread != null) {
			thread.interrupt();
		}
	}

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

	//	public static B_myGUI getGui() {
	//		return gui;
	//	}

	//	public void set_modelArcAndNodeLinkedList(
	//			B2_LinkedLists _modelArcAndNodeLinkedList) {
	//		this._modelArcAndNodeLinkedList = _modelArcAndNodeLinkedList;
	//	}
	//
	//	public static B2_LinkedLists get_modelArcAndNodeLinkedList() {
	//		return _modelArcAndNodeLinkedList;
	//	}
	//
	//	public static B2_LinkedLists get_adaptionArcAndNodeLinkedList() {
	//		return _adaptionArcAndNodeLinkedList;
	//	}

	// /**
	// * @unimod.event.descr event e_PointsRequest
	// */
	// public static final String E_PointsRequestQueue =
	// "e_process_point_request_queue";
	// /**
	// * @unimod.event.descr event e_Stop
	// */
	// public static final String E_Stop = "e_stop";

	// public void e_Timer() {
	// //int currentPrisoner = (int) (Math.random() * Prison.PRISONERS_NUMBER);
	//
	// StateMachineContext context = createContext();
	//
	// Event e1 = new Event(
	// E, new Parameter[]{
	// P1.createEventNo()
	// });
	// engine.getEventManager().handle(e1, context);
	// //P1.visitedBy(context, pointno);
	// }
	// public static void e_process_point_request_queue(String[] pointRequest) {
	// //2//3//99System.out.print("point request: " + pointRequest[0].toString()+
	// " " + pointRequest[1].toString());
	//
	// StateMachineContext context = createContext();
	// Event e2 = new Event(
	// E_PointsRequestQueue
	// ,
	// new Parameter[]{
	// P1.createPointRequestQueue1(context, pointRequest)
	// // now we need to do everything else as a controlled action
	// // create Point request needed to be done nere because it has an argument
	// pointrequest
	// // ,
	// // P1.createPointRequestQueue(context,pointRequest)
	// }
	// );
	//
	// engine.getEventManager().handle(e2, context);
	//
	// }
	//
	// public static void e_initialise(String string){
	// //started = true;
	// StateMachineContext context = createContext();
	// Event e1 = new Event(
	// E_Init,
	// new Parameter[]{
	// P1.createPointRequestQueue(context,string)
	// });
	// engine.getEventManager().handle(e1, context);
	// }
	//
	// public static void e_initialise2(){
	// //started = true;
	// StateMachineContext context = createContext();
	// Event e1 = new Event(
	// E_Init,
	// new Parameter[]{
	// P1.createPointRequestQueue(context,"xxxxx")
	// });
	// engine.getEventManager().handle(e1, context);
	// }

	// //
	// // private StateMachineContext createContext() {
	// // return new StateMachineContextImpl(
	// // P1.getApplicationContext(), null, new
	// StateMachineContextImpl.ContextImpl());
	// }

}
