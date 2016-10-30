package myscene;

import javax.media.j3d.AmbientLight;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.*;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import sm2.E1;

import net.Serial_IO;

import myscene.CheckerFloor;

import myscene.GroundShape;
import myscene.KeyBehavior;
import mytrack.C1_BranchGroup;
//import mytrack.C_MyDoAll;
import mytrack.F1_TArcNames;
import mytrack.F2_TArcsHashmaps;
import mytrack.F3_TArc;
import mytrack.I1_PointNames;
import myscene.ModelLoader;
import mytrack.U4_Constants;
//import mytrack.Track;
import mytrack.U1_TAppearance;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.List;

/**
 * @author bill
 *
 */
public class B_Mypanel extends JPanel
// Holds the 3D canvas where the loaded image is displayed
{
	/**
	 * Requires this branchgroup
	 */
	static private C1_BranchGroup _BG;


	private static final int PWIDTH = 1000;   // size of panel
	private static final int PHEIGHT = 400; 

	//private static final int BOUNDSIZE = 10000;  // larger than world
	private static final int BOUNDSIZE = 100000;  // larger than world

	// info used to position initial viewpoint
	private final static double Z_START = 43.0;

	//private static final String SKY_DIR = "C:/Users/user/EclipseWorkspace/java3dbill/skyBox/";
	private static final String SKY_DIR = U4_Constants.projectlocationUnix + "/skyBox/";
	//private static final String SKY_DIR = U4_Constants.projectlocationUnix + "C:/Users/bill/EclipseWorkspace/java3dbill/skyBox/";
	
	// location of sky textures
	//private final static double DY = 0.001;


	private SimpleUniverse su;
	private BranchGroup _sceneBG;

	private BoundingSphere bounds;

//	private Track track;

//	private C_MyDoAll _myDoAll;
	private BranchGroup _get_BG;
	private PickCanvas pickCanvas;

	final static String newline = "\n";

	private Serial_IO _serialIO;



	public B_Mypanel(C1_BranchGroup BG, Serial_IO serialIO) 
	{
		set_BG(BG);
		set_serialIO(serialIO);
		//1//2//3//99System.out.print(new java.io.File("").getAbsolutePath()); 
	    //1//2//3//99System.out.print(B_Mypanel.class.getClassLoader().getResource("").getPath());
		
	    setLayout( new BorderLayout() );
	    setOpaque( false );
	    setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

	    GraphicsConfiguration config =
						SimpleUniverse.getPreferredConfiguration();
	    Canvas3D canvas3D = new Canvas3D(config);
	    add("Center", canvas3D);
	    canvas3D.setFocusable(true);     // give focus to the canvas 
	    canvas3D.requestFocus();

	    su = new SimpleUniverse(canvas3D);

	    // depth-sort transparent objects on a per-geometry basis
	    View view = su.getViewer().getView();
	    view.setTransparencySortingPolicy(View.TRANSPARENCY_SORT_GEOMETRY);

	    createSceneGraph();
	    createUserControls();
	    
	    su.addBranchGraph( _sceneBG );
	    

	    C_PickRoutines _pickRoutines = new C_PickRoutines(canvas3D, _sceneBG, _BG);
	    
//	    pickCanvas = new PickCanvas(canvas3D, _sceneBG); 
//
//	    pickCanvas.setMode(PickCanvas.BOUNDS); 
//
//	    canvas3D.addMouseListener(this ); 
	  } 
	  

	  public void createSceneGraph()
	  // initilise the scene
	  { 
		 _sceneBG = new BranchGroup();
	    
	 // Set the Capabilities that is need by adding and removing
	    // the Cube
	    _sceneBG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
	    _sceneBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	    _sceneBG.setCapability(BranchGroup.ALLOW_DETACH);
	    //content.setCapability(BranchGroup.ALLOW_DETACH);
	    
	    
	    enablePicking(_sceneBG);
	    
	    bounds = new BoundingSphere(new Point3d(0,0,0), BOUNDSIZE);  

	    lightScene();         // add the lights

	    // three background methods: choose one 
	    addBackground("clouds.jpg"); // or "stars.jpg", "clouds.jpg"
	    // addSkyBox("clouds.jpg");      // skybox version 1
	    //addSceneBox(BOUNDSIZE/2);    // version 2

	    _sceneBG.addChild( new CheckerFloor().getBG() );  // add the floor
	    
	    addTrack();        // draw the track, given the jTree
	    
	    
	    //addRectangle();
	    
	    
	    //addGroundCover();

	    //_sceneBG.compile();   // fix the scene
	  } // end of createSceneGraph()
	  
	  public void addTrack()
	  // 
	  {
//		//_myDoAll = new C_MyDoAll();
//		//_myDoAll.set_jTree(myJTree);
//		 
//		//_myDoAll.produceArcandNodeLinkedLists();
//		//_myDoAll.SetUpLinkedListContainingEnginesAndRoutesFromTree();
		
		//_myDoAll.ProduceDJGraph();
		//2//3//99System.out.print("in addtrack " + _BG.getTest());
		//_BG.set_BG();
	    _get_BG = _BG.get_BG();
	    
	   //1//2//3//99System.out.print( "backgroundgroup: " + _BG.get_linkedLists().toString());

	    _get_BG.setCapability(BranchGroup.ALLOW_DETACH);
		_sceneBG.addChild( _get_BG);
	  }  
	  

	  public void enablePicking(Node node) { 

	      node.setPickable(true); 

	      node.setCapability(Node.ENABLE_PICK_REPORTING); 

	      try { 

	         Group group = (Group) node; 

	         for (Enumeration e = group.getAllChildren(); e.hasMoreElements();) { 

	            enablePicking((Node)e.nextElement()); 

	         } 

	      } 

	      catch(ClassCastException e) { 

	          // if not a group node, there are no children so ignore exception 

	      } 

	      try { 

	            Shape3D shape = (Shape3D) node; 

	            PickTool.setCapabilities(node, PickTool.INTERSECT_FULL); 

	            for (Enumeration e = shape.getAllGeometries(); e.hasMoreElements();) { 

	               Geometry g = (Geometry)e.nextElement(); 

	               g.setCapability(Geometry.ALLOW_INTERSECT); 

	            } 

	         } 

	      catch(ClassCastException e) { 

	         // not a Shape3D node ignore exception 

	      } 

	  } 



//	  public void mouseClicked(MouseEvent e) 
//
//	  { 
//
//	      pickCanvas.setShapeLocation(e); 
//
//	      PickResult result = pickCanvas.pickClosest(); 
//
//	      if (result == null) { 
//
//	       //99//99System.out.print("Nothing picked"); 
//
//	      } else { 
//	    	 //99//99System.out.print( "Closest PickResult: " + result );
//	    	   Node actualNode = result.getObject();
//	    	   
//	    	   if( actualNode.getUserData() != null )
//	    	   {
//	    		   Object a = null;
//	    	   //99//99System.out.print("Closest Object: " + actualNode.getUserData() );
//	    	   }
//
//
//	         Primitive p = (Primitive)result.getNode(PickResult.PRIMITIVE); 
//	         BranchGroup b = (BranchGroup)result.getNode(PickResult.BRANCH_GROUP); 
//
//	         Shape3D s = (Shape3D)result.getNode(PickResult.SHAPE3D); 
//	         
//	         //1//2//3//99System.out.print("SceneGraphPTH: " + result.getSceneGraphPath().toString() + newline);
//	         SceneGraphPath path = result.getSceneGraphPath();
//	         
//	         //1//2//3//99System.out.print("Number of nodes in path: " + path.nodeCount());
//	         int lastnode = path.nodeCount()-1;
//	        //1//2//3//99System.out.print("Nodename0: " + path.getNode(0).getName());
//	        //1//2//3//99System.out.print("Nodename1: " + path.getNode(1).getName());
//	        //1//2//3//99System.out.print("Nodename: " + path.getNode(lastnode).getName());
//	        //1//2//3//99System.out.print("Object: " + path.getObject());
//	         
//	         //
//	         chooseActionOnPick(path);
//	         
//	      } 
//	  } 
//
//
//
//
//	private void chooseActionOnPick(SceneGraphPath path) {
//		// TODO Auto-generated method stub
//		int lastnode = path.nodeCount()-1;
//		String lastnodename = path.getNode(lastnode).getName();
//		if (lastnodename != null){
//			
//			String[] words = lastnodename.split("_");
//			if (words[0].equals("track")){
//				// string is of the form track_2_B_LC
//				String node = words[1];
//				String nodeDirection = words[2];
//				String curve = words[3];
//				checkForPoint(node, nodeDirection, curve);
//			}
//			
//		}
//	}
//
//
//	private void checkForPoint(String node2, String nodeDirection2, String curve2) {
//		
//		List<I1_PointNames> pointsLinkedList = _BG.get_linkedLists().get_PointLinkedList();
//		for(I1_PointNames pointname : pointsLinkedList){
//			String nodeName = pointname.getNodeName();
//			String nodeDirection = pointname.getNodeDirection();
//			String curve = pointname.getCurve();
//			
//			if (nodeName.equals(node2) && nodeDirection.equals(nodeDirection2)){
//				String desiredState = curve2;
//				switchPoint(pointname,desiredState);
//			}
//			
//		}
//		
//	}
//
//
//	private void switchPoint(I1_PointNames pointname, String desiredState) {
//		
//		//99System.out.print("pointname:" + pointname.getNodeName() + " desiredState: " + desiredState);
//		String pointNodeDirection = pointname.getNodeDirection();
//		String curve = pointname.getCurve();
//		String pointNodeName = pointname.getNodeName();
//		
//		String offState;
//		if (desiredState.equals("ST")){
//			offState = curve;
//		} else {
//			offState = "ST";
//		}
//		//99System.out.print("desiredState" + desiredState);
//		// set the branchGroup variables so the desiredState branch goes green and the offState goes red
//		
//		//get the ArcName for the 
//		
//		List<F1_TArcNames> arcLinkedList = _BG.get_linkedLists().get_ArcLinkedList();
//		
//		for ( F1_TArcNames tArcName : arcLinkedList){
//			
//			
//			F2_TArcsHashmaps arcsHashmap = _BG.get_HM().get_TArcsHashmap();
//			F3_TArc tArc = arcsHashmap.get_TArc(tArcName);
//			
//
//			//turn on SERIAL 90 xxxx yyyy 
//			//xxxx is the node
//			//YYYY is the port
//			
//			//points are on node 0001
//			String prefix = "POINTN";
//			String node = pointname.getNodeNumber();
//			String pointno = pointname.getPointNumber();
//			//pointno = pointname.getPointNumber();
//			
//			String On = "91";
//			String Off = "90";
//			String messageOn = prefix + On + node + pointno;
//			String messageOff = prefix + Off + node + pointno;
//
//			if ( tArcName.getNodeFromName().equals(pointNodeName) &&
//					tArcName.getNodeFromDir().equals(pointNodeDirection)){
//				
//				String tracktype = tArc.getFirstSegment().get_TrackType();
//				if (tracktype.equals(offState)){
//					tArc.getFirstSegment().setpointcolor(Color.red);
//				} else {
//					tArc.getFirstSegment().setpointcolor(Color.green);
//					if (desiredState.equals("ST")){
//						E1.e_process_point_request(new String[]{prefix,On,node,pointno,"ON"});
//						//get_serialIO().writeSerial(messageOn);
//					} else {
//						//get_serialIO().writeSerial(messageOff);
//						E1.e_process_point_request(new String[]{prefix,Off,node,pointno,"OFF"});
//					}
//				}
//				
//			}
//			
//			
//			if ( tArcName.getNodeToName().equals(pointNodeName) &&
//					tArcName.getNodeToDir().equals(pointNodeDirection)){
//				
//				String tracktype = tArc.getLastSegment().get_TrackType();
//				if (tracktype.equals(offState)){
//					tArc.getLastSegment().setpointcolor(Color.red);
//				} else {
//					tArc.getLastSegment().setpointcolor(Color.green);
//				}
//				if (desiredState.equals("ST")){
//					//get_serialIO().writeSerial(messageOn);
//					E1.e_process_point_request(new String[]{prefix,On,node,pointno,"ON"});
//				} else {
//					//get_serialIO().writeSerial(messageOff);
//					E1.e_process_point_request(new String[]{prefix,Off,node,pointno,"OFF"});
//				}
//			}
//		}
//		
//	}
//

	private void addRectangle() {
		// TODO Auto-generated method stub
		
	}

	  private void addBackground(String fnm)
	  /* Create a spherical background. The texture for the 
	     sphere comes from fnm. */
	  {
	    Texture2D tex = loadTexture(SKY_DIR + fnm);

	    Sphere sphere = new Sphere(1000.0f,
				      Sphere.GENERATE_NORMALS_INWARD |
					  Sphere.GENERATE_TEXTURE_COORDS, 8);   // default = 15 (4, 8)
	    Appearance backApp = sphere.getAppearance();
		backApp.setTexture( tex );

		BranchGroup backBG = new BranchGroup();
	    backBG.addChild(sphere);

	    Background bg = new Background();
	    bg.setApplicationBounds(bounds);
	    bg.setGeometry(backBG);

	    _sceneBG.addChild(bg);
	  }  // end of addBackground()

	  private Texture2D loadTexture(String fn)
	  // load image from file fn as a texture
	  {
		  TextureLoader texLoader = new TextureLoader(fn, null);
		  Texture2D texture = (Texture2D) texLoader.getTexture();
		  Object a;
		  if (texture == null){
			  a=null;
			  //3//99System.out.print("Cannot load texture from " + fn);
		  }
		  else {
			  //3//99System.out.print("Loaded texture from " + fn);
			  texture.setEnable(true);
		  }
		  return texture;
	  }  // end of loadTexture()
	  
	
	  
	  public void removeTrack(){
		  _sceneBG.removeChild( _get_BG); 
	  }

	private void lightScene()
	  /* One ambient light, 2 directional lights */
	  {
	    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		
		    // Set up the ambient light
		AmbientLight ambientLightNode = new AmbientLight(white);
		ambientLightNode.setInfluencingBounds(bounds);
		_sceneBG.addChild(ambientLightNode);
		
		// Set up the directional lights
		Vector3f light1Direction  = new Vector3f(-1.0f, -1.0f, -1.0f);
		   // left, down, backwards 
		Vector3f light2Direction  = new Vector3f(1.0f, 1.0f, 1.0f);
		   // right, down, forwards
		
	    DirectionalLight light1 = 
	            new DirectionalLight(white, light1Direction);
	    light1.setInfluencingBounds(bounds);
	    _sceneBG.addChild(light1);
	
	    DirectionalLight light2 = 
	        new DirectionalLight(white, light2Direction);
	    light2.setInfluencingBounds(bounds);
	   // _sceneBG.addChild(light2);
	  }  // end of lightScene()


//	  private void addGroundCover()
//	  /* GroundShape expects a GIF filename, located in the images/
//	     subdirectory and a scale factor. The resulting shape is
//	     located at (0,0) on the (x, z) plain, and can be adjusted
//	     with an additional TG.
//	  */
//	  {
//	    Transform3D t3d = new Transform3D();
//
//	    t3d.set( new Vector3d(4,0,1)); 
//	    TransformGroup tg1 = new TransformGroup(t3d);
//	    tg1.addChild( new GroundShape("tree1.gif", 3) );
//	    _sceneBG.addChild(tg1);
//
//	    t3d.set( new Vector3d(3,0,1));
//	    TransformGroup tg2 = new TransformGroup(t3d);
//	    tg2.addChild( new GroundShape("tree2.gif", 2) );
//	    _sceneBG.addChild(tg2);
//
//	    t3d.set( new Vector3d(2,6,1));
//	    TransformGroup tg3 = new TransformGroup(t3d);
//	    tg3.addChild( new GroundShape("tree4.gif", 3) );
//	    _sceneBG.addChild(tg3);
//
//	    t3d.set( new Vector3d(1,4,1));
//	    TransformGroup tg4 = new TransformGroup(t3d);
//	    tg4.addChild( new GroundShape("cactus.gif") );
//	    _sceneBG.addChild(tg4);
//	  }  // end of addGroundCover()



	  private void createUserControls()
	  /*  Attach the keyboard behavior for moving around the scene,
	      and set the initial viewpoint.
	  */
	  { 
	    ViewingPlatform vp = su.getViewingPlatform();

	    // position viewpoint
	    TransformGroup targetTG = vp.getViewPlatformTransform();
	    Transform3D t3d = new Transform3D();
	    targetTG.getTransform(t3d);
	    t3d.setTranslation( new Vector3d(0,0,Z_START));
	    targetTG.setTransform(t3d);
	    t3d.setRotation( new AxisAngle4d( 0, 1, 0, Math.PI/2)) ;
	    //targetTG.setTransform(t3d);

	    // set up keyboard controls to move the viewpoint
	    KeyBehavior keyBeh = new KeyBehavior();
	    keyBeh.setSchedulingBounds(bounds);
	    vp.setViewPlatformBehavior(keyBeh);
	  } // end of createUserControls()



//	public void setTrack(Track track) {
//		this.track = track;
//	}
//
//
//
//	public Track getTrack() {
//		return track;
//	}



	public BranchGroup getSceneBG() {
		return _sceneBG;
	}

//	@Override
//    public void mouseEntered(MouseEvent e) {
//	       saySomething("Mouse entered", e);
//	    }
//
//	@Override
//    public void mouseExited(MouseEvent e) {
//	       saySomething("Mouse exited", e);
//	    }
//
//	@Override
//	  public void mousePressed(MouseEvent e) {
//	       saySomething("Mouse pressed (# of clicks: "
//	                    + e.getClickCount() + ")", e);
//	    }
//
//	@Override
//    public void mouseReleased(MouseEvent e) {
//	       saySomething("Mouse released (# of clicks: "
//	                    + e.getClickCount() + ")", e);
//	    }

//    public void mouseClicked(MouseEvent e) {
//	       saySomething("Mouse clicked (# of clicks: "
//	                    + e.getClickCount() + ")", e);
//	    }








//	    void saySomething(String eventDescription, MouseEvent e) {
////	        textArea.append(eventDescription + " detected on "
////	                        + e.getComponent().getClass().getName()
////	                        + "." + newline);
////	        textArea.setCaretPosition(textArea.getDocument().getLength());
////	    	//1//2//3//99System.out.print(eventDescription + " detected on "
////                    + e.getComponent().getClass().getName()
////                    + "./n" + newline);
//	    }




		public void set_BG(C1_BranchGroup _BG) {
			this._BG = _BG;
		}


		public void set_serialIO(Serial_IO _serialIO) {
			this._serialIO = _serialIO;
		}


		public Serial_IO get_serialIO() {
			return _serialIO;
		}



}
