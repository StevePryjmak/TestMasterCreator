package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import TestData.*;

import java.io.*;
import java.net.Socket;
import connection.Message;
import database.DataBase;
import UserData.UserData;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final Map<String, Runnable> functionMap = new HashMap<>();
    private Queue<Object> recived = new LinkedList<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
        clientHandlers.add(this);
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        intializeFunctionMap();
    }

    public void intializeFunctionMap() {
        functionMap.put("List of tests", this::sendTestsList);
        functionMap.put("Give me the test", this::sendTest);
        functionMap.put("User exists", this::sendUserExists);
        functionMap.put("Check password", this::sendCheckPassword);
        functionMap.put("Add user", this::sendAddUser);
        functionMap.put("List of user test", this::sendUserTestList);
        functionMap.put("List of liked test", this::sendLikedTestList);
        functionMap.put("Add to likes", this::sendAddToLikes);
        // TODO add more functions here
    }

    public void sendAddToLikes() {
        Object obj = recived.poll();
        if (obj instanceof TestInfoData) {
            int currentUserID = 0; // TODO: get current userID

            if (DataBase.getLikedTests(currentUserID).getTests().contains((TestInfoData) obj)) {
                sendObject(new Message("Already liked", (Boolean) true));
            } else {
                DataBase.addToLikes(currentUserID, ((TestInfoData) obj).testID);
                sendObject(new Message("Adding to liked", (Boolean) true));
            }

        }

    }

    public void sendLikedTestList() {
        sendObject(new Message("List of liked tests", DataBase.getLikedTests((int) recived.poll())));
    }

    public void sendUserTestList() {
        sendObject(new Message("List of user tests", DataBase.getUserTests((int) recived.poll())));
    }

    public void sendTestsList() {
        sendObject(new Message("List of tests", DataBase.getTests()));
    }

    public void sendTest() {
        sendObject(new Message("Give me the test", DataBase.getTest((String) recived.poll())));
    }

    public void sendUserExists() {
        Object obj = recived.poll();
        if (obj instanceof UserData) {
            Boolean res = DataBase.userExists(((UserData) obj).username);
            sendObject(new Message("Information if user exists", res));
        }
    }

    public void sendCheckPassword() {
        Object obj = recived.poll();
        if (obj instanceof UserData) {
            Boolean res = DataBase.checkPassword(((UserData) obj).username, ((UserData) obj).password);
            sendObject(new Message("Check password", res));
        }
    }

    public void sendAddUser() {
        Object obj = recived.poll();
        if (obj instanceof UserData) {
            DataBase.addUser(((UserData) obj).username, ((UserData) obj).password, ((UserData) obj).email);
            sendObject(new Message("Information if user exists", (Boolean) true));
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
                    try {
                        recived.add(message.getObject());
                    } catch (NullPointerException e) {
                        System.out.println("Function without arguments good");
                    }
                    callFromMap(message.getMessage());
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

    public void callFromMap(String message) {
        functionMap.get(message).run();
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
