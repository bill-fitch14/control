package net;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * This is a very simple example showing the most basic use of
 * {@link net.Network} and {@link net.Network_iface}. Feel free to use,
 * overwrite, or just ignore code as you like.
 * 
 * As a default, a connection speed of 115200 baud is assumed. You can use a
 * different speed by giving it as an <b>int</b> as the first command line
 * argument or changing the default speed in the source code.
 * 
 * @author Raphael Blatter (raphael@blatter.sg)
 */
public class Serial_IO implements net.Network_iface , KeyListener{

	// set the speed of the serial port
	public static int speed = 115200;
	private static net.Network network;

	private static boolean resend_active = false;
	private  int connection_instance;
	private net_Console _console;
	
	public Serial_IO(String ModelorAdaption, net_Console console) {
		if (ModelorAdaption == "Model"){
			connection_instance = 0;
		}
		else{
			connection_instance = 1;
		}
		
		_console = console;
	}

	public void SIO_connect(int serialPort){
		
		//network = new net.Network(connection_instance, new net.Serial_IO(), 38);
		//58 is a : 
		//59 is a ;
		network = new net.Network(connection_instance, this, 59);

//		// initializing reader from command line
//		int i, inp_num = 0;
//		String input;
//		BufferedReader in_stream = new BufferedReader(new InputStreamReader(
//				System.in));

//		 getting a list of the available serial ports
		Vector<String> ports = network.getPortList();
		if (ports.size() > 0) {		
			println("the following serial ports have been detected:" + "\n");
		} else {
			println("sorry, no serial ports were found on your computer\n");
			//System.exit(0);
			return;
		}
		for (int i = 0; i < ports.size(); ++i) {
			println("    " + Integer.toString(i + 1) + ":  "
					+ ports.elementAt(i));
		}
		
		boolean validPort = false;
		int inp_num = getInputNo(ports,serialPort);
		println("just tried to get input no" + " got " + inp_num);
		if ((inp_num <= 0) || (inp_num >= ports.size() + 1))
			println("your input " + inp_num + " is not valid");
		else
			validPort = true;
		
		// connecting to the selected port4
		//99System.out.print("inp_num = " + inp_num + " ports.size = " + ports.size());
		if (network.connect(ports.elementAt(inp_num - 1), speed)) {
			println("Connected to serial port " + (inp_num-1) + " (" + ports.elementAt(inp_num-1) + ")");
		} else {
			println("sorry, there was an error connecting\n");
			System.exit(1);
		}
		
	}

	private int getInputNo(Vector<String> ports, int serialPort) {
		// serialPort is no of required serial port
		// ports has elements .size() and .elementsAt(inputno);
		int inputno =99;
		for (int i = 0; i < ports.size(); i++){
			println("Com: "+"COM" + Integer.toString(serialPort));
			println("Port: "+ports.elementAt(i).toString());
			println("i:  "+ (i+1));
			if (("COM" + Integer.toString(serialPort)).equals(ports.elementAt(i).toString())){
				
				inputno = i;
				//println("equal");
			}
			else{
				//println("not equal");
			}
				
		}
		if (inputno == 99){
			println("can't get an input no");
		}
		else{
			//println("inputno:"+(inputno+1));
		}
		return inputno+1;
	}
	
	public void writeSerial(String string){
		
		// the string needs to be surrounded by a : and a ;
		
		String message = ":" + string + ";";
		
		// send the string to the port
		network.writeSerial(message);
		println("writing: "  + string);
	}

//	public void execute() {
//		network = new net.Network(0, this, 255);
//
//		// reading the speed if
////		if (args.length > 0) {
////			try {
////				speed = Integer.parseInt(args[0]);
////			} catch (NumberFormatException e) {
////				//1//2//3//99System.out.print("the speed must be an integer\n");
////				System.exit(1);
////			}
////		}
//
//		// initializing reader from command line
//		int i, inp_num = 0;
//		String input;
//		BufferedReader in_stream = new BufferedReader(new InputStreamReader(
//				System.in));
//
//		// getting a list of the available serial ports
//		Vector<String> ports = network.getPortList();
//
//		// choosing the port to connect to
//		//1//2//3//99System.out.print();
//		if (ports.size() > 0) {
//			//3//99System.out.print
//					.println("the following serial ports have been detected:");
//		} else {
//			//3//99System.out.print
//					.println("sorry, no serial ports were found on your computer\n");
////			System.exit(0);
//			return;
//		}
//		for (i = 0; i < ports.size(); ++i) {
//			//2//3//99System.out.print("    " + Integer.toString(i + 1) + ":  "
//					+ ports.elementAt(i));
//		}
//		boolean valid_answer = false;
//		while (!valid_answer) {
//			//3//99System.out.print
//					.println("enter the id (1,2,...) of the connection to connect to: ");
//			try {
//				input = in_stream.readLine();
//				inp_num = Integer.parseInt(input);
//				if ((inp_num < 1) || (inp_num >= ports.size() + 1))
//					//2//3//99System.out.print("your input is not valid");
//				else
//					valid_answer = true;
//			} catch (NumberFormatException ex) {
//				//1//2//3//99System.out.print("please enter a correct number");
//			} catch (IOException e) {
//				//1//2//3//99System.out.print("there was an input error\n");
//				System.exit(1);
//			}
//		}
//
//		// connecting to the selected port
//		if (network.connect(ports.elementAt(inp_num - 1), speed)) {
//			//1//2//3//99System.out.print();
//		} else {
//			//1//2//3//99System.out.print("sorry, there was an error connecting\n");
//			System.exit(1);
//		}
//
//		// asking whether user wants to mirror traffic
//		//3//99System.out.print
//				.println("do you want this tool to send back all the received messages?");
//		valid_answer = false;
//		while (!valid_answer) {
//			//1//2//3//99System.out.print("'y' for yes or 'n' for no: ");
//			try {
//				input = in_stream.readLine();
//				if (input.equals("y")) {
//					resend_active = true;
//					valid_answer = true;
//				} else if (input.equals("n")) {
//					valid_answer = true;
//				} else if (input.equals("q")) {
//					//1//2//3//99System.out.print("example terminated\n");
//					System.exit(0);
//				}
//			} catch (IOException e) {
//				//1//2//3//99System.out.print("there was an input error\n");
//				System.exit(1);
//			}
//		}
//
//		// reading in numbers (bytes) to be sent over the serial port
//		//1//2//3//99System.out.print("type 'q' to end the example");
//		while (true) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e1) {
//			}
//			//3//99System.out.print
//					.println("\nenter a number between 0 and 254 to be sent ('q' to exit): ");
//			try {
//				input = in_stream.readLine();
//				if (input.equals("q")) {
//					//1//2//3//99System.out.print("example terminated\n");
//					network.disconnect();
//					System.exit(0);
//				}
//				inp_num = Integer.parseInt(input);
//				if ((inp_num > 255) || (inp_num < 0)) {
//					//1//2//3//99System.out.print("the number you entered is not valid");
//				} else {
//					int temp[] = { inp_num };
//					network.writeSerial(1, temp);
//					//1//2//3//99System.out.print("sent " + inp_num + " over the serial port");
//				}
//			} catch (NumberFormatException ex) {
//				//1//2//3//99System.out.print("please enter a correct number");
//			} catch (IOException e) {
//				//1//2//3//99System.out.print("there was an input error");
//			}
//		}
//	}

	/**
	 * Implementing {@link net.Network_iface#networkDisconnected(int)}, which is
	 * called when the connection has been closed. In this example, the program
	 * is ended.
	 * 
	 * @see net.Network_iface
	 */
	public void networkDisconnected(int id) {
		System.exit(0);
	}

	/**
	 * Implementing {@link net.Network_iface#parseInput(int, int, int[])} to
	 * handle messages received over the serial port. In this example, the
	 * received bytes are written to command line (0 to 254) and the message is
	 * sent back over the same serial port.
	 * 
	 * @see net.Network_iface
	 */
	public void parseInput(int id, int numBytes, int[] message) {
		if (resend_active) {
			network.writeSerial(numBytes, message);
			print("received and sent back the following message: ");
		} else {
			print("received the following message: ");
		}
		print(message[0]);
		for (int i = 1; i < numBytes; ++i) {
			print(", ");
			print(message[i]);
		}
		println();
		//process_message(message);
	}

	private void process_message(int[] message) {
		
		//check if the message needs to be processed
		
		//signal messages do
		
		//Bottom 4 bytes are the event
		//next 4 bytes are the node address
		//next 2 bytes are 90 off 91 on (status)
		
		int lastElement = message.length-1;
		String strMess = message.toString();
		String event = strMess.substring(lastElement-3, lastElement);
		String node = strMess.substring(lastElement-7, lastElement-4);
		String Status = strMess.substring(lastElement-8, lastElement-8); //0 for on //1 for off
		
		int keycode = Integer.getInteger(Status);
		int keyval =  Integer.getInteger(event);
		
		//2//3//99System.out.print ("event = "+event + " "+ keyval);
		//2//3//99System.out.print ("status = " + Status + ":" + keycode);
		
		// if node = 0001 then we have a signal
		int intNode = 1;
		if ( intNode == 1 ) {
			// Signal
			// generate a keypressed event with artificial keyval and keycode representing the signal state
			KeyEvent evt = new KeyEvent(_console, KeyEvent.KEY_PRESSED, 
					System.currentTimeMillis(), keycode, keyval);
		} 
		else if (intNode == 2) {
			// point
			// generate a keyreleased event
			KeyEvent evt = new KeyEvent(_console, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), keycode, keyval);
		}
		
	}

	private void generateKeyEvent(int keyval, int keycode) {
		// generate a keypress event on the console
		//The keycode will be the valu of ther 
		
	}

	/**
	 * Implementing {@link net.Network_iface#writeLog(int, String)}, which is
	 * used to write information concerning the connection. In this example, all
	 * the information is simply written out to command line.
	 * 
	 * @see net.Network_iface
	 */
	public void writeLog(int id, String text) {
		//1//2//3//99System.out.print("   log:  |" + text + "|");
	}
	
	public void println(){
		_console.getjEditorPane1().setText(_console.getjEditorPane1().getText() + "\n");
		_console.getjEditorPane1().setCaretPosition(_console.getjEditorPane1().getDocument().getLength());
	}
	public void println(String message){
		_console.getjEditorPane1().setText(_console.getjEditorPane1().getText() + message + "\n");
		_console.getjEditorPane1().setCaretPosition(_console.getjEditorPane1().getDocument().getLength());
	}
	
	public void print(int message){
		char mes = (char)message;
		String strmes = Character.toString(mes);
		_console.getjEditorPane1().setText(_console.getjEditorPane1().getText() + strmes);
	}
	
	public void print(String message){
		_console.getjEditorPane1().setText(_console.getjEditorPane1().getText() + message);
	}

	@Override
	public void keyPressed(KeyEvent keyevent) {
		// TODO Auto-generated method stub
		int event = keyevent.getKeyCode();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

