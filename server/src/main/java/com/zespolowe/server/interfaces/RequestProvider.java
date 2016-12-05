package com.zespolowe.server.interfaces;

import com.zespolowe.server.dataFormats.Request;

/**
 *
 * @author Cirben
 */
public interface RequestProvider {
    public Request getRequest();

	public boolean shouldStop();
}
