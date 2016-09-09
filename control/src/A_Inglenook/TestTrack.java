package A_Inglenook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import mytrack.C1_BranchGroup;
import mytrack.C2_DJGraph;
import mytrack.D_MyGraph;
import sm2.E1;

public class TestTrack {
	
	static C2_DJGraph graph;

	static boolean use3Dgraphics = false;
	static boolean displayStackWhileMoving = false;

	static long init;
	// private static int notrucksToMove;

	private static M_TruckMovements tm;

	private static int n;

	private static Long[] layout;

	private static List<String> list;
	
	public static void main(String[] args) {
		runInglenook(null);
	}
	
	
	public static void runInglenook(C1_BranchGroup branchGroup) {

		list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
		Collections.shuffle(list);
		String s = list.toString();
		s = s.replace("[", "");
		s = s.replace("]", "");
		s = s.replace(" ", "");
		s = s.replace(",", "");


		Long init = Long.parseLong(s);

		// init = 67245183l;

		n = 8;


		init = 54321678000l;

		layout = new Long[] { init, init };

		Myfunctions.openGraphics(n, layout);

		// run First run of Inglenook
		runFirstInglenook(graph.get_DJG(),graph, branchGroup);

	}
	
	public static void runInglenook2(long init, C2_DJGraph graph, C1_BranchGroup branchGroup) {

		Inglenook.graph = graph;

		M_TruckMovements.graph = graph.get_DJG();
		use3Dgraphics = true;
		displayStackWhileMoving = true;
		n = 8;

		//99System.out.print(init);
		layout = new Long[] { init, init };

		Myfunctions.openGraphics(n, layout);

		// run First run of Inglenook
		runFirstInglenook(M_TruckMovements.graph,graph,branchGroup);
	}
	
	private static void runFirstInglenook(D_MyGraph graph2, C2_DJGraph graph3, C1_BranchGroup branchGroup) {
		// run inglenook

		M_TruckMovements.set_routes(graph2,graph3,branchGroup );

		inglenook(n, layout);
	}
	
	public static void inglenook(int N, Long[] init) {

		// set up the stacks
		int[] pole = new int[N + 1]; // pole[i] = pole (0-2) that disc i is on
		//99System.out.print("init = " + init);
		
		Myfunctions.convertLongToStacks(init, pole);
		Myfunctions.drawStacks(pole);
		
		try{
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};

		//solvePuzzle();
		movetrainbackandforth();
		
		Myfunctions.drawStacks(pole);
		
		
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		

		
		
		//pole = new int[N + 1];
		
		Myfunctions.convertLongToStacks(init, pole);
		//99System.out.print("init = " + init);
		Myfunctions.drawStacks(pole);
		layout = new Long[] { init[0], init[0] };
		//added
		
//		layout = Myfunctions.convertStacksToLong(Myfunctions.getSt1(),
//				Myfunctions.getSt2(), Myfunctions.getSt3(),
//				Myfunctions.getSth());
		

		//solvePuzzle();
		//Myfunctions.drawStacks(pole);
		//added
		
		try{
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		//
		runTrainUsingStack(graph);

	}
	
	private static void movetrainbackandforth() {

		int noTrucksToMove = 1;
		for (int i = 0; i < 2; i++) {
			int fromBranch = 1;
			int toBranch = 2;
			moveTrucks(fromBranch, noTrucksToMove, toBranch);
			fromBranch = 2;
			toBranch = 1;
			moveTrucks(fromBranch, noTrucksToMove, toBranch);
		}
//		moveTruckToDesiredPosition(5, destSiding);
//		printlist();
//		moveTruckToDesiredPosition(4, destSiding);
//		moveTruckToDesiredPosition(3, destSiding);
//		moveTruckToDesiredPosition(2, destSiding);
//		moveTruckToDesiredPosition(1, destSiding);
		
	}


	private static void runTrainUsingStack(C2_DJGraph graph2) {

		/*
		 * This reads from the stack sets a event to get the train moving sets a
		 * condition so that when the train reaches the desired position it
		 * stops, and reads again from the stack or if the stack is finished,
		 * terminates.
		 */

		graph = graph2;

		readFromStack();

	}
	
	private static void readFromStack() {

		M_TruckMovements.readDeque();

	}
	private static void solvePuzzle() {

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
		moveTrucks(1, destSiding);
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
	
	private static void moveTrucks(int i, int fromBranch) {
		int freeBranch = 0;
		int noTrucksToMove = i;
		freeBranch = getFreeBranch(noTrucksToMove,
				Myfunctions.getPositions());
		
		moveTrucks(fromBranch, noTrucksToMove, freeBranch);
		
	}


	
	private static void printlist() {

		int i = 0;
		for (String[] elem : M_TruckMovements.deque) {
			//99System.out.print("index " + i++ + " " + Arrays.toString(elem));
		}

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

	private static int getFreeBranch(int NoOfTrucksRequired, Long[] layout) {
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

	private static int getOccupiedBranch(int truckSiding) {
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

	private static int noOfTrucksInBranch(int branchNo) {

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

	private static int noOfTrucksToLeftFromEnd(int branchNo, int posfromend) {
		// posfromend counts from 0
		int noOfTrucksToLeft = noOfTrucksInBranch(branchNo) - posfromend - 1;
		return noOfTrucksToLeft;
	}

	static Deque<Integer[]> getstack(int branch) {
		switch (branch) {
		case 1:
			return Myfunctions.getSt1();
		case 2:
			return Myfunctions.getSt2();
		case 3:
			return Myfunctions.getSt3();
		case 4:
			return Myfunctions.getSth();
		}
		return null;
	}

	private static void moveTrucks(int fromBranch, int noTrucks, int destBranch) {

		
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
			M_TruckMovements.moveTrucksOnDisplay(noTrucks,fromBranch, destBranch);
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
				M_TruckMovements
						.swapRouteSameDirectionTravelling(4, fromBranch);
				M_TruckMovements.moveEngineToBranch(4, fromBranch); // move
																	// and
																	// connect
																	// with
																	// existing
																	// trucks
																	// if
																	// any
				M_TruckMovements.pause(1);
				M_TruckMovements.connectTrucks(fromBranch); // move from one
															// linked list
															// to another
				M_TruckMovements.swapRouteOppDirectionTravelling(fromBranch, 4);
				M_TruckMovements.swapRouteSameDirectionTravelling(fromBranch, 4);
				// initialmove = false;
				// }
				int noTrucksInitially = noTrucksOnStack4;
				int noTrucksToMove = noTrucks;
				M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
						noTrucksToMove, fromBranch, 4); // move to right place
														// to
				// disconnect
				M_TruckMovements.disconnectTrucks(noTrucksOnStack4,
						noTrucksToMove, fromBranch, destBranch); // move from
																	// one
																	// linked
																	// list to
																	// another
				M_TruckMovements.pause(1); // pause for 1 sec
				M_TruckMovements.disconnectSignal(fromBranch); // pulse the
																// disconnect
																// signal

				M_TruckMovements.moveEngineToBranch(fromBranch, 4);
				// M_TruckMovements.connectTrucksToTrain4(destBranch);
				M_TruckMovements.swapRouteOppDirectionTravelling(4, fromBranch);
				//M_TruckMovements.swapRouteSameDirectionTravelling(4,
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
			
				
				

				M_TruckMovements.swapRouteSameDirectionTravelling(4, destBranch);

				M_TruckMovements.moveEngineToBranch(4, destBranch);
				M_TruckMovements.connectTrucks(destBranch);

				int noTrucksToMove = -noTrucks; // deposit a truck
				int noTrucksInitially = noTrucksOnStack4 + noTruckPickedUp; // we have

				if(noTrucksToMove>0){ //remove truck
					M_TruckMovements.swapRouteOppDirectionTravelling(destBranch, 4);
					// should not do anything, but does
					M_TruckMovements.swapRouteSameDirectionTravelling(destBranch, 4); 
					M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, destBranch, 4);
				}else{
					M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, 4, destBranch);	
				}
				// picked
				// a
				// truck
				// up

				//				M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
				//						noTrucksToMove, destBranch, 4);
				//				M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
				//				noTrucksToMove, 4, destBranch);				
				M_TruckMovements.disconnectTrucks(noTrucksInitially,
						noTrucksToMove, destBranch, 10);
				M_TruckMovements.disconnectSignal(destBranch);
				if(noTrucksToMove<0){ //remove truck
					M_TruckMovements.swapRouteOppDirectionTravelling(destBranch, 4);
					// should not do anything, but does
					M_TruckMovements.swapRouteSameDirectionTravelling(destBranch, 4); 
				}
				//				M_TruckMovements
				//						.swapRouteSameDirectionTravelling(destBranch, 4); // should
////																			// not
////																			// do
////																			// anything,
////																			// but
////																			// does
				M_TruckMovements.pause(2); // pause for 1 sec
				M_TruckMovements.moveEngineToBranch(destBranch, 4);
				M_TruckMovements.swapRouteOppDirectionTravelling(4, destBranch);
				//M_TruckMovements.swapRouteSameDirectionTravelling(4, destBranch);

			}
		}

	}

	
	private static void moveTrucksOneByOne(int fromBranch, int refPos,
			int noTrucks, int destBranch) {

		if (use3Dgraphics) {
			E1.threads.get_serialModel().println(
					"xxxx movetrucksOneByOne" + " fromBranch: " + fromBranch
							+ " refPos: " + refPos + " noTrucks: " + noTrucks
							+ " destBranch: " + destBranch);

			// moveTrucksOneByOne3D(fromBranch, refPos, noTrucks, destBranch);
		}
		
		if(displayStackWhileMoving){
			M_TruckMovements.moveTrucksOneByOneOnDisplay(noTrucks,fromBranch,destBranch);
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
					M_TruckMovements.swapRouteSameDirectionTravelling(4,
							fromBranch);
					M_TruckMovements.moveEngineToBranch(4, fromBranch); // move
																		// engine
																		// so
																		// engine
																		// is at
																		// stop
					M_TruckMovements.connectTrucks(fromBranch);
					M_TruckMovements.swapRouteOppDirectionTravelling(
							fromBranch, 4);

					int noTrucksInitially = noTrucksOnStack4;
					int noTrucksToMove = 1; // if noTruckstomove is +ve then
											// picking up a truck

					M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
							noTrucksToMove, fromBranch, 4); // move engine back
															// so
															// can disconnect
					M_TruckMovements.disconnectTrucks(noTrucksOnStack4,
							noTrucksToMove, fromBranch, i); // disconnect on
															// graphic
					M_TruckMovements.disconnectSignal(fromBranch); // i have
																	// added
																	// destbranch
																	// it
																	// is not
																	// needed
					M_TruckMovements.moveEngineToBranch(fromBranch, 4);
					M_TruckMovements.swapRouteOppDirectionTravelling(4,
							fromBranch);
					//M_TruckMovements.swapRouteSameDirectionTravelling(4,fromBranch);

				}
			}
			


			if (destBranch != 4) {
				temp = Myfunctions.mypop(getstack(4));
				Myfunctions.mypush(getstack(destBranch), temp);
				Myfunctions.drawStacks();

				if (use3Dgraphics) {

					M_TruckMovements.swapRouteSameDirectionTravelling(4,
							destBranch);

					M_TruckMovements.moveEngineToBranch(4, destBranch);
					M_TruckMovements.connectTrucks(destBranch);

					int noTrucksInitially = noTrucksOnStack4 + 1; // we have
																	// picked a
																	// truck up
					int noTrucksToMove = -1; // deposit a truck
					
					if(noTrucksToMove>0){ //remove truck
						M_TruckMovements.swapRouteOppDirectionTravelling(destBranch, 4);
						M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
								noTrucksToMove, destBranch, 4);
					}else{
						M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
								noTrucksToMove, 4, destBranch);	
					}
//					M_TruckMovements.moveToDisconnectTrucks(noTrucksInitially,
//							noTrucksToMove, 4, destBranch);
				
					M_TruckMovements.disconnectTrucks(noTrucksInitially,
							noTrucksToMove, destBranch, i * 10);
					M_TruckMovements.disconnectSignal(destBranch);
					if(noTrucksToMove<0){ //remove truck
						M_TruckMovements.swapRouteOppDirectionTravelling(destBranch, 4);
						M_TruckMovements.swapRouteSameDirectionTravelling(
								destBranch, 4); // should not do anything, but does
					}
//					M_TruckMovements.swapRouteOppDirectionTravelling(destBranch, 4);
//							
////					M_TruckMovements.swapRouteOppDirectionTravelling(
////							destBranch, 4);
//					M_TruckMovements.swapRouteSameDirectionTravelling(
//							destBranch, 4); // should not do anything, but does
					M_TruckMovements.pause(0); // pause for 1 sec
					M_TruckMovements.moveEngineToBranch(destBranch, 4);
					M_TruckMovements.swapRouteOppDirectionTravelling(4,
							destBranch);
					//M_TruckMovements.swapRouteSameDirectionTravelling(4,destBranch);

				}
			}

		}

	}

	// private static void moveTrucksOneByOne3D(int fromBranch, int refPos,
	// int noTrucks, int destBranch) {
	// for (int i = 0; i < noTrucks; i++) {
	// if (fromBranch!=4){
	// // temp = Myfunctions.mypop(getstack(fromBranch));
	// // Myfunctions.mypush(getstack(4), temp);
	// // Myfunctions.drawStacks();
	// moveTrucks3D(fromBranch,destBranch,1);
	//
	//
	// }
	//
	// if(destBranch!= 4){
	// // temp = Myfunctions.mypop(getstack(4));
	// // Myfunctions.mypush(getstack(destBranch), temp);
	// // Myfunctions.drawStacks();
	// moveTrucks3D(fromBranch,destBranch,1);
	// moveTrucks3D(destBranch,fromBranch,0);
	// }
	//
	// if(i!= noTrucks-1){
	// moveTrucks3D(destBranch,fromBranch,0);
	// }
	// }
	//
	// }
	private static void insertTruck(int fromBranch, int destBranch, int refPos,
			Long[] layout) {

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

	private static int capacityOfBranch(int destBranch) {
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

	private static int noSpacesinBranch(int Branch) {
		return capacityOfBranch(Branch) - noOfTrucksInBranch(Branch);
	}

	private static int getOtherBranch(int fromBranch, int destBranch,
			Long[] layout) {
		for (int i = 1; i <= 3; i++) {
			if (i != fromBranch && i != destBranch)
				return i;
		}
		return 99;
	}

	private static boolean truckAtPosition(int truckNo, int branchNo,
			int position, Long[] positions) {
		int _branchNo = getBranchNo(truckNo, positions);
		int _position = getPositionInBranch(truckNo, positions);
		if ((branchNo == _branchNo) && position == _position) {
			;
			return true;
		} else {
			return false;
		}
	}

	private static boolean truckAtPositionFromEnd(int truckNo, int branchNo,
			int position, Long[] layout) {
		int _branchNo = getBranchNo(truckNo, layout);
		int _position = getPositionInBranchFromEnd(truckNo, layout);
		if ((branchNo == _branchNo) && (position == _position)) {
			;
			return true;
		} else {
			return false;
		}
	}

	private static int getPositionInBranch(Integer truckNo, Long[] positions) {

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

	private static int getPositionInBranchFromEnd(Integer truckNo, Long[] layout) {

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

	private static int getPosition(Integer truckNo, Long[] layout) {

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

	private static int getBranchNo(Integer truckNo, Long[] layout) {

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

	private static boolean truckInSiding(int truckNo, int branchNo,
			Long[] layout) {

		if (branchNo == getBranchNo(truckNo, layout)) {
			return true;
		} else {
			return false;
		}
	}
}
