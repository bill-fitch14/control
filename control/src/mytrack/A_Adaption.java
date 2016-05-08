package mytrack;

import java.awt.Point;

import javax.swing.JTree;

import net.Serial_IO;

import myscene.A_MyScene;
import A_Inglenook.Inglenook;
import A_Inglenook.Myfunctions;

public class A_Adaption {
	
//	private static B_myGUI gui;
//	private static JTree jTree;
//	private static A_MyScene scene;// = new MyScene(null);
//	private static B2_LinkedLists arcAndNodeLinkedList = new B2_LinkedLists();
//	
//	private B3_Hashmaps arcAndNodeHashmaps = new B3_Hashmaps();
//	static private C1_BranchGroup branchGroup = new C1_BranchGroup();
//	private C2_DJGraph graph = new C2_DJGraph();
//	
//	/**
//	 * @param serialIO
//	 */
//	public A_Adaption(Serial_IO serialIO, String sceneName) {
//		// share the variables between all the programs
//		gui = new B_myGUI(arcAndNodeLinkedList, arcAndNodeHashmaps);
//		Point p = B0_Toplevel_Gui.SetFrameLocation(5, 30);
//		gui.setLocation(p);
//		gui.setVisible(true);
//		jTree = gui.GetJTree();
//		//99System.out.print("SIZE - " + jTree.size());
//		arcAndNodeLinkedList.set_jTree(jTree);
//		arcAndNodeLinkedList.traverseTreeSettingUpTrackNodesAndArcs();
//		//jTree = getArcAndNodeLinkedList().get_jTree();
//		B_myGUI.set_jTree(jTree);
//
//		// Calculate Hashmaps pointing to nodes and arcs parameters
//		// 1. set up the link to the lists
//		arcAndNodeHashmaps.setArcAndNodeLinkedLists(getArcAndNodeLinkedList());
//		// 2. Calculate the hashmaps and the parameters
//		arcAndNodeHashmaps.calculateHashmapsAndParameters();
//
//
//		// Calculate BackgroundGroup for track
//		branchGroup.set_HM(getArcAndNodeLinkedList(),
//				arcAndNodeHashmaps);
//		branchGroup.set_BG();
//		gui.set_modelBranchGroup(branchGroup);
//
//		// Calculate DJGraph
//		graph.set_HM(getArcAndNodeLinkedList(), arcAndNodeHashmaps);
//		graph.get_linkedLists().set_jTree(jTree);
//		graph.ProduceDJGraph();
//
//		// Model Engines and Routes
//		// this call gets the routes for each train and adds to linked
//		// list
//		// 	_modelArcAndNodeLinkedList.traverseTreeObtainingTrainParameters();
//
//
//
//		Long init = 104273685l;
//		Long[] layout = new Long[] { init, init };
//
//		//Myfunctions.openGraphics(8, layout);   //converts long to stacks 
//
//
//		//why is this done here. It would be better done in graph??
//		B2_LinkedLists.getTrainParametersfromLong(graph.get_DJG(),new long[] {Myfunctions.getInit(), Myfunctions.getInit() });
//		H1_Engine_Routes _engine_routes = arcAndNodeLinkedList.get_H1_EngineRoutes();  //engine routes now contain M5_Engine_Route
//
//		graph.set_EngineRoutes(_engine_routes);
//		graph.generate_shortest_routes(); // this at moment plots the train
//		branchGroup.set_BG_train();
//
//		try {
//			Boolean showSceneModel = true;
//			if (showSceneModel) {
//				scene = new A_MyScene(branchGroup,serialIO, sceneName);
//				Point p1 = B0_Toplevel_Gui.SetFrameLocation(45, 5);
//				scene.setLocation(p1);
//				gui.setSceneModel(scene);
//				scene.setVisible(true);
//			}
//		} finally {
//		}
//
//		//Inglenook.runInglenook2(init,graph,branchGroup);
//	}
//	public static B2_LinkedLists get_modelArcAndNodeLinkedList() {
//		return arcAndNodeLinkedList;
//	}
//
//	public static B2_LinkedLists getArcAndNodeLinkedList() {
//		return arcAndNodeLinkedList;
//	}
//
//	public static B_myGUI getGui() {
//		return gui;
//	}
}