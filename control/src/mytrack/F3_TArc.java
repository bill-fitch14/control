package mytrack;

import java.util.LinkedList;
import java.util.ListIterator;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.apache.commons.lang.ArrayUtils;

import Utilities.StringHelper;

import com.ajexperience.utils.DeepCopyUtil;


public class F3_TArc {
	// linked list having the start and end points of the segments in the arc
	public LinkedList<G_TSegment> _TrackSegments = new LinkedList<G_TSegment> ();

	// this linked list is used for checking
	public LinkedList<String> _AllowedSegments = new LinkedList<String> ();

	private BranchGroup _BG = new BranchGroup();

	public F3_TArc() {
		// TODO Auto-generated constructor stub
	}

	public F3_TArc(F1_TArcNames tArcNames, E2_TNodesHashmaps tNodes){

		String nodeFromName = tArcNames.getNodeFromName();
		E1_TNodeNames tNodeNames_from = tNodes.get_TNodeNamesMap().get(nodeFromName);
		E3_TNode NodeFrom = tNodes.get_TNodesMap().get(tNodeNames_from);
		//		TNode NodeFrom = tNode;

		int NodeFromDirection = tArcNames.getNodeFromDirection();

		String nodeToName = tArcNames.getNodeToName();
		E1_TNodeNames tNodeNames_to = tNodes.get_TNodeNamesMap().get(nodeToName);
		E3_TNode NodeTo = tNodes.get_TNodesMap().get(tNodeNames_to);
		//		TNode NodeTo = tNode_to;

		int NodeToDirection = tArcNames.getNodeToDirection();
		String[] TrackType = tArcNames.getSegElements();
		String stopPoints = tArcNames.getStopPoints();
		//		
		//		//1//2//3//99System.out.print("TArc NodeFrom: before segmentLinkedList " + NodeFrom.toString());
		//		//1//2//3//99System.out.print("TArc NodeTo: before segmentLinkedList " + NodeTo.toString());
		//		
		try {
			set_TrackSegmentLinkedList(		
					NodeFrom, NodeFromDirection,
					NodeTo, NodeToDirection,
					TrackType, stopPoints);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//3//99System.out.print("TArc NodeFrom: after segmentLinkedList " + NodeFrom.toString());
		//3//99System.out.print("TArc NodeTo: after segmentLinkedList " + NodeTo.toString());
		//3//99System.out.print();
	}

	//
	//    
	//	public void set_TrackSegmentLinkedList() throws Exception
	//	{
	//		_tAS = new TArcSpec() 
	//		set_TrackSegmentLinkedList (
	//				_tAS.getNodeFrom(),_tAS.getNodeFromDirection(),
	//				_tAS.getNodeTo(),_tAS.getNodeToDirection(),_tAS.getTrackType() );
	//	}
	public void set_TrackSegmentLinkedList ( E3_TNode nodeFrom,int nodeFromDirection,
			E3_TNode nodeTo,int nodeToDirection,String[] trackType, String stopPoints  ) throws Exception {
		try
		{
			//3//99System.out.print("nodeFrom: " + nodeFrom.get_NodeName().getNodeName());
			//3//99System.out.print("nodeTo: " + nodeTo.get_NodeName().getNodeName());
			if(nodeFrom.get_NodeName().getNodeName().equals("2")){
				//3//99System.out.print("nodeFrom: " + nodeFrom.get_NodeName().getNodeName());
			}
			if (nodeFrom == null | nodeTo == null){
				//1//2//3//99System.out.print("A node has not been set up");
				return;
			}
			if (!nodeFrom.Drawn ( ))
			{
				//don't draw arc yet since we have no start node to draw from
				//3//99System.out.print("Cannot Draw Arc, NodeTo not defined");
			}
			else if (!nodeTo.Drawn (  ))
			{

				//Draw the segments, if the arc is not extendible
				if (!this.extendibleArc ( trackType ))
				{
					if (!this.StretchedArc ( trackType ))
					{
						//1//2//3//99System.out.print("TArc NodeFrom: before add segments " +nodeFrom.toString());
						//1//2//3//99System.out.print("TArc Nodeto: before add segments " +nodeTo.toString());
						AddTheSegments ( nodeFrom,nodeFromDirection,
								nodeTo,nodeToDirection,trackType,stopPoints );
						//1//2//3//99System.out.print("");
						//1//2//3//99System.out.print("TArc NodeFrom: after add segments " +nodeFrom.toString());
						//1//2//3//99System.out.print("TArc Nodeto: after add segments " +nodeTo.toString());
						//Draw the segments
						setNodetoPostionAndTangent ( nodeFrom, nodeFromDirection, 
								nodeTo, nodeToDirection, trackType);
						//1//2//3//99System.out.print("");
						//1//2//3//99System.out.print("TArc NodeFrom: after drawfixed " +nodeFrom.toString());
						//1//2//3//99System.out.print("TArc Nodeto: after drawfixed " +nodeTo.toString());
					}
					else if (this.StretchedArc ( trackType ))
					{

						AddTheSegments ( nodeFrom,nodeFromDirection,
								nodeTo,nodeToDirection,trackType ,stopPoints);
						//Draw the segments, then join up to the existing node
						DrawStretched ( nodeFrom, nodeFromDirection, 
								nodeTo, nodeToDirection, trackType );
					}
				}
			}
			else // both nodes are drawn, and our arc is not drawn
			{
				////Draw the segments, if the arc is not extendable


				if (!this.extendibleArc ( trackType ))
				{
					if (!this.StretchedArc ( trackType ))
					{

						AddTheSegments ( nodeFrom,nodeFromDirection,
								nodeTo,nodeToDirection,trackType,stopPoints );

						setNodetoPostionAndTangent ( nodeFrom,nodeFromDirection,
								nodeTo,nodeToDirection,trackType );
					}
					else if (this.StretchedArc ( trackType ))
					{
						//add the segments
						AddTheSegments ( nodeFrom,nodeFromDirection,
								nodeTo,nodeToDirection,trackType,stopPoints );
						//join up to the existing node
						DrawStretched ( nodeFrom,nodeFromDirection,
								nodeTo,nodeToDirection,trackType );
					}
				}
				else
				{
					//call solve to find the length of the extended segments
					drawExtended ( nodeFrom, nodeFromDirection, 
							nodeTo, nodeToDirection, trackType,stopPoints );
				}
			}
		}
		catch (Exception e)
		{

			throw new Exception ( );
		}


			}

	private void AddTheSegments ( E3_TNode nodeFrom,int nodeFromDirection,
			E3_TNode nodeTo,int nodeToDirection,String[] trackType, String stopPoints ) throws Exception
			{
		try
		{
			String[]stopPointArray = stopPoints.split("_");   // stopPoints should be a string 1_2_3 etc. is now just 1
			Set_Allowed_Segments ( );

			//            _NodeFrom = nodeFrom;
			//            _NodeFromDirection = nodeFromDirection;
			//            _NodeTo = nodeTo;

			for (int i = 0 ; i < trackType.length ; i++)
			{
				boolean lastSegment;
				if(i==trackType.length-1){
					lastSegment = true;
				}
				else{
					lastSegment = false;
				}

				String PointNo = String.valueOf(i);
				boolean ContainsStop = ArrayUtils.contains(stopPointArray, PointNo);
				//ContainsStop=false;
				AddSegmentToTrackSegments ( trackType[i], nodeFrom, nodeFromDirection, nodeTo, nodeToDirection, lastSegment ,i,ContainsStop);
				//1//2//3//99System.out.print("TArc NodeFrom: after add segment[ " + i + "] " +nodeFrom.toString());
				//1//2//3//99System.out.print("TArc NodeTo: after add segment[ " + i + "] " +nodeTo.toString());

			}


		}
		catch (Exception e)
		{
			throw new Exception ( );
		}

			}



	public void Set_Allowed_Segments ( )
	{
		//LinkedList<String> _AllowedSegments = new LinkedList<String>();
		_AllowedSegments.clear ( );
		_AllowedSegments.add ( "RC" );
		_AllowedSegments.add ( "RCH" );
		_AllowedSegments.add ( "LC" );
		_AllowedSegments.add ( "LCH" );
		_AllowedSegments.add ( "ST" );
		_AllowedSegments.add ( "STE" );
		_AllowedSegments.add ( "RCE" );     //extendible segments
		_AllowedSegments.add ( "LCE" );
		_AllowedSegments.add ( "STE" );
		_AllowedSegments.add ( "RCS" );     //stretched element at end
		_AllowedSegments.add ( "LCS" );
		_AllowedSegments.add ( "STS" );
		_AllowedSegments.add ( "ST2" );
	}
		

	public boolean AddSegmentToTrackSegments ( String TrackIdentifier, E3_TNode nodeFrom, int nodeFromDirection, 
			E3_TNode nodeTo, int nodeToDirection, boolean lastSegment, int segmentNo, boolean containsStop ) throws Exception
			{
		if (StringHelper.CountWords ( TrackIdentifier ) > 1)
		{
			String Word2 = StringHelper.GetLastWord ( TrackIdentifier );
			String Word1 = StringHelper.RemoveLastWord ( TrackIdentifier );

			if (Word2.equals("STF"))
			{
				//ok to continue
			}
			else
			{
				return false;
			}
		}
//		else
//		{
//			if (TrackIdentifier == null || !_AllowedSegments.contains ( TrackIdentifier ))
//				return false;
//		}

		if (_TrackSegments.size() == 0)
		{
			//First node. we need to get the previous tangents from the start node, _NodeFrom 
			Vector3d tangent = nodeFrom.getTangentCopy();
			tangent.scale(nodeFromDirection);
			Point3d position = nodeFrom.getPositionCopy();
			//G_TSegment NewSegment = new G_TSegment ( TrackIdentifier,tangent,position, nodeFrom, nodeFromDirection , "first" );
			G_TSegment NewSegment = new G_TSegment ( TrackIdentifier,tangent,position,nodeFrom,nodeFromDirection, 
					nodeTo, nodeToDirection,segmentNo, "first",containsStop );
			_TrackSegments.add ( NewSegment );
			NewSegment = null;
		}
		else
		{
			//Subsequent node. We need to get the previous tangents from the previous tracksegment
			int i = _TrackSegments.size() - 1;
			Vector3d tangent = _TrackSegments.get(i).getTangent2();
			Point3d position = _TrackSegments.get(i).getposition2();

			G_TSegment NewSegment;
			if(lastSegment){
				NewSegment = new G_TSegment ( TrackIdentifier,tangent,position,nodeFrom,nodeFromDirection, 
						nodeTo, nodeToDirection,segmentNo, "last",containsStop );
				//NewSegment = new G_TSegment ( TrackIdentifier,tangent,position, nodeTo, nodeToDirection , "last" );
			}
			else
			{
				NewSegment = new G_TSegment ( TrackIdentifier,tangent,position,nodeFrom,nodeFromDirection, 
						nodeTo, nodeToDirection,segmentNo, "middle",containsStop );
			}
			_TrackSegments.add ( NewSegment );
			NewSegment = null;
		}

		return true;
			}

	//	public boolean AddSegmentToTrackSegments1 ( String TrackIdentifier )
	//	{
	//		if (TrackIdentifier == null || !_AllowedSegments.contains ( TrackIdentifier ))
	//			return false;
	//
	//		//just add the trackidentifier, don't worry about the other values
	//		TrackSegment NewSegment = null;
	//		try {
	//			NewSegment = new TrackSegment ( TrackIdentifier );
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		_TrackSegments.add ( NewSegment );
	//
	//
	//		return true;
	//	}

	/// <summary>
	/// Indicates whether (the tangents and) Positions have been calculated
	/// </summary>
	public boolean extendibleArc ( String[] TrackType )
	{
		boolean returnval = false;

		//Check whether one of the segments is extendible, i.e the tracktype ends with the letter E
		for (String Trackelement : TrackType)
		{
			if (Trackelement.endsWith ( "E" ))
			{
				returnval = true;
			}
		}
		return returnval;
	}

	/// <summary>
	/// Indicates whether (the tangents and) Positions have been calculated
	/// </summary>
	public boolean StretchedArc ( String[] TrackType )
	{
		boolean returnval = false;
		//Check whether one of the segments is stretchable, i.e the tracktype ends with the letter S
		for (String Trackelement : TrackType)
		{
			if (Trackelement.endsWith ( "S" ))
			{
				returnval = true;
			}
		}
		return returnval;
	}


	private void setNodetoPostionAndTangent ( E3_TNode nodeFrom,int NodeFromDirection,
			E3_TNode nodeTo,int NodeToDirection,String[] TrackType ) throws Exception
			{
		try
		{
			//        	nodeTo.setPosition(NewSegment._position2);
			//          DeepCopyUtil deepCopyUtil = new DeepCopyUtil(); // create deep copies of objects.        
			//          
			//          // set the tangent
			//          Vector3d t = deepCopyUtil.deepCopy(NewSegment._tangent2);
			//          t.normalize();
			//          t.scale(nodeFromDirection);
			//      	nodeTo.setTangent(t);
			int j = _TrackSegments.size();
			nodeTo.setPosition(_TrackSegments.get(j-1).getposition2());
			DeepCopyUtil deepCopyUtil = new DeepCopyUtil();
			Vector3d t = deepCopyUtil.deepCopy(_TrackSegments.get(j - 1).getTangent2());
			t.scale(NodeFromDirection);
			nodeTo.setTangent(t);
			//			Vector3d t3 = new Vector3d();
			//			_TrackSegments.get(j - 1).getTangent2().scale(NodeFromDirection,t3);
			//            
			//			 nodeTo.setTangent(t3);
			//note that we will create the node in the calling program
		}
		catch (Exception e)
		{
			throw new Exception ( );
		}

			}

	private void DrawStretched ( E3_TNode nodeFrom,int NodeFromDirection,
			E3_TNode nodeTo,int NodeToDirection,String[] TrackType ) throws Exception
			{
		try
		{
			////Draw the segments, then join up to the existing node
			//Set_Allowed_Segments ( );

			//_NodeFrom = NodeFrom;
			//_NodeFromDirection = NodeFromDirection;
			//_NodeTo = NodeTo;

			//for (int i = 0 ; i < TrackType.Length ; i++)
			//{
			//    AddSegmentToTrackSegments ( TrackType[i] );
			//}

			// add a segment to join to _NodeTo
			//note that we may not always have to do this. 
			//We could just replace the last segment with this one if they are nearly the same
			int j = _TrackSegments.size();
			Vector3d tangent1 = _TrackSegments.get(j-1).getTangent2();
			Point3d position1 = _TrackSegments.get(j-1).getposition2();
			Vector3d tangent2 = nodeTo.getTangent();
			tangent2.scale(NodeToDirection); //specify the direction of NodeTo
			Point3d position2 = nodeTo.getPosition();  

			float length = (float)Math.abs ( position1.distance(position2) );
			G_TSegment NewSegment = new G_TSegment ( position1,tangent1,position2,tangent2,length );
			_TrackSegments.add ( NewSegment );
			//NewSegment = null;
		}
		catch (Exception e)
		{
			throw(e);
		}
			}

	private void drawExtended ( E3_TNode nodeFrom,int nodeFromDirection,
			E3_TNode nodeTo,int nodeToDirection,String[] TrackType, String stopPoints ) throws Exception
			{
		
		String[]stopPointArray = stopPoints.split("_");
		//split into segments not containing / elements.
		//get the tangents at the extendible elements

		//We need to solve AC=b0-b1-b2-b3 where
		//      [a1 a2 ... ai] [c1 ] = [d] - [f1] - [f2] - ... [fi]
		//      [b1 b2 ... bi] [c2 ]   [e]   [g0]   [g0] -     [g0]
		//                     [...]
		//                     [ci ]
		//where a1 b1 are the x and y elements of the tangents of the extendible elements
		// and ci are the final lengths of the extendible elements
		// d e are the x and y coordinates of the line between the two nodes
		// fi gi are the x and y coordinates of the lines between the segments not containing extendible elements

		// We first find
		//      [T1 T2 ... Ti] [c1 ] = [D] - [F1] - [F2] - ... [Fi]
		//                     [c2 ]   
		//                     [...]
		//                     [ci ]
		// Where T D and F are vector3 with Z element 0
		// T are the tangents

		// or better
		//      [T1 T2 ... Ti] [c1 ] = [D] - [F] = [G]
		//                     [c2 ]   
		//                     [...]
		//                     [ci ]
		// where T D and F are vector3 with Z element 0
		// T are the tangents at the extendible elements
		// D is the vector from the start to end node
		// F is the vector from the start to the end of the arc of the non extendable elements

		// We then solve TC = G for C given T and G

		// i.e. C = T Solve G

		//Draw the segments, then join up to the existing node
		Set_Allowed_Segments ( );

		//        _odeFrom = NodeFrom;
		//        _NodeFromDirection = NodeFromDirection;
		//        _NodeTo = NodeTo;

		//int ab = 0;
		//int fg = 0;

		Vector3d[] A0 = new Vector3d[20];
		Vector3d[] B = new Vector3d[20];
		Point3d D = new Point3d();
		Vector3d F = new Vector3d();
		Vector3d G1 = new Vector3d();

		//float d;

		//set the D 
		D.sub(nodeTo.getPosition(),nodeFrom.getPosition());
		//D.sub(nodeFrom.getPosition(),nodeTo.getPosition());

		//draw the arc with no extendible segments

		//set the index so that each non extendible arc has a consecutive number
		int j = -1;
		int k = -1;
		int[] NonextendibleArcIndex = new int[TrackType.length];
		int[] extendibleArcIndex = new int[TrackType.length];

		for (int i = 0 ; i < TrackType.length ; i++)
		{
			if (!TrackType[i].endsWith ( "E" )) //check if extendible
			{
				//if (i == 0)
				//{
				//    ArcIndex[i] = 1; // sets to the first non-extendible arc
				//}
				//else
				//{
				j++;
				//AddSegmentToTrackSegments ( TrackType[i], nodeTo, nodeToDirection, nodeFrom,false );
				String PointNo = String.valueOf(i);
				boolean ContainsStop = ArrayUtils.contains(stopPointArray, PointNo);
				AddSegmentToTrackSegments ( TrackType[i], nodeFrom, nodeFromDirection, nodeTo,nodeToDirection ,false , i, ContainsStop);
				NonextendibleArcIndex[j] = i;  //sets to the previous non-extendible arc
				//}
			}
			else
			{
				k++;
				extendibleArcIndex[k] = i; //increment and set
			}
		}
		int NoOfNonextendibleSegments = j+1;
		int NoOfextendibleSegments = k+1;

		//int[] extendibleIndex = new int[TrackType.length];
		Vector3d[] Tangent = new Vector3d[TrackType.length];
		Point3d[] Position = new Point3d[TrackType.length];

		for (int i = 0 ; i < NoOfextendibleSegments ; i++)
		{
			DeepCopyUtil deepCopyUtil = new DeepCopyUtil(); // create deep copies of objects.        
			//          
			//          // set the tangent
			//          Vector3d t = deepCopyUtil.deepCopy(NewSegment._tangent2);

			k = extendibleArcIndex[i];
			if (k == 0)
			{
				Vector3d t = deepCopyUtil.deepCopy(_TrackSegments.get(0).getTangent1()) ;
				t.normalize();
				Tangent[i] = t;
				Position[i] = _TrackSegments.get(0).getposition1() ;

			}
			else
			{
				Vector3d t = deepCopyUtil.deepCopy(_TrackSegments.get(k-i-1).getTangent2()) ;
				t.normalize();
				Tangent[i] = t;
				Position[i] = _TrackSegments.get(k-i-1).getposition2() ;
			}
		}
		// get the end position of the non extendible arc
		// 
		Point3d EndPosition = new Point3d();

		EndPosition = _TrackSegments.get(NonextendibleArcIndex[NoOfNonextendibleSegments - NoOfextendibleSegments]).getposition2();

		//set the D 
		Point3d p2 = new Point3d();
		p2 = nodeFrom.getPosition();

		D.sub(nodeTo.getPosition(),p2);

		// set the F

		F.sub(EndPosition ,nodeFrom.getPosition());

		G1.sub(nodeTo.getPosition(),EndPosition);

		// set T

		// T = [t1 T2 T3 .. Ti]

		Jama.Matrix T = new Jama.Matrix ( 2,NoOfextendibleSegments );
		for (int i = 0; i < NoOfextendibleSegments; i++)
		{            
			T.set(0, i, Tangent[i].x);
			T.set(1, i, Tangent[i].y);
		}

		// set G
		Jama.Matrix G = new Jama.Matrix(2,1);
		G.set(0,0,G1.x);
		G.set(1,0,G1.y);
		// find C
		Jama.Matrix C = new Jama.Matrix ( NoOfextendibleSegments,1);
		C = (Jama.Matrix) T.solve ( G );

		//Example
		//A[0,0] = 2;
		//A[0,1] = 3;
		//A[1,0] = 1;

		//A[1,1] = 4;
		////A[2,0] = 3;
		////A[2,1] = 4;

		//Y[0,0] = -1;
		//Y[1,0] = 0;

		//X = (Matrix) A.Solve ( Y );

		//Now we have the C put them in the arc in the right places
		float [] length = new float[NoOfextendibleSegments];
		for (int i = 0; i < NoOfextendibleSegments; i++)
		{
			length [i] = (float)C.get(i,0);
		}
		//Recreate the string with the extensible elements replaced by Fixed values
		String[] TrackTypeFixed = new String[TrackType.length];
		j=0;
		k = 0;
		for (int i = 0 ; i < TrackType.length ; i++)
		{
			if (!TrackType[i].endsWith ( "E" )) //check if extendible
			{
				TrackTypeFixed[j+k] = TrackType[i];
				j++;
			}
			else
			{
				String T1 = TrackType[i];
				TrackTypeFixed[j+k] = Float.toString(length[k]) + " " + T1.substring(0,T1.lastIndexOf('E')) + "F";
				k++;
			}
		}

		//DrawFixed ( NodeFrom,NodeFromDirection,
		//            NodeTo,NodeToDirection,TrackTypeFixed );
		DeleteAllSegments();
		AddTheSegments ( nodeFrom,nodeFromDirection,
				nodeTo,nodeToDirection,TrackTypeFixed , "");
		//DrawFixed ( NodeFrom,NodeFromDirection,
		//            NodeTo,1,TrackTypeFixed );
			}

	private boolean DeleteAllSegments ( )
	{
		_TrackSegments.clear();
		return true;
	}

	public LinkedList<G_TSegment> get_TrackSegments() {
		return _TrackSegments;
	}


	public float Arclength() {
		float length = 0;
		for(G_TSegment tracksegment : _TrackSegments){
			length += tracksegment.get_length();
		}
		return length;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TArc [_TrackSegments2=");
		builder.append("no of segments:"+_TrackSegments.size()+"||");
		for( G_TSegment seg:_TrackSegments){
			builder.append(seg.toString());
		}
//		ListIterator<G_TSegment> itr = _TrackSegments.listIterator();
//		////3//99System.out.print("Iterating through elements of Arc LinkedList using" + " ListIterator in forward direction...");
//
//		while(itr.hasNext())
//		{      //3//99System.out.print(itr.next().toString());
//		} 
//		builder.append(_TrackSegments);
		builder.append("]]}");
		return builder.toString();
	}

	public void set_BG() {
		//		if(_NodeName.getStation().equals("T")){
		//			TransformGroup node_position_TG = get_node_position_TG();
		//			this._BG.addChild(node_position_TG);
		//			this._BG.addChild(get_station_TG());
		//		}
		ListIterator<G_TSegment> itr = _TrackSegments.listIterator();
		//2//3//99System.out.print("Iterating through elements of Arc LinkedList using" + " ListIterator in forward direction...");

		int i=1;
		while(itr.hasNext())
		{      
			G_TSegment segment = itr.next();
			//2//3//99System.out.print(i + " " +segment.toString());
			segment.set_BG();
			this._BG.addChild(segment.get_BG());
			i++;
		} 

	}

	public BranchGroup get_BG() {
		//		ListIterator<TSegment> itr = _TrackSegments.listIterator();
		//		//1//2//3//99System.out.print("Iterating through elements of Arc LinkedList using" +
		//		" ListIterator in forward direction...");
		//		while(itr.hasNext())
		//		{      //1//2//3//99System.out.print(itr.next().toString());
		//			this._BG.addChild(itr.next().get_BG());
		//		} 
		return this._BG;
	}

	public G_TSegment getLastSegment(){
		int lastsegment = this.get_TrackSegments().size() - 1;
		return this.get_TrackSegments().get(lastsegment);
	}

	public G_TSegment getFirstSegment(){
		int firstsegment = 0;
		return this.get_TrackSegments().get(firstsegment);
	}


}
