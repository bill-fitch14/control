package A_Inglenook;

import java.awt.event.KeyEvent;
import java.util.Iterator;
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
			setTrainParameters("To", "For", "T0",stringFraction,noEngines,noTrucks);
		}else if (fnkey == f2){
			msgBox("position Train Pointing Forwards Moving Backwards",stringFraction);
			setTrainParameters("From", "Rev", "T0",stringFraction,noEngines,noTrucks);
		}else if (fnkey == f3){
			msgBox("position Train Pointing Backwards Moving Forwards",stringFraction);
			setTrainParameters("To", "For", "T0", stringFraction,noEngines,noTrucks);
		}else if (fnkey == f4){
			msgBox("position Train Pointing Backwards Moving Backwards",stringFraction);
			setTrainParameters("From", "Rev", "T0",stringFraction,noEngines,noTrucks);
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


	protected static void setTrainParameters(String fromTo, String ForRev, String trainString, String startFraction,
			String noEngines, String noTrucks){
		
		Integer numberEngines = Integer.valueOf(noEngines);
		Integer numberTrucks = Integer.valueOf(noTrucks);

		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute(trainString);  //trainString = "T0" say
		train0.setNumberEngines(numberEngines);
		train0.setNumberTrucks(numberTrucks);
		
		K2_Route route;
		if (ForRev.equals("For")){
			if (fromTo.equals("From")){
				route = Inglenook.routetFromFor;
			}else{
				route = Inglenook.routetToFor;
			}
		}else{
			if (fromTo.equals("From")){
				route = Inglenook.routetFromRev;
			}else{
				route = Inglenook.routetToRev;
			}
		}

		int startArcPairIndex=0;

		String[] startArcPair = route.getRoutePairs().get(startArcPairIndex);
		String startArc = U7_StartArcPairs.getIdentFromArcStringArray(startArcPair, DJG);
		String arc = U7_StartArcPairs.getArcFromIdent(startArc);
		String direction =  U7_StartArcPairs.getDirectionFromIdent(startArc);
		String[] arcStringArray = DJG.getIdentToArcStringArrayMap().get(startArc);
		String routePairKey = route.getEngineRoutePairKey(arcStringArray);
		String orientation = DJG.getArcStringArrayKeyToTrainOrientationMap().get(routePairKey);
		String movement = DJG.getArcStringArrayKeyToTrainMovementMap().get(routePairKey);
		//String startArc = startArc = "1_F_2_B";

		//String startDirection = "Rev";		 //changed rev to for

		//set route.startArcPair 
		M43_TruckData_Display engine = train0.getTruckPositions().get(0);
		String trainCoupling = "tail";
//		M43_TruckData_Display truck = new M43_TruckData_Display(arc, direction, DJG, trainCoupling);
//		train0.truckPositions.add(truck);
		//train0.set_truck_locations(engine, route, arc, direction, DJG, startFraction, trainCoupling);
		int noSegments = engine.gettArc()._TrackSegments.size();     //is 5

		//engine.setSegmentNo(noSegments-engine.getSegmentNo()-1); 
		int segmentNoChosen = 2;

		if(noSegments<segmentNoChosen){
			segmentNoChosen = noSegments;
		}
		engine.setSegmentNo(segmentNoChosen);
		//String startFraction = "3.1";

		engine.setStartArcPair(startArcPair);
		engine.setArc(arc);
		engine.setDirectionFacing(direction);
		//engine. = arcStringArray;
		//engine.arcPairKey = routePairKey;
		engine.setOrientation(orientation);
		engine.setMovement(movement);
		engine.set_train_location(startArcPair, startFraction);


		train0.reset_truck_locations(0);

	}

	private static void positionTrain2(D_MyGraph graph, int NoTrucks, String trainStr, M6_Trains_On_Routes trainsOnRoute) {

		String strEngineLength = null;
		String strNoTrucks = null;
		Integer[] truckNames = null;
		String strTruckLength = null;
		String route = null;
		String showTrains = null;
		String startFraction = null;
		String startArc = null;
		String startDirection = null;
		int trainNo = 0;

		String straight;
		String strEngineColor = null;





		float engineLength;
		float truckLength;
		String trainName = "sth";
		if(trainName.equals("sth")){
			trainNo = 0;
		}else{
			trainNo = Integer.parseInt(trainName.substring(2));
		}

		strEngineColor = "red";
		if (trainName != "sth") {
			strEngineLength = "0";
			engineLength = 0;
		} else {
			strEngineLength = "4";
			engineLength = 4;
		}
		strNoTrucks = Integer.toString(NoTrucks);
		int noTrucks = NoTrucks;
		int numberTrucks=NoTrucks;


		//		strNoTrucks = "4";
		//		noTrucks = 4;
		//		numberTrucks = 4;

		truckNames = null;

		truckNames = new Integer[8];



		//Commented this out!!!!		
		//		Integer i = 0;
		//		for (Iterator iterator = deque.iterator(); iterator.hasNext();) {
		//			Integer tn[] = (Integer[]) iterator.next();
		//			//99System.out.print(i+"   "+tn[0]);
		//			truckNames[i+1] = tn[0];
		//			i++;
		//		}




		strTruckLength = "1";
		//		String trainStr;
		int trainNumber;
		M61_Train_On_Route route31;
		M61_Train_On_Route route21;
		M61_Train_On_Route route1;
		M61_Train_On_Route route2;
		String traincoupling;
		switch (trainName) {

		case "sth":


			//			strTruckLength = "1";
			//			truckLength = 1;
			//			route = "2_To_Rev,5_To_Rev";
			//			startArc = "4_F_5_B";
			//			startFraction = "1.5";
			//			startDirection = "Rev";
			//			trainStr = "T0";

			strTruckLength = "0.5";

			//			truckLength = 1;
			route = "1_To_Rev,3_To_Rev";  //changed rev to for
			startArc = "1_F_2_B";
			startFraction = "0.1";
			startDirection = "Rev";		 //changed rev to for
			trainStr = "T0";
			trainNo = 4; 

			int numberEngines = 1; 
			engineLength = U4_Constants.enginelength*U4_Constants.scalefactor; 
			truckLength = U4_Constants.trucklength*U4_Constants.scalefactor; 			


			traincoupling = "tail";
			route21 = new M61_Train_On_Route( trainStr,  trainNo,  strEngineColor,
					engineLength,  numberTrucks,  truckLength,
					truckNames,  route,  startArc,
					startFraction,  startDirection, traincoupling, graph);


			trainsOnRoute.addtrainOnRoute(route21);

			//er = new M5_Train_Route(trainNo, color, engineLength, noTrucks, truckLength, route, startArc, startFraction, startDirection, truckNames);// assumes
		}

	}

	private static int one = KeyEvent.VK_1;
	private static int two = KeyEvent.VK_2;
	private static int three = KeyEvent.VK_3;
	private static int four = KeyEvent.VK_4;

	private static String noEngines = "1";

	private static String noTrucks = "1";

	public static void StandardMove(int fnkey) {
		if (fnkey == esk){
			String s1 = "";
			s1 = s1 + "1 Number of engines\n";
			s1 = s1 + "2 Number of Trucks\n";
			s1 = s1 + "3 position Train Pointing Backwards Moving Forwards\n";
			s1 = s1 + "4 position Train Pointing Backwards Moving Backwards\n";
			s1 = s1 + "5 inglenook\n";
			s1 = s1 + "6 move back and forth\n";
			msgBox(s1,"Menu");
		}else if (fnkey == one){
			noEngines = JOptionPane.showInputDialog("No Engines");
		}else if (fnkey == two){
			noTrucks = JOptionPane.showInputDialog("No Trucks");
		}else if (fnkey == three){
			msgBox("position Train Pointing Backwards Moving Forwards",stringFraction);
			setTrainParameters("To", "For", "T0", stringFraction,noEngines,noTrucks);
		}else if (fnkey == four){
			msgBox("position Train Pointing Backwards Moving Backwards",stringFraction);
			setTrainParameters("From", "Rev", "T0",stringFraction,noEngines,noTrucks);

		}


	}
}
