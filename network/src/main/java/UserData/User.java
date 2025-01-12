package UserData;

import java.io.Serializable;

public class User implements Serializable{
    private int id;
    private String username;
    private String email;

    public User(){
        this.id = 0;
        this.username = "unknown";
        this.email = "unknown";
    }

    public void setAttributes(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public void setAttributes(int id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

}
