package com.example.social_network_gradle;

public class DataSingleton {
    private static DataSingleton instance;
    String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private DataSingleton(){}

    public static DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }
}
