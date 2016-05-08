package myscene;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class listenerObjects {

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	// Property property1
	private int point1;

	// Property property2
	private int point2;

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
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

}
