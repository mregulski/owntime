package com.zespolowe.server.PathFindingClasses;

import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.Coords;
import com.zespolowe.server.dataFormats.Point;
import com.zespolowe.server.dataFormats.Request;
import com.zespolowe.server.dataFormats.Transport;
import com.zespolowe.server.dataFormats.TransportType;
import com.zespolowe.server.interfaces.DataProvider;
import com.zespolowe.server.interfaces.RequestProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Cirben
 */
public class ReallyBadExampleOfFakeDataAndRequestProviderInOneClass implements RequestProvider, DataProvider {

    ArrayList<Request> testRequests;

    int ROWI = 50;
    int ROWJ = 50;

    public ReallyBadExampleOfFakeDataAndRequestProviderInOneClass() {

        LocalDateTime a;
        a = LocalDateTime.now();
        testRequests = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 5; ++i) {
            testRequests.add(new Request(i, r.nextInt(ROWI * ROWJ), r.nextInt(ROWI * ROWJ), LocalDateTime.now().plusMinutes(i)));
        }
        testRequests.add(new Request(5, new Coords(r.nextDouble() * 0.0005 * r.nextInt(ROWI), r.nextDouble() * 0.0005 * r.nextInt(ROWJ)), r.nextInt(ROWI * ROWJ), LocalDateTime.now().plusMinutes(5)));
        testRequests.add(new Request(6, r.nextInt(ROWI * ROWJ), new Coords(r.nextDouble() * 0.0005 * r.nextInt(ROWI), r.nextDouble() * 0.0005 * r.nextInt(ROWJ)), LocalDateTime.now().plusMinutes(6)));
        testRequests.add(new Request(7, new Coords(r.nextDouble() * 0.0005 * r.nextInt(ROWI), r.nextDouble() * 0.0005 * r.nextInt(ROWJ)), new Coords(r.nextDouble() * 0.0005 * r.nextInt(ROWI), r.nextDouble() * 0.0005 * r.nextInt(ROWJ)), LocalDateTime.now().plusMinutes(7)));
    }

    @Override
    public Request getRequest() {
        return testRequests.isEmpty() ? null : testRequests.remove(0);
    }

    @Override
    public boolean shouldStop() {
        return !testRequests.isEmpty();
    }

    @Override
    public ArrayList<Point> getStops() {
        ArrayList<Point> lol = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < ROWI; i++) {
            for (int j = 0; j < ROWJ; j++) {
                lol.add(new Point(i * ROWJ + j, "test", new Coords(r.nextDouble() * 0.0005 * i, r.nextDouble() * 0.0005 * j)));
            }
        }
        return lol;
    }

    @Override
    public ArrayList<Connection> getConnections() {
        ArrayList<Connection> lol = new ArrayList<>();
        LocalDateTime a;
        a = LocalDateTime.now().plusMinutes(2);
        Random r = new Random();
        for (int i = 0; i < ROWI; i++) {
            for (int j = 0; j < ROWJ; j++) {
                if (r.nextInt(10) < 7 && i != ROWI - 1) {
                    for (int k = 0; k < 100; k++) {
                        lol.add(new Connection((i * ROWJ + j) * 1000 + k, (i * ROWJ + j), ((i + 1) * ROWJ + j), a.plusSeconds(k * 60), a.plusSeconds(k * 60 + 20 + r.nextInt(50)), new Transport(TransportType.BUS,"")));
                    }
                }
                if (r.nextInt(10) < 7 && i != 0) {
                    for (int k = 0; k < 100; k++) {
                        lol.add(new Connection((i * ROWJ + j) * 1000 + k + 100, (i * ROWJ + j), ((i - 1) * ROWJ + j), a.plusSeconds(k * 60), a.plusSeconds(k * 60 + 10 + r.nextInt(40)), new Transport(TransportType.BUS,"")));
                    }
                }
                if (r.nextInt(10) < 7 && j != ROWJ - 1) {
                    for (int k = 0; k < 100; k++) {
                        lol.add(new Connection((i * ROWJ + j) * 1000 + k + 200, (i * ROWJ + j), (i * ROWJ + (j + 1)), a.plusSeconds(k * 60), a.plusSeconds(k * 60 + 15 + r.nextInt(50)), new Transport(TransportType.BUS,"")));
                    }
                }
                if (r.nextInt(10) < 7 && j != 0) {
                    for (int k = 0; k < 100; k++) {
                        lol.add(new Connection((i * ROWJ + j) * 1000 + k + 300, (i * ROWJ + j), (i * ROWJ + (j - 1)), a.plusSeconds(k * 60), a.plusSeconds(k * 60 + 20 + r.nextInt(50)), new Transport(TransportType.BUS,"")));
                    }
                }
            }
        }
        return lol;
    }

    @Override
    public ArrayList<Connection> getUpdatedConnections() {
        return null;
    }

}
