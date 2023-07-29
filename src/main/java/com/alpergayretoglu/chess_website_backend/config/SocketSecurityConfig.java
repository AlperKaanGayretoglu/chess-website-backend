package com.alpergayretoglu.chess_website_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/secured/**").permitAll() // TODO: Temporarily permits all but should be changed to authenticated
                .anyMessage().permitAll(); // TODO: Temporarily permits all but should be changed to authenticated
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}