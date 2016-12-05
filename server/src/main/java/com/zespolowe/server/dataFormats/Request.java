package com.zespolowe.server.dataFormats;

import java.time.LocalDateTime;

/**
 *
 * @author Cirben
 */
public class Request {

	int requestId;
	int stopId;
	int targetId;
	LocalDateTime when;	
	
	public Request(int requestId, int nearestStopId, int targetStopId, LocalDateTime time){
		this.requestId = requestId;
		stopId = nearestStopId;
		targetId = targetStopId;
		when = time;
	}

	public int getTargetId() {
		return targetId;
	}

	public int getRequestId() {
		return requestId;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public int getStopId(){
		return stopId;
	}

}
