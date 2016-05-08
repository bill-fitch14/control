package myscene;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.Shape3D;

import mytrack.C1_BranchGroup;
import mytrack.F1_TArcNames;
import mytrack.F2_TArcsHashmaps;
import mytrack.F3_TArc;
import mytrack.I1_PointNames;
import sm2.C1;
import sm2.E1;
import sm2.Main;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;

public class C_PickRoutines implements MouseListener {
	
	private static PickCanvas pickCanvas;
	private static C1_BranchGroup _BG;

	public  C_PickRoutines(Canvas3D canvas3D, BranchGroup _sceneBG, C1_BranchGroup _BG){
		
		//enablePicking(_sceneBG);
		pickCanvas = new PickCanvas(canvas3D, _sceneBG); 
	    pickCanvas.setMode(PickCanvas.BOUNDS); 
	    set_BG(_BG);
	    canvas3D.addMouseListener(this);
	}
	

//	public static void enablePicking(Node node) { 
//		
//		node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
//		node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
//		node.setCapability(BranchGroup.ALLOW_DETACH);
//		node.setPickable(true); 
//		node.setCapability(Node.ENABLE_PICK_REPORTING); 
//		try { 
//			Group group = (Group) node; 
//			for (Enumeration e = group.getAllChildren(); e.hasMoreElements();) { 
//				enablePicking((Node)e.nextElement()); 
//			} 
//		} 
//
//		catch(ClassCastException e) { 
//			// if not a group node, there are no children so ignore exception 
//		} 
//		try { 
//			Shape3D shape = (Shape3D) node; 
//			PickTool.setCapabilities(node, PickTool.INTERSECT_FULL); 
//			for (Enumeration e = shape.getAllGeometries(); e.hasMoreElements();) { 
//				Geometry g = (Geometry)e.nextElement(); 
//				g.setCapability(Geometry.ALLOW_INTERSECT); 
//			} 
//		} 
//		catch(ClassCastException e) { 
//			// not a Shape3D node ignore exception 
//		} 
//	} 


	public void mouseClicked(MouseEvent e) 

	{ 

		pickCanvas.setShapeLocation(e); 

		PickResult result = pickCanvas.pickClosest(); 

		if (result == null) { 

			//99System.out.print("Nothing picked"); 

		} else { 
			////99System.out.print( "Closest PickResult: " + result );
			Node actualNode = result.getObject();

			if( actualNode.getUserData() != null )
			{
				Object a = null;
				//99System.out.print("Closest Object: " + actualNode.getUserData() );
			}


			Primitive p = (Primitive)result.getNode(PickResult.PRIMITIVE); 
			BranchGroup b = (BranchGroup)result.getNode(PickResult.BRANCH_GROUP); 

			Shape3D s = (Shape3D)result.getNode(PickResult.SHAPE3D); 

			//1//2//3//99System.out.print("SceneGraphPTH: " + result.getSceneGraphPath().toString() + newline);
			SceneGraphPath path = result.getSceneGraphPath();

			//1//2//3//99System.out.print("Number of nodes in path: " + path.nodeCount());
			int lastnode = path.nodeCount()-1;
			//99System.out.print("Nodename0: " + path.getNode(0).getName());
			//99System.out.print("Nodename1: " + path.getNode(lastnode).getName() + " no nodes "+lastnode);
			//2//3//99System.out.print("Nodename: " + path.getNode(lastnode).getName());
			//2//3//99System.out.print("Object: " + path.getObject());

			//
			chooseActionOnPick(path);

		} 
	} 
	@Override
    public void mouseEntered(MouseEvent e) {
	       saySomething("Mouse entered", e);
	    }

	@Override
    public void mouseExited(MouseEvent e) {
	       saySomething("Mouse exited", e);
	    }

	@Override
	  public void mousePressed(MouseEvent e) {
	       saySomething("Mouse pressed (# of clicks: "
	                    + e.getClickCount() + ")", e);
	    }

	@Override
    public void mouseReleased(MouseEvent e) {
	       saySomething("Mouse released (# of clicks: "
	                    + e.getClickCount() + ")", e);
	    }
	
    void saySomething(String eventDescription, MouseEvent e) {
//        textArea.append(eventDescription + " detected on "
//                        + e.getComponent().getClass().getName()
//                        + "." + newline);
//        textArea.setCaretPosition(textArea.getDocument().getLength());
//    	//1//2//3//99System.out.print(eventDescription + " detected on "
//                + e.getComponent().getClass().getName()
//                + "./n" + newline);
    }
	
	private void chooseActionOnPick(SceneGraphPath path) {
		
		int lastnode = path.nodeCount()-1;
		String lastnodename = path.getNode(lastnode).getName();
		if (lastnodename != null){
			String[] words = lastnodename.split(":");
			if (words[0].equals("track")){
				// string is of the form track 2_B_LC-1 
				sendElementEvent(words[1]);
				//we have put an extra word at the start and end of an arc
				if(words.length>2){
					checkForPointAndIfPointSendPointEvent(words[2]);
				}
				//checkForTrackElement
			}
			if(words[0].startsWith("stop")){
				sendStopEvent(words);
			}
			
		}
	}

	private void sendStopEvent(String[] stopTypeAndTrackElement) {
		
		E1.e_stop_element_Picked(stopTypeAndTrackElement);
	}


	private void sendElementEvent(String word2) {
		
		//word2 is of the form 1_F_2_B_8
		
		E1.e_track_element_Picked(word2);
	}


	//private void checkForPoint(String node2, String nodeDirection2, String curve2) {
	private void checkForPointAndIfPointSendPointEvent(String word3) {
		String[] words = word3.split("_");
		String elementNode = words[0];
		String elementNodeDirection = words[1];
		String elementCurve = words[2];
		
		List<I1_PointNames> pointsLinkedList = _BG.get_linkedLists().get_PointLinkedList();
		for(I1_PointNames pointname : pointsLinkedList){
			String nodeName = pointname.getNodeName();
			String nodeDirection = pointname.getNodeDirection();
			String curve = pointname.getCurve();
			
			if (nodeName.equals(elementNode) && nodeDirection.equals(elementNodeDirection)){
				String desiredState = elementCurve;
				switchPoint(pointname,desiredState);
			}
			
		}
		
		while (!C1.getPrq().peekFirst().equals(null)){
			E1.fire_read_points_stack_event();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	static public void switchPoint(I1_PointNames pointname, String desiredState) {
		
		//2//3//99System.out.print("pointname:" + pointname.getNodeName() + " desiredState: " + desiredState);
		String pointNodeDirection = pointname.getNodeDirection();
		String curve = pointname.getCurve();
		String pointNodeName = pointname.getNodeName();
		
		String offState;
		if (desiredState.equals("ST")||desiredState.equals("ST1")){
			offState = curve;
		} else {
			offState = "ST";
		}
		//2//3//99System.out.print("desiredState" + desiredState);
		// set the branchGroup variables so the desiredState branch goes green and the offState goes red
		
		//get the ArcName for the 
		
		List<F1_TArcNames> arcLinkedList = _BG.get_linkedLists().get_ArcLinkedList();
		
		for ( F1_TArcNames tArcName : arcLinkedList){
			
			
			F2_TArcsHashmaps arcsHashmap = _BG.get_HM().get_TArcsHashmap();
			F3_TArc tArc = arcsHashmap.get_TArc(tArcName);
			

			//turn on SERIAL 90 xxxx yyyy 
			//xxxx is the node
			//YYYY is the port
			
			//points are on node 0001
			String prefix = "POINTN";
			String node = pointname.getNodeNumber();
			String pointno = pointname.getPointNumber();
			//pointno = pointname.getPointNumber();
			
			String On = "91";
			String Off = "90";
			String messageOn = prefix + On + node + pointno;
			String messageOff = prefix + Off + node + pointno;

			if ( tArcName.getNodeFromName().equals(pointNodeName) &&
					tArcName.getNodeFromDir().equals(pointNodeDirection)){
				
				String tracktype = tArc.getFirstSegment().get_TrackType();
				if (tracktype.startsWith(offState)){
					tArc.getFirstSegment().setpointcolor(Color.red);
				} else {
					tArc.getFirstSegment().setpointcolor(Color.green);
					if (desiredState.equals("ST")||desiredState.equals("ST1")){
						E1.e_process_point_request(new String[]{prefix,On,node,pointno,"ON"});
						setpoint(pointNodeName,"ON");
						//get_serialIO().writeSerial(messageOn);
					} else {
						//get_serialIO().writeSerial(messageOff);
						E1.e_process_point_request(new String[]{prefix,Off,node,pointno,"OFF"});
						setpoint(pointNodeName,"OFF");
					}
				}
				
			}
			
			
			if ( tArcName.getNodeToName().equals(pointNodeName) &&
					tArcName.getNodeToDir().equals(pointNodeDirection)){
				
				String tracktype = tArc.getLastSegment().get_TrackType();
				if (tracktype.equals(offState)){
					tArc.getLastSegment().setpointcolor(Color.red);
				} else {
					tArc.getLastSegment().setpointcolor(Color.green);
				}
				if (desiredState.equals("ST")||desiredState.equals("ST1")){
					//get_serialIO().writeSerial(messageOn);
					E1.e_process_point_request(new String[]{prefix,On,node,pointno,"ON"});
					setpoint(pointNodeName,"ON");
					
				} else {
					//get_serialIO().writeSerial(messageOff);
					E1.e_process_point_request(new String[]{prefix,Off,node,pointno,"OFF"});
					setpoint(pointNodeName,"OFF");
				}
			}
		}
		
	}
	
	public static void setpoint(String pointno_str, String state) {
		System.out.println("cxbccxbbbcb" + pointno_str + " " + state);
		int pointno=Integer.parseInt(pointno_str.trim());
		if (state.equals("OFF")){
			if(pointno == 2){
				Main.lo.setPoint1(0);
			}else{;
				Main.lo.setPoint2(0);
			}
		}else{
			if(pointno == 2){
				Main.lo.setPoint1(1);
			}else{;
				Main.lo.setPoint2(1);
			}
		}
		if (state.equals("OFF")){
			if(pointno == 2){
				Main.lo.setPoint1(0);
			}else{;
				Main.lo.setPoint2(0);
			}
		}else{
			if(pointno == 2){
				Main.lo.setPoint1(1);
			}else{;
				Main.lo.setPoint2(1);
			}
		}
		
	}


	public void set_BG(C1_BranchGroup _BG) {
		this._BG = _BG;
	}


}
