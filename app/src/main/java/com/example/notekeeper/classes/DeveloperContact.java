package com.example.notekeeper.classes;

public class DeveloperContact {
    public static String TYPE_DEFAULT = "default";
    public static String TYPE_EMAIL = "email";

    private String contactMeans, url, type;

    public DeveloperContact(String contactMeans, String url, String type) {
        this.contactMeans = contactMeans;
        this.url = url;
        this.type = type;
    }

    public String getType() { return type; }

    public String getContactMeans() { return contactMeans; }

    public void setContactMeans(String contactMeans) { this.contactMeans = contactMeans; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
