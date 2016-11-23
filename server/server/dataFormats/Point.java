package server.dataFormats;

import java.util.ArrayList;

/**
 *
 * @author Cirben
 */
public class Point {
	String displayName;
	Coords coords;
	int id;
	//ArrayList<transport> !?

	public Point(int id){
		this.id = id;
	}
	
	public Point(int id, String displayName, Coords coords){
		this.id = id;
		this.displayName = displayName;
		this.coords = coords;
	} 
	
	public Point(int id, String displayName, double x, double y){
		this.id = id;
		this.displayName = displayName;
		coords = new Coords(x,y);
	}

	public String getDisplayName() {
		return displayName;
	}

	public Coords getCoords() {
		return coords;
	}

	public int getId() {
		return id;
	}

}
