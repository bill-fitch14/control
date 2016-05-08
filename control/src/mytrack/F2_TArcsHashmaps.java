package mytrack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class F2_TArcsHashmaps {

	/**
	 * These Maps are added to when a Arc is added
	 */
	private Map<F1_TArcNames, F3_TArc> tArcNamesArcsMap = new HashMap<F1_TArcNames, F3_TArc>();
	
	private Map<String, F3_TArc> StringTArcsMap = new HashMap<String, F3_TArc>();
	
	private Map<String, F1_TArcNames> StringTArcNamesMap= new HashMap<String, F1_TArcNames>();

	private Map<F3_TArc, F1_TArcNames> tArcsArcNamesMap = new HashMap<F3_TArc, F1_TArcNames>();

	//getters for the maps
	/**
	 * @return the tArcNamesArcsMap
	 */
	public Map<F1_TArcNames, F3_TArc> getArcNamesArcsMap() {
		return tArcNamesArcsMap;
	}

	/**
	 * @return the stringTArcsMap
	 */
	public Map<String, F3_TArc> getStringTArcsMap() {
		return StringTArcsMap;
	}

	/**
	 * @return the stringTArcNamesMap
	 */
	public Map<String, F1_TArcNames> getStringTArcNamesMap() {
		return StringTArcNamesMap;
	}
	
	public Map<F1_TArcNames, F3_TArc> gettArcNamesArcsMap() {
		return tArcNamesArcsMap;
	}

	public Map<F3_TArc, F1_TArcNames> gettArcsArcNamesMap() {
		return tArcsArcNamesMap;
	}
	
	//get the arc name
	public F3_TArc get_TArc(F1_TArcNames tArcName){
		if(tArcNamesArcsMap.containsKey(tArcName)){
			//3//99System.out.print("found tarcName");
			return tArcNamesArcsMap.get(tArcName);
		}else{
			//3//99System.out.print("cant find tarcName");
			return null;
		}
	}
	


	public void addArc(F1_TArcNames tArcNames, E2_TNodesHashmaps tNodes) {

		// Check that the Arc is not already in the Map

		F3_TArc tArc = new F3_TArc(tArcNames, tNodes);
		tArcNames.settArc(tArc);
		
		//3//99System.out.print("fred");
		//3//99System.out.print("length = " + tArc.get_TrackSegments().size());
		if (!tArcNamesArcsMap.containsKey(tArcNames)) {
			tArcNamesArcsMap.put(tArcNames, tArc);
		}
		if (!tArcsArcNamesMap.containsKey(tArcNames)){
			tArcsArcNamesMap.put(tArc, tArcNames);
		}

		//The following maps are used by the routine to plot the train position
		String key = (tArcNames.nodeFromName + "_" + tArcNames.nodeFromDir + "_" +
				tArcNames.nodeToName + "_" + tArcNames.nodeToDir);
		if (!StringTArcsMap.containsKey(key)){
			StringTArcsMap.put(key, tArc);
		}
		if (!StringTArcNamesMap.containsKey(key)){
			StringTArcNamesMap.put(key, tArcNames);
		}
	}
	


	public void printArcPositionsAndTangents() {
        for ( Entry<F1_TArcNames, F3_TArc> entry : tArcNamesArcsMap.entrySet() )
        {

        //1//2//3//99System.out.print( "as Entry: " + entry );

        // this does not require an expensive get lookup to find the value.
        F1_TArcNames key = entry.getKey();
        String valueString = entry.getValue().toString();
        //1//2//3//99System.out.print( "Nodekey: " + key.toString() + "\n" + valueString );
        }// TODO Auto-generated method stub
		
	}



}
