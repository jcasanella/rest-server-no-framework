package com.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.rest.model.UserPayment;
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
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserPaymentClient {

    private final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

    final protected UserPayment addUserPayment(CloseableHttpClient client, String srcAccountId, BigDecimal quantity,
                                               String trgAccountId) throws IOException {

        HttpPost httpPost = new HttpPost(uriContext + "/" + NameResources.USERS_PAYMENTS);

        String accJson = String.format("{\"srcAccountId\": \"%s\", \"quantity\": \"%s\", \"trgAccountId\": \"%s\"}",
                srcAccountId, quantity.setScale(3).toPlainString(), trgAccountId);
        StringEntity accEntity = new StringEntity(accJson);
        httpPost.setEntity(accEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse accountResponse = client.execute(httpPost);
        String accountBodyAsString = EntityUtils.toString(accountResponse.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        UserPayment accountRet = objectMapper.readValue(accountBodyAsString, UserPayment.class);

        return accountRet;
    }

    final protected UserPayment getUserPaymentById(CloseableHttpClient client, String id) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS_PAYMENTS + "/" + id);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        UserPayment userRet = objectMapper.readValue(bodyAsString, UserPayment.class);

        return userRet;
    }

    final protected UserPayment[] getAllUserPayments(CloseableHttpClient client) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS_PAYMENTS);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        UserPayment[] userRet = objectMapper.readValue(bodyAsString, UserPayment[].class);

        return userRet;
    }

    final protected UserPayment[] getUserPaymentsByUsr(CloseableHttpClient client, String srcTrg, String userId) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS_PAYMENTS + "/" + srcTrg + "/" + userId);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
        assertThat(bodyAsString, notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        UserPayment[] userRet = objectMapper.readValue(bodyAsString, UserPayment[].class);

        return userRet;
    }
}
