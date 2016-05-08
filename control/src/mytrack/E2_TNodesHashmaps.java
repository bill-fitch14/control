package mytrack;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


public class E2_TNodesHashmaps {

	/**
	 * _TNodesDict is added to when a node is added
	 */
	private Map <E1_TNodeNames ,E3_TNode > _TNodesMap = new HashMap<E1_TNodeNames,E3_TNode>();
	private Map <String, E1_TNodeNames> _TNodeNamesMap = new HashMap<String,E1_TNodeNames>();

	public E2_TNodesHashmaps() {
	}

	E3_TNode get_TNode(E1_TNodeNames tNodeNames){
		//    	//1//2//3//99System.out.print ("get_TNode: Map is empty : " + _TNodesMap.isEmpty());
		return _TNodesMap.get(tNodeNames);
	}

	public Boolean AddNode(E1_TNodeNames tNodeName) {

		//Check that the node is not already in the Map
		if (!_TNodesMap.containsKey ( tNodeName ))
		{
			//create the node with all the characteristics
			E3_TNode TN = new E3_TNode ( tNodeName );
			//1//2//3//99System.out.print(TN.toString());
			//store the node in the hashmap
			////1//2//3//99System.out.print ("put _TNodesMap before: Map is empty : " + _TNodesMap.isEmpty());
			_TNodesMap.put ( tNodeName,TN );
			// //1//2//3//99System.out.print ("put _TNodesMap after: Map is empty : " + _TNodesMap.isEmpty());
			_TNodeNamesMap.put(tNodeName.getNodeName(), tNodeName);
			return true;
		}
		else
		{
			return false;
		}
	}

	public Boolean DeleteNode(E1_TNodeNames tNodeNames){
		if (_TNodesMap.containsKey ( tNodeNames ))
		{
			_TNodesMap.remove(tNodeNames) ;
			_TNodeNamesMap.remove(tNodeNames.getNodeName());
			return true;
		}
		else
		{
			return false;
		}
	}


	public Map<E1_TNodeNames, E3_TNode> get_TNodesMap() {
		return _TNodesMap;
	}

	public Map<String, E1_TNodeNames> get_TNodeNamesMap() {
		return _TNodeNamesMap;
	}

	public Boolean AddNode(E1_TNodeNames tNodeName, Point3d position, Vector3d tangent) {
		//Check that the node is not already in the Map
		if (!_TNodesMap.containsKey ( tNodeName ))
		{
			//create the node with all the characteristics
			E3_TNode TN = new E3_TNode ( tNodeName , position, tangent);
			//1//2//3//99System.out.print(tNodeName.toString() + TN.toString());
			//store the node in the hashmap
			//            //1//2//3//99System.out.print ("put _TNodesMap before: Map is empty : " + _TNodesMap.isEmpty());
			_TNodesMap.put ( tNodeName,TN );
			////1//2//3//99System.out.print(TN.toString());
			//            //1//2//3//99System.out.print ("put _TNodesMap after: Map is empty : " + _TNodesMap.isEmpty());
			_TNodeNamesMap.put(tNodeName.getNodeName(), tNodeName);
			return true;
		}
		else
		{
			return false;
		}

	}

	public void printNodePositionsAndTangents() {

		for ( Map.Entry<E1_TNodeNames, E3_TNode> entry : _TNodesMap.entrySet() )
		{
			// prints lines of the form NY=New York
			// in effectively random order.
			// //1//2//3//99System.out.print( "as Entry: " + entry );

			// this does not require an expensive get lookup to find the value.
			E1_TNodeNames key = entry.getKey();
			String valueString = entry.getValue().toString();
			//1//2//3//99System.out.print( "Node: " + key.getNodeName() + " " + valueString );
		}
	}
}
