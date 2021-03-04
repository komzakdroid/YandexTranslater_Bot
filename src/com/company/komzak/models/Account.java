package com.company.komzak.models;

import org.telegram.telegrambots.api.objects.User;

public class Account {
    private User user;

    private String language;

    public Account(User user) {
        this.user = user;
    }


    public Account(User user, String language) {
        this.user = user;
        this.language = language;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", language='" + language + '\'' +
                '}';
    }
}
