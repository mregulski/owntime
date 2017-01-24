package com.zespolowe.server.dataFormats;

import com.zespolowe.server.interfaces.RequestProvider;
import java.util.ArrayList;

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

    @Override
	public synchronized void addPath(Path path) {
		// TYM WRZUCAĆ OBLICZONE PATHY!!!
		isReady = true;
		pathList.add(path);
	}

	public synchronized Result getResult() {
		if (pathList.size() == 1) {
			isReady = false;
		}
		Path path = pathList.remove(pathList.size() - 1);
		return Path.toResult(path);
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
		//return (!isProcessing);
        return false;
	}
}
