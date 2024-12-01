package server;

import java.util.ArrayList;

import TestData.*;

import java.io.*;
import java.net.Socket;
import connection.Message;
import database.DataBase;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        clientHandlers.add(this);
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                Object receivedObject = objectInputStream.readObject();
                if (receivedObject instanceof Message) {
                    Message message = (Message) receivedObject;
                    System.out.println("Message received: " + message.getMessage());
                    if (message.getMessage().equals("Give me the test")) {
                        TestData testData = DataBase.getTest((String) message.getObject());
                        Message responce = new Message("Here is the test", testData);
                        sendObject(responce);
                    } else if (message.getMessage().equals("List of tests")) {
                        AvalibleTestsList avalibleTestsList = DataBase.getTests();
                        Message responce = new Message("Here is the list of tests", avalibleTestsList);
                        sendObject(responce);
                    }
                } else {
                    System.err.println("Got not expected object");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
    }

}
