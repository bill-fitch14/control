package myscene;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class listenerObjects {

	private static PropertyChangeSupport pcs = new PropertyChangeSupport(listenerObjects.class);

	// Property property1
	private static int point1;

	// Property property2
	private static int point2;
	
	//Property engine1
	private static int engine1;
	
	private static int sensortime;
	
	public static void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	
	public static void setSensor( int millis) {
		pcs.firePropertyChange("property1", listenerObjects.sensortime, millis);
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
		pcs.firePropertyChange("loco1SetSpeed",listenerObjects.engine1,engineSpeed);
	}
	
	public static void setEngineDirection(String direction){
		if (direction.equals("forwards")){
			pcs.firePropertyChange("locoSetDirection",listenerObjects.engine1,1);
		}else{
			pcs.firePropertyChange("locoSetDirection",listenerObjects.engine1,0);
		}
			
	}
	
	public static void moveLoco(String direction,double engineSpeed){
		if (direction.equals("forwards")){
			System.out.println("forwards loco1F: enginespeed" + engineSpeed);
			pcs.firePropertyChange("loco1F",listenerObjects.engine1,engineSpeed);
		}else{
			System.out.println("forwards loco1R: enginespeed" + engineSpeed);
			pcs.firePropertyChange("loco1R",listenerObjects.engine1,engineSpeed);
		}
	}
	


	public static void pause(int milli) {
		pcs.firePropertyChange("pause",listenerObjects.engine1,milli);
		
	}

	public static void stopLoco(double engineSpeed) {
		pcs.firePropertyChange("loco1SetSpeedZero",listenerObjects.engine1,engineSpeed);
		
	}
	
	

}
