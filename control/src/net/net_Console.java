package net;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.WindowConstants;
import org.jdesktop.application.Application;
import javax.swing.SwingUtilities;


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
public class net_Console extends javax.swing.JFrame {
	private String Title;
	private JScrollPane jScrollPane2;
	private JEditorPane jEditorPane1;
	private int[] _message;
	
	net_Console _console;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				net_Console inst = new net_Console();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public net_Console() {
		super();
		initGUI();
	}
	
	public net_Console(String title) {
		super();
		initGUI();
		Title = title;
		this.setTitle(title);
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jScrollPane2 = new JScrollPane();
				getContentPane().add(jScrollPane2, BorderLayout.CENTER);
				jScrollPane2.setName("jScrollPane2");
				{
					jEditorPane1 = new JEditorPane();
					jScrollPane2.setViewportView(jEditorPane1);
					jEditorPane1.setName("jEditorPane1");
				}
			}
			pack();
			this.setSize(522, 251);
			//Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return Title;
	}

	public JEditorPane getjEditorPane1() {
		return jEditorPane1;
	}

	public void set_console(net_Console _console) {
		this._console = _console;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return this.getText();
	}





}
