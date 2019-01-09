package com.example.demo.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 吴
 * @create 2019/1/8 - 16:11
 */
@Controller
public class Login {
    /**
     * 模拟登录     */
    @RequestMapping(value = "/loginIn", method = RequestMethod.GET)
    public String login(HttpServletRequest request, @RequestParam(required=true) String name, String pwd){
        HttpSession httpSession = request.getSession();
        // 如果登录成功，则保存到会话中
        httpSession.setAttribute("loginName", name);
        return "/index.html";
    }

    /**
     * 转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        // 转到登录页面

        return "login";
    }

//    /**
//     * websocket页面
//     * @return
//     */
//    @RequestMapping(value="/broadcast-rabbitmq/index")
//    public String broadcastIndex(){
//        return "websocket/sendtouser/ws-sendtouser-rabbitmq";
//    }
}
