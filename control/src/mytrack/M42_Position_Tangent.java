package mytrack;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import Utilities.Util;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Text2D;

public class M42_Position_Tangent extends M41_TruckData_Position {

	public M42_Position_Tangent(String arc, String directionFacing,
			D_MyGraph graph, String startFraction, String trainCoupling) {
		super(arc, directionFacing, graph, startFraction, trainCoupling);
		setPositionTangent();
	}


	public M42_Position_Tangent(String[] startArcPair, String startFraction) {
		super(startArcPair, startFraction);
		
		setPositionTangent();
		
		//now can use getPosition and getTangent.
	}


	public M42_Position_Tangent(String arc, String directionFacing,
			D_MyGraph graph, String startFraction) {
		super(arc, directionFacing, graph, startFraction);
		setPositionTangent();// TODO Auto-generated constructor stub
	}


	void setPositionTangent() {
		
		G_TSegment seg = getSegment();
		float segmentFraction = getSegmentFraction();
		
		set_positionAndTangent(seg,segmentFraction);
		
	}

	public void set_positionAndTangent (G_TSegment seg,float segmentFraction) {
		
		seg.setPositionAndTangentForDisplayAsStraightLine(segmentFraction);
	}
	/*
	 * given the arc or arc pair and the fraction, get the position and tangent of the components of the object to be displayed
	 * 
	 * also does set_BG which should be done somewhere else, but is done here because it uses the position and tangent
	 */
	//these are supplied
	//F3_TArc tArc;
	//G_TSegment seg ;   //contains position and tangent
	//float segmentFraction = 0;
	//int segmentNo = 0;
	

//	private Vector3d tangent= null;
//	private Point3d position = null;
	
	//private List<String[]> startArcPairList = null;
	
//	private BranchGroup _BG= new BranchGroup();
//	
//	private D_MyGraph DJG;
//	private float objectLength = 0;
//	private String objectStr= null;
//	private String objectText= null;
//	
//	private String objectType= "Stop";
//	private String orientation = "same";
//	
//	private TransformGroup rotateToTangent= null;
	

	
		
//	public M41_Position_Tangent(String[] ArcPair, String fraction, String objectType){
//		
//		
//		
//		tArc = getArc(ArcPair);
//		set_PostionTangent(tArc, fraction, objectType);
//	}
//	
//	public M41_Position_Tangent(F3_TArc tArc, String fraction, String objectType){
//		this.tArc = tArc;
//		set_PostionTangent(tArc, fraction, objectType);
//	}
	
	  /**
	 * @param ArcPair		of the form
	 * @param fraction     	of the form "3.5" where 3 is the segment no and .5 is the segment fraction
	 * @param objectType	"Stop", "Engine", "Truck" , "Sensor"
	 */
//	public void set_PostionTangent(F3_TArc tArc, String fraction, String objectType){
//		
//		this.tArc = tArc;
//		// now get the tangent and position of the train
//		String strSegmentNo = U3_Utils.readLeaf(fraction, "\\.", 0);
//		segmentNo = U3_Utils.CStringToInt(strSegmentNo);
//		String strSegmentFraction = U3_Utils.readLeaf(fraction, "\\.", 1);
//		segmentFraction = U3_Utils.CStringToFloat("0." + strSegmentFraction);
//
//		seg = tArc.get_TrackSegments().get(segmentNo);
//		
//		set_positionAndTangent(seg,segmentFraction,objectType);
//	}
	
//	public void set_positionAndTangent(G_TSegment seg,float segmentFraction,String objectType){
//
////		switch (objectType) {
////		case "Stop":
////			super.objectLength = 0.1f;
////			objectStr = "stop";
////			objectText = "stopText";
////			break;
////		default:
////			objectLength = 0.1f;
////			objectStr = "default";
////			objectText = "defaultText";
////			break;
////		}
//
//		//set up the position and tangent
//		set_positionAndTangent(seg, segmentFraction, objectType//, objectLength, objectStr, objectText
//				);
//
////		//use the position and tangent to enable the object to be plotted if the item does not move
////		if (objectType.equals("Stop")){
////			set_BG();
////		}
//
//	}

//	public M41_Position_Tangent (G_TSegment seg,float segmentFraction, //String trainDirection, 
//			String objectType, float objectLength,
//			String objectStr, String objectText) {
//	
//		set_positionAndTangent(seg, segmentFraction);
//
//	}
	

	

	
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

//	D_MyGraph getDJG() {
//		return DJG;
//	}

	/**
	 * @return the position
	 */
	public Tuple3d getPosition() {
		return getSegment().position;
	}
	

//	/**
//	 * @param position the position to set
//	 */
//	public void setPosition(Vector3d position) {
//		seg.position = position;
//	}

	/**
	 * @return the position
	 */
	public Vector3d getTangent() {
		return getSegment().tangent;
	}


	public void setRoute(K2_Route route) {
		super.setRoute(route);
		
	}
	

//	/**
//	 * @param position the tangent to set
//	 */
//	public void setTangent(Vector3d tangent) {
//		seg.tangent = tangent;
//	}

	

}
