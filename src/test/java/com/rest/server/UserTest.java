package com.rest.server;

import com.revolut.rest.data.DataMemory;
import com.revolut.rest.model.User;
import com.revolut.rest.server.ServerRest;
import com.revolut.rest.server.constants.NameResources;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

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

        int size1 = DataMemory.users.size();
        String userJson = cg.buildJsonUser("test_name", "surname_test", "address_test", "city_test");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        int size2 = DataMemory.users.size();
        assert(size2 > size1);

        assertEquals(added.getName(), "test_name");
        assertEquals(added.getSurname(), "surname_test");
        assertEquals(added.getAddress(), "address_test");
        assertEquals(added.getCity(), "city_test");

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        int size1 = DataMemory.users.size();
        String userJson = cg.buildJsonUser("test_name9", "surname_test9", "address_test9", "city_test9");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        int size2 = DataMemory.users.size();
        assert(size2 > size1);
        User user2 = cg.getElement(client, added.getId(), NameResources.USERS, User.class);

        assertEquals(added.getName(), user2.getName());
        assertEquals(added.getSurname(), user2.getSurname());
        assertEquals(added.getCity(), user2.getCity());
        assertEquals(added.getAddress(), user2.getAddress());

        client.close();
    }

    @Test
    public void doGetAll() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        int size1 = DataMemory.users.size();
        User[] users = cg.getElements(client, NameResources.USERS, User[].class);
        int size2 = users.length;
        assertEquals(size1, size2);

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("name_test7", "surname_test7", "address_test7", "city_test7");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        String userUpdateJson = cg.buildJsonUpdateUser(added.getId(), "name_test7", "surname_test7", "address_updated",
                "city_updated");
        User user = cg.update(client, userUpdateJson, NameResources.USERS, User.class);

        assertEquals(user.getName(), "name_test7");
        assertEquals(user.getSurname(), "surname_test7");
        assertEquals(user.getAddress(), "address_updated");
        assertEquals(user.getCity(), "city_updated");

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        int size1 = DataMemory.users.size();
        cg.delete(client, "", NameResources.USERS);
        int size2 = DataMemory.users.size();
        assertEquals(size1, size2);

        client.close();
    }

    @Test
    public void doDelete2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("name_test8", "surname_test8", "address_test8", "city_test8");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        int size1 = DataMemory.users.size();
        cg.delete(client, added.getId(), NameResources.USERS);
        int size2 = DataMemory.users.size();
        assert(size2 < size1);

        User deleted = cg.getElement(client, added.getId(), NameResources.USERS, User.class);
        assertNull(deleted);

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
