package com.example.spring1;

import java.util.ArrayList;
import java.util.List;

public class WeChatIterator implements ProfileIterator {
    private WeChat weChat;
    private String profileId;
    private String type;
    
    private int currentPosition = 0;
    private List<Profile> cache;

    public WeChatIterator(WeChat weChat, String profileId, String type) {
        this.weChat = weChat;
        this.profileId = profileId;
        this.type = type;
    }

    private void lazyInit() {
        if (cache == null) {
            cache = weChat.socialGraphRequest(profileId, type);
        }
    }

    @Override
    public Profile getNext() {
        if (hasMore()) {
            Profile result = cache.get(currentPosition);
            currentPosition++;
            return result;
        }
        return null;
    }

    @Override
    public boolean hasMore() {
        lazyInit();
        return currentPosition < cache.size();
    }
}