package com.summer;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class HttpClientTest {

    @Test
    public void testGET() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");

        CloseableHttpResponse response = client.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("服务端返回的状态码为:" + statusCode);

        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity));

        response.close();
        client.close();
    }

    @Test
    public void testPOST() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost post = new HttpPost("http://localhost:8080/admin/auth/login");

        post.addHeader("Content-Type", "application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "admin");
        jsonObject.put("password", "123456");

        StringEntity entity = new StringEntity(jsonObject.toJSONString());
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("服务端返回的状态码为:" + statusCode);
        System.out.println(EntityUtils.toString(response.getEntity()));

        response.close();
        client.close();

    }
}
