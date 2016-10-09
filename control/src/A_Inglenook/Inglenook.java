package A_Inglenook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.media.j3d.BranchGroup;

import mytrack.C1_BranchGroup;
import mytrack.C2_DJGraph;
import mytrack.D_MyGraph;
import mytrack.K2_Route;
import Jama.util.Maths;
import edu.princeton.cs.introcs.StdDraw;

public class Inglenook {

	static C2_DJGraph graph;




	static long init;
	// private static int notrucksToMove;

//	private static M_TruckMovements tm;

	private static int n;

	private static Long[] layout;

	private static List<String> list;




	 static boolean use3Dgraphics = false;
		
	 static boolean displayStackWhileMoving = false;

	

	public static void main(String[] args) {
		runInglenook(null);
	}

	public static void runInglenookFromTrain(C1_BranchGroup branchGroup) {
		//use3Dgraphics = true;
		displayStackWhileMoving = false;
		runInglenook(branchGroup);
	}
	


	public static void runInglenook(C1_BranchGroup branchGroup) {

		list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
		Collections.shuffle(list);
		String s = list.toString();
		s = s.replace("[", "");
		s = s.replace("]", "");
		s = s.replace(" ", "");
		s = s.replace(",", "");

		//99System.out.print(s);

		Long init = Long.parseLong(s);

		// init = 67245183l;

		n = 8;

		// specify the initial layout of the trucks
		/*
		 * 0-2 branch 1(top) 3-5 branch 3 (middle) 6-8 branch 2 (bottom) 9-13
		 * branch 4 (to left)
		 */
		// //init = 8302106574l;
		// //init = 83402700561l;
		// //init = 3401800762500l;
		// init = 52847136l;
		// init = 2500461873l;
		// init = 64573218l;
		// init = 3600412857l;
		// init = 104273685l;
		// //init = 87654321l;
		init = 104273685l;
		//99System.out.print(init);
		layout = new Long[] { init, init };

		Myfunctions.openGraphics(n, layout);

		// run First run of Inglenook
		runFirstInglenook(graph.get_DJG(),graph, branchGroup);

		// if(Myfunctions.pause)StdDraw.show(Myfunctions.delay); //
		// loop:
		// for (int i = 1;i<3000;i++){
		////99//99System.out.print("pass "+i);
		// rerunInglenook();
		// }
		// //get random arrangement
	}

	public static void runInglenook2(long init, C2_DJGraph graph, C1_BranchGroup branchGroup) {

		Inglenook.graph = graph;

		CreateTrainMovementDeque.graph = graph.get_DJG();
		use3Dgraphics = true;
		displayStackWhileMoving = true;
		n = 8;

		//99System.out.print(init);
		layout = new Long[] { init, init };

		Myfunctions.openGraphics(n, layout);

		// run First run of Inglenook
		runFirstInglenook(CreateTrainMovementDeque.graph,graph,branchGroup);
	}

	private static void runFirstInglenook(D_MyGraph graph2, C2_DJGraph graph3, C1_BranchGroup branchGroup) {
		// run inglenook

		set_routes(graph2,graph3,branchGroup );

		inglenook(n, layout);
	}
	





	private static D_MyGraph D_MyGraphgraph;



	private static void rerunInglenook(C1_BranchGroup branchGroup) {

		Collections.shuffle(list);
		String s = list.toString();
		s = list.toString();
		s = s.replace("[", "");
		s = s.replace("]", "");
		s = s.replace(" ", "");
		s = s.replace(",", "");

		//99System.out.print(s);

		layout = Myfunctions.convertStacksToLong(Myfunctions.getSt1(),
				Myfunctions.getSt2(), Myfunctions.getSt3(),
				Myfunctions.getSth());

		Long init0 = layout[0];
		Long init1 = layout[1];

		//99System.out.print("end: " + init1);

		long init5 = init1 % 100000l;
		//99System.out.print("end: last 5: " + init5);
		if (init5 != 54321l) {
			//99System.out.print(init5 + "    " + 54321l);
			return;
		}

		long init2 = Long.parseLong(s);
		long init3 = shuffleinit1withinit2(init1, init2);
		// layout = new Long[]{init0,init1};

		// Myfunctions.openGraphics(N,layout);
		layout = new Long[] { init3, init3 };

		//99System.out.print("init3: " + init3);

		Myfunctions.openGraphics(n, layout);

		if (Myfunctions.pause)
			StdDraw.show(Myfunctions.delay);
		// layout = new Long[]{init3,init1};
		//
		// Myfunctions.openGraphics(N,layout);
		// StdDraw.show(4000);

		inglenook(n, layout);
		if (Myfunctions.pause)
			StdDraw.show(Myfunctions.delay);

	}

	private static Long shuffleinit1withinit2(Long init1, long init2) {
		int j = 1;
		long init3 = 0l;
		for (int i = 1; i < 14; i++) {
			int n = Myfunctions.getNthDigit(init1, 10, i);
			if (n != 0) {
				n = Myfunctions.getNthDigit(init2, 10, j);
				j++;
			}
			init3 += n * (long) (Math.pow(10, i - 1));
		}
		return init3;
	}

	public static String generateString(Random rng, String characters,
			int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
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

		//CreateTrainMovementDeque.solvePuzzle(use3Dgraphics,displayStackWhileMoving);
		//solvePuzzle();
		CreateTrainMovementDeque.movetrainbackandforth(use3Dgraphics,displayStackWhileMoving);
		
		Myfunctions.drawStacks(pole);
		
		
		try{
			Thread.sleep(10000);
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

		MoveTrainUsingDeque.readDeque();

	}

	

	// if (truckInSiding(truckNo,destSiding,Myfunctions.getPositions())){
	// // a) If Truck 5 is in top siding, but not nearest buffers, move to other
	// siding
	// // if truck 5 is in position 2, move to position 3 by putting another
	// truck at buffers
	// // If truck is in position 3 or more move trucks to other siding putting
	// in position 3 if possible
	// if(!truckAtPositionFromEnd(truckNo,destSiding,0,Myfunctions.getPositions())){
	// //move truck5 to another branch
	// if(truckAtPositionFromEnd(truckNo,destSiding,1,Myfunctions.getPositions())){
	// int fromBranch = getOccupiedBranch(destSiding);
	// insertTruck(fromBranch,destSiding,1, Myfunctions.getPositions());
	// }
	// //check the position of the truck
	// int freeBranch = 0;
	// int refPos = 2;
	// if(truckAtPositionFromEnd(truckNo,destSiding,refPos,Myfunctions.getPositions())){
	// int noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
	// if(noTrucks>1){
	// //we can't have more than one truck to left of truck to position
	// int noTrucks1 = noOfTrucksToLeftFromEnd(destSiding,3);
	// //move the surplus trucks
	// int freeBranch1 = getFreeBranch(noTrucks1, Myfunctions.getPositions());
	// moveTrucks(destSiding,refPos,noTrucks1,freeBranch1);
	// }
	// noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
	// if(noTrucks==1){
	// int noTrucksToMove = 2;
	// freeBranch = getFreeBranch(noTrucksToMove, Myfunctions.getPositions());
	// moveTrucksOneByOne(destSiding,refPos,noTrucksToMove, freeBranch);
	// }
	// noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
	// if(noTrucks==0){
	// //can just move truck
	// int noTrucksToMove = 1;
	// freeBranch = getFreeBranch(noTrucksToMove, Myfunctions.getPositions());
	// moveTrucks(destSiding,refPos,noTrucksToMove, freeBranch);
	// }
	// }
	// refPos = 3;
	//
	// if(truckAtPositionFromEnd(truckNo,destSiding,refPos,Myfunctions.getPositions())||
	// truckAtPositionFromEnd(truckNo,destSiding,refPos+1,Myfunctions.getPositions())||
	// truckAtPositionFromEnd(truckNo,destSiding,refPos+2,Myfunctions.getPositions())){
	// //now we have to move the trucks to another siding
	// int noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
	// freeBranch = getFreeBranch(noTrucks, Myfunctions.getPositions());
	// moveTrucksOneByOne(destSiding,refPos,noTrucks,freeBranch);
	// }
	// //now move the truck from the first position on the free branch to
	// another branch
	// insertTruck(freeBranch,destSiding,0,Myfunctions.getPositions());
	//
	// }
	// }
	//
	// if (!truckInSiding(truckNo,destSiding,Myfunctions.getPositions())){
	// int truckSiding = getBranchNo(truckNo, Myfunctions.getPositions());
	// // b) If Truck 5 is nearest buffer of another siding
	// // Ensure siding has one spare slot
	// // Move a Truck to buffer
	// // Ensure siding has one spare slot
	// if(truckAtPositionFromEnd(truckNo,truckSiding,2,Myfunctions.getPositions())){
	// int fromBranch = getOccupiedBranch(truckSiding);
	// insertTruck(fromBranch,truckNo,0, Myfunctions.getPositions());
	//
	// //move truck5 to another branch
	// if(truckAtPositionFromEnd(truckNo,destSiding,1,Myfunctions.getPositions())){
	// fromBranch = getOccupiedBranch(truckSiding);
	// insertTruck(fromBranch,truckNo,0, Myfunctions.getPositions());
	// }
	// int refPos = 2;
	// if(truckAtPositionFromEnd(truckNo,destSiding,refPos,Myfunctions.getPositions())){
	// int noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
	// if (noTrucks>1){
	// int noTrucks1 = noOfTrucksToLeftFromEnd(destSiding,3);
	// int freeBranch = getFreeBranch(noTrucks1, Myfunctions.getPositions());
	// moveTrucks(destSiding,refPos,noTrucks1 ,freeBranch);
	// }
	// //now we have 1 truck to move
	// noTrucks = noOfTrucksToLeftFromEnd(destSiding,2);
	// int freeBranch = getFreeBranch(noTrucks, Myfunctions.getPositions());
	// moveTrucks(destSiding,refPos,noTrucks,freeBranch);
	// if(noTrucks == 1){
	// insertTruck(freeBranch,destSiding,0, Myfunctions.getPositions());
	// }
	// }
	//
	// }
	// // Truck 5 is now in position 2
	// // c) If Truck 5 is in position 2
	// // Move a Truck to buffer
	// // Truck 5 is now in position 3
	// }
	//
	//
	//
	// // d) Ensure only 2 trucks are in siding 1 (top)
	// // e) Move truck 5 to buffers in top siding
	//
	// // 3) Ensure that truck 4 moves to position 2 of top siding
	// // a) If Truck 4 is in top siding, but not nearest Truck 5, move to other
	// siding
	// // if truck 4 is in position 3, move to position 4 by putting another
	// truck at buffers
	// // If truck is in position 4 or more move trucks to other siding putting
	// in position 3 if possible
	// // b) If Truck 4 is nearest buffer of another siding
	// // Ensure siding has one spare slot
	// // Move a Truck to buffer
	// // Ensure siding has one spare slot
	// // Truck 4 is now in position 2
	// // c) If Truck 4 is in position 2
	// // Move a Truck to buffer
	// // Truck 4 is now in position 3
	// // d) Ensure only 3 trucks are in siding 1 (top)
	// // e) Move truck 4 to position 2 in top siding
	// // 4) Similar for trucks 3 2 and 1.
	//
	// }
	
	protected static K2_Route routeh1;
	protected static K2_Route route1h;
	protected static K2_Route routeh2;
	protected static K2_Route route2h;
	protected static K2_Route routeh3;
	protected static K2_Route route3h;
	
	public static void set_routes(D_MyGraph graph2, C2_DJGraph graphx, C1_BranchGroup branchGroup) {
//		Inglenook.D_MyGraphgraph = graph2;
		routeh1 =  new K2_Route("1_To_Rev,3_To_Rev", graph2, branchGroup);
		route1h = new K2_Route("3_From_Rev,1_From_Rev", graph2, branchGroup);
		routeh2 =  new K2_Route("1_To_Rev,5_To_Rev", graph2, branchGroup);
		route2h = new K2_Route("5_From_Rev,1_From_Rev", graph2, branchGroup);
		routeh3 =  new K2_Route("1_To_Rev,6_To_Rev", graph2, branchGroup);
		route3h = new K2_Route("6_From_Rev,1_From_Rev", graph2, branchGroup);
	
		CreateTrainMovementDeque.graph3 = graphx;
	}

}

