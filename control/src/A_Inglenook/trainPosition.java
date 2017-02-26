package A_Inglenook;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;

import mytrack.A_Setup;
import mytrack.C1_BranchGroup;
import mytrack.C2_DJGraph;
import mytrack.D_MyGraph;
import mytrack.K2_Route;
import mytrack.M43_TruckData_Display;
import mytrack.M4_TruckData;
import mytrack.M61_Train_On_Route;
import mytrack.M62_train;
import mytrack.M6_Trains_On_Routes;
import mytrack.M75Stops;
import mytrack.M76Stop;
import mytrack.N2_Time;
import mytrack.U4_Constants;
import mytrack.U7_StartArcPairs;
import net.Serial_IO;

public final class trainPosition {

	private static boolean DEBUG = true;
	private static void print(String x){
		if (DEBUG ){
			System.out.println(x);
		}
	}

	private static int esk = KeyEvent.VK_ESCAPE;

	private static int f9 = KeyEvent.VK_F9;
	private static int f10 = KeyEvent.VK_F10;
	private static int f11 = KeyEvent.VK_F11;
	private static int f12 = KeyEvent.VK_F12;

	private static int f1 = KeyEvent.VK_F1;
	private static int f2 = KeyEvent.VK_F2;
	private static int f3 = KeyEvent.VK_F3;
	private static int f4 = KeyEvent.VK_F4;

	private static int f5 = KeyEvent.VK_F5;
	private static int f6 = KeyEvent.VK_F6;
	private static int f7 = KeyEvent.VK_F7;
	private static int f8 = KeyEvent.VK_F8;

	private static C2_DJGraph graph;
	private static M6_Trains_On_Routes trainsOnRoute;

	private static C1_BranchGroup branchGroup;

	private static D_MyGraph DJG;

	static String stringFraction = "1.0";

	public void setGraph(D_MyGraph graph) {
		this.DJG = graph;
	}

	public static void settrainPositionParameters(C2_DJGraph graph, M6_Trains_On_Routes trainsOnRoute, C1_BranchGroup branchGroup) {

		trainPosition.graph = graph;
		trainPosition.DJG = graph.get_DJG();
		trainPosition.trainsOnRoute = trainsOnRoute;
		trainPosition.branchGroup = branchGroup;
	}

	public void setTrainsOnRoute(M6_Trains_On_Routes trainsOnRoute) {
		this.trainsOnRoute = trainsOnRoute;
	}

	public static void positionTrain(int fnkey){
		int result;

		M61_Train_On_Route train0 = M6_Trains_On_Routes.getTrainOnRoute("T0"); 
		if (fnkey == esk){
			String s1 = "f9 stop\nf10 start\nf11 start \nf12 stop \n";
			s1 = s1 + "f1 position Train Pointing Forwards Moving Forwards\n";
			s1 = s1 + "f2 position Train Pointing Forwards Moving Backwards\n";
			s1 = s1 + "f3 position Train Pointing Backwards Moving Forwards\n";
			s1 = s1 + "f4 position Train Pointing Backwards Moving Backwards\n";
			s1 = s1 + "f5 inglenook\n";
			s1 = s1 + "f6 move back and forth\n";
			s1 = s1 + "f7 testtrack\n";
			s1 = s1 + "f8 startFraction\n";
			msgBox(s1,"Menu");
			//			M6_Trains_On_Routes.movetrain("T0");

			//			N2_Time.initialise();
			//			train0.setMoving(true);

		}else if (fnkey == f9){
			//			msgBox("stop","f9");
			//			M6_Trains_On_Routes.movetrain("T0");
			N2_Time.initialise();
			train0.setMoving(true);

		}else if (fnkey == f10){
			//			setTrainParameters("T0", null, null);
			//			msgBox("position Train","F10");

			train0.setMoving(false);
		}else if (fnkey == f11){
			//			msgBox("move Train","F11");
			//			M6_Trains_On_Routes.movetrain("T0");
			N2_Time.initialise();
			train0.setMoving(true);


		}else if (fnkey == f12){
			//			msgBox("stop Train","F12");
			//			M6_Trains_On_Routes.stoptrain("T0");
			train0.setMoving(false);

		}else if (fnkey == f1){
			msgBox("position Train Pointing Forwards Moving Forwards",stringFraction);
			setTrainParameters("To", "For", "T0",0,stringFraction,noEngines,engineLength, noTrucks, truckLength);
//			public static K2_Route setTrainParameters(String fromTo, String ForRev, String trainString, int indexOfStartArcPairList, String startFraction,
//					String noEngines, float engineLength2, String noTrucks, float truckLength2){
			//set stop
//			M75Stops.addStopPosition2(DJG, arc, stringFraction, directionFacing, stopLength, stopStr)

			
			
			
//			public static void addStopPosition2(D_MyGraph graph, String arc, String fraction, String directionFacing, float stopLength, String stopStr){
			
			//move train checking for stop
		}else if (fnkey == f2){
			msgBox("position Train Pointing Forwards Moving Backwards",stringFraction);
			setTrainParameters("From", "Rev", "T0",0,stringFraction,noEngines,engineLength, noTrucks, truckLength);
		}else if (fnkey == f3){
			msgBox("position Train Pointing Backwards Moving Forwards",stringFraction);
			setTrainParameters("To", "For", "T0", 0,stringFraction,noEngines,engineLength, noTrucks, truckLength);
		}else if (fnkey == f4){
			msgBox("position Train Pointing Backwards Moving Backwards",stringFraction);
			setTrainParameters("From", "Rev", "T0",0,stringFraction,noEngines,engineLength, noTrucks, truckLength);
		}else if (fnkey == f5){
			msgBox("inglenook","f5");
			Long init = 104273685l;
			Inglenook.runInglenook2(init,graph,branchGroup);
		}else if (fnkey == f6){
			msgBox("movebackandforth","f6");
		}else if (fnkey == f7){
			msgBox("testtrack","f7");
		}else if (fnkey == f8){

			stringFraction = JOptionPane.showInputDialog("StringFraction");
		}
		//branchGroup.set_BG_train();
	}
	
	public static void generateFractionFromArcAndDistance(String arc, int indexOfStartArcPairList, String startFraction){
		
	}
	
//	public static void addStopPosition2(D_MyGraph graph, String arc, String fraction, String directionFacing, float stopLength, String stopStr){
////		arc = "2_F_3_B";
////		fraction = "2.1";
////		directionFacing = "Rev";
//
//		M76Stop stop_display1f1 = new M76Stop(arc, directionFacing, graph, fraction, false);
//		stop_display1f1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
//
//
////		stopLength = 0.5f*U4_Constants.scalefactor;
//		stopLength = stopLength*U4_Constants.scalefactor;
//		stopStr = "S1F1";
//		stop_display1f1.set_BG(stopLength, stopStr, stopStr, "Stop");
//		stop_display1f1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
//		//trainsOnRoute.addtrainOnRoute(route1);
//		addstop(stop_display1f1);
//	}


	private static void msgbox(String string) {

		msgBox(string,"alert");

	}

	public static void main(String[] argv) throws Exception {
		int i = okcancel("Are you sure ?","alert");
		System.out.println("ret : " + i);
		System.out.println(JOptionPane.YES_OPTION);
	}

	public static int okcancel(String theMessage, String titleBar) {
		int result = JOptionPane.showConfirmDialog(null, theMessage, titleBar, JOptionPane.YES_NO_OPTION);

		return result;
	}

	public static void ok(String theMessage ){
		if(okcancel(theMessage, "alert")== JOptionPane.YES_OPTION){

		}
	}

	public static void msgBox(String infoMessage, String titleBar)
	{
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}


	public static K2_Route setTrainParameters(String fromTo, String ForRev, String trainString, int indexOfStartArcPairList, String startFraction,
			String noEngines, float engineLength2, String noTrucks, float truckLength2){
		
		Integer numberEngines = Integer.valueOf(noEngines);
		Integer numberTrucks = Integer.valueOf(noTrucks);
//
//		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute(trainString);  //trainString = "T0" say
//		train0.setNumberEngines(numberEngines);
//		train0.setNumberTrucks(numberTrucks);
		
		K2_Route route = setRoute(fromTo, ForRev);
		//int indexOfStartArcPairList = 2;
		
		String directionFacing = "";
		
		positionTrainOnRoute(trainString, route, indexOfStartArcPairList, startFraction, directionFacing, numberEngines, engineLength2, numberTrucks, truckLength2);
		return route;
	}

	static K2_Route setRoute(String fromTo, String ForRev) {
		K2_Route route = null;

		if (ForRev.equals("For")){
			
			if (fromTo.equals("From")){
				route = Inglenook.routetFromFor;
				
			}else{
				route = Inglenook.routetToFor;
			}
		}else if (ForRev.equals("Rev")){
			if (fromTo.equals("From")){
				route = Inglenook.routetFromRev;
			}else{
				route = Inglenook.routetToRev;
			}
		}else if (ForRev.equals("For1")){
			
			if (fromTo.equals("From1")){
				route = Inglenook.routeh1;
				route = Inglenook.routeFromFor1;
				
			}else{
				route = Inglenook.route1h;
				route = Inglenook.routeToFor1;
				
			}
		}else if (ForRev.equals("Rev1")){
			
			if (fromTo.equals("From1")){
				route = Inglenook.route1h;
				//route = Inglenook.routeFromRev1;
				
			}else{
				route = Inglenook.routeh1;
				//route = Inglenook.routeToRev1;
				
			}
		}else if (ForRev.equals("For2")){
			
			if (fromTo.equals("From2")){
				route = Inglenook.routeh2;
				route = Inglenook.routeFromFor2;
				
			}else{
				route = Inglenook.route2h;
				route = Inglenook.routeToFor2;
				
			}
		}else if (ForRev.equals("Rev2")){
			
			if (fromTo.equals("From2")){
				route = Inglenook.route2h;
				route = Inglenook.routeFromRev2;
				
			}else{
				route = Inglenook.routeh2;
				route = Inglenook.routeToRev2;
				
			}
		}
		return route;
	}

	public static void positionTrainOnRoute(String trainString, K2_Route route, int indexOfStartArcPairList, String startFraction, String directionFacing, int numberEngines, float engineLength, int numberTrucks, float truckLength) {
		/*
		 * to do with route. this code should be in route
		 */
		print(trainString + " " + route.hashCode() + " " + startFraction + " " + directionFacing + " " + numberEngines + " " + numberTrucks);

		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute(trainString);  //trainString = "T0" say
		train0.setNumberEngines(numberEngines);
		train0.setNumberTrucks(numberTrucks);
		train0.setEngineLength(engineLength);
		train0.setTruckLength(truckLength);
		
		route.setup();
/*
 * does this
 */ 			route.setRoute(route.convertRouteToEngineRoute(route.getStrRoute()));
			LinkedList<String[]> routePairs = K2_Route.generateEngineRoutePairs(route.getRoute());
		
			List<List<String[]>> routePath = route.generateEngineRoutePath(routePairs, DJG);
			int routePairIndex = 0;
			List<String[]> startArcPairList = routePath.get(routePairIndex);
		//String[] startArcPair = k2_route.getRoutePairs().get(startArcPairIndex);
		//List<String[]> startArcPairList = route.getStartArcPairList();
		//int indexOfStartArcPairList = route.getIndexOfStartArcPairList();
		String[] startArcPair = startArcPairList.get(indexOfStartArcPairList);
		String startArc = U7_StartArcPairs.getIdentFromArcStringArray(startArcPair , graph.get_DJG());
		String arc = U7_StartArcPairs.getArcFromIdent(startArc);
		String direction =  U7_StartArcPairs.getDirectionFromIdent(startArc);
		String[] arcStringArray = DJG.getIdentToArcStringArrayMap().get(startArc);
		String routePairKey = route.getEngineRoutePairKey(arcStringArray);
		String orientation = DJG.getArcStringArrayKeyToTrainOrientationMap().get(routePairKey);
		String movement = DJG.getArcStringArrayKeyToTrainMovementMap().get(routePairKey);
		
		
		
		

//		int startArcPairIndex=0;
//
//		String[] startArcPair = route.getRoutePairs().get(startArcPairIndex);
//		String startArc = U7_StartArcPairs.getIdentFromArcStringArray(startArcPair, DJG);
//		String arc = U7_StartArcPairs.getArcFromIdent(startArc);
//		String direction =  U7_StartArcPairs.getDirectionFromIdent(startArc);
//		String[] arcStringArray = DJG.getIdentToArcStringArrayMap().get(startArc);
//		String routePairKey = route.getEngineRoutePairKey(arcStringArray);
//		String orientation = DJG.getArcStringArrayKeyToTrainOrientationMap().get(routePairKey);
//		String movement = DJG.getArcStringArrayKeyToTrainMovementMap().get(routePairKey);
		//String startArc = startArc = "1_F_2_B";

		//String startDirection = "Rev";		 //changed rev to for

		//set route.startArcPair 
		
		/*
		 * to do with train. this code should be in train
		 */
		
		M43_TruckData_Display engine = train0.getTruckPositions().get(0);
		
//		M43_TruckData_Display truck = new M43_TruckData_Display(arc, direction, DJG, trainCoupling);
//		train0.truckPositions.add(truck);
		//train0.set_truck_locations(engine, route, arc, direction, DJG, startFraction, trainCoupling);
		int noSegments = engine.gettArc()._TrackSegments.size();     //is 5

		//engine.setSegmentNo(noSegments-engine.getSegmentNo()-1); 
		int segmentNoChosen = 1;

		if(noSegments<segmentNoChosen){
			segmentNoChosen = noSegments;
		}
		route.setIndexOfRoutePath(0);
		route.indexOfStartArcPairList= 0;
		engine.setRoute(route);
		/*
		 * does following
		 * 	
		this.route = route;
		this.routePath=route.getRoutePath();
		this.indexOfRoutePath=route.indexOfRoutePath;
		this.routePairs=route.getRoutePairs();
		this.indexOfStartArcPairList=route.indexOfStartArcPairList;
		this.startArcPairList=route.startArcPairList;	
		this.startArcPair=route.startArcPair;
		 * 
		 */
		engine.setSegmentNo(segmentNoChosen);
		//String startFraction = "3.1";

		engine.setStartArcPair(startArcPair);
		engine.setArc(arc);
		engine.setDirectionFacing(direction);
		//engine. = arcStringArray;
		//engine.arcPairKey = routePairKey;
		engine.setOrientation(orientation);
		engine.setMovement(movement);
		engine.setDirectionFacing(directionFacing);
		engine.set_train_location(startArcPair, startFraction);


		train0.reset_truck_locations(0);
	}

	

	private static int one = KeyEvent.VK_1;
	private static int two = KeyEvent.VK_2;
	private static int three = KeyEvent.VK_3;
	private static int four = KeyEvent.VK_4;
	private static int five = KeyEvent.VK_5;
	private static int six = KeyEvent.VK_6;
	private static int seven = KeyEvent.VK_7;
	private static int eight = KeyEvent.VK_8;
	private static int nine = KeyEvent.VK_9;

	private static String noEngines = "1";
	private static String noTrucks = "1";
	
	private static float  engineLength =  U4_Constants.enginelength;
	private static float  truckLength =  U4_Constants.trucklength;

	private static K2_Route route;

	

	public static void StandardMove(int fnkey) {
		if (fnkey == esk){
			String s1 = "";
			s1 = s1 + "1 Number of engines\n";
			s1 = s1 + "2 Number of Trucks\n";
			s1 = s1 + "3 startFraction\n";
			s1 = s1 + "4 add trucks\n";
			s1 = s1 + "5 remove trucks\n";
			s1 = s1 + "6 position train 1h\n";
			s1 = s1 + "7 position train h1\n";
			s1 = s1 + "8 position train \n";
			s1 = s1 + "9 position train \n";
			msgBox(s1,"Menu");
		}else if (fnkey == one){
			noEngines = JOptionPane.showInputDialog("No Engines");
		}else if (fnkey == two){
			noTrucks = JOptionPane.showInputDialog("No Trucks");
		}else if (fnkey == three){
			String startFraction = JOptionPane.showInputDialog("startFraction");
		}else if (fnkey == four){
//			noTrucks = JOptionPane.showInputDialog("No Trucks to add");
			M61_Train_On_Route train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
			M61_Train_On_Route train2 = M6_Trains_On_Routes.getTrainOnRoute("T5");
//			Integer noTrucksInt = Integer.getInteger(noTrucks);
			int noTrucksInt = 1;
			A_Inglenook.MoveTrainUsingDeque.decoupleFrom1CoupleTo2(train2, train1, noTrucksInt);
			train1.reset_truck_locations(0);
			train2.reset_truck_locations(0);
//			train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
//			train2 = M6_Trains_On_Routes.getTrainOnRoute("T5");
		}else if (fnkey == five){
//			noTrucks = JOptionPane.showInputDialog("No Trucks to add");
			M61_Train_On_Route train1 = M6_Trains_On_Routes.getTrainOnRoute("T5");
			M61_Train_On_Route train2 = M6_Trains_On_Routes.getTrainOnRoute("T0");
			int noTrucksInt = 1;
			A_Inglenook.MoveTrainUsingDeque.decoupleFrom1CoupleTo2(train2, train1, noTrucksInt);
			train1.reset_truck_locations(0);
			train2.reset_truck_locations(0);
//			train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
//			train2 = M6_Trains_On_Routes.getTrainOnRoute("T5");
//			Integer noTrucksInt = Integer.getInteger(noTrucks);

		}else if (fnkey == six){
			msgBox("position Train Pointing Forwards Moving Forwards",stringFraction);
			setTrainParameters("From1", "Rev1", "T0",0,stringFraction,noEngines,engineLength,noTrucks,truckLength);
		}else if (fnkey == seven){
			msgBox("position Train Pointing Forwards Moving Forwards",stringFraction);
			setTrainParameters("To1", "Rev1", "T0",0,stringFraction,noEngines,engineLength,noTrucks,truckLength);
		}else if (fnkey == eight){
			msgBox("position Train Pointing Forwards Moving Forwards",stringFraction);
			setTrainParameters("From1", "For1", "T0",0,stringFraction,noEngines,engineLength,noTrucks,truckLength);
		}else if (fnkey == nine){
			msgBox("position Train Pointing Forwards Moving Forwards",stringFraction);
			setTrainParameters("To1", "For1", "T0",0,stringFraction,noEngines,engineLength,noTrucks,truckLength);
		}

	}

	public static void altpositionTrain(int fnkey) {
		int result;

		M61_Train_On_Route train0 = M6_Trains_On_Routes.getTrainOnRoute("T0"); 
		if (fnkey == esk){
			String s1 = "f9 stop\nf10 start\nf11 start \nf12 stop \n";
			s1 = s1 + "f1 position Train Pointing Forwards Moving Forwards\n";
			s1 = s1 + "f2 position Train Pointing Forwards Moving Backwards\n";
			s1 = s1 + "f3 position Train Pointing Backwards Moving Forwards\n";
			s1 = s1 + "f4 position Train Pointing Backwards Moving Backwards\n";
			s1 = s1 + "f5 inglenook\n";
			s1 = s1 + "f6 move back and forth\n";
			s1 = s1 + "f7 testtrack\n";
			s1 = s1 + "f8 startFraction\n";
			msgBox(s1,"Menu");
			//			M6_Trains_On_Routes.movetrain("T0");

			//			N2_Time.initialise();
			//			train0.setMoving(true);

		}else if (fnkey == f9){
			//			msgBox("stop","f9");
			//			M6_Trains_On_Routes.movetrain("T0");
			N2_Time.initialise();
			train0.setMoving(true);

		}else if (fnkey == f10){
			//			setTrainParameters("T0", null, null);
			//			msgBox("position Train","F10");

			train0.setMoving(false);
		}else if (fnkey == f11){
			//			msgBox("move Train","F11");
			//			M6_Trains_On_Routes.movetrain("T0");
			N2_Time.initialise();
			train0.setMoving(true);


		}else if (fnkey == f12){
			//			msgBox("stop Train","F12");
			//			M6_Trains_On_Routes.stoptrain("T0");
			train0.setMoving(false);

		}else if (fnkey == f1){
			msgBox("position Train Pointing Forwards Moving Forwards",stringFraction);
			route = setTrainParameters("To2", "For2", "T0", 0, stringFraction,noEngines,engineLength, noTrucks, truckLength);
		}else if (fnkey == f2){
			msgBox("position Train Pointing Forwards Moving Backwards",stringFraction);
			route = setTrainParameters("From2", "Rev2", "T0", 2, stringFraction,noEngines,engineLength, noTrucks, truckLength);
		}else if (fnkey == f3){
			msgBox("position Train Pointing Backwards Moving Forwards",stringFraction);
			route = setTrainParameters("To2", "For2", "T0", 0, stringFraction,noEngines,engineLength, noTrucks, truckLength);
		}else if (fnkey == f4){
			msgBox("position Train Pointing Backwards Moving Backwards",stringFraction);
			route = setTrainParameters("From2", "Rev2", "T0", 2, stringFraction,noEngines,engineLength, noTrucks, truckLength);
		}else if (fnkey == f5){
			msgBox("inglenook","f5");
			Long init = 104273685l;
			Inglenook.runInglenook2(init,graph,branchGroup);
		}else if (fnkey == f6){
			msgBox("movebackandforth","f6");
		}else if (fnkey == f7){
			msgBox("testtrack","f7");
		}else if (fnkey == f8){
			stringFraction = JOptionPane.showInputDialog("StringFraction");
		}else if (fnkey == f9){
			msgBox("move opposite direction","f9");
//			String strFromBranch = "sth";
//			String strToBranch = "st2";
			if (route == null) {
				route = setRoute("From2", "For2");
			}	
//			route.setRoute(route.convertRouteToEngineRoute(route.getStrRoute()));
//			LinkedList<String[]> routePairs = K2_Route.generateEngineRoutePairs(route.getRoute());
//		
//			List<List<String[]>> routePath = route.generateEngineRoutePath(routePairs, DJG);
//			int routePairIndex = 1;
//			List<String[]> startArcPairList = routePath.get(0);
//			//String[] startArcPair = k2_route.getRoutePairs().get(startArcPairIndex);
//			//List<String[]> startArcPairList = route.getStartArcPairList();
//			int indexOfStartArcPairList = train0.getTruckData(0).getIndexOfStartArcPairList();
//			
//			String[] startArcPair = startArcPairList.get(indexOfStartArcPairList);
			String[] startArcPair = train0.getTruckData(0).getStartArcPair();
			String startArc = U7_StartArcPairs.getIdentFromArcStringArray(startArcPair , graph.get_DJG());
//			String arc = U7_StartArcPairs.getArcFromIdent(startArc);
			String direction =  U7_StartArcPairs.getDirectionFromIdent(startArc);
//			String[] arcStringArray = DJG.getIdentToArcStringArrayMap().get(startArc);
//			String routePairKey = route.getEngineRoutePairKey(arcStringArray);
//			String orientation = DJG.getArcStringArrayKeyToTrainOrientationMap().get(routePairKey);
//			String movement = DJG.getArcStringArrayKeyToTrainMovementMap().get(routePairKey);
			train0.getTruckPositions().get(0).swapDirectiontravelling(route, CreateTrainMovementDeque.graph,direction);
			//MoveTrainUsingDeque.swapRouteOppDirection(strFromBranch, strToBranch,direction );
		}
		
	}
}
