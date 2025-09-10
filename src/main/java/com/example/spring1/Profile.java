package com.example.spring1;

public class Profile {
    private String id;
    private String email;
    private String name;
    private String companyId;

    public Profile(String id, String email, String name, String companyId) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.companyId = companyId;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCompanyId() {
        return companyId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        return id.equals(profile.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}