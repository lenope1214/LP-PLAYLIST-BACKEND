package kr.weareboard.lp.api.config.chat;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == StompCommand.SEND){
        }
        if (accessor.getCommand() == StompCommand.CONNECT) {
//
//            if (!"jmj-chatting".equals(authToken)) { // token 확인..
//                throw new AuthException("fail");
//            }
        }

        return message;
    }
}
