package mytrack;

import java.awt.Color;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;

import myscene.Apperance;
import myscene.GroundShape;
import myscene.ModelLoader;

//import com.sun.j3d.loaders.Scene;
//import com.sun.j3d.loaders.objectfile.ObjectFile;
//import com.sun.j3d.utils.universe.SimpleUniverse;

public class C1_BranchGroup {
	
	/**
	 * The branchgroup of the track
	 * 
	 * For this class we have B2_Linked Lists as the starting point
	 * From the names in these links we get the items (nodes, arcs, points) using hashmaps
	 * From the hashmaps we can get the linked lists (since they are in that class
	 * So we just pass over B3_Hashmaps
	 */
	private B3_Hashmaps _HM;
	private B2_LinkedLists _linkedLists ;


	private BranchGroup _BG  ;
	
	private String test;
	
	//called before using this class
	public void set_HM(B2_LinkedLists linkedLists,B3_Hashmaps HM) {
		this._HM = HM;
		this._linkedLists = linkedLists;
	}
	
	public void set_BG() {
		//2//3//99System.out.print("set _BG");
		//BranchGroup B = new BranchGroup();
		_BG = new BranchGroup();
		test = " about to set _BG test string";

		//if(!_BG.isLive()){
			_BG.setCapability(BranchGroup.ALLOW_DETACH);
		//}
		
		// set the BranchGroups for all the nodes
		for(E1_TNodeNames tNodeNames : _linkedLists.get_NodeLinkedList()){
			
//			set the branchgroup for the node
			//TNode CurrentNode = _G.get_TNodes().get_TNode(tNodeNames);
			//2//3//99System.out.print("Current Node number =" + _HM.get_TNodesHashmap().get_TNode(tNodeNames).get_NodeID());
			E2_TNodesHashmaps get_TNodesHashmap = _HM.get_TNodesHashmap();
			get_TNodesHashmap.get_TNode(tNodeNames).set_BG();
			
			// add the node branchgroup to the Nodes branchgroup
			if(get_TNodesHashmap.get_TNode(tNodeNames).get_BG() != null){
				_BG.addChild(get_TNodesHashmap.get_TNode(tNodeNames).get_BG());
			}
		}
		
		//set the BranchGroups for all the arcs
		for(F1_TArcNames tArcNames : _linkedLists.get_ArcLinkedList()){
			// set the branchgroup for the arc
			//2//3//99System.out.print("Current Arc descriptor = " + _HM.get_TArcsHashmap().get_TArc(tArcNames).get_TrackSegments());
			F2_TArcsHashmaps get_TArcsHashmap = _HM.get_TArcsHashmap();
			get_TArcsHashmap.get_TArc(tArcNames).set_BG();
			
			// add the arc branchgroup to the Arcs branchgroup
			if(get_TArcsHashmap.get_TArc(tArcNames).get_BG() != null){
				//2//3//99System.out.print("65465467");
				_BG.addChild(get_TArcsHashmap.get_TArc(tArcNames).get_BG());
			}
		}
		
		//set the BranchGroups for all the points
//		for(I1_PointNames PointNames: _linkedLists.get_PointsLinkedList()){
//			// set the branchgroup for the point
//			//_BG.addChild(arg0)
//		}
		

		addModels();
		addGroundCover();
		

	}
	
	public void set_BG_train() {
		//set the branchGroups for all the engines
		boolean setEngineBranchgroup=true;
		if(setEngineBranchgroup){
//			H1_Engine_Routes h1EngineRoutes = _linkedLists.get_H1_EngineRoutes();
//			List<H2_Engine_Route> getEngineRoutes = h1EngineRoutes.get_engine_Routes();
//			for(H2_Engine_Route eRoute : getEngineRoutes ){
//				// ensure we have a method of getting the node parameters from the node name
//				// set the branchgroup for the engine
//				//1//2//3//99System.out.print("calling eRoute.set_BG();" + eRoute.getEngineRoute().toString());
//				//eRoute.set_BG_train();
//				_BG.addChild(eRoute.get_BG());
//			}
			
			M6_Trains_On_Routes trainsOnRoutes = _linkedLists.getTrainsOnRoute();
			trainsOnRoutes.set_BG(_BG);
			
			//M75Stops stops = _linkedLists.getStops();
			M75Stops.set_BG(_BG);
			
			N1_TimeBehavior behav = new N1_TimeBehavior(U4_Constants.timeInterval, trainsOnRoutes) ;
			behav.setSchedulingBounds(new BoundingSphere());
			this._BG.addChild(behav);
		}

	}
	
	  private void addGroundCover()
	  /* GroundShape expects a GIF filename, located in the images/
	     subdirectory and a scale factor. The resulting shape is
	     located at (0,0) on the (x, z) plain, and can be adjusted
	     with an additional TG.
	  */
	  {
	    Transform3D t3d = new Transform3D();

	    t3d.set( new Vector3d(0,0,4)); 
	    TransformGroup tg1 = new TransformGroup(t3d);
	    tg1.addChild( new GroundShape("tree1.gif", 3) );
	    _BG.addChild(tg1);
	    


//	    t3d.set( new Vector3d(0,0,-3));
//	    TransformGroup tg2 = new TransformGroup(t3d);
//	    tg2.addChild( new GroundShape("tree2.gif", 2) );
//	    _BG.addChild(tg2);
//
//	    t3d.set( new Vector3d(2,0,-6));
//	    TransformGroup tg3 = new TransformGroup(t3d);
//	    tg3.addChild( new GroundShape("tree4.gif", 3) );
//	    _BG.addChild(tg3);
//
//	    t3d.set( new Vector3d(-1,0,-4));
//	    TransformGroup tg4 = new TransformGroup(t3d);
//	    tg4.addChild( new GroundShape("cactus.gif") );
//	    _BG.addChild(tg4);
	  } 

	  private void addModels()
	  /* The ModelLoader getModel() method expects a OBJ filename, for
	     files in the Models/ subdirectory, and an optional y-axis 
	     translation. The method returns a TransformGroup which can
	     be used with other TGs to further position, rotate, or scale
	     the model.

	     List of models with their optional y-axis offsets:
	       penguin.obj, sword.obj 1, humanoid.obj 0.8
	       hand.obj 1, barbell.obj 0.25, longBox.obj, cone.obj 0.4
	       heli.obj 0, matCube.obj 0.5, cubeSphere.obj 0.9
	  */
	  {
	    ModelLoader ml = new ModelLoader();
	    Transform3D t3d = new Transform3D();

	    t3d.setIdentity();   // resets t3d  (just to be safe)
	    t3d.setTranslation( new Vector3d(-3,0,0));   // move
	    t3d.setScale(U4_Constants.enginelength*U4_Constants.scalefactor);   // enlarge
	    t3d.setRotation( new AxisAngle4d(1,0,0, Math.toRadians(90)) ); 
	    Transform3D temp = new Transform3D();
	    temp.setRotation( new AxisAngle4d(0,1,0, Math.toRadians(90)) );
	    t3d.mul(temp);
	              // rotate 90 degrees anticlockwise around y-axis
	    TransformGroup tg1 = new TransformGroup(t3d);
	    //tg1.setRotation( new AxisAngle4d(0,1,0, Math.toRadians(90)) );
	    TransformGroup temp1 = ml.getModel("Locomotive TGM3.obj", 0.8) ;
	    tg1.addChild( temp1);
	    Color brown = new Color(165, 42, 42);
	    Appearance brownAppearance = Apperance.getAppearance(brown);
//	    Scene t1 = getSceneFromFile("Some local path\torus.obj");
	    //_BG.addChild(tg1);

	    t3d.set( new Vector3d(0,0,10));   // move, and resets other parts of t3d
	    t3d.setScale(1.0);   // shrink
	    TransformGroup tg2 = new TransformGroup(t3d);
	    tg2.addChild( ml.getModel("penguin.obj") );
	    _BG.addChild(tg2);

	    t3d.set( new Vector3d(0,0,100)); 
	    TransformGroup tg3 = new TransformGroup(t3d);
	    tg3.addChild( ml.getModel("barbell.obj", 0.25) );
	    _BG.addChild(tg3);

	    t3d.set( new Vector3d(0,0,1000)); 
	    t3d.setScale( new Vector3d(4,1,0.25));  
	          // stretch along x-axis, shrink along z-axis
	    t3d.setRotation( new AxisAngle4d(1,0,0, Math.toRadians(-45)) ); 
	              // rotate 45 degrees anticlockwise around x-axis
	    TransformGroup tg4 = new TransformGroup(t3d);
	    tg4.addChild( ml.getModel("longBox.obj") );
	    _BG.addChild(tg4);

	    t3d.set( new Vector3d(0,0,-10));   // up into the air
	    TransformGroup tg5 = new TransformGroup(t3d);
	    tg5.addChild( ml.getModel("heli.obj") );
	    _BG.addChild(tg5);

	    t3d.set( new Vector3d(0,0,-100));   // up into the air
	    TransformGroup tg6 = new TransformGroup(t3d);
	    tg6.addChild( ml.getModel("colorCube.obj"));
	    _BG.addChild(tg6);

	  }  // end of addModels()

	public BranchGroup get_BG() {
		return _BG;
	}

	public B3_Hashmaps get_HM() {
		return _HM;
	}

	public B2_LinkedLists get_linkedLists() {
		return _linkedLists;
	}

	public String getTest() {
		return test;
	}

	public void set_HM(B3_Hashmaps _HM) {
		this._HM = _HM;
	}

	public void set_linkedLists(B2_LinkedLists _linkedLists) {
		this._linkedLists = _linkedLists;
	}

	public void set_BG(BranchGroup _BG) {
		this._BG = _BG;
	}






}
