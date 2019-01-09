package com.example.demo.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionMapService {
    private final ConcurrentHashMap<String,WebSocketSession> map = new ConcurrentHashMap<>();

    public void put(WebSocketSession session){
        map.put(session.getId(),session);
    }

    public WebSocketSession get(String id){
        return map.get(id);
    }

    public List<WebSocketSession> getAll(){
        return new ArrayList<>(map.values());
    }

}
