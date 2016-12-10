package mytrack;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.media.j3d.*;
import javax.vecmath.*;

import Utilities.Util;

import com.ajexperience.utils.DeepCopyException;
import com.ajexperience.utils.DeepCopyUtil;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Text2D;


/**
 * @author bill
 *
 */
public class E3_TNode {
	 /// <summary>
    /// Nodes should be created through 
    /// </summary>

    
    private E1_TNodeNames _NodeName = new E1_TNodeNames();

    /**
	 * @return the _NodeName
	 */
	public E1_TNodeNames get_NodeName() {
		return _NodeName;
	}


	private Vector3d _Tangent = new Vector3d();

    private Point3d _Position = new Point3d();
    
    private BranchGroup _BG = new BranchGroup();

	private boolean _drawStation;

	private boolean _drawCrossing;

	private boolean _drawNodeMarker;

	//private TransformGroup _TG = new TransformGroup();;
    

    public E3_TNode(E1_TNodeNames tNodeName)
    {
        _NodeName = tNodeName;
    } 
    
 
    E3_TNode(E1_TNodeNames NodeNum, Point3d Position, Vector3d Tangent)  
    {
        _NodeName = NodeNum;
        _Position = Position;
        _Tangent = Tangent;
    }
    public boolean Drawn()
    {
    	Vector3d Zero = new Vector3d(0,0,0);
    	Point3d ZeroP = new Point3d(0,0,0);
    	
        if (_Position.equals(ZeroP) || _Tangent.equals(Zero) )
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     *
     */
    void set_BG(){
    	
		if(_NodeName.getStation().equals("T")){
			_drawStation = true;
		}
		else{
			_drawStation = false;
		}
		if(_NodeName.getCrossing().equals("T")){
			_drawCrossing = true;
		}
		else{
			_drawCrossing = false;
		}
		if(_NodeName.getDisplayNode().equals("T")){
			_drawNodeMarker = true;
		}
		else{
			_drawNodeMarker = false;
		}
		
//		if(_NodeName.getCrossing().equals("T")){
//			setCrossingTG();
//			//node_position_TG.addChild(get_Crossing_TG());
//		}
//		
//		if(_NodeName.getDisplayNode().equals("T")){
//			setDisplayNodeTG();
//			//node_position_TG.addChild(get_Crossing_TG());
//		}
		// the field elements are accessible in node_position_TG, and hence can display selectively what is required
		TransformGroup node_position_TG = get_node_position_TG();
		this._BG.addChild(node_position_TG);
		
    }
    



	private TransformGroup get_node_position_TG() {
		
		Point3d Pos= new Point3d();
		Pos = getPosition();
		Vector3d P = new Vector3d(Pos);
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(P); //modified from set
		//create the branchgroup for the node
		TransformGroup translateToNode1 = new TransformGroup(t3d);
		translateToNode1.addChild(rotateToTangent_TG());
		return translateToNode1;
	}

	private TransformGroup rotateToTangent_TG() {
		// the station is initially pointing along the x axis
		//rotate by the angle between the tangent and the x axis

		Vector3d tangent = new Vector3d(getTangent());
		double angle = Util.anglebetween(tangent,new Vector3d(1,0,0));
		Transform3D t3d = new Transform3D();
		t3d.rotZ(-1.0f*angle);
		TransformGroup rotateToTangent = new TransformGroup(t3d);
		if (_drawNodeMarker){
			rotateToTangent.addChild(nodeMarker_TG());
		}
		
		if(_drawStation){
			rotateToTangent.addChild(rotateBy90_TG());
		}
		return rotateToTangent;
	}
	

	private Node nodeMarker_TG() {
		Transform3D t3d = new Transform3D();
		t3d.rotY(0);
		TransformGroup rotateBy90 = new TransformGroup(t3d);
		U1_TAppearance A = new U1_TAppearance(Color.black);
		Appearance app = A.get_Appearance();
		float boxHeight=1.0f;
		float boxWidth=.50f;
		float boxLen=.50f;
		com.sun.j3d.utils.geometry.Box nodeMarker= 
			new com.sun.j3d.utils.geometry.Box(boxWidth/2, boxLen/2, boxHeight/2, 
					Primitive.GENERATE_NORMALS |
					Primitive.GENERATE_TEXTURE_COORDS, app);
		rotateBy90.addChild(nodeMarker);
		
		TransformGroup rotateBy90Text = new TransformGroup(t3d);
		Vector3d pt = new Vector3d(0.25,-0.5,5);
		String NodeNo = _NodeName.getNodeName();
		rotateBy90Text.addChild(makeText(pt,""+NodeNo));

		rotateBy90.addChild(rotateBy90Text  );

		return rotateBy90;
	}




	private TransformGroup rotateBy90_TG() {
		//rotate about the z axis to point at right angles to tangent
		Transform3D t3d = new Transform3D();
		t3d.rotZ(Math.PI/2);
		TransformGroup rotateBy90 = new TransformGroup(t3d);
		rotateBy90.addChild(get_station_TG());
		//rotateBy90.addChild(stationBase);
		

		return rotateBy90;
	}

	public TransformGroup get_station_TG() {
		
		// go at 90 deg to tangent
		Vector3d tan = new Vector3d(1.0f,1.0f,0.0f);
		Transform3D t3d1 = new Transform3D();
		// draw station
		float boxHeight=.40f;
		float boxWidth=.50f;
		float boxLen=2.50f;
		U1_TAppearance A = new U1_TAppearance(Color.black);
		Appearance app = A.get_Appearance();
		com.sun.j3d.utils.geometry.Box stationBase = 
			new com.sun.j3d.utils.geometry.Box(boxWidth/2, boxLen/2, boxHeight/2, 
					Primitive.GENERATE_NORMALS |
					Primitive.GENERATE_TEXTURE_COORDS, app);
		//translate by box height vertically

		t3d1.setTranslation( new Vector3f(0, 0,boxHeight/2 ));
		TransformGroup middleOfBase = new TransformGroup(t3d1);
		middleOfBase.addChild(stationBase);
		
		
		// go up by boxheight/2
		// draw station
		float boxHeight1=.40f;
		float boxWidth1=.50f;
		float boxLen1=2.50f;
		 A = new U1_TAppearance(Color.red);
		 app = A.get_Appearance();
		com.sun.j3d.utils.geometry.Box stationTop = 
			new com.sun.j3d.utils.geometry.Box(boxWidth1/2, boxLen1/2, boxHeight1/2, 
					Primitive.GENERATE_NORMALS |
					Primitive.GENERATE_TEXTURE_COORDS, app);
		//translate by box height vertically

		t3d1.setTranslation( new Vector3f(0, 0,boxHeight +boxHeight1/2 ));
		TransformGroup middleOfTop = new TransformGroup(t3d1);
		middleOfTop.addChild(stationTop);

		Transform3D t3d = new Transform3D();
		t3d.rotZ(-Math.PI/2);
		TransformGroup rotateBy90Text = new TransformGroup(t3d);
		Vector3d pt = new Vector3d(0,-.3,boxHeight1/2);
		String NodeNo = _NodeName.getNodeName();
		rotateBy90Text.addChild(makeText(pt,""+NodeNo));

		middleOfTop.addChild(rotateBy90Text  );
		
		//translate by offset from node
		Vector3d V1 = new Vector3d(1.0f,0.0f,0.0f);
		t3d1 = new Transform3D();;
		t3d1.setTranslation(V1); //modified from set
		//create the branchgroup for the node
		TransformGroup get_station_TG = new TransformGroup(t3d1);
		//get_station_TG.addChild(rotateBy90X);
		get_station_TG.addChild(middleOfBase);
		get_station_TG.addChild(middleOfTop);
		
		return get_station_TG;
	}
	private TransformGroup AddTextrotateBy90_TG() {
		//rotate about the z axis to point at right angles to tangent
		Transform3D t3d = new Transform3D();
		t3d.rotZ(Math.PI/2);
		TransformGroup rotateBy90 = new TransformGroup(t3d);
		rotateBy90.addChild(get_station_TG());
		//rotateBy90.addChild(stationBase);
		return rotateBy90;
	}
	  private TransformGroup makeText(Vector3d vertex, String text)
	  // Create a Text2D object at the specified vertex
	  {
		final Color3f white = new Color3f(Color.white);
		Text2D message = new Text2D(text, white , "SansSerif", 130, Font.BOLD );
	       // 36 point bold Sans Serif

	    TransformGroup tg = new TransformGroup();
	    Transform3D t3d = new Transform3D();
	    t3d.setTranslation(vertex);
	    tg.setTransform(t3d);
	    tg.addChild(message);
	    return tg;
	  } // end of getTG()

	private void setCrossingTG() {
		// TODO Auto-generated method stub
		
	}
	
	private void setDisplayNodeTG() {
		// TODO Auto-generated method stub
		
	}


	private TransformGroup calc_station_TG() {
		
		//transform the station to where we want it
		
		// move it 1 square away from the track
    	Vector3d V = new Vector3d();
    	V.cross(this._Tangent, new Vector3d(0,0,1));
    	V.normalize();
    	V.scale(2);
    	// set the  transform group at the node position
    	Transform3D t3d = new Transform3D();
    	t3d.set(V);
    	//create the branchgroup for the node
    	TransformGroup position_tg = new TransformGroup(t3d);
		
		position_tg.addChild(get_station_TG());
		
		
		
		return position_tg;
		
	}


	private Node get_Crossing_TG() {
		// TODO Auto-generated method stub
		return null;
	}




	
	


	
	private TransformGroup get_box( float boxWidth, float boxHeight, float boxLen, Appearance app){

//		// limb dimensions
//		float boxWidth = 0.24f;
//		float boxHeight = 2.0f;
//		float boxLen = 4.0f;

		Transform3D t3d = new Transform3D();
		
		/* position baseTG at the base limb's midpoint, so the limb
	       will rest on the floor */
		t3d.rotY(Math.PI/2);
	    t3d.setTranslation( new Vector3f(0, 0, boxHeight/2));
	    TransformGroup baseTG = new TransformGroup(t3d);

	    // make base limb
	    com.sun.j3d.utils.geometry.Box baseLimb = 
	        new com.sun.j3d.utils.geometry.Box(boxWidth/2, boxLen/2, boxHeight/2, 
	                       Primitive.GENERATE_NORMALS |
	                       Primitive.GENERATE_TEXTURE_COORDS, app);
	    baseTG.addChild(baseLimb);
	    
	    return baseTG;

//	    // move to top of base limb
//	    t3d.set( new Vector3f(0, boxHeight/2, 0)); 
//	    TransformGroup baseTopTG = new TransformGroup(t3d);
//	    baseTG.addChild(baseTopTG);
	}


	/**
     * @return
     * getters and setters
     */
    public E1_TNodeNames get_NodeID()
    {
        return _NodeName;
    }
    public void setNodeID(E1_TNodeNames value)
    {
        _NodeName = value; 
    }
    
    public Vector3d getTangent()
    {
        return _Tangent;
    }
    
    
    public void setTangent(Vector3d value)
    {
        _Tangent = value;
    }
    public void setPosition(Point3d value)
    {
	    _Position = value;
    }   


    
    public Point3d getPosition(){
 	    return _Position;
	}
    
    public Point3d getPositionCopy(){
    	
    	try {
    		DeepCopyUtil deepCopyUtil = new DeepCopyUtil();
			return deepCopyUtil.deepCopy(_Position);
		} catch (DeepCopyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hopefully never used
		return _Position;
    }
    
    public Vector3d getTangentCopy(){
    	
    	try {
    		DeepCopyUtil deepCopyUtil = new DeepCopyUtil();
			return deepCopyUtil.deepCopy(_Tangent);
		} catch (DeepCopyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hopefully never used
		return _Tangent;
    }


	public BranchGroup get_BG() {
		return _BG;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TNode [_NodeName=");
		builder.append(_NodeName);
		builder.append(", _Tangent=");
		builder.append(_Tangent);
		builder.append(", _Position=");
		builder.append(_Position);
//		builder.append(", _BG=");
//		builder.append(_BG);
		builder.append("]");
		return builder.toString();
	}

}

