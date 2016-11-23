package server.PathFindingClasses;

import java.time.LocalDateTime;
import server.dataFormats.Connection;
import server.dataFormats.Point;
import server.interfaces.DataProvider;
import server.interfaces.RequestProvider;
import java.util.ArrayList;
import server.dataFormats.Request;

/**
 *
 * @author Cirben
 */
public class ReallyBadExampleOfFakeDataAndRequestProviderInOneClass implements RequestProvider, DataProvider {

	ArrayList<Request> testRequests;

	public ReallyBadExampleOfFakeDataAndRequestProviderInOneClass() {
		
		LocalDateTime a;
		a = LocalDateTime.now();
		testRequests = new ArrayList<>();
		for (int i = 0; i < 2; ++i) {
			testRequests.add(new Request(i,i, i + 5, LocalDateTime.now().plusMinutes(i)));
		}
	}

	@Override
	public Request getRequest() {
		return testRequests.isEmpty() ? null : testRequests.remove(0);
	}

	@Override
	public boolean shouldStop() {
		return !testRequests.isEmpty();
	}

	@Override
	public ArrayList<Point> getStops() {
		ArrayList<Point> lol = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			lol.add(new Point(i));
		}
		return lol;
	}

	@Override
	public ArrayList<Connection> getConnections() {
		ArrayList<Connection> lol = new ArrayList<>();
		LocalDateTime a;
		a = LocalDateTime.now().plusMinutes(2);
		for (int i = 0; i < 9; i++) {
			lol.add(new Connection(i,i,i+1,a.plusMinutes(i),a.plusMinutes(i+1)));
		}
		return lol;
	}

	@Override
	public ArrayList<Connection> getUpdatedConnections() {
		return null;
	}

}
