package sm2;


//import Model1EventProcessor;
//import ru.ifmo.blackjack.CardsControlledObject;
//import ru.ifmo.blackjack.MoneyControlledObject;
//import ru.ifmo.blackjack.ScreenControlledObject;
//import ru.ifmo.blackjack.SystemEventProvider;

import javax.swing.SwingUtilities;

import A_Inglenook.Inglenook;
import myscene.listenerObjects;

import com.evelopers.common.exception.CommonException;
import com.evelopers.unimod.adapter.standalone.provider.Timer;
import com.evelopers.unimod.runtime.ControlledObject;
import com.evelopers.unimod.runtime.ControlledObjectsManager;
import com.evelopers.unimod.runtime.EventProvider;
import com.evelopers.unimod.runtime.EventProvidersManager;
import com.evelopers.unimod.runtime.ModelEngine;
import com.evelopers.unimod.runtime.QueuedHandler;
import org.apache.commons.logging.Log;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;



public class Main {



	public static void main(String[] args) {
		
		runFSM();
	}

	
	
	


	public static void runFSM(){


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MMI inst = new MMI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});

		try {
			ModelEngine me = ModelEngine.createStandAlone(
					new QueuedHandler(),
					new Model1EventProcessor(),
					
					new ControlledObjectsManager() {
						C1 o1 = new C1();
						//						C3 o3 = new C3();
						//						CardsControlledObject o3 = new CardsControlledObject();

						public ControlledObject getControlledObject(String coName) {
							if (coName.equals("o1")) return o1;
							//							else if (coName.equals("o3")) return o3;
							//							else if (coName.equals("o3")) return o3;
							else 
								throw new IllegalArgumentException("Can't find controlled object " + coName);
						}
						public void dispose() {}
						public void init(ModelEngine engine) throws CommonException {}
					},
					
					new EventProvidersManager() {
						//E3 p3 = new E3();
						E1 e1 = new E1();

						//Timer p2 = new Timer();


						public EventProvider getEventProvider(String epName) {
							//99System.out.print("GETEVENTPROVIDER");
							if (epName.equals("p1")) return e1;
							//else if (epName.equals("p3")) return p3;
							else 
								throw new IllegalArgumentException("Can't find event provider " + epName);
						}

						public void dispose() {
							e1.dispose();
							//p3.dispose();
						}

						public void init(ModelEngine engine) throws CommonException {

							//99System.out.print("main.init");
							e1.init(engine);
							//p3.init(engine);
						}

						public E1 getE1() {
							return e1;
						}
					});
			me.start();

		} catch (CommonException e) {
			//99System.out.print(e);
		}
	}






	public static listenerObjects lo = new listenerObjects();



}

