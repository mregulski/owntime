package com.zespolowe.server.dataFormats;

import com.zespolowe.server.util.JsonUtil;

import static spark.Spark.*;

public class JsonConnection {

    private PointService pointService;

    public JsonConnection(PointService pointService) {

        if(pointService == null)
            throw new RuntimeException("PointService cannot be null");

        this.pointService = pointService;

    }

    public void configureRESTPointHandlers(){
        //get("/", (request, response) -> new PointDTO("karmel", "karmelowo", "karmel@karmelowo.com"), JsonUtil.json());
        get("/point/:id", (request, response) -> pointService.getById(Integer.parseInt(request.params(":id"))), JsonUtil.json());
        //get("/", ((request, response) -> new AdjustedTime(), JsonUtil.json()));
        //get("/", ((request, response) -> new Node(), JsonUtil.json()));
        //get("/", ((request, response) -> new Transport(), JsonUtil.json()));
    }
}
