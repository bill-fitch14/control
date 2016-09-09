package mytrack;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;


import Utilities.StringHelper;

import com.ajexperience.utils.DeepCopyException;
import com.ajexperience.utils.DeepCopyUtil;
import com.sun.j3d.utils.geometry.Primitive;


public class G_TSegment {
	
//	private static final float scalemultiplier = U4_Constants.scalemultiplier;
	Tuple3d position = new Point3d ( );    	//to display
    Vector3d tangent = new Vector3d ( );	//to display
	
    String _TrackType;
    Vector3d _tangent1 = new Vector3d ( );
    Tuple3d _position1 = new Point3d ( );
    Vector3d _tangent2 = new Vector3d ( );
    Tuple3d _position2 = new Point3d ( );
	private float _length;
	private BranchGroup _BG = new BranchGroup();
//	private float radius = 8.98f;
	private float radius = 914.0f;
	private float angleDivisorDouble = 4.0f;
	private float angleDivisorFull = 8.0f;
	private float angleDivisorLarge = 18.0f;
	//private float botchfactor = 1.6f;
	private float botchfactor = 1.0f;
	private float angleDivisorHalf = 16.0f*botchfactor;
	private String segmentName;
	private U1_TAppearance appearanceStraight;
	private Color pointcolor = Color.black;
	
	private boolean _drawStop = true ;
	

	
	
	
    public G_TSegment ( Point3d Position1,Vector3d Tangent1,Point3d Position2,Vector3d Tangent2, float Length)
    {
        //use this constuctor when joining up from end segment to end node
        _tangent1 = Tangent1;
        _position1 = Position1;
        _tangent2 = Tangent2;
        _position2 = Position2;
        _length = Length;
        _TrackType = "Join";

    }
    
	public void setPositionAndTangentForDisplayAsStraightLine (float segmentFraction) {
//		//99System.out.print("segmentfraction =" + segmentFraction);
		// The object that we are plotting is either an engine or a truck
		// if i = 0 then we have the engine, which at the moment we distinguish
		// by a colour

		// just for now the tangent will be along the straight line joining the
		// start and end of the arcs
		Vector3d tangent1 = this.getTangent1();
		Vector3d tangent2 = this.getTangent2();
		
		tangent.add(tangent1, tangent2);
 
		Point3d position1 = this.getposition1();
		Point3d position2 = this.getposition2();
		position.set(0, 0, 0);
		// position.add(position1,position2);
		// position.scale(0.5);
		position.sub(position2, position1);
		position.scale(segmentFraction);
		position.add(position1);

//		//99System.out.print("position truck " + truckNo + " = " + position.x
//				+ " segmentFraction =" + segmentFraction);
		
//		this.truckNo = truckNo;
//		this.truckType = truckType;

	}
    
    /**
     * Called in middle of arc.
     * SegmentName is created to identify segment when picking
     * @param trackIdentifier
     * @param tangent
     * @param position
     * @param nodeFrom
     * @param nodeFromDirection
     * @param nodeTo
     * @param nodeToDirection
     * @param segmentNo
     * @param firstLastOrMiddle 
     * @param containsStop 
     * 
     * 
     */
    public G_TSegment ( String trackIdentifier,Vector3d tangent,Point3d position, 
    		E3_TNode nodeFrom, int nodeFromDirection, 
    		E3_TNode nodeTo, int nodeToDirection, int segmentNo, String firstLastOrMiddle, boolean containsStop) 
    {
    	_drawStop = containsStop;
    	String directionFrom = (nodeFromDirection == 1?"F":"B");
    	String directionTo = (nodeToDirection == 1?"F":"B");

    	// segmentName word[0] is of the form 1_F_2_B-1
    	// there is a - before the segmentno so that the segmentno can be split off easily
    	
    	segmentName = 
    		nodeFrom.get_NodeID().getNodeName() + "_" + directionFrom + "_" + 
    		nodeTo.get_NodeID().getNodeName() + "_" + directionTo + "-" + (segmentNo);
    	//add second word for point switching if at the beginning or end of arc
    	
    	// segmentNme word[1] is of the form 1_F_RC
    	if (firstLastOrMiddle.equals("first")){
    		segmentName = segmentName + ":" + 
    		nodeFrom.get_NodeID().getNodeName() + "_" + directionFrom + "_" + trackIdentifier;
        	process ( trackIdentifier,tangent,position);
    	} else if (firstLastOrMiddle.equals("last")){
    		segmentName = segmentName + ":" + 
    		nodeTo.get_NodeID().getNodeName() + "_" + directionTo + "_" + trackIdentifier;
        	process ( trackIdentifier,tangent,position);
    	}
    	
    	process ( trackIdentifier,tangent,position); 

    }

//    public G_TSegment(String trackIdentifier, Vector3d tangent,	Point3d position, 
//    		E3_TNode node, int nodeDirection,
//    		String string) {
//
//    	String direction;
//    	if (nodeDirection == 1) {
//    		direction = "F";
//    	} else {
//    		direction = "B";
//    	}
//
//    	segmentName = node.get_NodeID().getNodeName() + "_" + direction + "_" + trackIdentifier;
//    	process ( trackIdentifier,tangent,position);
//    }

    	
    private void  process ( String TrackType1,Vector3d tangent,Point3d position) 
    {
    	try {
    		DeepCopyUtil deepCopyUtil = new DeepCopyUtil();
			Vector3d tangentcopy = deepCopyUtil.deepCopy(tangent);
			Point3d positioncopy = deepCopyUtil.deepCopy(position);
	        //ItemsInSegment();
	        SetElements ( TrackType1,tangentcopy,positioncopy );
		} catch (DeepCopyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
    }


	public void setTangentAndPos1 ( Vector3d tangent,Point3d position )
    {

        _tangent1 = tangent;
        _position1 = position;
    }
    
    /**
     * @param distance  distance from start of segment
     * @param position	resulting position
     * @param tangent	resulting tangent
     * @throws Exception
     */
    public void getPositionAndTangentAlongSegment ( float distance, Point3d position, Vector3d tangent) throws Exception
    {
    	Vector3d tangent0 = new Vector3d(0,0,0);
        Tuple3d position0 = new Point3d(0,0,0);
        DeepCopyUtil deepCopyUtil = new DeepCopyUtil(); // create deep copies of objects. 
    	Vector3d t = new Vector3d();
    	t = deepCopyUtil.deepCopy(_tangent1);
    	t.normalize();
    	Tuple3d p = new Point3d();
    	p = deepCopyUtil.deepCopy(_position1);
//        //check on type of element
//        try {
//			if (StringHelper.CountWords ( _TrackType ) > 1)
//			{
//			    String Word2 = StringHelper.GetLastWord ( _TrackType );
//			    String Word1 = StringHelper.RemoveLastWord ( _TrackType );
//			    if (Word2 == "STF")
//			    {
//			        //Matrix translationMatrix = Matrix.CreateTranslation ( _tangent1 * distance );
//			        //position0 = Vector3d.Transform ( _position1,translationMatrix );
//
//			    	t.scale(distance);
//			    	
//			        Transform3D translationMatrix1=new Transform3D();
//			        translationMatrix1.set(t0);
//			        translationMatrix1.transform(_position1,position0);
//			    }
//
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        if (_TrackType.startsWith("ST"))
        {
            //Matrix translationMatrix = Matrix.CreateTranslation ( _tangent1 * distance );
            //position0 = Vector3d.Transform ( _position1,translationMatrix );
//            tangent0 = _tangent1;
//        	Vector3d t0 = _tangent1;
        	
        	t.scale(distance);
        	
            Transform3D translationMatrix1=new Transform3D();
            translationMatrix1.set(t);
            translationMatrix1.transform((Point3d) p,(Point3d)position0);
            
            t.normalize();
            tangent0 = t;
        }



        if (_TrackType.startsWith( "LC") || _TrackType.startsWith( "RC") )
        {
            //float radius = 20.0f;
            //float length = Math.abs ( 2.0f * radius * (float)Math.sin ( angle / 2.0f ) );
        	/*
        	            Matrix rotationMatrix = Matrix.CreateRotationZ ( angle );
        	            //tangent0 = Vector3d.Normalize ( Math.Sign ( angle ) * Vector3d.Transform ( _tangent1,rotationMatrix ) );
        	            tangent0 = Vector3d.Normalize ( Vector3d.Transform ( _tangent1,rotationMatrix ) );

        	            Matrix rotMat = Matrix.CreateRotationZ ( angle / 2.0f );
        	            Vector3d dir = Vector3d.Transform ( _tangent1,rotMat );
        	            Vector3d nDir = Vector3d.Normalize ( dir );
        	            Matrix translationMatrix = Matrix.CreateTranslation ( nDir * length );
        	            position0 = Vector3d.Transform ( _position1,translationMatrix );
        	*/            
        	            
        	            //rotate the tangent, normalize, then translate by length
        	            //_tangent2 = Vector3d.Normalize ( Math.Sign ( angle ) * Vector3d.Transform ( tangent1,rotationMatrix ) );
        	float angle = 0;
        	if (_TrackType.startsWith("LC2")){
        		angle = +(float)Math.PI / angleDivisorDouble;
        	}   	
        	else if (_TrackType.startsWith("LCH")){
        		angle = +(float)Math.PI / angleDivisorHalf;
        	} 
        	else if (_TrackType.startsWith("LC")){
        		angle = +(float)Math.PI / angleDivisorLarge;
        	}
        	else if (_TrackType.startsWith("LCL")){
        		angle = +(float)Math.PI / angleDivisorLarge;
        	}
        	else if (_TrackType.startsWith("RC2")){
        		angle = -(float)Math.PI / angleDivisorDouble;
        	}
        	else if (_TrackType.startsWith("RCH")){
        		angle = -(float)Math.PI / angleDivisorHalf;
        	}
        	else if (_TrackType.startsWith("RC")){
        		angle = -(float)Math.PI / angleDivisorLarge;
        	}
        	else if (_TrackType.startsWith("RCL")){
        		angle = -(float)Math.PI / angleDivisorLarge;
        	}
            float arclength = radius * angle;

            angle = angle * distance / arclength;
            
            float length = Math.abs ( 2.0f * radius * (float)Math.sin ( angle / 2.0f ) );
           
            Transform3D rotationMatrix1=new Transform3D();
            rotationMatrix1.rotZ(angle);
            rotationMatrix1.transform(t);
            //_tangent2.scale(Math.signum(angle));
            
            tangent0 = t;
                       
            Vector3d t1 = new Vector3d();
        	t1 = deepCopyUtil.deepCopy(_tangent1);
        	t1.normalize();            
            Transform3D rotationMatrix2=new Transform3D();
            rotationMatrix2.rotZ(angle/2.0f);
            rotationMatrix2.transform(t1);
            
            Transform3D translationMatrix2 = new Transform3D();
            t1.scale(length);
            translationMatrix2.set(t1);
            translationMatrix2.transform((Point3d)p);
            
            position0 = p;
        }

//        if (_TrackType == "RC" || _TrackType == "RCS")
//        {
//            //float radius = 20.0f;
//            float angle = -(float)Math.PI / angleDivisorFull;
//            float arclength = radius * angle;
//
//            angle = angle * Math.abs(distance / arclength);
//            
//            float length = Math.abs ( 2.0f * radius * (float)Math.sin ( angle / 2.0f ) );
//
//            Transform3D rotationMatrix1=new Transform3D();
//            rotationMatrix1.rotZ(angle);
//            rotationMatrix1.transform(t);
//            //_tangent2.scale(Math.signum(angle));
//            tangent0 = t;
//            
//            Vector3d t1 = new Vector3d();
//        	t1 = deepCopyUtil.deepCopy(_tangent1);
//        	t1.normalize(); 
//            
//            Transform3D rotationMatrix2=new Transform3D();
//            rotationMatrix2.rotZ(angle/2.0f);
//            rotationMatrix2.transform(t1);
//            
//            Transform3D translationMatrix2 = new Transform3D();
//            t1.scale(length);
//            translationMatrix2.set(t1);
//            translationMatrix2.transform(p); 
//            
//            position0 = p;
//            /*
//            Matrix rotationMatrix = Matrix.CreateRotationZ ( angle );
//            //tangent0 = Vector3d.Normalize ( Math.Sign ( angle ) * Vector3d.Transform ( _tangent1,rotationMatrix ) );
//            tangent0 = Vector3d.Normalize ( Vector3d.Transform ( _tangent1,rotationMatrix ) );
//
//            Matrix rotMat = Matrix.CreateRotationZ ( angle / 2.0f );
//            Vector3d dir = Vector3d.Transform ( _tangent1,rotMat );
//            Vector3d nDir = Vector3d.Normalize ( dir );
//            Matrix translationMatrix = Matrix.CreateTranslation ( nDir * length );
//            position0 = Vector3d.Transform ( _position1,translationMatrix );
//            */
//            //rotate the tangent, normalize, then translate by length
//            //_tangent2 = Vector3d.Normalize ( Math.Sign ( angle ) * Vector3d.Transform ( tangent1,rotationMatrix ) );
//        }

//        if (_TrackType == "Join")
//        {
//        	/*
//            Matrix translationMatrix = Matrix.CreateTranslation ( _tangent1 * distance );
//            position0 = Vector3d.Transform ( _position1,translationMatrix );
//            tangent0 = _tangent1;
//            */
//        	Vector3d t0 = _tangent1;
//        	t0.scale(distance);
//        	
//            tangent0 = _tangent1;
//
//            Transform3D translationMatrix1=new Transform3D();
//            translationMatrix1.set(t0);
//            translationMatrix1.transform(_position1,position0);
//        }

        tangent = tangent0;
        position = (Point3d) position0;
    }
    
    public double anglebetween( Vector3d vector1, Vector3d vector2){
//    	// get absolute angle between Y projected and Up 
//    	double absAngle = vector1.angle(vector2);  
//    	// magic formula 
//    	Vector3d cross = new Vector3d();
//    	cross.cross(vector1, vector2); 
//    	double dot = DotProduct(lookDirection, cross);  
//    	// set actual signed angle 
//    	return (dot >= 0) ?  absAngle : -absAngle;
    	return (float)Math.atan2(vector2.y, vector2.x) - (float)Math.atan2(vector1.y, vector1.x);
    }
//    public void setTangentAndPos2_Curve2 ( Vector3d tangent1,Point3d position1,float angle,float radius ) throws DeepCopyException
//    {
//    	float length = Math.abs ( 2.0f * radius * (float)Math.sin ( angle / 2.0f ) );
//    	//1//2//3//99System.out.print("*************************************");
//    	//1//2//3//99System.out.print("length = " + length + " ,radius = " + radius);
//    	DeepCopyUtil deepCopyUtil = new DeepCopyUtil(); // create deep copies of objects. 
//    	Vector3d t = new Vector3d();
//    	t = deepCopyUtil.deepCopy(tangent1);
//    	t.normalize();
//    	//1//2//3//99System.out.print("tangent =" + t);
//    	//t.scale(length);
//    	//vector orthogonal to t is formed by cross product of zaxis and t
//    	Vector3d zaxis = new Vector3d(0,0,1);
//    	Vector3d rVec = new Vector3d();
//    	rVec.cross(zaxis, t);
//    	rVec.normalize();
//    	//1//2//3//99System.out.print("lvec norm =" + rVec);
//    	rVec.scale(radius);
//    	//1//2//3//99System.out.print("lvec =" + rVec);
//    	//transform the position
//    	_position2 = deepCopyUtil.deepCopy(position1);
//    	Point3d p = new Point3d();
//    	p = deepCopyUtil.deepCopy(position1);
//    	
//        Matrix4d identity = new Matrix4d();
//        identity.setIdentity();
//        //1//2//3//99System.out.print("identity\n"+identity);
//        
//    	Transform3D translationMatrix = new Transform3D();
//    	Vector3d p1 = new Vector3d(p);
//    	Vector3d MyVec = new Vector3d();
//
//        
//        //set tranlation and rotation matrices
////        Transform3D trans_transform = new Transform3D();
////        Transform3D rot_transform = new Transform3D();
//		Transform3D rotationMatrix = new Transform3D();
//
//		
//		//set transformation
////        trans_transform.set(identity);
////        rot_transform.set(identity);
//		
//		MyVec.sub(rVec,p1);
//		//1//2//3//99System.out.print("p1(position1)  = "+p1);
//		//1//2//3//99System.out.print("MyVec  = "+MyVec);
//		//1//2//3//99System.out.print("lvec =" + rVec);
//        translationMatrix.setTranslation(MyVec);
//        //1//2//3//99System.out.print ("translationMatrix\n" + translationMatrix);
//        
//    	//1//2//3//99System.out.print("p2(position1)  = "+_position2.toString()+":"+_position2.distance(new Point3d(0,0,0)));
//    	translationMatrix.transform(_position2);
//        //1//2//3//99System.out.print("p2 after translation  = "+_position2.toString()+":"+_position2.distance(new Point3d(0,0,0)));
//        
//        rotationMatrix.rotZ(angle);
//        //1//2//3//99System.out.print("rotationMatrix\n" +rotationMatrix);
//        rotationMatrix.transform(_position2);
//        //1//2//3//99System.out.print("p2 after rotation  = "+_position2.toString()+":"+_position2.distance(new Point3d(0,0,0)));
//        
//        Vector3d MyVec1 = new Vector3d();
//		MyVec1.sub(p1,rVec);
//    	translationMatrix.setTranslation(MyVec1);
//    	//1//2//3//99System.out.print ("translationMatrix\n" + translationMatrix);
//     	
//    	translationMatrix.transform(_position2);
//        //1//2//3//99System.out.print("_position2 after transform  = "+_position2.toString()+":"+_position2.distance(new Point3d(0,0,0)));
//
//        double dist = _position1.distance(_position2);
//    	
//    	//1//2//3//99System.out.print("dist = "+dist);
////        
////        transformation.set(identity);
////        transformation.mul(translationMatrix);
////    	//transformation.mul(translationMatrix);
////        rotationMatrix.rotZ(angle/2.0f);
////    	//transformation.mul(rotationMatrix);
////        MyVec.sub(p1,lvec);
////    	translationMatrix.setTranslation(MyVec);
////    	;
////    	
////    	transformation.transform(_position2);
//    	
//    	//transform the tangent
//    	_tangent2 = deepCopyUtil.deepCopy(tangent1);
//
//    	rotationMatrix.transform(_tangent2);
//    }
    
    public void setTangentAndPos2_Curve ( Vector3d tangent1,Point3d position1,float angle,float radius ) throws DeepCopyException
    {
    	float lengthOfSide = Math.abs ( 2.0f * radius * (float)Math.sin ( angle / 2.0f ) );

    	DeepCopyUtil deepCopyUtil = new DeepCopyUtil(); // create deep copies of objects.
    	_position2 = deepCopyUtil.deepCopy(position1);
        
    	//get a vector t pointing along tangent of length lengthOfSide

		Transform3D rotationMatrix=new Transform3D();
		rotationMatrix.rotZ(angle/2.0f);

    	 
    	Vector3d t = new Vector3d();
        t = deepCopyUtil.deepCopy(tangent1);
    	t.normalize();
    	t.scale(lengthOfSide);
    	
    	//align t so that it is pointing along the side of the polygon
    	rotationMatrix.transform(t);
    	
    	Transform3D translationMatrix = new Transform3D();
    	
        translationMatrix.setTranslation(t);
        
        //transform the position2 along the side of the polygon
		translationMatrix.transform((Point3d)_position2);
        
        _tangent2 = deepCopyUtil.deepCopy(tangent1);

    	//transformation.mul(rotationMatrix); 
		rotationMatrix.transform(_tangent2);
		rotationMatrix.transform(_tangent2);
        //              pi/8
        //           *                
        //      pi/16      *
        //        *len           *
        //                             *   
        //                          pi/8     * 
        //     ************************************
    }

   
    

    public void SetElements ( String TrackType1,Vector3d tangent,Point3d position ) throws DeepCopyException  
    {
    	float scalefactor = U4_Constants.scalefactor;
    	float loc_radius = (float) (scalefactor*radius);
    	
        _TrackType = TrackType1;
        //3//99System.out.print("_TrackType: " + TrackType1);
        try {
			if (StringHelper.CountWords(TrackType1) > 1)
			{
			    String Word2 = null;
				try {
					Word2 = StringHelper.GetLastWord(TrackType1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    String Word1 = null;
				try {
					Word1 = StringHelper.RemoveLastWord(TrackType1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//
//			    if (Word2.equals("RCF"))
//			    {
//			    	tangent.normalize ( tangent );
//			    	setTangentAndPos1 ( tangent,position );
//			    	float length = Float.valueOf(Word1.trim()).floatValue(); 
//			        setTangentAndPos2_Straight ( tangent,position,length );
//			        set_length(length);
//			    }

			    if (Word2.equals("STF"))
			    {
			    	tangent.normalize ( tangent );
			    	setTangentAndPos1 ( tangent,position );
			        float length = Float.valueOf(Word1.trim()).floatValue();
			        //length = (length * scalefactor);
			        setTangentAndPos2_Straight ( tangent,position,length );
			        set_length(length);
			    }

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //if (TrackType1 == "RC" || TrackType1 == "RCS")
        if (TrackType1.equals("RCH") )
        {
        	//radius = 4.0f;
            float angle = -(float)Math.PI / (16.0f*botchfactor);

            tangent.normalize ( tangent );
            float m = (float) (0.35f * loc_radius * 3.0f * Math.abs ( angle ));
            tangent.scale(m);
            setTangentAndPos1 ( tangent, position );
            
            setTangentAndPos2_Curve ( tangent,position,angle,loc_radius );
            set_length(loc_radius * Math.abs(angle));
        }
        if (TrackType1.equals("RCold") )
        {
        	//was 20
            //radius = 4.0f;
            float angle = -(float)Math.PI / 8.0f;

            tangent.normalize ( tangent );
            float m = 0.35f * loc_radius * 3.0f * Math.abs ( angle );
            tangent.scale(m);
            setTangentAndPos1 ( tangent, position );
            
            setTangentAndPos2_Curve ( tangent,position,angle,loc_radius );
            set_length(loc_radius * Math.abs(angle));
        }
        if (TrackType1.equals("RC") )
        {
        	//was 20
            //radius = 4.0f;
            float angle = -(float)Math.PI / angleDivisorLarge;

            tangent.normalize ( tangent );
            float m = 0.35f * loc_radius *  Math.abs ( angle ) * scalefactor;
            tangent.scale(m);
            setTangentAndPos1 ( tangent, position );
            
            setTangentAndPos2_Curve ( tangent,position,angle,loc_radius );
            set_length(loc_radius * Math.abs(angle));
        }
        if (_TrackType.equals("LCold") || _TrackType.equals("LCS"))
        {
            //float radius = 4.0f;
            float angle = +(float)Math.PI / 8.0f;

            tangent.normalize ( tangent );
            float m = 0.35f * loc_radius * 3.0f * Math.abs ( angle );
            tangent.scale(m);
            setTangentAndPos1 ( tangent, position );
            setTangentAndPos2_Curve ( tangent, position, angle, loc_radius );
            set_length(loc_radius * Math.abs(angle));
        }
        if (_TrackType.equals("LCH") )
        {
            //float radius = 4.0f;
            float angle = +(float)Math.PI / (16.0f*botchfactor);

            tangent.normalize ( tangent );
            float m = 0.35f * loc_radius * 3.0f * Math.abs ( angle );
            tangent.scale(m);
            setTangentAndPos1 ( tangent, position );
            setTangentAndPos2_Curve ( tangent, position, angle, loc_radius );
            set_length(loc_radius * Math.abs(angle));
        }
        if (_TrackType.equals("LC") )
        {
            //float radius = 4.0f;
            float angle = +(float)Math.PI / (angleDivisorLarge*botchfactor);

            tangent.normalize ( tangent );
            float m = 0.35f * loc_radius * Math.abs ( angle ) * scalefactor;
            tangent.scale(m);
            setTangentAndPos1 ( tangent, position );
            setTangentAndPos2_Curve ( tangent, position, angle, loc_radius );
            set_length(loc_radius * Math.abs(angle));
        }
        if (TrackType1.equals("ST") || TrackType1.equals("ST1") || TrackType1.equals("STS") || TrackType1.substring(0,2).equals("STM"))
        	
        {
        	tangent.normalize ( tangent );
            setTangentAndPos1 ( tangent, position );
//            float length = 1.0f;
            float length;
            if(TrackType1.substring(0,2).equals("STM")){
            	length = U3_Utils.CStringToFloat(TrackType1.substring(3,10));
            }else{
            	length = 160f;  //was 3.43f
            }
            length = (length * scalefactor);
            setTangentAndPos2_Straight ( tangent,position, length );
            set_length(length);
        } else if (TrackType1.equals("ST2"))
        {
        	tangent.normalize ( tangent );
            setTangentAndPos1 ( tangent, position );
            float length = 2.28f;
            length = (length * scalefactor);
            setTangentAndPos2_Straight ( tangent,position, length );
            set_length(length);
        } else if (TrackType1.equals("ST11"))
        {
        	tangent.normalize ( tangent );
            setTangentAndPos1 ( tangent, position );
            float length = 6.85f;
            length = (length * scalefactor);
            setTangentAndPos2_Straight ( tangent,position, length );
            set_length(length);
        } else if (TrackType1.startsWith("ST") && (TrackType1.contains("_"))){
        	tangent.normalize ( tangent );
            setTangentAndPos1 ( tangent, position );
        	float length = (float) U3_Utils.CStringToDouble(U3_Utils.readLeaf(TrackType1, "_", 1));
        	length = (length * scalefactor);
        	setTangentAndPos2_Straight ( tangent,position, length );
            set_length(length);
        }else if (TrackType1.equals("STE"))
        {
        	tangent.normalize ( tangent );
            setTangentAndPos1 ( tangent, position );
            float length = 0f;
            setTangentAndPos2_Straight ( tangent,position, length );
            set_length(length);
        }
        //1//2//3//99System.out.print(this.toString());
    }


    public void setTangentAndPos2_Straight ( Vector3d tangent1,Point3d position1,float length ) throws DeepCopyException    
    {
//    	Vector3d t0 = tangent1;
//    	t0.scale(length);
//    	_tangent2 = tangent1;
    	
    	
        DeepCopyUtil deepCopyUtil = new DeepCopyUtil(); // create deep copies of objects.        
 
        // set the tangent
        _tangent2 = deepCopyUtil.deepCopy(tangent1);
        
        // set the position
        Vector3d t0 = new Vector3d();
        t0 = deepCopyUtil.deepCopy(tangent1);
        t0.scale(length);
        Transform3D translationMatrix1=new Transform3D();
        translationMatrix1.set(t0);
        translationMatrix1.transform((Point3d)position1,(Point3d)_position2);
    }

	public void set_length(float _length) {
		this._length = _length;
	}

	public float get_length() {
		return _length;
	}
	
    public Vector3d getTangent2()
    {
        return _tangent2; 
    }

    public Vector3d getTangent1()
    {
        return _tangent1; 
    }

    public Point3d getposition2()
    {
        return (Point3d)_position2; 
    }
    public Point3d getposition1()
    {
        return (Point3d)_position1;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TSegment [_TrackType=");
		builder.append(_TrackType);
		builder.append(", _tangent1=");
		builder.append(_tangent1.toString());
		builder.append(", _position1=");
		builder.append(_position1.toString());
		builder.append(", _tangent2=");
		builder.append(_tangent2.toString());
		builder.append(", _position2=");
		builder.append(_position2.toString());
		builder.append(", _length=");
		builder.append(_length);
		builder.append("]");
		return builder.toString();
//		return "fred";
	}

	public void set_BG() {
		// TODO Auto-generated method stub
		this._BG.addChild(get_node_position_TG());
		
	}

	private TransformGroup get_node_position_TG() {
		
		Vector3d P = new Vector3d(_position1);
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(P); //modified from set
		//create the branchgroup for the node
		TransformGroup translateToNode1 = new TransformGroup(t3d);
		//translateToNode1.addChild(trackBase);
		translateToNode1.addChild(rotateToTangent_TG());
		return translateToNode1;
	}
	
	private TransformGroup rotateToTangent_TG() {
		//rotate by the angle between the tangent and the vector from the origin to the node
		Vector3d nodePosition = new Vector3d(1,0,0);
		Vector3d tangent = new Vector3d(_tangent1);
		//double angle = nodePosition.angle(tangent);
		double angle = anglebetween(nodePosition ,tangent);
		Transform3D t3d = new Transform3D();
		t3d.rotZ(angle);
		//t3d.rotZ(Math.PI/2);
		//t3d.rotZ(0);
		TransformGroup rotateToTangent = new TransformGroup(t3d);
		rotateToTangent.addChild(get_trackelement_TG());
		return rotateToTangent;
	}
	
	private TransformGroup get_trackelement_TG() {
		TransformGroup trackelement_BG;
		if (_TrackType.equals("RT"))	{
			//rotate to
			trackelement_BG = get_Straight_TG();	
		}
		else	{
			//join up the two points
			//rotate to line between the two points
			Vector3d startnodeVector = new Vector3d(_position1);
			Vector3d endnodeVector = new Vector3d(_position2);
			Vector3d vectorBetweenPoints = new Vector3d();
			vectorBetweenPoints.sub(endnodeVector,startnodeVector);
			double angle = anglebetween(_tangent1,vectorBetweenPoints);
			
			Transform3D t3d = new Transform3D();
			t3d.rotZ(angle);
			//t3d.rotZ(0);
			TransformGroup rotateToTangent = new TransformGroup(t3d);
			rotateToTangent.addChild(get_Straight_TG());
			return rotateToTangent;	
		}
		return trackelement_BG;
	}
	private TransformGroup get_Straight_TG() {
			
			// go at 90 deg to tangent

			// draw straight track
			float trackheight=.20f;
			float boxWidth=.20f;
			float trackLen=(float)((Point3d) _position1).distance((Point3d)_position2);
			String Word2 = null;
			try {
				Word2 = StringHelper.GetLastWord(_TrackType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Word2.equals("STF")){
				appearanceStraight = new U1_TAppearance(Color.magenta);
			}
			else
			{
				appearanceStraight = new U1_TAppearance(Color.blue);
			}
			

			
			Appearance app = appearanceStraight.get_Appearance();
			com.sun.j3d.utils.geometry.Box trackBase = 
				new com.sun.j3d.utils.geometry.Box(trackLen/2.0f, boxWidth/2, trackheight/2, 
						Primitive.GENERATE_NORMALS |
						Primitive.GENERATE_TEXTURE_COORDS, app);
			//translate by box height vertically
			//Vector3d tan = new Vector3d(1.0f,1.0f,0.0f);
			Transform3D t3d1 = new Transform3D();
			t3d1.setTranslation( new Vector3f(0, 0,trackheight/2 ));
			TransformGroup middleOfTrackBase = new TransformGroup(t3d1);
			middleOfTrackBase.addChild(trackBase);
			//%%%stop%%%//
			//if(_drawStop) middleOfTrackBase.addChild(get_stop_for(trackheight));
			//if(_drawStop) middleOfTrackBase.addChild(get_stop_rev(trackheight));
			//if(!_drawStop) middleOfTrackBase.addChild(get_stop_none(trackheight));
			
			trackBase.setName("track:" + segmentName);
			
			// go up by boxheight/2
			// draw the rail
			float railHeight=.20f;
			float boxWidth1=.020f;
			float boxLen1=(float)((Point3d) _position1).distance((Point3d)_position2);
			 U1_TAppearance appearanceRail = new U1_TAppearance(Color.red);
			 app = appearanceRail.get_Appearance();
			com.sun.j3d.utils.geometry.Box rail = 
				new com.sun.j3d.utils.geometry.Box(boxLen1/2.0f, boxWidth1/2, railHeight/2, 
						Primitive.GENERATE_NORMALS |
						Primitive.GENERATE_TEXTURE_COORDS, app);
			//translate by box height vertically

			t3d1.setTranslation( new Vector3f(0, 0,trackheight +railHeight/2 ));
			TransformGroup middleOfTop = new TransformGroup(t3d1);
			middleOfTop.addChild(rail);
			
			//translate by from end of straight to middle
			Vector3d V1 = new Vector3d(trackLen/2,0.0f,0.0f);
			t3d1 = new Transform3D();
			t3d1.setTranslation(V1); //modified from set
			//create the branchgroup for the node
			TransformGroup bottomCentreOfTrackBase = new TransformGroup(t3d1);
			//get_station_TG.addChild(rotateBy90X);
			bottomCentreOfTrackBase.addChild(middleOfTrackBase);
			bottomCentreOfTrackBase.addChild(middleOfTop);
			
			return bottomCentreOfTrackBase;
		}
	
	
	private Node get_stop_for(float trackheight) {
		float stopHeight=0.5f;
		float stopWidth=0.5f;
		float stopLength=0.5f;

		U1_TAppearance appearance_Stop_forward = new U1_TAppearance(Color.green);
		Appearance app_for = appearance_Stop_forward.get_Appearance();
		com.sun.j3d.utils.geometry.Box stop_forward = 
			new com.sun.j3d.utils.geometry.Box(stopLength/2.0f, stopWidth/2, stopHeight/2, 
					Primitive.GENERATE_NORMALS |
					Primitive.GENERATE_TEXTURE_COORDS, app_for);
		stop_forward.setName("stop_forward:" + segmentName);

		Transform3D t3d1= new Transform3D();
		t3d1.setTranslation( new Vector3f(0, stopWidth/2,trackheight +stopHeight/2 ));
		TransformGroup middleOfTop = new TransformGroup(t3d1);
		middleOfTop.addChild(stop_forward);

		return middleOfTop;
	}
	
	private Node get_stop_rev(float trackheight) {
		float stopHeight=0.5f;
		float stopWidth=0.5f;
		float stopLength=0.5f;
		U1_TAppearance appearance_Stop_reverse = new U1_TAppearance(Color.red);
		Appearance app_rev = appearance_Stop_reverse.get_Appearance();

		com.sun.j3d.utils.geometry.Box stop_reverse = 
			new com.sun.j3d.utils.geometry.Box(stopLength/2.0f, stopWidth/2, stopHeight/2, 
					Primitive.GENERATE_NORMALS |
					Primitive.GENERATE_TEXTURE_COORDS, app_rev);
		stop_reverse.setName("stop_reverse:" + segmentName);
		//translate by box height vertically

		Transform3D t3d1= new Transform3D();
		t3d1.setTranslation( new Vector3f(0, -stopWidth/2,trackheight +stopHeight/2 ));
		TransformGroup middleOfTop = new TransformGroup(t3d1);
		middleOfTop.addChild(stop_reverse);
		
		return middleOfTop;
	}
	
	private Node get_stop_none(float trackheight) {
		float stopHeight=0.5f;
		float stopWidth=0.5f;
		float stopLength=0.5f;
		U1_TAppearance appearance_Stop_none = new U1_TAppearance(Color.blue);
		Appearance app_rev = appearance_Stop_none.get_Appearance();

		com.sun.j3d.utils.geometry.Box stop_none = 
			new com.sun.j3d.utils.geometry.Box(stopLength/2.0f, stopWidth/2, stopHeight/2, 
					Primitive.GENERATE_NORMALS |
					Primitive.GENERATE_TEXTURE_COORDS, app_rev);
		stop_none.setName("stop_none:" + segmentName);
		//translate by box height vertically

		Transform3D t3d1= new Transform3D();
		t3d1.setTranslation( new Vector3f(0, -stopWidth/2,trackheight +stopHeight/2 ));
		TransformGroup middleOfTop = new TransformGroup(t3d1);
		middleOfTop.addChild(stop_none);
		
		return middleOfTop;
	}


	// go up by boxheight/2
	// draw the rail
	


	public BranchGroup get_BG() {
		// TODO Auto-generated method stub
		return _BG;
	}

	public void setpointcolor(Color pointcolor) {
		//2//3//99System.out.print("setting pointcolor "  + pointcolor);
//		this.pointcolor = pointcolor;
//		if(!pointcolor.equals(Color.black)){
//			//2//3//99System.out.print("pointcolor = " + pointcolor.toString());
			appearanceStraight.SetEmissiveColor(pointcolor) ;
//		}
		//2//3//99System.out.print(this.toString());
	}

	public String get_TrackType() {
		return _TrackType;
	}

	public String getSegmentName() {
		return segmentName;
	}
}
