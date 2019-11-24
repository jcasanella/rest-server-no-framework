package com.rest.server;

import com.revolut.rest.model.User;
import com.revolut.rest.server.ServerRest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;

public class UserTest extends UserActions{

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

    @Test
    public void doPost() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

       addUser(client, "test_name", "surname_test", "address_test", "city_test");

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        User user = addUser(client, "test_name", "surname_test", "address_test", "city_test");
        getUser(client, user.getId());

        client.close();
    }

    @Test
    public void doGetAll() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        getUsers(client);

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        User user = addUser(client, "name_test7", "surname_test7", "address_test7",
                "city_test7");
        updateUser(client, user.getId(), "name_test7", "surname_test7", "address_updated",
                "city_updated");

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        deleteUser(client, "");

        client.close();
    }

    @Test
    public void doDelete2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        User user = addUser(client, "name_test8", "surname_test8", "address_test8",
                "city_test8");
        deleteUser(client, user.getId());

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
