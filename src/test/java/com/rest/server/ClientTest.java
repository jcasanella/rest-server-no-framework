package com.rest.server;

import com.revolut.rest.server.ServerRest;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.constants.StatusCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;


public class ClientTest {

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

        String json = "{\"name\": \"test\" , \"surname\" : \"test\" , \"address\" : \"aaaa\" , \"city\" : \"fffff\"}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(StatusCode.OK.getCode()));

        id = EntityUtils.toString(response.getEntity());
        System.out.println(id);

        client.close();
    }

    @Test
    public void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS + "/" + id);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        client.close();
    }

    @Test
    public void doGetAll() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        client.close();
    }

    @Test
    public void doUpdate() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(uriContext + "/" + NameResources.USERS);

        String json = "{\"id\": \"test_test\", \"name\": \"test\" , \"surname\" : \"test\" , \"address\" : \"bbbbb\" , \"city\" : \"fffff\"}";
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(httpPut);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        client.close();
    }

    @Test
    public void doDelete() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(uriContext + "/" + NameResources.USERS);

        HttpResponse response = client.execute(httpDelete);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        client.close();
    }

    @Test
    public void doDelete2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(uriContext + "/" + NameResources.USERS + "/" + id);

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
