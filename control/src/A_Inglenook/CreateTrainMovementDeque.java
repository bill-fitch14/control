package A_Inglenook;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import org.apache.commons.lang.StringUtils;

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
import sm2.E1;
import sm2.Main;
//import unimod.E3;

public class CreateTrainMovementDeque {

	static C2_DJGraph graph3 = new C2_DJGraph();

	static Deque <String[]> deque = new ArrayDeque<String[]>();

	static D_MyGraph graph;

	static boolean use3Dgraphics = false;

	static boolean displayStackWhileMoving = false;

	static void movetrainbackandforth(boolean use3Dgraphics2, boolean displayStackWhileMoving2) {

		use3Dgraphics = use3Dgraphics2;
		displayStackWhileMoving = displayStackWhileMoving2;

		int noTrucksToMove = 2;
		for (int i = 0; i < 10; i++) {
			int fromBranch = 2;
			int toBranch = 4;
			moveTruckstest(fromBranch, noTrucksToMove, toBranch);
			fromBranch = 4;
			toBranch = 2;
			moveTrucks(fromBranch, noTrucksToMove, toBranch);
		}
	}
	private static void moveTruckstest(int fromBranch, int noTrucks, int destBranch) {


		if(noTrucks == 0){
			int a = 0;
			a=a+1;
		}
		if ((fromBranch==4)&&(destBranch==2)){
			int a=1;
			a++;
		}
		if (use3Dgraphics) {
			E1.threads.get_serialModel().println(
					"xxxx movetrucks" + " fromBranch: " + fromBranch
					+ " noTrucks: " + noTrucks + " destBranch: "
					+ destBranch);
			// moveTrucks3D(fromBranch, noTrucks, destBranch);
		}

		if(displayStackWhileMoving){
			moveTrucksOnDisplay(noTrucks,fromBranch, destBranch);
		}

		// moves trucks as block
		// doesn't matter how many trucks are on receiving branch
		Integer[] temp;
		int noTrucksOnStack4 = getstack(4).size();
		if (fromBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				temp = Myfunctions.mypop(getstack(fromBranch));
				Myfunctions.mypush(getstack(4), temp);
				Myfunctions.drawStacks();
			}
			//setroute(fromBranch, 4, noTrucks);
			Myfunctions.drawStacks();

			// assume trucks are connected
			if (use3Dgraphics) {

				//move from 4 to from branch
				swapRouteSameDirectionTravelling(4, fromBranch);

				moveEngineToBranch(4, fromBranch); // move and connect with existing trucks if any

				pause(1);

				//change trucks and change direction
				//connect and swap direction
				//pick up truck
				connectTrucks(fromBranch); // move from one liked list to another
				pause(1);								
				swapRouteOppDirectionTravelling(fromBranch, 4);
				swapRouteSameDirectionTravelling(fromBranch, 4);

				int noTrucksInitially = noTrucksOnStack4;
				int noTrucksToMove = noTrucks;
				//move back to 4
				moveToDisconnectTrucks(noTrucksInitially,
						noTrucksToMove, fromBranch, 4); // move to right place
				pause(1);										// to
				// disconnect unwanted trucks
				disconnectTrucks(noTrucksOnStack4,
						noTrucksToMove, fromBranch, destBranch); // move from
				// one
				// linked
				// list to
				// another
				pause(1); // pause for 1 sec
				disconnectSignal(fromBranch); // pulse the
				// disconnect
				// signal
				//move back to branch 4
				moveEngineToBranch(fromBranch, 4);
				// connectTrucksToTrain4(destBranch);
				pause(1);
				swapRouteOppDirectionTravelling(4, fromBranch);
				//				swapRouteSameDirectionTravelling(4,
				//						fromBranch);
				//then in next section go to dest branch (dest branch != 4)
			}
		}

		int noTruckPickedUp;
		if (fromBranch != 4) {
			noTruckPickedUp = noTrucks;
		}else{
			noTruckPickedUp = 0;
		}


		// get no trucks on from branch
		if (destBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				temp = Myfunctions.mypop(getstack(4));
				Myfunctions.mypush(getstack(destBranch), temp);
				Myfunctions.drawStacks();
			}

			if (use3Dgraphics) {



				//move to dest branch
				swapRouteSameDirectionTravelling(4, destBranch);

				moveEngineToBranch(4, destBranch);
				//connect and swap direction 
				int noTrucksToMove = -noTrucks; // deposit a truck
				int noTrucksInitially = noTrucksOnStack4 + noTruckPickedUp; // we have

				connectTrucks(destBranch);
				//if add trucks swap direction then move back to disconnect and then disconnect and move
				//if deposit trucks move forward to disconnect then disconnect then swap direction and move	
				//swap direction
				if(noTrucksToMove>=0){ 
					//swap direction then move back to disconnect
					swapRouteOppDirectionTravelling(destBranch, 4);
					// should not do anything, but does
					swapRouteSameDirectionTravelling(destBranch, 4);
					//move back to disconnect
					moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, destBranch, 4);

				}else{
					//move forward to disconnect
					moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, 4, destBranch);	
				}
				//disconnect
				disconnectTrucks(noTrucksInitially,
						noTrucksToMove, destBranch, 10);
				disconnectSignal(destBranch);
				if(noTrucksToMove<0){ //remove truck
					//swap direction
					swapRouteOppDirectionTravelling(destBranch, 4);
					// should not do anything, but does
					swapRouteSameDirectionTravelling(destBranch, 4); 
				}

				pause(2); // pause for 1 sec
				//move
				moveEngineToBranch(destBranch, 4);
				swapRouteOppDirectionTravelling(4, destBranch);
				//				// should not do anything, but does added recently
				swapRouteSameDirectionTravelling(4, destBranch);

			}
		}

	}


	public static void solvePuzzle(boolean use3Dgraphics2, boolean displayStackWhileMoving2) {

		use3Dgraphics = use3Dgraphics2;
		displayStackWhileMoving = displayStackWhileMoving2;

		// renumber

		// how to move truck to buffer
		// other truck has to be nearest engine on a siding
		// get two trucks on siding
		// move two trucks to branch 4
		// move reqd truck to branch 4
		// move branch 4 to reqd branch

		// 1) Renumber the trucks so we get truck 5 to go nearest the buffers on
		// the top siding, truck 1 nearest the exit.

		// 2) Ensure that truck 5 moves to the buffers of top siding

		int destSiding = 1;
		moveTruckToDesiredPosition(5, destSiding);
		printlist();
		moveTruckToDesiredPosition(4, destSiding);
		moveTruckToDesiredPosition(3, destSiding);
		moveTruckToDesiredPosition(2, destSiding);
		moveTruckToDesiredPosition(1, destSiding);

		// for (int truckNo = 5; truckNo >=0; truckNo--) {
		// moveTruckToDesiredPosition(truckNo, destSiding);
		// }

	}

	static void moveTruckToDesiredPosition(int truckNo, int destSiding) {
		if (truckInSiding(truckNo, destSiding, Myfunctions.getPositions())) {
			// a) If Truck 5 is in top siding, but not nearest buffers, move to
			// other siding
			// if truck 5 is in position 2, move to position 3 by putting
			// another truck at buffers
			// If truck is in position 3 or more move trucks to other siding
			// putting in position 3 if possible
			int desiredPosition = 5 - truckNo;
			if (!truckAtPositionFromEnd(truckNo, destSiding, desiredPosition,
					Myfunctions.getPositions())) {
				// move truck5 to another branch
				int refPos = desiredPosition + 1;
				// if(truckAtPositionFromEnd(truckNo,destSiding,refPos,Myfunctions.getPositions())){
				// int fromBranch = getOccupiedBranch(destSiding);
				// insertTruck(fromBranch,destSiding,desiredPosition+1,
				// Myfunctions.getPositions());
				// }
				// check the position of the truck
				int freeBranch = 0;
				refPos = desiredPosition + 1;
				int rp = 0;
				if (truckAtPositionFromEnd(truckNo, destSiding, refPos,
						Myfunctions.getPositions())) {
					rp = desiredPosition + 1;
				}
				refPos = desiredPosition + 1;
				if (truckAtPositionFromEnd(truckNo, destSiding, refPos + 1,
						Myfunctions.getPositions())) {
					rp = desiredPosition + 2;
				}
				if (rp > 0) {
					int noTrucks = noOfTrucksToLeftFromEnd(destSiding, rp);
					if (noTrucks > 1) {
						// we can't have more than one truck to left of truck to
						// position
						int noTrucks1 = noOfTrucksToLeftFromEnd(destSiding,
								rp + 1);
						// move the surplus trucks
						int freeBranch1 = getFreeBranch(noTrucks1,
								Myfunctions.getPositions());
						moveTrucks(destSiding, noTrucks1, freeBranch1);
					}
					noTrucks = noOfTrucksToLeftFromEnd(destSiding, rp);
					if (noTrucks == 1) {
						int noTrucksToMove = 2;
						freeBranch = getFreeBranch(noTrucksToMove,
								Myfunctions.getPositions());
						moveTrucksOneByOne(destSiding, rp, noTrucksToMove,
								freeBranch);
					}
					noTrucks = noOfTrucksToLeftFromEnd(destSiding, rp);
					if (noTrucks == 0) {
						// can just move truck
						int noTrucksToMove = 1;
						freeBranch = getFreeBranch(noTrucksToMove,
								Myfunctions.getPositions());
						moveTrucks(destSiding, noTrucksToMove, freeBranch);
					}
				}
				refPos = desiredPosition + 3;

				if (truckAtPositionFromEnd(truckNo, destSiding, refPos,
						Myfunctions.getPositions())
						|| truckAtPositionFromEnd(truckNo, destSiding,
								refPos + 1, Myfunctions.getPositions())
						|| truckAtPositionFromEnd(truckNo, destSiding,
								refPos + 2, Myfunctions.getPositions())) {
					// now we have to move the trucks to another siding
					int noTrucks = noOfTrucksToLeftFromEnd(destSiding, 2);
					freeBranch = getFreeBranch(noTrucks,
							Myfunctions.getPositions());
					moveTrucksOneByOne(destSiding, refPos, noTrucks, freeBranch);
				}
				// now move the truck from the first position on the free branch
				// to another branch
				insertTruck(freeBranch, destSiding, 5 - truckNo,
						Myfunctions.getPositions());

			}
		}
		if (!truckInSiding(truckNo, destSiding, Myfunctions.getPositions())) {
			int truckSiding = getBranchNo(truckNo, Myfunctions.getPositions());
			// b) If Truck 5 is nearest buffer of another siding
			// Ensure siding has one spare slot
			// Move a Truck to buffer
			// Ensure siding has one spare slot
			if (truckAtPositionFromEnd(truckNo, truckSiding, 0,
					Myfunctions.getPositions())) {
				int noTrucks = noOfTrucksToLeftFromEnd(truckSiding, 0);
				if (noTrucks > 0) {
					// better occupied branch excluding all trucks less than
					// current truck

					int otherBranch = getOtherBranch(destSiding, truckSiding,
							Myfunctions.getPositions());
					if (noOfTrucksInBranch(otherBranch) == 0) {
						moveTrucks(truckSiding, Math.min(2, noTrucks),
								otherBranch);
						// if truckno >3 then the remaining trucks and the truck
						// to position will not be able to fit on branch 4
						if (truckNo >= 3) {
							moveTrucks(truckSiding, 1, otherBranch);
							truckSiding = getBranchNo(truckNo,
									Myfunctions.getPositions());
						}
					} else if (noOfTrucksInBranch(otherBranch) == 1) {
						moveTrucks(truckSiding, Math.min(2, noTrucks),
								otherBranch);
					} else if (noOfTrucksInBranch(otherBranch) == 2) {
						moveTrucks(truckSiding, 1, otherBranch);
						if (noTrucks == 2) {
							moveTrucks(truckSiding, 1, destSiding);
						}
					} else {
						moveTrucks(truckSiding, noTrucks, destSiding);
						moveTrucks(truckSiding, 1, 4);
						int noTrucks1 = Math.min(
								noOfTrucksToLeftFromEnd(destSiding,
										5 - (truckNo + 1)), 2);
						moveTrucks(1, noTrucks1, 4);
						moveTrucks(4, noOfTrucksInBranch(4), truckSiding);
						// insertTruck(otherBranch,truckSiding,1,
						// Myfunctions.getPositions());
					}
				}
				int refPos = 5 - truckNo;
				insertTruck(truckSiding, destSiding, refPos,
						Myfunctions.getPositions());

			}
			if (truckAtPositionFromEnd(truckNo, truckSiding, 1,
					Myfunctions.getPositions())) {
				int noTrucks = noOfTrucksToLeftFromEnd(truckSiding, 1);
				if (noTrucks > 0) {
					// better occupied branch excluding all trucks less than
					// current truck
					int otherBranch = getOtherBranch(destSiding, truckSiding,
							Myfunctions.getPositions());
					if (noOfTrucksInBranch(otherBranch) <= 2) {
						moveTrucks(truckSiding, 1, otherBranch);
					} else {
						insertTruck(otherBranch, truckSiding, 1,
								Myfunctions.getPositions());
					}
				}
				insertTruck(truckSiding, destSiding, 5 - truckNo,
						Myfunctions.getPositions());
			}
			// check the position of the truck
			int freeBranch = 0;
			int refPos = 2;
			if (truckAtPositionFromEnd(truckNo, truckSiding, refPos,
					Myfunctions.getPositions())) {
				int refPos1 = 5 - truckNo; // because of the numbering
				insertTruck(truckSiding, destSiding, refPos1,
						Myfunctions.getPositions());
				// int noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
				// if(noTrucks>1){
				// //we can't have more than one truck to left of truck to
				// position
				// int noTrucks1 = noOfTrucksToLeftFromEnd(destSiding,3);
				// //move the surplus trucks
				// int freeBranch1 = getFreeBranch(noTrucks1,
				// Myfunctions.getPositions());
				// moveTrucks(destSiding,refPos,noTrucks1,freeBranch1);
				// }
				// noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
				// if(noTrucks==1){
				// int noTrucksToMove = 2;
				// freeBranch = getFreeBranch(noTrucksToMove,
				// Myfunctions.getPositions());
				// moveTrucksOneByOne(destSiding,refPos,noTrucksToMove,
				// freeBranch);
				// }
				// noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
				// if(noTrucks==0){
				// //can just move truck
				// int noTrucksToMove = 1;
				// freeBranch = getFreeBranch(noTrucksToMove,
				// Myfunctions.getPositions());
				// moveTrucks(destSiding,refPos,noTrucksToMove, freeBranch);
				// }

			}
			// refPos = 3;

			// if(truckAtPositionFromEnd(truckNo,destSiding,refPos,Myfunctions.getPositions())||
			// truckAtPositionFromEnd(truckNo,destSiding,refPos+1,Myfunctions.getPositions())||
			// truckAtPositionFromEnd(truckNo,destSiding,refPos+2,Myfunctions.getPositions())){
			// //now we have to move the trucks to another siding
			// int noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
			// freeBranch = getFreeBranch(noTrucks, Myfunctions.getPositions());
			// moveTrucksOneByOne(destSiding,refPos,noTrucks,freeBranch);
			// }
			// now move the truck from the first position on the free branch to
			// another branch
			// insertTruck(truckSiding,destSiding,0,Myfunctions.getPositions());
			// Truck 5 is now in position 2
			// c) If Truck 5 is in position 2
			// Move a Truck to buffer
			// Truck 5 is now in position 3
		}

	}


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

	/**
	 * used by process E1.e_read_stack
	 */
	public static String[] readDeque0(){
		String[] st = null;
		st = deque.pollFirst();
		return st;
	}

	static void delay(int milli) {
		try{
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}

	static void hstopLoco() {
		double engineSpeed = 0;
		Main.lo.stopLoco( engineSpeed);

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

	public static Deque<String[]> getDeque() {
		return deque;
	}







	//private static M62_train trainData2;


	//	public static void setup(M62_train train1, M62_train train2){
	//		M_TruckMovements2.setTrainData1(train1.getTruckPositions());
	//		M_TruckMovements2.trainData2 = train2.getTruckPositions();
	//	}

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


	static boolean truckAtPositionFromEnd(int truckNo, int branchNo, int position, Long[] layout) {
		int _branchNo = getBranchNo(truckNo, layout);
		int _position = getPositionInBranchFromEnd(truckNo, layout);
		if ((branchNo == _branchNo) && (position == _position)) {
			;
			return true;
		} else {
			return false;
		}
	}

	static int noOfTrucksToLeftFromEnd(int branchNo, int posfromend) {
		// posfromend counts from 0
		int noOfTrucksToLeft = noOfTrucksInBranch(branchNo) - posfromend - 1;
		return noOfTrucksToLeft;
	}

	static void insertTruck(int fromBranch, int destBranch, int refPos, Long[] layout) {

		// for this to work there must be no more than 2 trucks to move from the
		// dest branch

		// move trucks from toBranch to head
		int noTrucksToMove = noOfTrucksToLeftFromEnd(destBranch, refPos) + 1;

		if (noTrucksToMove == 0) {
			moveTrucks(fromBranch, 1, destBranch);
			return;
		}

		if (noTrucksToMove > 2) {
			// move the excess trucks to another branch
			int noTrucks1 = noTrucksToMove - 2;
			int otherBranch = getOtherBranch(fromBranch, destBranch, layout);
			if (noSpacesinBranch(otherBranch) == 0) {
				insertTruck(destBranch, fromBranch, 0,
						Myfunctions.getPositions());
				noTrucksToMove--;
			} else {
				if (noSpacesinBranch(otherBranch) < noTrucks1) {
					moveTrucks(fromBranch, noOfTrucksInBranch(fromBranch),
							otherBranch);
					// move trucks on from branch to other branch
					int temp = fromBranch;
					fromBranch = otherBranch;
					otherBranch = temp;
				}
				moveTrucks(destBranch, noTrucks1, otherBranch);
			}
		}

		moveTrucks(destBranch, Math.min(noTrucksToMove, 2), 4);

		// move truck from fromBranch to head
		noTrucksToMove = 1;
		moveTrucks(fromBranch, noTrucksToMove, 4);

		// move trucks from head to toBranch

		// positions=Myfunctions.convertStacksToLong();
		int noTrucksToMove1 = Math.min(noOfTrucksInBranch(4),
				capacityOfBranch(destBranch) - noOfTrucksInBranch(destBranch));
		moveTrucks(4, noTrucksToMove1, destBranch);
		// return positions=Myfunctions.convertStacksToLong();
		// there may be some trucks left on branch 4. move them
		int noTrucksToMove2 = noOfTrucksInBranch(4);
		if (noTrucksToMove2!= 0){
			int freeBranch = getFreeBranch(noTrucksToMove2, layout);
			moveTrucks(4, noTrucksToMove2, freeBranch);
		}
	}

	static int capacityOfBranch(int destBranch) {
		switch (destBranch) {
		case 1:
			return 5;
		case 2:
		case 3:
		case 4:
			return 3;
		}
		return 0;
	}

	static int noSpacesinBranch(int Branch) {
		return capacityOfBranch(Branch) - noOfTrucksInBranch(Branch);
	}

	static int getOtherBranch(int fromBranch, int destBranch, Long[] layout) {
		for (int i = 1; i <= 3; i++) {
			if (i != fromBranch && i != destBranch)
				return i;
		}
		return 99;
	}

	static void moveTrucks(int fromBranch, int noTrucks, int destBranch) {


		if(noTrucks == 0){
			int a = 0;
			a=a+1;
		}
		if ((fromBranch==4)&&(destBranch==2)){
			int a=1;
			a++;
		}
		if (use3Dgraphics) {
			E1.threads.get_serialModel().println(
					"xxxx movetrucks" + " fromBranch: " + fromBranch
					+ " noTrucks: " + noTrucks + " destBranch: "
					+ destBranch);
			// moveTrucks3D(fromBranch, noTrucks, destBranch);
		}

		if(displayStackWhileMoving){
			moveTrucksOnDisplay(noTrucks,fromBranch, destBranch);
		}

		// moves trucks as block
		// doesn't matter how many trucks are on receiving branch
		Integer[] temp;
		int noTrucksOnStack4 = getstack(4).size();
		if (fromBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				temp = Myfunctions.mypop(getstack(fromBranch));
				Myfunctions.mypush(getstack(4), temp);
				Myfunctions.drawStacks();
			}
			//setroute(fromBranch, 4, noTrucks);
			Myfunctions.drawStacks();

			// assume trucks are connected
			if (use3Dgraphics) {

				// if (initialmove) {
				swapRouteSameDirectionTravelling(4, fromBranch);
				moveEngineToBranch(4, fromBranch); // move
				// and
				// connect
				// with
				// existing
				// trucks
				// if
				// any
				pause(1);
				connectTrucks(fromBranch); // move from one
				// linked list
				// to another
				swapRouteOppDirectionTravelling(fromBranch, 4);
				swapRouteSameDirectionTravelling(fromBranch, 4);
				// initialmove = false;
				// }
				int noTrucksInitially = noTrucksOnStack4;
				int noTrucksToMove = noTrucks;
				moveToDisconnectTrucks(noTrucksInitially,
						noTrucksToMove, fromBranch, 4); // move to right place
				// to
				// disconnect
				disconnectTrucks(noTrucksOnStack4,
						noTrucksToMove, fromBranch, destBranch); // move from
				// one
				// linked
				// list to
				// another
				pause(1); // pause for 1 sec
				disconnectSignal(fromBranch); // pulse the
				// disconnect
				// signal

				moveEngineToBranch(fromBranch, 4);
				// connectTrucksToTrain4(destBranch);
				swapRouteOppDirectionTravelling(4, fromBranch);
				//swapRouteSameDirectionTravelling(4,
				//		fromBranch);
			}
		}

		int noTruckPickedUp;
		if (fromBranch != 4) {
			noTruckPickedUp = noTrucks;
		}else{
			noTruckPickedUp = 0;
		}


		// get no trucks on from branch
		if (destBranch != 4) {
			for (int i = 0; i < noTrucks; i++) {
				temp = Myfunctions.mypop(getstack(4));
				Myfunctions.mypush(getstack(destBranch), temp);
				Myfunctions.drawStacks();
			}

			if (use3Dgraphics) {




				swapRouteSameDirectionTravelling(4, destBranch);

				moveEngineToBranch(4, destBranch);
				connectTrucks(destBranch);

				int noTrucksToMove = -noTrucks; // deposit a truck
				int noTrucksInitially = noTrucksOnStack4 + noTruckPickedUp; // we have

				if(noTrucksToMove>0){ //remove truck
					swapRouteOppDirectionTravelling(destBranch, 4);
					// should not do anything, but does
					swapRouteSameDirectionTravelling(destBranch, 4); 
					moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, destBranch, 4);
				}else{
					moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, 4, destBranch);	
				}
				// picked
				// a
				// truck
				// up

				//				moveToDisconnectTrucks(noTrucksInitially,
				//						noTrucksToMove, destBranch, 4);
				//				moveToDisconnectTrucks(noTrucksInitially,
				//				noTrucksToMove, 4, destBranch);				
				disconnectTrucks(noTrucksInitially,
						noTrucksToMove, destBranch, 10);
				disconnectSignal(destBranch);
				if(noTrucksToMove<0){ //remove truck
					swapRouteOppDirectionTravelling(destBranch, 4);
					// should not do anything, but does
					swapRouteSameDirectionTravelling(destBranch, 4); 
				}
				//				M_TruckMovements
				//						.swapRouteSameDirectionTravelling(destBranch, 4); // should
				////																			// not
				////																			// do
				////																			// anything,
				////																			// but
				////																			// does
				pause(2); // pause for 1 sec
				moveEngineToBranch(destBranch, 4);
				swapRouteOppDirectionTravelling(4, destBranch);
				//swapRouteSameDirectionTravelling(4, destBranch);

			}
		}

	}

	static int getFreeBranch(int NoOfTrucksRequired, Long[] layout) {
		// finds a free branch if not creates one
		if ((3 - noOfTrucksInBranch(2)) >= NoOfTrucksRequired) {
			return 2;
		} else if ((3 - noOfTrucksInBranch(3)) >= NoOfTrucksRequired) {
			return 3;
		} else {
			int noTrucksToMove;
			int refPos;
			if (noOfTrucksInBranch(2) < noOfTrucksInBranch(3)) {
				noTrucksToMove = 3 - noOfTrucksInBranch(2) - NoOfTrucksRequired;
				refPos = 3 - noTrucksToMove;
				moveTrucks(2, noTrucksToMove, 3);
				return 2;
			} else {
				noTrucksToMove = NoOfTrucksRequired - noOfTrucksInBranch(3);
				refPos = 3 - noTrucksToMove;
				moveTrucks(3, noTrucksToMove, 3);
				return 3;
			}
		}
	}

	private int getOccupiedBranch(int truckSiding) {
		// finds a branch (not equal to truckSiding) that has at least 1 truck
		switch (truckSiding) {
		case 1:
			if (noOfTrucksInBranch(2) > 0) {
				return 2;
			} else {
				return 3;
			}
		case 2:
			if (noOfTrucksInBranch(3) > 0) {
				return 3;
			} else {
				return 1;
			}
		case 3:
			if (noOfTrucksInBranch(2) > 0) {
				return 2;
			} else {
				return 1;
			}
		}
		return 0;
	}

	static int noOfTrucksInBranch(int branchNo) {

		String pos = StringUtils.leftPad(
				(Myfunctions.getPositions()[1]).toString(), 14);
		pos = pos.replace(' ', '0');
		int count = 0;

		int noOfTrucksInBranch = 0;

		switch (branchNo) {
		case 1:
			count = StringUtils.countMatches(pos.substring(9, 14), "0");
			noOfTrucksInBranch = 5 - count;
			break;
		case 2:
			count = StringUtils.countMatches(pos.substring(6, 9), "0");
			noOfTrucksInBranch = 3 - count;
			break;
		case 3:
			count = StringUtils.countMatches(pos.substring(3, 6), "0");
			noOfTrucksInBranch = 3 - count;
			break;
		case 4:
			count = StringUtils.countMatches(pos.substring(0, 3), "0");
			noOfTrucksInBranch = 3 - count;
			break;
		}

		return noOfTrucksInBranch;

	}

	static Deque<Integer[]> getstack(int branch) {
		return Myfunctions.getstack(branch);
	}

	static void moveTrucksOneByOne(int fromBranch, int refPos, int noTrucks, int destBranch) {

		if (use3Dgraphics) {
			E1.threads.get_serialModel().println(
					"xxxx movetrucksOneByOne" + " fromBranch: " + fromBranch
					+ " refPos: " + refPos + " noTrucks: " + noTrucks
					+ " destBranch: " + destBranch);

			// moveTrucksOneByOne3D(fromBranch, refPos, noTrucks, destBranch);
		}

		if(displayStackWhileMoving){
			moveTrucksOneByOneOnDisplay(noTrucks,fromBranch,destBranch);
		}

		Integer[] temp;

		boolean init = true;
		for (int i = 0; i < noTrucks; i++) {
			int noTrucksOnStack4 = getstack(4).size();
			if (fromBranch != 4) {
				temp = Myfunctions.mypop(getstack(fromBranch));
				Myfunctions.mypush(getstack(4), temp);
				Myfunctions.drawStacks();


				if (use3Dgraphics) {
					init = false;
					swapRouteSameDirectionTravelling(4,
							fromBranch);
					moveEngineToBranch(4, fromBranch); // move
					// engine
					// so
					// engine
					// is at
					// stop
					connectTrucks(fromBranch);
					swapRouteOppDirectionTravelling(
							fromBranch, 4);

					int noTrucksInitially = noTrucksOnStack4;
					int noTrucksToMove = 1; // if noTruckstomove is +ve then
					// picking up a truck

					moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, fromBranch, 4); // move engine back
					// so
					// can disconnect
					disconnectTrucks(noTrucksOnStack4,
							noTrucksToMove, fromBranch, i); // disconnect on
					// graphic
					disconnectSignal(fromBranch); // i have
					// added
					// destbranch
					// it
					// is not
					// needed
					moveEngineToBranch(fromBranch, 4);
					swapRouteOppDirectionTravelling(4,
							fromBranch);
					//swapRouteSameDirectionTravelling(4,fromBranch);

				}
			}



			if (destBranch != 4) {
				temp = Myfunctions.mypop(getstack(4));
				Myfunctions.mypush(getstack(destBranch), temp);
				Myfunctions.drawStacks();

				if (use3Dgraphics) {

					swapRouteSameDirectionTravelling(4,
							destBranch);

					moveEngineToBranch(4, destBranch);
					connectTrucks(destBranch);

					int noTrucksInitially = noTrucksOnStack4 + 1; // we have
					// picked a
					// truck up
					int noTrucksToMove = -1; // deposit a truck

					if(noTrucksToMove>0){ //remove truck
						swapRouteOppDirectionTravelling(destBranch, 4);
						moveToDisconnectTrucks(noTrucksInitially,
								noTrucksToMove, destBranch, 4);
					}else{
						moveToDisconnectTrucks(noTrucksInitially,
								noTrucksToMove, 4, destBranch);	
					}
					//					moveToDisconnectTrucks(noTrucksInitially,
					//							noTrucksToMove, 4, destBranch);

					disconnectTrucks(noTrucksInitially,
							noTrucksToMove, destBranch, i * 10);
					disconnectSignal(destBranch);
					if(noTrucksToMove<0){ //remove truck
						swapRouteOppDirectionTravelling(destBranch, 4);
						swapRouteSameDirectionTravelling(
								destBranch, 4); // should not do anything, but does
					}
					//					swapRouteOppDirectionTravelling(destBranch, 4);
					//							
					////					swapRouteOppDirectionTravelling(
					////							destBranch, 4);
					//					swapRouteSameDirectionTravelling(
					//							destBranch, 4); // should not do anything, but does
					pause(0); // pause for 1 sec
					moveEngineToBranch(destBranch, 4);
					swapRouteOppDirectionTravelling(4,
							destBranch);
					//swapRouteSameDirectionTravelling(4,destBranch);

				}
			}

		}

	}

	private boolean truckAtPosition(int truckNo, int branchNo, int position, Long[] positions) {
		int _branchNo = getBranchNo(truckNo, positions);
		int _position = getPositionInBranch(truckNo, positions);
		if ((branchNo == _branchNo) && position == _position) {
			;
			return true;
		} else {
			return false;
		}
	}

	static int getPositionInBranchFromEnd(Integer truckNo, Long[] layout) {

		int branchNo = getBranchNo(truckNo, layout);
		int position = StringUtils.indexOf(
				StringUtils.leftPad(layout[1].toString(), 14, "0"),
				truckNo.toString());
		int noTrucksInBranch = noOfTrucksInBranch(branchNo);
		switch (position) {
		case 13:
		case 12:
		case 11:
		case 10:
		case 9:
			branchNo = 1;
			return (noTrucksInBranch - (13 - position) - 1);
		case 8:
		case 7:
		case 6:
			return (noTrucksInBranch - (8 - position) - 1);
		case 5:
		case 4:
		case 3:
			return (noTrucksInBranch - (5 - position) - 1);
		case 2:
		case 1:
		case 0:
			return (noTrucksInBranch - (2 - position) - 1);
		}
		return position;

	}

	static int getBranchNo(Integer truckNo, Long[] layout) {

		int positionOfTruck = getPosition(truckNo, layout);
		int branchNo = 0;

		switch (positionOfTruck) {
		case 13:
		case 12:
		case 11:
		case 10:
		case 9:
			branchNo = 1;
			break;
		case 8:
		case 7:
		case 6:
			branchNo = 2;
			break;
		case 5:
		case 4:
		case 3:
			branchNo = 3;
			break;
		case 2:
		case 1:
		case 0:
			branchNo = 4;
			break;
		}
		return branchNo;
	}

	static boolean truckInSiding(int truckNo, int branchNo, Long[] layout) {

		if (branchNo == getBranchNo(truckNo, layout)) {
			return true;
		} else {
			return false;
		}
	}

	static void printlist() {

		int i = 0;
		for (String[] elem : deque) {
			//99System.out.print("index " + i++ + " " + Arrays.toString(elem));
		}

	}

	int getPositionInBranch(Integer truckNo, Long[] positions) {

		int branchNo;
		int position = StringUtils.indexOf(
				StringUtils.leftPad(positions[1].toString(), 14, "0"),
				truckNo.toString());
		switch (position) {
		case 13:
		case 12:
		case 11:
		case 10:
		case 9:
			branchNo = 1;
			return position - 9;
		case 8:
		case 7:
		case 6:
			return position - 6;
		case 5:
		case 4:
		case 3:
			return position - 3;
		case 2:
		case 1:
		case 0:
			return position;
		}
		return position;

	}

	static int getPosition(Integer truckNo, Long[] layout) {

		// int positionOfTruck = 0;
		// for (int j = 0; j < 14; j++) {
		// if(StringUtils.leftPad(positions.toString(),14,"0").charAt(j) ==
		// truckNo.toString().charAt(0)){
		// positionOfTruck = j;
		// }
		// }
		// return positionOfTruck;

		int position = StringUtils.indexOf(
				StringUtils.leftPad(layout[1].toString(), 14, "0"),
				truckNo.toString());

		return position;

	}





}
