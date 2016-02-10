package me.pt.spanish;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game {

	public static void main( String[] args ) {
		
		Frame frame = new Frame();
		frame.setSize( 500, 525 );
		frame.setLocationRelativeTo(null);
		frame.setTitle("Touring Roberto Clemento's Life - by Peter Toth");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	        	System.exit(0);
	        }
	     }
	);
		
		GameCanvas gc = new GameCanvas( 500, 500 );
		frame.add(gc);
		gc.start();
		frame.validate();
	}
}
