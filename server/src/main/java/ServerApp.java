import server.Server;

public class ServerApp {
    
    public static void main(String[] args) {
        Server server = new Server(8080);
        server.startAcsept();
    }
}
