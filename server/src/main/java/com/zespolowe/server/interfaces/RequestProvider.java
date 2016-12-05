package server.interfaces;

import server.dataFormats.Request;

/**
 *
 * @author Cirben
 */
public interface RequestProvider {

    public Request getRequest();

    public boolean shouldStop();
}
