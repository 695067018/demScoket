package com.example.demo.config;

import com.example.demo.constants.GlobalConsts;
import com.example.demo.demo.AuthHandshakeInterceptor;
import com.example.demo.demo.MyPrincipalHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * @author 吴
 * @create 2019/1/7 - 16:07
 */
@Configuration
@EnableWebSocketMessageBroker// 此注解表示使用STOMP协议来传输基于消息代理的消息，此时可以在@Controller类中使用@MessageMapping
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private MyPrincipalHandshakeHandler myDefaultHandshakeHandler;

    @Autowired
    private AuthHandshakeInterceptor sessionAuthHandshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /**
         * 配置消息代理
         * 启动简单Broker，消息的发送的地址符合配置的前缀来的消息才发送到这个broker
         */
        // 订阅Broker名称，这样使用@SendTo默认消息目的地前缀要加上/topic
        config.enableSimpleBroker("/topic");
        // 全局使用的消息前缀（客户端订阅路径上会体现出来），这样js页面访问controller的@MessageMapping默认前缀加上“/app”
        config.setApplicationDestinationPrefixes(GlobalConsts.APP_PREFIX);
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        //config.setUserDestinationPrefix("/user/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 注册 Stomp的端点
         * addEndpoint：添加STOMP协议的端点。这个HTTP URL是供WebSocket或SockJS客户端访问的地址
         * withSockJS：指定端点使用SockJS协议
         */
        // 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        // 在网页上我们就可以通过这个链接
        // http://localhost:8080/gs-guide-websocket
        // 来和服务器的WebSocket连接
        registry.addEndpoint(GlobalConsts.ENDPOINT)
//                .addInterceptors(sessionAuthHandshakeInterceptor)
//                .setHandshakeHandler(myDefaultHandshakeHandler)
                .withSockJS();
    }

//    /**
//     * 消息传输参数配置（可选）
//     */
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
//        registry.setMessageSizeLimit(8192) //设置消息字节数大小
//                .setSendBufferSizeLimit(8192)//设置消息缓存大小
//                .setSendTimeLimit(10000); //设置消息发送时间限制毫秒
//    }

//    /**
//     * 输入通道参数设置（可选）
//     */
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.taskExecutor().corePoolSize(4) //设置消息输入通道的线程池线程数
//                .maxPoolSize(8)//最大线程数
//                .keepAliveSeconds(60);//线程活动时间
////        registration.setInterceptors(presenceChannelInterceptor());
//    }

}
