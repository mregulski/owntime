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
    
    boolean stopIdSpecfied;
    boolean targetIdSpecfied;
    
    Coords startLocation;
    Coords targetLocation;
    
    LocalDateTime when;

    public Request(int requestId, int nearestStopId, int targetStopId, LocalDateTime time) {
        this.requestId = requestId;
        stopId = nearestStopId;
        targetId = targetStopId;
        when = time;
        stopIdSpecfied = true;
        targetIdSpecfied = true;
    }
    
    public Request(int requestId, Coords startLocation, int targetStopId, LocalDateTime time) {
        this.requestId = requestId;
        this.startLocation = startLocation;
        targetId = targetStopId;
        when = time;
        stopIdSpecfied = false;
        targetIdSpecfied = true;
    }
    
    public Request(int requestId, int nearestStopId, Coords targetLocation, LocalDateTime time) {
        this.requestId = requestId;
        stopId = nearestStopId;
        this.targetLocation = targetLocation;
        when = time;
        stopIdSpecfied = true;
        targetIdSpecfied = false;
    }
    
    public Request(int requestId, Coords startLocation, Coords targetLocation, LocalDateTime time) {
        this.requestId = requestId;
        this.startLocation = startLocation;
        this.targetLocation = targetLocation;
        when = time;
        stopIdSpecfied = false;
        targetIdSpecfied = false;
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

    public int getStopId() {
        return stopId;
    }

    public boolean isStopIdSpecfied() {
        return stopIdSpecfied;
    }

    public boolean isTargetIdSpecfied() {
        return targetIdSpecfied;
    }

    public Coords getStartLocation() {
        return startLocation;
    }

    public Coords getTargetLocation() {
        return targetLocation;
    }
    
    

}
