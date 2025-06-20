package com.summer.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.summer.properties.WeChatProperties;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class WeChatPayUtil {

    public static final String JSAPI = "https://api.mch.weixin.qq.com/v3/pay/transaction/jsapi";

    public static final String REFUNDS = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

    @Autowired
    private WeChatProperties weChatProperties;

    public JSONObject pay(String orderNum, BigDecimal total, String description, String openid) throws Exception {
        String bodyAsString = jsapi(orderNum, total, description, openid);

        JSONObject jsonObject = JSON.parseObject(bodyAsString);
        System.out.println(jsonObject);

        String prepayId = jsonObject.getString("prepay_id");

        if (prepayId != null) {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String nonceStr = RandomStringUtils.randomNumeric(32);

            List<Object> list = new ArrayList<>();
            list.add(timestamp);
            list.add(nonceStr);
            list.add("prepay_id=" + prepayId);

            StringBuilder sb = new StringBuilder();
            for (Object o : list) {
                sb.append(o).append("\n");
            }

            String signMessage = sb.toString();
            byte[] bytes = signMessage.getBytes(StandardCharsets.UTF_8);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(PemUtil.loadPrivateKey(new FileInputStream(new File(weChatProperties.getPrivateKeyFilePath()))));
            signature.update(bytes);
            String sign = Base64.getEncoder().encodeToString(signature.sign());


            JSONObject o = new JSONObject();
            o.put("timestamp", timestamp);
            o.put("nonceStr", nonceStr);
            o.put("package", "prepay_id=" + prepayId);
            o.put("signType", "RSA");
            o.put("paySign", sign);

            return o;
        }

        return jsonObject;
    }

    private String jsapi(String orderNum, BigDecimal total, String description, String openid) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", weChatProperties.getAppid());
        jsonObject.put("mchid", weChatProperties.getMchid());
        jsonObject.put("description", description);
        jsonObject.put("out_trade_no", orderNum);
        jsonObject.put("notify_url", weChatProperties.getNotifyUrl());

        JSONObject amount = new JSONObject();
        amount.put("total", total.multiply(new BigDecimal(100).setScale(0, RoundingMode.HALF_UP)).intValue());
        amount.put("currency", "CNY");

        jsonObject.put("amount", amount);

        JSONObject payer = new JSONObject();
        payer.put("openid", openid);
        jsonObject.put("payer", payer);

        String body = jsonObject.toJSONString();

        return post(JSAPI, body);
    }

    private String post(String url, String body) throws Exception {
        CloseableHttpClient httpClient = getClient();

        HttpPost post = new HttpPost(url);
        post.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        post.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        post.addHeader("Wechatpay-Serial", weChatProperties.getMchSerialNo());
        post.setEntity(new StringEntity(body, StandardCharsets.UTF_8.toString()));

        return httpClient.execute(post, response -> {
            try {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                return bodyAsString;
            } finally {
                httpClient.close();
            }
        });
    }

    private CloseableHttpClient getClient() {

        try {
            // 加载商户私钥
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream(new File(weChatProperties.getPrivateKeyFilePath())));
            // 加载平台证书
            X509Certificate x509Certificate = PemUtil.loadCertificate(new FileInputStream(new File(weChatProperties.getWeChatPayCertFilePath())));
            List<X509Certificate> list = Arrays.asList(x509Certificate);

            CloseableHttpClient client = WechatPayHttpClientBuilder.create()
                    .withMerchant(weChatProperties.getMchid(), weChatProperties.getMchSerialNo(), merchantPrivateKey)
                    .withWechatPay(list)
                    .build();

            return client;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String refund(String outTradeNo, String outRefundNo, BigDecimal refund, BigDecimal total) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_trade_no", outTradeNo);
        jsonObject.put("out_refund_no", outRefundNo);

        JSONObject amount = new JSONObject();
        amount.put("total", total.multiply(new BigDecimal(100)).intValue());
        amount.put("refund", refund.multiply(new BigDecimal(100)).intValue());
        amount.put("currency", "CNY");

        jsonObject.put("amount", amount);
        jsonObject.put("notify_url", weChatProperties.getRefundNotifyUrl());

        String body = jsonObject.toJSONString();

        return post(REFUNDS, body);
    }


}
