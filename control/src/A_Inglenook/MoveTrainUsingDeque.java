package A_Inglenook;

import java.util.List;

import mytrack.C1_BranchGroup;
import mytrack.C2_DJGraph;
import mytrack.D_MyGraph;
import mytrack.K2_Route;
import mytrack.M43_TruckData_Display;
import mytrack.M61_Train_On_Route;
import mytrack.M62_train;
import mytrack.M6_Trains_On_Routes;
import mytrack.M75Stops;
import mytrack.U3_Utils;
import mytrack.U4_Constants;
import sm2.E1;
import sm2.Main;

public class MoveTrainUsingDeque  {
	
	static List<M43_TruckData_Display> trainData1;

	static List<M43_TruckData_Display> trainData2;

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
				MoveTrainUsingDeque.printreadfromdeque(st,3);
				MoveTrainUsingDeque.moveTrucksOneByOneOnDisplay2(strNoTrucks, strFromBranch, strDestBranch);
				MoveTrainUsingDeque.readDeque();
				break;
			case "moveTrucksOnDisplay":
				strNoTrucks = st[1];
				strFromBranch = st[2];
				strDestBranch = st[3];
				MoveTrainUsingDeque.printreadfromdeque(st,3);
				MoveTrainUsingDeque.moveTrucksOnDisplay2(strNoTrucks, strFromBranch, strDestBranch);
				MoveTrainUsingDeque.readDeque();
				break;
			case "move":
				//train T0 already has to be on the right route
				String fromBranch = st[1];
				String toBranch = st[2];
				//			set up route
				//			fromBranch = "st1";
				//			toBranch = "sth";
				MoveTrainUsingDeque.printreadfromdeque(st,2);
				//			positionTrainToMove("T0",fromBranch, toBranch);
				M61_Train_On_Route tr = M6_Trains_On_Routes.getTrainOnRoute("T0");
				//setpoints(fromBranch,toBranch);
				M43_TruckData_Display stop = MoveTrainUsingDeque.getStop(fromBranch, toBranch,"to");
				
				MoveTrainUsingDeque.hmoveLoco(U4_Constants.getDirection());
	//			Main.lo.pause(9000);
				MoveTrainUsingDeque.startMovingToStopFromBranch(stop, tr.getNumberTrucks2());  //counts no of trucks
			    
				break;
			case "connectTrucks":
				MoveTrainUsingDeque.printreadfromdeque(st,1);
				fromBranch = st[1];
				String strBranch = MoveTrainUsingDeque.getStrBranch(fromBranch);
				String trainStr =  MoveTrainUsingDeque.getTrainStrFromStrBranch(strBranch);
				//99System.out.print("connecting trucks");
				MoveTrainUsingDeque.sendConnectMessage(fromBranch);
				M61_Train_On_Route train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
				//turnmovementon(train1);
				M61_Train_On_Route train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
				////			train1.moving = false;
				////			train2.moving = false;
				//			//99System.out.print(train2.getNumberTrucks());
				MoveTrainUsingDeque.decoupleFrom1CoupleTo2(train2, train1, train2.getNumberTrucks());
				//			//99System.out.print("trucks connected");
				//			//99System.out.print("t1 size " + train1.getTruckPositions().size());
				//			//99System.out.print("t2 size " + train2.getTruckPositions().size());
				train1.reset_truck_locations(0);
				//			train2.reset_truck_locations(0);
				//			turnmovementon(train1);
				//turnmovementoff(train1);
	
	
	
				train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
				train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
	
				MoveTrainUsingDeque.readDeque();
	
				break;
			case "disconnectTrucks":
				MoveTrainUsingDeque.printreadfromdeque(st,3);
				int noTrucksBeforeConnection = Integer.valueOf(st[1]);
				int noTrucksToAdd = Integer.valueOf(st[2]); //no of trucks to add or deposit
				String branch = st[3];
				String index = st[4];
				strBranch = MoveTrainUsingDeque.getStrBranch(branch);
				trainStr =  MoveTrainUsingDeque.getTrainStrFromStrBranch(strBranch);
				//99System.out.print("disconnecting trucks");
				train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
				train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
				//99System.out.print(train2.getNumberTrucks());
				//decoupleFrom1CoupleTo2(train1, train2, train1.getNumberTrucks()-noTrucksOnStack4-noTrucksToMove );
				int noTrucksToMove = noTrucksBeforeConnection + noTrucksToAdd;
				int noTrucksToTakeOffTrain1 = train1.getNumberTrucks()-noTrucksToMove;
				MoveTrainUsingDeque.decoupleFrom1CoupleTo2(train1, train2, noTrucksToTakeOffTrain1 );
				//99System.out.print("trucks disconnected");
				//turnmovementon(train1);			
				train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
				train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
	
				MoveTrainUsingDeque.readDeque();
				break;
			case "swapRouteOppDirectionTravelling":
				strFromBranch = st[1];
				String strToBranch = st[2];
				MoveTrainUsingDeque.printreadfromdeque(st,2);
				direction = U4_Constants.swapDirection();
				MoveTrainUsingDeque.swapRouteOppDirection(strFromBranch, strToBranch,direction);
	
	//			Main.lo.moveLoco(direction, 0);
				MoveTrainUsingDeque.readDeque();
				break;
			case "swapRouteSameDirectionTravelling":
				strFromBranch = st[1];
				strToBranch = st[2];
				MoveTrainUsingDeque.printreadfromdeque(st,2);
				MoveTrainUsingDeque.swapRouteSameDirection(strFromBranch, strToBranch);
				//turnmovementon(train1);
				MoveTrainUsingDeque.readDeque();
				break;
			case "moveToDisconnectTrucks":
				MoveTrainUsingDeque.printreadfromdeque(st,4);
				int noTrucksOnStack4 = Integer.valueOf(st[1]);
				noTrucksToMove = Integer.valueOf(st[2]); //deposition is -ve
				fromBranch = st[3];
				toBranch = st[4];
				strFromBranch = MoveTrainUsingDeque.getStrBranch(fromBranch);
				strToBranch = MoveTrainUsingDeque.getStrBranch(toBranch);
				trainStr =  MoveTrainUsingDeque.getTrainStrFromStrBranch(strFromBranch);
				//99System.out.print("disconnecting trucks");
				train1 = M6_Trains_On_Routes.getTrainOnRoute("T0");
				train2 = M6_Trains_On_Routes.getTrainOnRoute(trainStr);
				int trucktostopat = noTrucksOnStack4+noTrucksToMove;
				if (noTrucksToMove>0){    //picking up a truck hence move towards sth
					//swapDirection(strFromBranch, strToBranch);
					tr = M6_Trains_On_Routes.getTrainOnRoute("T0");
					//stop = getStop(strFromBranch, "sth","from");
					
					stop = MoveTrainUsingDeque.getStop(strFromBranch,strToBranch,"from");
					//trucktostopat = 0;
					MoveTrainUsingDeque.startMovingToStopFromBranch(stop, trucktostopat);
	
				}else{					//depositing a truck hence move away from sth, hence do not swap direction
					//swapRouteSameDirection(strFromBranch, strToBranch);
					tr = M6_Trains_On_Routes.getTrainOnRoute("T0");
					//stop = getStop("sth",strToBranch,"from");
					stop = MoveTrainUsingDeque.getStop(strFromBranch, strToBranch,"from");
					MoveTrainUsingDeque.startMovingToStopFromBranch(stop, trucktostopat);
					//swapRouteOppDirection(strToBranch, strFromBranch);
				}
				MoveTrainUsingDeque.hmoveLoco(U4_Constants.getDirection());
				//readDeque();
				break;
	
			case "moveToDisconnectTrucks2":
				MoveTrainUsingDeque.printreadfromdeque(st,4);
				noTrucksOnStack4 = Integer.valueOf(st[1]);
				noTrucksToMove = Integer.valueOf(st[2]); //deposition was -ve
				fromBranch = st[3];
				toBranch = st[4];
				strFromBranch = MoveTrainUsingDeque.getStrBranch(fromBranch);
				strToBranch = MoveTrainUsingDeque.getStrBranch(toBranch);
				if (noTrucksToMove>0){
					//do nothing
				}else{
					//swapRouteOppDirection(strFromBranch, strToBranch);
				}
				MoveTrainUsingDeque.readDeque();
				break;
			case "disconnectSignal":
				MoveTrainUsingDeque.printreadfromdeque(st,0);
				//this to be added later
				MoveTrainUsingDeque.readDeque();
				break;
			case "pause":
				MoveTrainUsingDeque.printreadfromdeque(st,1);
				int noSecs = Integer.valueOf(st[1]);
				CreateTrainMovementDeque.hstopLoco();
				milli = noSecs*1000;
				Main.lo.pause(milli);
				CreateTrainMovementDeque.delay(milli);
				
				MoveTrainUsingDeque.readDeque();
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

	protected static void printreadfromdeque(String[] st, int i) {
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
		System.out.print("hmoveloco domin move engine speed " + engineSpeed);
		Main.lo.moveLoco(direction, engineSpeed);
		
	}

	protected static void startMovingToStopFromBranch(M43_TruckData_Display stop, int NoTrucks) {
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

	public static void decoupleFrom1CoupleTo2(M62_train train1,
			M62_train train2, int noTrucks) {
	
		trainData1 = train1.getTruckPositions();
		trainData2 = train2.getTruckPositions();
	
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
		M43_TruckData_Display stop = MoveTrainUsingDeque.assignStop(fromBranch, toBranch,from_to);
		return stop;
	}

	/**
	 * @param strFromBranch
	 * @param strToBranch
	 * @param direction 
	 */
	protected static void swapRouteOppDirection(String strFromBranch, String strToBranch, String direction) {
	
		M62_train train0 = M6_Trains_On_Routes.getTrainOnRoute("T0");	
	
		K2_Route route= MoveTrainUsingDeque.assignRoute(strFromBranch, strToBranch);
	
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
	
		K2_Route route= MoveTrainUsingDeque.assignRoute(strFromBranch, strToBranch);
		
		route.switchPointsOnRoute(CreateTrainMovementDeque.graph,CreateTrainMovementDeque.graph3);
	
		if(!train0.getTruckPositions().get(0).anotherRouteSameDirectiontravelling(route, CreateTrainMovementDeque.graph)){
			//99System.out.print("unable to swap route");
		}
	
		train0.getTruckPositions().get(0).setRoute(route);
	
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

	protected static M43_TruckData_Display assignStop(String fromBranch,
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

}
