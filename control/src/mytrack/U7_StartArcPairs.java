package mytrack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public final class U7_StartArcPairs {

	//String arc is of form 2_F_4_B_For
	
	public static String[] getStringArraySameDirectionTravellingSameDirectionFacing(String[] startArcPair, D_MyGraph graph) {
		
		//cant use existing values of arc and direction since they have the initial values
		
		String ident = getIdentFromArcStringArray(startArcPair, graph);
//	   //99//99System.out.print("*******");
//		//99System.out.print( Arrays.deepToString((String[]) startArcPair));
//		
//		
//		String ident = ArcStringArrayMaptoIdent.get(startArcPair);
		
		
		String[] ids = ident.split("_");
		
		String arc = StringUtils.join(new String[] { ids[0], ids[1], ids[2],ids[3] }, "_");
		String direction = ids[4];
		
		return getStringArraySameDirectionTravellingSameDirectionFacing(arc, direction, graph);
	}

	public static String getIdentFromArcStringArray(String[] startArcPair, D_MyGraph graph) {
		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		Map<String[], String> ArcStringArrayMaptoIdent =  invert(IdentToArcStringArrayMap);   //need to store this somewhere
		
		
		//99System.out.print("*****");
		
		String ident = null;
	    for (Entry<String[], String> entry : ArcStringArrayMaptoIdent.entrySet()){
	        
	    	//99System.out.print( Arrays.deepToString((String[]) entry.getKey()) + "value" + entry.getValue() + "key");
	    	if(Arrays.equals(entry.getKey(), startArcPair)){
	    		ident = ArcStringArrayMaptoIdent.get(entry.getKey());
	    		//99System.out.print("arrays equal");
	    		break;
	    	}else{
	    		//99System.out.print("arrays not equal");
	    		
	    	}	
	    }
		return ident;
	}
	
	public static String getArcFromIdent(String ident){
		
		String[] ids = ident.split("_");
		
		String arc = StringUtils.join(new String[] { ids[0], ids[1], ids[2],ids[3] }, "_");
		
		return arc;
		
	}
	
	public static String getDirectionFromIdent(String ident){
		
		String[] ids = ident.split("_");
		
		String arc = StringUtils.join(new String[] { ids[0], ids[1], ids[2],ids[3] }, "_");
		String direction = ids[4];
		return direction;
		
	}


	public static String[] getStringArraySameDirectionTravellingSameDirectionFacing(String arc, String direction, D_MyGraph graph){

		//need to translate to
		//[2_To_For, 4_To_For]

		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		String arcString = arc + "_" + direction;
		String[] arcStringArray = IdentToArcStringArrayMap.get(arcString);

		return arcStringArray;

	}
	
	public static String[] getStringArrayOppDirectionTravellingSameDirectionFacing(String[] startArcPair, D_MyGraph graph) {
		
		//cant use existing values of arc and direction since they have the initial values
		
		String ident = getIdentFromArcStringArray(startArcPair, graph);

		String[] ids = new String[5];
		ids = ident.split("_");
		
		String arc = StringUtils.join(new String[] { ids[0], ids[1], ids[2],ids[3] }, "_");
		String direction = ids[4];
		
		return getStringArrayOppDirectionTravellingSameDirectionFacing(arc, direction, graph);
	}

	public static String[] getStringArrayOppDirectionTravellingSameDirectionFacing(String arc, String directionFacing, D_MyGraph graph){

		//need to translate to
		//[4_To_For,2_To_For]????
		
		
		//direction must already have been set up

		  
		//2.5 For
		//need to translate to
		//[2_To_For, 4_To_For] 2.5

		// the train is facing For or Rev, so we can narrow the 4 paths to 2 immediately
		// We dont know if the train is going forward or back (To or For)
		// Assume For, check if that is on route, then assume Rev, check if that is on route if not
		// put up message box to say none are on route

		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		String arcString = arc + "_" + oppDirectionFacing(directionFacing);

		String[] sp  = arc.split("_");

		String nodeFrom = sp[0];
		String nodeFromDir = sp[1];
		String nodeTo = sp[2];
		String nodeToDir = sp[3];
		
		//nodeFromDir = oppDirectionTravelling_new(nodeFromDir);
		//nodeToDir = oppDirectionTravelling_new(nodeToDir);

		String pairedArc = nodeTo + "_" + nodeToDir + "_" + nodeFrom + "_" + nodeFromDir + "_" + directionFacing; //oppDirectionFacing(directionFacing);
		//as the direction of the route has changed, the direction facing needs to change in order to face the same way
		////99System.out.print("paired arcstring = " + arcString);


		//get the String[] representation

		String[] pairedArcStringArray = IdentToArcStringArrayMap.get(pairedArc);

		return pairedArcStringArray;

	}

	static String oppDirectionFacing(String direction){
		if(direction.equals("For")){
			return "Rev";														///IS THIS RIGHT
		} else{
			return "For";
		}
	}
	
	static String oppDirectionTravelling(String direction){
		if(direction.equals("F")){
			return "B";														///IS THIS RIGHT
		} else{
			return "F";
		}	
	}
	
	static String oppDirectionTravelling_new(String direction){
		if(direction.equals("From")){
			return "To";														///IS THIS RIGHT
		} else{
			return "From";
		}	
	}
	
	public static String[] getStringArraySameDirectionTravellingOppDirectionFacing(String[] startArcPair, D_MyGraph graph) {
		
		//cant use existing values of arc and direction since they have the initial values
		
		String ident = getIdentFromArcStringArray(startArcPair, graph);
		
		String[] ids = ident.split("_");
		
		String arc = StringUtils.join(new String[] { ids[0], ids[1], ids[2],ids[3] }, "_");
		String direction = ids[4];
		
		return getStringArraySameDirectionTravellingOppDirectionFacing(arc, direction, graph);
	}
	
	public static String[] getStringArraySameDirectionTravellingOppDirectionFacing(String arc, String directionFacing, D_MyGraph graph){

		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		String arcString = arc + "_" + directionFacing;

		String[] sp  = arc.split("_");

		String nodeFrom = sp[0];
		String nodeFromDir = sp[1];
		String nodeTo = sp[2];
		String nodeToDir = sp[3];


		String pairedArc = nodeTo + "_" + nodeToDir + "_" + nodeFrom + "_" + nodeFromDir + "_" + oppDirectionFacing(directionFacing);
		//as the direction of the route has changed, the direction facing needs to change in order to face the same way
		////99System.out.print("paired arcstring = " + arcString);


		//get the String[] representation

		String[] pairedArcStringArray = IdentToArcStringArrayMap.get(pairedArc);

		return pairedArcStringArray;
	}
	
	public static String[] getStringArrayOppDirectionTravellingOppDirectionFacing(String[] startArcPair, D_MyGraph graph) {
		
		//cant use existing values of arc and direction since they have the initial values
		
		String ident = getIdentFromArcStringArray(startArcPair, graph);
		
		String[] ids = ident.split("_");
		
		String arc = StringUtils.join(new String[] { ids[0], ids[1], ids[2],ids[3] }, "_");
		String direction = ids[4];
		
		return getStringArrayOppDirectionTravellingOppDirectionFacing(arc, direction, graph);
	}
	
	public static String[] getStringArrayOppDirectionTravellingOppDirectionFacing(String arc, String directionFacing, D_MyGraph graph){

		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		String arcString = arc + "_" + directionFacing;

		String[] sp  = arc.split("_");

		String nodeFrom = sp[0];
		String nodeFromDir = sp[1];
		String nodeTo = sp[2];
		String nodeToDir = sp[3];


		String pairedArc = nodeTo + "_" + nodeToDir + "_" + nodeFrom + "_" + nodeFromDir + "_" + oppDirectionFacing(directionFacing);
		//as the direction of the route has changed, the direction facing needs to change in order to face the same way
		////99System.out.print("paired arcstring = " + arcString);


		//get the String[] representation

		String[] pairedArcStringArray = IdentToArcStringArrayMap.get(pairedArc);

		return pairedArcStringArray;
	}


	private static <V, K> Map<V, K> invert(Map<K, V> map) {

	    Map<V, K> inv = new HashMap<V, K>();

	    for (Entry<K, V> entry : map.entrySet()){
	        inv.put(entry.getValue(), entry.getKey());

	    	//99System.out.print("key" + entry.getKey() + "value" + Arrays.deepToString((String[]) entry.getValue()) + "returned value" + inv.get(entry.getValue()));
	    	
	    }
	   //99//99System.out.print("*****");
	    
	    for (Entry<V, K> entry : inv.entrySet()){
	        //inv.put(entry.getValue(), entry.getKey());

	    	//99System.out.print( Arrays.deepToString((String[]) entry.getKey()) + "value" + entry.getValue() + "key");
	    	
	    }
	    return inv;
	}

	public static int getobjectRouteIndex(K2_Route route, String arc, D_MyGraph graph, String directionFacing ){

		/*
		 * The object has been specified by 
		 * 			an arc This ia a string of the form 1_F_2_B_For
		 * 			a direction
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
		//only really need this one
		String[] startArcStringArray=U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(arc, directionFacing, graph);
		
		String[] startArcStringArray2=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(arc, directionFacing, graph);
		String[] startArcStringArray3=U7_StartArcPairs.getStringArraySameDirectionTravellingOppDirectionFacing(arc, directionFacing, graph);
		String[] startArcStringArray4=U7_StartArcPairs.getStringArrayOppDirectionTravellingOppDirectionFacing(arc, directionFacing, graph);
		


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
					
//					String startArcPairKey = getEngineRoutePairKey(truckdata_position.getStartArcPair());
//					String truckOrientation = graph.getOrientation(startArcPairKey);
//					String truckMovement = graph.getMovement(startArcPairKey);
					return j;

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
		return -1;  //need to check for this incorrect value in calling routine
	}
}