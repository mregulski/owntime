package com.zespolowe.server.interfaces;

import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.Point;
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
