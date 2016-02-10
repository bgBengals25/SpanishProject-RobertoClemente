package me.pt.spanish.map;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;

public class Tile {
	
	private String tileType;
	private int length;
	private Point pixLocation;

	public String getTileType() {
		return tileType;
	}

	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Point getPixLocation() {
		return pixLocation;
	}

	public void setPixLocation(Point pixLocation) {
		this.pixLocation = pixLocation;
	}
	
	private Canvas c;

	public Tile( String tileType, int length, Point pixLocation, Canvas c ) {
		
		this.tileType = tileType;
		this.length = length;
		this.pixLocation = pixLocation;
		this.c = c;
	}
	
	public void paint( Graphics g ) {
		
		if ( tileType.equals("O") ) {
			g.setColor( Color.blue );
			g.drawOval( pixLocation.x, pixLocation.y, length, length );
		}
		else if ( tileType.equals("C") ) {
			g.setColor( Color.red );
			g.fillOval( pixLocation.x, pixLocation.y, length, length);
		}
		else if ( tileType.equals("S") ) {
			g.setColor( Color.green );
			g.drawRect( pixLocation.x, pixLocation.y, length, length );
		}
		else if ( tileType.equals("X") ) {
			g.setColor( Color.black );
			g.fillRect( pixLocation.x, pixLocation.y, length, length );
		}
		else if ( tileType.equals("P") ) {
			g.drawImage(new ImageIcon("res/smile.png").getImage(), pixLocation.x, pixLocation.y, c);
		}
		else if ( tileType.equals("W") ) {
			g.drawImage(new ImageIcon("res/wall.png").getImage(), pixLocation.x, pixLocation.y, c);
		}
		else if ( tileType.equals("F") ) {
			g.drawImage(new ImageIcon("res/floor.png").getImage(), pixLocation.x, pixLocation.y, c);
		}
		else if ( tileType.equals("T")) {
			g.drawImage(new ImageIcon("res/fact.png").getImage(), pixLocation.x, pixLocation.y, c);
		}
		else {
			g.clearRect( pixLocation.x+1, pixLocation.y+1, length-1, length-1);
		}
	}
}
