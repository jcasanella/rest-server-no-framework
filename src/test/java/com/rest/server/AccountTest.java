package com.rest.server;

import com.revolut.rest.data.DataMemory;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

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

        int size1 = DataMemory.accounts.size();

        String accountJson = cg.buildJsonAccount(added.getId());
        Account ac = cg.add(client, accountJson, NameResources.ACCOUNTS, Account.class);

        int size2 = DataMemory.accounts.size();

        assertEquals(ac.getUserId(), added.getId());
        assertEquals(ac.getBalance(), new BigDecimal(0));
        assertNotNull(ac.getDateCreation());
        assert(size1 < size2);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String userJson = cg.buildJsonUser("nameTest5", "surnameTest5", "addressTest5", "cityTest5");
        User added = cg.add(client, userJson, NameResources.USERS, User.class);

        String accountJson = cg.buildJsonAccount(added.getId());
        cg.add(client, accountJson, NameResources.ACCOUNTS, Account.class);

        int size1 = DataMemory.accounts.size();
        Account[] accounts = cg.getElements(client, NameResources.ACCOUNTS, Account[].class);
        int size2 = accounts.length;
        assertEquals(size1, size2);

        Account ac = cg.getElement(client, accounts[0].getId(), NameResources.ACCOUNTS, Account.class);
        assertEquals(ac, accounts[0]);

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        int size1 = DataMemory.accounts.size();
        Account[] accounts = cg.getElements(client, NameResources.ACCOUNTS, Account[].class);
        cg.delete(client, accounts[0].getId(), NameResources.ACCOUNTS);
        int size2 = DataMemory.accounts.size();

        assert(size2 < size1);

        Account acc = cg.getElement(client, accounts[0].getId(), NameResources.ACCOUNTS, Account.class);
        assertNull(acc);

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
        Account acc = cg.update(client, jsonUpdate, NameResources.ACCOUNTS, Account.class);

        assertEquals(acc.getDateCreation(), account.getDateCreation());
        assertEquals(acc.getId(), account.getId());
        assertEquals(acc.getUserId(), account.getUserId());

        String bal1 = account.getBalance().add(new BigDecimal(2000)).setScale(3).toPlainString();
        String bal2 = acc.getBalance().setScale(3).toPlainString();
        assertEquals(bal1, bal2);

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
