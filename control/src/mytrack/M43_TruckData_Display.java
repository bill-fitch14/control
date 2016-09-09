package mytrack;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Tuple3d;
//import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import sm2.E1;
import sm2.Main;
import A_Inglenook.M_TruckMovements;
import Utilities.Util;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Text2D;

public class M43_TruckData_Display extends M42_Position_Tangent
{
	public M43_TruckData_Display(String arc, String directionFacing, D_MyGraph graph, String startFraction){
		
		super(arc, directionFacing, graph, startFraction);
		//set_BG();
	}
	
	public M43_TruckData_Display(String arc, String directionFacing, D_MyGraph graph, String startFraction, String trainCoupling){
		
		super(arc, directionFacing, graph, startFraction, trainCoupling);
		//set_BG();
	}
	
	public M43_TruckData_Display(String[] startArcPair, String startFraction) {
		super(startArcPair, startFraction);
		//set_BG();
	}



	float objectLength;	
	String objectStr; 
	String objectText; 
	String objectType;
	
//	private double trainSpeed = U4_Constants.speed;
	
	//F3_TArc tArc = null;
	//G_TSegment seg = null;
	//float segmentFraction = 0;
	//int segmentNo = 0;
	

	//private Vector3d tangent= null;
	//private Point3d position = null;
	
	//private List<String[]> startArcPairList = null;
	
	private BranchGroup _BG= new BranchGroup();
	
	private D_MyGraph DJG;
//	private float objectLength = 0;
//	private String objectStr= null;
//	private String objectText= null;
//	
//	private String objectType= "Stop";
//	private String orientation = "same";
	
	private TransformGroup rotateToTangent= null;
	//private boolean moving = false;
	private TransformGroup engine_position_TG;
	private TransformGroup rotateToTangent_TG;
	private M43_TruckData_Display currentStop;
	private boolean currentStopActive = false;
	

//	private void set_BG(H4_truckLocation tl, Integer truckNames2) {
//		this.objectStr = ""+truckNames2;
//		this.objectText = ""+truckNames2;
//		this.setPosition(tl.position);
//		this.tangent = tl.tangent;
//		this.objectType = tl.truckType;
//		set_BG();
//
//	}
	
	public void reset_BG(float objectLength, String objectStr, String objectText, String objectType, boolean currentStopActive) {
		this.objectLength = objectLength;
		this.objectStr = objectStr;
		this.objectText = objectText;
		this.objectType = objectType;
		this.currentStopActive = currentStopActive;
		//engine_position_TG = engine_position_TG();

		System.out
				.println("set_BG before  engine_position_TG[truckNo], truckno"
						+ engine_position_TG.toString() );
		//this._BG.addChild(engine_position_TG);
	}
	
	public void set_BG(float objectLength, String objectStr, String objectText, String objectType) {
		this.objectLength = objectLength;
		this.objectStr = objectStr;
		this.objectText = objectText;
		this.objectType = objectType;
		engine_position_TG = engine_position_TG();

		System.out
				.println("set_BG before  engine_position_TG[truckNo], truckno"
						+ engine_position_TG.toString() );
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
		rotateToTangent_TG = rotateToTangent_TG();
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

		double angle = Util.anglebetween(new Vector3d(1, 0, 0), super.getTangent());

		if (getOrientation().equals("same")) {
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
		} else{
			//99System.out.print("error in objectType");
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
		//boxLen = 1;
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
				boxLen / 8, boxWidth / 2, boxHeight/2 ,
				Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				app);
		engineCab.setName("engineCab_" + objectStr);
		translateToEngineCabCentre.addChild(engineCab);
		
		return rotateBy90;
	}
//	private Node engine_TG() {
//		Transform3D t3d = new Transform3D();
//		t3d.rotY(0);
//		TransformGroup rotateBy90 = new TransformGroup(t3d);
//		// U1_TAppearance A = new U1_TAppearance(engineColor);
//		U1_TAppearance A = new U1_TAppearance(Color.red);
//		Appearance app = A.get_Appearance();
//		float boxHeight = .5f;
//		float boxWidth = .4f;
//		float boxLen = engineLength;
//		com.sun.j3d.utils.geometry.Box engineBody = new com.sun.j3d.utils.geometry.Box(
//				boxLen / 2 * .8f, boxWidth / 2, boxHeight / 2,
//				Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
//				app);
//		rotateBy90.addChild(engineBody);
//		engineBody.setName("engineBody_" + trainStr);
//		
//
//
//		Vector3d P = new Vector3d(3 * boxLen / 8, 0, boxHeight);
//		t3d.setTranslation(P);
//		A = new U1_TAppearance(Color.white);
//		app = A.get_Appearance();
//		TransformGroup translateToEngineCabCentre = new TransformGroup(t3d);
//		rotateBy90.addChild(translateToEngineCabCentre);
//
//		com.sun.j3d.utils.geometry.Box engineCab = new com.sun.j3d.utils.geometry.Box(
//				boxLen / 8, boxWidth / 2, boxHeight / 2,
//				Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
//				app);
//		engineCab.setName("engineCab_" + trainStr);
//		translateToEngineCabCentre.addChild(engineCab);
//		
//		
//		return rotateBy90;
//	}
	
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


	/**
	 * @return the position
	 */
	public Tuple3d getPosition() {
		return super.getPosition();
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



	private Node truck_TG(String type) {
		float objectLen = objectLength * .8f;
		float objectHeight = 0.5f;
		float objectWidth = .4f;
		Color objectColor = Color.cyan;
		float textOffset = -.3f;
		return block_TG(type, objectHeight, objectWidth, objectLen, objectColor, textOffset);
	}
	
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
		t3d1.rotZ(-Math.PI);   //rotates text
		t3d1.rotZ(0);
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

public String getObjectType() {
	return objectType;
}

public void setObjectType(String objectType) {
	this.objectType = objectType;
}

public BranchGroup get_BG() {
	return _BG;
}	



public void setStop(M43_TruckData_Display stop) {
	currentStop = stop;
	setCurrentStopActive(true);
	
}



public boolean update(float distance) {
	
	//float distance = (float) N2_Time.getDistance(trainSpeed);

//	//99System.out.print("train speed = " + trainSpeed);
//	//99System.out.print("distance = " + distance);
	
	//distance = 1;
//	//99System.out.print("updating truck");
	
//	if (E1.mybreak == true){
//		int a=1;
//		a = a+3;
//	}
	
	if( isCurrentStopActive()){	
		
//		//99System.out.print("updating truck moving current stop active" + distance);
		
//		if (currentStop.getStartArcPair()[0]==null){
//
//			if (this.getStartArcPair()[0]==null){
//				int a = 1;
//				a++;
//			}
//
//			int a = 1;
//			a++;
//		}

		if(this.getStartArcPair()[0].equals(currentStop.getStartArcPair()[0]) && 
				this.getStartArcPair()[1].equals(currentStop.getStartArcPair()[1]) &&
				this.getSegmentNo()==currentStop.getSegmentNo()){
			if(this.getOrientation().equals("same")) {
				if(this.getSegmentFraction() > currentStop.getSegmentFraction()){
					//99System.out.print("whoopee");
					setCurrentStopActive(false);

					return false;
				}else{
//					//99System.out.print("movement " + this.getMovement() + " orientation " + this.getOrientation());
//					//99System.out.print("not yet"  + "this.getSegmentFraction()" + this.getSegmentFraction()+ "currentStop.getSegmentFraction()" + currentStop.getSegmentFraction() );
					moveWithinSegment3(distance);
					setPositionTangent();
					updatePositionAndTangent(getPosition(),getTangent());
					//generate new event
					return true;
				}
			}else if (this.getOrientation().equals("opposite")){
				if(this.getSegmentFraction() < currentStop.getSegmentFraction()){
					//99System.out.print("whoopee");
					setCurrentStopActive(false);
					
					return false;
				}else{
//					//99System.out.print("movement " + this.getMovement() + " orientation " + this.getOrientation());
//					//99System.out.print("not yet"  + "this.getSegmentFraction()" + this.getSegmentFraction()+ "currentStop.getSegmentFraction()" + currentStop.getSegmentFraction() );
					moveWithinSegment3(distance);
					setPositionTangent();
					updatePositionAndTangent(getPosition(),getTangent());
					//generate new event
					return true;
				}
			}else{
				//99System.out.print("by gum there is an error here");
				return true;
			}
		}else{
			printPair("this.startarcpairlist" , this.getStartArcPair() );
			//99System.out.print("this.getSegmentNo()" + this.getSegmentNo());
			//99System.out.print("currentStop.getSegmentNo()" + currentStop.getSegmentNo());
			printPair("currentstop " , currentStop.getStartArcPair());
			moveWithinSegment3(distance);
			setPositionTangent();
			updatePositionAndTangent(getPosition(),getTangent());
			return true;
		}
	}else{
//		//99System.out.print("updating truck moving current stop not active" + this.objectStr + distance);
		moveWithinSegment3(distance);
		setPositionTangent();
		updatePositionAndTangent(getPosition(),getTangent());
		return true;
	}
	
	
}

public void printPair(String name, String[] sarray){
	
	//99System.out.print(name + " = " + sarray[0] + " , " + sarray[1]);
}

public void updatePositionAndTangent(Tuple3d position2, Vector3d newTangent) {

	Transform3D t3d = new Transform3D();
	// Vector3d newPosition = new Vector3d(position);
	Tuple3d tuple = position2;
	Vector3d vector = new Vector3d(tuple);
	//vector = (Vector3d) tuple;
	t3d.setTranslation(vector); // modified from set
	
	engine_position_TG.setTransform(t3d);

	t3d.setIdentity();
	double angle = Util.anglebetween(new Vector3d(1, 0, 0), newTangent);
	t3d.rotZ(1.0f * angle);
	rotateToTangent_TG.setTransform(t3d);

}

public void setRoute(K2_Route route) {
	super.setRoute(route);
	
}

public void reposition() {
	
	String[] ArcStringArray=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(this.getArc(), this.getDirectionFacing(), this.getDJG());
}

public boolean anotherRouteSameDirectiontravelling(K2_Route routeInOppDirTravelling, D_MyGraph graph) {
	/*
	 * truckdate_posdition is the position of the truck
	 * route is the route in the opposite direction traveling
	 * we run this to reposition the truck on the line in the opposite direction traveling
	 */
	
	
	/*
	 * The object has been specified by a track location which is specified by an arc
	 * This ia a string of the form 1_F_2_B_For
	 * 
	 * We could only stop if the train is moving with that orientation and direction
	 * but we assume that we want to stop the train if the route contains
	 * 
	 * 1_F_2_B_For, 2_B_1_F_For, but not 1_F_2_B_Rev, 2_B_1_F_Rev 
	 * for that we need to specify another stop 
	 */

	/*
	 * Get the position on the track
	 */
	String[] startArcPair = this.getStartArcPair();
	
	@SuppressWarnings("unused")
	String arcPairKey = getEngineRoutePairKey(this.getStartArcPair());
	String[] startArcStringArray=U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray2=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray3=U7_StartArcPairs.getStringArraySameDirectionTravellingOppDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray4=U7_StartArcPairs.getStringArrayOppDirectionTravellingOppDirectionFacing(this.getStartArcPair(), graph);
	


	/*
	 * Get all the positions on the path and check if any of the above stringArrays are on the route
	 * Should be startArcStringArray, but if any of the others then give error message and continue
	 */

	for (int i = 0; i < routeInOppDirTravelling.getRoutePath().size(); i++) {
		routeInOppDirTravelling.startArcPairList = routeInOppDirTravelling.getRoutePath().get(i);
		routeInOppDirTravelling.indexOfRoutePath = i;
		
		   //check this
		
		//3//99System.out.print("index of startRoute = " + indexOfStartRoute);
		for (int j = 0; j < routeInOppDirTravelling.startArcPairList.size(); j++) {
			
			String[] ArcStringArray = routeInOppDirTravelling.startArcPairList.get(j);
			/*
			 * Only set the remainder of the data if the engine lies on the route
			 */
			System.out.print("size "+routeInOppDirTravelling.startArcPairList.size()+ " Hi "+ j + Arrays.toString(ArcStringArray) + " " + Arrays.toString(startArcStringArray2));
			if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray))){

				//get the index corresponding to the list of arcs
				//routeInOppDirTravelling.indexOfStartArcPairList = j;
				routeInOppDirTravelling.startArcPair = routeInOppDirTravelling.startArcPairList.get(routeInOppDirTravelling.indexOfStartArcPairList);
//				route.indexOfStartArcPairList = j;
//				route.setIndexOfStartArcPairList(j);
//				truckdata_position.setIndexOfStartArcPairList(j);
//				
//				String[] startArcPair = truckdata_position.getStartArcPairList().get(indexOfStartArcPairList);
//				route.setStartArcPair(startArcPair);
//				truckdata_position.setStartArcPair(startArcPair);
				
				String startArcPairKey = getEngineRoutePairKey(this.getStartArcPair());
				this.orientation = graph.getOrientation(startArcPairKey);
				this.movement = graph.getMovement(startArcPairKey);

				
				int noSegments = this.tArc._TrackSegments.size();     //is 5
				
				this.setSegmentNo(noSegments-this.getSegmentNo()-1);    //is 3//this only works when it is the same route in reverse
				
				//this.setSegmentFraction(1-this.getSegmentFraction());
				
				//now move the truck to the new route
				
				
				this.setRoute(routeInOppDirTravelling);
				this.setIndexOfStartArcPairList(j);
				this.setArcPairList(routeInOppDirTravelling);
				//99System.out.print("new route is right2 ... "
				//99+Arrays.toString(ArcStringArray)+"..." 
				//99		+ Arrays.toString(startArcStringArray));
				
				//99System.out.print("found new route");
				
				return true;

			} else {
				//99System.out.print("new route is wrong2 ... "
				//99		+ Arrays.toString(ArcStringArray)+"..." + Arrays.toString(startArcStringArray));
				if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray2))){
					//99System.out.print("SameDirectionTravelling");
				}else if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray3))){
					//99System.out.print("OppDirectionFacing");
				}else if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray4))){
					//99System.out.print("OppDirectionTravelling and OppDirectionFacing");
				}
				//return false;
			}
		}	
	}
	return false;
	
}

public boolean swapDirectiontravelling(K2_Route routeInOppDirTravelling, D_MyGraph graph, String direction) {
	/*
	 * truckdate_posdition is the position of the truck
	 * route is the route in the opposite direction traveling
	 * we run this to reposition the truck on the line in the opposite direction traveling
	 */
	
	
	/*
	 * The object has been specified by a track location which is specified by an arc
	 * This ia a string of the form 1_F_2_B_For
	 * 
	 * We could only stop if the train is moving with that orientation and direction
	 * but we assume that we want to stop the train if the route contains
	 * 
	 * 1_F_2_B_For, 2_B_1_F_For, but not 1_F_2_B_Rev, 2_B_1_F_Rev 
	 * for that we need to specify another stop 
	 */
	System.out.println("HHHswapdirection"+direction);
	hswapDirectionTravelling(direction);
	/*
	 * Get the position on the track
	 */
	String[] startArcPair = this.getStartArcPair();
	
	@SuppressWarnings("unused")
	String arcPairKey = getEngineRoutePairKey(this.getStartArcPair());
	String[] startArcStringArray=U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray2=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray3=U7_StartArcPairs.getStringArraySameDirectionTravellingOppDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray4=U7_StartArcPairs.getStringArrayOppDirectionTravellingOppDirectionFacing(this.getStartArcPair(), graph);
	


	/*
	 * Get all the positions on the path and check if any of the above stringArrays are on the route
	 * Should be startArcStringArray, but if any of the others then give error message and continue
	 */

	for (int i = 0; i < routeInOppDirTravelling.getRoutePath().size(); i++) {
		routeInOppDirTravelling.startArcPairList = routeInOppDirTravelling.getRoutePath().get(i);
		routeInOppDirTravelling.indexOfRoutePath = i;
		
		   //check this
		
		//3//99System.out.print("index of startRoute = " + indexOfStartRoute);
		for (int j = 0; j < routeInOppDirTravelling.startArcPairList.size(); j++) {
			
			String[] ArcStringArray = routeInOppDirTravelling.startArcPairList.get(j);
			/*
			 * Only set the remainder of the data if the engine lies on the route
			 */
			//99System.out.print("size "+routeInOppDirTravelling.startArcPairList.size()+ " Hi "+ j + Arrays.toString(ArcStringArray) + " " + Arrays.toString(startArcStringArray2));
			
			if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray2))){

				//get the index corresponding to the list of arcs
				//routeInOppDirTravelling.indexOfStartArcPairList = j;
				routeInOppDirTravelling.startArcPair = routeInOppDirTravelling.startArcPairList.get(routeInOppDirTravelling.indexOfStartArcPairList);
//				route.indexOfStartArcPairList = j;
//				route.setIndexOfStartArcPairList(j);
//				truckdata_position.setIndexOfStartArcPairList(j);
//				
//				String[] startArcPair = truckdata_position.getStartArcPairList().get(indexOfStartArcPairList);
//				route.setStartArcPair(startArcPair);
//				truckdata_position.setStartArcPair(startArcPair);
				
				String startArcPairKey = getEngineRoutePairKey(this.getStartArcPair());
				this.orientation = graph.getOrientation(startArcPairKey);
				this.movement = graph.getMovement(startArcPairKey);

				
				int noSegments = this.tArc._TrackSegments.size();     //is 5
				
				this.setSegmentNo(noSegments-this.getSegmentNo()-1);    //is 3//this only works when it is the same route in reverse
				
				//this.setSegmentFraction(1-this.getSegmentFraction());
				
				//now move the truck to the new route
				
				
				this.setRoute(routeInOppDirTravelling);  // this was not set
				this.setIndexOfStartArcPairList(j);
				this.setArcPairList(routeInOppDirTravelling);
				//99System.out.print("new route is right2 ... "
				//99+Arrays.toString(ArcStringArray)+"..." 
				//99		+ Arrays.toString(startArcStringArray2));
				
				
				
				//99System.out.print("found new route");
				
				
				
				return true;

			} else {
				//99System.out.print("new route is wrong2 ... "
				//99		+ Arrays.toString(ArcStringArray)+"..." + Arrays.toString(startArcStringArray));
				if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray))){
					//99System.out.print("SameDirectionTravelling");
				}else if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray3))){
					//99System.out.print("OppDirectionFacing");
				}else if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray4))){
					//99System.out.print("OppDirectionTravelling and OppDirectionFacing");
				}
				//return false;
			}
		}	
	}
	return false;
	
}

/**
 * @return the moving
 */
//public boolean isMoving() {
//	return moving;
//}

/**
 * @param moving the moving to set
 */
//public void setMoving(boolean moving) {
//	this.moving = moving;
//}

private void hswapDirectionTravelling(String direction) {
//	Main.lo.swapDirectionTravelling(direction, 0.2);
}

/**
 * @param referenceTrucknNo
 * @param m43_TruckData_Display
 */
	public void setKeyValues(int referenceTrucknNo,
			M43_TruckData_Display m43_TruckData_Display) {

		this.segmentNo = m43_TruckData_Display.segmentNo;
		this.segment = m43_TruckData_Display.segment;
		this.segmentFraction = m43_TruckData_Display.segmentFraction;
		// this.moving = m43_TruckData_Display.moving;
		this.setMovement(getMovement());
		if (objectType == null) {        //don't want to chahe types of trucks
			this.objectType = m43_TruckData_Display.objectType;
		}

	}

/**
 * @return the currentStopActive
 */
public boolean isCurrentStopActive() {
	return currentStopActive;
}

/**
 * @param currentStopActive the currentStopActive to set
 */
public void setCurrentStopActive(boolean currentStopActive) {
	this.currentStopActive = currentStopActive;
}

public void setvaluesfromEngine(int referenceTruckNo,
		M43_TruckData_Display m43_TruckData_Display) {
	this.setArc(m43_TruckData_Display.getArc());
	this.settArc(m43_TruckData_Display.gettArc());
	//this assumes the engine is in the same arc pair as the truck. need a better method
	this.setStartArcPairList(m43_TruckData_Display.getStartArcPairList());
	this.setIndexOfStartArcPairList(m43_TruckData_Display.getIndexOfStartArcPairList());
	this.setStartArcPair(m43_TruckData_Display.getStartArcPair());

	
}






}
