package mytrack;

import java.awt.Point;

import javax.swing.SwingUtilities;

//import com.evelopers.unimod.runtime.context.StateMachineContext;

import net.Serial_IO;
import net.net_Console;
//import sm2.E1;
import sm2.MMI;

public final class A_Threads {

	
	private boolean started;
	
	private net_Console _consoleModel;
	private Serial_IO _serialModel;
	
	private net_Console _eventConsole;
	
	private boolean started2=false;
	private net_Console _consoleModel2;
	private Serial_IO _serialModel2;
	
	private A_Setup modelSetup;
	
	private A_Adaption modelAdaption;
	
	private MMI inst;

	public A_Threads(){
		
		
	setup();
	
	eventConsole();

	modelConsole();

	modelConsole2();
	
	inglenook();
	
	mmi();



	

}

	private void mmi(){
		SwingUtilities.invokeLater(new Runnable() {
			

			public void run() {
				inst = new MMI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}


	/**
	 * 
	 */
	private void inglenook() {
		Thread thread2 = new Thread() {

			public void run() {
				//99System.out.print("running Inglenook");
				//while (!thread.isInterrupted()) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						return;
					}



					// StateMachineContext context = createContext();
					//e_process_queue();
				//}
			}

		};
		thread2.start();
	}



	/**
	 * 
	 */
	private void modelConsole() {
		if (!started) {
			SwingUtilities.invokeLater(new Runnable() {


				public void run() {
					_consoleModel = new net_Console("Model Console");
					Point p2 = B0_Toplevel_Gui.SetFrameLocation(5, 5);
					_consoleModel.setLocation(p2);
					_consoleModel.setVisible(true);

					_serialModel = new Serial_IO("Model", _consoleModel);
					_serialModel.SIO_connect(1);
				}
			});
		}
	}
	
	private void modelConsole2() {
		if (!started2) {
			SwingUtilities.invokeLater(new Runnable() {




				public void run() {
					_consoleModel2 = new net_Console("Model Console2");
					Point p2 = B0_Toplevel_Gui.SetFrameLocation(10, 5);
					_consoleModel2.setLocation(p2);
					_consoleModel2.setVisible(true);

					_serialModel2 = new Serial_IO("Model2", _consoleModel2);
					_serialModel2.SIO_connect(2);
				}
			});
		}
	}



	/**
	 * 
	 */
	private void eventConsole() {
		if (!started) {
			SwingUtilities.invokeLater(new Runnable() {
				

				public void run() {
					MMI inst = new MMI();

					inst.setLocationRelativeTo(null);
					inst.setVisible(true);

					_eventConsole = new net_Console("Events");
					Point p2 = B0_Toplevel_Gui.SetFrameLocation(25, 5);
					_eventConsole.setLocation(p2);
					_eventConsole.setVisible(true);
				}
			});
		}
	}



	/**
	 * 
	 */
	private void setup() {
		Thread thread0 = new Thread() {

			//private Serial_IO _serialModel;

			

			public void run() {
				//99System.out.print("running init");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				modelSetup = new A_Setup(_serialModel, "Model");
			

			}
		};
		thread0.start();
	}

	/**
	 * 
	 */
	private void adaption() {
		Thread thread0 = new Thread() {

			//private Serial_IO _serialModel;

			

			public void run() {
				//99System.out.print("running init");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				modelAdaption = new A_Adaption(_serialModel, "Model");
				

			}
		};
		thread0.start();
	}

	public net_Console get_consoleModel() {
		return _consoleModel;
	}



	public Serial_IO get_serialModel() {
		return _serialModel;
	}



	public net_Console get_eventConsole() {
		return _eventConsole;
	}

	public MMI getInst() {
		return inst;
	}

	public A_Setup getModelSetup() {
		return modelSetup;
	}
}
