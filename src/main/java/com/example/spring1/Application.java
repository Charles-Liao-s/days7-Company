package com.example.spring1;

public class Application {
    private SocialNetwork network;
    private SocialSpammer spammer;

    public Application() {
        // 默认使用WeChat
        this.network = new WeChat();
        this.spammer = new SocialSpammer();
    }

    public void sendSpamToFriends(String profileId) {
        ProfileIterator iterator = network.createFriendsIterator(profileId);
        spammer.send(iterator, "Very important message");
    }

    public void sendSpamToCoworkers(String profileId) {
        ProfileIterator iterator = network.createCoworkersIterator(profileId);
        spammer.send(iterator, "Very important message");
    }
    
    public static void main(String[] args) {
        Application app = new Application();
        
        System.out.println("Sending spam to friends of user 1:");
        app.sendSpamToFriends("1");
        
        System.out.println("\nSending spam to coworkers of user 1:");
        app.sendSpamToCoworkers("1");
    }
}