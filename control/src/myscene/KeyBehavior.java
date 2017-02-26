package myscene;
// KeyBehavior.java
// Andrew Davison, September 2006, ad@fivedots.coe.psu.ac.th

/* Use key presses to move the viewpoint.
   Movement is restricted to: forward, backwards, move left,
     move right, rotate left, rotate right, up, down.
   The down movement can not go below the starting height.

   The behaviour is added to the user's viewpoint. 
   It extends ViewPlatformBehavior so that targetTG is available
   to it, the ViewPlatform's tranform.
*/

import java.awt.AWTEvent;
import java.awt.event.*;
import java.util.Enumeration;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;

import mytrack.U4_Constants;
import sm2.Main;

import com.sun.j3d.utils.behaviors.vp.*;



public class KeyBehavior extends ViewPlatformBehavior
{
  private static final double ROT_AMT = Math.PI / 36.0;   // 5 degrees
  private static final double MOVE_STEP = 0.2;
  private static final double SPD_AMT = 0.1;

  // hardwired movement vectors
  private static final Vector3d UP = new Vector3d(0,0,-MOVE_STEP);
  private static final Vector3d DOWN = new Vector3d(0,0,MOVE_STEP);
  private static final Vector3d LEFT = new Vector3d(-MOVE_STEP,0,0);
  private static final Vector3d RIGHT = new Vector3d(MOVE_STEP,0,0);
  private static final Vector3d FWD = new Vector3d(0,MOVE_STEP,0);
  private static final Vector3d BACK = new Vector3d(0,-MOVE_STEP,0);

  // key names
  private int forwardKey = KeyEvent.VK_UP;
  private int backKey = KeyEvent.VK_DOWN;
  private int leftKey = KeyEvent.VK_LEFT;
  private int rightKey = KeyEvent.VK_RIGHT;
  private int zeroKey = KeyEvent.VK_0;
  private int oneKey = KeyEvent.VK_1;
  private int twoKey = KeyEvent.VK_2;
  private int threeKey = KeyEvent.VK_3;
  private int fourKey = KeyEvent.VK_4;
  private int fiveKey = KeyEvent.VK_5;
  private int sixKey = KeyEvent.VK_6;
  private int sevenKey = KeyEvent.VK_7;
  private int eightKey = KeyEvent.VK_8;
  
  private int f1 = KeyEvent.VK_F1;
  private int f2 = KeyEvent.VK_F2;
  private int f3 = KeyEvent.VK_F3;
  private int f4 = KeyEvent.VK_F4;
  

  private WakeupCondition keyPress;

  // for repeated calcs
  private Transform3D t3d = new Transform3D();
  private Transform3D toMove = new Transform3D();
  private Transform3D toRot = new Transform3D();


  private int upMoves = 0;


  public KeyBehavior()
  { keyPress = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);  } 


  public void initialize()
  {  wakeupOn( keyPress );  }


  public void processStimulus(Enumeration criteria)
  // respond to a keypress
  {
    WakeupCriterion wakeup;
    AWTEvent[] event;

    while( criteria.hasMoreElements() ) {
      wakeup = (WakeupCriterion) criteria.nextElement();
      if( wakeup instanceof WakeupOnAWTEvent ) {
        event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
        for( int i = 0; i < event.length; i++ ) {
          if( event[i].getID() == KeyEvent.KEY_PRESSED )
            processKeyEvent((KeyEvent)event[i]);
        }
      }
    }
    wakeupOn( keyPress );
  } // end of processStimulus()


  
  private void processKeyEvent(KeyEvent eventKey)
  {
    int keyCode = eventKey.getKeyCode();
    // //1//2//3//99System.out.print(keyCode);    

    if(eventKey.isAltDown())   // key + <alt>
    	altMove(keyCode);  //position graph
    else if(eventKey.isControlDown())   // key + <altgraph>
    	controlfunctionMove(keyCode); 
    else{
    	standardMove(keyCode);
    	functionMove(keyCode);
    }
//    if(eventKey.isAltGraphDown())   // key + <alt>
//    	altfunctionMove(keyCode);
//    else 
//    	functionMove(keyCode);
  } // end of processKeyEvent()


  private void controlfunctionMove(int keyCode) {
	  A_Inglenook.trainPosition.altpositionTrain(keyCode);
	
}
  private void functionMove(int keycode)
  // the function moves are for moving the train and are dealt with in trainPosition
  { 
      A_Inglenook.trainPosition.positionTrain(keycode);


  }  // end of altMove()

private void standardMove(int keycode)
  /* Make viewer moves forward or backward; 
     rotate left or right */
  { if(keycode == forwardKey)
      doMove(FWD);
    else if(keycode == backKey)
      doMove(BACK);
    else if(keycode == leftKey)
      rotateY(ROT_AMT);
    else if(keycode == rightKey)
      rotateY(-ROT_AMT);
    else if(keycode == zeroKey)
        engineSpeed(0);
    else if(keycode == oneKey)
    	engineSpeed(0.1f);
  
  A_Inglenook.trainPosition.StandardMove(keycode);

  } // end of standardMove()


  private void altMove(int keycode)
  // moves viewer up or down, left or right
  { if(keycode == forwardKey) {
      upMoves++;
      doMove(UP);
    }
    else if(keycode == backKey) {
      //if (upMoves > 0) {  // don't drop below start height
        upMoves--;
        doMove(DOWN);
      //}
    }
    else if(keycode == leftKey)
      doMove(LEFT);
    else if(keycode == rightKey)
      doMove(RIGHT);
  }  // end of altMove()


  private void rotateY(double radians)
  // rotate about y-axis by radians
  { targetTG.getTransform(t3d);   // targetTG is the ViewPlatform's transform
    toRot.rotX(radians);
    t3d.mul(toRot);
    targetTG.setTransform(t3d);
  } // end of rotateY()


  private void doMove(Vector3d theMove)
  { targetTG.getTransform(t3d);
    toMove.setTranslation(theMove);
    t3d.mul(toMove);
    targetTG.setTransform(t3d);
  } // end of doMove()

  private float engineSpeedLocal = U4_Constants.engineSpeedSetting;
  private void engineSpeed(float theSpeed)
  { 
	  Main.lo.setEngineSpeed(theSpeed);
	  U4_Constants.engineSpeedSetting = theSpeed;
  } // end of doMove()

}  // end of KeyBehavior class
