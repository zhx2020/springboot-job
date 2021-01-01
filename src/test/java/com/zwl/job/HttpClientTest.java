package com.zwl.job;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import java.io.IOException;

public class HttpClientTest {

    @Test
    public void testGet() throws IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("https://getman.cn/mock/11");
        client.executeMethod(method);
        System.out.println(method.getStatusLine());
        System.out.println(method.getResponseBodyAsString());
        method.releaseConnection();
    }
}
