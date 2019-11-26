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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserPaymentClient {

    private final String uriContext = "http://localhost:8001/" + NameResources.VERSION;

    final protected UserPayment[] getUserPaymentsByCondit(CloseableHttpClient client, String srcTrg, String userId) throws IOException {
        HttpGet httpGet = new HttpGet(uriContext + "/" + NameResources.USERS_PAYMENTS + "/" + srcTrg + "/" + userId);

        HttpResponse response = client.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(StatusCode.OK.getCode()));

        String bodyAsString = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, UserPayment[].class);
    }
}
