package com.rest.server;

import com.revolut.test.server.ServerRest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;


public class ClientTest {

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
    public void firstTest() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(new HttpGet("http://localhost:8001/api/hello"));
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());
    }

    @AfterClass
    public static void stop() {
        ServerRest.getInstance().stop();
    }
}
