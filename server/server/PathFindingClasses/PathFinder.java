package server.PathFindingClasses;

import server.dataFormats.Connection;
import server.interfaces.DataProvider;
import server.dataFormats.Request;
import server.interfaces.RequestProvider;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.dataFormats.Path;

public class PathFinder implements Runnable {

	DataProvider dp;
	RequestProvider rp;
	CommunicationMap graph;

	public void setDataProvider(DataProvider dp) {
		this.dp = dp;
	}

	public void setRequestProvider(RequestProvider rp) {
		this.rp = rp;
	}

	public void init() {
		graph = new CommunicationMap(dp.getStops(), dp.getConnections());
	}

	@Override
	public void run() {
		while (rp.shouldStop()) {
			ArrayList<Connection> updatedConnections = dp.getUpdatedConnections();
			if (updatedConnections != null) {
				graph.update(updatedConnections);
			}
			for (int i = 0; i < 3; i++) { // TO:DO replace with time based update interval ex: update every 30 seconds
				//TO:DO invoke multiple findPaths in new threads
				Request r = rp.getRequest();
				Path path;
				try {
					path = graph.findPath(r, 0);
					System.out.println("request id: " + r.getRequestId());
					path.print(); 
									//TO:DO function to process found path in some way
									// put answer in request class?
									// make another interface: request analizer and inject into this class then invoke analizer.analize(path)?
				} catch (MyException ex) {
					System.out.println("EXCEPTION: "+ex.getMessage());
				}
			}
		}
	}
}
