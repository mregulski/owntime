package com.zespolowe.server.PathFindingClasses;

import com.zespolowe.server.dataFormats.Connection;
import java.time.LocalDateTime;

/**
 *
 * @author Cirben
 */
public class VConnection implements Comparable{
	Connection c;
	VPoint target;
	VPoint source;
	
	public VConnection(Connection c, VPoint a, VPoint b){
		this.c = c;
		this.source = a;
		this.target = b;
	}

	public Connection getConnection() {
		return c;
	}


	public LocalDateTime getDeparture() {
		return c.getDeparture();
	}

	public LocalDateTime getArrival(){
		return c.getArrival();
	}

	public VPoint getSource() {
		return source;
	}

	@Override
	public int compareTo(Object b) {
		return this.getDeparture().compareTo(((VConnection)b).getDeparture());
	}

	public int getId() {
		return c.getId();
	}

	public VPoint getTarget() {
		return target;
	}



}
