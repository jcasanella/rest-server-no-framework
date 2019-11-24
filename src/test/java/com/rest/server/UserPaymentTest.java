package com.rest.server;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;
import com.revolut.rest.model.UserPayment;
import com.revolut.rest.server.ServerRest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class UserPaymentTest {

    private UserClient uc = new UserClient();
    private AccountClient ac = new AccountClient();
    private UserPaymentClient upc = new UserPaymentClient();

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

        // Create 2 users
        User user1 = uc.addUser(client, "user1_name", "user1_surname", "user1_address",
                "user1_city");
        User user2 = uc.addUser(client, "user2_name", "user2_surname", "user2_address",
                "user2_city");

        // Create 2 accounts
        Account account1 = ac.addAccount(client, user1.getId());
        Account account2 = ac.addAccount(client, user2.getId());

        // Add money to the accounts
        Account acc1Updated = ac.updateAccount(client, account1.getId(), new BigDecimal(500));
        Account acc2Updated = ac.updateAccount(client, account2.getId(), new BigDecimal(600));

        // Do transaction
        UserPayment userPayment = upc.addUserAccount(client, acc1Updated.getId(), new BigDecimal(300),
                acc2Updated.getId());

        // Check status operation
        Account acc1After = ac.getAccount(client, acc1Updated.getId());
        Account acc2After = ac.getAccount(client, acc2Updated.getId());

        System.out.println(acc1After);
        System.out.println(acc2After);

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
