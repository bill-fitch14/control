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
		
		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		Map<String[], String> ArcStringArrayMaptoIdent = invert(IdentToArcStringArrayMap);   //need to store this somewhere
		
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
		
		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		Map<String[], String> ArcStringArrayMaptoIdent = invert(IdentToArcStringArrayMap);   //need to store this somewhere	
		
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
		
		Map<String, String[]> IdentToArcStringArrayMap = graph.getIdentToArcStringArrayMap();

		Map<String[], String> ArcStringArrayMaptoIdent = invert(IdentToArcStringArrayMap);   //need to store this somewhere
		
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

}