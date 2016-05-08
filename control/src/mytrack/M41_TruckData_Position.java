package mytrack;

import java.util.List;

/*
 * Positions within segment
 * 
 * Sets the new segment fraction given the distance from the given segment fraction
 * useful for getting the next truck position
 * 
 * not used for initial truck
 * 
 * 
 */

public class M41_TruckData_Position extends M4_TruckData {

	
	

	public M41_TruckData_Position(String arc, String directionFacing, D_MyGraph graph, String startFraction, String trainCoupling){
		super(arc, directionFacing, graph, startFraction, trainCoupling);
	}
	
	public M41_TruckData_Position(String[] startArcPair, String startFraction) {
		super(startArcPair, startFraction);
	}

	public M41_TruckData_Position(String arc, String directionFacing,
			D_MyGraph graph, String startFraction) {
		super(arc, directionFacing, graph, startFraction);
	}

	void setNewTruckLocation(M4_TruckData existingTruck, M4_TruckData newTruck, 
			float distanceTruckFromEngine, String truckType,
			float itemLength, int truckNo){

		positionWithinSegment3(distanceTruckFromEngine);
	}

	public void set_truck_location3(float distanceTruckFromEngine, String truckType,
	float itemLength, int truckNo) {
		
		positionWithinSegment3(distanceTruckFromEngine);
		
		//99System.out.print("segmentFraction before setBGTrainElement = "
		//99		+ this.getSegmentFraction() + " truckNo = " + truckNo);
		
		L3_TruckEngineObject_BG tl = new L3_TruckEngineObject_BG(this.getSegment(), this.getSegmentFraction(),
				getDirectionFacing(), truckType, truckNo);
		
		//99System.out.print("segmentFraction after setBGTrainElement = "
		//99		+ this.getSegmentFraction() + " truckNo = " + truckNo);

		//return tl;
	}
	
//	void positionWithinSegment3(float distance) {
//		//99System.out.print("distance =" + distance);
//		// if new position is in current segment, then move within segment
//		float distanceRemainingInSegment = this.getDistanceRemainingInSegmentFromFraction3WhenPositioning();
//		////99//99System.out.print("distanceRemainingInSegment1:" +
//		// distanceRemainingInSegment);
//		String truckOrientation = this.getOrientation();
//		String truckMovement = this.getTruckMovement();
//		////99//99System.out.print(headOfTrainData.toString());
//		////99//99System.out.print("truckMovement = "+ truckMovement);
//		float distanceToGo;
//		int dir;
//
//		if (truckOrientation.equals("same")) {
//			if (Math.abs(distance) > Math.abs(distanceRemainingInSegment)) {
//				distanceToGo = distance - distanceRemainingInSegment;
//				positionInNextSegment3(distanceToGo);
//			} else {
//				setSegmentFractionWhenPositioning3(distance, distanceRemainingInSegment);
//				//99System.out.print("segmentfraction1="
//						+ this.getSegmentFraction());
//				distanceRemainingInSegment = this.getDistanceRemainingInSegmentFromFraction3WhenPositioning();
//				//99System.out.print("segmentfraction2="
//						+ this.getSegmentFraction());
//				//99System.out.print("distanceRemainingInSegment2:"
//						+ distanceRemainingInSegment);
//			}
//		} else {
//			if (Math.abs(distance) > Math.abs(distanceRemainingInSegment)) {
//				distanceToGo = Math.abs(distance) - Math.abs(distanceRemainingInSegment);
//				positionInNextSegment3(distanceToGo);
//			} else {
//				setSegmentFractionWhenPositioning3(distance, distanceRemainingInSegment);
//				////99//99System.out.print("segmentfraction1=" +
//				// headOfTrainData.getSegmentFraction());
//				distanceRemainingInSegment = getDistanceRemainingInSegmentFromFraction3WhenPositioning();
//				//setSegmentFractionWhenPositioning3(distance, distanceRemainingInSegment);
//				////99//99System.out.print("segmentfraction1=" +
//				// headOfTrainData.getSegmentFraction());
//				// distanceRemainingInSegment = getDistanceRemainingInSegmentFromFraction3();
//				////99//99System.out.print("segmentfraction2=" +
//				// headOfTrainData.getSegmentFraction());
//				////99//99System.out.print("distanceRemainingInSegment2:" +
//				// distanceRemainingInSegment);
//			}
//		}
//	}
	
	void 	setSegmentFraction3(float distance, float distanceRemainingInSegment) {

		//String type = existingTruck.getSegment(getDJG()).get_TrackType();
		float segmentLength = this.getSegment(getDJG()).get_length();
		// data.setSegmentFraction(distance/segmentLength);

		float segmentFraction;
		String truckOrientation = this.getOrientation();
		String truckMovement = this.getTruckMovement();
		if (truckMovement.equals("same")) {
			if (truckOrientation.equals("same")) {
				segmentFraction = 1.0f - ((Math.abs(distanceRemainingInSegment) - Math.abs(distance)) / segmentLength);
			}else{
				segmentFraction =  ((Math.abs(distanceRemainingInSegment)-Math.abs(distance))/segmentLength);
			}
			//segmentFraction = 1.0f - ((distanceRemainingInSegment - distance) / segmentLength);
			segmentFraction = 1.0f - ((Math.abs(distanceRemainingInSegment) - Math.abs(distance)) / Math.abs(segmentLength));
		} else {
			if (truckOrientation.equals("same")) {
				segmentFraction = ((Math.abs(distanceRemainingInSegment) - Math.abs(distance)) / segmentLength);
			}else{
				segmentFraction = 1.0f - ((Math.abs(distanceRemainingInSegment)-Math.abs(distance))/segmentLength);
			}
			segmentFraction = ((distanceRemainingInSegment - distance) / segmentLength);
		}

		this.setSegmentFraction(segmentFraction);

	}
	
	void 	setSegmentFractionWhenPositioning3(float distance, float distanceRemainingInSegment) {

		//String type = existingTruck.getSegment(getDJG()).get_TrackType();
		float segmentLength = this.getSegment(getDJG()).get_length();
		// data.setSegmentFraction(distance/segmentLength);

		float segmentFraction;
		String truckOrientation = this.getOrientation();
		String truckMovement = this.getTruckMovement();
		if (truckOrientation.equals("same")) {
			if (truckOrientation.equals("same")) {
				segmentFraction = 1.0f - ((Math.abs(distanceRemainingInSegment) - Math.abs(distance)) / segmentLength);
			}else{
				segmentFraction =  ((Math.abs(distanceRemainingInSegment)-Math.abs(distance))/segmentLength);
			}
			//segmentFraction = 1.0f - ((distanceRemainingInSegment - distance) / segmentLength);
			segmentFraction = 1.0f - ((Math.abs(distanceRemainingInSegment) - Math.abs(distance)) / Math.abs(segmentLength));
		} else {
			if (truckOrientation.equals("same")) {
				segmentFraction = ((Math.abs(distanceRemainingInSegment) - Math.abs(distance)) / segmentLength);
			}else{
				segmentFraction = 1.0f - ((Math.abs(distanceRemainingInSegment)-Math.abs(distance))/segmentLength);
			}
			segmentFraction = ((distanceRemainingInSegment - distance) / segmentLength);
		}

		this.setSegmentFraction(segmentFraction);

	}

	public void setRoute(K2_Route route) {
		super.setRoute(route);
		
	}









}
