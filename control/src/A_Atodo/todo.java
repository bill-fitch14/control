package A_Atodo;

import java.util.Map;

import mytrack.D_MyGraph;
import mytrack.M76Stop;
import mytrack.U7_StartArcPairs;

/**
 * @author bill
 *
 */
public class todo {
	
	/*
	 * set train
	 * add truck from other train
	 * delete truck from other train
	 * 
	 * try out the 4 directions and see if the train is displayed correctly
	 * in two of the directions it must point the same way, and in the rest it must point the other
	 * 
	 * if we set the connection to head the trucks must go on the front of the engine
	 * if we set the connection to tail the trucks must go on the back of the engine
	 * 
	 * 
	 * move the truck from 0 and see what happens. 
	 * It should catch up if the train is not set up too far from the first sensor
	 * 
	 */
	
	
	
	
	/*
	 * The actions on each event take place in class M_TruckMovements
	 * public static void readDeque1(String[]st){
	 * 
	 * 	case "swapRouteOppDirectionTravelling":
			String strFromBranch = st[1];
			String strToBranch = st[2];
			printreadfromdeque(st,2);
			swapRouteOppDirection(strFromBranch, strToBranch);
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
	 * 
	 * String[] startArcStringArray=U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray2=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray3=U7_StartArcPairs.getStringArraySameDirectionTravellingOppDirectionFacing(this.getStartArcPair(), graph);
	String[] startArcStringArray4=U7_StartArcPairs.getStringArrayOppDirectionTravellingOppDirectionFacing(this.getStartArcPair(), graph);
	
	
	public static String[] getStringArrayOppDirectionTravellingSameDirectionFacing(String arc, String directionFacing, D_MyGraph graph){

		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		String arcString = arc + "_" + oppDirectionFacing(directionFacing);

		String[] sp  = arc.split("_");

		String nodeFrom = sp[0];
		String nodeFromDir = sp[1];
		String nodeTo = sp[2];
		String nodeToDir = sp[3];

		String pairedArc = nodeTo + "_" + nodeToDir + "_" + nodeFrom + "_" + nodeFromDir + "_" + directionFacing; //oppDirectionFacing(directionFacing);
		//as the direction of the route has changed, the direction facing needs to change in order to face the same way
		////99System.out.print("paired arcstring = " + arcString);


		//get the String[] representation

		String[] pairedArcStringArray = IdentToArcStringArrayMap.get(pairedArc);

		return pairedArcStringArray;

	}
	 * 
	 * 
	 * in class M75stops there are stops. these have to correspond to the routes defined in B2_LinkedLists 
	 * but then we do a swap direction in 
	 * 
	 * class M75stops
	 * 
	 * case "st1f1":
			arc = "2_F_3_B";
			fraction = "2.1";
			directionFacing = "Rev";
			//stopNo = "S1F1";
			M76Stop stop_display1f1 = new M76Stop(arc, directionFacing, graph, fraction);
			stop_display1f1.positionWithinSegment3(1.0f);
			
			
			stopLength = 0.5f;
			stopStr = "S1F1";
			stop_display1f1.set_BG(stopLength, stopStr, stopStr, "Engine");
			stop_display1f1.moveWithinSegment3(1.0f);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display1f1);
			
		case "st1f2":
			arc = "2_F_3_B";
			fraction = "2.5";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_display1f2 = new M76Stop(arc, directionFacing, graph, fraction);
			stop_display1f2.positionWithinSegment3(1.0f);
			stopLength = 0.5f;
			stopStr = "S1F2";
			stop_display1f2.set_BG(stopLength, stopStr, stopStr, "Engine");
			stop_display1f2.moveWithinSegment3(1.0f);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display1f2);

		case "st1r1":
			arc = "3_B_2_F";
			fraction = "1.9";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_display1r1 = new M76Stop(arc, directionFacing, graph, fraction);
			stop_display1r1.positionWithinSegment3(0.5f);
			
			
			stopLength = 0.5f;
			stopStr = "S1R1";
			stop_display1r1.set_BG(stopLength, stopStr, stopStr, "Engine");
			stop_display1r1.moveWithinSegment3(0.5f);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display1r1);

		case "st1r2":
			arc = "3_B_2_F";
			fraction = "2.5";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_display1r2 = new M76Stop(arc, directionFacing, graph, fraction);
			stop_display1r2.positionWithinSegment3(0.5f);
			
			
			stopLength = 0.5f;
			stopStr = "S1R2";
			stop_display1r2.set_BG(stopLength, stopStr, stopStr, "Engine");
			stop_display1r2.moveWithinSegment3(0.5f);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display1r2);//	
	 * 
	 * clase B2_LinkedLists
	 * 
	 * train in sth
	 * 		strTruckLength = "0.5";
			
			truckLength = 1;
			route = "1_To_Rev,3_To_Rev";
			startArc = "1_F_2_B";
			startFraction = "1.5";
			startDirection = "Rev";
			trainStr = "T0";
			trainNo = 2; 

			numberEngines = 1; 
			engineLength = 1; 
			truckLength = 1; 			
	 *
	 * now after a swap of movement in class U7_StartArcPairs we get 2_B_1_F_Rev
	 * the stops for this are 
	 * 
	 * 		
	 * 
	 * train in st1
	 * 		route = "3_From_For,1_From_For";
			startArc = "3_B_2_F";		
			startFraction = "4.5";
			startDirection = "For";
	 *		
	 * now after a swap of movement in class U7_StartArcPairs we get 3_B_2_F_Rev
	 * 
	 * st1 goes from 1_F_2_B_Rev to 2_B_1_F_Rev at least by the diagram in docs
	 * 
	 * This takes place in class 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * if we need to deposit a truck we need to change the movement and change the stop, 
	 * so a better strategy would be to set the stop when we know the number of trucks to pick up
	 * 
	 * setting the stop
	 * 
	 * change the movement
	 * in public static void readDeque1(String[]st){
	 * 
	 * change the movement back
	 * 
	 * The place that the commands are set is in class xxxx
	 * 
	 * 
			
	 */

}
