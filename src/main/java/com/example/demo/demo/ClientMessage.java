package com.example.demo.demo;

/**
 * @author 吴
 * @create 2019/1/7 - 16:08
 */

public class ClientMessage {
    private String name;

    private String userId;

    public ClientMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientMessage() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
