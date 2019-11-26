package com.rest.server;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;
import com.revolut.rest.server.ServerRest;
import com.revolut.rest.server.constants.NameResources;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.math.BigDecimal;

public class AccountTest {

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

        String userJson = cg.buildJsonUser("nameTest", "surnameTest", "addressTest", "cityTest");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        String accountJson = cg.buildJsonAccount(added.getId());
        cg.add(client, accountJson, NameResources.ACCOUNTS, Account.class);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("nameTest5", "surnameTest5", "addressTest5", "cityTest5");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        String accountJson = cg.buildJsonAccount(added.getId());
        cg.add(client, accountJson, NameResources.ACCOUNTS, Account.class);

        Account[] accounts = cg.getElements(client, NameResources.ACCOUNTS, Account[].class);
        cg.getElement(client, accounts[0].getId(), NameResources.ACCOUNTS, Account.class);

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        Account[] accounts = cg.getElements(client, NameResources.ACCOUNTS, Account[].class);
        cg.delete(client, accounts[0].getId(), NameResources.ACCOUNTS);

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("nameTest2", "surnameTest2", "addressTest2", "cityTest2");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        String accountJson = cg.buildJsonAccount(added.getId());
        Account account = cg.add(client, accountJson, NameResources.ACCOUNTS, Account.class);

        String jsonUpdate = cg.buildJsonUpdateAccount(account.getId(), new BigDecimal(2000));
        cg.update(client, jsonUpdate, NameResources.ACCOUNTS, Account.class);

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
