package com.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.model.Account;
import com.revolut.rest.server.ServerRest;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.constants.StatusCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.io.IOException;

public class AccountTest {

    private String id = "test_test";
    private static final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

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
        HttpPost httpPost = new HttpPost(uriContext + "/" + NameResources.USERS);

        String userJson = "{\"name\": \"test\" , \"surname\" : \"test\" , \"address\" : \"aaaa\" , \"city\" : \"fffff\"}";
        StringEntity userEntity = new StringEntity(userJson);
        httpPost.setEntity(userEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse userResponse = client.execute(httpPost);

        HttpPost httpPost2 = new HttpPost(uriContext + "/" + NameResources.ACCOUNTS);

        String accJson = String.format("{ \"userId\": \"%s\", \"balance\": \"0\"}", id);
        StringEntity accEntity = new StringEntity(accJson);
        httpPost2.setEntity(accEntity);
        httpPost2.setHeader("Accept", "application/json");
        httpPost2.setHeader("Content-type", "application/json");

        CloseableHttpResponse accountResponse = client.execute(httpPost2);
        String account = EntityUtils.toString(accountResponse.getEntity());

        System.out.println(account);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.ACCOUNTS);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        Account[] accounts = objectMapper.readValue(bodyAsString, Account[].class);

        HttpGet httpGet2 = new HttpGet(uriContext + "/" + NameResources.ACCOUNTS + "/" + accounts[0].getId());

        HttpResponse response2 = client.execute(httpGet2);
        int statusCode2 = response.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String bodyAsString2 = EntityUtils.toString(response2.getEntity());
        System.out.println(bodyAsString2);
        assertThat(bodyAsString, notNullValue());

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.ACCOUNTS);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        Account[] accounts = objectMapper.readValue(bodyAsString, Account[].class);

        HttpDelete httpDelete2 = new HttpDelete(uriContext + "/" + NameResources.ACCOUNTS + "/" + accounts[0].getId());

        HttpResponse response2 = client.execute(httpDelete2);
        int statusCode2 = response2.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String bodyAsString2 = EntityUtils.toString(response2.getEntity());
        System.out.println(bodyAsString2);
        assertThat(bodyAsString2, notNullValue());

        client.close();
    }

    @Test
    public void doDelete2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(uriContext + "/" + NameResources.ACCOUNTS + "/" + id);

        HttpResponse response = client.execute(httpDelete);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        client.close();
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
