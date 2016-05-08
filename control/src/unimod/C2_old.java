package unimod;

import javax.swing.JEditorPane;

import com.evelopers.unimod.runtime.ControlledObject;
import com.evelopers.unimod.runtime.context.StateMachineContext;

public class C2_old implements ControlledObject {
	
   
    
    public void println(String message){
//		//E1 e1 = new E1();
//		
		JEditorPane display = E1.get_eventConsole().getjEditorPane1();
		display.setText(display.getText() + message + "\n");
		
		//display.setCaretPosition(display.getText().length());
		//main.setCaretPosition(main.getText().length());
    	//E1.println()
	}

}
