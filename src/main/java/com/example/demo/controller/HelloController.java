package com.example.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
    @GetMapping({ "", "/" })
    public ModelAndView index(ModelAndView mav) {
        // 現在時刻を取得
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = now.format(formatter);

        // マシンのホスト名を取得
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = "Unknown";
        }

        mav.addObject("currentTime", currentTime);
        mav.addObject("hostName", hostName);
        mav.setViewName("hello");
        return mav;
    }
}
