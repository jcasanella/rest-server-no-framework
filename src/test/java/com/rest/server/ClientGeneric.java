package com.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.model.Account;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientGeneric {
    private final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

    final protected <T> T add(CloseableHttpClient client, String json, String resource, Class<T> valueType) throws IOException {
        HttpPost httpPost = new HttpPost(uriContext + "/" + resource);

        StringEntity accEntity = new StringEntity(json);
        httpPost.setEntity(accEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse accountResponse = client.execute(httpPost);
        int statusCode2 = accountResponse.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String accountBodyAsString = EntityUtils.toString(accountResponse.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(accountBodyAsString, valueType);
    }

    final protected <T> T getElement(CloseableHttpClient client, String id, String resource, Class<T> typeValue) throws IOException {
        HttpGet httpGet2 = new HttpGet(uriContext + "/" + resource + "/" + id);

        HttpResponse response2 = client.execute(httpGet2);
        int statusCode2 = response2.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String bodyAsString2 = EntityUtils.toString(response2.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString2, typeValue);
    }

    final protected <T> T getElements(CloseableHttpClient client, String resource, Class<T> typeValue) throws IOException {
        HttpGet httpGet2 = new HttpGet(uriContext + "/" + resource);

        HttpResponse response2 = client.execute(httpGet2);
        int statusCode2 = response2.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String bodyAsString2 = EntityUtils.toString(response2.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString2, typeValue);
    }
}
