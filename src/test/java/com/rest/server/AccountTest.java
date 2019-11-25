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

    private AccountClient ac = new AccountClient();

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

        String userJson = "{\"name\": \"nameTest\" , \"surname\" : \"surnameTest\" , \"address\" : \"addressTest\" , " +
                "\"city\" : \"cityTest\"}";
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        ac.addAccount(client, added.getId());

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = "{\"name\": \"nameTest5\" , \"surname\" : \"surnameTest5\" , \"address\" : \"addressTest5\" , " +
                "\"city\" : \"cityTest5\"}";
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        ac.addAccount(client, added.getId());

        Account[] accounts = ac.getAccounts(client);
        ac.getAccount(client, accounts[0].getId());

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        Account[] accounts = ac.getAccounts(client);
        ac.deleteAccount(client, accounts[0].getId());

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = "{\"name\": \"nameTest2\" , \"surname\" : \"surnameTest2\" , \"address\" : \"addressTest2\" , " +
                "\"city\" : \"cityTest2\"}";
        User added = cg.add(client, userJson, NameResources.USERS, User.class);
        Account account = ac.addAccount(client, added.getId());
        ac.updateAccount(client, account.getId(), new BigDecimal(2000));

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
