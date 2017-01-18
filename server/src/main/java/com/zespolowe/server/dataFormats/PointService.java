package com.zespolowe.server.dataFormats;

/**
 * Created by ja on 05.12.16.
 */
public class PointService {

    Point start;
    Point end;
    //AdjustedTime planned;
    //AdjustedTime predicted;
    //Route route; tab nodów; nody, time - Michał, point, transport - Olga

    public PointService(){

    }

    public Point getById(int id){
        return new Point(id, "kot"+id, new Coords(id, id));
    }

}
