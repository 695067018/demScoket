package com.example.demo.contoller;

import com.example.demo.constants.GlobalConsts;
import com.example.demo.demo.ClientMessage;
import com.example.demo.demo.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author 吴
 * @create 2019/1/7 - 16:12
 */
@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate template;

    private final ConcurrentMap<String,Integer> userMap = new ConcurrentHashMap<>();

    @Autowired
    private HttpSession httpSession;

    @MessageMapping("/toAll")
    @SendTo(GlobalConsts.TOPIC)
    public ServerMessage toAll(ClientMessage message) throws Exception {
        // 模拟延时，以便测试客户端是否在异步工作
        Thread.sleep(1000);
        return new ServerMessage("Hello,world");
    }

    @MessageMapping("/user")
    @SendToUser(GlobalConsts.TOUSER)
    public ServerMessage user(ClientMessage message,@Header("simpSessionId") String sessionId) throws Exception {
        // 模拟延时，以便测试客户端是否在异步工作
        Thread.sleep(1000);
        System.out.println("userId:" + sessionId);
        userMap.putIfAbsent(sessionId,1);
        return new ServerMessage("Hello, " + HtmlUtils.htmlEscape(message.getName() + sessionId) + "!");
    }
//    @RequestMapping("/startStomp.do")
//    @ResponseBody
//    public String startStomp() {
//        final int counter = 10;
//        MoreExecutors.newDirectExecutorService().submit(() -> {
//            int index = 0;
//            while (index++ < counter) {
//                template.convertAndSend("/topic/subscribeTest", new ServerMessage("服务器主动推的数据"));
//                try {
//                    Thread.sleep(RandomUtils.nextInt(0, 3000));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        return "ok";
//    }

    @Scheduled(cron="0/5 * * * * *")
    public void sendQueueMessage() {
        List<String> users = new ArrayList(userMap.keySet());
        if(users.size() > 0){
            System.out.println("users count:" + users.size());
        }
        users.forEach(u->{
            System.out.println("start send to user:" + u);
            Map<String,Object> map = new HashMap<>();
            map.put("simpSessionId",u);
            map.put("simpDestination","/user/" + u + "/topic/toUser");
            map.put("simpMessageType",SimpMessageType.MESSAGE);
            map.put("contentType",APPLICATION_JSON_UTF8_VALUE);
            map.put(NativeMessageHeaderAccessor.NATIVE_HEADERS,new HashMap<>());
            this.template.convertAndSendToUser(u,"/topic/toUser",new ServerMessage("服务器主动推的数据 userId:" + u + System.currentTimeMillis()),new MessageHeaders(map),null);
        });

    }

}
