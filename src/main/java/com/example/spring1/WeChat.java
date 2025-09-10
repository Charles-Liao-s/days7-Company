package com.example.spring1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChat implements SocialNetwork {
    private Map<String, List<String>> friendsRelations = new HashMap<>();
    private Map<String, List<String>> coworkersRelations = new HashMap<>();
    private Map<String, Profile> profiles = new HashMap<>();

    public WeChat() {
        // 初始化一些示例数据
        initTestData();
    }

    private void initTestData() {
        // 创建一些示例用户档案
        Profile profile1 = new Profile("1", "alice@example.com", "Alice", "companyA");
        Profile profile2 = new Profile("2", "bob@example.com", "Bob", "companyA");
        Profile profile3 = new Profile("3", "charlie@example.com", "Charlie", "companyB");
        Profile profile4 = new Profile("4", "david@example.com", "David", "companyA");
        
        profiles.put(profile1.getId(), profile1);
        profiles.put(profile2.getId(), profile2);
        profiles.put(profile3.getId(), profile3);
        profiles.put(profile4.getId(), profile4);
        
        // 设置朋友关系
        List<String> aliceFriends = new ArrayList<>();
        aliceFriends.add("2");
        aliceFriends.add("3");
        aliceFriends.add("4");
        friendsRelations.put("1", aliceFriends);
        
        List<String> bobFriends = new ArrayList<>();
        bobFriends.add("1");
        bobFriends.add("4");
        friendsRelations.put("2", bobFriends);
        
        // 设置同事关系
        List<String> aliceCoworkers = new ArrayList<>();
        aliceCoworkers.add("2");
        aliceCoworkers.add("4");
        coworkersRelations.put("1", aliceCoworkers);
    }
    
    public Profile getProfile(String profileId) {
        return profiles.get(profileId);
    }
    
    public List<String> getFriendsRelations(String profileId) {
        return friendsRelations.getOrDefault(profileId, new ArrayList<>());
    }
    
    public List<String> getCoworkersRelations(String profileId) {
        return coworkersRelations.getOrDefault(profileId, new ArrayList<>());
    }

    @Override
    public ProfileIterator createFriendsIterator(String profileId) {
        return new WeChatIterator(this, profileId, "friends");
    }

    @Override
    public ProfileIterator createCoworkersIterator(String profileId) {
        return new WeChatIterator(this, profileId, "coworkers");
    }
    
    // 模拟发送社交图请求的方法
    public List<Profile> socialGraphRequest(String profileId, String type) {
        List<Profile> result = new ArrayList<>();
        List<String> relationIds;
        
        if ("friends".equals(type)) {
            relationIds = getFriendsRelations(profileId);
        } else {
            relationIds = getCoworkersRelations(profileId);
        }
        
        for (String id : relationIds) {
            Profile profile = profiles.get(id);
            if (profile != null) {
                result.add(profile);
            }
        }
        
        return result;
    }
}