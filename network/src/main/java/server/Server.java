package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    private int PORT;
    private ServerSocket serverSocket;
    public Server(int PORT) {
        this.PORT = PORT;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAcsept() {
        System.out.println("Server is running on port " + PORT);
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            if(serverSocket != null)
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
