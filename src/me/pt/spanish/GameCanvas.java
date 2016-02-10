package me.pt.spanish;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import me.pt.spanish.map.TileMap;

public class GameCanvas extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = -8079661724344991019L;
	private int frames = 0;
	
	public int width, height;
	
	private Image i;
	private Graphics doubleG;
	
	private TileMap tm;
	
	private int x = 10;
	private int y = 19;
	private boolean canMove = true;
	
	private boolean firstPaint = true;
	
	private boolean displayFact = false;
	private String[] factTitles =	{	"Fact #1",				"Fact #2",				"Fact #3",				"Fact #4",				"Fact #5",				"Fact #6",				"Fact #7",				"Fact #8",				"Fact #9",				"Fact #10"				};
	private String[] factContent =	{	"FACT.WINDOW.CONTENT", 	"FACT.WINDOW.CONTENT", 	"FACT.WINDOW.CONTENT",  "FACT.WINDOW.CONTENT",  "FACT.WINDOW.CONTENT", 	"FACT.WINDOW.CONTENT",  "FACT.WINDOW.CONTENT", 	"FACT.WINDOW.CONTENT",  "FACT.WINDOW.CONTENT", 	"FACT.WINDOW.CONTENT"	};
	private int factIndex = 0;
	private int factCounter = 0;
	
	boolean gameFinished = false;
	boolean exitNow = false;
	
	public GameCanvas( int height, int width ) {
		
		this.height = height;
		this.width = width;
		
		tm = new TileMap("res/rc.map", 20, this, 10);
		
		setBackground(Color.darkGray);
		
		addKeyListener(this);
	}
	
	public void start() {
		
		Thread thread = new Thread( this );
		thread.start();
	}
	
	public void update( Graphics g ) {
		
		if ( i == null ){
			i = createImage( getWidth(), getHeight() );
			doubleG = i.getGraphics();
		}
		
		doubleG.setColor( getBackground() );
		doubleG.fillRect( 0, 0, getWidth(), getHeight() );
		
		doubleG.setColor( getForeground() );
		paint( doubleG );
		
		g.drawImage( i, 0, 0, this );
	}
	
	@Override
	public void paint( Graphics g ) {
		
		g.setColor( Color.blue );
		tm.paintAll( g );
		
		if ( firstPaint ) {
			g.setColor(Color.getHSBColor(139, 100, 100));
			g.fillRect(50, 50, 400, 400);
			g.setColor(Color.black);
			g.setFont(new Font("Times New Roman", Font.BOLD, 35));
			g.drawString("¡Hola!", 200, 100);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.drawString("Use the arrow keys to navigate through Roberto", 65, 150);
			g.drawString("Clemente's Museum to learn more about him.  ", 65, 175);
			g.drawString("Your goal is to find 10 facts that you didn't know", 65, 200);
			g.drawString("about him. Good Luck!", 65, 225);
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
			g.drawString("Press enter to exit...", 185, 420);
		}
		
		if ( gameFinished ) {
			g.setColor(Color.getHSBColor(139, 100, 100));
			g.fillRect(50, 50, 400, 400);
			g.setColor(Color.black);
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.drawString("¡FELICITACIONES!", 62, 100);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 28));
			g.drawString("You learned all 10 facts about", 75, 250);
			g.drawString("Roberto Clemente!", 150, 285);
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
			g.drawString("Press enter to exit...", 185, 420);
			exitNow = true;
		}
		
		if ( displayFact ) {
			g.setColor( Color.getHSBColor(40, 5, 100) );
			g.fillRect(50, 50, 400, 400);
			g.setColor( Color.black );
			g.setFont(new Font("Times New Roman", Font.BOLD, 30));
			g.drawString(factTitles[factIndex], 65, 80);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.drawString(factContent[factIndex], 65, 125);
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
			g.drawString("Press enter to continue...", 150, 420);
		}
		
		g.setFont(new Font("Times New Roman", Font.BOLD, 25));
		String fpsString = "Score: "+factCounter+"/10 Facts Found";
		g.setColor( Color.white );
		g.drawString( fpsString, 0, 525 );
	}

	@Override
	public void run() {
		
		TimerTask updateFPS = new TimerTask() {
			
			public void run() {
				
				System.out.println("FPS -> "+frames);
				frames = 0;
			}
		};
		Timer t = new Timer();
		t.scheduleAtFixedRate(updateFPS, 1000, 1000);
		
		repaint();
		
		tm.setPoint("P", x, y);
		
		while ( true ) {
			++frames;
			
			repaint();
			
			if ( tm.isTileFact(x, y) ) {
				canMove = false;
				displayFact = true;
			}
			
			try {Thread.sleep(17);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch ( e.getKeyCode() ) {
		
		case KeyEvent.VK_LEFT:
			tm.setPoint("F", x, y);
			if ( x > 0 && !(tm.checkCollisions(x-1, y, "W")) && canMove )
				--x;
			tm.setPoint("P", x, y);
			break;
		case KeyEvent.VK_RIGHT:
			tm.setPoint("F", x, y);
			if ( x < 19 && !(tm.checkCollisions(x+1, y, "W")) && canMove )
				++x;
			tm.setPoint("P", x, y);
			break;
		case KeyEvent.VK_UP:
			tm.setPoint("F", x, y);
			if ( y > 0 && !(tm.checkCollisions(x, y-1, "W")) && canMove )
				--y;
			tm.setPoint("P", x, y);
			break;
		case KeyEvent.VK_DOWN:
			tm.setPoint("F", x, y);
			if ( y < 19 && !(tm.checkCollisions(x, y+1, "W")) && canMove )
				++y;
			tm.setPoint("P", x, y);
			break;
		case KeyEvent.VK_ENTER:
			if ( firstPaint ) {
				firstPaint = false;
			}
			if ( exitNow ) {
				System.exit(0);
			}
			if ( displayFact ) {
				displayFact = false;
				if (factIndex > 8){
					tm.removeFact(x, y);
					gameFinished = true;
					displayFact = false;
				}
				if ( !gameFinished ) {
					factIndex++;
					tm.removeFact(x, y);
					canMove = true;
					factCounter++;
				}
			}
			break;
		case KeyEvent.VK_ESCAPE:
			
			break;
		}			
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
