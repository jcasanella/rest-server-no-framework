package com.rest.server;

import com.revolut.rest.server.ServerRest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;

public class UserAccountsTest {

    @BeforeClass
    public static void startUp() {
        Runnable task = () -> {
            try {
                ServerRest.getInstance().startUp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
