package me.pt.spanish.map;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TileMap {
	
	String[][] smap;
	Tile[][] map;
	public Point[] factPoints = { new Point(1,14), new Point(14,18), new Point(16,8), new Point(16,17), new Point(18,1), new Point(10,1), new Point(8,3), new Point(5,11), new Point(5,5), new Point(1,1) };
	
	private int length;
	
	private Canvas c;

	public TileMap( String filename, int length, Canvas c, int facts ) {
		
		this.length = length;
		this.c = c;
		
		smap = new String[length][length];
		
		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if (!(line.startsWith("#") || line.equals(""))){
			    	for ( int j = 0; j < line.split( " " ).length; j++ ) {
			    		smap[i][j] = line.split( " " )[j];
			    	}
			    	i++;
		    	}
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map = new Tile[length][length];
		
		for ( i = 0; i < length; i++ ) {
			for ( int j = 0; j < length; j++ ) {
				map[i][j] = new Tile(smap[i][j], 25, new Point(j*25, i*25), c);
			}
		}
	}
	
	public void paintAll( Graphics g ) {
		
		for ( int i = 0; i < length; i++ ) {
			for ( int j = 0; j < length; j++ ) {
				map[i][j].paint(g);
			}
		}
	}
	
	public boolean checkCollisions(int x, int y, String checkFor) {
		
		if ( map[y][x].getTileType().equals(checkFor) )
			return true;
		return false;
	}
	
	public void setPoint(String s, int x, int y) {
		
		map[y][x] = new Tile(s, 25, new Point(x*25, y*25), c);
	}
	
	public Tile[][] getMap() {
		return map;
	}
	
	public boolean isTileFact(int x, int y){
		for ( Point p : factPoints ) {
			if ( (int)p.getX() == x && (int)p.getY() == y )
				return true;
		}
		return false;
	}
	
	public void removeFact(int x, int y) {
		
		Point[] points = new Point[factPoints.length-1];
		int u = 0;
		for ( int i = 0; i < factPoints.length; i++ ) {
			if ( !((int)factPoints[i].getX() == x && (int)factPoints[i].getY() == y) ) {
				points[u] = factPoints[i];
			} else {
				u--;
			}
			u++;
		}
		
		factPoints = new Point[factPoints.length-1];
		factPoints = points;
	}
}
