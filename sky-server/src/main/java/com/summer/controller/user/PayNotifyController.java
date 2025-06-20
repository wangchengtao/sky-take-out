package com.summer.controller.user;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.summer.properties.WeChatProperties;
import com.summer.service.OrderService;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;

@RestController
@RequestMapping("payNotify")
@Slf4j
public class PayNotifyController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeChatProperties weChatProperties;

    public void paySuccessNotity(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String body = readData(request);
        log.info("微信支付成功通知: {}", body);

        String plainText = decryptData(body);
        log.info("解密后的数据: {}", plainText);

        JSONObject jsonObject = JSON.parseObject(plainText);
        String outTradeNo = jsonObject.getString("out_trade_no");
        String transactionId = jsonObject.getString("transaction_id");

        log.info("订单号: {}, 微信订单号: {}", outTradeNo, transactionId);

        orderService.paySuccess(outTradeNo);

        responseToWeixin(response);
    }

    private void responseToWeixin(HttpServletResponse response) throws IOException {
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        response.setStatus(HttpStatus.SC_OK);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        map.put("message", "SUCCESS");

        response.getOutputStream().write(JSONUtils.toJSONString(map).getBytes(StandardCharsets.UTF_8));

        response.flushBuffer();
    }

    private String decryptData(String body) throws GeneralSecurityException {
        JSONObject resultObject = JSON.parseObject(body);
        JSONObject resource = resultObject.getJSONObject("resource");
        String ciphertext = resource.getString("ciphertext");
        String nonce = resource.getString("nonce");
        String associatedData = resource.getString("associated_data");

        AesUtil aesUtil = new AesUtil(weChatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        String plainText = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes(StandardCharsets.UTF_8), ciphertext);

        return plainText;

    }

    private String readData(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();

        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(line);
        }

        // reader.lines().forEach(line1 -> sb.append(line1));
        return sb.toString();
    }
}
