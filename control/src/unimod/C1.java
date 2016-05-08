package unimod;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

import javax.swing.JEditorPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import mytrack.A_Setup;
import mytrack.SortableDefaultMutableTreeNode;
import mytrack.U2_Tree;
import mytrack.U3_Utils;
import net.net_Console;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.evelopers.unimod.runtime.ControlledObject;
import com.evelopers.unimod.runtime.StateMachineConfig;
import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;

public class C1 implements ControlledObject {
	private static final Log log = LogFactory.getLog(C1.class);

	private Queue<String[]> prq = new LinkedList<String[]>();
	
	/**
	 * @unimod.action.descr print latest point request
	 */
	public void z_processRequestQueue(StateMachineContext context) {

		String[] pointrequest = prq.poll();
		String message = null;
		String serialmessage = null;
		if (pointrequest != null) {
			// //2//3//99System.out.print("SWITCHING: " + pointrequest[0] + " " +
			// pointrequest[1]);
			// println("SWITCHING: " + pointrequest[0] + " " + pointrequest[1]);

			for (String item : pointrequest) {
				if (message == null) {
					message = item;
					serialmessage = item;
				} else {
					message = message + " " + item;
					if (!(item.equals("ON") || item.equals("OFF"))) {
						serialmessage = serialmessage + item;
					}
				}
			}
			println("SWITCHING: " + message);
			// 2//3//99System.out.print(serialmessage);
			E1.get_serialModel().writeSerial(serialmessage);
		}

		// //2//3//99System.out.print("Remaining Queue is:");
		// //2//3//99System.out.print("prq is " + prq.toString());
		// //2//3//99System.out.print("size of prq is " + prq.size());

	}

	// ******************************************************************
	private int currentTrainNo = 1;
	
	// routines for setting truck position
	/**
	 * @unimod.action.descr assign the current train no
	 */
	public void init_train_no(StateMachineContext context) {
		currentTrainNo = 1;
	}

	/**
	 * @unimod.action.descr positions train on current route
	 */
	
	public void position_train(StateMachineContext context) {

		// find from the context the train position
		String arcAndPosition = P2b.getStopRequest(context);
		String[] stopLocation = arcAndPosition.split("-");
		String arc = stopLocation[0];
		String arcloc = stopLocation[1];

		// 3//99System.out.print(arc + "   " + arcloc);

		// the train no is set by referring to currentTrainNo

		// get the route and position on route from the jTree
		JTree jTree = E1.get_jTree();
		// read the jtree and get the train parameters
		A_Setup.getArcAndNodeLinkedList().traverseTreeObtainingTrainParameters();
		// the train parameters are stored in E1._modelArcAndNodeLinkedList

		// the train will now be drawn using the branchgroup automatically if we
		// set the train visibility flag
		A_Setup.getArcAndNodeLinkedList().set_TrainOnTrack(1);

	}

	// ******************************************************************

	/**
	 * @unimod.action.descr positions train on current route
	 */
	public void initialise_train(StateMachineContext context) {

		// find from the context the train position
		String arcAndPosition = P2b.getStopRequest(context);
		String[] stopLocation = arcAndPosition.split("-");
		String arc = stopLocation[0];
		String arcloc = stopLocation[1];

		// 3//99System.out.print(arc + "   " + arcloc);

		// the train no is set by referring to currentTrainNo

		// get the route and position on route from the jTree
		JTree jTree = E1.get_jTree();
		// read the jtree and get the train parameters

		A_Setup.getArcAndNodeLinkedList().traverseTreeObtainingTrainParameters();
		// E1._adaptionArcAndNodeLinkedList.get

		// the train will now be drawn using the branchgroup
		// draw the train on the model
		// E1.

	}

	// public void setPointRequest(StateMachineContext context) {
	//
	// }
	/**
	 * @unimod.action.descr print start acknowledgement
	 */
	public void z_acknowledgeStart(StateMachineContext context) {
		// 2//3//99System.out.print("Hi there acknowledge start");
		// println("E2 initialised:");
	}

	/**
	 * @unimod.action.descr print start acknowledgement
	 */
	public void z_addToJtree(StateMachineContext context) {
		// //set stop location of the form 1_F_2_B-1_0.5
		// 2//3//99System.out.print("z_addToJtree started");
		String arcAndPosition = P2b.getStopRequest(context);
		// 2//3//99System.out.print("arcAndPosition = " + arcAndPosition);
		String[] stopLocation = arcAndPosition.split("-");
		String arc = stopLocation[0];
		String arcloc = stopLocation[1];

		// 2//3//99System.out.print("Trying to add: " + arcloc);

		// String[] items = arcloc.split("_");
		// String arcno = items[0];
		// String arcFrac = items[1];
		// insert the stop location in the arcs section
		JTree jTree = E1.get_jTree();
		TreePath path = U2_Tree.findByName(jTree, new String[] { "root",
				"track", "edges", arc });
		MutableTreeNode node = (MutableTreeNode) path.getLastPathComponent();
		// check the node for a child
		// int cc;
		// cc = odel.getChildCount(o)m;

		boolean childStrAltered = false;
		DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
		SortableDefaultMutableTreeNode newChild = null;
		for (int i = 0; i < model.getChildCount(node); i++) {
			MutableTreeNode child = (MutableTreeNode) model.getChild(node, i);
			String childStr = child.toString();
			if (childStr.startsWith("Stop", 0)) {
				String[] items2 = childStr.split(":");
				String[] stops = items2[1].split("_");
				TreeSet<String> tset = new TreeSet<String>();
				for (String stop : stops)
					tset.add(stop);
				// 2//3//99System.out.print("Trying to add: " + arcloc);
				tset.add(arcloc);

				String newChildStr = "Stop:" + U3_Utils.join(tset, "_");
				newChild = new SortableDefaultMutableTreeNode(newChildStr);
				model.removeNodeFromParent(child);
				model.insertNodeInto(newChild, node, i);
				// child = newChild;
				childStrAltered = true;
			}
		}
		if (childStrAltered == false) {
			newChild = new SortableDefaultMutableTreeNode("Stop:" + arcloc);
			int insertAt = model.getChildCount(node);
			((DefaultTreeModel) jTree.getModel()).insertNodeInto(newChild,
					node, insertAt);
		}
		((DefaultTreeModel) (jTree.getModel())).reload();
		A_Setup.getGui().displayNode(newChild);
		A_Setup.getGui().updateGraph();

		// 2//3//99System.out.print("z_addToJtree processed");

	}

	public void z_removeFromJtree(StateMachineContext context) {
		String arcAndPosition = P2b.getStopRequest(context);
		// 2//3//99System.out.print("arcAndPosition = " + arcAndPosition);
		String[] stopLocation = arcAndPosition.split("-");
		String arc = stopLocation[0];
		String arcloc = stopLocation[1];

		JTree jTree = E1.get_jTree();
		TreePath path = U2_Tree.findByName(jTree, new String[] { "root",
				"track", "edges", arc });
		MutableTreeNode node = (MutableTreeNode) path.getLastPathComponent();

		boolean childStrAltered = false;
		DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
		SortableDefaultMutableTreeNode newChild = null;
		for (int i = 0; i < model.getChildCount(node); i++) {
			MutableTreeNode child = (MutableTreeNode) model.getChild(node, i);
			String childStr = child.toString();
			if (childStr.startsWith("Stop", 0)) {
				String[] items2 = childStr.split(":");
				String[] stops = items2[1].split("_");
				TreeSet<String> tset = new TreeSet<String>();
				for (String stop : stops) {
					if (stop.equals(arcloc)) {
						tset.remove(stop);
					} else {
						tset.add(stop);
					}
				}
				// 2//3//99System.out.print("Trying to add: " + arcloc);
				// tset.add(arcloc);

				String newChildStr = "Stop:" + U3_Utils.join(tset, "_");
				newChild = new SortableDefaultMutableTreeNode(newChildStr);
				model.removeNodeFromParent(child);
				model.insertNodeInto(newChild, node, i);
				// child = newChild;
				childStrAltered = true;
			}
		}
		if (childStrAltered == false) {
			newChild = new SortableDefaultMutableTreeNode("Stop:" + arcloc);
			int insertAt = model.getChildCount(node);
			((DefaultTreeModel) jTree.getModel()).insertNodeInto(newChild,
					node, insertAt);
		}
		((DefaultTreeModel) (jTree.getModel())).reload();
		A_Setup.getGui().displayNode(newChild);
		A_Setup.getGui().updateGraph();
	}

	// ***********************************************************************

	/**
	 * @return currentRequest
	 * @unimod.action.descr latest point request
	 */
	public static String[] x_CurrentRequest(StateMachineContext context) {
		String[] currentRequest = P1.getPointRequest(context);
		// //2//3//99System.out.print(">> Latest Point Request:" +
		// currentRequest);

		return currentRequest;
	}

	/**
	 * @unimod.action.descr print latest point request
	 */
	public void z_printCurrentRequest(StateMachineContext context) {

		String[] currentRequest = P1.getPointRequest(context);
		//99System.out.print(">> Latest Point Request:" + currentRequest[0]
		//99 + "_" + currentRequest[1]);
		//currentRequest = P1.getPointRequest(context);
		// 2//3//99System.out.print(">> Latest Point Request:" + currentRequest[0]
		// + "_" + currentRequest[1]);
	}

	/**
	 * @unimod.action.descr add Request To Queue
	 */
	public void z_addRequestToQueue(StateMachineContext context) {

		String[] currentRequest = P1.getPointRequest(context);

		prq.add(currentRequest);

		// 2//3//99System.out.print("size of prq is " + prq.size());

		// 2//3//99System.out.print("added request: " );

	}

	/**
	 * @unimod.action.descr print latest point request
	 */
	public void z_printCurrentRequestQueue(StateMachineContext context) {

		// 2//3//99System.out.print("prq is " + prq.toString());
		// 2//3//99System.out.print("size of prq is " + prq.size());

	}



	public void println(String message) {
		// E1 e1 = new E1();

		JEditorPane display = E1.get_eventConsole().getjEditorPane1();
		display.setText(display.getText() + message + "\n");

		// display.setCaretPosition(display.getText().length());
		// main.setCaretPosition(main.getText().length());
	}

	// // see whether there are any requests in the queue
	// /**
	// * @return no of requests
	// * @unimod.action.descr no Of Requests In Queue
	// */nt
	// public int x_noOfRequestsInQueue(StateMachineContext coext){
	// int ans = 0;
	// String currentRequestQueue = P1.getPointRequestQueue(context);
	// if(currentRequestQueue.matches(".*_.*")){
	// String[] items = currentRequestQueue.split("_");
	// ans = items.length;
	// }
	// return ans;
	// }
	//
	// // see whether there are any requests in the queue
	// /**
	// * @return no of requests
	// * @unimod.action.descr no Of Requests In Queue
	// */
	// public void z_noOfRequestsInQueue(StateMachineContext context){
	//
	// int ans = 0;
	// String currentRequestQueue = P1.getPointRequestQueue(context);
	// //2//3//99System.out.print("currentRequestQueue= " + currentRequestQueue);
	// if(currentRequestQueue.matches(".*_.*")){
	// String[] items = currentRequestQueue.split("_");
	// ans = items.length;
	// }
	// //2//3//99System.out.print("no requests in queue = " + ans);
	// }
	//
	// /**
	// * @unimod.action.descr switch Point
	// */
	// public void z_switchPoint(StateMachineContext context){
	//
	// int ans = 0;
	// String currentRequestQueue = P1.getPointRequestQueue(context);
	// if(currentRequestQueue.matches(".*_.*")){
	// String[] items = currentRequestQueue.split("_");
	// ans = items.length;
	// }
	// if(ans > 0){
	// String currentRequest = P1.pollLatestPointRequestQueue(context);
	//
	// //2//3//99System.out.print("Switcing point: " + currentRequest.toString());
	// }
	// else
	// {
	// //2//3//99System.out.print("Stack is empty, not switching point");
	// }
	//
	// }

}
