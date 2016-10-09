package sm2;


import java.util.Deque;
import java.util.LinkedList;

import javax.swing.JEditorPane;

import com.evelopers.unimod.runtime.ControlledObject;
import com.evelopers.unimod.runtime.context.StateMachineContext;

import A_Inglenook.MoveTrainUsingDeque;
//import A_Inglenook.MoveTrainUsingDeque;

public class C1 implements ControlledObject {

	/**
	 * @unimod.action.descr process stack
	 */
	public void z_process_stack(StateMachineContext context) {
		
		//99System.out.print("processing stack");
		
		String[] st = P1.getStackInstruction(context);
		
		MoveTrainUsingDeque.readDeque1(st);   
		
	}
	
	/**
	 * @unimod.action.descr process stack
	 */
	public void z_start_stack(StateMachineContext context) {
		
		//99System.out.print("starting stack");
		E1.threads.get_serialModel().print("starting stack");  
		E1.threads.get_serialModel().println();
	}
	
//	/**
//	 * @unimod.action.descr process stack
//	 */
//	public void z_process_points_stack(StateMachineContext context) {
//		
//		//99System.out.print("processing points stack");
//		
//		String[] st = P1.getPointsStackInstruction(context);
//		
//		M_TruckMovements.readDeque1(st);   
//		
//	}
//	
	private static Deque<String[]> prq = new LinkedList<String[]>();
	
	/**
	 * @unimod.action.descr add Request To Queue
	 */
	public void z_addRequestToQueue(StateMachineContext context) {

		String[] currentRequest = P1.getPointRequest(context);

		getPrq().add(currentRequest);

		//99System.out.print("size of prq is " + getPrq().size());

		//99System.out.print("added request: " );

	}
	
	/**
	 * @unimod.action.descr print latest point request
	 */
	public void z_processRequestQueue(StateMachineContext context) {

		String[] pointrequest = getPrq().poll();
		String message = null;
		String serialmessage = null;
		if (pointrequest != null) {
			System.out.print("SWITCHING: " + pointrequest[0] + " " +
			pointrequest[1]);
			println("SWITCHING: " + pointrequest[0] + " " + pointrequest[1]);

			for (String item : pointrequest) {
				if (message == null) {
					message = item;
					serialmessage = item;
				} else {
					message = message + " " + item;
					if (!(item.equals("ON") || item.equals("OFF"))) {
						serialmessage = serialmessage + item;
					}
				}
			}
			println("SWITCHING: " + message);
			System.out.println(serialmessage);
			E1.get_serialModel().writeSerial(serialmessage);
			//sm2.Main.setpoint(message);
			
		}

		//99System.out.print("Remaining Queue is:");
		//99System.out.print("prq is " + getPrq().toString());
		//99System.out.print("size of prq is " + getPrq().size());

	}
	
	/**
	 * @unimod.action.descr print latest point request
	 */
	public void z_printCurrentRequest(StateMachineContext context) {

		String[] currentRequest = P1.getPointRequest(context);
		//99System.out.print(">> Latest Point Request:" + currentRequest[0]
		//99 + "_" + currentRequest[1]);
		//currentRequest = P1.getPointRequest(context);
		// 2//3//99System.out.print(">> Latest Point Request:" + currentRequest[0]
		// + "_" + currentRequest[1]);
	}
	
	public void println(String message) {
		// E1 e1 = new E1();

		JEditorPane display = E1.get_eventConsole().getjEditorPane1();
		display.setText(display.getText() + message + "\n");

		// display.setCaretPosition(display.getText().length());
		// main.setCaretPosition(main.getText().length());
	}

	/**
	 * @return the prq
	 */
	public static Deque<String[]> getPrq() {
		return prq;
	}

	/**
	 * @param prq the prq to set
	 */
	public void setPrq(Deque<String[]> prq) {
		this.prq = prq;
	}
	
	public static String[] readPointsDeque(){
		return getPrq().pollFirst();
	}
}
