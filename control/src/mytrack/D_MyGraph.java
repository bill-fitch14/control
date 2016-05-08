package mytrack;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;
//import javax.vecmath.Vector3d;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
//import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.demo.JGraphAdapterDemo;
//import org.jgrapht.demo.JGraphAdapterDemo.ListenableDirectedMultigraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
//import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class D_MyGraph extends JApplet {

	// ArrayList<Vertex> nodes = new ArrayList<Vertex>();
	// ArrayList<Edge> edges = new ArrayList<Edge>();
	// private Map <String,Vertex> nodesMap = new HashMap<String,Vertex>();

	// ListenableGraph<String, DefaultEdge> g =
	// new ListenableDirectedMultigraph<String, DefaultEdge>(
	// DefaultEdge.class);
	//
	// // create a visualization using JGraph, via an adapter
	// jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(g);

	private Map<String, F3_TArc> identToArcMap = new HashMap<String, F3_TArc>();

	// SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> _g =
	// new SimpleDirectedWeightedGraph<String,
	// DefaultWeightedEdge>(DefaultWeightedEdge.class);

	ListenableDirectedWeightedGraph<String, DefaultWeightedEdge> _g = new ListenableDirectedWeightedGraph<String, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);

	JGraphModelAdapter m_jgAdapter = new JGraphModelAdapter<String, DefaultWeightedEdge>(
			_g);
	// JGraphModelAdapter jgAdapterDisp = new JGraphModelAdapter<String,
	// DefaultEdge>( _gdisp );

	JGraph jgraph = new JGraph(m_jgAdapter);
	// JGraph jgraphDisp = new JGraph(jgAdapterDisp );

	// getContentPane().add(jgraph);
	// resize(DEFAULT_SIZE);

	private DijkstraShortestPath<String, DefaultWeightedEdge> _shortestPath;

	private Map<String, String[]> identToArcStringArrayMap = new HashMap<String, String[]>();

	private Map<String, F3_TArc> arcStringArrayKeyToArcMap = new HashMap<String, F3_TArc>();

	private Map<String, String> arcStringArrayKeyToTrainOrientationMap = new HashMap<String, String>();

	private Map<String, String> arcStringArrayKeyToTrainMovementMap = new HashMap<String, String>();

	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	// private void adjustDisplaySettings( JGraph jg ) {
	// jg.setPreferredSize( DEFAULT_SIZE );
	//
	// Color c = DEFAULT_BG_COLOR;
	// String colorStr = null;
	//
	// try {
	// colorStr = getParameter( "bgcolor" );
	// }
	// catch( Exception e ) {}
	//
	// if( colorStr != null ) {
	// c = Color.decode( colorStr );
	// }
	//
	// jg.setBackground( c );
	// }

	// private void positionVertexAt( Object vertex, int x, int y ) {
	// DefaultGraphCell cell = m_jgAdapter.getVertexCell( vertex );
	// Map attr = cell.getAttributes( );
	// Rectangle b = GraphConstants.getBounds( attr );
	//
	// GraphConstants.setBounds( attr, new Rectangle( x, y, b.width, b.height )
	// );
	//
	// Map cellAttr = new HashMap( );
	// cellAttr.put( cell, attr );
	// m_jgAdapter.edit( cellAttr );
	// }

	public void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
		AttributeMap attr = cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);

		Rectangle2D newBounds = new Rectangle2D.Double(x, y, bounds.getWidth(),
				bounds.getHeight());

		GraphConstants.setBounds(attr, newBounds);

		// TODO: Clean up generics once JGraph goes generic
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}

	public D_MyGraph() {
	};

	public void ads() {
		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);

		Color c = DEFAULT_BG_COLOR;
		// String colorStr = null;

		// try {
		// colorStr = getParameter("bgcolor");
		// } catch (Exception e) {
		// }

		// if (colorStr != null) {
		// c = Color.decode(colorStr);
		// }

		jg.setBackground(c);
	}

	/**
	 * @param tArcNames
	 * @param tarc
	 */
	public void AddArc(F1_TArcNames tArcNames, F3_TArc tarc) {

		String NodeFrom = tArcNames.getNodeFromName();
		String NodeFromDir = tArcNames.getNodeFromDir();

		String NodeTo = tArcNames.getNodeToName();
		String NodeToDir = tArcNames.getNodeToDir();

		float length = tarc.Arclength();

		String vertex_NodeFrom_name1 = null;
		String vertex_NodeFrom_name2 = null;
		String vertex_NodeFrom_name3 = null;
		String vertex_NodeFrom_name4 = null;
		String vertex_NodeTo_name1 = null;
		String vertex_NodeTo_name2 = null;
		String vertex_NodeTo_name3 = null;
		String vertex_NodeTo_name4 = null;

		String ident1 = null;
		String ident2 = null;
		String ident3 = null;
		String ident4 = null;

		String train_orientation1 = null;
		String train_orientation2 = null;
		String train_orientation3 = null;
		String train_orientation4 = null;

		String train_movement1 = null;
		String train_movement2 = null;
		String train_movement3 = null;
		String train_movement4 = null;

		String d = "_";

		// the identifier ident gives a representation of the direction of the
		// train
		// the direction of travel is given by the order of the nodes
		// forwards is pointing from first node to next
		// reverse is pointing from second node to first

		/*
		 * The train orientation is the direction the train is facing, compared
		 * to the way the segments of the arc are specified
		 * 
		 * The train movement is the direction the train is moving, compared to
		 * the way the segments of the arc are specified
		 */
		if (NodeFromDir.equals("F") & NodeToDir.equals("B")) {
			vertex_NodeFrom_name1 = NodeFrom + "_To_For";
			vertex_NodeTo_name1 = NodeTo + "_To_For";
			ident1 = NodeFrom + d + "F" + d + NodeTo + d + "B" + d + "For";
			train_orientation1 = "same";
			train_movement1 = "same";

			vertex_NodeFrom_name2 = NodeTo + "_From_For";
			vertex_NodeTo_name2 = NodeFrom + "_From_For";
			ident2 = NodeTo + d + "B" + d + NodeFrom + d + "F" + d + "For";
			train_orientation2 = "same";
			train_movement2 = "opposite";

			vertex_NodeFrom_name3 = NodeFrom + "_To_Rev";
			vertex_NodeTo_name3 = NodeTo + "_To_Rev";
			ident3 = NodeFrom + d + "F" + d + NodeTo + d + "B" + d + "Rev";
			train_orientation3 = "opposite";
			train_movement3 = "same";

			vertex_NodeFrom_name4 = NodeTo + "_From_Rev";
			vertex_NodeTo_name4 = NodeFrom + "_From_Rev";
			ident4 = NodeTo + d + "B" + d + NodeFrom + d + "F" + d + "Rev";
			train_orientation4 = "opposite";
			train_movement4 = "opposite";
		} else if (NodeFromDir.equals("B") & NodeToDir.equals("F")) {
			vertex_NodeFrom_name1 = NodeTo + "_To_For";
			vertex_NodeTo_name1 = NodeFrom + "_To_For";
			ident1 = NodeTo + d + "B" + d + NodeFrom + d + "F" + d + "Rev";
			train_orientation1 = "opposite";
			train_movement1 = "opposite";

			vertex_NodeFrom_name2 = NodeFrom + "_From_For";
			vertex_NodeTo_name2 = NodeTo + "_From_For";
			ident2 = NodeFrom + d + "F" + d + NodeTo + d + "B" + d + "For";
			train_orientation2 = "opposite";
			train_movement2 = "same";

			vertex_NodeFrom_name3 = NodeTo + "_To_Rev";
			vertex_NodeTo_name3 = NodeFrom + "_To_Rev";
			ident3 = NodeTo + d + "B" + d + NodeFrom + d + "F" + d + "For";
			train_orientation3 = "same";
			train_movement3 = "opposite";

			vertex_NodeFrom_name4 = NodeFrom + "_From_Rev";
			vertex_NodeTo_name4 = NodeTo + "_From_Rev";
			ident4 = NodeFrom + d + "F" + d + NodeTo + d + "B" + d + "Rev";
			train_orientation4 = "same";
			train_movement4 = "same";
			
		} else if (NodeFromDir.equals("B") & NodeToDir.equals("B")) {
			vertex_NodeFrom_name1 = NodeTo + "_From_Rev";
			vertex_NodeTo_name1 = NodeFrom + "_To_For";
			ident1 = NodeTo + d + "B" + d + NodeFrom + d + "B" + d + "For";
			train_orientation1 = "opposite ";
			train_movement1 = "opposite ";

			vertex_NodeFrom_name2 = NodeFrom + "_From_For";
			vertex_NodeTo_name2 = NodeTo + "_To_Rev";
			ident2 = NodeFrom + d + "B" + d + NodeTo + d + "B" + d + "Rev";
			train_orientation3 = "opposite";
			train_movement3 = "same";

			vertex_NodeFrom_name3 = NodeTo + "_From_For";
			vertex_NodeTo_name3 = NodeFrom + "_To_Rev";
			ident3 = NodeTo + d + "B" + d + NodeFrom + d + "B" + d + "Rev";
			train_orientation2 = "same";
			train_movement2 = "opposite";

			vertex_NodeFrom_name4 = NodeFrom + "_From_Rev";
			vertex_NodeTo_name4 = NodeTo + "_To_For";
			ident4 = NodeFrom + d + "B" + d + NodeTo + d + "B" + d + "For";
			train_orientation4 = "same";
			train_movement4 = "same";
		}

		else if (NodeFromDir.equals("F") & NodeToDir.equals("F")) {
			vertex_NodeFrom_name1 = NodeTo + "_To_For";
			vertex_NodeTo_name1 = NodeFrom + "_From_Rev";
			ident1 = NodeTo + d + "F" + d + NodeFrom + d + "F" + d + "For";
			train_orientation1 = "same";
			train_movement1 = "same";

			vertex_NodeFrom_name2 = NodeFrom + "_To_Rev";
			vertex_NodeTo_name2 = NodeTo + "_From_For";
			ident2 = NodeFrom + d + "F" + d + NodeTo + d + "F" + d + "Rev";
			train_orientation2 = "opposite";
			train_movement2 = "same";

			vertex_NodeFrom_name3 = NodeTo + "_To_Rev";
			vertex_NodeTo_name3 = NodeFrom + "_From_For";
			ident3 = NodeTo + d + "F" + d + NodeFrom + d + "F" + d + "Rev";
			train_orientation3 = "same";
			train_movement3 = "opposite";

			vertex_NodeFrom_name4 = NodeFrom + "_To_For";
			vertex_NodeTo_name4 = NodeTo + "_From_Rev";
			ident4 = NodeFrom + d + "F" + d + NodeTo + d + "F" + d + "For";
			train_orientation4 = "opposite";
			train_movement4 = "opposite";
		} else {
			// 3//99System.out.print("no arc added");
		}

		// //3//99System.out.print("ident1" + ident1);
		// //3//99System.out.print("ident2" + ident2);
		// //3//99System.out.print("ident3" + ident3);
		// //3//99System.out.print("ident4" + ident4);

		// Set the edges and set up the maps
		// now can go from position on track to graph arc
		// since position on track is given by 1F-2B For
		// which gives the direction of travel 1-2, and the orientation For

		String ArcName1 = vertex_NodeFrom_name1 + "_to_" + vertex_NodeTo_name1;
		//99System.out.print(ArcName1 + ":" + length);
		DefaultWeightedEdge e = _g.addEdge(vertex_NodeFrom_name1,
				vertex_NodeTo_name1);
		try {
			// _gdisp.addEdge(vertex_NodeFrom_name1, vertex_NodeTo_name1);
		} catch (Exception e2) {
			// TODO: handle exception
		}

		_g.setEdgeWeight(e, length);
		// Map ident1 to edge name

		String ArcName2 = vertex_NodeFrom_name2 + "_to_" + vertex_NodeTo_name2;
		//99System.out.print(ArcName2 + ":" + length);
		e = _g.addEdge(vertex_NodeFrom_name2, vertex_NodeTo_name2);
		// _gdisp.addEdge(vertex_NodeFrom_name2, vertex_NodeTo_name2);
		_g.setEdgeWeight(e, length);

		String ArcName3 = vertex_NodeFrom_name3 + "_to_" + vertex_NodeTo_name3;
		//99System.out.print(ArcName3 + ":" + length);
		e = _g.addEdge(vertex_NodeFrom_name3, vertex_NodeTo_name3);
		// _gdisp.addEdge(vertex_NodeFrom_name3, vertex_NodeTo_name3);
		_g.setEdgeWeight(e, length);

		String ArcName4 = vertex_NodeFrom_name4 + "_to_" + vertex_NodeTo_name4;
		//99System.out.print(ArcName4 + ":" + length);
		e = _g.addEdge(vertex_NodeFrom_name4, vertex_NodeTo_name4);
		// _gdisp.addEdge(vertex_NodeFrom_name4, vertex_NodeTo_name4);
		_g.setEdgeWeight(e, length);

		// 1//2//3//99System.out.print("_g after edges add =  " + _g.toString());
		if (!identToArcMap.containsKey(ident1))
			identToArcMap.put(ident1, tarc);
		if (!identToArcMap.containsKey(ident2))
			identToArcMap.put(ident2, tarc);
		if (!identToArcMap.containsKey(ident3))
			identToArcMap.put(ident3, tarc);
		if (!identToArcMap.containsKey(ident4))
			identToArcMap.put(ident4, tarc);

		// use string arrays instead of edge names
		String[] arcStringArray1 = new String[2];
		arcStringArray1[0] = vertex_NodeFrom_name1;
		arcStringArray1[1] = vertex_NodeTo_name1;
		// 3//99System.out.print(Arrays.deepToString(arcStringArray1) + "***1");
		String arcStringKey1 = Arrays.deepToString(arcStringArray1);
		if (!arcStringArrayKeyToArcMap.containsKey(arcStringKey1))
			arcStringArrayKeyToArcMap.put(arcStringKey1, tarc);

		if (!arcStringArrayKeyToTrainOrientationMap.containsKey(arcStringKey1))
			arcStringArrayKeyToTrainOrientationMap.put(arcStringKey1,
					train_orientation1);

		if (!arcStringArrayKeyToTrainMovementMap.containsKey(arcStringKey1))
			arcStringArrayKeyToTrainMovementMap.put(arcStringKey1,
					train_movement1);

		if (!identToArcStringArrayMap.containsKey(ident1))
			identToArcStringArrayMap.put(ident1, arcStringArray1);

		String[] arcStringArray2 = new String[2];
		arcStringArray2[0] = vertex_NodeFrom_name2;
		arcStringArray2[1] = vertex_NodeTo_name2;
		// 3//99System.out.print(Arrays.deepToString(arcStringArray2) + "***2");
		String arcStringKey2 = Arrays.deepToString(arcStringArray2);
		if (!arcStringArrayKeyToArcMap.containsKey(arcStringKey2))
			arcStringArrayKeyToArcMap.put(arcStringKey2, tarc);

		if (!arcStringArrayKeyToTrainOrientationMap.containsKey(arcStringKey2))
			arcStringArrayKeyToTrainOrientationMap.put(arcStringKey2,
					train_orientation2);

		if (!arcStringArrayKeyToTrainMovementMap.containsKey(arcStringKey2))
			arcStringArrayKeyToTrainMovementMap.put(arcStringKey2,
					train_movement2);

		if (!identToArcStringArrayMap.containsKey(ident2))
			identToArcStringArrayMap.put(ident2, arcStringArray2);

		String[] arcStringArray3 = new String[2];
		arcStringArray3[0] = vertex_NodeFrom_name3;
		arcStringArray3[1] = vertex_NodeTo_name3;
		// 3//99System.out.print(Arrays.deepToString(arcStringArray3) + "***3");
		String arcStringKey3 = Arrays.deepToString(arcStringArray3);
		if (!arcStringArrayKeyToArcMap.containsKey(arcStringKey3))
			arcStringArrayKeyToArcMap.put(arcStringKey3, tarc);

		if (!arcStringArrayKeyToTrainOrientationMap.containsKey(arcStringKey3))
			arcStringArrayKeyToTrainOrientationMap.put(arcStringKey3,
					train_orientation3);

		if (!arcStringArrayKeyToTrainMovementMap.containsKey(arcStringKey3))
			arcStringArrayKeyToTrainMovementMap.put(arcStringKey3,
					train_movement3);

		if (!identToArcStringArrayMap.containsKey(ident3))
			identToArcStringArrayMap.put(ident3, arcStringArray3);

		String[] arcStringArray4 = new String[2];
		arcStringArray4[0] = vertex_NodeFrom_name4;
		arcStringArray4[1] = vertex_NodeTo_name4;
		// 3//99System.out.print(Arrays.deepToString(arcStringArray4) +
		// "***4 orientation:" + train_orientation4 + " movement:" +
		// train_movement4 );
		String arcStringKey4 = Arrays.deepToString(arcStringArray4);
		// 3//99System.out.print(arcStringKey4 + "***4");
		if (!arcStringArrayKeyToArcMap.containsKey(arcStringKey4))
			arcStringArrayKeyToArcMap.put(arcStringKey4, tarc);

		if (!arcStringArrayKeyToTrainOrientationMap.containsKey(arcStringKey4))
			arcStringArrayKeyToTrainOrientationMap.put(arcStringKey4,
					train_orientation4);

		if (!arcStringArrayKeyToTrainMovementMap.containsKey(arcStringKey4))
			arcStringArrayKeyToTrainMovementMap.put(arcStringKey4,
					train_movement4);

		if (!identToArcStringArrayMap.containsKey(ident4))
			identToArcStringArrayMap.put(ident4, arcStringArray4);
	}

	/**
	 * @return the arcStringArrayKeyToTrainOrientationMap
	 */
	public Map<String, String> getArcStringArrayKeyToTrainOrientationMap() {
		return arcStringArrayKeyToTrainOrientationMap;
	}

	public String getOrientation(String arcStringArrayKey) {
		// String arcStringArrayKey = null;
		String Orientation = getArcStringArrayKeyToTrainOrientationMap().get(
				arcStringArrayKey);
		return Orientation;
	}

	public String getMovement(String arcStringArrayKey) {
		// String arcStringArrayKey = null;
		String movement = getArcStringArrayKeyToTrainMovementMap().get(
				arcStringArrayKey);
		return movement;
	}

	/**
	 * @return the arcStringArrayKeyToTrainMovementMap
	 */
	public Map<String, String> getArcStringArrayKeyToTrainMovementMap() {
		return arcStringArrayKeyToTrainMovementMap;
	}

	/**
	 * @return the identToArcNameMap
	 */
	public Map<String, F3_TArc> getIdentToArcMap() {
		return identToArcMap;
	}

	public Map<String, String[]> getIdentToArcStringArrayMap() {
		return identToArcStringArrayMap;
	}

	/**
	 * @return the identToArcStringArray
	 */
	public Map<String, F3_TArc> getArcStringArrayToArcMap() {
		return arcStringArrayKeyToArcMap;
	}

	public void AddNode(E1_TNodeNames tNodeNames, E3_TNode tnode) {
		String nodename = tNodeNames.getNodeName();
		String str_dJNodes[] = { "_To_For", "_From_For", "_To_Rev", "_From_Rev" };
		int offset = 0;
		for (String str_dJNode : str_dJNodes) {
			String vertex_name = nodename + str_dJNode;
			offset += 35;
			// 1//2//3//99System.out.print(vertex_name);;
			_g.addVertex(vertex_name);
			// _gdisp.addVertex(vertex_name);
			int x_pos = (int) (tnode.getPosition().x * 40) + 800;
			int y_pos = -(int) (tnode.getPosition().y * 50) + 500 + offset;
			positionVertexAt(vertex_name, x_pos, y_pos);
		}

		// if we allow revering at the nodes, we need to add links from the
		// forward and reverse Vertices at the track node
		String reversingAllowed = tNodeNames.getReversingAllowed();

		if (reversingAllowed == "T") {
			float weighting = 100.0f;

			DefaultWeightedEdge e = _g.addEdge(nodename + "_To_For", nodename
					+ "_From_For");
			// _gdisp.addEdge(nodename + "_To_For", nodename + "_From_For");
			_g.setEdgeWeight(e, weighting);
			// 1//2//3//99System.out.print(e.toString() + ":" + weighting );

			e = _g.addEdge(nodename + "_From_For", nodename + "_To_For");
			// _gdisp.addEdge(nodename + "_From_For", nodename + "_To_For");
			_g.setEdgeWeight(e, weighting);
			// 1//2//3//99System.out.print(e.toString() + ":" + weighting );

			e = _g.addEdge(nodename + "_To_Rev", nodename + "_From_Rev");
			// _gdisp.addEdge(nodename + "_To_Rev", nodename + "_From_Rev");
			_g.setEdgeWeight(e, weighting);
			// 1//2//3//99System.out.print(e.toString() + ":" + weighting );

			e = _g.addEdge(nodename + "_From_Rev", nodename + "_To_Rev");
			// _gdisp.addEdge(nodename + "_From_Rev", nodename + "_To_Rev");
			_g.setEdgeWeight(e, weighting);
			// 1//2//3//99System.out.print(e.toString() + ":" + weighting );

		}
		// 1//2//3//99System.out.print("_g after nodes add =  " + _g.toString());
	}

	// get the tangent and position and the vector at right angles to the
	// tangent
	// Vector3d tan = _TG.get_NodeLinkedList().;
	// TrackNodesDict[TNStr].Tangent;
	// Vector3d pos = _TG.TrackNodesDict[TNStr].Position;
	// Vector3d right = Vector3d.Cross (
	// _TG.TrackNodesDict[TNStr].Tangent,Vector3d.UnitZ );

	// translate the 4 nodes

	// Node Node_To_For = _G.putNode ( To_For.X,To_For.Y,To_For.Z );
	// _NodesDict.Add ( String.Concat ( TNStr,"_To_For" ),Node_To_For );
	//
	// Vector3d To_Rev_Translation = Vector3d.Multiply ( right,-1.0f );
	// Vector3d To_Rev = Vector3d.Add (
	// _TG.TrackNodesDict[TNStr].Position,To_Rev_Translation );
	// Node Node_To_Rev = _G.AddNode ( To_Rev.X,To_Rev.Y,To_Rev.Z );
	// _NodesDict.Add ( String.Concat ( TNStr,"_To_Rev" ),Node_To_Rev );
	//
	// Vector3d From_For_Translation = Vector3d.Multiply ( right,2.0f );
	// Vector3d From_For = Vector3d.Add (
	// _TG.TrackNodesDict[TNStr].Position,From_For_Translation );
	// Node Node_From_For = _G.AddNode ( From_For.X,From_For.Y,From_For.Z );
	// _NodesDict.Add ( String.Concat ( TNStr,"_From_For" ),Node_From_For );
	//
	// Vector3d From_Rev_Translation = Vector3d.Multiply ( right,1.0f );
	// Vector3d From_Rev = Vector3d.Add (
	// _TG.TrackNodesDict[TNStr].Position,From_Rev_Translation );
	// Node Node_From_Rev = _G.AddNode ( From_Rev.X,From_Rev.Y,From_Rev.Z );
	// _NodesDict.Add ( String.Concat ( TNStr,"_From_Rev" ),Node_From_Rev );

	/**
	 * @param startNodeName
	 * @param startNodeEngineDirection
	 *            : For or Rev
	 * @param startNodeMovingDirection
	 *            : To or From
	 * @param endNodeName
	 * @param endNodeEngineDirection
	 * @param endNodeMovingDirection
	 */
	public void ShortestDistance(String startNodeName,
			String startNodeMovingDirection, String startNodeEngineDirection,
			String endNodeName, String endNodeMovingDirection,
			String endNodeEngineDirection) {

		String startNodeDescriptor = "_" + startNodeMovingDirection + "_"
				+ startNodeEngineDirection;
		String endNodeDescriptor = "_" + endNodeMovingDirection + "_"
				+ endNodeEngineDirection;

		String djStartNodeName = startNodeName + startNodeDescriptor;
		String djEndNodeName = endNodeName + endNodeDescriptor;

		// note undirected edges are printed as: {<v1>,<v2>}
		// 1//2//3//99System.out.print(_g.toString());
		// 3//99System.out.print("start and end nodes" + djStartNodeName + ":" +
		// djEndNodeName);
		_shortestPath = new DijkstraShortestPath<String, DefaultWeightedEdge>(
				_g, djStartNodeName, djEndNodeName);
		if (_shortestPath != null) {
			//99System.out.print(_shortestPath.getPathEdgeList().toString());
		} else {
			//99System.out.print("could not find path");
		}

	}

	public List<DefaultWeightedEdge> get_shortestPathList() {
		return _shortestPath.getPathEdgeList();
	}

	public void ShortestDistance(String[] routePair) {
		String[] From = routePair[0].split("_");
		String[] To = routePair[1].split("_");

		String startNodeName = From[0]; // Typical 1
		String startNodeMovingDirection = From[1]; // Typical To
		String startNodeEngineDirection = From[2]; // Typical For

		String endNodeName = To[0]; // Typical 1
		String endNodeMovingDirection = To[1]; // Typical To
		String endNodeEngineDirection = To[2]; // Typical For

		// now set _shortestPath
		ShortestDistance(startNodeName, startNodeMovingDirection,
				startNodeEngineDirection, endNodeName, endNodeMovingDirection,
				endNodeEngineDirection);
	}

	
	F3_TArc getArc( String[] startArcPair){
		String arcPairKey = Arrays.deepToString(startArcPair);
		Map<String, F3_TArc> arcStringArrayToArcMap = this.getArcStringArrayToArcMap();
		if (arcStringArrayToArcMap.keySet().contains(arcPairKey)){
			F3_TArc tArc = arcStringArrayToArcMap.get(arcPairKey);
			return tArc;
		} 
		else {
			//3//99System.out.print("tArc could not be found");
			return null;
		}
	}
	
	
	
}
