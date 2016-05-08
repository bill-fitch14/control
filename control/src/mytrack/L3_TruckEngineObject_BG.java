package mytrack;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.apache.commons.digester.ObjectParamRule;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Text2D;

import Utilities.Util;

public class L3_TruckEngineObject_BG {
	
	/*
	 * given the arc or arc pair and the fraction, get the position and tangent of the components of the object to be displayed
	 * 
	 * also does set_BG which should be done somewhere else, but is done here because it uses the position and tangent
	 */
	//these are supplied
	F3_TArc tArc = null;
	G_TSegment seg = null;
	float segmentFraction = 0;
	int segmentNo = 0;
	

	private Vector3d tangent= null;
	private Point3d position = null;
	
	//private List<String[]> startArcPairList = null;
	
	private BranchGroup _BG= new BranchGroup();
	
	private D_MyGraph DJG;
	private float objectLength = 0;
	private String objectStr= null;
	private String objectText= null;
	
	private String objectType= "Stop";
	private String orientation = "same";
	
	private TransformGroup rotateToTangent= null;
	

	public L3_TruckEngineObject_BG(){
		
	}
	
	
//	public L3_DisplayTrain(Point3d position, Vector3d tangent) {
//		
//		set_PostionAndTangent(position, tangent);
//		this.objectStr = "";
//		this.objectType = "";
//	}
	
	public L3_TruckEngineObject_BG(G_TSegment seg, float segmentFraction,
			String trainDirection, String objectType, int truckNo) {
		
//		set_positionAndTangent(seg, segmentFraction, objectType, objectLength, objectStr, objectText);
		
	}

	
	public L3_TruckEngineObject_BG(Point3d position, Vector3d tangent,
			String objectStr, String objectType, BranchGroup _BG,
			String orientation, TransformGroup rotateToTangent,
			float objectLength, String objectText) {
		this.setPosition(position);
		this.tangent = tangent;
		this.objectStr = objectStr;
		this.objectType = objectType;
		this._BG = _BG;
		this.orientation = orientation;
		this.rotateToTangent = rotateToTangent;
		this.objectLength = objectLength;
		this.objectText = objectText;
	}
	
	public L3_TruckEngineObject_BG(String[] ArcPair, String fraction, String objectType){
		
		tArc = getArc(ArcPair);
		set_PostionTangent(tArc, fraction, objectType);
	}
	
	


	
//	public void set_PostionAndTangent(Point3d position, Vector3d tangent) {
//		this.setPosition(position);
//		this.tangent = tangent;
//	}
	
	public L3_TruckEngineObject_BG(F3_TArc tArc, String fraction, String objectType){
		this.tArc = tArc;
		set_PostionTangent(tArc, fraction, objectType);
	}
	
	  /**
	 * @param ArcPair		of the form
	 * @param fraction     	of the form "3.5" where 3 is the segment no and .5 is the segment fraction
	 * @param objectType	"Stop", "Engine", "Truck" , "Sensor"
	 */
	public void set_PostionTangent(F3_TArc tArc, String fraction, String objectType){
		
		this.tArc = tArc;
		// now get the tangent and position of the train
		String strSegmentNo = U3_Utils.readLeaf(fraction, "\\.", 0);
		segmentNo = U3_Utils.CStringToInt(strSegmentNo);
		String strSegmentFraction = U3_Utils.readLeaf(fraction, "\\.", 1);
		segmentFraction = U3_Utils.CStringToFloat("0." + strSegmentFraction);

		seg = tArc.get_TrackSegments().get(segmentNo);
		
		set_positionAndTangent(seg,segmentFraction,objectType);
	}
	
	public void set_positionAndTangent(G_TSegment seg,float segmentFraction,String objectType){

		switch (objectType) {
		case "Stop":
			objectLength = 0.1f;
			objectStr = "stop";
			objectText = "stopText";
			break;
		default:
			objectLength = 0.1f;
			objectStr = "default";
			objectText = "defaultText";
			break;
		}

		//set up the position and tangent
//		set_positionAndTangent(seg, segmentFraction, objectType, objectLength, objectStr, objectText);

		//use the position and tangent to enable the object to be plotted if the item does not move
		if (objectType.equals("Stop")){
			set_BG();
		}

	}

	public L3_TruckEngineObject_BG (G_TSegment seg,float segmentFraction, //String trainDirection, 
			String truckEngineType, float truckEngineLength,
			String truckEngineText) {
		
		String truckEngineStr = truckEngineType + truckEngineText;
	
//		set_positionAndTangent(seg, segmentFraction, truckEngineType, truckEngineLength, truckEngineStr, truckEngineText);

	}
	


//	public void set_positionAndTangent (G_TSegment seg,float segmentFraction, //String trainDirection, 
//			String objectType, float objectLength,
//			String objectStr, String objectText) {
//		//99System.out.print("segmentfraction =" + segmentFraction);
//		
//		seg.setPositionAndTangentForDisplayAsStraightLine(segmentFraction);
//		// The object that we are plotting is either an engine or a truck
//		// if i = 0 then we have the engine, which at the moment we distinguish
//		// by a colour
//
//		// just for now the tangent will be along the straight line joining the
//		// start and end of the arcs
////		Vector3d tangent1 = seg.getTangent1();
////		Vector3d tangent2 = seg.getTangent2();
////		
////		tangent.add(tangent1, tangent2);
//// 
////		Point3d position1 = seg.getposition1();
////		Point3d position2 = seg.getposition2();
////		position.set(0, 0, 0);
////		// position.add(position1,position2);
////		// position.scale(0.5);
////		position.sub(position2, position1);
////		position.scale(segmentFraction);
////		position.add(position1);
//		
//		this.objectStr = objectStr;				//for naming the object
//		this.objectText = objectText; 			//for displaying on object
//		this.objectLength = objectLength;
//		this.objectType = objectType;
//
//	}
	
	
	private Node block_TG(String type, float objectHeight, float objectWidth, float objectLen, Color objectColor, float textOffset) {
		
//		rotateBy90 --> objectBody --> rotateBy90Text --> makeText(pt,""+objectText)
		
		//define rotational transform group rotateby90
		Transform3D t3d = new Transform3D();
		t3d.rotY(0);
		TransformGroup rotateBy90 = new TransformGroup(t3d);
		U1_TAppearance A = new U1_TAppearance(Color.green);
		Appearance app = A.get_Appearance();
//		float boxHeight = 0.5f;
//		float boxWidth = .4f;
		
		//define box
		float boxLen = objectLength;
		com.sun.j3d.utils.geometry.Box objectBody = new com.sun.j3d.utils.geometry.Box(
				objectLen / 2 , objectWidth / 2, objectHeight / 2,
				Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				app);
		
		//add objectbody to rotateby90
		rotateBy90.addChild(objectBody);
		objectBody.setName(type + "_" + objectStr);
		
		//define rotational transform group rotateBy90Text
		Transform3D t3d1 = new Transform3D();
		t3d1.rotZ(-Math.PI);
		TransformGroup rotateBy90Text = new TransformGroup(t3d1);
		
		//define translation group makeText(pt,""+objectStr)
		Vector3d pt = new Vector3d(0,textOffset,objectHeight/2);
		
		//add the text to rotateBy90Text
		rotateBy90Text.addChild(makeText(pt,""+objectText));

		//add the text to the box
		objectBody.addChild(rotateBy90Text);

//		Vector3d P = new Vector3d(3 * boxLen / 8, 0, objectHeight);
//		t3d.setTranslation(P);
//		A = new U1_TAppearance(Color.cyan);
//		app = A.get_Appearance();
//		TransformGroup translateToEngineCabCentre = new TransformGroup(t3d);
//		rotateBy90.addChild(translateToEngineCabCentre);
//
//		com.sun.j3d.utils.geometry.Box engineCab = new com.sun.j3d.utils.geometry.Box(
//				boxLen / 8, objectWidth / 2, objectHeight / 2,
//				Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
//				app);
//		engineCab.setName(objectStr + "_" + type);
		// translateToEngineCabCentre.addChild(engineCab);
		return rotateBy90;
	}

	
//	private void set_BG(H4_truckLocation tl, Integer truckNames2) {
//		this.objectStr = ""+truckNames2;
//		this.objectText = ""+truckNames2;
//		this.setPosition(tl.position);
//		this.tangent = tl.tangent;
//		this.objectType = tl.truckType;
//		set_BG();
//
//	}
	
	public void set_BG() {
		////99//99System.out.print("set_BG before  6[truckNo], truckno"
		// + engine_position_TG[truckNo].toString() + " " + truckNo);
		TransformGroup engine_position_TG = engine_position_TG();

		//99System.out
		//99		.println("set_BG before  engine_position_TG[truckNo], truckno"
		//99				+ engine_position_TG.toString() );
		this._BG.addChild(engine_position_TG);
	}
	
	private TransformGroup engine_position_TG() {
		
//		uses position
//		translateToNode --> rotateToTangent_TG() 

		Vector3d positionOfObject;
		positionOfObject = new Vector3d(getPosition());
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(positionOfObject); 
		TransformGroup translateToNode = new TransformGroup(t3d);
		translateToNode.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		translateToNode.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		TransformGroup rotateToTangent_TG = rotateToTangent_TG();
		translateToNode.addChild(rotateToTangent_TG);
//		System.out
//				.println("translateToNode  engine_position_TG[truckNo], truckno"
//						+ translateToNode.toString() + " " + truckNo);

		return translateToNode;
	}
	
	private TransformGroup rotateToTangent_TG() {
		
//		uses tangent
//		rotateToTangent --> engineTG
//		or              --> truckTG
//		or              --> sensorTG
//		or              --> sensorTG
		
		// the train is initially pointing along the x axis
		// rotate by the angle between the tangent and the x axis

		// Vector3d tangent = new Vector3d(0,1,0); //arbitrary tangent pointing
		// along y axis

		double angle = Util.anglebetween(new Vector3d(1, 0, 0), tangent);

		if (this.orientation.equals("same")) {
			angle = angle + Math.PI;
		} else {
			angle = angle;
		}

		Transform3D t3d = new Transform3D();
		t3d.rotZ(1.0f * angle);
		rotateToTangent = new TransformGroup(t3d);

		rotateToTangent.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		rotateToTangent.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// rotateToTangent.addChild(engine_TG(tangent));
		// 3//99System.out.print("type ="+type);
		if (this.objectType.equals("Engine")) {
			rotateToTangent.addChild(engine_TG());
		} else if (objectType.equals("Truck")) {
			rotateToTangent.addChild(truck_TG(this.objectType));
		} else if (objectType.equals("Sensor")) {
			rotateToTangent.addChild(sensor_TG(this.objectType));
		} else if (objectType.equals("Stop")) {
			rotateToTangent.addChild(sensor_TG(this.objectType));
		}

		return rotateToTangent;
		
	}

	
	private Node engine_TG() {
		
//		rotateBy90 --> engineBody
//		           --> translateToEngineCabCentre --> engineCab
		
		Transform3D t3d = new Transform3D();
		t3d.rotY(0);
		TransformGroup rotateBy90 = new TransformGroup(t3d);
		// U1_TAppearance A = new U1_TAppearance(engineColor);
		U1_TAppearance A = new U1_TAppearance(Color.red);
		Appearance app = A.get_Appearance();
		float boxHeight = .5f;
		float boxWidth = .4f;
		float boxLen = objectLength;
		com.sun.j3d.utils.geometry.Box engineBody = new com.sun.j3d.utils.geometry.Box(
				boxLen / 2 * .8f, boxWidth / 2, boxHeight / 2,
				Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				app);
		rotateBy90.addChild(engineBody);
		engineBody.setName("engineBody_" + objectStr);
		
		Vector3d P = new Vector3d(3 * boxLen / 8, 0, boxHeight);
		t3d.setTranslation(P);
		A = new U1_TAppearance(Color.white);
		app = A.get_Appearance();
		TransformGroup translateToEngineCabCentre = new TransformGroup(t3d);
		rotateBy90.addChild(translateToEngineCabCentre);

		com.sun.j3d.utils.geometry.Box engineCab = new com.sun.j3d.utils.geometry.Box(
				boxLen / 8, boxWidth / 2, boxHeight / 2,
				Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				app);
		engineCab.setName("engineCab_" + objectStr);
		translateToEngineCabCentre.addChild(engineCab);
		
		return rotateBy90;
	}
	
	private Node sensor_TG(String type) {
		float objectLen = objectLength;
		float objectHeight = 0.5f;
		float objectWidth = .4f;
		Color objectColor = Color.cyan;
		float textOffset = -0.3f;
		return block_TG(type, objectHeight, objectWidth, objectLen, objectColor, textOffset);
	}
	
	private F3_TArc getArc(String[] arcPair) {

		String arcPairKey = getarcPairKey(arcPair);

		Map<String, F3_TArc> arcStringArrayToArcMap = getDJG().getArcStringArrayToArcMap();

		if (arcStringArrayToArcMap.keySet().contains(arcPairKey)) {
			F3_TArc tArc = arcStringArrayToArcMap.get(arcPairKey);
			return tArc;
		}

		// 3//99System.out.print("tArc could not be found");
		return null;

	}
	
	public String getarcPairKey(String[] engineRoutePair) {

		String erpkey = Arrays.deepToString(engineRoutePair);
		return erpkey;
	}

	D_MyGraph getDJG() {
		return DJG;
	}

	/**
	 * @return the position
	 */
	public Point3d getPosition() {
		return position;
	}
	

	
	private TransformGroup makeText(Vector3d vertex, String text)
	  // Create a Text2D object at the specified vertex
	  {
		final Color3f black = new Color3f(Color.black);
		Text2D message = new Text2D(text, black , "SansSerif", 130, Font.BOLD );
	       // 36 point bold Sans Serif

	    TransformGroup tg = new TransformGroup();
	    Transform3D t3d = new Transform3D();
	    t3d.setTranslation(vertex);
	    tg.setTransform(t3d);
	    tg.addChild(message);
	    return tg;
	  }

	
	


	

	
	


	/**
	 * @param position the position to set
	 */
	public void setPosition(Point3d position) {
		this.position = position;
	}




	private Node truck_TG(String type) {
		float objectLen = objectLength * .8f;
		float objectHeight = 0.5f;
		float objectWidth = .4f;
		Color objectColor = Color.cyan;
		float textOffset = -.3f;
		return block_TG(type, objectHeight, objectWidth, objectLen, objectColor, textOffset);
	}


}
