package com.example.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ImageService;

@Controller
public class HelloController {
    @Autowired
    private ImageService imageService;

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

        // 一時的にS3オブジェクトにアクセスできるURLを生成
        String imageUrl1 = imageService.generatePresignedUrl("icon_bear.jpg", Duration.ofHours(1));
        String imageUrl2 = imageService.generatePresignedUrl("sampledir/icon.jpeg", Duration.ofHours(1));

        mav.addObject("currentTime", currentTime);
        mav.addObject("hostName", hostName);
        mav.addObject("imageUrl1", imageUrl1);
        mav.addObject("imageUrl2", imageUrl2);
        mav.setViewName("hello");
        return mav;
    }
}
