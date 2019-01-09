package com.example.demo.demo;

import java.security.Principal;

/**
 * @author 吴
 * @create 2019/1/8 - 15:45
 */
public class MyPrincipal implements Principal {
    private String name;

    public MyPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
