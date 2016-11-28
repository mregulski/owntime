package server.interfaces;

import server.dataFormats.Connection;
import server.dataFormats.Point;
import java.util.ArrayList;

/**
 *
 * @author Cirben
 */
public interface DataProvider {

    public ArrayList<Point> getStops();

    public ArrayList<Connection> getConnections();

    public ArrayList<Connection> getUpdatedConnections();

}
