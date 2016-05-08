package mytrack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ajexperience.utils.DeepCopyException;
import com.ajexperience.utils.DeepCopyUtil;

public class M4_TruckData  {
	
	private D_MyGraph DJG;

	
	//initial specification
	private String arc;
	//private String startFraction;
	private String directionFacing;
	
	//geometry
	F3_TArc tArc;
	G_TSegment segment;
	int segmentNo;
	float segmentFraction;
	
	//from route
	private List<String[]> routePairs;
	private List<List<String[]>> routePath;
	private int indexOfRoutePath;
	//route
	private List<String[]> startArcPairList;
	private int indexOfStartArcPairList;
	private String[] startArcPair;
	
	protected String orientation;
	protected String movement;
	
	public void setArcPairList(K2_Route route) {
		List<List<String[]>> listArcPairList = route.getRoutePath();    //not used at present
		indexOfStartArcPairList=route.getIndexOfStartArcPairList();
		startArcPairList = route.getStartArcPairList();
	}

	public M4_TruckData(String arc, String directionFacing, D_MyGraph graph, String startFraction, String trainCoupling){
		set_train_location(arc, directionFacing, graph, startFraction, trainCoupling);
	}
	
	public M4_TruckData(String[] startArcPair2, String startFraction) {
		set_train_location(startArcPair2, startFraction);
	}
	
	public M4_TruckData(String arc2, String directionFacing2, D_MyGraph graph,
			String startFraction) {
		set_train_location(arc, directionFacing, graph, startFraction);
	}

	private void set_train_location(String arc2, String directionFacing2,
			D_MyGraph graph, String startFraction) {
		set_train_location(arc, directionFacing, graph, startFraction);
		
	}

	public void set_train_location(String arc, String directionFacing, D_MyGraph graph, String startFraction, String trainCoupling) {
		
		this.arc = arc;
		//this.Fraction = startFraction;
		this.DJG = graph;
		
		startArcPair = U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(arc, directionFacing, graph);
		
		this.directionFacing = directionFacing;
		set_train_location(startArcPair, startFraction)
		;
	}

	public void set_train_location(String[] StartArcPair, String startFraction) {
		
		//startfraction is of the form "3.2" 
		// 3 is the segmentno
		// .2 is the segment fraction
		
		startArcPair = StartArcPair;

		String strSegmentFraction = U3_Utils.readLeaf(startFraction, "\\.", 1);
		float segmentFraction = U3_Utils.CStringToFloat("0." + strSegmentFraction);
		
		String strSegmentNo = U3_Utils.readLeaf(startFraction, "\\.", 0);
		//99System.out.print("**************" + strSegmentNo);
		int segmentNo = U3_Utils.CStringToInt(strSegmentNo);
		//99System.out.print("**************" +"segmentNo " + segmentNo);
		tArc = getArc(startArcPair);
//		if(tArc.get_TrackSegments().size()==3 && segmentNo == 3){
//			int a = 1;
//			a ++;
//		}
		
		//99System.out.print("segmentno " + segmentNo);
		//99System.out.print("tArc.get_TrackSegments().size()" + tArc.get_TrackSegments().size());
		
		if (tArc.get_TrackSegments().size()<segmentNo+1)	{
			int a=1;
			a=a+1;
		}
		
		G_TSegment seg = tArc.get_TrackSegments().get(segmentNo);
		
		
	
		//F3_TArc tArc = getArc(startArcPair);
		
		

		this.tArc = tArc;
		this.segment = seg;
		this.segmentNo = segmentNo;
		this.segmentFraction = segmentFraction;
		
	}


	//need to call code to setSegment
	
	float getDistanceRemainingInSegmentFromFractionWhenMoving() {
	

		// what we need
		String truckMovement = this.getTruckMovement();
		G_TSegment seg = this.getSegment();
		float segmentFraction = this.getSegmentFraction();


		float distanceRemainingInSegment;
		if (truckMovement.equals("same")) {
			distanceRemainingInSegment = seg.get_length() * (1.0f - segmentFraction);
		} else {
			distanceRemainingInSegment = seg.get_length() * (segmentFraction);
		}

		return distanceRemainingInSegment;
	}
	
	float getDistanceRemainingInSegmentFromFractionWhenMoving(String trainCoupling) {
		

		// what we need
		String truckMovement = this.getTruckMovement();
		G_TSegment seg = this.getSegment();
		float segmentFraction = this.getSegmentFraction();


		float distanceRemainingInSegment;
		if (truckMovement.equals("same")&& trainCoupling.equals("tail")||
				truckMovement.equals("opposite")&& trainCoupling.equals("head")	) {
			distanceRemainingInSegment = seg.get_length() * (1.0f - segmentFraction);
		} else {
			distanceRemainingInSegment = seg.get_length() * (segmentFraction);
		}

		return distanceRemainingInSegment;
	}
	
	float getDistanceRemainingInSegmentFromFraction3WhenPositioning() {
		

		// what we need
		String truckOrientation = this.getOrientation();
		G_TSegment seg = this.getSegment();
		float segmentFraction = this.getSegmentFraction();


		float distanceRemainingInSegment;
		if (truckOrientation.equals("opposite")  ) {
			distanceRemainingInSegment = seg.get_length() * (1.0f - segmentFraction);
		} else {
			distanceRemainingInSegment = seg.get_length() * (segmentFraction);
		}

		return distanceRemainingInSegment;
	}
	void setSegmentFractionWhenPositioning(float distance, float distanceRemainingInSegment) {

		String type = this.getSegment(DJG).get_TrackType();
		float segmentLength = this.getSegment(DJG).get_length();
		// data.setSegmentFraction(distance/segmentLength);

		float segmentFraction;
		String truckOrientation = this.getOrientation();
		if (truckOrientation.equals("opposite")  ) {
			segmentFraction = 1.0f -((distanceRemainingInSegment - distance) / segmentLength);
		} else {
			segmentFraction = ((distanceRemainingInSegment - distance) / segmentLength);
		}
		this.setSegmentFraction(segmentFraction);

	}
	
	void setSegmentFraction(float distance, float distanceRemainingInSegment) {
		
		

		//String type = this.getSegment(DJG).get_TrackType();
		float segmentLength = this.getSegment(DJG).get_length();
		// data.setSegmentFraction(distance/segmentLength);

		float segmentFraction;
		// truckOrientation = this.getOrientation();
		String truckMovement = this.getMovement();
		if (truckMovement.equals("same")) {
			segmentFraction = 1.0f - ((distanceRemainingInSegment - distance)
					/ segmentLength);
		} else {
			segmentFraction = ((distanceRemainingInSegment - distance) / segmentLength);
		}
		
//		//99System.out.print("setSegmentFraction" + segmentFraction);
		
		this.setSegmentFraction(segmentFraction);

	}
	boolean positionInNextSegment3(float distance) {

//		//99System.out.print("positionInNextSegment3");
//		//99System.out.print("distance = " + distance);
		int segmentNo = this.getSegmentNo();
//		//99System.out.print("segmentNo =" + segmentNo);
		String truckMovement = this.getTruckMovement();
//		//99System.out.print("truckMovement = " + truckMovement);
		String truckOrientation = this.getOrientation();
		if (truckOrientation.equals("opposite") ) {
			if (segmentNo < this.getArc(DJG).get_TrackSegments().size() - 1) {
//				//99System.out.print(this.getArc(DJG).toString());
				segmentNo++;
//				//99System.out.print("segmentNo: " + segmentNo);
				this.setSegmentNo(segmentNo);
				segment = this.getSegment(getDJG());
				
				// this.setSegmentFraction(1);
				truckMovement = this.getTruckMovement();
				truckOrientation = this.getOrientation();
				
				this.setSegmentFraction(0);
//				//99System.out.print("segmentfraction =" + segmentFraction);
				////99//99System.out.print("");
				positionWithinSegment3(distance);
				return true;
			} else {
				if (positionInNextArc3(distance) == true) {
					return true;
				} else {
					return false;
				}
			}
		} else {

			if (segmentNo > 0) {
				segmentNo--;
				////99//99System.out.print("segmentNo: " + segmentNo );
				this.setSegmentNo(segmentNo);
				segment = this.getSegment(getDJG());
				//String truckOrientation = this.getTruckOrientation();
				
				this.setSegmentFraction(1);
		
				////99//99System.out.print("");
				positionWithinSegment3(distance);
				return true;
			} else {
				if (moveToNextArc3(distance) == true) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
//	boolean positionInNextSegment3(float distance) {
//
//		//what we need
//		//getArc
//		int segmentNo = this.getSegmentNo();
//		String truckMovement = this.getTruckMovement();
//		String truckOrientation = this.getOrientation();
//		
//		//do it
//		if (truckMovement.equals("same")) {
//			if (segmentNo < this.getArc(DJG).get_TrackSegments().size() - 1) {
//				//99System.out.print(this.getArc(DJG).toString());
//				//99System.out.print("segmentno before"+segmentNo);
//				segmentNo++;
//				//99System.out.print("just incremented segmentno"+segmentNo);
//				//99System.out.print("segmentNo: " + segmentNo);
//				this.setSegmentNo(segmentNo);
//				// this.setSegmentFraction(1);
//				//String truckOrientation = this.getTruckOrientation();
//				if (truckMovement.equals("same")) {
//					this.setSegmentFraction(0);
//				} else {
//					this.setSegmentFraction(1);
//				}
//				////99//99System.out.print("");
//				moveWithinSegment3(distance);
//				return true;
//			} else {
//				if (moveToNextArc3(distance) == true) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		} else {
//			if (segmentNo > 0) {
//				segmentNo--;
//				//99System.out.print("segmentNo: " + segmentNo );
//				this.setSegmentNo(segmentNo);
//				//String truckOrientation = this.getTruckOrientation();
//				if (truckMovement.equals("same")) {
//					this.setSegmentFraction(1);
//				} else {
//					this.setSegmentFraction(0);
//				}
//				////99//99System.out.print("");
//				moveWithinSegment3(distance);
//				return true;
//			} else {
//				if (moveToNextArc3(distance) == true) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		}
//	}
	
	boolean positionInNextArc3(float distance) {

		int indexOfStartArcPairList = this.getIndexOfStartArcPairList();

//		if (indexOfStartArcPairList < this.getStartArcPairList().size() - 1) {
		if (indexOfStartArcPairList >0) {	
//			//99System.out.print("indexOfStartArcPairList: "
//					+ indexOfStartArcPairList);
//			//99System.out.print("this.getStartArcPairList().size()-1: "
//					+ (this.getStartArcPairList().size() - 1));
			indexOfStartArcPairList--;

			
			this.setIndexOfStartArcPairList(indexOfStartArcPairList);
			String[] startArcPair = this.getStartArcPairList().get(indexOfStartArcPairList);
			this.setStartArcPair(startArcPair);
			
//			segmentNo = 0;
//			this.setSegmentNo(segmentNo);
//			segment = this.getSegment(getDJG());
			
//			this.setOrientation(getOrientation(startArcPair));
//			this.setMovement(getMovement(startArcPair));
			tArc = getArc(startArcPair);
			//99System.out.print(tArc.toString());
			initialiseSegmentInArc3(startArcPair);
			positionWithinSegment3(distance);
			return true;
		} else {
			// move to next route
//			//99System.out.print("can't move");
			return false;
		}
	}
	
	boolean moveToNextArc3(float distance) {

		int indexOfStartArcPairList = this.getIndexOfStartArcPairList();
		////99//99System.out.print("this.getStartArcPairList().size(): " +
		// this.getStartArcPairList().size());
		////99//99System.out.print("indexOfStartArcPairList = " +
		// indexOfStartArcPairList);
		////99//99System.out.print("************");
		////99//99System.out.print("StartArcPairList: " +
		// Arrays.toString(this.getStartArcPairList().get(indexOfStartArcPairList)));
		////99//99System.out.print("************");
		if (getIndexOfStartArcPairList() == 2){
			int a=1;
			a++;
		}
		if (indexOfStartArcPairList < this.getStartArcPairList().size() - 1) {
//			//99System.out.print("indexOfStartArcPairList: "
//					+ indexOfStartArcPairList);
//			//99System.out.print("this.getStartArcPairList().size()-1: "
//					+ (this.getStartArcPairList().size() - 1));
			indexOfStartArcPairList++;

			
			this.setIndexOfStartArcPairList(indexOfStartArcPairList);
			String[] startArcPair = this.getStartArcPairList().get(indexOfStartArcPairList);
			this.setStartArcPair(startArcPair);
			
//			segmentNo = 0;
//			this.setSegmentNo(segmentNo);
//			segment = this.getSegment(getDJG());
			
//			this.setOrientation(getOrientation(startArcPair));
//			this.setMovement(getMovement(startArcPair));
			tArc = getArc(startArcPair);
			//99System.out.print(tArc.toString());
			initialiseSegmentInArc3(startArcPair);
			moveWithinSegment3(distance);
			return true;
		} else {
			// move to next route
//			//99System.out.print("can't move");
			return false;
		}
	}
	
	public void positionTrucks(float f) {
		//move positions the truck in one direction, position the other. Convenient eh?
		if (f>0){
			positionWithinSegment3(f);
		}else{
			moveWithinSegment3(-f);
		}

	}
	
	void moveWithinSegment3(float distance,String trainCoupling){			
		float distanceRemainingInSegment = getDistanceRemainingInSegmentFromFractionWhenMoving(trainCoupling);
		////99System.out.print("distanceRemainingInSegment1:" +
		//distanceRemainingInSegment);
		String truckOrientation = this.getOrientation();
		String truckMovement = this.getTruckMovement();
		////99//99System.out.print(headOfTrainData.toString());
//		//99System.out.print("truckMovement = "+ truckMovement);
		float distanceToGo;
		int mult = 1;
		float distanceM = 0;
		if (trainCoupling.equals("head")){
			distanceM =-distance;
		}else{
			distanceM = distance;
		}
		distance = distance*mult;
		if ((truckMovement.equals("opposite") && trainCoupling.equals("tail"))||
				(truckMovement.equals("same") && trainCoupling.equals("head"))) {
			if (distanceM > distanceRemainingInSegment) {
				distanceToGo =  distanceRemainingInSegment - distanceM;   //this will be negative as the distance is greater than distance remaining
				distanceToGo = -distanceToGo; //make +ve
//				//99System.out.print("moveWithinSegment3 A");
//				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
//				//99System.out.print("distanceToGo = " + distanceToGo);
//				//99System.out.print("1 distanceToGo="+distanceToGo);
				moveToNextSegment3(distanceToGo);
			} else {
				setSegmentFraction(distanceM, distanceRemainingInSegment);
				distanceRemainingInSegment = getDistanceRemainingInSegmentFromFractionWhenMoving(trainCoupling);
			}
		} else {
			if (distanceM > distanceRemainingInSegment) {
				distanceToGo =  distanceRemainingInSegment - distanceM;   //this will be negative as the distance is greater than distance remaining
				distanceToGo = -distanceToGo; //make +ve
//				//99System.out.print("moveWithinSegment3 B");
//				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
//				//99System.out.print("distanceToGo = " + distanceToGo);

				moveToNextSegment3(distanceToGo);
			} else {
				setSegmentFraction(distanceM, distanceRemainingInSegment);
				distanceRemainingInSegment = getDistanceRemainingInSegmentFromFractionWhenMoving(trainCoupling);			
			}
		}

	}

	
	
	void moveWithinSegment3(float distance) {
//		//99System.out.print("distance =" + distance);
		// if new position is in current segment, then move within segment
		float distanceRemainingInSegment = getDistanceRemainingInSegmentFromFractionWhenMoving();
		////99System.out.print("distanceRemainingInSegment1:" +
		//distanceRemainingInSegment);
		String truckOrientation = this.getOrientation();
		String truckMovement = this.getTruckMovement();
		////99//99System.out.print(headOfTrainData.toString());
//		//99System.out.print("truckMovement = "+ truckMovement);
		float distanceToGo;
		if (truckMovement.equals("opposite")) {
			if (distance > distanceRemainingInSegment) {
				distanceToGo =  distanceRemainingInSegment - distance;   //this will be negative as the distance is greater than distance remaining
				distanceToGo = -distanceToGo; //make +ve
//				//99System.out.print("moveWithinSegment3 A");
//				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
//				//99System.out.print("distanceToGo = " + distanceToGo);
//				//99System.out.print("1 distanceToGo="+distanceToGo);
				moveToNextSegment3(distanceToGo);
			} else {
				setSegmentFraction(distance, distanceRemainingInSegment);
				//				//99System.out.print("segmentfraction1="
				//						+ this.getSegmentFraction());
				distanceRemainingInSegment = getDistanceRemainingInSegmentFromFractionWhenMoving();
				//				//99System.out.print("segmentfraction2="
				//						+ this.getSegmentFraction());
				//				//99System.out.print("distanceRemainingInSegment2:"
				//						+ distanceRemainingInSegment);
			}
		} else {
			if (distance > distanceRemainingInSegment) {
				distanceToGo =  distanceRemainingInSegment - distance;   //this will be negative as the distance is greater than distance remaining
				distanceToGo = -distanceToGo; //make +ve
//				//99System.out.print("moveWithinSegment3 B");
//				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
//				//99System.out.print("distanceToGo = " + distanceToGo);
				
				moveToNextSegment3(distanceToGo);
			} else {
				setSegmentFraction(distance, distanceRemainingInSegment);
				distanceRemainingInSegment = getDistanceRemainingInSegmentFromFractionWhenMoving();			
			}
		}
	}
	
	void positionWithinSegment3(float distance) {
		//99System.out.print("distance =" + distance);
		// if new position is in current segment, then move within segment
		float distanceRemainingInSegment = getDistanceRemainingInSegmentFromFraction3WhenPositioning();
		//99System.out.print("distanceRemainingInSegment1:" +
		//99distanceRemainingInSegment);
		String truckOrientation = this.getOrientation();
		String truckMovement = this.getTruckMovement();
		
		////99//99System.out.print(headOfTrainData.toString());
		//99System.out.print("truckOrientation = "+ truckOrientation);
		float distanceToGo;
		if (!truckOrientation.equals("opposite") ) {
			if (distance > distanceRemainingInSegment) {
				distanceToGo =  distanceRemainingInSegment - distance;   //this will be negative as the distance is greater than distance remaining
				distanceToGo = -distanceToGo; //make +ve
				//99System.out.print("positionWithinSegment3 B");
//				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
//				//99System.out.print("distanceToGo = " + distanceToGo);
				positionInNextSegment3(distanceToGo);
			} else {
				//99System.out.print("positionWithinSegment3 A1");
				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
				setSegmentFractionWhenPositioning(distance, distanceRemainingInSegment);
				//				System.out.prindistanceRemainingInSegmenttln("segmentfraction1="
				//						+ this.getSegmentFraction());
				distanceRemainingInSegment = getDistanceRemainingInSegmentFromFraction3WhenPositioning();
//				//99System.out.print("A1 distanceRemainingInSegment = " + distanceRemainingInSegment);
				
//				if(distanceRemainingInSegment!=distanceRemainingInSegment2){
//					distanceRemainingInSegment=distanceRemainingInSegment2;
//				}
				//				//99System.out.print("segmentfraction2="
				//						+ this.getSegmentFraction());
				//				//99System.out.print("distanceRemainingInSegment2:"
				//						+ distanceRemainingInSegment);
			}
		} else {
			if (distance > distanceRemainingInSegment) {
				distanceToGo = distanceRemainingInSegment - distance; //this will be negative as the distance is greater than distance remaining
				distanceToGo = -distanceToGo; //make +ve
				////99System.out.print("positionWithinSegment3 B");
//				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
//				//99System.out.print("distanceToGo = " + distanceToGo);
				positionInNextSegment3(distanceToGo);
			} else {
//				//99System.out.print("positionWithinSegment3 B1");
//				//99System.out.print("distanceRemainingInSegment = " + distanceRemainingInSegment);
//				//99System.out.print("distance = " + distance);
				setSegmentFractionWhenPositioning(distance, distanceRemainingInSegment);
				distanceRemainingInSegment = getDistanceRemainingInSegmentFromFraction3WhenPositioning();
//				//99System.out.print("B1 distanceRemainingInSegment = " + distanceRemainingInSegment);
			}
		}
	}


	


	boolean moveToNextSegment3(float distance) {

		int segmentNo = this.getSegmentNo();
//		//99System.out.print("segmentNo =" + segmentNo);
		String truckMovement = this.getTruckMovement();
//		//99System.out.print("truckMovement = " + truckMovement);
		String truckOrientation = this.getOrientation();
		if (truckMovement.equals("same")) {
			if (segmentNo < this.getArc(DJG).get_TrackSegments().size() - 1) {
//				//99System.out.print(this.getArc(DJG).toString());
				segmentNo++;
//				//99System.out.print("segmentNo: " + segmentNo);
				this.setSegmentNo(segmentNo);
				segment = this.getSegment(getDJG());
				// this.setSegmentFraction(1);
				truckOrientation = this.getOrientation();
				
				this.setSegmentFraction(0);
				
				////99//99System.out.print("");
				moveWithinSegment3(distance);
				return true;
			} else {
				if (moveToNextArc3(distance) == true) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			if (segmentNo > 0) {
				segmentNo--;
				////99//99System.out.print("segmentNo: " + segmentNo );
				this.setSegmentNo(segmentNo);
				segment = this.getSegment(getDJG());
				//String truckOrientation = this.getTruckOrientation();
				
				this.setSegmentFraction(1);
				
				////99//99System.out.print("");
				moveWithinSegment3(distance);
				return true;
			} else {
				if (moveToNextArc3(distance) == true) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
	F3_TArc getArc(D_MyGraph dJG){
		String arcPairKey = Arrays.deepToString(startArcPair);
		Map<String, F3_TArc> arcStringArrayToArcMap = dJG.getArcStringArrayToArcMap();
		if (arcStringArrayToArcMap.keySet().contains(arcPairKey)){
			F3_TArc tArc = arcStringArrayToArcMap.get(arcPairKey);
			return tArc;
		} 
		else {
			//3//99System.out.print("tArc could not be found");
			return null;
		}
	}
	
	

	
	void initialiseSegmentInArc3( String[] startArcPair) {

//		String truckOrientation = getOrientation(startArcPair);
		String truckMovement = getMovement(startArcPair);

		if (!truckMovement.equals("same")) {
			this.setSegmentNo(this.getArc(DJG).get_TrackSegments().size() - 1); // set to the end segment
			////99//99System.out.print("SegmentNo = " + this.getSegmentNo());
			this.setSegmentFraction(1);
		} else {
			this.setSegmentNo(0); // set to the end segment
			this.setSegmentFraction(0);
		}

		this.segment = this.getSegment(getDJG());
		////99//99System.out.print("segment name = " + segment.getSegmentName());
		////99//99System.out.print("getStartArcPairToString = " +
		// truckData.getStartArcPairToString());
		////99//99System.out.print("truckNo = " + this.truckNo);
		////99//99System.out.print("orientation = " + truckOrientation);
		////99//99System.out.print("movement = " + truckMovement);
		////99//99System.out.print("fraction = " + truckData.getSegmentFraction());

	}
	
	G_TSegment getSegment(D_MyGraph dJG){
		int segmentNo2 = getSegmentNo();
		////99System.out.print("segmentNo2="+segmentNo2);
		LinkedList<G_TSegment> get_TrackSegments = getArc(dJG).get_TrackSegments();
		////99System.out.print("SIZE=" +this.getArc(DJG).get_TrackSegments().size());
		G_TSegment seg = get_TrackSegments.get(segmentNo2);
		return seg;
	}

	
//	List<List<String[]>> getEngineRoutePath(){
//		return getRoutePath();
//	}



//	public int getIndexOfStartRoute() {
//		return indexOfStartRoute;
//	}

	public List<String[]> getStartArcPairList() {
		return startArcPairList;
	}

	public String[] getStartArcPair() {
		return startArcPair;
	}

//	public String getTruckOrientation() {
//		return orientation;
//	}

	public String getTruckMovement() {
		return getMovement(startArcPair);
	}

	public int getIndexOfStartArcPairList() {
		return indexOfStartArcPairList;
	}

//	public List<List<String[]>> getRoutePath() {
//		return RoutePath;
//	}



	public void setSegmentNo(int segmentNo) {
		this.segmentNo = segmentNo;
	}



	public void setSegmentFraction(float segmentFraction) {
		this.segmentFraction = segmentFraction;
	}


	public G_TSegment getSegment() {
		return segment;
	}

	public void setSegment(G_TSegment segment) {
		this.segment = segment;
	}

	public float getSegmentFraction() {
		return segmentFraction;
	}

	public int getSegmentNo() {
		return segmentNo;
	}

	public String getOrientation() {
		return getOrientation(startArcPair);
	}

	public String getMovement() {
		return getMovement(startArcPair);
	}

//	public void setIndexOfStartArcPairList(int indexOfStartArcPairList) {
//		this.indexOfStartArcPairList = indexOfStartArcPairList;
//	}
//
//	public void setStartArcPair(String[] startArcPair) {
//		this.startArcPair = startArcPair;
//	}

	String getOrientation(String[] startArcPair) {

		String startArcPairKey = getEngineRoutePairKey(startArcPair);
		String truckOrientation = getDJG().getOrientation(startArcPairKey);
		this.orientation = truckOrientation;
		return truckOrientation;
	}

	String getMovement(String[] startArcPair) {

		String startArcPairKey = getEngineRoutePairKey(startArcPair);
		String truckMovement = getDJG().getMovement(startArcPairKey);

		return truckMovement;
	}

	public String getEngineRoutePairKey(String[] engineRoutePair) {

		String erpkey = Arrays.deepToString(engineRoutePair);
		return erpkey;
	}

	public D_MyGraph getDJG() {
		return DJG;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public void setMovement(String movement) {
		this.movement = movement;
	}
	private F3_TArc getArc(String[] arcPair) {
		// String[] erp;
		// if (Orientation.equals("same")){
		// erp = it1.previous();
		// it1.next(); // get ready for next() call
		// } else {
		// erp = it1.next();
		// it1.previous(); // get ready for previous() call
		// }
		// String[] erp = it1.next();
		// it1.previous();
		String arcPairKey = getEngineRoutePairKey(arcPair);
		////99//99System.out.print("arcPairKey = " + arcPairKey.toString());

		// String truckOrientation = getDJG().getOrientation(erpKey);
		// String erpKey = getEngineRoutePairKey(erp);

		// erp is 9_from_Rev, 8_From_Rev

		Map<String, F3_TArc> arcStringArrayToArcMap = getDJG().getArcStringArrayToArcMap();
		
		// 3//99System.out.print("all keys in DJG");

		//
		// for(String s : arcStringArrayToArcMap.keySet()){
		// //3//99System.out.print(s);
		// if (arcPairKey.equals(s)){
		// //3//99System.out.print("BINGO");
		// }
		// }
		if (arcStringArrayToArcMap.keySet().contains(arcPairKey)) {
			F3_TArc tArc = arcStringArrayToArcMap.get(arcPairKey);
			//99System.out.print("tArc = " + tArc.toString());
			//99System.out.print("arclength = "+tArc.Arclength());
			// G_TSegment seg = tArc.get_TrackSegments().get(startPosition);
			return tArc;
		}

		// 3//99System.out.print("tArc could not be found");
		return null;

		// to get the tangent we need
	}

//	public void setIndexOfStartRoute(int indexOfStartRoute) {
//		this.indexOfStartRoute = indexOfStartRoute;
//	}

	public void setIndexOfStartArcPairList(int indexOfStartArcPairList) {
		this.indexOfStartArcPairList = indexOfStartArcPairList;
	}

	public void setStartArcPairList(List<String[]> startArcPairList) {
		this.startArcPairList = startArcPairList;
	}

	public void setStartArcPair(String[] startArcPair) {
		this.startArcPair = startArcPair;
	}



	public String getDirectionFacing() {
		return directionFacing;
	}



	public void setDirectionFacing(String directionFacing) {
		this.directionFacing = directionFacing;
	}

	public String getArc() {
		
		return arc;
	}

	public void setRoute(K2_Route route) {
		
		this.routePath=route.getRoutePath();
		this.indexOfRoutePath=route.indexOfRoutePath;
		this.routePairs=route.getRoutePairs();
		this.indexOfStartArcPairList=route.indexOfStartArcPairList;
		this.startArcPairList=route.startArcPairList;	
		this.startArcPair=route.startArcPair;	
	}

	public void setArc(String arc) {
		this.arc = arc;
	}
	
	public void setArc(String[] startArcPair) {
		this.arc = arc;
	}

	public F3_TArc gettArc() {
		return tArc;
	}

	public void settArc(F3_TArc tArc) {
		this.tArc = tArc;
	}

//	@Override
//	public List<List<String[]>> getRoutePath() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	

}
