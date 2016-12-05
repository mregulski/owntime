package com.zespolowe.server.PathFindingClasses;

import java.time.LocalDateTime;

/**
 *
 * @author Cirben
 */
class DijkstraValues {

		VConnection fastestIn;
		boolean visited;
		LocalDateTime in;

		DijkstraValues(VConnection a, boolean b, LocalDateTime in) {
				fastestIn = a;
				visited = b;
				this.in = in;
		}

		DijkstraValues() {
		}

		public VConnection getFastestIn() {
				return fastestIn;
		}

		public void setFastestIn(VConnection fastestIn) {
				this.fastestIn = fastestIn;
		}

		public boolean isVisited() {
				return visited;
		}

		public void setVisited(boolean visited) {
				this.visited = visited;
		}

		public LocalDateTime getIn() {
				return in;
		}

		public void setIn(LocalDateTime in) {
				this.in = in;
		}

}
