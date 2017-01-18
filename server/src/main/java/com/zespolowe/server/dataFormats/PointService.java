package com.zespolowe.server.dataFormats;

import com.zespolowe.server.PathFindingClasses.ReallyBadExampleOfFakeDataAndRequestProviderInOneClass;

import java.util.ArrayList;

public class PointService {

    ReallyBadExampleOfFakeDataAndRequestProviderInOneClass rbeofdarpion = new ReallyBadExampleOfFakeDataAndRequestProviderInOneClass();

    //Node node;
    //AdjustedTime planned;
    //AdjustedTime predicted;
    //Route route - tab. nodów;
    //nody, time - Michał, point, transport - Olga

    public Connection getCons(int id){
        return rbeofdarpion.getConnections().get(id);
    }

    public Point getStps(int id){
        return rbeofdarpion.getStops().get(id);
    }

    public Point getById(int id){
        return new Point(id, "kot"+id, new Coords(id, id));
    }

}
