package com.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.model.User;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.constants.StatusCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class UserActions {

    public static final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

    final protected User addUser(CloseableHttpClient client, String name, String surname, String address, String city) throws IOException {
        HttpPost httpPost = new HttpPost(uriContext + "/" + NameResources.USERS);

        String userJson = String.format("{\"name\": \"%s\" , \"surname\" : \"%s\" , \"address\" : \"%s\" , \"city\" : \"%s\"}",
                name, surname, address, city);
        StringEntity userEntity = new StringEntity(userJson);
        httpPost.setEntity(userEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse userResponse = client.execute(httpPost);

        String userBodyAsString = EntityUtils.toString(userResponse.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        User userRet = objectMapper.readValue(userBodyAsString, User.class);

        return userRet;
    }

    final protected User getUser(CloseableHttpClient client, String userId) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS + "/" + userId);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        User userRet = objectMapper.readValue(bodyAsString, User.class);

        return userRet;
    }

    final protected User[] getUsers(CloseableHttpClient client) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        User[] userRet = objectMapper.readValue(bodyAsString, User[].class);

        return userRet;
    }
}
