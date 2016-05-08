package unimod;
import A_Inglenook.M_TruckMovements;

import com.evelopers.unimod.runtime.ControlledObject;
import com.evelopers.unimod.runtime.context.StateMachineContext;


public class C2 implements ControlledObject {
	/**
	 * @unimod.action.descr process stack
	 */
	public void z_process_stack(StateMachineContext context) {
		
		//99System.out.print("processing stack");
		
		String[] st = P1.getStackInstruction(context);
		
		M_TruckMovements.readDeque1(st);
		
		
	}

}
