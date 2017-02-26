	package mytrack;

import java.awt.Point;

import javax.swing.JTree;

import myscene.A_MyScene;
import net.Serial_IO;
import net.net_Console;

import A_Inglenook.Inglenook;
import A_Inglenook.Myfunctions;
import A_Inglenook.trainPosition;


public class A_Setup {

	private static B_myGUI gui;
	private static JTree jTree;
	private static A_MyScene scene;// = new MyScene(null);
	private static B2_LinkedLists arcAndNodeLinkedList = new B2_LinkedLists();
	
	private B3_Hashmaps arcAndNodeHashmaps = new B3_Hashmaps();
	static C1_BranchGroup branchGroup = new C1_BranchGroup();
	private static C2_DJGraph graph = new C2_DJGraph();
	
	/**
	 * @param serialIO
	 */
	public A_Setup(Serial_IO serialIO, String sceneName) {
		// share the variables between all the programs

		gui = new B_myGUI(arcAndNodeLinkedList, arcAndNodeHashmaps);
		Point p = B0_Toplevel_Gui.SetFrameLocation(5, 30);
		gui.setLocation(p);
		gui.setVisible(true);
		jTree = gui.GetJTree();
		//99System.out.print("SIZE - " + jTree.size());
		arcAndNodeLinkedList.set_jTree(jTree);
		arcAndNodeLinkedList.traverseTreeSettingUpTrackNodesAndArcs();
		//jTree = getArcAndNodeLinkedList().get_jTree();
		B_myGUI.set_jTree(jTree);

		// Calculate Hashmaps pointing to nodes and arcs parameters
		// 1. set up the link to the lists
		arcAndNodeHashmaps.setArcAndNodeLinkedLists(getArcAndNodeLinkedList());
		// 2. Calculate the hashmaps and the parameters
		arcAndNodeHashmaps.calculateHashmapsAndParameters();
		

		// Calculate BackgroundGroup for track
		branchGroup.set_HM(getArcAndNodeLinkedList(),
				arcAndNodeHashmaps);
		branchGroup.set_BG();
		gui.set_modelBranchGroup(branchGroup);

		// Calculate DJGraph
		getGraph().set_HM(getArcAndNodeLinkedList(), arcAndNodeHashmaps);
		getGraph().get_linkedLists().set_jTree(jTree);
		getGraph().ProduceDJGraph();

		// Model Engines and Routes
		// this call gets the routes for each train and adds to linked
		// list
		// 	_modelArcAndNodeLinkedList.traverseTreeObtainingTrainParameters();
		
		
		
		Long init = 104273685l;
		Long[] layout = new Long[] { init, init };
//		Myfunctions.setPole(new int[8+1]); 
		Myfunctions.openGraphics(8, layout);   //converts long to stacks 
//		Myfunctions.convertLongToStacks(layout,Myfunctions.getPole());
//		Myfunctions.drawStacks(Myfunctions.getPole());
		//arcAndNodeLinkedList.addStopPositions();
		
		Inglenook.set_routes(getGraph().get_DJG(), getGraph(), branchGroup);
		//ensure we can use trainPosition effectively
		trainPosition.settrainPositionParameters(getGraph(), B2_LinkedLists.trainsOnRoute, branchGroup);
		
		//why is this done here. It would be better done in graph??
		B2_LinkedLists.getTrainParametersfromLongAndSetStopAndSensorPositions(getGraph().get_DJG(),new long[] {Myfunctions.getInit(), Myfunctions.getInit() });
		

		//H1_Engine_Routes _engine_routes = arcAndNodeLinkedList.get_H1_EngineRoutes();  //engine routes now contain M5_Engine_Route
		
		//M6_Trains_On_Routes _trainsOnRoutes = arcAndNodeLinkedList.getTrainsOnRoute();
		
		//graph.set_EngineRoutes(_engine_routes);
		//graph.generate_shortest_routes(); // this at moment plots the train
		branchGroup.set_BG_train();
		
		//Inglenook.runInglenook2(init);

		//_trainsOnRoutes.gsr(graph.get_DJG());
		
		
		try {
			Boolean showSceneModel = true;
			if (showSceneModel) {
				scene = new A_MyScene(branchGroup,serialIO, sceneName);
				Point p1 = B0_Toplevel_Gui.SetFrameLocation(45, 5);
				scene.setLocation(p1);
				gui.setSceneModel(scene);
				scene.setVisible(true);
			}
		} finally {
		}
		
		
		
//		B2_LinkedLists.
		
		//Inglenook.runInglenook2(init,graph,branchGroup);
//		TestTrack.runInglenook2(init,graph,branchGroup);
	}

	//	public void set_modelArcAndNodeLinkedList(
	//			B2_LinkedLists _modelArcAndNodeLinkedList) {
	//		this._modelArcAndNodeLinkedList = _modelArcAndNodeLinkedList;
	//	}

	public static B2_LinkedLists get_modelArcAndNodeLinkedList() {
		return arcAndNodeLinkedList;
	}

	public static B2_LinkedLists getArcAndNodeLinkedList() {
		return arcAndNodeLinkedList;
	}

	public static B_myGUI getGui() {
		return gui;
	}

	public static C2_DJGraph getGraph() {
		return graph;
	}

	public void setGraph(C2_DJGraph graph) {
		this.graph = graph;
	}
}
