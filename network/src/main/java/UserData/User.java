package UserData;

public class User {
    private int id;
    private String username;
    private String email;

    public User(){
        this.id = 0;
        this.username = "unknown";
        this.email = "unknown";
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
