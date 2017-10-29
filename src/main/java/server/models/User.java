package server.models;

import server.utility.Digester;

public class User {

    private int userId;
    private String username;
    private String password;
    private int type;
    private long timeCreated;

    public User(String username, String password, long timeCreated) {
        this.username = username;
        this.password = password;
        this.timeCreated = timeCreated;
    }

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    //Always hash password created
    public void setPassword(String password) {
        Digester digester = new Digester();
        this.password = digester.hashWithSalt(password);

    }
    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

}