package com.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.model.Account;
import com.revolut.rest.server.constants.NameResources;
import com.revolut.rest.server.constants.StatusCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientGeneric {
    private final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

    final public String buildJsonUser(String name, String surname, String address, String city) {
        return String.format("{\"name\": \"%s\" , \"surname\" : \"%s\" , \"address\" : \"%s\" , \"city\" : \"%s\"}",
                name, surname, address, city);
    }

    final public String buildJsonAccount(String idAccount) {
        return String.format("{ \"userId\": \"%s\", \"balance\": \"0\"}", idAccount);
    }

    final public String buildJsonUserPayments(String srcAccountId, BigDecimal quantity, String trgAccountId) {
        return String.format("{\"srcAccountId\": \"%s\", \"quantity\": \"%s\", \"trgAccountId\": \"%s\"}",
                srcAccountId, quantity.setScale(3).toPlainString(), trgAccountId);
    }

    final public String buildJsonUpdateAccount(String accountId, BigDecimal quantity) {
        return String.format("{ \"id\": \"%s\", \"balance\": \"%s\"}", accountId, quantity.setScale(3).toPlainString());
    }

    final public String buildJsonUpdateUser(String id, String name, String surname, String address, String city) {
        return String.format("{\"id\": \"%s\", \"name\": \"%s\", \"surname\": \"%s\", \"address\": \"%s\", \"city\": \"%s\"}",
                id, name, surname, address, city);
    }

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

    final public boolean delete(CloseableHttpClient client, String id, String resource) throws IOException {
        HttpDelete httpDelete2 = new HttpDelete(uriContext + "/" + resource + "/" + id);

        HttpResponse response2 = client.execute(httpDelete2);
        int statusCode2 = response2.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String bodyAsString2 = EntityUtils.toString(response2.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString2, Boolean.class);
    }

    final protected <T> T update(CloseableHttpClient client, String json, String resource,  Class<T> typeName) throws IOException {
        HttpPut httpPut = new HttpPut(uriContext + "/" + resource);

        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        HttpResponse response3 = client.execute(httpPut);
        int statusCode = response3.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString3 = EntityUtils.toString(response3.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString3, typeName);
    }
}
