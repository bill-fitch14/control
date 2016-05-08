package mytrack;

import javax.swing.JFrame;

public class C2_DJGraph {
	

	private B3_Hashmaps _HM;
	private B2_LinkedLists _linkedLists ;
	//private B4_Routes _routeslinkedLists ;
	
//	private H1_Engine_Routes _EngineRoutes;
	
	//called before using this class
	public void set_HM(B2_LinkedLists linkedLists,B3_Hashmaps HM) {
		this._HM = HM;
		this._linkedLists = linkedLists;
	}
	/**
	 * @return the _DJG
	 */
	public D_MyGraph get_DJG() {
		return _DJG;
	}

	private D_MyGraph _DJG = new D_MyGraph();
	
    public void displayGraph()
    {
        D_MyGraph applet = _DJG;
        applet.ads();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph demonstration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
	
	public void ProduceDJGraph() {
		
		
		
		for(E1_TNodeNames tNodeNames : _linkedLists.get_NodeLinkedList()){

			E3_TNode tnode = _HM.get_TNodesHashmap().get_TNode(tNodeNames);
			_DJG.AddNode(tNodeNames,tnode);
		}
		
		for(F1_TArcNames tArcNames : _linkedLists.get_ArcLinkedList()){

			F3_TArc tarc = _HM.get_TArcsHashmap().get_TArc(tArcNames);
			//3//99System.out.print(tarc.Arclength());
			//99System.out.print(tArcNames.get_items_as_String());
			_DJG.AddArc(tArcNames,tarc);
		}
		
		displayGraph();
				
		//Obtain the desired routes from the tree
		
		// set up the engine routes and the engine route 
		//_modelArcAndNodeLinkedList.traverseTreeObtainingTrainParameters();
		
		//_routeslinkedLists.traverseTreeObtainingDesiredRoutesForTrains();
		
		//the required route is given by _route
		//this is of the form: 1_F_2_B_0.5_To_For
		//in the general case we need to interpret it by replacing Ri by the corresponding route
		//but for now we just use it
		//1) generate the required path
			//Need to generate a list of the form 1_To_For,2_From_For
			//We use the algorithm
			//Start at the point on the arc
		//get the two nodes of the arc
		//try each node in turn, adding the length of the node to the point on the arc 
		//to get the length of the path
		//thus get the required path from one of the nodes plus the path from the point on the arc
			
		//2) check for any reversals, and split the paths and add arcs
		
		//3) generate the first path and add the train
		
		// now we want to generate the shortest paths and store then in the engine routes
		
		
		
		//we are now ready to move the engines along the routes
		
		//sbehav = new SimpleBehavior();
	}
	
//	public void generate_shortest_routes() {
//		get_EngineRoutes().setHM(_HM);
//		get_EngineRoutes().generate_shortest_routes(_DJG);
//		
//	}
	
	/**
	 * @param _EngineRoutes the _EngineRoutes to set
	 */
//	public void set_EngineRoutes(H1_Engine_Routes _EngineRoutes) {
//		this._EngineRoutes = _EngineRoutes;
//	}
	public void get_train_position(){
		
	}
	
	
	
	
//	/**
//	 * @return the _routeslinkedLists
//	 */
//	public B4_Routes get_routeslinkedLists() {
//		return _routeslinkedLists;
//	}
//	/**
//	 * @param _routeslinkedLists the _routeslinkedLists to set
//	 */
//	public void set_routeslinkedLists(B4_Routes _routeslinkedLists) {
//		this._routeslinkedLists = _routeslinkedLists;
//	}
	/**
	 * @return the _linkedLists
	 */
	public B2_LinkedLists get_linkedLists() {
		return _linkedLists;
	}
//	public H1_Engine_Routes get_EngineRoutes() {
//		return _EngineRoutes;
//	}
	public void set_linkedLists(B2_LinkedLists _linkedLists) {
		this._linkedLists = _linkedLists;
	}
}
