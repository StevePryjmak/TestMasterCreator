package client;

import java.io.*;
import java.net.Socket;

import connection.Message;

import java.util.List;
import java.util.ArrayList;

public class Client {

    private Socket socket;
    private List<Message> recived = new ArrayList<>();
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            listenForObjects();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // shorten implemetation of functional interface
    public void listenForObjects() {
        new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    Object receivedObject = objectInputStream.readObject();
                    System.err.println("Received object: ");

                    if (receivedObject instanceof Message) {
                        Message message = (Message) receivedObject;
                        recived.add(message);
                        System.out.println(recived.size());
                        System.out.println("Received a TestData object");
                    } else {
                        System.out.println("Received an unknown type of object");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public Message getOneRecivedObject() {
        while (true) {
            System.out.println("Waiting for object");
            if (recived.size() > 0) {
                System.out.println("Object received");
                return recived.remove(0);
            }
        }
        // return null;
    }

    public void sendObject(Object object) {
        try {
            System.out.println("Sending object");
            objectOutputStream.writeObject(object); // Serialize and send the object
            objectOutputStream.flush();
            System.out.println("Object sent: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text, Object object) {
        Message message = new Message(text, object);
        sendObject(message);
    }

    public void closeConnection() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
