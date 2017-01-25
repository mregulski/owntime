package com.zespolowe.server.dataFormats;

import com.google.gson.JsonIOException;
import com.zespolowe.server.PathFindingClasses.PathFinder;
import com.zespolowe.server.util.JsonUtil;
import spark.*;

import java.time.LocalDateTime;

import static spark.Spark.*;

public class JsonConnection {

    MariaDBDataProvider mariaDBDataProvider;

    DonaldTrumpRequestProvider donald;

    PathFinder pathFinder;

    public JsonConnection(MariaDBDataProvider mariaDBDataProvider, DonaldTrumpRequestProvider donald, PathFinder pathFinder){
        this.mariaDBDataProvider = mariaDBDataProvider;
        this.donald = donald;
        this.pathFinder = pathFinder;
    }

    LocalDateTime local;
    Request dataformats_request;
    Result result = null;

    public void configureRESTPointHandlers(){

        get("/stopsbyname/:name", (request, response) -> {
            return null;
        });

        get("/route/:from/:to/:time", (request, response) -> {

            String from = request.params(":from");
            String to = request.params(":to");
            String time = request.params(":time");

            local = LocalDateTime.parse(time);

            boolean from_int = true;
            boolean to_int = true;

            try{
                int n = Integer.parseInt(from);
            }catch(NumberFormatException ex){
                from_int = false;
            }

            try{
                int m = Integer.parseInt(to);
            }catch(NumberFormatException e){
                to_int = false;
            }

            if(from_int && to_int){
                dataformats_request = new Request(0, Integer.parseInt(from), Integer.parseInt(to), local);
                donald.addRequest(dataformats_request);
            }
            else if(from_int && !to_int){
                double x = Double.parseDouble(to.substring(0,to.indexOf(",")));
                double y = Double.parseDouble(to.substring(to.indexOf(",")+2, to.length()));

                dataformats_request = new Request(0, Integer.parseInt(from), new Coords(x, y), local);
                donald.addRequest(dataformats_request);

            }
            else if(!from_int && to_int){
                double x = Double.parseDouble(from.substring(0,from.indexOf(",")));
                double y = Double.parseDouble(from.substring(from.indexOf(",")+2, from.length()));

                dataformats_request = new Request(0, new Coords(x, y), Integer.parseInt(to), local);
                donald.addRequest(dataformats_request);
            }else{
                double from_x = Double.parseDouble(from.substring(0,from.indexOf(",")));
                double from_y = Double.parseDouble(from.substring(from.indexOf(",")+2, from.length()));

                double to_x = Double.parseDouble(to.substring(0,to.indexOf(",")));
                double to_y = Double.parseDouble(to.substring(to.indexOf(",")+2, to.length()));

                dataformats_request = new Request(0, new Coords(from_x, from_y), new Coords(to_x, to_y), local);
                donald.addRequest(dataformats_request);

            }

            pathFinder.setDataProvider(mariaDBDataProvider);
            pathFinder.setRequestProvider(donald);

            pathFinder.init();
            pathFinder.run();

            result = donald.getResult();


            return result;
        }, JsonUtil.json());
    }

}



