import server.Server;
import database.DataBase;

public class ServerApp {
    
    public static void main(String[] args) {
        DataBase.start();
        Server server = new Server(8080);
        server.startAcsept();
    }
}
