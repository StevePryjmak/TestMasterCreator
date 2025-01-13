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
        functionMap.put("Get user", this::sendGetUser);
        functionMap.put("Update user", this::sendUpdateUser);
        functionMap.put("Delete user", this::sendDeleteUser);
        functionMap.put("List of user test", this::sendUserTestList);
        functionMap.put("List of liked test", this::sendLikedTestList);
        functionMap.put("Add to likes", this::sendAddToLikes);
        functionMap.put("Add result", this::sendAddResult);
        // TODO add more functions here
    }

    public void sendAddResult() {
        Object obj = recived.poll();
        try {
            DataBase.saveResult(
                    ((TestInfoData) obj).currentUserID,
                    ((TestInfoData) obj).testID,
                    ((TestInfoData) obj).result);
            sendObject(new Message("Added the result", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Failed to add teh result", (Boolean) false));
        }
    }

    public void sendAddToLikes() {
        Object obj = recived.poll();
        if (obj instanceof TestInfoData) {

            if (DataBase.getLikedTests(((TestInfoData) obj).currentUserID).getTests().contains((TestInfoData) obj)) {
                sendObject(new Message("Already liked", (Boolean) false));
            } else {
                DataBase.addToLikes(((TestInfoData) obj).currentUserID, ((TestInfoData) obj).testID);
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
        sendObject(
                new Message("Information if user exists", DataBase.userExists(((UserData) recived.poll()).username)));
    }

    public void sendCheckPassword() {
        Object obj = recived.poll();
        sendObject(new Message("Information if user exists",
                DataBase.checkPassword(((UserData) obj).username, ((UserData) obj).password)));
    }

    public void sendAddUser() {
        Object obj = recived.poll();
        try {
            DataBase.addUser(((UserData) obj).username, ((UserData) obj).password, ((UserData) obj).email);
            sendObject(new Message("Information if user exists", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Information if user exists", (Boolean) false));
        }
    }

    public void sendGetUser() {
        Object obj = recived.poll();
        try {
            sendObject(new Message("User object: ", DataBase.getUser(((UserData) obj).username)));
        } catch (Exception e) {
            sendObject(new Message("Information if user exists", (Boolean) false));
        }

    }

    private void sendUpdateUser() {
        try {
            DataBase.updateUser((UserData) recived.poll());
            sendObject(new Message("Information if user is updated", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Information if user is updated", (Boolean) false));
        }
    }

    private void sendDeleteUser() {
        Object obj = recived.poll();
        try {
            DataBase.deleteUser(((UserData) obj).username);
            sendObject(new Message("Information if user is deleted", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Information if user is deleted", (Boolean) false));
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
