package ltd.jellyfish.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import ltd.jellyfish.models.BaseMessage;

@Slf4j
@Component
@ServerEndpoint("/base")
public class BaseController {

    private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();

    private static final Map<String, Session> SESSION_POOL = new HashMap<>();

    private String ID;

    @OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
        SESSION_POOL.put(UUID.randomUUID().toString(), session);
        this.ID = UUID.randomUUID().toString();
        session.getAsyncRemote().sendText(ID);
    }

    @OnClose
    public void onClose(Session seession) {
        SESSIONS.remove(seession);
    }

    @OnMessage
    public void onMessage(String message) {
        BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);
        log.info("UUID: " + baseMessage.getDeviceMessage().getDeviceId() + "\tdevice name: " + baseMessage.getDeviceMessage().getDeviceName());
        sendMessage(message);
    }

    public void sendMessage(String message){
        for (Session session : SESSIONS) {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }
}
