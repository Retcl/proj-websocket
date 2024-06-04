package ltd.jellyfish.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import jakarta.websocket.Session;

@Component
public class MapResource {

    @Bean
    public Map<String, Session> sessionMap(){
        return new HashMap<>();
    }
}
