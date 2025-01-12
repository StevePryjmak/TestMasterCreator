package UserData;

import java.io.Serializable;

public class UserData implements Serializable{
    public String username;
    public String password;
    public String email;
    public int id = -1;

    public UserData(String username) {
        this.username = username;
    }

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserData(String username, String password, String email, int id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }
}