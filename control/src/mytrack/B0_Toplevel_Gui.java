package mytrack;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class B0_Toplevel_Gui {
	
	public static Point SetFrameLocation(int xPercent,int yPercent){
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Point p = new Point(dim.width*xPercent/100,dim.height*yPercent/100);
		return p;
	}

}
