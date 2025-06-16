package com.summer.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class HttpClientUtil {

    public static final int TIMEOUT_MSEC = 5000;

    public static String doGet(String url, Map<String, String> paramMap) {

        CloseableHttpClient client = HttpClients.createDefault();

        String result = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);

            if (paramMap != null) {
                paramMap.forEach(builder::addParameter);
            }

            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);
            response = client.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
