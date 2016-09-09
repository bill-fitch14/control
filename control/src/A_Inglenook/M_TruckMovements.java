package A_Inglenook;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import com.ajexperience.utils.DeepCopyException;
import com.ajexperience.utils.DeepCopyUtil;

import mytrack.C1_BranchGroup;
import mytrack.C2_DJGraph;
import mytrack.D_MyGraph;
import mytrack.K2_Route;
import mytrack.L3_TruckEngineObject_BG;
import mytrack.M43_TruckData_Display;
import mytrack.M61_Train_On_Route;
import mytrack.M62_train;
import mytrack.M6_Trains_On_Routes;
import mytrack.M75Stops;
import mytrack.U3_Utils;
import mytrack.U4_Constants;
import sm2.E1;
import sm2.Main;
//import unimod.E3;

public class M_TruckMovements {
	
	static C2_DJGraph graph3 = new C2_DJGraph();

	static Deque <String[]> deque = new ArrayDeque<String[]>();

	static D_MyGraph graph;
	
	static int getIntBranch(String branch){
		switch (branch){
		case "st1":
			return 1;
		case "st2":
			return 2;
		case "st3":
			return 3;
		case "sth":
			return 4;
		}
		return 99;
	}	

	static String getStrBranch(int branch){
		switch (branch){
		case 1:
			return "st1";
		case 2:
			return "st2";
		case 3:
			return "st3";
		case 4:
			return "sth";
		}
		return null;
	}	

	static String getStrBranch(String branch){
		switch (branch){
		case "1":
			return "st1";
		case "2":
			return "st2";
		case "3":
			return "st3";
		case "4":
			return "sth";
		}
		return null;
	}



	static String getTrainStrFromStrBranch(String strBranch){
		String trainStr = null;
		switch(strBranch){
		case "sth":
			trainStr = "T0";
			break;
		case "st1":
			trainStr = "T1";
			break;
		case "st2":
			trainStr = "T2";
			break;
		case "st3":
			trainStr = "T3";
			break;
		}
		return trainStr;
	}


	public static void moveEngineToBranch(int fromBranch, int toBranch) {

		String strFromBranch = null;
		String strToBranch = null;
		// put the request to move and set the route in a queue

		strFromBranch = getStrBranch(fromBranch);
		strToBranch = getStrBranch(toBranch);

		String[] item = {"move",strFromBranch, strToBranch};

		deque.add(item );

		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("move from to");

	}



	public static void moveToDisconnectTrucks(
			int noTrucksOnStack4, int NoTrucksToMove, int fromBranch, int destBranch) {
		String[] item = {"moveToDisconnectTrucks",
				Integer.toString(noTrucksOnStack4), 
				Integer.toString(NoTrucksToMove),
				Integer.toString(fromBranch)
				, Integer.toString(destBranch)};
		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("movetodisconnecttruck nostack4 noToMove from to");

		String[] item2 = {"moveToDisconnectTrucks2",
				Integer.toString(noTrucksOnStack4), 
				Integer.toString(NoTrucksToMove),
				Integer.toString(fromBranch)
				, Integer.toString(destBranch)};
		deque.add(item2 );
		E1.threads.get_serialModel().print("  ");
		for(String i : item2){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("movetodisconnecttruck2 nostack4 noToMove from to");

	}

	public static void connectTrucksToEngine(int noTrucks, int fromBranch, int toBranch) {
		String[] item = {"connectTrucksToEngine",Integer.toString(noTrucks), Integer.toString(fromBranch)};


		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("conn no from");
	}

	public static void readDeque(){
		//		String[] st = readDeque0();
		//		if(st == null ){
		//			//do nothing
		//		}else{
		//			readDeque1(st);
		//		}

		E1.fire_read_stack_event();
	}

	/**
	 * used by process E1.e_read_stack
	 */
	public static String[] readDeque0(){
		String[] st = null;
		st = deque.pollFirst();
		return st;
	}

	public static void readDeque1(String[]st){

		String instruction = st[0];

		U4_Constants.pbl_instructionNo++;

//		if (U4_Constants.pbl_instructionNo == 1){
//			//could check how the train has been set up. assume has been set up forwards
//			Main.lo.setEngineDirection("forwards");
//		}

		E1.threads.get_serialModel().println("deque: " + instruction + "" + U4_Constants.pbl_instructionNo);


		//		String fromBranch; 
		//		String toBranch;
		String strTobranch;
		String trainStrTo;
		M61_Train_On_Route train0;
		K2_Route route;
		int noTrucks;
		String direction = null;
		int milli;
		switch (instruction){
		case "moveTrucksOneByOneOnDisplay":
			String strNoTrucks = st[1];
			String strFromBranch = st[2];
			String strDestBranch = st[3];
			printreadfromdeque(st,3);
			moveTrucksOneByOneOnDisplay2(strNoTrucks, strFromBranch, strDestBranch);
			readDeque();
			break;
		case "moveTrucksOnDisplay":
			strNoTrucks = st[1];
			strFromBranch = st[2];
			strDestBranch = st[3];
			printreadfromdeque(st,3);
			moveTrucksOnDisplay2(strNoTrucks, strFromBranch, strDestBranch);
			readDeque();
			break;
		case "move":
			//train T0 already has to be on the right route
			String fromBranch = st[1];
			String toBranch = st[2];
			//			set up route
			//			fromBranch = "st1";
			//			toBranch = "sth";
			printreadfromdeque(st,2);
			//			positionTrainToMove("T0",fromBranch, toBranch);
			M61_Train_On_Route tr = M6_Trains_On_Routes.getTrainOnRoute("T0");
			//setpoints(fromBranch,toBranch);
			M43_TruckData_Display stop = getStop(fromBranch, toBranch,"to");
			
			hmoveLoco(fromBranch);
			Main.lo.pause(9000);
			startMovingToStopFromBranch(stop, tr.getNumberTrucks2());  //counts no of trucks
		    
			break;
		case "connectTrucks":
			printreadfromdeque(st,1);
			fromBranch = st[1];
			String strBranch = getStrBranch(fromBranch);
			String trainStr =  getTrainStrFromStrBranch(strBranch);
			//99System.out.print("connecting trucks");
			sendConnectMessage(fromBranch);
			M61_Train_On_Route train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
			//turnmovementon(train1);
			M61_Train_On_Route train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
			////			train1.moving = false;
			////			train2.moving = false;
			//			//99System.out.print(train2.getNumberTrucks());
			decoupleFrom1CoupleTo2(train2, train1, train2.getNumberTrucks());
			//			//99System.out.print("trucks connected");
			//			//99System.out.print("t1 size " + train1.getTruckPositions().size());
			//			//99System.out.print("t2 size " + train2.getTruckPositions().size());
			train1.reset_truck_locations(0);
			//			train2.reset_truck_locations(0);
			//			turnmovementon(train1);
			//turnmovementoff(train1);



			train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
			train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);

			readDeque();

			break;
		case "disconnectTrucks":
			printreadfromdeque(st,3);
			int noTrucksBeforeConnection = Integer.valueOf(st[1]);
			int noTrucksToAdd = Integer.valueOf(st[2]); //no of trucks to add or deposit
			String branch = st[3];
			String index = st[4];
			strBranch = getStrBranch(branch);
			trainStr =  getTrainStrFromStrBranch(strBranch);
			//99System.out.print("disconnecting trucks");
			train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
			train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
			//99System.out.print(train2.getNumberTrucks());
			//decoupleFrom1CoupleTo2(train1, train2, train1.getNumberTrucks()-noTrucksOnStack4-noTrucksToMove );
			int noTrucksToMove = noTrucksBeforeConnection + noTrucksToAdd;
			int noTrucksToTakeOffTrain1 = train1.getNumberTrucks()-noTrucksToMove;
			decoupleFrom1CoupleTo2(train1, train2, noTrucksToTakeOffTrain1 );
			//99System.out.print("trucks disconnected");
			//turnmovementon(train1);			
			train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
			train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);

			readDeque();
			break;
		case "swapRouteOppDirectionTravelling":
			strFromBranch = st[1];
			String strToBranch = st[2];
			printreadfromdeque(st,2);
			direction = U4_Constants.swapDirection();
			swapRouteOppDirection(strFromBranch, strToBranch,direction);
//			Main.lo.moveLoco(direction, 0.2);
			readDeque();
			break;
		case "swapRouteSameDirectionTravelling":
			strFromBranch = st[1];
			strToBranch = st[2];
			printreadfromdeque(st,2);
			swapRouteSameDirection(strFromBranch, strToBranch);
			//turnmovementon(train1);
			readDeque();
			break;
		case "moveToDisconnectTrucks":
			printreadfromdeque(st,4);
			int noTrucksOnStack4 = Integer.valueOf(st[1]);
			noTrucksToMove = Integer.valueOf(st[2]); //deposition is -ve
			fromBranch = st[3];
			toBranch = st[4];
			strFromBranch = getStrBranch(fromBranch);
			strToBranch = getStrBranch(toBranch);
			trainStr =  getTrainStrFromStrBranch(strFromBranch);
			//99System.out.print("disconnecting trucks");
			train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
			train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
			int trucktostopat = noTrucksOnStack4+noTrucksToMove;
			if (noTrucksToMove>0){    //picking up a truck hence move towards sth
				//swapDirection(strFromBranch, strToBranch);
				tr = M6_Trains_On_Routes.getTrainOnRoute("T0");
				//stop = getStop(strFromBranch, "sth","from");
				
				stop = getStop(strFromBranch,strToBranch,"from");
				//trucktostopat = 0;
				startMovingToStopFromBranch(stop, trucktostopat);

			}else{						//depositing a truck hence move away from sth, hence do not swap direction
				//swapRouteSameDirection(strFromBranch, strToBranch);
				tr = M6_Trains_On_Routes.getTrainOnRoute("T0");
				//stop = getStop("sth",strToBranch,"from");
				stop = getStop(strFromBranch, strToBranch,"from");
				startMovingToStopFromBranch(stop, trucktostopat);
				//swapRouteOppDirection(strToBranch, strFromBranch);
			}
			//readDeque();
			break;

		case "moveToDisconnectTrucks2":
			printreadfromdeque(st,4);
			noTrucksOnStack4 = Integer.valueOf(st[1]);
			noTrucksToMove = Integer.valueOf(st[2]); //deposition was -ve
			fromBranch = st[3];
			toBranch = st[4];
			strFromBranch = getStrBranch(fromBranch);
			strToBranch = getStrBranch(toBranch);
			if (noTrucksToMove>0){
				//do nothing
			}else{
				//swapRouteOppDirection(strFromBranch, strToBranch);
			}
			readDeque();
			break;
		case "disconnectSignal":
			printreadfromdeque(st,0);
			//this to be added later
			readDeque();
			break;
		case "pause":
			printreadfromdeque(st,1);
			int noSecs = Integer.valueOf(st[1]);
			milli = 2000;
			Main.lo.pause(milli);
			delay(milli);
			
			readDeque();
			hstopLoco();
		default:
			//99System.out.print("instruction " + instruction + " not processed");

		}


		//		//generate an event to read the deque. This will move the train
		//	assignStop	E3.e_readList();
	}

	private static void delay(int milli) {
		try{
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}

	private static void hstopLoco() {
		double engineSpeed = 0;
		Main.lo.stopLoco( engineSpeed);
		
	}

	private static void hmoveLoco(String fromBranch) {
		String direction;
		double engineSpeed = 0.2;
		if (fromBranch.equals("sth") ){
			direction = "forwards";
		}else{
			direction = "backwards";
		}
		Main.lo.moveLoco(direction, engineSpeed);
	}

	/**
	 * @param strFromBranch
	 * @param strToBranch
	 */
	private static void swapRouteSameDirection(String strFromBranch,
			String strToBranch) {

		//		String trainStr = getTrainStrFromStrBranch(strFromBranch);

		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute("T0");	

		K2_Route route= assignRoute(strFromBranch, strToBranch);
		
		route.switchPointsOnRoute(graph,graph3);

		if(!train0.getTruckPositions().get(0).anotherRouteSameDirectiontravelling(route, graph)){
			//99System.out.print("unable to swap route");
		}

		train0.getTruckPositions().get(0).setRoute(route);

		train0.reset_truck_locations(0);
	}

	/**
	 * @param strFromBranch
	 * @param strToBranch
	 * @param direction 
	 */
	private static void swapRouteOppDirection(String strFromBranch, String strToBranch, String direction) {

		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute("T0");	

		K2_Route route= assignRoute(strFromBranch, strToBranch);

		if(!train0.getTruckPositions().get(0).swapDirectiontravelling(route, graph,direction)){
			//99System.out.print("unable to swap route");
		}
		//route has changed hence comment out
		//train0.getTruckPositions().get(0).setRoute(route);

		train0.reset_truck_locations(0);

	}

	//	private static void turnmovementoff(M61_Train_On_Route train1) {
	//		for (M43_TruckData_Display truck : train1.getTruckPositions()){
	//			truck.setCurrentStopActive(false);
	//		}
	//		train1.moving = false;
	//		//99System.out.print("train1.moving" + train1.moving);
	//		
	//	}

	private static void swapTrainCoupling(M62_train train1) {
		if (train1.getTrainCoupling().equals("head")){
			train1.setTrainCoupling("tail");
		}else{
			train1.setTrainCoupling("head");
		}

	}

	private static void turnmovementon(M61_Train_On_Route train1) {


		for (M43_TruckData_Display truck : train1.getTruckPositions()){
			truck.setCurrentStopActive(false);
		}
		train1.setMoving(true);
		//99System.out.print("train1.moving" + train1.moving);

	}

	private static void printreadfromdeque(String[] st, int i) {
		E1.threads.get_serialModel().print("readfromdeque  ");
		for (int j = 0; j <= i; j++) {
			String string = st[j];
			E1.threads.get_serialModel().print(" "+string);
		}
		E1.threads.get_serialModel().println();


		System.out.println("readfromdeque  ");
		for (int j = 0; j <= i; j++) {
			String string = st[j];
			System.out.print(" "+string);
		}
		//99System.out.print();
		for (int j = 0; j <= i; j++) {
			String string = st[j];
			//E1.threads.get_serialModel().print(" "+string);
		}
		E1.threads.get_serialModel().println();
	}

	private static void sendConnectMessage(String fromBranch) {
		//serialcommand

	}

	private static M43_TruckData_Display getStop(String fromBranch, String toBranch, String from_to) {

		String trainStr = "T0";

		//K2_Route route = assignRoute(fromBranch, toBranch);

		//M43_TruckData_Display stop = M75Stops.getStop("S1F1");
		M43_TruckData_Display stop = assignStop(fromBranch, toBranch,from_to);
		return stop;
	}

	private static M43_TruckData_Display assignStop(String fromBranch,
			String toBranch, String from_to) {
		M43_TruckData_Display stop = null;
		switch(fromBranch){
		case "sth":
			switch(toBranch){
			case "st1":
				stop = M75Stops.getStop("S1F1");
				break;
			case "st2":
				stop = M75Stops.getStop("S2F1");
				break;
			case "st3":
				stop = M75Stops.getStop("S3F1");
				break;
			}
			break;
		case "st1":
			if (from_to.equals("to")){
				stop = M75Stops.getStop("SHR1");
			}else{
				stop = M75Stops.getStop("S1R1");
			}
			break;
		case "st2":
			if (from_to.equals("to")){
				stop = M75Stops.getStop("SHR2");
			}else{
				stop = M75Stops.getStop("S2R1");
			}
			break;
		case "st3":
			if (from_to.equals("to")){
				stop = M75Stops.getStop("SHR3");
			}else{
				stop = M75Stops.getStop("S3R1");
			}
			break;
		}
		return stop;
	}

	private static void startMovingToStopFromBranch(M43_TruckData_Display stop, int NoTrucks) {
		//99System.out.print("startMovingToStopFromBranch");

		//Train T0 has to be already on the right route

		//		switch(fromBranch){
		//		case "sth":
		//			trainStr = "T0";
		//			break;
		//		case "st1":
		//			trainStr = "T1";
		//			break;
		//		case "st2":
		//			trainStr = "T2";
		//			break;
		//		case "st3":
		//			trainStr = "T3";
		//			break;
		//		}

		String trainStr = null;
		trainStr = "T0";	
		M6_Trains_On_Routes.moveTrainCheckForStop(trainStr, stop, NoTrucks);

	}


	//	}



	//	private M43_TruckData_Display stop1 = new M43_TruckData_Display(arc, directionFacing, graph, startFraction);
	//	
	//
	//	}

	private static void positionTrainToMove(String trainStr, String fromBranch, String toBranch) {


		//get Train
		M62_train tr = M6_Trains_On_Routes.getTrainOnRoute(trainStr);

		//		tr.position(fromBranch, toBranch);

		//		//train has been positioned, just need it to be repositioned
		//		
		//		if (initial == false){
		//
		//		K2_Route route = assignRoute(fromBranch, toBranch);
		//		
		//		M43_TruckData_Display firstTruck = tr.getTruckPositions().get(0);
		//		
		//		tr.swapDirectiontravelling(route, firstTruck, graph);
		//		
		//		initial = false;
		//		}


	}

	static boolean initial = true;

	/**
	 * @param fromBranch
	 * @param toBranch
	 * @return
	 */
	private static K2_Route assignRoute(String fromBranch, String toBranch) {
		K2_Route route = null;
		switch(fromBranch){
		case "sth":
			switch(toBranch){
			case "st1":
				route = routeh1;
				break;
			case "st2":
				route = routeh2;
				break;
			case "st3":
				route = routeh3;
				break;
			}
			break;
		case "st1":
			route = route1h;
			break;
		case "st2":
			route = route2h;
			break;
		case "st3":
			route = route3h;
			break;
		}
		return route;
	}

	public static Deque<String[]> getDeque() {
		return deque;
	}

	private static K2_Route routeh1;
	private static K2_Route route1h;
	private static K2_Route routeh2;
	private static K2_Route route2h;
	private static K2_Route routeh3;
	private static K2_Route route3h;

	public static void set_routes(D_MyGraph graph2, C2_DJGraph graphx, C1_BranchGroup branchGroup) {
		M_TruckMovements.graph = graph2;
		routeh1 =  new K2_Route("1_To_Rev,3_To_Rev", graph, branchGroup);
		route1h = new K2_Route("3_From_Rev,1_From_Rev", graph, branchGroup);
		routeh2 =  new K2_Route("1_To_Rev,5_To_Rev", graph, branchGroup);
		route2h = new K2_Route("5_From_Rev,1_From_Rev", graph, branchGroup);
		routeh3 =  new K2_Route("1_To_Rev,6_To_Rev", graph, branchGroup);
		route3h = new K2_Route("6_From_Rev,1_From_Rev", graph, branchGroup);

		graph3 = graphx;
	}

	private static List<M43_TruckData_Display> trainData1;

	private static List<M43_TruckData_Display> trainData2;

	//private static M62_train trainData2;


	public static void setup(M62_train train1, M62_train train2){
		M_TruckMovements.trainData1 = train1.getTruckPositions();
		M_TruckMovements.trainData2 = train2.getTruckPositions();
	}

	public static void decoupleFrom1CoupleTo2(M62_train train1,
			M62_train train2, int noTrucks) {

		M_TruckMovements.trainData1 = train1.getTruckPositions();
		M_TruckMovements.trainData2 = train2.getTruckPositions();

		if (noTrucks > 0) {    //
			//check if number of trucks in train1 >= no of trucks to be removed
			if (trainData1.size() - 1 >= noTrucks) {
				for (int i = 0; i < noTrucks; i++) {
					M43_TruckData_Display remove = trainData1.remove(trainData1.size() - 1);
					trainData2.add(remove);
				}
			}
		} else {
			//check if number of trucks in train2 >= no of trucks to be removed
			if (trainData2.size() - 1  >= -noTrucks) {
				for (int i = 0; i < -noTrucks; i++) {
					M43_TruckData_Display remove = trainData2.remove(trainData1.size() - 1);
					trainData1.add(remove);
				}
			}
		}
		train1.setTrainVariablesFromTruckPositions();
		train2.setTrainVariablesFromTruckPositions();

		train1.setTruckPositions(trainData1);
		train2.setTruckPositions(trainData2);

	}  

	public static M43_TruckData_Display getTruckCopy(M43_TruckData_Display truck){

		try {
			DeepCopyUtil deepCopyUtil = new DeepCopyUtil();
			return deepCopyUtil.deepCopy(truck);
		} catch (DeepCopyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hopefully never used
		return truck;
	}

	public static void disconnectSignal(int fromBranch) {
		String[] item = {"disconnectSignal",Integer.toString(fromBranch)};


		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("disconnectSignal from");

	}

	public static void connectTrucks(int fromBranch) {
		String[] item = {"connectTrucks",Integer.toString(fromBranch)};
		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("connectTrucks");
	}

	public static void disconnectTrucks(int noTrucksOnStack4, int noTrucksToMove, int branch, int index) {
		String[] item = {"disconnectTrucks",Integer.toString(noTrucksOnStack4),Integer.toString(noTrucksToMove),
				Integer.toString(branch),Integer.toString(index)};
		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("disconnectTrucks");

	}

	public static void pause(int noSecs) {
		String[] item = {"pause",Integer.toString(noSecs)};
		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("pause");

	}

	public static void swapRouteOppDirectionTravelling(int fromBranch, int toBranch) {

		String strFromBranch = getStrBranch(fromBranch);
		String strToBranch = getStrBranch(toBranch);

		String[] item = {"swapRouteOppDirectionTravelling",strFromBranch, strToBranch};



		//		String[] item = {"swapDirection",Integer.toString(fromBranch)
		//				, Integer.toString(destBranch)};
		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("swapRouteOppDirectionTravelling");

	}

	public static void swapRouteSameDirectionTravelling(int fromBranch, int toBranch) {

		String strFromBranch = getStrBranch(fromBranch);
		String strToBranch = getStrBranch(toBranch);

		String[] item = {"swapRouteSameDirectionTravelling",strFromBranch, strToBranch};



		//		String[] item = {"swapDirection",Integer.toString(fromBranch)
		//				, Integer.toString(destBranch)};
		deque.add(item );
		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("swapRoute");

	}
	
	public static void moveTrucksOneByOneOnDisplay(int noTrucks, int fromBranch, int destBranch) {
		
		String strFromBranch = null;
		String strDestBranch = null;
		String strNoTrucks = null;
		// put the request to move and set the route in a queue

		strFromBranch = getStrBranch(fromBranch);
		strDestBranch = getStrBranch(destBranch);
		strNoTrucks = Integer.toString(noTrucks);

		String[] item = {"moveTrucksOneByOneOnDisplay",strNoTrucks,strFromBranch,strDestBranch};

		deque.add(item );

		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("moveTrucksOneByOneOnDisplay");
		
	}
	
	public static void moveTrucksOnDisplay(int noTrucks, int fromBranch, int destBranch) {
		String strFromBranch = null;
		String strNoTrucks = null;
		String strDestBranch = null;
		// put the request to move and set the route in a queue

		strFromBranch = getStrBranch(fromBranch);
		strDestBranch = getStrBranch(destBranch);
		strNoTrucks = Integer.toString(noTrucks);

		String[] item = {"moveTrucksOnDisplay",strNoTrucks,strFromBranch, strDestBranch};

		deque.add(item );

		E1.threads.get_serialModel().print("  ");
		for(String i : item){
			E1.threads.get_serialModel().print(i + " ");
		}
		E1.threads.get_serialModel().println("moveTrucksOnDisplay");
	}

	public static void moveTrucksOneByOneOnDisplay2(String strNoTrucks, String strFromBranch, String strDestBranch) {
		
		int noTrucks = U3_Utils.CStringToInt(strNoTrucks);
		int fromBranch = getIntBranch(strFromBranch);
		int destBranch = getIntBranch(strDestBranch);

		for (int i = 0; i < noTrucks; i++) {
			if (fromBranch != 4) {
				Integer[] temp = Myfunctions.mypop(A_Inglenook.Inglenook.getstack(fromBranch));
				Myfunctions.mypush(A_Inglenook.Inglenook.getstack(4), temp);
				Myfunctions.drawStacks();
			}
			if (destBranch != 4) {
				Integer[] temp = Myfunctions.mypop(A_Inglenook.Inglenook.getstack(4));
				Myfunctions.mypush(A_Inglenook.Inglenook.getstack(destBranch), temp);
				Myfunctions.drawStacks();
			}
		}
		
		Myfunctions.drawStacks();
		
	}

	public static void moveTrucksOnDisplay2(String strNoTrucks, String strFromBranch, String strDestBranch) {

		int noTrucks = U3_Utils.CStringToInt(strNoTrucks);
		int fromBranch = getIntBranch(strFromBranch);
		int destBranch = getIntBranch(strDestBranch);
		if (fromBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				Integer[] temp = Myfunctions.mypop(A_Inglenook.Inglenook.getstack(fromBranch));
				Myfunctions.mypush(A_Inglenook.Inglenook.getstack(4), temp);
				Myfunctions.drawStacks();
			}
		}
		if (destBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				Integer[] temp = Myfunctions.mypop(A_Inglenook.Inglenook.getstack(4));
				Myfunctions.mypush(A_Inglenook.Inglenook.getstack(destBranch), temp);
				Myfunctions.drawStacks();
			}
		}
		Myfunctions.drawStacks();		
	}



}
