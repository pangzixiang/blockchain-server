package com.scutgroup3.blockchainserver.Login;

public class person {
    private String username;
    private String id;

    public person(String username, String id){
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
