package com.zespolowe.server.delays;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.MariaDBDataProvider;
import com.zespolowe.server.dataFormats.Point;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

/**
 *	Chyba jednak nic z tego nie będzie.
 *
 */
public class Delays
{
	/** Graph whose vertices are pairs of the form (point, arrival time) and
	 *	whose edges are commute times. */ 
	private DirectedWeightedMultigraph<TimespacePoint, DefaultWeightedEdge> commuteGraph;
	public DirectedWeightedMultigraph<TimespacePoint, DefaultWeightedEdge> getCommuteGraph()
	{
		return this.commuteGraph;
	}
	
	/** A helper map used while initializing the commute graph. */
	private Map<Point, List<Integer>> timesOfPoints;
	
	/** The set of all points returned by MariaDBDataProvider.getStops() about
	 *	which we have some commute time data. */
	public Set<Point> getPoints()
	{
		return this.timesOfPoints.keySet();
	}
	
	/** Returns a TimespacePoint(p, t), where p is the point passed as an argument
	 *	and t is the least time when something arrives at p such that time <= t. */
	public TimespacePoint nextTimespacePoint(Point p, int time)
	{
		List<Integer> times = this.timesOfPoints.get(p);
		if(times == null) return null;
		for(int i = 0; i < times.size(); ++i)
		{
			if(time <= times.get(i)) return new TimespacePoint(p, times.get(i));
		}
		return null;
	}
	
	/** Same as above, but a bit packed. */
	public TimespacePoint nextTimespacePoint(TimespacePoint tsp)
	{
		return this.nextTimespacePoint(tsp.getPoint(), tsp.getTime());
	}
	
	/** Some tests. Looks like MariaDBDataProvider.getConnections() returns
	 * 	too few connections and there are no paths between their endpoints
	 * 	in the graph (for any pair). */
	public static void main(String[] args) throws IOException
	{
		Delays d = new Delays();
		List<Connection> lst = new MariaDBDataProvider().getConnections();
		int paths = 0;
		for(int i = 0; i < lst.size(); ++i)
		{
			Connection con = lst.get(i);
			List<DefaultWeightedEdge> path = d.estimateTime(con);
			if(path != null) paths++;
		}
		System.out.println("MariaDBDataProvider.getConnections() zwraca " + lst.size() + " połączeń, a między ich"
				+ "początkami i końcami jest w grafie " + paths + " ścieżek.");
	}
	
	public Delays()
	{
		// Initialize stuff and read data about points from the DB.
		this.commuteGraph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
		this.timesOfPoints = new HashMap<>();
		new MariaDBDataProvider().getStops().forEach(point -> this.timesOfPoints.put(point, new ArrayList<>()));
		
		// Read commute time data for each source point.
		this.getPoints().forEach(point ->
		{
			// This try-catch block is required by Files.lines, but nothing
			// should be thrown unless too many files are open.
			try
			{
				// Read data about the point whose id is src.
				if(Files.exists(Paths.get("data/" + point.getId())))
				{
					Files.readAllLines(Paths.get("data/" + point.getId())).forEach(line ->
					{
						// The data format is like this:
						// dst1 departureTime1 commuteTime1 ... departureTimeN commuteTimeN
						// ...
						// dstM departeTime1 commuteTime1 ... departureTimeN' commuteTimeN'.
						// The file can be empty.
						String[] splits = line.split(" ");
						
						// This is the first pass, so we will only collect departure times
						// for each point. This list will be then stored in a map that we
						// will use in the second pass.
						for(int i = 1; i < splits.length; i += 2)
						{
							int departureTime = Integer.parseInt(splits[i]);
							this.commuteGraph.addVertex(new TimespacePoint(point, departureTime));
							this.timesOfPoints.get(point).add(departureTime);
						}
					});
				}
			}
			catch(Exception e)
			{
				System.out.println("Error in class Delays, first loop.");
				e.printStackTrace();
			}
		});

		// First few lines of the loop are exactly the same as before.
		this.getPoints().forEach(srcPoint ->
		{
			try
			{
				if(Files.exists(Paths.get("data/" + srcPoint.getId())))
				{
					Files.readAllLines(Paths.get("data/" + srcPoint.getId())).forEach(line ->
					{
						String[] splits = line.split(" ");
						
						// This time we will connect source points and target points with edges
						// whose weights are commute times.
						Point dstPoint = new Point(Integer.parseInt(splits[0]));
						for(int i = 1; i < splits.length; i += 2)
						{
							int departureTime = Integer.parseInt(splits[i]);
							int commuteTime = Integer.parseInt(splits[i + 1]);

							// We want to connect the pair (source point, departureTime) to
							// (destination point, next arrival time after departureTime + commuteTime).
							TimespacePoint src = new TimespacePoint(srcPoint, departureTime);
							TimespacePoint dst = this.nextTimespacePoint(dstPoint, departureTime + commuteTime);

							// Such a point may not exist (for example late at night)
							// and we can add it to the graph only if it isn't null.
							if(dst != null)
							{
								this.commuteGraph.setEdgeWeight(
									this.commuteGraph.addEdge(src, dst), commuteTime);
							}
						}
					});
				}
			}
			catch(Exception e)
			{
				System.out.println("Error in class Delays, second loop.");
				e.printStackTrace();
			}
		});
	}	

	/** Try to estimate the commute time between the start and end points of
	 * 	a connection. This doesn't work because it looks like there is not
	 * 	enough data about commute times. */
	public List<DefaultWeightedEdge> estimateTime(Connection con)
	{
		// Convert dates' hour-of-day and minute-of-hour into
		// minute-of-day format.
		TimespacePoint src = new TimespacePoint(new Point(con.getIdB()),
			con.getDeparture().getHour() * 3600 + con.getDeparture().getMinute() * 60);
		TimespacePoint dst = new TimespacePoint(new Point(con.getIdA()),
			con.getArrival().getHour() * 3600 + con.getArrival().getMinute() * 60);

		TimespacePoint nearSrc = this.nextTimespacePoint(src);
		TimespacePoint nearDst = this.nextTimespacePoint(dst);
		
		// Tests.
		//System.out.println(src);
		//System.out.println(dst);

		
		//System.out.println("Next point after " + src + " is " + nearSrc);
		//System.out.println("Next point after " + dst + " is " + nearDst);

		//System.out.println("this.commuteGraph().vertexSet().contains(nearSrc) = " + this.commuteGraph.vertexSet().contains(nearSrc));
		//System.out.println("this.commuteGraph().vertexSet().contains(nearDst) = " + this.commuteGraph.vertexSet().contains(nearDst));

		//System.out.println(this.timesOfPoints.get(nearSrc));
		//System.out.println(this.timesOfPoints.get(nearDst));
		
		// Try to find a path.
		if(this.commuteGraph.vertexSet().contains(nearSrc) && this.commuteGraph.vertexSet().contains(nearDst))
		{
			List<DefaultWeightedEdge> path =
				DijkstraShortestPath.findPathBetween(this.getCommuteGraph(), nearSrc, nearDst);
			return path;
		}
		else
		{
			return null;
		}
	}
}