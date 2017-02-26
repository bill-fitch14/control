package mytrack;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.media.j3d.BranchGroup;

import sm2.E1;

public class M6_Trains_On_Routes {
	
	private static boolean DEBUG = true;
	private static void print(String x){
		if (DEBUG ){
		System.out.println(x);
		}
	}

	static List<M61_Train_On_Route> trainsOnRoutes = new LinkedList<M61_Train_On_Route>();

	// private static boolean tr.checkforstop;

	public static M61_Train_On_Route getTrainOnRoute(String trainStr) {
		for (M61_Train_On_Route tr : trainsOnRoutes) {
			if (tr.getTrainStr().equals(trainStr)) {
				return tr;
			}
		}
		return null;
	}
	
	public static M61_Train_On_Route removeTrainOnRoute(String trainStr) {
		for (M61_Train_On_Route tr : trainsOnRoutes) {
			if (tr.getTrainStr().equals(trainStr)) {
				trainsOnRoutes.remove(tr);
			}
		}
		return null;
	}

	public static void movetrain(String trainStr) {
		M61_Train_On_Route tr = getTrainOnRoute(trainStr);

		for (M43_TruckData_Display truck : tr.getTruckPositions()) {
			truck.setCurrentStopActive(false);
		}
		N2_Time.initialise();
		tr.setMoving(true);

	}
	
	public static void stoptrain(String trainStr) {
		M61_Train_On_Route tr = getTrainOnRoute(trainStr);

		for (M43_TruckData_Display truck : tr.getTruckPositions()) {
			truck.setCurrentStopActive(false);
		}
		tr.setMoving(false);

	}

	int i = 0;

	public static void moveTrainCheckForStop(String trainStr,
			M76Stop stop, M76Stop[] sensors, int truckNo) {

		M61_Train_On_Route tr = getTrainOnRoute(trainStr);


		// set stops for all trucks false
		for (M43_TruckData_Display truck : tr.getTruckPositions()) {
			truck.setCurrentStop(null);
		}
		
		// set sensors for all trucks false
		for (M43_TruckData_Display truck : tr.getTruckPositions()) {
			truck.setCurrentSensors(null);
		}

		// make the train stop at the required truck
		if (stop != null) {
			// set the stop for the required truck true
			tr.getTruckData(truckNo).setStop(stop);
		}
		
		//make the engine trigger the sensor (not the truck)
		//if its going the other way the last truck should trigger the sensor
		if (sensors != null) {
			// set the sensors for the required truck true
			tr.getTruckData(0).setSensors(sensors);
		}


		// set the train moving
		N2_Time.initialise();
		((M61_Train_On_Route) tr).setMoving(true);

	}

	public void set_BG(BranchGroup _BG) {
		_BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE); 
		_BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		_BG.setCapability(BranchGroup.ALLOW_DETACH);
		_BG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		for (M62_train tr : trainsOnRoutes) {
			
			_BG.addChild(tr.getTrainBranchGroup());
			//tr.getTrainBranchGroup().setCapabilityIsFrequent(BranchGroup.ALLOW_DETACH);
			tr.getTrainBranchGroup().setCapability(BranchGroup.ALLOW_CHILDREN_WRITE); 
			tr.getTrainBranchGroup().setCapability(BranchGroup.ALLOW_CHILDREN_READ);
			tr.getTrainBranchGroup().setCapability(BranchGroup.ALLOW_DETACH);
			tr.getTrainBranchGroup().setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
			

		}
	}

	public void gsr(D_MyGraph graph) {

		for (M62_train tr : trainsOnRoutes) {
			// tr.getMyroute().convertRouteToEngineRoute(tr.getMyroute().getRoute());
			// tr.getMyroute().generateEngineRoutePairs();
			// tr.getMyroute().setDJG(tr.getDJG());
			// tr.getMyroute().generateEngineRoutePath();

			// tr.setParametersAndDisplay(tr.getDJG(),
			// tr.getK2_route().getRoute());
			// //tr.getMyroute().setParameters(tr.getDJG(),
			// tr.getMyroute().getRoute());
			// boolean objectIsOnRoute = tr.objectIsOnRoute();
			// if(objectIsOnRoute){
			// tr.setTrainLocationAndDisplay();
			// //tr.set_BG_train();
		}
	}

	public void addtrainOnRoute(M61_Train_On_Route trainOnRoute) {
		if (!this.getTrainsOnRoutes().contains(trainOnRoute)) {
			this.getTrainsOnRoutes().add(trainOnRoute);
		} else {
			// raise exception
		}
	}

	public List<M61_Train_On_Route> getTrainsOnRoutes() {
		return trainsOnRoutes;
	}

	public void update() {
		for (M61_Train_On_Route t_on_route : trainsOnRoutes) {
			// 3//99System.out.print("processing train no:" + er.getTrainNo());
			if (t_on_route.isMoving()) {

				t_on_route.update();
			}
		}
	}
	
	public void setMoveToSensorFlag(String TrainString,boolean trueFalse){
		M61_Train_On_Route train1 = M6_Trains_On_Routes.getTrainOnRoute(TrainString);
		train1.setToMoveToSensor(trueFalse);
	}
	
	public void updateToNextSensor(String TrainString, String SensorName, D_MyGraph graph){
		//get train
		
		
		
		M61_Train_On_Route train = M6_Trains_On_Routes.getTrainOnRoute(TrainString);
		
		//See whetehger it is relevant to update the train
		
		M76Stop sensor = M75Stops.getStop(SensorName);
		String sensorArc = sensor.getArc();
		
		//get appropriate end of train
		M43_TruckData_Display engine = train.getTruckPositions().get(0);
		M43_TruckData_Display tail = train.getTruckPositions().get(train.NumberItems()-1);
		M43_TruckData_Display relevantTruck;
		print("M6 updatePositionToNextSensor 1"+engine.getMovement());
		if(engine.getMovement().equals("same")){
			relevantTruck= engine;
		}else{
			relevantTruck = tail;
		}
		//get position of train in sectors and fraction of sectors (assume in same arc!)
		float positionOfTrain = relevantTruck.getDistance();
		//get position of sensor in sectors and fraction of sectors (assume in same arc!)
		
		
//		Need to move forward 1/2 an engine or truck to get to the head of the truck
		engine.createTailOfTruck();
		M43_TruckData_Display enginehead = engine.tailOfTruck;
		
		List<String[]> startArcPairList = enginehead.getStartArcPairList();
		int engineIndex = enginehead.getIndexOfStartArcPairList();
		enginehead.setStartArcPair(startArcPairList.get(engineIndex));
		//startArcPair = engine.getStartArcPair();  // maybe need startarcPair = startArcPairList.get(index);
		String[] startArcPair = enginehead.getStartArcPair();
		
		
		String engineIdent = U7_StartArcPairs.getIdentFromArcStringArray(startArcPair,graph);
		
		String engineArc = U7_StartArcPairs.getArcFromIdent(engineIdent);
		
		
		enginehead.setArc(engineArc);
		
		//check whether sensor is on route and if so get the index of the sensor on route
		int sensorIndex = U7_StartArcPairs.getobjectRouteIndex(enginehead.getRoute() , sensor.getArc(), graph, enginehead.getDirectionFacing() );
		if (sensorIndex < 0) {
			print("1 stopping sensor index = " + sensorIndex + " sensor not on route ");
			print("2 note sensor arc "+ sensor.getArc()+ " enginehead ident " + engineIdent + " engine arc " + enginehead.getArc() + " stopping !!!!!!!!!");
			return;
		}else{
			if(sensorIndex < engineIndex){
				print("1 stopping sensor index = " + sensorIndex + " engine index = " + engineIndex);
				print("2 note sensor arc "+ sensor.getArc()+ " engine ident " + engineIdent + " engine arc " + engine.getArc() + " stopping !!!!!!!!!");
				return;
			}else if (sensorIndex > engineIndex){
				print("1 updatePosition sensor index = " + sensorIndex + " engine index = " + engineIndex);
				print("2 note sensor arc "+ sensor.getArc()+ " engine ident " + engineIdent + " engine arc " + engine.getArc() + " updating !!!!!!!!!");
				train.updatePositionToNextSensor(SensorName);
				print(" the train should have jumped !!!!!!!!!!!!!!!!!!!!!!");
				return;
			}else{
				//continue
				print("continuing sensor index = " + sensorIndex + " engine index = " + engineIndex + " these should be the same");
				print("sensor arc "+ sensor.getArc()+ " engine ident " + engineIdent + " engine arc " + engine.getArc() + " continuing !!!!!");
				float positionOfSensor = sensor.getDistance();
				float distance = positionOfSensor-positionOfTrain;
				if(engine.getMovement().equals("same")){
					print("M6 updatePositionToNextSensor 2"+engine.getMovement()+" "+ distance);
					if(distance>0){
						train.updatePositionToNextSensor(SensorName);
					}else{
						print("distance > 0 not updating");
					}
				}else{
					print("M6 updatePositionToNextSensor 300000"+engine.getMovement()+" "+ distance);
					if(distance<0){
						train.updatePositionToNextSensor(SensorName);
					}else{
						print("distance < 0 not updating");
					}
				}
			}
		}
		
//		//continue here
//		if (!sensor.getArc().equals(engine.getArc())){
//			print("sensor arc "+ sensor.getArc()+ " engine ident " + engineIdent + " engine arc " + engine.getArc() + "  stopping !!!!!!!!!!!!!!!!!!");
//			//check whether sensor is on route and if so get the index of the sensor on route
//			return;
//		} else {
//			print("sensor arc "+ sensor.getArc()+ " engine ident " + engineIdent + " engine arc " + engine.getArc() + " continuing !!!!!!!!!!!!!!!!!!");
//		}
//			
		

		
	}
	
	protected static float getPositionFromEngineName(String EngineName){
		//M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute("T0");
		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute(EngineName);
		M43_TruckData_Display engine = train0.getTruckPositions().get(0);
		float distance = engine.getDistance();
		return distance;
	}
	
//	public void updateToNextSensor(String TrainString){
//		print("M6 updatePositionToNextSensor 1");
//		setMoveToSensorFlag(TrainString,true);
//		for (M61_Train_On_Route t_on_route : trainsOnRoutes) {
//			// 3//99System.out.print("processing train no:" + er.getTrainNo());
//			if (t_on_route.isToMoveToSensor()) {
//				print("M6 updatePositionToNextSensor 2");
//				t_on_route.updatePositionToNextSensor();
//			}
//		}
//		setMoveToSensorFlag(TrainString,false);
//	}

	public void generate_shortest_routes(D_MyGraph _DJG) {
		for (M62_train trainOnRoute : trainsOnRoutes) {
			// convert from 1_B_2_F-1-For,4_F_5_B-3-For
			// route = string
			// trainOnRoute.setParametersAndDisplay(_DJG,
			// trainOnRoute.getRouteStr());
			// includes below
			//
			//
			// // er.print_ep();
			// er.setHM(_HM);
			//
			// boolean startArcOnRoute =
			// er.get_headOfTrainData().setDataUsingTree(_DJG , er,
			// er.getStartArc(), er.getStartFraction(), er.getStartDirection());
			// if(startArcOnRoute){
			// //3//99System.out.print("train on route");
			// er.set_train_location(); // sets trainLocation in er
			// er.set_BG_Train();
			// }
			// else
			// {
			// //indicate that train is not on route
			////99//99System.out.print("train not on route");
			// }

		}
	}

}
