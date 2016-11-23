package server.dataFormats;

import java.time.LocalDateTime;

/**
 *
 * @author Cirben
 */
public class Connection {
	int idA; // id of departure stop
	int idB; // id of arrival stop
	LocalDateTime departure;
	LocalDateTime arrival;
	int id; //connection id for updating

	public Connection(int id, int idA, int idB, LocalDateTime departure, LocalDateTime arrival){
		this.id = id;
		this.idA = idA;
		this.idB = idB;
		this.departure = departure;
		this.arrival = arrival;
	}

	public int getId() {
		return id;
	}

	public int getIdA() {
		return idA;
	}

	public int getIdB() {
		return idB;
	}

	public LocalDateTime getDeparture() {
		return departure;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}
	
}
