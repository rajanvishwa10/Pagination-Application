package com.android.paginationapplication;

public class POJO {
    String reputation, user_id, profile_image, display_name, link;

    public POJO(String reputation, String user_id, String profile_image, String display_name, String link) {
        this.reputation = reputation;
        this.user_id = user_id;
        this.profile_image = profile_image;
        this.display_name = display_name;
        this.link = link;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
