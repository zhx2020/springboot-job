package com.zwl.job.listener;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QueueConsumerListener {

    //queue模式的消费者
    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListener")
    public void readActiveQueue(String message) throws IOException {
        System.out.println("queue接收到：" + message);
        if ("0".equals(message)) {
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod("http://localhost:8080/config/stop");
            client.executeMethod(method);
            System.out.println(method.getStatusLine());
            System.out.println(method.getResponseBodyAsString());
            method.releaseConnection();
        }
    }
}
