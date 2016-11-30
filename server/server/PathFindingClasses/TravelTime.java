package server.PathFindingClasses;

/**
 *
 * @author Cirben
 *
 * This class is used for transports such as traveling on foot or by bike where
 * departure time depends on time one entered to the stop.
 */
public class TravelTime implements Comparable {

    int secs;
    VPoint target;
    VPoint source;

    TravelTime(int i, VPoint a, VPoint b) {
        secs = i;
        this.source = a;
        this.target = b;
    }

    public int getSecs() {
        return secs;
    }

    public VPoint getTarget() {
        return target;
    }

    public VPoint getSource() {
        return source;
    }

    @Override
    public int compareTo(Object o) {
        if (this.getSecs() < ((TravelTime) o).getSecs()) {
            return -1;
        } else if (this.getSecs() == ((TravelTime) o).getSecs()) {
            return 0;
        } else {
            return 1;
        }
    }

}
