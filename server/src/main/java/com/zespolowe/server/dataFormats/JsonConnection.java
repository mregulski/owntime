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
        get("/connection/:id", (request, response) -> pointService.getCons(Integer.parseInt(request.params(":id"))), JsonUtil.json());
        get("/stop/:id", (request, response) -> pointService.getStps(Integer.parseInt(request.params(":id"))), JsonUtil.json());

    }


}
