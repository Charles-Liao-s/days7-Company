package com.example.spring1;

import java.util.ArrayList;
import java.util.List;

public class SocialSpammer {
    public void send(ProfileIterator iterator, String message) {
        List<String> emails = new ArrayList<>();
        while (iterator.hasMore()) {
            Profile profile = iterator.getNext();
            emails.add(profile.getEmail());
        }
        
        // 模拟发送邮件
        System.out.println("Sending message to: " + String.join(", ", emails));
        System.out.println("Message: " + message);
    }
}