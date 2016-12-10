package mytrack;

import java.util.LinkedList;
import java.util.List;

import javax.media.j3d.BranchGroup;

public final class M75Stops {

	static List <M76Stop> stops = new LinkedList<M76Stop>();

	public static void addstop(M76Stop stop){
		if (!getStops().contains(stop)) {
			getStops().add(stop);
		} else {
			// raise exception
		}
	}

	public static void set_BG(BranchGroup _BG){
		for(M76Stop st: stops){
			_BG.addChild(st.getStopBranchGroup());
		}
	}

	public static List<M76Stop> getStops() {
		return stops;
	}

	public static M76Stop getStop(String string) {
		for(M76Stop st: stops){
			if(st.objectStr.equals(string)){
				return st;

			}
		}
		//99System.out.print("no stop returned" + string);
		return null;
	}


	public static void addStopPosition(D_MyGraph graph, String stopName){

		String arc = null;
		String fraction = null;
		String directionFacing = null;
		String stopNo = null;

		//M76Stop stop_display;
		float stopLength;
		String stopStr;

		M76Stop stop_displayhf1;
		switch (stopName) {
		case "st1f1":
			arc = "2_F_3_B";
			fraction = "2.1";
			directionFacing = "Rev";
			//stopNo = "S1F1";
			M76Stop stop_display1f1 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_display1f1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "S1F1";
			stop_display1f1.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_display1f1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display1f1);
			break;
			//		case "st1f2":
			//			arc = "2_F_3_B";
			//			fraction = "2.5";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_display1f2 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_display1f2.positionWithinSegment3(1.0f);
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "S1F2";
			//			stop_display1f2.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_display1f2.moveWithinSegment3(1.0f);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_display1f2);

		case "st1r1":
			arc = "3_B_2_F";
			fraction = "2.1";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_display1r1 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_display1r1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "S1R1";
			stop_display1r1.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_display1r1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display1r1);
			break;

			//		case "st1r2":
			//			arc = "3_B_2_F";
			//			fraction = "2.5";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_display1r2 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_display1r2.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "S1R2";
			//			stop_display1r2.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_display1r2.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_display1r2);//	

		case "st2f1":
			arc = "4_F_5_B";
			fraction = "2.1";
			directionFacing = "Rev";
			//stopNo = "S1F1";
			M76Stop stop_display2f1 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_display2f1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "S2F1";
			stop_display2f1.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_display2f1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display2f1);
			break;

			//		case "st2f2":
			//			arc = "4_F_5_B";
			//			fraction = "1.5";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_display2f2 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_display2f2.positionWithinSegment3(1.0f);
			//			
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "S2F2";
			//			stop_display2f2.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_display2f2.moveWithinSegment3(1.0f);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_display2f2);

		case "st2r1":
			arc = "5_B_4_F";
			fraction = "2.1";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_display2r1 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_display2r1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "S2R1";
			stop_display2r1.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_display2r1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display2r1);
			break;

			//		case "st2r2":
			//			arc = "5_B_4_F";
			//			fraction = "1.5";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_display2r2 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_display2r2.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "S2R2";
			//			stop_display2r2.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_display2r2.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_display2r2);//	

		case "st3f1":
			arc = "4_F_6_B";
			fraction = "2.1";
			directionFacing = "Rev";
			//stopNo = "S1F1";
			M76Stop stop_display3f1 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_display3f1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "S3F1";
			stop_display3f1.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_display3f1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display3f1);
			break;

			//		case "st3f2":
			//			arc = "4_F_6_B";
			//			fraction = "1.5";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_display3f2 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_display3f2.positionWithinSegment3(1.0f);
			//			
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "S3F2";
			//			stop_display3f2.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_display3f2.moveWithinSegment3(1.0f);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_display3f2);

		case "st3r1":
			arc = "6_B_4_F";
			fraction = "2.1";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_display3r1 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_display3r1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "S3R1";
			stop_display3r1.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_display3r1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_display3r1);
			break;

			//		case "st3r2":
			//			arc = "6_B_4_F";
			//			fraction = "1.5";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_display3r2 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_display3r2.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "S3R2";
			//			stop_display3r2.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_display3r2.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_display3r2);//	
			//		case "sthf1":
			//			arc = "1_F_2_B";
			//			fraction = "3.1";
			//			directionFacing = "Rev";
			//			//stopNo = "S1F1";
			//			stop_displayhf1 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_displayhf1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "SHF1";
			//			stop_displayhf1.set_BG(stopLength, stopStr, stopStr, "Engine");           
			//			stop_displayhf1.moveWithinSegment3(1.0f*U4_Constants.scalefactor);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_displayhf1);
			//			break;

			//		case "sthf2":
			//			arc = "1_F_2_B";
			//			fraction = "3.1";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_displayhf2 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_displayhf2.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "SHF2";
			//			stop_displayhf2.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_displayhf2.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_displayhf2);
			//			break;

			//		case "sthf3":
			//			arc = "1_F_2_B";
			//			fraction = "3.1";
			//			directionFacing = "Rev";
			//			//stopNo = "S1";
			//			M76Stop stop_displayhf3 = new M76Stop(arc, directionFacing, graph, fraction);
			//			stop_displayhf3.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			
			//			stopLength = 0.5f*U4_Constants.scalefactor;
			//			stopStr = "SHF2";
			//			stop_displayhf3.set_BG(stopLength, stopStr, stopStr, "Engine");
			//			stop_displayhf3.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//			//trainsOnRoute.addtrainOnRoute(route1);
			//			addstop(stop_displayhf3);
			//			break;

		case "sthr1":
			arc = "2_B_1_F";
			fraction = "2.7";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_displayhr1 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_displayhr1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "SHR1";
			stop_displayhr1.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_displayhr1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_displayhr1);
			break;

		case "sthr2":
			arc = "2_B_1_F";
			fraction = "2.7";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_displayhr2 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_displayhr2.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "SHR2";
			stop_displayhr2.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_displayhr2.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_displayhr2);//	
			break;

		case "sthr3":
			arc = "2_B_1_F";
			fraction = "2.7";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_displayhr3 = new M76Stop(arc, directionFacing, graph, fraction, false);
			stop_displayhr3.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "SHR3";
			stop_displayhr3.set_BG(stopLength, stopStr, stopStr, "Stop");
			stop_displayhr3.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_displayhr3);
			break;	

			//		case "st2":
			//			arc = "4_F_5_B";
			//			fraction = "4.5";
			//			direction = "For";
			//			stopNo = "T2";
			//			stop = new L3_TrackLocation("stop", stopNo, arc, fraction, direction);
			//			stop = new L3_TrackLocation(arc, fraction, "Stop");
			//			stops.addLocation(stop);
			//			break;
			//		case "st2p":
			//			arc = "4_F_5_B";
			//			fraction = "5.0";
			//			direction = "For";
			//			stopNo = "T2";
			//			stop = new L2_TrackLocation("stop", stopNo, arc, fraction, direction);
			//			stops.addLocation(stop);
			//			break;
			//
			//		case "st3":
			//			arc = "4_F_6_B";
			//			fraction = "4.5";
			//			direction = "For";
			//			stopNo = "T3";
			//			stop = new L2_TrackLocation("stop", stopNo, arc, fraction, direction);
			//			stops.addLocation(stop);
			//			break;
			//		case "st3p":
			//			arc = "4_F_6_B";
			//			fraction = "5.0";
			//			direction = "For";
			//			stopNo = "T3";
			//			stop = new L2_TrackLocation("stop", stopNo, arc, fraction, direction);
			//			stops.addLocation(stop);
			//			break;			
			//
			//		case "sth":
			//			arc = "1_F_2_B";
			//			fraction = "4.5";
			//			direction = "For";
			//			stopNo = "T4";
			//			stop = new L2_TrackLocation("stop", stopNo, arc, fraction, direction);
			//			stops.addLocation(stop);
			//			break;
			//		case "sthp":
			//			arc = "1_F_2_B";
			//			fraction = "5.0";
			//			direction = "For";
			//			stopNo = "T4";
			//			stop = new L2_TrackLocation("stop", stopNo, arc, fraction, direction);
			//			stops.addLocation(stop);
			//			break;
		}
	}

	public static void addSensorPosition(D_MyGraph graph, String sensorName) {

		String arc = null;
		String fraction = null;
		String directionFacing = null;
		String stopNo = null;

		//M76Stop stop_display;
		float stopLength;
		String stopStr;

		M76Stop stop_displayhf1;
		switch (sensorName) {
		case "tosthr1":
			arc = "2_B_1_F";
			fraction = "3.625";
			fraction = "2.5";
			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_displaytosthr1 = new M76Stop(arc, directionFacing, graph, fraction, true);
			stop_displaytosthr1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "SENR1";
			stop_displaytosthr1.set_BG(stopLength, stopStr, stopStr, "Sensor");
			stop_displaytosthr1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_displaytosthr1);//	
			break;
		case "tosthr2":
			arc = "2_B_1_F";
			fraction = "2.20625";

			directionFacing = "Rev";
			//stopNo = "S1";
			M76Stop stop_displaytosthr2 = new M76Stop(arc, directionFacing, graph, fraction, true);
			stop_displaytosthr2.positionWithinSegment3(0.5f*U4_Constants.scalefactor);


			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "SENR2";
			stop_displaytosthr2.set_BG(stopLength, stopStr, stopStr, "Sensor");
			stop_displaytosthr2.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_displaytosthr2);//	
			break;
		case "fromsthf1":
			arc = "1_F_2_B";
			fraction = "2.20625";

			directionFacing = "Rev";
			//stopNo = "S1F1";
			M76Stop stop_displayfromsthf1 = new M76Stop(arc, directionFacing, graph, fraction, true);
			stop_displayfromsthf1.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "SENF1";
			stop_displayfromsthf1.set_BG(stopLength, stopStr, stopStr, "Sensor");
			stop_displayfromsthf1.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_displayfromsthf1);
			break;
		case "fromsthf2":
			arc = "1_F_2_B";
			fraction = "3.625";
			fraction = "2.5";
			directionFacing = "Rev";
			//stopNo = "S1F1";
			M76Stop stop_displayfromsthf2 = new M76Stop(arc, directionFacing, graph, fraction, true);
			stop_displayfromsthf2.positionWithinSegment3(0.5f*U4_Constants.scalefactor);
			stopLength = 0.5f*U4_Constants.scalefactor;
			stopStr = "SENF2";
			stop_displayfromsthf2.set_BG(stopLength, stopStr, stopStr, "Sensor");
			stop_displayfromsthf2.moveWithinSegment3(0.5f*U4_Constants.scalefactor);
			//trainsOnRoute.addtrainOnRoute(route1);
			addstop(stop_displayfromsthf2);
			break;
			

		}
	}
}
