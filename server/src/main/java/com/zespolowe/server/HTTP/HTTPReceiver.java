package com.zespolowe.server.HTTP;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
/**
 * Created by lizzard on 17.01.17.
 */
public class HTTPReceiver {
    int port;
    HttpServer server;

    public HTTPReceiver(int port){
        this.port = port;
        try{
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new RootHandler());
            server.createContext("/echoHeader", new EchoHeaderHandler());
            server.createContext("/echoGet", new EchoGetHandler());
            server.setExecutor(null);
            server.start();
        }
        catch(IOException ex){
            System.out.println("Error: server can't start at: " + port);
        }

    }
}
