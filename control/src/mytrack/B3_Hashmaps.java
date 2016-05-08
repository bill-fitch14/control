package mytrack;

import java.util.List;
import java.util.ListIterator;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class B3_Hashmaps {
	
	/**
	 * For this class we have B2_Linked Lists as the starting point
	 * 
	 * From the names in these links we get the items (nodes, arcs, points) 
	 * We do this by using the AddItem (AddNode AddArc) methods 
	 * These implicitly create the Node by looking ant the information in the name
	 * 
	 * This routine thus sets up the hashmaps linking the Names to Items, and implicitly
	 * puts parameters in all the Items
	 * 
	 * Classes that are inherently called for the nodes are
	 * _TNodesHashmap.AddNode(nextNodeName);
	 * E2_TNodesHashmaps
	 * E3_TNode
	 * 
	 * Similarly for Arcs
	 * 
	 */

	private B2_LinkedLists _linkedLists;
	
	/**
	 * Hashmap referencing each node by the Nodename
	 */
	private E2_TNodesHashmaps _TNodesHashmap = new E2_TNodesHashmaps();
	
	/**
	 * Hashmap referencing each arc by the arcName
	 */
	private F2_TArcsHashmaps _TArcsHashmap = new F2_TArcsHashmaps();
	
	/**
	 * Hashmap referencing each point by the arcName
	 */
	
	private I2_PointsHashmaps _PointsHashmap = new I2_PointsHashmaps();
	/**
	 * Routines to set the Hash maps of the Nodes and Arcs
	 */
	
	public void calculateHashmapsAndParameters(){

		set_NodeHashmap();
		set_ArcHashmap();
		
		//set_PointsHashmap();
		
	}


	public void set_NodeHashmap(){
		//2//3//99System.out.print("linked lists" + _linkedLists.toString());
		Point3d _startposition = _linkedLists.get_initialNodePosition();
		Vector3d _starttangent = _linkedLists.get_initialNodeTangent();
		//_startposition = new Point3d(0,0,1);
		//_starttangent = new Vector3d(1,0,0);
		
		// Iterating through elements of Java LinkedList using
		// ListIterator in forward direction.
		List<E1_TNodeNames> NodeLinkedList = _linkedLists.get_NodeLinkedList();
		ListIterator<E1_TNodeNames> itr = NodeLinkedList.listIterator();
		int counter = 0;	
		while(itr.hasNext()){
			E1_TNodeNames nextNodeName = itr.next();
			
			//add all the nodes etc.
			if (counter == 0){
				_TNodesHashmap.AddNode(nextNodeName, _startposition, _starttangent);
			} else{
				_TNodesHashmap.AddNode(nextNodeName);
			}
			//2//3//99System.out.print("Linked list " + nextNodeName.toString());
			counter++;
		}
		
	}
	
	public void set_ArcHashmap(){

		// Iterating through elements of Java LinkedList using
		// ListIterator in forward direction.

		List<F1_TArcNames> arcLinkedList = _linkedLists.get_ArcLinkedList();
		ListIterator<F1_TArcNames> itr = arcLinkedList.listIterator();

		while(itr.hasNext()){
			F1_TArcNames next = itr.next();
			//1//2//3//99System.out.print("Linked list " + next.toString());
			//add all the Arcs etc.
			_TArcsHashmap.addArc(next,_TNodesHashmap);

		}
		
	}
	
	public void set_PointsHashmap(){

		// Iterating through elements of Java LinkedList using
		// ListIterator in forward direction.

		List<I1_PointNames> pointLinkedList = _linkedLists.get_PointLinkedList();
		ListIterator<I1_PointNames> itr = pointLinkedList.listIterator();

		while(itr.hasNext()){
			I1_PointNames next = itr.next();
			//1//2//3//99System.out.print("Linked list " + next.toString());
			//add all the Arcs etc.
			//_PointsHashmap.addPoint(next,_PointsHashmap);

		}
		
	}

	public void setArcAndNodeLinkedLists(B2_LinkedLists linkedLists) {
		_linkedLists = linkedLists;
	}


	public E2_TNodesHashmaps get_TNodesHashmap() {
		return _TNodesHashmap;
	}


	public F2_TArcsHashmaps get_TArcsHashmap() {
		return _TArcsHashmap;
	}


	public I2_PointsHashmaps get_PointsHashmap() {
		return _PointsHashmap;
	}

	public B2_LinkedLists get_linkedLists() {
		return _linkedLists;
	}
}
