package server.interfaces;

import server.dataFormats.Connection;
import server.dataFormats.Point;
import java.util.ArrayList;

/**
 *
 * @author Cirben
 * provides not ordered in any way lists
 */
public interface DataProvider {
	public ArrayList<Point> getStops();
	public ArrayList<Connection> getConnections();
 	public ArrayList<Connection> getUpdatedConnections();
	
}
