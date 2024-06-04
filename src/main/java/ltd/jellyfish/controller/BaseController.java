package ltd.jellyfish.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import ltd.jellyfish.models.BaseMessage;
import ltd.jellyfish.models.RewardMessage;

@Slf4j
@Component
@ServerEndpoint("/base")
public class BaseController {

    private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();
    private String ID;

    private static Map<String, Session> sessionMap;

    @Autowired
    public void setSessionMap(Map<String, Session> sessionMap) {
        this.sessionMap = sessionMap;
    }

    @OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
        this.ID = UUID.randomUUID().toString();
        sessionMap.put(ID, session);
        RewardMessage rewardMessage = new RewardMessage();
        rewardMessage.setFrom(ID);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setRewardMessage(rewardMessage);
        String message = ID + "\t 格式为：" + baseMessage.toString();
        session.getAsyncRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session seession) {
        SESSIONS.remove(seession);
    }

    @OnMessage
    public void onMessage(String message) {
        BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);
        if (baseMessage.getRewardMessage().getTo() == null ||baseMessage.getRewardMessage().getTo().isEmpty()) {
            sendReward(baseMessage);
        } else {
            sendMessage(message);
        }
    }

    public void sendMessage(String message){
        for (Session session : SESSIONS) {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }

    public void sendReward(BaseMessage baseMessage) {
        Session session = sessionMap.get(baseMessage.getRewardMessage().getTo());
        session.getAsyncRemote().sendText(baseMessage.getRewardMessage().getMessage());
    }
}
