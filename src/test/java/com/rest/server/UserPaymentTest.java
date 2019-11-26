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

    private UserPaymentClient upc = new UserPaymentClient();
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

        // Create 2 users
        String json1 = cg.buildJsonUser("user1_name", "user1_surname", "user1_address", "user1_city");
        User user1 = cg.add(client, json1, NameResources.USERS, User.class);
        String json2 = cg.buildJsonUser("user2_name", "user2_surname", "user2_address", "user2_city");
        User user2 = cg.add(client, json2, NameResources.USERS, User.class);

        // Create 2 accounts
        String accountJson1 = cg.buildJsonAccount(user1.getId());
        Account account1 = cg.add(client, accountJson1, NameResources.ACCOUNTS, Account.class);
        String accountJson2 = cg.buildJsonAccount(user2.getId());
        Account account2 = cg.add(client, accountJson2, NameResources.ACCOUNTS, Account.class);

        // Add money to the accounts
        String jsonUpd1 = cg.buildJsonUpdateAccount(account1.getId(), new BigDecimal(500));
        Account acc1Updated = cg.update(client, jsonUpd1, NameResources.ACCOUNTS, Account.class);
        String jsonUpd2 = cg.buildJsonUpdateAccount(account2.getId(), new BigDecimal(600));
        Account acc2Updated = cg.update(client, jsonUpd2, NameResources.ACCOUNTS, Account.class);

        // Do transaction
        String userPaymentJson = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(300),
                acc2Updated.getId());
        UserPayment userPayment = cg.add(client, userPaymentJson, NameResources.USERS_PAYMENTS, UserPayment.class);

        // Check status operation
        Account acc1After = cg.getElement(client, acc1Updated.getId(), NameResources.ACCOUNTS, Account.class);
        Account acc2After = cg.getElement(client, acc2Updated.getId(), NameResources.ACCOUNTS, Account.class);

        System.out.println(acc1After);
        System.out.println(acc2After);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Create 2 users
        String json1 = cg.buildJsonUser("user1A_name", "user1A_surname", "user1A_address", "user1A_city");
        User user1 = cg.add(client, json1, NameResources.USERS, User.class);
        String json2 = cg.buildJsonUser("user2A_name", "user2A_surname", "user2A_address", "user2A_city");
        User user2 = cg.add(client, json2, NameResources.USERS, User.class);

        // Create 2 accounts
        String jsonAcc1 = cg.buildJsonAccount(user1.getId());
        Account account1 = cg.add(client, jsonAcc1, NameResources.ACCOUNTS, Account.class);
        String jsonAcc2 = cg.buildJsonAccount(user2.getId());
        Account account2 = cg.add(client, jsonAcc2, NameResources.ACCOUNTS, Account.class);

        // Add money to the accounts
        String jsonUpd1 = cg.buildJsonUpdateAccount(account1.getId(), new BigDecimal(800));
        Account acc1Updated = cg.update(client, jsonUpd1, NameResources.ACCOUNTS, Account.class);
        String jsonUpd2 = cg.buildJsonUpdateAccount(account2.getId(), new BigDecimal(200));
        Account acc2Updated = cg.update(client, jsonUpd2, NameResources.ACCOUNTS, Account.class);

        // Do transaction
        String userPaymentJson = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(100), acc2Updated.getId());
        cg.add(client, userPaymentJson, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson2 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(100), acc2Updated.getId());
        cg.add(client, userPaymentJson2, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson3 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(50), acc2Updated.getId());
        cg.add(client, userPaymentJson3, NameResources.USERS_PAYMENTS, UserPayment.class);


        UserPayment[] userPayments = cg.getElements(client, NameResources.USERS_PAYMENTS, UserPayment[].class);
        Arrays.stream(userPayments)
                .forEach(System.out::println);

        UserPayment searchUserPayment = cg.getElement(client, userPayments[0].getId(), NameResources.USERS_PAYMENTS, UserPayment.class);
        System.out.println(searchUserPayment);

        client.close();
    }

    @Test
    public void doGet2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Create 2 users
        String json1 = cg.buildJsonUser("user1B_name", "user1B_surname", "user1B_address",
                "user1B_city");
        User user1 = cg.add(client, json1, NameResources.USERS, User.class);
        String json2 = cg.buildJsonUser("user2B_name", "user2B_surname", "user2B_address",
                "user2B_city");
        User user2 = cg.add(client, json2, NameResources.USERS, User.class);
        String json3 = cg.buildJsonUser("user3B_name", "user3B_surname", "user3B_address",
                "user3B_city");
        User user3 = cg.add(client, json3, NameResources.USERS, User.class);
        String json4 = cg.buildJsonUser("user4B_name", "user4B_surname", "user4B_address",
                "user4B_city");
        User user4 = cg.add(client, json4, NameResources.USERS, User.class);
        String json5 = cg.buildJsonUser("user5B_name", "user5B_surname", "user5B_address",
                "user5B_city");
        User user5 = cg.add(client, json5, NameResources.USERS, User.class);

        // Create 2 accounts
        String accJson1 = cg.buildJsonAccount(user1.getId());
        Account account1 = cg.add(client, accJson1, NameResources.ACCOUNTS, Account.class);
        String accJson2 = cg.buildJsonAccount(user2.getId());
        Account account2 = cg.add(client, accJson2, NameResources.ACCOUNTS, Account.class);
        String accJson3 = cg.buildJsonAccount(user3.getId());
        Account account3 = cg.add(client, accJson3, NameResources.ACCOUNTS, Account.class);
        String accJson4 = cg.buildJsonAccount(user4.getId());
        Account account4 = cg.add(client, accJson4, NameResources.ACCOUNTS, Account.class);
        String accJson5 = cg.buildJsonAccount(user5.getId());
        Account account5 = cg.add(client, accJson5, NameResources.ACCOUNTS, Account.class);

        // Add money to the accounts
        String jsonUpd1 = cg.buildJsonUpdateAccount(account1.getId(), new BigDecimal(800));
        Account acc1Updated = cg.update(client, jsonUpd1, NameResources.ACCOUNTS, Account.class);
        String  jsonUpd2 = cg.buildJsonUpdateAccount(account2.getId(), new BigDecimal(200));
        Account acc2Updated = cg.update(client, jsonUpd2, NameResources.ACCOUNTS, Account.class);
        String jsonUpd3 = cg.buildJsonUpdateAccount(account3.getId(), new BigDecimal(700));
        Account acc3Updated = cg.update(client, jsonUpd3, NameResources.ACCOUNTS, Account.class);
        String jsonUpd4 = cg.buildJsonUpdateAccount(account4.getId(), new BigDecimal(800));
        Account acc4Updated = cg.update(client, jsonUpd4, NameResources.ACCOUNTS, Account.class);
        String jsonUpd5 = cg.buildJsonUpdateAccount(account5.getId(), new BigDecimal(900));
        Account acc5Updated = cg.update(client, jsonUpd5, NameResources.ACCOUNTS, Account.class);

        // Do transaction
        String userPaymentJson1 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(100), acc2Updated.getId());
        cg.add(client, userPaymentJson1, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson2 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(300), acc2Updated.getId());
        cg.add(client, userPaymentJson2, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson3 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(50), acc2Updated.getId());
        cg.add(client, userPaymentJson3, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson4 = cg.buildJsonUserPayments(acc2Updated.getId(), new BigDecimal(50), acc1Updated.getId());
        cg.add(client, userPaymentJson4, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson5 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(100), acc3Updated.getId());
        cg.add(client, userPaymentJson5, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson6 = cg.buildJsonUserPayments(acc3Updated.getId(), new BigDecimal(100), acc1Updated.getId());
        cg.add(client, userPaymentJson6, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson7 = cg.buildJsonUserPayments(acc3Updated.getId(), new BigDecimal(100), acc4Updated.getId());
        cg.add(client, userPaymentJson7, NameResources.USERS_PAYMENTS, UserPayment.class);
        String userPaymentJson8 = cg.buildJsonUserPayments(acc4Updated.getId(), new BigDecimal(100), acc5Updated.getId());
        cg.add(client, userPaymentJson8, NameResources.USERS_PAYMENTS, UserPayment.class);

        UserPayment[] userPayments = cg.getElements(client, NameResources.USERS_PAYMENTS, UserPayment[].class);

        UserPayment[] usersSrc = upc.getUserPaymentsByCondit(client, NameResources.USERS_PAYMENTS_SRC,
                userPayments[0].getSrcAccountId());
        Arrays.stream(usersSrc)
                .forEach(System.out::println);

        UserPayment[] usersTrg = upc.getUserPaymentsByCondit(client, NameResources.USERS_PAYMENT_TRG,
                userPayments[0].getId());
        Arrays.stream(usersTrg)
                .forEach(System.out::println);

        client.close();
    }

    @Test
    public void doGet3() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Create 2 users
        String json1 = cg.buildJsonUser("user1C_name", "user1C_surname", "user1C_address",
                "user1C_city");
        User user1 = cg.add(client, json1, NameResources.USERS, User.class);
        String json2 = cg.buildJsonUser("user2C_name", "user2C_surname", "user2C_address",
                "user2C_city");
        User user2 = cg.add(client, json2, NameResources.USERS, User.class);
        String json3 = cg.buildJsonUser("user3C_name", "user3C_surname", "user3C_address",
                "user3C_city");
        User user3 = cg.add(client, json3, NameResources.USERS, User.class);
        String json4 = cg.buildJsonUser("user4C_name", "user4C_surname", "user4C_address",
                "user4C_city");
        User user4 = cg.add(client, json4, NameResources.USERS, User.class);
        String json5 = cg.buildJsonUser("user5C_name", "user5C_surname", "user5C_address",
                "user5C_city");
        User user5 = cg.add(client, json5, NameResources.USERS, User.class);

        // Create 2 accounts
        String jsonAccount1 = cg.buildJsonAccount(user1.getId());
        Account account1 = cg.add(client, jsonAccount1, NameResources.ACCOUNTS, Account.class);
        String jsonAccount2 = cg.buildJsonAccount(user2.getId());
        Account account2 = cg.add(client, jsonAccount2, NameResources.ACCOUNTS, Account.class);
        String jsonAccount3 = cg.buildJsonAccount(user3.getId());
        Account account3 = cg.add(client, jsonAccount3, NameResources.ACCOUNTS, Account.class);
        String jsonAccount4 = cg.buildJsonAccount(user4.getId());
        Account account4 = cg.add(client, jsonAccount4, NameResources.ACCOUNTS, Account.class);
        String jsonAccount5 = cg.buildJsonAccount(user5.getId());
        Account account5 = cg.add(client, jsonAccount5, NameResources.ACCOUNTS, Account.class);

        // Add money to the accounts
        String jsonUpd1 = cg.buildJsonUpdateAccount(account1.getId(), new BigDecimal(800));
        Account acc1Updated = cg.update(client, jsonUpd1, NameResources.ACCOUNTS, Account.class);
        String jsonUpd2 = cg.buildJsonUpdateAccount(account2.getId(), new BigDecimal(200));
        Account acc2Updated = cg.update(client, jsonUpd2, NameResources.ACCOUNTS, Account.class);
        String jsonUpd3 = cg.buildJsonUpdateAccount(account3.getId(), new BigDecimal(700));
        Account acc3Updated = cg.update(client, jsonUpd3, NameResources.ACCOUNTS, Account.class);
        String jsonUpd4 = cg.buildJsonUpdateAccount(account4.getId(), new BigDecimal(800));
        Account acc4Updated = cg.update(client, jsonUpd4, NameResources.ACCOUNTS, Account.class);
        String jsonUpd5 = cg.buildJsonUpdateAccount(account5.getId(), new BigDecimal(900));
        Account acc5Updated = cg.update(client, jsonUpd5, NameResources.ACCOUNTS, Account.class);

        // Do transaction
        String accJson1 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(100), acc2Updated.getId());
        cg.add(client, accJson1, NameResources.USERS_PAYMENTS, UserPayment.class);
        String accJson2 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(300), acc2Updated.getId());
        cg.add(client, accJson2, NameResources.USERS_PAYMENTS, UserPayment.class);
        String accJson3 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(50), acc2Updated.getId());
        cg.add(client, accJson3, NameResources.USERS_PAYMENTS, UserPayment.class);
        String accJson4 = cg.buildJsonUserPayments(acc2Updated.getId(), new BigDecimal(50), acc1Updated.getId());
        cg.add(client, accJson4, NameResources.USERS_PAYMENTS, UserPayment.class);
        String accJson5 = cg.buildJsonUserPayments(acc1Updated.getId(), new BigDecimal(100), acc3Updated.getId());
        cg.add(client, accJson5, NameResources.USERS_PAYMENTS, UserPayment.class);
        String accJson6 = cg.buildJsonUserPayments(acc3Updated.getId(), new BigDecimal(100), acc1Updated.getId());
        cg.add(client, accJson6, NameResources.USERS_PAYMENTS, UserPayment.class);
        String accJson7 = cg.buildJsonUserPayments(acc3Updated.getId(), new BigDecimal(100), acc4Updated.getId());
        cg.add(client, accJson7, NameResources.USERS_PAYMENTS, UserPayment.class);
        String accJson8 = cg.buildJsonUserPayments(acc4Updated.getId(), new BigDecimal(100), acc5Updated.getId());
        cg.add(client, accJson8, NameResources.USERS_PAYMENTS, UserPayment.class);

        UserPayment[] usersSrc = upc.getUserPaymentsByCondit(client, NameResources.USERS_PAYMENT_USER_SRC,
                user1.getId());
        Arrays.stream(usersSrc)
                .forEach(System.out::println);

        UserPayment[] usersTrg = upc.getUserPaymentsByCondit(client, NameResources.USERS_PAYMENT_USER_TRG,
                user3.getId());
        Arrays.stream(usersTrg)
                .forEach(System.out::println);

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
