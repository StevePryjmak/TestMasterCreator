package server;
import java.util.ArrayList;

import QuestionData.MyClass;
import server.ExampleTest;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ObjectOutputStream objectOutputStream;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        clientHandlers.add(this);
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String message = reader.readLine().trim();
                if(message.equals("Give me the test")) {
                    System.out.println("Sending test");
                    this.sendObject(ExampleTest.exampleTestData());
                }
                else {
                    System.out.println("Client: " + message);
                    this.sendMessage("I got you message:" + message);
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object) {
        try {
            objectOutputStream.writeObject(object); // Serialize and send the object
            objectOutputStream.flush();
            System.out.println("Object sent: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // try {
            
        //     MyClass objectToSend = new MyClass("Example", 42);
        //     objectOutputStream.writeObject(objectToSend); // Serialize and send the object
        //     objectOutputStream.flush();
        //     System.out.println("Object sent: " + objectToSend);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // try {
        //     ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        //     objectOutputStream.writeObject(object);
        //     objectOutputStream.flush();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    
}
