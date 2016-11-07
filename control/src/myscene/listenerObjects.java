package myscene;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import mytrack.M61_Train_On_Route;

public final class listenerObjects {

	private static PropertyChangeSupport pcs = new PropertyChangeSupport(listenerObjects.class);

	// Property property1
	private static int point1;

	// Property property2
	private static int point2;
	
	//Property engine1
	private static double engineSpeed;
	
	private static long sensortime;

	private static int engineDirection;
	
	public static void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	
	public static void setSensor( String sensorAddress, 
			String sensorName, long millis) {
		pcs.firePropertyChange(sensorName, listenerObjects.sensortime, millis);
		listenerObjects.sensortime = millis;
	}

	public int getPoint1() {
		return point1;
	}

	public static void setPoint1(int property1) {
		pcs.firePropertyChange("property1", listenerObjects.point1, property1);
		listenerObjects.point1 = property1;
	}

	public int getPoint2() {
		return point2;
	}

	public void setPoint2(int property2) {
		pcs.firePropertyChange("property2", listenerObjects.point2, property2);
		listenerObjects.point2 = property2;
	}
//	
//	public void setEngineSpeed(double engineSpeed){
//		pcs.firePropertyChange("loco1R",this.engine1,engineSpeed);
//	}
//	
//	public void setEngineSpeed(double engineSpeed){
//		pcs.firePropertyChange("loco1F",this.engine1,engineSpeed);
//	}
	
	public static void setEngineSpeed(double engineSpeed){
		pcs.firePropertyChange("locoSetSpeed",listenerObjects.engineSpeed,engineSpeed);
		listenerObjects.engineSpeed = engineSpeed;
	}
	
	public static void setEngineDirection(String direction){
		int intDirection;
		if (direction.equals("forwards")){
			intDirection = 1;
		}else{
			intDirection = 0;
		}
		pcs.firePropertyChange("locoSetDirection",listenerObjects.engineDirection,intDirection);
		listenerObjects.engineDirection = intDirection;	
	}
	
	public static void moveLoco(String direction,double engineSpeed){
		setEngineDirection(direction);
		setEngineSpeed(engineSpeed);
		
//		if (direction.equals("forwards")){
//			//System.out.println("forwards loco1F: enginespeed" + engineSpeed);
//
//			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$direction = " + direction);
//			pcs.firePropertyChange("loco1F",listenerObjects.engineSpeed,engineSpeed);
//		}else{
//			//System.out.println("forwards loco1R: enginespeed" + engineSpeed);
//			//pcs.firePropertyChange("locoSetDirection",listenerObjects.engine1,0);
//			pcs.firePropertyChange("loco1R",listenerObjects.engineSpeed,engineSpeed);
//		}
//		listenerObjects.engineSpeed = engineSpeed;
	}
	


	public static void pause(int milli) {
		pcs.firePropertyChange("pause",listenerObjects.engineSpeed,milli);
		
	}

	public static void stopLoco(double engineSpeed) {
		pcs.firePropertyChange("loco1SetSpeedZero",listenerObjects.engineSpeed,engineSpeed);
		listenerObjects.engineSpeed = engineSpeed;
	}
	
	

}
