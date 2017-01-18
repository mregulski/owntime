package com.zespolowe.server.delays;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.zespolowe.server.PathFindingClasses.ReallyBadExampleOfFakeDataAndRequestProviderInOneClass;
import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.MariaDBDataProvider;
import com.zespolowe.server.dataFormats.Point;
import com.zespolowe.server.dataFormats.PointService;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.alg.DijkstraShortestPath;
//import static fj.P.p;


/**
 *	This class will turn text files with data about commute time 
 *	between adjacent stops into a graph. As of now, it can load
 *	the data and store it in a retarded graph (it doesn't contain
 *	the departure time data (or is it arrival time?)). There's also this
 *  retarded map of maps of maps (hey Java, why in hell don't
 *	you have a pair class in the standard library?).
 *
 */
public class Delays
{
	// Słupki.
	List<Point> points;
	
	// Source point -> destination point -> average commute time (rounded to the nearest integer).
	Map<Integer, Map<Integer, Integer>> averageCommuteTime;
	
	// Source point -> destination point -> arrival time -> commute time.
	Map<Integer, Map<Integer, Map<Integer, Integer>>> commuteTimes;
	
	// Vertices are points, edges are average commute times for each
	// departure time.
	DirectedWeightedMultigraph<Integer, DefaultWeightedEdge> commuteGraph;
	
	/**
	 *	@param src Id of the source point.
	 *	@param dst Id of the destination point.
	 *	@return
	 *		Optional.empty when there's no data about delays for
	 *			this source-destination pair (like when they are
	 *			not directly connected).
	 *		Otherwise Optional[t] where t is an average commute time
	 *			of this connection (it's an average of averages for all
	 *			departure times)
	 */
	public Optional<Integer> averageCommuteTimeById(int src, int dst)
	{
		return Optional.ofNullable(this.averageCommuteTime.get(src)).map(map -> map.get(dst));
	}
	
	public Optional<Integer> averageCommuteTime(Point src, Point dst)
	{
		return averageCommuteTimeById(src.getId(), dst.getId());
	}
	
	/**
	 *	@param src Id of the source point.
	 *	@param dst Id of the destination point.
	 *	@return
	 *		Optional.empty when there's no data about delays for
	 *			this source-destination pair (like when they are
	 *			not directly connected).
	 *		Otherwise Optional[m] where m is a map that maps
	 *			departure times to average commute times for
	 *			this particular departure time only.
	 */
	public Optional<Map<Integer, Integer>> commuteTimesById(int src, int dst)
	{
		return Optional.ofNullable(this.commuteTimes.get(src)).map(map -> map.get(dst));
	}
	
	// For test purposes.
	public static void main(String[] args) throws IOException
	{		
		Delays d = new Delays();
		System.out.println(d.averageCommuteTimeById(100, 102));
		System.out.println(d.averageCommuteTime.get(100));
		
		System.out.println(d.commuteGraph.containsEdge(100, 102));
		System.out.println(d.commuteGraph.getAllEdges(100, 102).size());
		
		DijkstraShortestPath<Integer, DefaultWeightedEdge> dsp =
			new DijkstraShortestPath<>(d.commuteGraph, 100, 799);
		System.out.println(dsp.getPath());
		dsp.getPath().getEdgeList().forEach(edge ->
		{
			System.out.println(edge + ", weight = " + d.commuteGraph.getEdgeWeight(edge));
		});
		
	}
	
	public Delays()
	{
		// Read points from the database, initialize stuff and add
		// the points as vertices to the graph.
		this.points = new MariaDBDataProvider().getStops();
		this.averageCommuteTime = new HashMap<>();
		this.commuteTimes = new HashMap<>();
		this.commuteGraph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
		this.points.forEach(point -> this.commuteGraph.addVertex(point.getId()));

		// Read delay data for each source point.
		this.points.forEach(src ->
		{
			// Initialize further stuff.
			this.averageCommuteTime.put(src.getId(), new HashMap<>());
			this.commuteTimes.put(src.getId(), new HashMap<>());
			
			// This try-catch block is required by Files.lines, but nothing
			// should be thrown unless too many files are open.
			try
			{
				// Read data about the point whose id is src.
				Files.lines(Paths.get("data/" + src.getId())).forEach(line ->
				{
					// The data format is like this:
					// dst1 departureTime1 commuteTime1 ... departureTimeN commuteTimeN
					// ...
					// dstM departeTime1 commuteTime1 ... departureTimeN' commuteTimeN'.
					// The file can be empty.
					String[] splits = line.split(" ");
					PointService p = new PointService();
					Point dst = p.getById(Integer.parseInt(splits[0]));

					// Initialize more stuff. We will compute the average (it'll most
					// likely be useless, but whatever).
					this.commuteTimes.get(src.getId()).put(dst.getId(), new HashMap<>());
					int sumOfCommuteTimes = 0;
					
					// We go from 1 in order to omit the destination's id.
					// We go by 2 to process each pair of departure time and
					// commute time at once.
					for(int i = 1; i < splits.length; i += 2)
					{
						int arrivalTime = Integer.parseInt(splits[i]);
						int commuteTime = Integer.parseInt(splits[i + 1]);
						this.commuteTimes.get(src.getId()).get(dst.getId()).put(arrivalTime, commuteTime);
						sumOfCommuteTimes += commuteTime;

						this.commuteGraph.setEdgeWeight(this.commuteGraph.addEdge(src.getId(), dst.getId()), commuteTime); //p(arrivalTime, commuteTime));
					}
					int averageCommuteTime = Math.round(sumOfCommuteTimes / ((splits.length - 1) / 2));
					this.averageCommuteTime.get(src.getId()).put(dst.getId(), averageCommuteTime);
				});
			}
			catch(Exception e)
			{
				System.out.println("Jeżeli tu pofrunął error, to jakiś geniusz prawdopodobnie otworzył za dużo plików.");
				e.printStackTrace();
			}
		});
		System.out.println(this.commuteGraph.edgeSet().size());
	}
}