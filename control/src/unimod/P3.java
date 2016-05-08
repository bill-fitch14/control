package unimod;

import com.evelopers.unimod.runtime.context.Parameter;
import com.evelopers.unimod.runtime.context.StateMachineContext;

public class P3 {

	
	private static final String LIST_ELEMENT = "LIST_ELEMENT";

	public static Parameter createListElement(StateMachineContext context,
			String[] word2) {
		//2//3//99System.out.print("createStopRequest " + word2);
		return new Parameter(LIST_ELEMENT, word2);
	}
}
