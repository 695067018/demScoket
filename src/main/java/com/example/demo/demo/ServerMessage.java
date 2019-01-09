package com.example.demo.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 吴
 * @create 2019/1/7 - 16:11
 */
@Data
@AllArgsConstructor
public class ServerMessage {
    private String content;


    @Override
    public String toString() {
        return content;
    }
}
