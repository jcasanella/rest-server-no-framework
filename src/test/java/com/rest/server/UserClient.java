package com.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.model.User;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.constants.StatusCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserClient {

    private final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

    final protected User getUser(CloseableHttpClient client, String userId) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS + "/" + userId);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, User.class);
    }

    final protected User[] getUsers(CloseableHttpClient client) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, User[].class);
    }

    final protected boolean updateUser(CloseableHttpClient client, String id, String name, String surname, String address, String city) throws IOException {
        HttpPut httpPut = new HttpPut(uriContext + "/" + NameResources.USERS);

        String json = String.format("{\"id\": \"%s\", \"name\": \"%s\", \"surname\": \"%s\", \"address\": \"%s\", \"city\": \"%s\"}",
                id, name, surname, address, city);
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(httpPut);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, Boolean.class);
    }

    final protected boolean deleteUser(CloseableHttpClient client, String id) throws IOException {
        HttpDelete httpDelete = new HttpDelete(uriContext + "/" + NameResources.USERS + "/" + id);

        HttpResponse response = client.execute(httpDelete);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, Boolean.class);
    }
}
