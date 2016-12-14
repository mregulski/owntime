package com.zespolowe.server.interfaces;


import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.Point;

import java.util.ArrayList;

/**
 *
 * @author Cirben
 */
public interface DataProvider { 
   
	/** 
	* @return Unordered ArrayList of all stops. Point's id is it's stop pole(?) number. 
	*/	
    ArrayList<Point> getStops(); 	
	
    /** 
	* @return unordered ArrayList of all elementary connections with unique id for future updating of specific Connection. Example,
    * all planned departures and arrivals for all pairs of connected points. That includes connections for all kinds of transport 
    * except for on foot Connections which are computed and added during the model creation.
    * elementary Connections should be added for ex. For Next 24 hours period. Then during each update new connections should be
    * added so length of the period is maintained.
    */
    ArrayList<Connection> getConnections();

    /** 
    * @return unordered ArrayList of new and updated Connections which are updated by their unique id. During update process 
    * Times of departure and arrival are updated in coresponding connections. Ex. Real data prediction alg. predicts new arrival 
    * time and new Connection with specific id(of Connection to be updated) and updated times will be passed 
    * through this function.
    */
    ArrayList<Connection> getUpdatedConnections();

}
