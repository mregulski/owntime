package server.dataFormats;

import java.util.ArrayList;
import java.util.Collections;
import server.PathFindingClasses.VConnection;

/**
 *
 * @author Cirben
 */
public class Path {

	ArrayList<VConnection> p;

	public Path() {
		p = new ArrayList<>();
	}

	public void add(VConnection last) {
		p.add(last);
	}

	/**
	 * @return	returns array of connections from source to target
	 */
	public ArrayList<Connection> getConnectionList() {
		ArrayList<Connection> ret = new ArrayList<>();
		for (VConnection v : p) {
			ret.add(v.getConnection());
			Collections.reverse(ret);
		}

		return ret;
	}
	/**
	 * @return	returns REVERSED array of connections from source to target use Collections.reverse(p) to reverse;
	 */
	public ArrayList<VConnection> getVConnectionList() {
		return p;
	}

	public void print() {
		for (int i = p.size() - 1; i >= 0; --i) {
			System.out.print(" " + p.get(i).getSource().getId() + "->" + p.get(i).getTarget().getId() + " ");
		}
		System.out.println();
	}
}
