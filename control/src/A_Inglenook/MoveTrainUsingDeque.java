package A_Inglenook;

import java.util.List;

import myscene.listenerObjects;
import mytrack.C1_BranchGroup;
import mytrack.C2_DJGraph;
import mytrack.D_MyGraph;
import mytrack.K2_Route;
import mytrack.M43_TruckData_Display;
import mytrack.M61_Train_On_Route;
import mytrack.M62_train;
import mytrack.M6_Trains_On_Routes;
import mytrack.M75Stops;
import mytrack.M76Stop;
import mytrack.N2_Time;
import mytrack.U3_Utils;
import mytrack.U4_Constants;
import sm2.E1;
import sm2.Main;

public class MoveTrainUsingDeque  {
	
	static List<M43_TruckData_Display> trainData1;

	static List<M43_TruckData_Display> trainData2;
	
	private static boolean DEBUG = false;
	private static void print(String x){
		if (DEBUG ){
		System.out.println(x);
		}
	}
	
	protected static void printreadfromdeque(String[] st, int i) {
		boolean DEBUG = true;
		if(DEBUG){
		E1.threads.get_serialModel().print("readfromdeque  ");
		for (int j = 0; j <= i; j++) {
			String string = st[j];
			E1.threads.get_serialModel().print(" "+string);
		}
		E1.threads.get_serialModel().println();
	
	
		print("readfromdeque  ");
		for (int j = 0; j <= i; j++) {
			String string = st[j];
			print(" "+string);
		}
		//99System.out.print();
		for (int j = 0; j <= i; j++) {
			String string = st[j];
			//E1.threads.get_serialModel().print(" "+string);
		}
		E1.threads.get_serialModel().println();
		}
	}

	public static List<M43_TruckData_Display> getTrainData1() {
		return trainData1;
	}

	public static void setTrainData1(List<M43_TruckData_Display> trainData1) {
		trainData1 = trainData1;
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
			String movement = null;
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
				M76Stop stop = assignStop(fromBranch, toBranch,"to");
				M76Stop[] sensors = assignSensors(fromBranch, toBranch,"to");
				
				hmoveLoco(U4_Constants.getDirection());
	//			Main.lo.pause(9000);
				startMovingToStopFromBranch(stop, sensors, tr.getNumberTrucks2());  //counts no of trucks
			    
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
				System.out.print("train2 number of trucks" + train2.getNumberTrucks());
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
				//Main.lo.setEngineDirection(direction);  //don't need this 'cos in move
				listenerObjects.moveLoco(direction, 0);
				readDeque();
				break;
			case "swapRouteSameDirectionTravelling":
				strFromBranch = st[1];
				strToBranch = st[2];
				printreadfromdeque(st,2);
				swapRouteSameDirection(strFromBranch, strToBranch);
				direction=U4_Constants.getDirection();
				//Main.lo.setEngineDirection(direction);  //don't need this 'cos in move
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
					
					stop = assignStop(strFromBranch,strToBranch,"from");
					//trucktostopat = 0;
					startMovingToStopFromBranch(stop, null, trucktostopat);
	
				}else{					//depositing a truck hence move away from sth, hence do not swap direction
					//swapRouteSameDirection(strFromBranch, strToBranch);
					tr = M6_Trains_On_Routes.getTrainOnRoute("T0");
					//stop = assignStop("sth",strToBranch,"from");
					stop = assignStop(strFromBranch, strToBranch,"from");
					startMovingToStopFromBranch(stop, null, trucktostopat);
					//swapRouteOppDirection(strToBranch, strFromBranch);
				}
				hmoveLoco(U4_Constants.getDirection());
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
				CreateTrainMovementDeque.hstopLoco();
				milli = noSecs*1000;
				Main.lo.pause(milli);
				CreateTrainMovementDeque.delay(milli);
				
				readDeque();
				break;
				
			default:
				//99System.out.print("instruction " + instruction + " not processed");
	
			}
	
	
			//		//generate an event to read the deque. This will move the train
			//	assignStop	E3.e_readList();
		}



	protected static String getStrBranch(String branch){
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



	protected static void sendConnectMessage(String fromBranch) {
		//serialcommand
	
	}

	public static void moveTrucksOneByOneOnDisplay2(String strNoTrucks, String strFromBranch, String strDestBranch) {
		
		int noTrucks = U3_Utils.CStringToInt(strNoTrucks);
		int fromBranch = CreateTrainMovementDeque.getIntBranch(strFromBranch);
		int destBranch = CreateTrainMovementDeque.getIntBranch(strDestBranch);
	
		for (int i = 0; i < noTrucks; i++) {
			if (fromBranch != 4) {
				Integer[] temp = Myfunctions.mypop(Myfunctions.getstack(fromBranch));
				Myfunctions.mypush(Myfunctions.getstack(4), temp);
				Myfunctions.drawStacks();
			}
			if (destBranch != 4) {
				Integer[] temp = Myfunctions.mypop(Myfunctions.getstack(4));
				Myfunctions.mypush(Myfunctions.getstack(destBranch), temp);
				Myfunctions.drawStacks();
			}
		}
		
		Myfunctions.drawStacks();
		
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

	public static void moveTrucksOnDisplay2(String strNoTrucks, String strFromBranch, String strDestBranch) {
	
		int noTrucks = U3_Utils.CStringToInt(strNoTrucks);
		int fromBranch = CreateTrainMovementDeque.getIntBranch(strFromBranch);
		int destBranch = CreateTrainMovementDeque.getIntBranch(strDestBranch);
		if (fromBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				Integer[] temp = Myfunctions.mypop(Myfunctions.getstack(fromBranch));
				Myfunctions.mypush(Myfunctions.getstack(4), temp);
				Myfunctions.drawStacks();
			}
		}
		if (destBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				Integer[] temp = Myfunctions.mypop(Myfunctions.getstack(4));
				Myfunctions.mypush(Myfunctions.getstack(destBranch), temp);
				Myfunctions.drawStacks();
			}
		}
		Myfunctions.drawStacks();		
	}

	protected static void hmoveLoco(String dir) {
		String direction;
		double engineSpeed = U4_Constants.getHspeed();
		if (dir.equals("forwards") ){
			direction = "forwards";
		}else{
			direction = "backwards";
		}
		//System.out.print("hmoveloco domin move engine speed " + engineSpeed);
		Main.lo.moveLoco(direction, engineSpeed);
		
	}

	protected static void startMovingToStopFromBranch(M76Stop stop, M76Stop[] sensors, int NoTrucks) {
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

		M6_Trains_On_Routes.moveTrainCheckForStop(trainStr, stop, sensors, NoTrucks);
	
	}

	public static void decoupleFrom1CoupleTo2(M62_train train1,
			M62_train train2, int noTrucks) {
	
		trainData1 = train1.getTruckPositions();
		trainData2 = train2.getTruckPositions();
	
		if (noTrucks > 0) {    //
			//check if number of trucks in train1 >= no of trucks to be removed
			if (trainData1.size() - train1.getNumberEngines() >= noTrucks) {
				for (int i = 0; i < noTrucks; i++) {
					M43_TruckData_Display remove = trainData1.remove(trainData1.size() - 1);
					trainData2.add(remove);
				}
			}
		} else {
			//check if number of trucks in train2 >= no of trucks to be removed
			if (trainData2.size() - train2.getNumberEngines()  >= -noTrucks) {
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

	protected static M43_TruckData_Display getStop(String fromBranch, String toBranch, String from_to) {
	
		String trainStr = "T0";
	
		//K2_Route route = assignRoute(fromBranch, toBranch);
	
		//M43_TruckData_Display stop = M75Stops.getStop("S1F1");
		M43_TruckData_Display stop = assignStop(fromBranch, toBranch,from_to);
		return stop;
	}
	
	private static M43_TruckData_Display[] getSensors(String fromBranch, String toBranch, String from_to) {
		M43_TruckData_Display[] sensors = assignSensors(fromBranch, toBranch,from_to);
		return sensors;
	}



	/**
	 * @param strFromBranch
	 * @param strToBranch
	 * @param direction 
	 */
	protected static void swapRouteOppDirection(String strFromBranch, String strToBranch, String direction) {
	
		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute("T0");	
	
		K2_Route route= assignRoute(strFromBranch, strToBranch);
		//set route.startArcPair 
		if(!train0.getTruckPositions().get(0).swapDirectiontravelling(route, CreateTrainMovementDeque.graph,direction)){
			//99System.out.print("unable to swap route");
		}
		//route has changed hence comment out
		//train0.getTruckPositions().get(0).setRoute(route);
	
		train0.reset_truck_locations(0);
	
	}

	/**
	 * @param strFromBranch
	 * @param strToBranch
	 */
	protected static void swapRouteSameDirection(String strFromBranch,
			String strToBranch) {
	
		//		String trainStr = getTrainStrFromStrBranch(strFromBranch);
	
		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute("T0");	
	
		K2_Route route= assignRoute(strFromBranch, strToBranch);
		
		route.switchPointsOnRoute(CreateTrainMovementDeque.graph,CreateTrainMovementDeque.graph3);
	
		if(!train0.getTruckPositions().get(0).anotherRouteSameDirectiontravelling(route, CreateTrainMovementDeque.graph)){
			//99System.out.print("unable to swap route");
		}
	
		train0.getTruckPositions().get(0).setRoute(route);  //set route of engine
	
		train0.reset_truck_locations(0);
	}

	


	/**
	 * @param fromBranch
	 * @param toBranch
	 * @return
	 */
	protected static K2_Route assignRoute(String fromBranch, String toBranch) {
		K2_Route route = null;
		switch(fromBranch){
		case "sth":
			switch(toBranch){
			case "st1":
				route = Inglenook.routeh1;
				break;
			case "st2":
				route = Inglenook.routeh2;
				break;
			case "st3":
				route = Inglenook.routeh3;
				break;
			}
			break;
		case "st1":
			route = Inglenook.route1h;
			break;
		case "st2":
			route = Inglenook.route2h;
			break;
		case "st3":
			route = Inglenook.route3h;
			break;
		}
		return route;
	}

	protected static M76Stop assignStop(String fromBranch,
			String toBranch, String from_to) {
		M76Stop stop = null;
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
	
	protected static M76Stop[] assignSensors(String fromBranch,
			String toBranch, String from_to) {
		M76Stop[] sensors= new M76Stop[2];
		switch(fromBranch){
		case "sth":
//			sensors[0] =  M75Stops.getStop("SENF1");
//			sensors[1] =  M75Stops.getStop("SENF2");
//			sensors=null;
//			switch(toBranch){
//			case "st1":
//				stop = M75Stops.getStop("S1F1");
//				break;
//			case "st2":
//				stop = M75Stops.getStop("S2F1");
//				break;
//			case "st3":
//				stop = M75Stops.getStop("S3F1");
//				break;
			sensors[0] =  M75Stops.getStop("SENF1");
			sensors[1] =  M75Stops.getStop("SENF2");
			break;
		case "st1":
		case "st2":
		case "st3":
			sensors[0] =  M75Stops.getStop("SENR1");
			sensors[1] =  M75Stops.getStop("SENR2");
			
			break;
		}
		return sensors;
	}

}
