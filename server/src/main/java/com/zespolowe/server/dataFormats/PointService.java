package com.zespolowe.server.dataFormats;

/**
 * Created by ja on 05.12.16.
 */
public class PointService {

    public Point getById(int id){
        return new Point(id, "kot"+id, new Coords(id, id));
    }
    
    // Same stuff as above, but no need to create instances of this sad class. Yay!
    public static Point staticGetById(int id)
    {
    	return new Point(id, "kot"+id, new Coords(id, id));
    }
}
