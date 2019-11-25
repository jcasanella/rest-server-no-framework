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

public class UserTest {

    private UserClient uc = new UserClient();
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

        String userJson = "{\"name\": \"test_name\" , \"surname\" : \"surname_test\" , \"address\" : \"address_test\" , " +
                "\"city\" : \"city_test\"}";
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = "{\"name\": \"test_name\" , \"surname\" : \"surname_test\" , \"address\" : \"address_test\" , " +
                "\"city\" : \"city_test\"}";
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        uc.getUser(client, added.getId());

        client.close();
    }

    @Test
    public void doGetAll() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        uc.getUsers(client);

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = "{\"name\": \"name_test7\" , \"surname\" : \"surname_test7\" , \"address\" : \"address_test7\" , " +
                "\"city\" : \"city_test7\"}";
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        uc.updateUser(client, added.getId(), "name_test7", "surname_test7", "address_updated",
                "city_updated");

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        uc.deleteUser(client, "");

        client.close();
    }

    @Test
    public void doDelete2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = "{\"name\": \"name_test8\" , \"surname\" : \"surname_test8\" , \"address\" : \"address_test8\" , " +
                "\"city\" : \"city_test8\"}";
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        uc.deleteUser(client, added.getId());

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
