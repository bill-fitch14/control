package myscene;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class listenerObjects {

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	// Property property1
	private int point1;

	// Property property2
	private int point2;
	
	//Property engine1
	private int engine1;

	public int getPoint1() {
		return point1;
	}

	public void setPoint1(int property1) {
		pcs.firePropertyChange("property1", this.point1, property1);
		this.point1 = property1;
	}

	public int getPoint2() {
		return point2;
	}

	public void setPoint2(int property2) {
		pcs.firePropertyChange("property2", this.point2, property2);
		this.point2 = property2;
	}
//	
//	public void setEngineSpeed(double engineSpeed){
//		pcs.firePropertyChange("loco1R",this.engine1,engineSpeed);
//	}
//	
//	public void setEngineSpeed(double engineSpeed){
//		pcs.firePropertyChange("loco1F",this.engine1,engineSpeed);
//	}
	
	public void setEngineSpeed(double engineSpeed){
		pcs.firePropertyChange("loco1SetSpeed",this.engine1,engineSpeed);
	}
	
	public void setEngineDirection(String direction){
		if (direction.equals("forwards")){
			pcs.firePropertyChange("loco1SetDirection",this.engine1,1);
		}else{
			pcs.firePropertyChange("loco1SetDirection",this.engine1,0);
		}
			
	}
	
	public void moveLoco(String direction,double engineSpeed){
		if (direction.equals("forwards")){
			pcs.firePropertyChange("loco1F",this.engine1,engineSpeed);
		}else{
			pcs.firePropertyChange("loco1R",this.engine1,engineSpeed);
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void pause(int milli) {
		pcs.firePropertyChange("pause",this.engine1,milli);
		
	}

	public void stopLoco(double engineSpeed) {
		pcs.firePropertyChange("loco1SetSpeed",this.engine1,engineSpeed);
		
	}
	
	

}
