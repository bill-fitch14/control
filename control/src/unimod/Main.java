package unimod;

//import Model1EventProcessor;
//import ru.ifmo.blackjack.CardsControlledObject;
//import ru.ifmo.blackjack.MoneyControlledObject;
//import ru.ifmo.blackjack.ScreenControlledObject;
//import ru.ifmo.blackjack.SystemEventProvider;

import javax.swing.SwingUtilities;

import A_Inglenook.Inglenook;

import com.evelopers.common.exception.CommonException;
import com.evelopers.unimod.adapter.standalone.provider.Timer;
import com.evelopers.unimod.runtime.ControlledObject;
import com.evelopers.unimod.runtime.ControlledObjectsManager;
import com.evelopers.unimod.runtime.EventProvider;
import com.evelopers.unimod.runtime.EventProvidersManager;
import com.evelopers.unimod.runtime.ModelEngine;
import com.evelopers.unimod.runtime.QueuedHandler;
import org.apache.commons.logging.Log;


public class Main {
	
	

	public static void main(String[] args) {
		runFSM();
	}
	
	public static void runFSM(){
		
	

		
		EventProvidersManager EP = new EventProvidersManager() {
			//E3 p3 = new E3();
			E1 p1 = new E1();
			
			//Timer p2 = new Timer();
			

			public EventProvider getEventProvider(String epName) {
				if (epName.equals("p1")) return p1;
				//else if (epName.equals("p3")) return p3;
				else 
					throw new IllegalArgumentException("Can't find event provider " + epName);
			}

			public void dispose() {
				p1.dispose();
				//p3.dispose();
			}

			public void init(ModelEngine engine) throws CommonException {
				//p1.start();
				
				p1.init(engine);
				//p3.init(engine);
			}

			public E1 getP1() {
				return p1;
			}
		};
		
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
						C3 o3 = new C3();
//						CardsControlledObject o3 = new CardsControlledObject();
						
						public ControlledObject getControlledObject(String coName) {
							if (coName.equals("o1")) return o1;
							else if (coName.equals("o3")) return o3;
//							else if (coName.equals("o3")) return o3;
							else 
								throw new IllegalArgumentException("Can't find controlled object " + coName);
						}
						public void dispose() {}
						public void init(ModelEngine engine) throws CommonException {}
					},
					EP
					);
			me.start();
			
		} catch (CommonException e) {
			//2//3//99System.out.print(e);
		}
	}

}
