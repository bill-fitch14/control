package unimod;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

//import ru.ifmo.blackjack.SystemEventProvider;

import com.evelopers.unimod.core.stateworks.Event;
import com.evelopers.unimod.runtime.context.StateMachineContextImpl;
import javax.swing.JSpinner;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MMI extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JButton jButton1;
	private JButton jButton4;
	private JButton jButton3;
	private JButton jButton2;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MMI inst = new MMI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public MMI() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1);
					jButton1.setText("point 1 ON");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//2//3//99System.out.print("jButton1.actionPerformed, event="+evt);
							//TODO add your code for jButton1.actionPerformed
							E1.e_process_point_request(new String[]{"1","ON"});
							//sendEvent("E1");
						}
					});
				}
				{
					jButton2 = new JButton();
					jPanel1.add(jButton2);
					jButton2.setText("point 1 OFF");
					jButton2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//2//3//99System.out.print("jButton2.actionPerformed, event="+evt);
						E1.e_process_point_request(new String[]{"1","OFF"});	
						}
					});
				}
				{
					jButton3 = new JButton();
					jPanel1.add(jButton3);
					jButton3.setText("point 2 ON");
					jButton3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//2//3//99System.out.print("jButton3.actionPerformed, event="+evt);
							//E1.e_initialise();
							E1.e_process_point_request(new String[]{"2","ON"});
						}
					});
				}
				{
					jButton4 = new JButton();
					jPanel1.add(jButton4);
					jButton4.setText("point 2 OFF");
					jButton4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//2//3//99System.out.print("jButton4.actionPerformed, event="+evt);
							//TODO add your code for jButton4.actionPerformed
							E1.e_process_point_request(new String[]{"2","OFF"});
							//sendEvent("E_Stop");
						}
					});
				}
			}
			pack();
			setSize(289, 168);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	public void sendEvent(String event) {
		E1.getEngine().getEventManager().handle(
					new Event(event), 
					StateMachineContextImpl.create()
				);
	}

}
