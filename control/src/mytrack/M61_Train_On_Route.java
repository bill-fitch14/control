package mytrack;

import java.awt.Color;
import java.awt.Container;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

import sm2.Main;
import A_Inglenook.MoveTrainUsingDeque;

public class M61_Train_On_Route extends M62_train{
	
	//we need a train, a route and a truckData-Position
	// and use this to create a M3_DisplayTrain
	
	// we put the train at the specified location on the route and move

	//private M62_train train;
	private String routeStr;
	//private L2_TrackLocation location;
	//private L3_DisplayTrain displayTrain;
	//private M3_DisplayTrain dispTrain;
	private D_MyGraph graph;
	//private M2_move_train moveTrain;
	
	private K2_Route k2_route;

//	M61_Train_On_Route(D_MyGraph DJG,M50_train train, String route, L2_TrackLocation location) {
//		this.DJG = DJG;
//		//this.train = train;
//		this.myroute.setParameters(DJG, route);
//		this.location = location;
//		setTrainLocationAndDisplay();
//	}
	
	//Movement Routines
	
	private boolean moving = false;
	private boolean ToMoveToSensor = false;
	
	
//	public startTrain(){
//		this.moving = true;
//		for ( M43_TruckData_Display truckPosition:getTruckPositions()){
//			if (truckPosition.update(distance) == false) {
//				this.moving = true;
//				
//			}
//			//			}
//			
//		}
//	}

	public boolean update(){

		float distance = (float) N2_Time.getDistance(getTrainSpeed());
		//System.out.print("updating " + this.moving);
		//boolean moving = true;
		//this.moving = true;
		if (this.isMoving() == true){
			
			for (M43_TruckData_Display truckPosition:getTruckPositions()){
				if(truckPosition.update(distance) == false) {
					this.setMoving(false);
//					try {
//					    Thread.sleep(5000);
//					} catch(InterruptedException ex) {
//					    Thread.currentThread().interrupt();
//					}
//					this.moving = true;
				}
			}

			if(this.isMoving() == false){
				MoveTrainUsingDeque.readDeque();
			}

		}

		return isMoving();
	}
	
	public void updatePositionToNextSensor()
	{
		boolean SensorReached = false;
		while (SensorReached = false){
			float distance = 0.1f;
			for (M43_TruckData_Display truckPosition:getTruckPositions()){
				boolean SensorReachedCurrentTruck = truckPosition.updateToSensor(distance); 
				if (SensorReachedCurrentTruck == true){
					SensorReached = true; 
				}
			}
		}
	}

	public M61_Train_On_Route(String trainStr, int trainNo, String strEngineColor,
			float engineLength, int numberTrucks, float truckLength,
			Integer[] truckNames, String strRoute, String arc,
			String startFraction, String directionFacing, String trainCoupling, D_MyGraph graph) {
		
		//set the distances from head
		super(trainStr, trainNo, strEngineColor, 1, engineLength, numberTrucks, truckLength, truckNames, trainCoupling);
		
		M43_TruckData_Display truck_display = new M43_TruckData_Display(arc, directionFacing, graph, startFraction, trainCoupling);
		
		K2_Route route = new K2_Route(strRoute, graph,null);
		
		if(!objectIsOnRoute(route, truck_display, graph)){
			//99System.out.print("train is not on route");
			if(!objectIsOnRoute(route, truck_display, graph)){
				//99System.out.print("train is not on route");
				return;
			}
			
			return;
		}
		truck_display.setRoute(route);
		truck_display.setArcPairList(route);
		
		this.set_truck_locations(truck_display, route, arc, directionFacing, graph, startFraction, trainCoupling);
		

		//dispTrain = new M3_DisplayTrain(train, route, arc, directionFacing, graph, startFraction);
		
		//dispTrain = new M3_DisplayTrain(train, graph, route, headOfTrain);
	
		//			train.setTrainStr(trainStr);
		//			train.setTrainNo(trainNo);
		//			train.setEngineColor(engineColor);
		//			train.setEngineLength(engineLength);
		//			train.setNumberTrucks(numberTrucks);
		//			train.setTruckLength(truckLength);
		//			train.setTruckNames(truckNames);
		//setParametersAndDisplay(graph, route );
		
//		location.setArc(startArc);
//		location.setstrFraction(startstrFraction);
//		location.setDirection(startDirection);
	}

	//setup routines	

//	public void setParametersAndDisplay(D_MyGraph DJG,  K2_Route route ){
//		
//		if(objectIsOnRouteSetPosition(EngineRoutePath)){
//			dispTrain.set_train_location(null, route);	// given the engine position, generates the positions of the trucks	
//		}
//	}
	




	
	boolean objectIsOnRoute(K2_Route route, M41_TruckData_Position truckdata_position, D_MyGraph graph){

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
		String[] startArcStringArray=U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray2=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray3=U7_StartArcPairs.getStringArraySameDirectionTravellingOppDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray4=U7_StartArcPairs.getStringArrayOppDirectionTravellingOppDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		


		/*
		 * Get all the positions on the path and check if any of the above stringArrays are on the route
		 * Should be startArcStringArray, but if any of the others then give error message and continue
		 */

		for (int i = 0; i < route.getRoutePath().size(); i++) {
			route.startArcPairList = route.getRoutePath().get(i);
			route.indexOfRoutePath = i;
			
			   //check this
			
			//3//99System.out.print("index of startRoute = " + indexOfStartRoute);
			for (int j = 0; j < route.startArcPairList.size(); j++) {
				
				String[] ArcStringArray = route.startArcPairList.get(j);
				/*
				 * Only set the remainder of the data if the engine lies on the route
				 */
				//99System.out.print("size "+route.startArcPairList.size()+" Hi "+ j + Arrays.toString(ArcStringArray) + " " + Arrays.toString(startArcStringArray));
				if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray))){

					//get the index corresponding to the list of arcs
					route.indexOfStartArcPairList = j;
					route.startArcPair = route.startArcPairList.get(route.indexOfStartArcPairList);
//					route.indexOfStartArcPairList = j;
//					route.setIndexOfStartArcPairList(j);
//					truckdata_position.setIndexOfStartArcPairList(j);
//					
//					String[] startArcPair = truckdata_position.getStartArcPairList().get(indexOfStartArcPairList);
//					route.setStartArcPair(startArcPair);
//					truckdata_position.setStartArcPair(startArcPair);
					
					String startArcPairKey = getEngineRoutePairKey(truckdata_position.getStartArcPair());
					String truckOrientation = graph.getOrientation(startArcPairKey);
					String truckMovement = graph.getMovement(startArcPairKey);
					return true;

				} else {
					if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray2))){
						//99System.out.print("OppDirectionTravelling");
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
	
	public boolean swapDirectiontravelling(K2_Route routeInOppDirTravelling, M41_TruckData_Position truckdata_position, D_MyGraph graph){

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
		String[] startArcStringArray=U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray2=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray3=U7_StartArcPairs.getStringArraySameDirectionTravellingOppDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray4=U7_StartArcPairs.getStringArrayOppDirectionTravellingOppDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		


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
				//99System.out.print("size "+routeInOppDirTravelling.startArcPairList.size()+" Hi "+ j + Arrays.toString(ArcStringArray) + " " + Arrays.toString(startArcStringArray2));
				if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray2))){

					//get the index corresponding to the list of arcs
					routeInOppDirTravelling.indexOfStartArcPairList = j;
					routeInOppDirTravelling.startArcPair = routeInOppDirTravelling.startArcPairList.get(routeInOppDirTravelling.indexOfStartArcPairList);
//					route.indexOfStartArcPairList = j;
//					route.setIndexOfStartArcPairList(j);
//					truckdata_position.setIndexOfStartArcPairList(j);
//					
//					String[] startArcPair = truckdata_position.getStartArcPairList().get(indexOfStartArcPairList);
//					route.setStartArcPair(startArcPair);
//					truckdata_position.setStartArcPair(startArcPair);
					
					String startArcPairKey = getEngineRoutePairKey(truckdata_position.getStartArcPair());
					String truckOrientation = graph.getOrientation(startArcPairKey);
					String truckMovement = graph.getMovement(startArcPairKey);
					return true;

				} else {
					//99System.out.print("new route is wrong ");
					int a = 1;
					if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray))){
						//99System.out.print("SameDirectionTravelling");
						return false;
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


	public String getEngineRoutePairKey(String[] engineRoutePair) {

		String erpkey = Arrays.deepToString(engineRoutePair);
		return erpkey;
	}
	
	

	
	

	

	public D_MyGraph getDJG() {
		return graph;
	}



	/**
	 * @param _DJG 
	 * @param _DJG
	 * @param myroute
	 * @return 
	 */
	public List<List<String[]>> convertRouteToRoutePath(String route, D_MyGraph _DJG) {
		LinkedList<String> EngineRoute = this.convertRouteToEngineRoute(route);
		List<String[]>  EngineRoutePairs = this.generateEngineRoutePairs( EngineRoute);
		List<List<String[]>> EngineRoutePath = this.generateEngineRoutePath(_DJG, EngineRoutePairs);
		return EngineRoutePath;
	}	
	
	public LinkedList<String> convertRouteToEngineRoute(String route) {

		// route is of the form 1_F_To_For,2_B_To_For
		String[] r = route.split(",");
		LinkedList<String> EngineRoute = new LinkedList<String>(Arrays.asList(r));;
		return EngineRoute;
	}

	
	public List<String[]> generateEngineRoutePairs(LinkedList<String> EngineRoute) {
		List<String[]> EngineRoutePairs = new LinkedList<String[]>();
		for (int i = 0; i < EngineRoute.size() - 1; i++) {
			String routeFrom = EngineRoute.get(i);
			String routeTo = EngineRoute.get(i + 1);
			String[] erp = { routeFrom, routeTo };
			EngineRoutePairs.add(erp);
		}
		return EngineRoutePairs;
	}
	
	public List<List<String[]>> generateEngineRoutePath(D_MyGraph DJG, List<String[]> EngineRoutePairs) {

		List<List<String[]>> EngineRoutePath = new LinkedList<List<String[]>>();
		for (String[] erp : EngineRoutePairs) {
			// 3//99System.out.print("Engine route pair: " +
			// Arrays.deepToString(erp));
			getDJG().ShortestDistance(erp);
			// generate a list of weighted edges for each engine route pair
			List<DefaultWeightedEdge> edgeListDWE = DJG.get_shortestPathList();

			List<String[]> edgeStrList = new LinkedList<String[]>();
			// turn each of the Weighted edges into a String[] pair containing a
			// From and To item
			for (DefaultWeightedEdge item : edgeListDWE) {
				String[] edge_pair = item.toString().replace("(", "")
						.replace(")", "").split(" : ");
				edgeStrList.add(edge_pair);
			}
			// Store the result in EngineRoutePath
			EngineRoutePath.add(edgeStrList);
		}
		return EngineRoutePath;
	}



	public String getRouteStr() {
		return routeStr;
	}



	public void setRouteStr(String routeStr) {
		this.routeStr = routeStr;
	}

	public K2_Route getK2_route() {
		return k2_route;
	}

	public void position(String fromBranch, String toBranch) {
		
		M43_TruckData_Display firstTruck = this.getTruckPositions().get(0);
		firstTruck.reposition();
		
		switch(fromBranch){
		case "sth":
			switch(toBranch){
			case "st1":
				
				break;
			case "st2":
				break;
			case "st3":
				break;
				
			}
		case "st1":
			break;
		case "st2":
			break;
		case "st3":
			break; 
		}
		
	}

	public boolean isMoving() {
		return this.moving;
	}

	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
		
		if(isMoving) {
			setEngineSpeed(U4_Constants.getHspeed());
			//wait 1 sec

		}else{
			setEngineSpeed(0);
		}
		
	}

	private void setEngineSpeed(double engineSpeed) {
		Main.lo.setEngineSpeed(engineSpeed);
	}

	public boolean isToMoveToSensor() {
		// TODO Auto-generated method stub
		return ToMoveToSensor;
	}



}
