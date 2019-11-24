package com.rest.server;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;
import com.revolut.rest.server.ServerRest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.math.BigDecimal;

public class AccountTest extends AccountActions{

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

        User added = addUser(client, "nameTest", "surnameTest", "addressTest", "cityTest");
        addAccount(client, added.getId());

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        User added = addUser(client, "nameTest5", "surnameTest5", "addressTest5", "cityTest5");
        addAccount(client, added.getId());

        Account[] accounts = getAccounts(client);
        getAccount(client, accounts[0].getId());

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        Account[] accounts = getAccounts(client);
        deleteAccount(client, accounts[0].getId());

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        User added = addUser(client, "nameTest2", "surnameTest2", "addressTest", "cityTest");
        Account account = addAccount(client, added.getId());
        updateAccount(client, account.getId(), new BigDecimal(2000));

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
