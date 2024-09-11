package com.kedu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.kedu.handlers.AlarmHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final AlarmHandler alarmHandler;

    public WebSocketConfig(AlarmHandler alarmHandler) {
        this.alarmHandler = alarmHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(alarmHandler, "/alarm")
                .setAllowedOrigins("*");  // 허용할 도메인 설정 (모든 도메인 허용)
    }
}
