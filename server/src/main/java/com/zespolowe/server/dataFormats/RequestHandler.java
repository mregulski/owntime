package com.zespolowe.server.dataFormats;

import com.zespolowe.server.interfaces.RequestProvider;

import java.time.LocalDateTime;

/**
 * Created by lizzard on 19.01.17.
 */
public class RequestHandler implements RequestProvider {
    private Request request;

    public RequestHandler(int requestId, int nearestStopId, int targetStopId, LocalDateTime time){
        this.request = new Request(requestId, nearestStopId, targetStopId, time);
    }

    public RequestHandler(int requestId, Coords startLocation, int targetStopId, LocalDateTime time){
        this.request = new Request(requestId, startLocation, targetStopId, time);
    }

    public RequestHandler(int requestId, int nearestStopId, Coords targetLocation, LocalDateTime time){
        this.request = new Request(requestId, nearestStopId, targetLocation, time);
    }

    public RequestHandler(int requestId, Coords startLocation, Coords targetLocation, LocalDateTime time){
        this.request = new Request(requestId, startLocation, targetLocation, time);
    }


    @Override
    public Request getRequest() {
        return this.request;
    }

    @Override
    public boolean shouldStop() {
        return true;
    }
}
