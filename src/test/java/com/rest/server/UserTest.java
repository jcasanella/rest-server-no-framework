package com.rest.server;

import com.revolut.rest.model.User;
import com.revolut.rest.server.ServerRest;
import com.revolut.rest.server.constants.NameResources;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;

public class UserTest {

    private ClientGeneric cg = new ClientGeneric();

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

        String userJson = cg.buildJsonUser("test_name", "surname_test", "address_test", "city_test");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("test_name", "surname_test", "address_test", "city_test");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        cg.getElement(client, added.getId(), NameResources.USERS, User.class);

        client.close();
    }

    @Test
    public void doGetAll() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        User[] users = cg.getElements(client, NameResources.USERS, User[].class);
        Arrays.stream(users).forEach(System.out::println);

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("name_test7", "surname_test7", "address_test7", "city_test7");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        String userUpdateJson = cg.buildJsonUpdateUser(added.getId(), "name_test7", "surname_test7", "address_updated",
                "city_updated");
        cg.update(client, userUpdateJson, NameResources.USERS, User.class);

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        cg.delete(client, "", NameResources.USERS);

        client.close();
    }

    @Test
    public void doDelete2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("name_test8", "surname_test8", "address_test8", "city_test8");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        cg.delete(client, added.getId(), NameResources.USERS);

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
