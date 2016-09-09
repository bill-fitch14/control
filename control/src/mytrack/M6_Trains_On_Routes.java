package mytrack;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.media.j3d.BranchGroup;

import sm2.E1;

public class M6_Trains_On_Routes {

	static List<M61_Train_On_Route> trainsOnRoutes = new LinkedList<M61_Train_On_Route>();

	// private static boolean tr.checkforstop;

	public static M61_Train_On_Route getTrainOnRoute(String trainStr) {
		for (M61_Train_On_Route tr : trainsOnRoutes) {
			if (tr.getTrainStr().equals(trainStr)) {
				return tr;
			}
		}
		return null;
	}

	public static void movetrain(String trainStr) {
		M61_Train_On_Route tr = getTrainOnRoute(trainStr);

		for (M43_TruckData_Display truck : tr.getTruckPositions()) {
			truck.setCurrentStopActive(false);
		}
		tr.setMoving(true);

	}

	int i = 0;

	public static void moveTrainCheckForStop(String trainStr,
			M43_TruckData_Display stop, int truckNo) {

		M61_Train_On_Route tr = getTrainOnRoute(trainStr);


		// set stops for all trucks false
		for (M43_TruckData_Display truck : tr.getTruckPositions()) {
			truck.setCurrentStopActive(false);
		}

		if (stop != null) {
			// set the stop for the required truck true
			tr.getTruckData(truckNo).setStop(stop);
		}
		// set the train moving
		((M61_Train_On_Route) tr).setMoving(true);

	}

	public void set_BG(BranchGroup _BG) {
		for (M62_train tr : trainsOnRoutes) {
			_BG.addChild(tr.getTrainBranchGroup());
		}
	}

	public void gsr(D_MyGraph graph) {

		for (M62_train tr : trainsOnRoutes) {
			// tr.getMyroute().convertRouteToEngineRoute(tr.getMyroute().getRoute());
			// tr.getMyroute().generateEngineRoutePairs();
			// tr.getMyroute().setDJG(tr.getDJG());
			// tr.getMyroute().generateEngineRoutePath();

			// tr.setParametersAndDisplay(tr.getDJG(),
			// tr.getK2_route().getRoute());
			// //tr.getMyroute().setParameters(tr.getDJG(),
			// tr.getMyroute().getRoute());
			// boolean objectIsOnRoute = tr.objectIsOnRoute();
			// if(objectIsOnRoute){
			// tr.setTrainLocationAndDisplay();
			// //tr.set_BG_train();
		}
	}

	public void addtrainOnRoute(M61_Train_On_Route trainOnRoute) {
		if (!this.getTrainsOnRoutes().contains(trainOnRoute)) {
			this.getTrainsOnRoutes().add(trainOnRoute);
		} else {
			// raise exception
		}
	}

	public List<M61_Train_On_Route> getTrainsOnRoutes() {
		return trainsOnRoutes;
	}

	public void update() {
		for (M61_Train_On_Route t_on_route : trainsOnRoutes) {
			// 3//99System.out.print("processing train no:" + er.getTrainNo());
			if (t_on_route.isMoving()) {

				t_on_route.update();
			}
		}
	}

	public void generate_shortest_routes(D_MyGraph _DJG) {
		for (M62_train trainOnRoute : trainsOnRoutes) {
			// convert from 1_B_2_F-1-For,4_F_5_B-3-For
			// route = string
			// trainOnRoute.setParametersAndDisplay(_DJG,
			// trainOnRoute.getRouteStr());
			// includes below
			//
			//
			// // er.print_ep();
			// er.setHM(_HM);
			//
			// boolean startArcOnRoute =
			// er.get_headOfTrainData().setDataUsingTree(_DJG , er,
			// er.getStartArc(), er.getStartFraction(), er.getStartDirection());
			// if(startArcOnRoute){
			// //3//99System.out.print("train on route");
			// er.set_train_location(); // sets trainLocation in er
			// er.set_BG_Train();
			// }
			// else
			// {
			// //indicate that train is not on route
			////99//99System.out.print("train not on route");
			// }

		}
	}

}
