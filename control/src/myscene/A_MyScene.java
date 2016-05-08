package myscene;

import javax.swing.*;

import net.Serial_IO;

import mytrack.C1_BranchGroup;

//import ObjView3D.WrapObjView3D;

//import ObjView3D.ObjView3D;

import java.awt.*;

public class A_MyScene extends JFrame
{
	private static final long serialVersionUID = 1L;
//private JTree myJTree;

	
private	B_Mypanel panel;     // panel holding the 3D canvas
	
	public A_MyScene(C1_BranchGroup bG, Serial_IO serialIO, String title) {
		this.setTitle(title);
	    Container c = getContentPane();
	    c.setLayout( new BorderLayout() );
	    setPanel(new B_Mypanel(bG,serialIO));     // panel holding the 3D canvas
	    c.add(getPanel(), BorderLayout.CENTER);
	    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    pack();
	    setResizable(false);    // fixed size display
	    setVisible(true);
	}

	public void setPanel(B_Mypanel panel) {
		this.panel = panel;
	}

	public B_Mypanel getPanel() {
		return panel;
	}

//	public void setTrackBG() {
//		try {
//			panel.getTrack().setBG();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

//	public void setJTree(JTree myJTree) {
//		panel.getTrack().setJTree(myJTree);
//		
//		
//	}

}
