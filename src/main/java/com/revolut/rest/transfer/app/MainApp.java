package com.revolut.rest.transfer.app;

import com.revolut.rest.server.ServerRest;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Starting the App");

        try {
            ServerRest.getInstance().startUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
