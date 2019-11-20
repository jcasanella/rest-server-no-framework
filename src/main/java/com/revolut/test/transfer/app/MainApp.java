package com.revolut.test.transfer.app;

import com.revolut.test.server.ServerRest;

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
