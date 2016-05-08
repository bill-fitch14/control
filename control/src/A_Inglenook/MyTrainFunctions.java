package A_Inglenook;

import java.util.Deque;

//import mytrack.H2_Engine_Route;

public class MyTrainFunctions {

//	H2_Engine_Route er;
	
	
	void setPositionsOfTrains(long positions){
		long[] positions1 = {positions,positions};
		Myfunctions.convertLongToStacks(positions1);
		
		//displayTrain(Myfunctions.st1, station1, );
	}
	

	
	private void setPositionOfTrain(Deque<Integer[]> stack, int station) {
		// TODO Auto-generated method stub
		
	}



	/**
	 * train starts with end of train at marker in header
	 * move so end of train is at sensor in siding
	 */
	void popFromHeader(int siding){
//		set route
//		  the start position of the train is with the end of the train at the marker
		  setRouteToSiding(siding);
//		move train
//		  set speed of train so it moves
	}


	private void setRouteToSiding(int siding) {
		// TODO Auto-generated method stub
		
	}
}
