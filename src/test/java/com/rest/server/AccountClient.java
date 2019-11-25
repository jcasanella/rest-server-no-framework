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

public class AccountClient {

    private final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

    final protected Account addAccount(CloseableHttpClient client, String userId) throws IOException {
        HttpPost httpPost = new HttpPost(uriContext + "/" + NameResources.ACCOUNTS);

        String accJson = String.format("{ \"userId\": \"%s\", \"balance\": \"0\"}", userId);
        StringEntity accEntity = new StringEntity(accJson);
        httpPost.setEntity(accEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse accountResponse = client.execute(httpPost);
        int statusCode2 = accountResponse.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String accountBodyAsString = EntityUtils.toString(accountResponse.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(accountBodyAsString, Account.class);
    }

    final protected Account getAccount(CloseableHttpClient client, String accountId) throws IOException {
        HttpGet httpGet2 = new HttpGet(uriContext + "/" + NameResources.ACCOUNTS + "/" + accountId);

        HttpResponse response2 = client.execute(httpGet2);
        int statusCode2 = response2.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String bodyAsString2 = EntityUtils.toString(response2.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString2, Account.class);
    }

    final protected Account[] getAccounts(CloseableHttpClient client) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.ACCOUNTS);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, Account[].class);
    }

    final protected boolean deleteAccount(CloseableHttpClient client, String accountId) throws IOException {
        HttpDelete httpDelete2 = new HttpDelete(uriContext + "/" + NameResources.ACCOUNTS + "/" + accountId);

        HttpResponse response2 = client.execute(httpDelete2);
        int statusCode2 = response2.getStatusLine().getStatusCode();
        assertThat(statusCode2, equalTo(StatusCode.OK.getCode()));

        String bodyAsString2 = EntityUtils.toString(response2.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString2, Boolean.class);
    }

    final protected Account updateAccount(CloseableHttpClient client, String accountId, BigDecimal quantity) throws IOException {
        HttpPut httpPut = new HttpPut(uriContext + "/" + NameResources.ACCOUNTS);

        String accJson = String.format("{ \"id\": \"%s\", \"balance\": \"%s\"}", accountId, quantity.setScale(3).toPlainString());
        StringEntity entity = new StringEntity(accJson);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        HttpResponse response3 = client.execute(httpPut);
        int statusCode = response3.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString3 = EntityUtils.toString(response3.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString3, Account.class);
    }
}
