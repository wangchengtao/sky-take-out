package com.summer.task;

import com.summer.websocket.WebSocketServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WebSocketTask {

    private final WebSocketServer webSocketServer;

    public WebSocketTask(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessageToClient() {
        webSocketServer.sendToAllClient("这是来自服务端的消息..." + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
    }
}
