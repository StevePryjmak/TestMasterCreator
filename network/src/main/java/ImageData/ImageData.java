package ImageData;

import java.io.Serializable;

public class ImageData implements Serializable{
    public byte[] map;
    public String username;

    public ImageData(){
        this.map = null;
        this.username = "";
    }
}
