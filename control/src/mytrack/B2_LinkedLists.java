package mytrack;

import java.awt.Component;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.vecmath.Point3d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import com.google.common.primitives.Ints;

import A_Inglenook.Myfunctions;

public class B2_LinkedLists  {

	/**
	 * The JTree which produces the graph
	 */
	private JTree _jTree = new JTree();

	// /**
	// * The Linked Lists containing strings derived from the graphs
	// */
	// private D_MyLinkedListsAndHashmaps _L = new D_MyLinkedListsAndHashmaps();

	/**
	 * Global variables used by hashmaps
	 */
	String _nodeFromName;
	String _nodeToName;
	String _nodeFromDir;
	String _nodeToDir;

	String nodeName = null;
	String nodeDirection = null;
	String curve = null;
	String pointNumber = null;
	private String nodeNumber = null;

	/**
	 * The maps of names and TNodes produced from the JTree
	 */
	private List<E1_TNodeNames> _NodeLinkedList;
	/**
	 * The maps of arcs and TArcs produced from the JTree
	 */
	private List<F1_TArcNames> _ArcLinkedList;

	private List<I1_PointNames> _PointLinkedList;

	private String[] _pointarray;

	// pointsList has elements 1_F_ST
	private LinkedList<String> pointsList;
	private LinkedList<String> nodesList;

	private Tuple3d _initialNodeTangent = new Vector3d(0, 0, 0);

	private Tuple3d _initialNodePosition = new Point3d(0, 0, 0);

	private LinkedList<String> _oldPointsList;

	private LinkedList<String> _oldNodesList;

	private LinkedList<String> newPointsList;

	private String stopPoints = null;

	private String[] _segments;

	private F1_TArcNames _arcEquiv;

	private static String strEngineLength = null;
	private static String strNoTrucks = null;
	private static Integer[] truckNames = null;
	private static String strTruckLength = null;
	private static String route = null;
	private String showTrains = null;
	private static String startFraction = null;
	private static String startArc = null;
	private static String startDirection = null;
	private static int trainNo = 0;
	private String trainStr;

	private String straight;
	private static String strEngineColor = null;

	// public void produceArcandNodeLinkedLists(JTree jTree){
	// _jTree = jTree;
	// // set up the node linked list
	// this.traverseTreeSettingUpTrackNodesAndArcs();
	// expandAll(_jTree, true);
	// }

	/**
	 * This Routine to set the Node and Arc Linked Lists and sets the points In
	 * fact it will convert the Jtree into linked lists and variables
	 * 
	 * Called from Toplevel
	 * 
	 */
	public void traverseTreeSettingUpTrackNodesAndArcs() {
		_NodeLinkedList = new LinkedList<E1_TNodeNames>();
		_ArcLinkedList = new LinkedList<F1_TArcNames>();
		// 2//3//99System.out.print("traverseTreeSettingUpTrackNodesAndArcs");
		int _level = 0;
		_level = 0;
		if (_jTree != null) {
			DefaultTreeModel model = (DefaultTreeModel) _jTree.getModel();
			if (model != null) {
				Object root = model.getRoot();
				// 1//2//3//99System.out.print(root.toString());
				_level = 1;
				// Get a list of the existing nodes
				_oldNodesList = new LinkedList<String>();
				_level = GetOldListOfNodeswalk(model, root, _level);
				// Initialise the Node List
				nodesList = new LinkedList<String>();
				_level = ProduceNodesFromArcsWalk(model, root, _level);
				AddNodesFromNodeList();
				// Add nodes and arcs to maps
				_level = Nodewalk(model, root, _level);
				_level = Arcwalk(model, root, _level);

				// Get a list of the existing points

				_oldPointsList = new LinkedList<String>();
				_level = GetOldListOfPointswalk(model, root, _level);

				// 2//3//99System.out.print("_oldPointsList:" +
				// _oldPointsList.toString());

				// Go through the arcs and produce a new list of points
				// 1 Get a list of ends of arcs
				pointsList = new LinkedList<String>();
				_level = ProducePointsFromArcsWalk(model, root, _level);
				// 2 Get the new list of points
				GetListOfPoints();
				AddPointsFromPointsList();

				// Add points to maps
				_level = Pointwalk(model, root, _level);
				_level = GetInitialNodePositionWalk(model, root, _level);

			}

			else {
				System.out.print("Tree is empty.");
			}
		} else
			System.out.print("JTree is null");
	}

	private int GetOldListOfPointswalk(DefaultTreeModel model, Object o,
			int _level) {

		int cc;
		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			Object child = (MutableTreeNode) model.getChild(o, i);
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			if ((_level == 1 && child.toString().equals("track")) || _level > 1)
				// check all the possibilities
				if (_level == 1) {
					// just go to next level
					// 2//3//99System.out.print("level:" + _level +
					// " GetExistingPointsWalk " + child.toString());

				} else if (_level == 2 && child.toString().equals("points")) {
					// just go to next level
					// 2//3//99System.out.print("level:" + _level +
					// " GetExistingPointsWalk " + child.toString());

				}
				// else if (_level == 3 ){
				// //just go to next level
				// //2//3//99System.out.print("level:" + _level +
				// " GetExistingPointsWalk " + child.toString());
				//
				//
				// }
				else if (_level == 3) {
					// 2//3//99System.out.print("level:" + _level +
					// " GetExistingPointsWalk " + child.toString());

					String pointString = child.toString();
					// String pointName;
					// if (pointString.matches(".*_.*")){
					// String[] pointArray = pointString.split("[_|\\s]");
					// pointName = pointArray[0];
					// }
					// else{
					// pointName = pointString;
					// }
					if (!_oldPointsList.contains(pointString)) {
						_oldPointsList.add(pointString);
					}
				}

			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals("points"))) {
					_level++;
					_level = GetOldListOfPointswalk(model, child, _level);
					_level--;
				}
			}
		}
		return _level;
	}

	// ***************************************************************************************
	/**
	 * Gets the initial node position entries on the JTree
	 * 
	 * calls GetInitialNodePosition calls GetInitialNodeTangent
	 * 
	 * eventually sets _initialNodePosition x,y,z _initialNodeTangent x,y,z
	 * 
	 * 
	 * @param model
	 * @param o
	 * @param _level
	 * @return
	 */
	private int GetInitialNodePositionWalk(DefaultTreeModel model, Object o,
			int _level) {

		int cc;
		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			Object child = (MutableTreeNode) model.getChild(o, i);
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			if ((_level == 1 && child.toString().equals("track")) || _level > 1)
				// check all the possibilities
				if (_level == 1) {
					// just go to next level
					// 2//3//99System.out.print("level:" + _level +
					// " GetInitialNodePositionWalk " + child.toString());

				} else if (_level == 2
						&& child.toString().equals("initial node")) {
					// just go to next level
					// 2//3//99System.out.print("level:" + _level +
					// " GetInitialNodePositionWalk " + child.toString());

				} else if (_level == 3) {
					// just go to next level
					// 2//3//99System.out.print("level:" + _level +
					// " GetInitialNodePositionWalk " + child.toString());

				} else if (_level == 4) {
					// 2//3//99System.out.print("level:" + _level +
					// " GetInitialNodePositionWalk " + child.toString());

					MutableTreeNode parent = (MutableTreeNode) o;
					if (parent.toString().equals("position")) {
						GetInitialNodePosition(child.toString());
					} else if (parent.toString().equals("tangent")) {
						GetInitialNodeTangent(child.toString());
					}
				}

			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals(
								"initial node")) || (_level == 3)) {
					_level++;
					_level = GetInitialNodePositionWalk(model, child, _level);
					_level--;
				}
			}
		}
		return _level;

	}

	private void GetInitialNodePosition(String initNodestring) {

		// _initialNodePosition = new Point3d(0,0,0);
		Tuple3d temp = new Vector3d(0, 0, 0);
		if (initNodestring.matches(".*_.*")) {
			String[] initNodearray = initNodestring.split("[_|\\s]");

			double value = U3_Utils.CStringToDouble(initNodearray[1]);
			// 2//3//99System.out.print("value =" + value);
			if (initNodearray[0].equals("x")) {
				_initialNodePosition.setX(value);
			} else if (initNodearray[0].equals("y")) {
				_initialNodePosition.setY(value);
			} else if (initNodearray[0].equals("z")) {
				_initialNodePosition.setZ(value);;
			}
		}

	}

	private void GetInitialNodeTangent(String initNodestring) {



		if (initNodestring.matches(".*_.*")) {
			String[] initNodearray = initNodestring.split("[_|\\s]");
			double value = U3_Utils.CStringToDouble(initNodearray[1]);
			if (initNodearray[0].equals("x")) {
				_initialNodeTangent.setX(value);
			} else if (initNodearray[0].equals("y")) {
				_initialNodeTangent.setY(value);
			} else if (initNodearray[0].equals("z")) {
				_initialNodeTangent.setZ(value);
			}
		}

	}

	// ***************************************************************************************

	/**
	 * ProducePointsFromArcsWalk
	 * 
	 * reads the edges on the graph
	 * 
	 * calls GetPointsFromArcStringStage1 GetPointsFromArcStringStage2 which
	 * produces pointsList which is a list of items of form 1_F_ST
	 * 
	 * then GetListOfPoints checks whether there are 2 starting with the same
	 * prefix 1_F if so puts them in pointsList2 which has elements of form
	 * 
	 * eventually creates new entries under track points by calling
	 * 
	 * then produces
	 * 
	 * @param model
	 * @param o
	 * @param _level
	 * @return
	 */
	private int ProducePointsFromArcsWalk(DefaultTreeModel model, Object o,
			int _level) {

		int cc;
		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			TreeNode[] path = model.getPathToRoot((TreeNode) o);
			TreeNode grandParent = null;
			if (path.length >= 2) {
				grandParent = path[path.length - 2];
				// 3//99System.out.print.print("GrandParent: " +
				// grandParent.toString());
			}
			Object child = model.getChild(o, i);
			// 2//3//99System.out.print(" Parent: " + o.toString() + " Child " +
			// child.toString());
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			// check all the possibilities
			if (_level == 1) {
				// just go to next level
			} else if (_level == 2 && child.toString().equals("edges")) {
				// just go to next level
			} else if (_level == 3) {
				GetPointsFromArcStringStage1(child.toString());
			} else if (_level == 4) {
				GetPointsFromArcStringStage2(child.toString());
			}

			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals("edges"))
						|| (_level == 3)) {
					_level++;
					_level = ProducePointsFromArcsWalk(model, child, _level);
					_level--;
				}
			}
		}
		return _level;
	}

	private void GetPointsFromArcStringStage1(String arcString) {
		if (arcString.matches(".*_.*")) {
			_pointarray = arcString.split("[_|\\s]");
		}
	}

	private void GetPointsFromArcStringStage2(String arcString) {
		if (arcString.matches(".*,.*")) {
			String[] pointarray2 = arcString.split("[,|\\s]");

			String firstitem = pointarray2[0];
			String lastitem = pointarray2[pointarray2.length - 1];

			// form the two point strings
			String points[] = {
					_pointarray[0] + "_" + _pointarray[1] + "_" + firstitem,
					_pointarray[2] + "_" + _pointarray[3] + "_" + lastitem };

			// 2//3//99System.out.print(" jkgkjgjg " + points[0] + " " +
			// points[1]);

			for (String point : points) {
				if (!pointsList.contains(point)) {
					pointsList.add(point);
				}
			}

		} else if (!(arcString == null)) {
			// assume there is only one element
			String firstitem = arcString;
			String lastitem = arcString;

			// form the two point strings
			String points[] = {
					_pointarray[0] + "_" + _pointarray[1] + "_" + firstitem,
					_pointarray[2] + "_" + _pointarray[3] + "_" + lastitem };

			// 2//3//99System.out.print(" jkgkjgjg " + points[0] + " " +
			// points[1]);

			for (String point : points) {
				if (!pointsList.contains(point)) {
					pointsList.add(point);
				}
			}
		}
	}

	private void GetListOfPoints() {

		newPointsList = new LinkedList<String>();

		// pointsList has elements 1_F_ST
		// need to check whether there are two starting with the same 1_F
		for (String point1 : pointsList) {
			// 2//3//99System.out.print("point1: " + point1);
			// string is of form 1_F_RC
			String[] items = point1.split("_");
			String check = items[0] + "_" + items[1];
			for (String point2 : pointsList) {
				// 2//3//99System.out.print("point2: " + point1);
				String[] items2 = point2.split("_");
				String check2 = items2[0] + "_" + items2[1];
				if (check.equals(check2)) {
					// 2//3//99System.out.print("equals " + point1 + " " +
					// point2);
					// 2//3//99System.out.print("items " + items[2] + " " +
					// items2[2]);
					if (!items[2].equals(items2[2])) {
						String[] pointsType = { items[2], items2[2] };
						Arrays.sort(pointsType, Collections.reverseOrder());
						String Strpoint = check + "_" + pointsType[0] + "_"
								+ pointsType[1];
						if (!(newPointsList.contains(Strpoint))) {
							// 2//3//99System.out.print("adding " + Strpoint );
							newPointsList.add(Strpoint);
						}
						// 2//3//99System.out.print("removing " +
						// point2.toString());
						// pointsList.remove(point2);

						// //2//3//99System.out.print(pointsList.size());
					}
				}
				// //2//3//99System.out.print("removing " +
				// pointsList.get(0).toString());
				// pointsList.remove(pointsList.get(0));
				// //2//3//99System.out.print(pointsList.size());
			}
		}
	}

	private void AddPointsFromPointsList() {

		// need to add nodes at location root,track,nodes
		// U2_Tree uTree = new U2_Tree();
		// get path to location
		TreePath path;
		try {
			path = U2_Tree.findByName(_jTree, new String[] { "root", "track",
					"points" });
		} catch (Exception e) {
			path = U2_Tree.findByName(_jTree, new String[] { "root", "track" });
			MutableTreeNode node = (MutableTreeNode) path
					.getLastPathComponent();
			DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(
					"points");
			((DefaultTreeModel) _jTree.getModel()).insertNodeInto(newChild,
					node, 0);
			path = U2_Tree.findByName(_jTree, new String[] { "root", "track",
					"points" });
		}

		// delete nodes at path
		MutableTreeNode node = (MutableTreeNode) path.getLastPathComponent();

		// // Remove node; if node has descendants, all descendants are removed
		// as well
		// ((DefaultTreeModel) _jTree.getModel()).removeNodeFromParent(node);
		//
		// // Put the node back
		// path = U2_Tree.findByName(_jTree, new String[]{"root", "track"});
		// node = (MutableTreeNode)path.getLastPathComponent();
		// MutableTreeNode newChild = new
		// SortableDefaultMutableTreeNode("points");
		// //((SortableDefaultMutableTreeNode) node).add(newChild);
		// ((DefaultTreeModel)_jTree.getModel()).insertNodeInto(newChild, node,
		// 0);

		// 2//3//99System.out.print("newPointsList: " + newPointsList.toString());
		// 2//3//99System.out.print("oldPointsList: " +
		// _oldPointsList.toString());
		// Delete the Old points not in the new points list
		String pointelement;
		String pointelementnew;

		for (String pointsname : _oldPointsList) {
			boolean matches = false;
			if (pointsname.matches(".*_.*")) {
				String[] items = pointsname.split("[_|\\s]");
				pointelement = items[0];
			} else {
				pointelement = pointsname;
			}
			// check if no item matches in the new list, if not delete the old
			// item
			for (String pointitem : newPointsList) {
				if (pointitem.matches(".*_.*")) {
					String[] items = pointitem.split("[_|\\s]");
					pointelementnew = items[0];
				} else {
					pointelementnew = pointitem;
				}
				if (pointelement.equals(pointelementnew)) {
					matches = true;
				}
			}

			if (matches == false) {
				// 2//3//99System.out.print("trying to remove " + pointsname);
				TreePath path2 = U2_Tree.findByName(_jTree, new String[] {
						"root", "track", "points", pointsname });
				node = (MutableTreeNode) path2.getLastPathComponent();
				((DefaultTreeModel) _jTree.getModel())
						.removeNodeFromParent(node);
			}

		}

		// Add the new points not in the old points list
		// String pointelement;
		String pointelementold = null;

		for (String pointsname : newPointsList) {
			boolean matches = false;
			if (pointsname.matches(".*_.*")) {
				String[] items = pointsname.split("[_|\\s]");
				pointelement = items[0];
			} else {
				pointelement = pointsname;
			}

			for (String pointitem : _oldPointsList) {
				if (pointitem.matches(".*_.*")) {
					String[] items = pointitem.split("[_|\\s]");
					pointelementold = items[0];
				} else {
					pointelementold = pointitem;
				}
				if (pointelement.equals(pointelementold)) {
					matches = true;
				}
			}

			if (_oldPointsList.isEmpty() || !matches) {
				TreePath path2 = U2_Tree.findByName(_jTree, new String[] {
						"root", "track", "points" });
				node = (MutableTreeNode) path2.getLastPathComponent();
				MutableTreeNode newChild = new SortableDefaultMutableTreeNode(
						pointsname);
				((DefaultTreeModel) _jTree.getModel()).insertNodeInto(newChild,
						node, 0);
			}

		}

		// path = U2_Tree.findByName(_jTree, new String[]{"root", "track",
		// "points"});
		// node = (MutableTreeNode)path.getLastPathComponent();
		// for (String pointsname : pointsList2){
		// newChild = new DefaultMutableTreeNode(pointsname);
		// ((DefaultTreeModel) _jTree.getModel()).insertNodeInto(newChild, node,
		// 0);
		// // addtoPointslinkedlist(pointsname);
		// }
	}

	// ****************************
	private int Pointwalk(DefaultTreeModel model, Object o, int _level) {
		_PointLinkedList = new LinkedList<I1_PointNames>();
		int cc;
		cc = model.getChildCount(o);
		// 2//3//99System.out.print("PointWalk");
		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(o, i);
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			if ((_level == 1 && child.toString().equals("track")) || _level > 1)
				// check all the possibilities
				if (_level == 1) {
					// 2//3//99System.out.print("level:" + _level +
					// " adding point " + child.toString());
					// just go to next level
				} else if (_level == 2 && child.toString().equals("points")) {
					// 2//3//99System.out.print("level:" + _level +
					// " adding point " + child.toString());
					// just go to next level
				} else if (_level == 3) {
					// 2//3//99System.out.print("level:" + _level +
					// " adding point " + child.toString());
					addtoPointlinkedlist(model, child);
				}
			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals("points"))) {
					_level++;
					_level = Pointwalk(model, child, _level);
					_level--;
				}
			}

		}
		return _level;
	}

	private void addtoPointlinkedlist(DefaultTreeModel model, Object o) {

		int cc;
		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(o, i);
			String pointString = child.toString();
			if (child.toString().matches(".*_.*")) {
				String[] pointFeatures = pointString.split("_");
				if (pointFeatures[0].equals("NodeNo")) {
					nodeNumber = pointFeatures[1];
				} else if (pointFeatures[0].equals("PointNo")) {
					pointNumber = pointFeatures[1];
				}
			}
		}

		String pointString = o.toString();
		// split node
		if (pointString.matches(".*_.*")) {
			String[] nodeFeatures = pointString.split("_");
			nodeName = nodeFeatures[0];
			nodeDirection = nodeFeatures[1];
			straight = nodeFeatures[2];
			curve = nodeFeatures[3];
		} else {
			nodeName = pointString;
		}

		I1_PointNames pointNames = new I1_PointNames(nodeName, nodeDirection,
				curve, straight, nodeNumber, pointNumber);
		if (!_PointLinkedList.contains(pointNames)) {

			_PointLinkedList.add(pointNames);
		} else {
			// raise exception
		}

	}

	// //****************************

	private int ProduceNodesFromArcsWalk(DefaultTreeModel model, Object o,
			int _level) {
		// this needs to be done after the nodes have been set up

		int cc;

		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			TreeNode[] path = model.getPathToRoot((TreeNode) o);
			TreeNode grandParent = null;
			if (path.length >= 2) {
				grandParent = path[path.length - 2];
				// 3//99System.out.print.print("GrandParent: " +
				// grandParent.toString());

			}
			Object child = model.getChild(o, i);
			// 2//3//99System.out.print(" Parent: " + o.toString() + " Child " +
			// child.toString());

			// Object child = model.getChild(o, i );
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			if ((_level == 1 && child.toString().equals("track"))
					|| (_level == 2 && child.toString().equals("edges"))
					|| (_level == 3))
				// check all the possibilities
				if (_level == 1) {
					// just go to next level
				} else if (_level == 2 && child.toString().equals("edges")) {
					// just go to next level
				} else if (_level == 3) {
					GetNodesFromArcString(child.toString());
				}

			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals("edges"))) {
					_level++;
					_level = ProduceNodesFromArcsWalk(model, child, _level);
					_level--;
				}
			}
		}
		return _level;
	}

	private void GetNodesFromArcString(String arcString) {
		// the ArcString is of the form 1_F_2_B
		// Add the 1 and 2 to a list of nodes
		if (arcString.matches(".*_.*")) {
			String[] items = arcString.split("[_|\\s]");
			// 2//3//99System.out.print(arcString.toString());
			String nodes[] = { items[0], items[2] };
			for (String item : nodes) {
				if (!nodesList.contains(item)) {
					nodesList.add(item);
				}
			}
		}
	}

	private int GetOldListOfNodeswalk(DefaultTreeModel model, Object o,
			int _level) {

		int cc;
		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			Object child = (MutableTreeNode) model.getChild(o, i);
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			if ((_level == 1 && child.toString().equals("track")) || _level > 1)
				// check all the possibilities
				if (_level == 1) {
					// just go to next level
					// 2//3//99System.out.print("level:" + _level +
					// " GetExistingPointsWalk " + child.toString());

				} else if (_level == 2 && child.toString().equals("nodes")) {
					// just go to next level
					// 2//3//99System.out.print("level:" + _level +
					// " GetExistingPointsWalk " + child.toString());

				}
				// else if (_level == 3 ){
				// //just go to next level
				// //2//3//99System.out.print("level:" + _level +
				// " GetExistingPointsWalk " + child.toString());
				//
				//
				// }
				else if (_level == 3) {
					// 2//3//99System.out.print("level:" + _level +
					// " GetInitialNodePositionWalk " + child.toString());

					String pointString = child.toString();
					String pointName;
					if (pointString.matches(".*_.*")) {
						String[] pointArray = pointString.split("[_|\\s]");
						pointName = pointArray[0];
					} else {
						pointName = pointString;
					}
					if (!_oldNodesList.contains(pointName)) {
						_oldNodesList.add(pointName);
					}
				}

			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals("nodes"))) {
					_level++;
					_level = GetOldListOfNodeswalk(model, child, _level);
					_level--;
				}
			}
		}
		return _level;
	}

	private void AddNodesFromNodeList() {

		// need to add nodes at location root,track,nodes
		// U2_Tree uTree = new U2_Tree();
		// get path to location
		TreePath path = U2_Tree.findByName(_jTree, new String[] { "root",
				"track", "nodes" });
		// delete nodes at path
		MutableTreeNode node = (MutableTreeNode) path.getLastPathComponent();

		// Delete the Old Nodes not in the new node list
		String nodeelement;
		for (String nodesname : _oldNodesList) {

			if (nodesname.matches(".*_.*")) {
				String[] items = nodesname.split("[_|\\s]");
				nodeelement = items[0];
			} else {
				nodeelement = nodesname;
			}

			if (!nodesList.contains(nodeelement)) {
				TreePath path2 = U2_Tree.findByName(_jTree, new String[] {
						"root", "track", "nodes", nodeelement });
				node = (MutableTreeNode) path2.getLastPathComponent();
				((DefaultTreeModel) _jTree.getModel())
						.removeNodeFromParent(node);
			}

		}

		// Add the new nodes not in the old node list
		// String pointelement;
		for (String pointsname : nodesList) {

			if (pointsname.matches(".*_.*")) {
				String[] items = pointsname.split("[_|\\s]");
				nodeelement = items[0];
			} else {
				nodeelement = pointsname;
			}

			if (!_oldNodesList.contains(nodeelement)) {
				TreePath path2 = U2_Tree.findByName(_jTree, new String[] {
						"root", "track", "nodes" });
				node = (MutableTreeNode) path2.getLastPathComponent();
				MutableTreeNode newChild = new SortableDefaultMutableTreeNode(
						nodeelement);
				((DefaultTreeModel) _jTree.getModel()).insertNodeInto(newChild,
						node, 0);
			}

		}

	}

	// private void UpdateTheNodeList(MutableTreeNode o, LinkedList<String>
	// nodesList2) {
	// // TODO Auto-generated method stub
	// cc = model.getChildCount(o);
	// //2//3//99System.out.print("NodeWalk");
	// for( int i=0; i < cc; i++) {
	// Object child = model.getChild(o, i );
	// }

	// If expand is true, expands all nodes in the tree.
	// Otherwise, collapses all nodes in the tree.
	public void expandAll(JTree tree, boolean expand) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();

		// Traverse tree from root
		expandAll(tree, new TreePath(root), expand);
	}

	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		// Traverse children
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}

		// Expansion or collapse must be done bottom-up
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	// as we are using add need to call following
	// if we had used insertNodeInto() we would not have to call this, but then
	// there
	// would be lots of implicit TreeStructureChanged events fired
	// ((DefaultTreeModel) _jTree.getModel()).nodeStructureChanged(node);

	private int Nodewalk(DefaultTreeModel model, Object o, int _level) {
		int cc;
		cc = model.getChildCount(o);
		// 2//3//99System.out.print("NodeWalk");
		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(o, i);
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			if ((_level == 1 && child.toString().equals("track")) || _level > 1)
				// check all the possibilities
				if (_level == 1) {
					// 2//3//99System.out.print("level:" + _level +
					// " adding node " + child.toString());
					// just go to next level
				} else if (_level == 2 && child.toString().equals("nodes")) {
					// 2//3//99System.out.print("level:" + _level +
					// " adding node " + child.toString());
					// just go to next level
				} else if (_level == 3) {
					// 2//3//99System.out.print("level:" + _level +
					// " adding node " + child.toString());
					addtoNodelinkedlist(child.toString());
				}
			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals("nodes"))) {
					_level++;
					_level = Nodewalk(model, child, _level);
					_level--;
				}
			}
		}
		return _level;
	}

	private void addtoNodelinkedlist(String nodeString) {

		String nodeName = null;
		String reverse = "F";
		String station = "F";
		String crossing = "F";
		String displaynode = "F";
		String reversingAllowed = "T";
		// split node
		if (nodeString.matches(".*_.*")) {

			String[] nodeFeatures = nodeString.split("_");
			nodeName = nodeFeatures[0];
			for (String item : nodeFeatures) {
				if (item.equals("R")) {
					reversingAllowed = "T";
				} else if (item.equals("S")) {
					station = "T";
				} else if (item.equals("C")) {
					crossing = "T";
				} else if (item.equals("N")) {
					displaynode = "T";
				}
			}
		} else {
			nodeName = nodeString;
			station = "T";
			displaynode = "T";
		}

		E1_TNodeNames tNodeNames = new E1_TNodeNames(nodeName, reverse,
				station, crossing, reversingAllowed, displaynode);
		if (!_NodeLinkedList.contains(nodeName)) {

			_NodeLinkedList.add(tNodeNames);
		} else {
			// raise exception
		}

	}

	protected int Arcwalk(DefaultTreeModel model, Object o, int _level) {
		// this needs to be done after the nodes have been set up
		// 2//3//99System.out.print("ArcWalk");
		int cc;
		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(o, i);
			TreeNode[] path = model.getPathToRoot((TreeNode) o);
			TreeNode Parent = null;
			if (path.length >= 1) {
				Parent = path[path.length - 1];
				// 3//99System.out.print.print("Parent: " + Parent.toString());

			}
			// if (!child.toString().equals("routes")){
			// only go into the track branch
			if ((_level == 1 && child.toString().equals("track")) || _level > 1)
				// check all the possibilities
				if (_level == 1) {
					// just go to next level
				} else if (_level == 2 && child.toString().equals("edges")) {
					// //2//3//99System.out.print("level:" + _level +
					// " adding edge " + child.toString());
					// just go to next level
				} else if (_level == 3) {
					// //2//3//99System.out.print("level:" + _level +
					// " adding edge " + child.toString());
					setArcNames(child.toString(), _level);
				} else if (_level == 4) {
					// //2//3//99System.out.print("level:" + _level +
					// " adding edge " + child.toString());
					// setArcSegmentsAndParameters(child.toString(),_level);

					// storeSegments(child.toString());
					// stopPoints = "";
					// addtoArclinkedlist(child.toString());

					if (((DefaultMutableTreeNode) Parent).getLeafCount() == 1) {
						storeSegments(child.toString());
						stopPoints = "";
						addtoArclinkedlist(child.toString());
					} else if (!child.toString().startsWith("Stop")) {
						storeSegments(child.toString());
					} else if (child.toString().startsWith("Stop")) {
						storeStops(child.toString());
						addtoArclinkedlist(child.toString());
					}
				}

			if (!model.isLeaf(child)) {
				if ((_level == 1 && child.toString().equals("track"))
						|| (_level == 2 && child.toString().equals("edges"))
						|| (_level == 3 && o.toString().equals("edges"))) {
					_level++;
					_level = Arcwalk(model, child, _level);
					_level--;
				}
			}
		}
		return _level;
	}

	private void storeStops(String stopString) {

		String[] items = stopString.split(":");
		stopPoints = items[1];

	}

	private void storeSegments(String arcString) {
		_segments = arcString.split(",");

	}

	private void setArcSegmentsAndParameters(String arcString, int _level) {
		// save segments so we can add the required items if a stop is present
		if (!arcString.startsWith("Stops")) {
			String segments[] = arcString.split(",");
			_segments = segments;
		}

	}

	private void setArcNames(String myString, int _level) {
		// 1//2//3//99System.out.print("\naddnode:" + myString + " level = " +
		// _level +"\n" );

		if (myString.matches(".*_.*")) {
			String[] nodes = myString.split("[_|\\s]");

			_nodeFromName = nodes[0];
			_nodeFromDir = nodes[1];
			_nodeToName = nodes[2];
			_nodeToDir = nodes[3];
			// //2//3//99System.out.print("**************");
			// //2//3//99System.out.print("\n_nodeFrom = " + _nodeFromName +
			// "\n_nodeFromDir = " + _nodeFromDir + " " +
			// "\n_nodeTo = " + _nodeToName +
			// "\n_nodeFromDir = " + _nodeFromDir );
			// //2//3//99System.out.print("**************");
		} else {
			int i = 1;
			i++;
		}
	}

	private void addtoArclinkedlist(String arcString) {
		// 1//2//3//99System.out.print("addarc:" + string + " " );
		F1_TArcNames arcEquiv = new F1_TArcNames();
		// arcEquiv.set_TArcNames(_nodeFromName, _nodeFromDir, _nodeToName,
		// _nodeToDir, _segments);
		// stopPoints = "Stop:";
		// //2//3//99System.out.print(this.toString());

		arcEquiv.set_StopPoints(_nodeFromName, _nodeFromDir, _nodeToName,
				_nodeToDir, _segments, stopPoints);

		if (!_ArcLinkedList.contains(arcEquiv)) {
			// //2//3//99System.out.print("**********adding to arclinkedlist " +
			// arcEquiv);
			// _arcEquiv = arcEquiv;
			_ArcLinkedList.add(arcEquiv);

		}
		// if(!arcString.startsWith("Stop")){
		// // String segments[] = arcString.split(",");
		//
		// F1_TArcNames arcEquiv = new F1_TArcNames();
		// arcEquiv.set_TArcNames(_nodeFromName, _nodeFromDir, _nodeToName,
		// _nodeToDir, _segments);
		//
		// if (!_ArcLinkedList.contains(arcEquiv)){
		// //2//3//99System.out.print("**********adding to arclinkedlist " +
		// arcEquiv);
		// _arcEquiv = arcEquiv;
		// _ArcLinkedList.add(_arcEquiv);
		//
		// }
		// }
		// else if(arcString.startsWith("Stop")){
		// //String stop = "F";
		// String[] items = arcString.split(":");
		// stopPoints = items[1];
		// //remove what we had added with no stop points
		// F1_TArcNames arcEquiv = new F1_TArcNames();
		// arcEquiv.set_TArcNames(_nodeFromName, _nodeFromDir, _nodeToName,
		// _nodeToDir, _segments);
		// //if(arcEquiv.equals(_arcEquiv)){
		// if (_ArcLinkedList.contains(_arcEquiv)){
		// _ArcLinkedList.remove(_arcEquiv);
		// }
		// //}
		// //now add with stop points
		// arcEquiv = new F1_TArcNames();
		// arcEquiv.set_StopPoints(_nodeFromName, _nodeFromDir, _nodeToName,
		// _nodeToDir, _segments, stopPoints);
		// if (!_ArcLinkedList.contains(arcEquiv)){
		// //2//3//99System.out.print("**********adding to arclinkedlist " +
		// arcEquiv);
		// _ArcLinkedList.add(arcEquiv);
		// }
		// }
	}

	// private int AddNodesFromNodeList(DefaultTreeModel model, Object o, int
	// _level){
	// int cc;
	// cc = model.getChildCount(o);
	// for( int i=0; i < cc; i++) {
	// Object child = model.getChild(o, i );
	// //if (!child.toString().equals("routes")){
	// //only go into the track branch
	// if ((_level == 1 && child.toString().equals("track")) || _level > 1)
	// // check all the possibilities
	// if (_level == 1){
	// //just go to next level
	// }
	// else if (_level == 2 && child.toString().equals("nodes") ){
	//
	// // delete all the existing nodes
	//
	// //get a list of all children
	// Enumeration<SortableDefaultMutableTreeNode> children =
	// ((SortableDefaultMutableTreeNode) child).children();
	// List<SortableDefaultMutableTreeNode> childrenList = new
	// ArrayList<SortableDefaultMutableTreeNode>();
	//
	// while (children.hasMoreElements()) {
	// SortableDefaultMutableTreeNode object = children.nextElement();
	// childrenList.add(object);
	// }
	//
	// //deleteAllNodes
	// for (SortableDefaultMutableTreeNode mutableTreeNode : childrenList) {
	//
	// model.removeNodeFromParent(mutableTreeNode);
	// }
	//
	// // add all the nodes that are in the array previously set up
	// for (String nodename : nodeList){
	// SortableDefaultMutableTreeNode newChild = new
	// SortableDefaultMutableTreeNode(nodename);
	// ((SortableDefaultMutableTreeNode) child).add(newChild);
	//
	// }
	//
	// }
	//
	// if (!model.isLeaf(child)){
	// _level++;
	// _level = AddNodesFromNodeList(model,child, _level );
	// _level--;
	// }
	//
	// }
	// return _level;
	// }
//	private static H1_Engine_Routes _EngineRoutes = new H1_Engine_Routes();
//
//	private H2_Engine_Route eRoute;
//
//	private static H2_Engine_Route eRouteh;
//	
//	private H2_Engine_Route eRoute2;
//
//	private H2_Engine_Route eRoute3;
//
//	private H2_Engine_Route eRoute1;
	
	private static M6_Trains_On_Routes trainsOnRoute = new M6_Trains_On_Routes();
	
	private static M61_Train_On_Route route2;

	private M62_train routeh;

	private M62_train route3;

	private static M61_Train_On_Route route1;
	private static M61_Train_On_Route route21;
	
//	private M75Stops stops = new M75Stops();

	/**
	 * @return the _EngineRoutes
	 */
//	public H1_Engine_Routes get_H1_EngineRoutes() {
//		return _EngineRoutes;
//	}

	/**
	 * @param _EngineRoutes
	 *            the _EngineRoutes to set
	 */
//	public void set_EngineRoutes(H1_Engine_Routes _EngineRoutes) {
//		this._EngineRoutes = _EngineRoutes;
//	}
	
	public static void addStopPositions(D_MyGraph graph){
		
		M75Stops.addStopPosition(graph, "sthr1");
		M75Stops.addStopPosition(graph, "sthr2");
		M75Stops.addStopPosition(graph, "sthr3");
		
		M75Stops.addStopPosition(graph, "st1f1");
		M75Stops.addStopPosition(graph, "st1r1");
		
		M75Stops.addStopPosition(graph, "st2f1");
		M75Stops.addStopPosition(graph, "st2r1");
		
		M75Stops.addStopPosition(graph, "st3f1");
		M75Stops.addStopPosition(graph, "st3r1");
		//M75Stops.addStopPosition(graph, "sthf1");

//		addStopPosition("st3");
//		addStopPosition("sth");
//		
//		addStopPosition("st1p");
//		addStopPosition("st2p");
//		addStopPosition("st3p");
//		addStopPosition("sthp");
		

	

//	L1_TrackLocations stops = new L1_TrackLocations();
//	L3_TruckEngineObject_BG stop;
	}
	
	


	/*
	 * Change the route of the sth engine 
	 */
	
//	public void changeRouteEngineAndMove(String route, String stopPosition,
//			//String startArc, String startFraction, String startDirection,
//			int NoOfTruckToStopAt,
//			String endArc, String endFraction, String endDirection){
//		
//		for(H2_Engine_Route eroute :_EngineRoutes.get_engine_Routes()){
//			if (eroute.getTrainNo()==0){
//				/* we have the train with the engine */
//				eroute.setRoute(route);
//				eroute.moving=true;
//				//eroute.setStartPosition(startArc, startFraction, startDirection);
//				eroute.setStopPostion(NoOfTruckToStopAt, endArc, endFraction, endDirection);
//			}
//		}
//	}
	
//	truckLength = "0.5";
//	route = "1_To_Rev,3_To_Rev";
//	startArc = "1_F_2_B";
//	startFraction = "1.5";
//	startDirection = "Rev";
//	trainNo = "T0";
//	// determine the engine route
//	eRouteh = new H2_Engine_Route(trainNo, color, engineLength,
//			noTrucks, truckLength, route, startArc, startFraction,
//			startDirection, truckNames);
//	// add the engine_route to the list of engine routes
//	if (!_EngineRoutes.get_engine_Routes().contains(eRouteh)) {
//		_EngineRoutes.get_engine_Routes().add(eRouteh);
//	} else {
//		// raise exception
//	}
//	
	

	public void traverseTreeObtainingTrainParameters() {

		//99System.out.print("traverseTreeObtainingDesiredRoutesForTrains");
		int _level = 0;
		_level = 0;
		if (_jTree != null) {
			TreeModel model = _jTree.getModel();
			if (model != null) {
				Object root = model.getRoot();
				// 1//2//3//99System.out.print(root.toString());
				_level = 1;
				_level = determineEngineParameters(model, root, _level);
				// _level = determineRoutes(model,root,_level);

				// We do this in the calling routine
				// the required route is given by _route
				// in the general case we need to interpret it by replacing Ri
				// by the corresponding route
				// but for now we just use it
				// 1) generate the required path

				// 2) check for any reversals, and split the paths and add arcs

				// 3) generate the first path and add the train
				// addEngineParametersToLinkedLists;
				// _level = routeWalk(model,root,_level);
			} else
				System.out.print("Tree is empty.");
		} else
			System.out.print("JTree is null");
	}

	private int determineEngineParameters(TreeModel model, Object o, int _level) {

		int cc;
		cc = model.getChildCount(o);
		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(o, i);
			if ((_level == 1 && child.toString().equals("trains"))
					|| _level > 1) {
				if (_level == 2) {
					// determine the show trains flag
					String leaf = child.toString();
					if (leaf.startsWith("ShowTrains")) {
						showTrains = readLeaf(leaf, "_", 1);
					}
					if (leaf.startsWith("T")) {
						trainStr = leaf;
					}
				} else if (_level == 3) {
					// determine the engine parameters
					String leaf = child.toString();
					if (leaf.startsWith("EngineLength")) {
						strEngineLength = readLeaf(leaf, ":", 1);
					} else if (leaf.startsWith("NoTrucks")) {
						strNoTrucks = readLeaf(leaf, ":", 1);
					} else if (leaf.startsWith("TruckLength")) {
						strTruckLength = readLeaf(leaf, ":", 1);
					} else if (leaf.startsWith("Route")) {
						route = readLeaf(leaf, ":", 1);
					} else if (leaf.startsWith("Colour")) {
						strEngineColor = readLeaf(leaf, ":", 1);
					} else if (leaf.startsWith("Position")) {
						String arc_position = readLeaf(leaf, ":", 1); // 1_F_2_B
						startArc = readLeaf(arc_position, "-", 0);
						startFraction = readLeaf(arc_position, "-", 1); // 2.5
						startDirection = readLeaf(arc_position, "-", 2); 																			
					}
				}
				//99System.out.print("engineLength = " + strEngineLength
				//99		+ " noTrucks = " + strNoTrucks + " truckLength = "
				//99		+ strTruckLength + " route = " + route + " startArc = "
				//99		+ startArc + " startFraction " + startFraction
				//99		+ " startDirection " + startDirection);
				if (!model.isLeaf(child)) {
					_level++;
					_level = determineEngineParameters(model, child, _level);
					_level--;
				}
			}
		}
		// all children have been processed. Now do the last action at each
		// level
		if (_level == 3) {
			// determine the engine route
	Integer[] truckNames = null; //we do not set the numbering of the trucks.
			
//			eRoute = new H2_Engine_Route(trainStr, strEngineColor, strEngineLength,
//					strNoTrucks, strTruckLength, route, startArc, startFraction,
//					startDirection, truckNames);// add the engine_route to the list of engine routes
//			if (!_EngineRoutes.get_engine_Routes().contains(eRoute)) {
//				_EngineRoutes.get_engine_Routes().add(eRoute);
//			} else {
//				// raise exception
//			}
		}
		return _level;
	}

	/**
	 * @return the eRoute
	 */
//	public H2_Engine_Route geteRoute() {
//		return eRoute;
//	}

	private String readLeaf(String leaf, String delimeter, int i) {
		// read the ith word of string leaf split by delimeter "-"
		String[] items = leaf.split(delimeter);
		return items[i];
	}

	// protected int routeWalk(TreeModel model, Object o,int _level){
	// //1//2//3//99System.out.print ("Starting routeWalk");
	// int cc;
	// cc = model.getChildCount(o);
	// for( int i=0; i < cc; i++) {
	// Object child = model.getChild(o, i );
	// if ((_level == 1 && child.toString().equals("routes")) || _level > 1){
	// // if level = 1 we have the routes
	// // if level = 2 we have the arcs
	// ////3//99System.out.print.print("level:" + _level + ": ");
	// if (_level == 1){
	// //add node to linked list
	// ////1//2//3//99System.out.print("level:" + _level + " adding node " +
	// child.toString());
	// // we store all the node names in a list so we can assign all the nodes
	// later
	//
	// }
	// else if (_level == 2){
	// //we have a child with elements of the form T1_Cblue_L3.5
	// //1//2//3//99System.out.print("*********adding child " + child.toString());
	// addEnginetoLinkedlist(child.toString());
	// ////3//99System.out.print.print("level:" + _level + " " +
	// child.toString()+" -- ");
	// }
	// else if (_level == 3){
	//
	// // We have a child with arc names S;RC,LC,.. etc.
	// // level 3 we have the
	// ////1//2//3//99System.out.print("level:" + _level + " " +
	// child.toString());
	// addRoutetoLinkedlist(child.toString());
	// }
	// if (!model.isLeaf(child)){
	// _level++;
	// _level = routeWalk(model,child, _level );
	// _level--;
	// }
	// }
	// }
	//
	// return _level;
	// }
	// private void addRoutetoLinkedlist(String routeString) {
	// H2_Engine_Route _eRoute = null;
	// if (routeString.matches(".* .*")){
	// String[] route = routeString.split(" ");
	// List<String> routeList = Arrays.asList(route);
	// _eRoute.setEngineRoute(routeList);
	// }
	// // add the engineroute to the list of engine routes
	// if (!_EngineRoutes.get_engine_Routes().contains(_eRoute)){
	// _EngineRoutes.get_engine_Routes().add(_eRoute);
	// }
	// else {
	// //raise exception
	// }
	// }

	// private void addEnginetoLinkedlist(String engineString
	// ) {
	// String train = null;
	// String color = null;
	// String length = null;
	// // split node
	// if (engineString.matches(".*_.*")){
	// String[] engineFeatures = engineString.split("_");
	//
	// for(String item : engineFeatures){
	// if (item.startsWith("T")){
	// train = item.replaceFirst("T", "");
	// }
	// else if (item.startsWith("C")){
	// color = item.replaceFirst("C", "");
	// }
	// else if (item.startsWith("L")){
	// length = item.replaceFirst("L", "");
	// }
	// }
	// }
	// else{
	// train = engineString.replaceFirst("T", "");
	// }
	//
	//
	// //_eRoute = new H2_Engine_Route(train, color, length);
	//
	//
	// }

	public JTree get_jTree() {
		return _jTree;
	}

	public void set_jTree(JTree _jTree) {
		this._jTree = _jTree;
	}

	public List<E1_TNodeNames> get_NodeLinkedList() {
		return _NodeLinkedList;
	}

	public void set_NodeLinkedList(List<E1_TNodeNames> _NodeLinkedList) {
		this._NodeLinkedList = _NodeLinkedList;
	}

	public List<F1_TArcNames> get_ArcLinkedList() {
		return _ArcLinkedList;
	}


	public void set_ArcLinkedList(List<F1_TArcNames> _ArcLinkedList) {
		this._ArcLinkedList = _ArcLinkedList;
	}

	public String get_nodeFromName() {
		return _nodeFromName;
	}

	public String get_nodeToName() {
		return _nodeToName;
	}

	public String get_nodeFromDir() {
		return _nodeFromDir;
	}

	public String get_nodeToDir() {
		return _nodeToDir;
	}

	public LinkedList<String> getPointsList() {
		return pointsList;
	}

	public String[] get_pointarray() {
		return _pointarray;
	}

	public LinkedList<String> getPointsList2() {
		return nodesList;
	}

	public Vector3d get_initialNodeTangent() {
		return (Vector3d) _initialNodeTangent;
	}

	public Point3d get_initialNodePosition() {
		return (Point3d) _initialNodePosition;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("B2_LinkedLists [_nodeFromName=");
		builder.append(_nodeFromName);
		builder.append(", _nodeToName=");
		builder.append(_nodeToName);
		builder.append(", _nodeFromDir=");
		builder.append(_nodeFromDir);
		builder.append(", _nodeToDir=");
		builder.append(_nodeToDir);
		builder.append(", stopPoints=");
		builder.append(stopPoints);
		builder.append(", _segments=");
		builder.append(_segments);
		builder.append("]");
		return builder.toString();
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getNodeDirection() {
		return nodeDirection;
	}

	public String getCurve() {
		return curve;
	}

	public String getPointNumber() {
		return pointNumber;
	}

	public List<I1_PointNames> get_PointLinkedList() {
		return _PointLinkedList;
	}

	public void set_TrainOnTrack(int i) {
		// TODO Auto-generated method stub

	}

	public M6_Trains_On_Routes getTrainsOnRoute() {
		return trainsOnRoute;
	}

	public void setTrainOnRoute(M6_Trains_On_Routes trainsOnRoute) {
		this.trainsOnRoute = trainsOnRoute;
	}

//	public M75Stops getStops() {
//		return stops;
//	}
//
//	public void setStops(M75Stops stops) {
//		this.stops = stops;
//	}
	
	public static void getTrainParametersfromLong(D_MyGraph graph, long[] init) {

		//99System.out.print("getTrainParametersfromLong");

		// convert long to stacks
		Myfunctions.convertLongToStacks(init);
		getParameters(graph, Myfunctions.getSt1(), "st1");
		getParameters(graph, Myfunctions.getSt2(), "st2");
		getParameters(graph, Myfunctions.getSt3(), "st3");
		getParameters(graph, Myfunctions.getSth(), "sth");
//		//99System.out.print("engineLength = " + strEngineLength + " noTrucks = "
//				+ strNoTrucks + " truckLength = " + strTruckLength + " route = "
//				+ route + " startArc = " + startArc + " startFraction "
//				+ startFraction + " startDirection " + startDirection);
		addStopPositions(graph);
	}

	private static void getParameters(D_MyGraph graph, Queue<Integer[]> deque, String trainName) {
		int engineLength;
		int truckLength;
		
		if(trainName.equals("sth")){
			trainNo = 0;
		}else{
			trainNo = Integer.parseInt(trainName.substring(2));
		}
		
		strEngineColor = "red";
		if (trainName != "sth") {
			strEngineLength = "0";
			engineLength = 0;
		} else {
			strEngineLength = "1";
			engineLength = 1;
		}
		strNoTrucks = Integer.toString(deque.size());
		int noTrucks = deque.size();
		int numberTrucks=deque.size();
		
//		strNoTrucks = "4";
//		noTrucks = 4;
//		numberTrucks = 4;
		
		truckNames = null;
		
		truckNames = new Integer[8];
//		for (int i = 0; i < deque.size(); i++) {
//			Integer[] tn = (Integer[])( deque).get(i);
//			truckNames[i] = tn[1];
//			//99System.out.print(truckNames[i]);
//		}
		Integer i = 0;
		for (Iterator iterator = deque.iterator(); iterator.hasNext();) {
			Integer tn[] = (Integer[]) iterator.next();
			//99System.out.print(i+"   "+tn[0]);
			truckNames[i+1] = tn[0];
			i++;
		}
//		for (int j = 0; j < 4; j++) {
//			truckNames[j+1] = j;
//		}
//		Collection<Integer[]> deque2 = deque;
//		int[] ints = Ints.toArray(deque2 );
		
		strTruckLength = "1";
		String trainStr;
		int trainNumber;
		M61_Train_On_Route route31;
		switch (trainName) {
		case "st1":
			
//			truckLength = 1;
//			route = "6_From_For,1_From_For";
//			startArc = "6_B_4_F";
//			startFraction = "3.5";
//			startDirection = "Rev";
//			trainStr = "T3";
//			trainNo = 3;
			
//			engineLength = "1";
			//noTrucks = "7";
			//strTruckLength = "1";
			
			truckLength = 1;
			route = "3_From_For,1_From_For";
			startArc = "3_B_2_F";
//			route = "1_To_For,3_To_For";
//			startArc = "2_F_3_B";
			
			startFraction = "3.5";
			startDirection = "For";
			
//			route = "6_From_For,1_From_For";
//			startArc = "6_B_4_F";
//			startFraction = "3.5";
//			startDirection = "For";
//			trainStr = "T3";
//			trainNo = 3; 
			
//			route = "2_To_For,3_From_For";
//			startArc = "2_F_3_B";
//			startFraction = "1.5";
//			startDirection = "For";
			trainStr = "T1";
			
//			strTruckLength = "0.5";
//			route = "3_From_Rev,1_From_Rev";
//			startArc = "3_B_2_F";
//			startFraction = "1.5";
//			startDirection = "For";
			
			trainStr = "T1";
			
			trainNumber = 1;

			trainStr = "T1"; 
			trainNo = 1; 
			//String strEngineColor;
			int numberEngines = 1; 
			engineLength = 1; 
			//int numberTrucks=deque.size();
			truckLength = 1; 

//			eRoute1 = new H2_Engine_Route(trainNo, strEngineColor, strEngineLength,
//					strNoTrucks, strTruckLength, route, startArc, startFraction,
//					startDirection, truckNames);
			//trainStr = trainNo;
			//trainNumber = 1;
			String traincoupling = "tail";
			route1 = new M61_Train_On_Route( trainStr,  trainNo,  strEngineColor,
					 engineLength,  numberTrucks,  truckLength,
					 truckNames,  route,  startArc,
					 startFraction,  startDirection, traincoupling, graph);
			//L3_TrackLocation etrain = new L3_TrackLocation("Engine", trainName, startArc, startFraction, startDirection);
			// add the engine_route to the list of engine routes
//			if (!_EngineRoutes.get_engine_Routes().contains(eRoute1)) {
//				_EngineRoutes.get_engine_Routes().add(eRoute1);
//			} else {
//				// raise exception
//			}
			trainsOnRoute.addtrainOnRoute(route1);
			
			break;
		case "st2":
//			engineLength = "1";
			//noTrucks = "6";

			strTruckLength = "0.5";
			truckLength = 1;
			route = "5_From_For,1_From_For";
			startArc = "5_B_4_F";
			startFraction = "2.5";
			startDirection = "For";
			trainStr = "T2";
			trainNo = 2; 

			numberEngines = 1; 
			engineLength = 1; 
			truckLength = 1; 
			traincoupling = "head";
			route2 = new M61_Train_On_Route( trainStr,  trainNo,  strEngineColor,
					 engineLength,  numberTrucks,  truckLength,
					 truckNames,  route,  startArc,
					 startFraction,  startDirection, traincoupling, graph);
			// add the engine_route to the list of engine routes

			trainsOnRoute.addtrainOnRoute(route2);
			
			M43_TruckData_Display stop = null;
			//M6_Trains_On_Routes.moveTrainCheckForStop(trainStr, stop, numberTrucks);
			
			break;
		case "st3":
			moveoldmethod();
			
			truckLength = 1;
			
			//note the arc specification is 4F 6B
			route = "6_From_For,1_From_For";
			startArc = "6_B_4_F";
			startFraction = "3.5";
			startDirection = "For";
			trainStr = "T3";
			trainNo = 3; 
			numberEngines = 5; 
			engineLength = 1; 			

			trainStr = "T3";
			trainNo = 3; 
			truckLength = 1;
			traincoupling = "head";
			route31 = new M61_Train_On_Route( trainStr,  trainNo,  strEngineColor,
					 engineLength,  numberTrucks,  truckLength,
					 truckNames,  route,  startArc,
					 startFraction,  startDirection, traincoupling, graph);
			// add the engine_route to the list of engine routes

			trainsOnRoute.addtrainOnRoute(route31);
			
			stop = null;
			//M6_Trains_On_Routes.moveTrainCheckForStop(trainStr, stop, numberTrucks);
			
			break;
		case "sth":

			
//			strTruckLength = "1";
//			truckLength = 1;
//			route = "2_To_Rev,5_To_Rev";
//			startArc = "4_F_5_B";
//			startFraction = "1.5";
//			startDirection = "Rev";
//			trainStr = "T0";
			
			strTruckLength = "0.5";
			
			truckLength = 1;
			route = "1_To_Rev,3_To_Rev";
			startArc = "1_F_2_B";
			startFraction = "1.5";
			startDirection = "Rev";
			trainStr = "T0";
			trainNo = 4; 

			numberEngines = 1; 
			engineLength = 1; 
			truckLength = 1; 			
			

			traincoupling = "tail";
			route21 = new M61_Train_On_Route( trainStr,  trainNo,  strEngineColor,
					 engineLength,  numberTrucks,  truckLength,
					 truckNames,  route,  startArc,
					 startFraction,  startDirection, traincoupling, graph);
			
			trainsOnRoute.addtrainOnRoute(route21);
			
			//er = new M5_Train_Route(trainNo, color, engineLength, noTrucks, truckLength, route, startArc, startFraction, startDirection, truckNames);// assumes
		}

	}

	/**
	 * 
	 */
	private static void moveoldmethod() {
		String trainStr;
		strTruckLength = "0.5";
		route = "1_To_Rev,3_To_Rev";
		startArc = "1_F_2_B";
		startFraction = "1.5";
		startDirection = "Rev";
		trainStr = "Told";
		// determine the engine route
//		eRouteh = new H2_Engine_Route(trainStr, strEngineColor, strEngineLength,
//				strNoTrucks, strTruckLength, route, startArc, startFraction,
//				startDirection, truckNames);
//		// add the engine_route to the list of engine routes
//		if (!_EngineRoutes.get_engine_Routes().contains(eRouteh)) {
//			_EngineRoutes.get_engine_Routes().add(eRouteh);
//		} else {
//			// raise exception
//		}
	}

	public void setPointsList(LinkedList<String> pointsList) {
		this.pointsList = pointsList;
	}

	public void set_PointLinkedList(List<I1_PointNames> _PointLinkedList) {
		this._PointLinkedList = _PointLinkedList;
	}
	

}
