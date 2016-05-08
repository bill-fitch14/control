package mytrack;

import java.util.Enumeration;
import java.util.List;

import javax.media.j3d.BoundingSphere;

import A_Inglenook.Myfunctions;

public class M0_Description {


	
	/*
	 * There are two modes of calling 
	 * 
	 * 1) Set up the train
	 * 2) Move the train
	 * 
	 * 1) set up the train
	 * The old way it is done is (in A_setup)
	 * 	arcAndNodeLinkedList.getTrainParametersfromLong(new long[] {Myfunctions.getInit(), Myfunctions.getInit() });
	 *  calls eRoute1 = new H2_Engine_Route(trainNo, color, engineLength,
					noTrucks, truckLength, route, startArc, startFraction,
					startDirection, truckNames);
	 * 
		H1_Engine_Routes _engine_routes = arcAndNodeLinkedList.get_H1_EngineRoutes();  //engine routes now contain M5_Engine_Route

		graph.set_EngineRoutes(_engine_routes);
		graph.generate_shortest_routes(); // this at moment plots the train
		branchGroup.set_BG_train();
		
		i.e.
		getTrainParametersfromLong : 
			getParameters(Myfunctions.getSth(), "sth");
				eRoute1 = new H2_Engine_Route(trainNo, color, engineLength,
					noTrucks, truckLength, route, startArc, startFraction,
					startDirection, truckNames);
		
		generate_shortest_routes :
			get_EngineRoutes().generate_shortest_routes(_DJG);
				for(H2_Engine_Route er :_engine_Routes){
					er.convertRouteToEngineRoute();
					er.generateEngineRoutePairs();
					er.generateEngineRoutePath();
					er.print_engine_route_path();
					if(startArcOnRoute){
						er.set_train_location();	// sets HeadOfTrainData, sets distances from head to trucks, adds truck to trainLocation
						er.set_BG_Train();			//for (int i = 0; i < trainLocation.size(); i++) {
													//    set_BG(trainLocation.get(i), truckNames[i]);
				
		set_BG_train
			for(H2_Engine_Route eRoute : getEngineRoutes ){
				// ensure we have a method of getting the node parameters from the node name
				// set the branchgroup for the engine
				//1//2//3//99System.out.print("calling eRoute.set_BG();" + eRoute.getEngineRoute().toString());
				//eRoute.set_BG_train();
				_BG.addChild(eRoute.get_BG());
				N1_TimeBehavior behav = new N1_TimeBehavior(U4_Constants.timeInterval,h1EngineRoutes) ;
			}
		
		2) Move the train
		The old way it is done is
		
		public void set_BG_train() {
		//set the branchGroups for all the engines
		boolean setEngineBranchgroup=true;
		if(setEngineBranchgroup){
			....
			
			N1_TimeBehavior behav = new N1_TimeBehavior(U4_Constants.timeInterval,h1EngineRoutes) ;
			behav.setSchedulingBounds(new BoundingSphere());
			this._BG.addChild(behav);
		
		
	public void processStimulus(Enumeration criteria)
	{
		//3//99System.out.print("Processing stimulus");
		N2_Time.update();
		h1engineRoute.update();      // ignore criteria
		wakeupOn(timeOut);
	}
		
	public void update(){
		for(H2_Engine_Route er :_engine_Routes){
			//3//99System.out.print("processing train no:" + er.getTrainNo());
			if (er.moving){
				er.update();
			}
		}
	}

	public void update() {
		float distance = (float) N2_Time.getDistance(trainSpeed);
		if (distance > 0) {
			moveWithinSegment2(distance, headOfTrainData);
			for (int truckNo = 0; truckNo < lengths.length; truckNo++) {

				H3_TruckData truckData = trainData.get(truckNo);
				
				moveWithinSegment2(distance, truckData);
				updatePositionAndTangent(truckData.getSegment(DJG).position, truckData.getSegment(DJG).tangent, truckNo);
			}
		}
		

	 *
	 *
	 * The new way it is done is
	 * 
	 * 1) Set up the train
	 * arcAndNodeLinkedList.getTrainParametersfromLong(new long[] {Myfunctions.getInit(), Myfunctions.getInit() });
	 *  in this routine we have
	 * 	eRoute1 = new H2_Engine_Route(trainNo, color, engineLength,
					noTrucks, truckLength, route, startArc, startFraction,
					startDirection, truckNames);	
		replaced by
		M61_Train_On_Route trainOnRoute = new M61_Train_On_Route( trainStr,  trainNumber,  strEngineColor,
					 engineLength,  noTrucks,  truckLength,
					 truckNames,  route,  startArc,
					 startFraction,  startDirection);	we need to change engineLength and truckLength to float from integer
		trainsOnRoute.addtrainOnRoute(route1);
		
	 *  H1_Engine_Routes _engine_routes = arcAndNodeLinkedList.get_H1_EngineRoutes();  //engine routes now contain M5_Engine_Route

		graph.set_EngineRoutes(_engine_routes);
		graph.generate_shortest_routes(); // this at moment plots the train
		branchGroup.set_BG_train();
		
		i.e.
		getTrainParametersfromLong : 
			getParameters(Myfunctions.getSth(), "sth");
				eRoute1 = new H2_Engine_Route(trainNo, color, engineLength,
					noTrucks, truckLength, route, startArc, startFraction,
					startDirection, truckNames);
			if(objectIsOnRoute()){
				dispTrain.set_train_location();	// given the engine position, generates the positions of the trucks			
			}
	 * 			M3_DisplayTrain
	 * 
	 * 			need to insert
	 * 			M42_truckData_Display
	 * 			set_BG
	 * 
	 * 2) Move the train
	 * 		The new way it is done is
		N1_TimeBehavior behav = new N1_TimeBehavior(U4_Constants.timeInterval,h1EngineRoutes) ;
			N2_Time.update();
			h1engineRoute.update();
				for(H2_Engine_Route er :_engine_Routes){
					if (er.moving){
						er.update();
					}
				}
					float distance = (float) N2_Time.getDistance(trainSpeed);
					if (distance > 0) {
						moveWithinSegment2(distance, headOfTrainData);
						for (int truckNo = 0; truckNo < lengths.length; truckNo++) {
			
							H3_TruckData truckData = trainData.get(truckNo);
							
							moveWithinSegment2(distance, truckData);
							updatePositionAndTangent(truckData.getSegment(DJG).position, truckData.getSegment(DJG).tangent, truckNo);
						}
					}
					
	In: M2_move_train;/>
	public void update(double trainSpeed) {

		// store current time, retrieve present frame time, and hence get time
		// since last frame

		// get last position of engine and trucks

		// get distance to travel

				float distance = (float) N2_Time.getDistance(trainSpeed);
		//
		//		//99System.out.print("train speed = " + trainSpeed);
		//		//99System.out.print("distance = " + distance);

		if (distance > 0) {
			headOfTrainData.moveWithinSegment3(distance);
			for (int truckNo = 0; truckNo < lengths.length; truckNo++) {

				M4_TruckData truckData = trainData.get(truckNo);

				truckData.moveWithinSegment3(distance);
				truckData.getSegment(DJG).setPositionAndTangentForDisplayAsStraightLine(truckData.getSegmentFraction());
				//				if (firstTime) {
				//					tl = modify_truck_location2(distance, truckData,
				//						distanceFromHead[truckNo], truckType[truckNo],
				//						itemLength[truckNo], truckNo);
				//					
				//				}else{
				//					tl = modify_truck_location3(tl,distance, truckData,
				//							distanceFromHead[truckNo], truckType[truckNo],
				//							itemLength[truckNo], truckNo);
				//				}


				updatePositionAndTangent(truckData.getSegment(DJG).position, truckData.getSegment(DJG).tangent, truckNo);

			}
		}
	}
	 */

}
