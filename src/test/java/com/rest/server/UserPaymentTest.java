package com.rest.server;

import com.revolut.rest.model.Account;
import com.revolut.rest.model.User;
import com.revolut.rest.model.UserPayment;
import com.revolut.rest.server.ServerRest;
import com.revolut.rest.server.constants.NameResources;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

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
        UserPayment userPayment = upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(300),
                acc2Updated.getId());

        // Check status operation
        Account acc1After = ac.getAccount(client, acc1Updated.getId());
        Account acc2After = ac.getAccount(client, acc2Updated.getId());

        System.out.println(acc1After);
        System.out.println(acc2After);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Create 2 users
        User user1 = uc.addUser(client, "user1A_name", "user1A_surname", "user1A_address",
                "user1A_city");
        User user2 = uc.addUser(client, "user2A_name", "user2A_surname", "user2A_address",
                "user2A_city");

        // Create 2 accounts
        Account account1 = ac.addAccount(client, user1.getId());
        Account account2 = ac.addAccount(client, user2.getId());

        // Add money to the accounts
        Account acc1Updated = ac.updateAccount(client, account1.getId(), new BigDecimal(800));
        Account acc2Updated = ac.updateAccount(client, account2.getId(), new BigDecimal(200));

        // Do transaction
        upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(100), acc2Updated.getId());
        upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(300), acc2Updated.getId());
        upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(50), acc2Updated.getId());

        UserPayment[] userPayments = upc.getAllUserPayments(client);
        Arrays.stream(userPayments)
                .forEach(System.out::println);

        UserPayment searchUserPayment = upc.getUserPaymentById(client, userPayments[0].getId());
        System.out.println(searchUserPayment);

        client.close();
    }

    @Test
    public void doGet2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Create 2 users
        User user1 = uc.addUser(client, "user1B_name", "user1B_surname", "user1B_address",
                "user1B_city");
        User user2 = uc.addUser(client, "user2B_name", "user2B_surname", "user2B_address",
                "user2B_city");
        User user3 = uc.addUser(client, "user3B_name", "user3B_surname", "user3B_address",
                "user3B_city");
        User user4 = uc.addUser(client, "user4B_name", "user4B_surname", "user4B_address",
                "user4B_city");
        User user5 = uc.addUser(client, "user5B_name", "user5B_surname", "user5B_address",
                "user5B_city");

        // Create 2 accounts
        Account account1 = ac.addAccount(client, user1.getId());
        Account account2 = ac.addAccount(client, user2.getId());
        Account account3 = ac.addAccount(client, user3.getId());
        Account account4 = ac.addAccount(client, user4.getId());
        Account account5 = ac.addAccount(client, user5.getId());

        // Add money to the accounts
        Account acc1Updated = ac.updateAccount(client, account1.getId(), new BigDecimal(800));
        Account acc2Updated = ac.updateAccount(client, account2.getId(), new BigDecimal(200));
        Account acc3Updated = ac.updateAccount(client, account3.getId(), new BigDecimal(700));
        Account acc4Updated = ac.updateAccount(client, account4.getId(), new BigDecimal(800));
        Account acc5Updated = ac.updateAccount(client, account5.getId(), new BigDecimal(900));

        // Do transaction
        upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(100), acc2Updated.getId());
        upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(300), acc2Updated.getId());
        upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(50), acc2Updated.getId());
        upc.addUserPayment(client, acc2Updated.getId(), new BigDecimal(50), acc1Updated.getId());
        upc.addUserPayment(client, acc1Updated.getId(), new BigDecimal(100), acc3Updated.getId());
        upc.addUserPayment(client, acc3Updated.getId(), new BigDecimal(100), acc1Updated.getId());
        upc.addUserPayment(client, acc3Updated.getId(), new BigDecimal(100), acc4Updated.getId());
        upc.addUserPayment(client, acc4Updated.getId(), new BigDecimal(100), acc5Updated.getId());

        UserPayment[] userPayments = upc.getAllUserPayments(client);

        UserPayment[] usersSrc = upc.getUserPaymentsByUsr(client, NameResources.USERS_PAYMENTS_SRC,
                userPayments[0].getId());
        Arrays.stream(usersSrc)
                .forEach(System.out::println);

        UserPayment[] usersTrg = upc.getUserPaymentsByUsr(client, NameResources.USERS_PAYMENT_TRG,
                userPayments[0].getId());
        Arrays.stream(usersTrg)
                .forEach(System.out::println);

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
