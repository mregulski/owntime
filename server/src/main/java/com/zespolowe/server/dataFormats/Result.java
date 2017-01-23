package com.zespolowe.server.dataFormats;

import java.util.ArrayList;

/**
 * Created by lizzard on 23.01.17.
 * Uwaga! Zakładam, że Nody są uszeregowane w kolejności występowania na trasie przejazdu. Należy o to zadbać!!!
 */
public class Result {
    public Point start;
    public Point stop;
    public AdjustedTime departure;
    public AdjustedTime arrival;
    public ArrayList<Node> route;

    public Result(Point start, Point stop, ArrayList<Node> route){
        this.start = start;
        this.stop = stop;
        this.route = route;
        this.departure = route.get(0).departure;
        this.arrival = route.get(route.size() - 1).arrival;
    }


}
