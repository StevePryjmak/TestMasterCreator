package connection;

import java.io.Serializable;

public class Message implements Serializable {
    private String message; // for serwer it probably would be a comand and if use HashMap to map functions to call with argument object
    private Object object;

    public Message(String message, Object object) {
        this.message = message; 
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }

}
