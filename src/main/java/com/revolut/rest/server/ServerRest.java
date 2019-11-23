package com.revolut.rest.server;

import com.revolut.rest.money.api.StatusCode;
import com.revolut.rest.server.api.HandlerImpl;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.errors.ExceptionHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRest {
    private static ServerRest instance = null;
    private static Logger log = Logger.getLogger(ServerRest.class);
    private static HttpServer server;
    private static ExecutorService httpThreadPool;

    private ServerRest() {

    }

    public static ServerRest getInstance() {
        if (instance == null) {
            synchronized (ServerRest.class) {
                if (instance == null) {
                    instance = new ServerRest();
                }
            }
        }

        return instance;
    }

    public void startUp() throws IOException {
        int port = 8001;

        server = HttpServer.create(new InetSocketAddress(port), 0);
        log.info("Starting the server");

        HandlerImpl registrationHandler = new HandlerImpl(new ExceptionHandler());
        server.createContext(
                String.format("/%s/%s", NameResources.VERSION, NameResources.USERS), // Create URI
                registrationHandler::handle
        );

        httpThreadPool = Executors.newFixedThreadPool(10);
        server.setExecutor(httpThreadPool);
        server.start();
    }

    public void stop() {
        log.info("Closing the server");
        server.stop(1);
        httpThreadPool.shutdownNow();
    }
}
