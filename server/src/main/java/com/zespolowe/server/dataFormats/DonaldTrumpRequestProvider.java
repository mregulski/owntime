package com.zespolowe.server.dataFormats;

import com.zespolowe.server.PathFindingClasses.VConnection;
import com.zespolowe.server.interfaces.RequestProvider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.time.LocalDateTime.now;
import java.util.Collections;

/**
 * Created by lizzard on 19.01.17. Mamy dwie listy działające na zasadzie
 * stosów: - requestList - lista aktualnych requestów - pathList - lista
 * aktualnych wyników Każdy getter ściąga ze stosu. Wstawiłem synchrony dla
 * pewności, że jakieś wątki się nie popitolą.
 */
public class DonaldTrumpRequestProvider implements RequestProvider {

	private ArrayList<Request> requestList;
	private ArrayList<Path> pathList;
	private boolean isProcessing;
	private boolean isReady;
	//Modyfikacja Path Findera.
	//Może dodać konwersję z Path od razu do Resulta w tej klasie i stworzyć stos do pobierania Resultów?

	public DonaldTrumpRequestProvider() {
		requestList = new ArrayList<>();
		pathList = new ArrayList<>();
		isProcessing = false;
		isReady = false;
	}

	public synchronized void addPath(Path path) {
		// TYM WRZUCAĆ OBLICZONE PATHY!!!
		isReady = true;
		pathList.add(path);
	}

	public synchronized Result getResult() { //TODO Przetworzenie Path ze stosu na obiekt Result
		Result result;
		if (pathList.size() == 1) {
			isReady = false;
		}
		Path path = pathList.remove(pathList.size() - 1);
		// FEEEEEEEEEEEEEJK!!!
		return Path.toResult(path);
		/*
        ArrayList<Node> route = new ArrayList<>();
        result = new Result(new Point(0), new Point(0), route);
        return result;*/
	}

	public synchronized boolean isProcessingFinished() {
		return isReady;
	}

	public synchronized void addRequest(Request request) {
		isProcessing = true;
		requestList.add(request);
	}

	@Override
	public synchronized Request getRequest() {
		if (requestList.size() == 1) {
			isProcessing = false;
		}
		return requestList.remove(requestList.size() - 1);
	}

	@Override
	public synchronized boolean shouldStop() {
		return (!isProcessing);
	}
}
