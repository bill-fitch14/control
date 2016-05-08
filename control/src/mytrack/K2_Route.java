package mytrack;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.media.j3d.BranchGroup;

import myscene.C_PickRoutines;

import org.jgrapht.graph.DefaultWeightedEdge;

import sm2.E1;

public class K2_Route {

	private D_MyGraph graph;
	private String strRoute;
	
	/**
	 * The route is formed from the strings in the linked list.
	 * Each string is of the form 2_F_4_B-1,4_F_5_B-3
	 */
	private List <String> route = new LinkedList <String>();
	private List<String[]> routePairs;
	private List<List<String[]>> routePath;
	int indexOfRoutePath;
	List<String[]> startArcPairList;
	int indexOfStartArcPairList;
	String[] startArcPair;
	private C1_BranchGroup branchgroup;
	
	
	public K2_Route(String strRoute, D_MyGraph graph, C1_BranchGroup branchGroup){
		
		this.strRoute = strRoute;
		this.graph = graph;
		this.branchgroup = branchGroup;
		setup();
		
//		setParametersAndDisplay(tr.getDJG(), tr.getMyroute().getRoute());
//		setParameters(tr.getDJG(), tr.getMyroute().getRoute());
		//			boolean objectIsOnRoute = tr.objectIsOnRoute();
		//			if(objectIsOnRoute){
		//				tr.setTrainLocationAndDisplay();
		//				//tr.set_BG_train();
	}
	
	public void setup(){
		
		this.route = convertRouteToEngineRoute(strRoute);
		this.routePairs = generateEngineRoutePairs(route);
		
		this.routePath = generateEngineRoutePath(routePairs, graph);
		this.startArcPairList = routePath.get(0);
	}
	
	public void switchPointsOnRoute(D_MyGraph graph2, C2_DJGraph graph3){
		//assume the route is a simple route with no changes of direction. 
		//If there is a change of direction we need to do somethisng more complicated
		//but we don't need it for the shunting puzzle

		setup();

		//get the start and end node

		//if startarcpair is 3_to_rev 2_to rev2
		startArcPairList = this.getStartArcPairList();
		for(String[] StartArcPair: startArcPairList){
			//get nodes
			//for(String x :StartArcPair){
				

			F3_TArc tArc = getArc(StartArcPair, graph2);
			
			G_TSegment[] segs = new G_TSegment[2];
			
			segs[0] = tArc.get_TrackSegments().get(0);
			segs[1] = tArc.get_TrackSegments().get(tArc.get_TrackSegments().size()-1);
			
			for(G_TSegment seg:segs){
//				String trackType1 = seg.get_TrackType();
//				
//
//				String trackType2 = seg.get_TrackType();
				
				String segmentName = seg.getSegmentName();
				String[] segmentNameFeatures0 = segmentName.split(":");
				String[] segmentNameFeatures1 = segmentNameFeatures0[0].split("_");

				String[] segmentNameFeatures2 = segmentNameFeatures0[1].split("_");
				String node1 = segmentNameFeatures2[0];
				String node1_direction = segmentNameFeatures2[1];
				String element = segmentNameFeatures2[2];
			
				//get corresponding point
				for(I1_PointNames point:graph3.get_linkedLists().get_PointLinkedList()){

					String nodeName1 = point.getNodeName();
					String direction = point.getNodeDirection();
					String straight = point.getStraight();
					String curve = point.getCurve();
					if(node1.equals(nodeName1) && node1_direction.equals(direction)){
						String desiredState;
						if (straight.equals(element)){
							desiredState = straight;
						}else{
							desiredState = curve;
						}
						 I1_PointNames pointname = point;
						
						C_PickRoutines.switchPoint(pointname, desiredState);
					}


				}
			}
		}

	}


	
	public List<String> convertRouteToEngineRoute(String strRoute) {

		// route is of the form 1_F_To_For,2_B_To_For
		String[] r = strRoute.split(",");
		route = new LinkedList<String>(Arrays.asList(r));
		return route;
	}
	
	// private List<String[]> EngineRoutePairs = new LinkedList<String[]>();
	
	public LinkedList<String[]> generateEngineRoutePairs(List<String> EngineRoute) {
		
		// the engineroute consists of the
		// the engineroutepairs consist of the routefrom and the routeto pira
		LinkedList<String[]> EngineRoutePairs = new LinkedList<String[]>();
		for (int i = 0; i < EngineRoute.size() - 1; i++) {
			String routeFrom = EngineRoute.get(i);
			String routeTo = EngineRoute.get(i + 1);
			String[] erp = { routeFrom, routeTo };
			EngineRoutePairs.add(erp);
		}
		return EngineRoutePairs;
	}

	public List<List<String[]>> generateEngineRoutePath( List<String[]> EngineRoutePairs, D_MyGraph graph) {
		
		List<List<String[]>> EngineRoutePath = new LinkedList<List<String[]>>();
		
		for (String[] erp : EngineRoutePairs) {
			
			if (graph == null){
				int a = 1;
				a++;
			}

			graph.ShortestDistance(erp);
			// generate a list of weighted edges for each engine route pair
			List<DefaultWeightedEdge> edgeListDWE = graph.get_shortestPathList();

			List<String[]> edgeStrList = new LinkedList<String[]>();
			// turn each of the Weighted edges into a String[] pair containing a
			// From and To item
			for (DefaultWeightedEdge item : edgeListDWE) {
				String[] edge_pair = item.toString().replace("(", "")
						.replace(")", "").split(" : ");
				edgeStrList.add(edge_pair);
			}
			// Store the result in EngineRoutePath
			EngineRoutePath.add(edgeStrList);
		}
		
		return EngineRoutePath;
	}

	public D_MyGraph getGraph() {
		return graph;
	}

	public String getStrRoute() {
		return strRoute;
	}

	public List<String> getRoute() {
		return route;
	}

	public List<String[]> getRoutePairs() {
		return routePairs;
	}

	public List<List<String[]>> getRoutePath() {
		return routePath;
	}

//	public List<String[]> getEngineRoutePairs() {
//		return EngineRoutePairs;
//	}

	public void checkOnRoute(M41_TruckData_Position headOfTrain) {
		
		
	}

	/**
	 * @return the startArcPairList
	 */
	public List<String[]> getStartArcPairList() {
		return startArcPairList;
	}

	/**
	 * @param startArcPairList the startArcPairList to set
	 */
	public void setStartArcPairList(List<String[]> startArcPairList) {
		this.startArcPairList = startArcPairList;
	}

	public int getIndexOfStartArcPairList() {
		return indexOfStartArcPairList;
	}

	public void setIndexOfStartArcPairList(int indexOfStartArcPairList) {
		this.indexOfStartArcPairList = indexOfStartArcPairList;
	}

	public void setStartArcPair(String[] startArcPair) {
		this.startArcPair = startArcPair;
		
	}
	
	public String getEngineRoutePairKey(String[] engineRoutePair) {

		String erpkey = Arrays.deepToString(engineRoutePair);
		return erpkey;
	}

	
	F3_TArc getArc(String[] arcPair, D_MyGraph DJG) {

		String arcPairKey = getEngineRoutePairKey(arcPair);


		Map<String, F3_TArc> arcStringArrayToArcMap = DJG.getArcStringArrayToArcMap();
		

		if (arcStringArrayToArcMap.keySet().contains(arcPairKey)) {
			F3_TArc tArc = arcStringArrayToArcMap.get(arcPairKey);

			return tArc;
		}

		// 3//99System.out.print("tArc could not be found");
		return null;

		// to get the tangent we need
	}
//
//public void switchPoint(I1_PointNames pointname, String desiredState) {
//		
//		//2//3//99System.out.print("pointname:" + pointname.getNodeName() + " desiredState: " + desiredState);
//		String pointNodeDirection = pointname.getNodeDirection();
//		String curve = pointname.getCurve();
//		String pointNodeName = pointname.getNodeName();
//		
//		String offState;
//		if (desiredState.equals("ST")){
//			offState = curve;
//		} else {
//			offState = "ST";
//		}
//		//2//3//99System.out.print("desiredState" + desiredState);
//		// set the branchGroup variables so the desiredState branch goes green and the offState goes red
//		
//		//get the ArcName for the 
//		
//		List<F1_TArcNames> arcLinkedList = branchgroup.get_linkedLists().get_ArcLinkedList();
//		
//		for ( F1_TArcNames tArcName : arcLinkedList){
//			
//			
//			F2_TArcsHashmaps arcsHashmap = branchgroup.get_HM().get_TArcsHashmap();
//			F3_TArc tArc = arcsHashmap.get_TArc(tArcName);
//			
//
//			//turn on SERIAL 90 xxxx yyyy 
//			//xxxx is the node
//			//YYYY is the port
//			
//			//points are on node 0001
//			String prefix = "POINTN";
//			String node = pointname.getNodeNumber();
//			String pointno = pointname.getPointNumber();
//			//pointno = pointname.getPointNumber();
//			
//			String On = "91";
//			String Off = "90";
//			String messageOn = prefix + On + node + pointno;
//			String messageOff = prefix + Off + node + pointno;
//
//			if ( tArcName.getNodeFromName().equals(pointNodeName) &&
//					tArcName.getNodeFromDir().equals(pointNodeDirection)){
//				
//				String tracktype = tArc.getFirstSegment().get_TrackType();
//				if (tracktype.equals(offState)){
//					tArc.getFirstSegment().setpointcolor(Color.red);
//				} else {
//					tArc.getFirstSegment().setpointcolor(Color.green);
//					if (desiredState.equals("ST")){
//						E1.e_process_point_request(new String[]{prefix,On,node,pointno,"ON"});
//						//get_serialIO().writeSerial(messageOn);
//					} else {
//						//get_serialIO().writeSerial(messageOff);
//						E1.e_process_point_request(new String[]{prefix,Off,node,pointno,"OFF"});
//					}
//				}
//				
//			}
//			
//			
//			if ( tArcName.getNodeToName().equals(pointNodeName) &&
//					tArcName.getNodeToDir().equals(pointNodeDirection)){
//				
//				String tracktype = tArc.getLastSegment().get_TrackType();
//				if (tracktype.equals(offState)){
//					tArc.getLastSegment().setpointcolor(Color.red);
//				} else {
//					tArc.getLastSegment().setpointcolor(Color.green);
//				}
//				if (desiredState.equals("ST")){
//					//get_serialIO().writeSerial(messageOn);
//					E1.e_process_point_request(new String[]{prefix,On,node,pointno,"ON"});
//				} else {
//					//get_serialIO().writeSerial(messageOff);
//					E1.e_process_point_request(new String[]{prefix,Off,node,pointno,"OFF"});
//				}
//			}
//		}
//		
//	}

	
	
}
