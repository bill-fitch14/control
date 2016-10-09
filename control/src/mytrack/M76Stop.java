package mytrack;

import java.util.Arrays;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

public class M76Stop extends M43_TruckData_Display{
	
	boolean isSensor;
	
	public M76Stop(String arc, String directionFacing, D_MyGraph graph,
			String startFraction, boolean isSensor) {
		super(arc, directionFacing, graph, startFraction, startFraction);
		this.isSensor = isSensor;
		
	}

	private BranchGroup stopBranchGroup = new BranchGroup();

	public BranchGroup getStopBranchGroup() {
		return get_BG();
	}

	public void setStopBranchGroup(BranchGroup stopBranchGroup) {
		this.stopBranchGroup = stopBranchGroup;
	}

	
	
	boolean objectIsOnRoute(K2_Route route, M41_TruckData_Position truckdata_position, D_MyGraph graph){

		/*
		 * The object has been specified by a track location which is specified by an arc
		 * This ia a string of the form 1_F_2_B_For
		 * 
		 * We could only stop if the train is moving with that orientation and direction
		 * but we assume that we want to stop the train if the route contains
		 * 
		 * 1_F_2_B_For, 2_B_1_F_For, but not 1_F_2_B_Rev, 2_B_1_F_Rev 
		 * for that we need to specify another stop 
		 */



		/*
		 * Get the position on the track
		 */
		String[] startArcStringArray=U7_StartArcPairs.getStringArraySameDirectionTravellingSameDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray2=U7_StartArcPairs.getStringArrayOppDirectionTravellingSameDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray3=U7_StartArcPairs.getStringArraySameDirectionTravellingOppDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		String[] startArcStringArray4=U7_StartArcPairs.getStringArrayOppDirectionTravellingOppDirectionFacing(truckdata_position.getArc(), truckdata_position.getDirectionFacing(), graph);
		


		/*
		 * Get all the positions on the path and check if any of the above stringArrays are on the route
		 * Should be startArcStringArray, but if any of the others then give error message and continue
		 */

		for (int i = 0; i < route.getRoutePath().size(); i++) {
			route.startArcPairList = route.getRoutePath().get(i);
			route.indexOfRoutePath = i;
			
			   //check this
			
			//3//99System.out.print("index of startRoute = " + indexOfStartRoute);
			for (int j = 0; j < route.startArcPairList.size(); j++) {
				
				String[] ArcStringArray = route.startArcPairList.get(j);
				/*
				 * Only set the remainder of the data if the engine lies on the route
				 */
				//99System.out.print("size "+route.startArcPairList.size()+" Hi "+ j + Arrays.toString(ArcStringArray) + " " + Arrays.toString(startArcStringArray));
				if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray))){

					//get the index corresponding to the list of arcs
					route.indexOfStartArcPairList = j;
					route.startArcPair = route.startArcPairList.get(route.indexOfStartArcPairList);
//					route.indexOfStartArcPairList = j;
//					route.setIndexOfStartArcPairList(j);
//					truckdata_position.setIndexOfStartArcPairList(j);
//					
//					String[] startArcPair = truckdata_position.getStartArcPairList().get(indexOfStartArcPairList);
//					route.setStartArcPair(startArcPair);
//					truckdata_position.setStartArcPair(startArcPair);
					
					String startArcPairKey = getEngineRoutePairKey(truckdata_position.getStartArcPair());
					String truckOrientation = graph.getOrientation(startArcPairKey);
					String truckMovement = graph.getMovement(startArcPairKey);
					return true;

				} else {
					if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray2))){
						//99System.out.print("OppDirectionTravelling");
					}else if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray3))){
						//99System.out.print("OppDirectionFacing");
					}else if(Arrays.toString(ArcStringArray).equals(Arrays.toString(startArcStringArray4))){
						//99System.out.print("OppDirectionTravelling and OppDirectionFacing");
					}
					//return false;
				}
			}	
		}
		return false;
	}

}
