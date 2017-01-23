package com.zespolowe.server.interfaces;

import com.zespolowe.server.dataFormats.Path;
import com.zespolowe.server.dataFormats.Request;

/**
 *
 * @author Cirben
 */
public interface RequestProvider {

    public Request getRequest();

    public boolean shouldStop();

	public void addPath(Path path);
}
