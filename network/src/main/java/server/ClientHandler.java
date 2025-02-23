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
import ImageData.ImageData;
import UserData.User;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final Map<String, Runnable> functionMap = new HashMap<>();
    private Queue<Object> recived = new LinkedList<>();
    private static User user;

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
        functionMap.put("Save test", this::saveTest);
        functionMap.put("Get user", this::sendGetUser);
        functionMap.put("Update user", this::sendUpdateUser);
        functionMap.put("Delete user", this::sendDeleteUser);
        functionMap.put("Add profile icon", this::sendAddIcon);
        functionMap.put("Get profile icon", this::sendGetIcon);
        functionMap.put("List of user test", this::sendUserTestList);
        functionMap.put("List of liked test", this::sendLikedTestList);
        functionMap.put("Add to likes", this::sendAddFavorites);
        functionMap.put("Add result", this::sendSaveResult);
        functionMap.put("Get test like count", this::sendTestLikeCount);
        functionMap.put("Remove from likes", this::sendDeleteFavorites);
        functionMap.put("Get user's test results", this::sendUserTestResult);
        functionMap.put("Get visibility", this::sendGetVisibility);
        functionMap.put("Set visibility", this::sendSetVisibility);
        functionMap.put("Delete", this::sendDelete);
    }

    public void sendDelete() {
        try {
            DataBase.deleteTest((int) recived.poll());
            sendObject(new Message("Deleted quiz", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Failed to deleted quiz", (Boolean) false));
        }

    }

    public void sendUserTestResult() {
        Object obj = recived.poll();
        sendObject(new Message("User test results",
                DataBase.getUserTestResults(((TestInfoData) obj).currentUserID, ((TestInfoData) obj).testID)));
    }

    public void sendTestLikeCount() {
        sendObject(new Message("Test like count", DataBase.getTestLikeCount((int) recived.poll())));
    }

    public void sendSaveResult() {
        Object obj = recived.poll();
        try {
            DataBase.saveResult(
                    ((TestInfoData) obj).currentUserID,
                    ((TestInfoData) obj).testID,
                    ((TestInfoData) obj).result);
            sendObject(new Message("Added the result", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Failed to add the result", (Boolean) false));
        }
    }

    public void sendDeleteFavorites() {
        Object obj = recived.poll();
        if (obj instanceof TestInfoData) {
            boolean already_liked = false;
            for (TestInfoData i : DataBase.getFavorites(((TestInfoData) obj).currentUserID).getTests()) {
                if (i.testID == ((TestInfoData) obj).testID) {
                    already_liked = true;
                    break;
                }
            }

            if (already_liked) {
                DataBase.deleteFavorites(((TestInfoData) obj).currentUserID, ((TestInfoData) obj).testID);
                sendObject(new Message("Removed from liked", Boolean.TRUE));
            } else {
                sendObject(new Message("Failed to removed from liked", Boolean.FALSE));
            }

        }

    }

    public void sendAddFavorites() {
        Object obj = recived.poll();
        if (obj instanceof TestInfoData) {
            boolean already_liked = false;
            for (TestInfoData i : DataBase.getFavorites(((TestInfoData) obj).currentUserID).getTests()) {
                if (i.testID == ((TestInfoData) obj).testID) {
                    already_liked = true;
                    break;
                }
            }

            if (!already_liked) {
                DataBase.addFavorites(((TestInfoData) obj).currentUserID, ((TestInfoData) obj).testID);
                sendObject(new Message("Adding to liked", Boolean.TRUE));
            } else {
                sendObject(new Message("Failed to add to liked", Boolean.FALSE));
            }

        }

    }

    public void sendLikedTestList() {
        sendObject(new Message("List of liked tests", DataBase.getFavorites((int) recived.poll())));
    }

    public void sendUserTestList() {
        sendObject(new Message("List of user tests", DataBase.getUserTests((int) recived.poll())));
    }

    public void saveTest() {
        Object obj = recived.poll();
        if (obj instanceof TestData) {
            DataBase.addTest((TestData) obj, user.getId());
        }
    }

    public void sendTestsList() {
        recived.poll();
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
        boolean correct_pass = DataBase.checkPassword(((UserData) obj).username, ((UserData) obj).password);
        if (correct_pass) {
            user = DataBase.getUser(((UserData) obj).username);
            System.out.println("current user: " + user + " userID: " + user.getId());
        }
        sendObject(new Message("Information if user exists", correct_pass));
    }

    public void sendAddUser() {
        Object obj = recived.poll();
        try {
            DataBase.addUser(((UserData) obj).username, ((UserData) obj).password, ((UserData) obj).email);
            user = DataBase.getUser(((UserData) obj).username);
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

    private void sendAddIcon() {
        Object obj = recived.poll();
        try {
            DataBase.setUserIcon(((ImageData) obj).username, ((ImageData) obj).map);
            sendObject(new Message("Information if icon is added", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Information if icon is added", (Boolean) false));
        }
    }

    private void sendGetIcon() {
        Object obj = recived.poll();
        ImageData img = new ImageData();
        try {
            img.map = DataBase.getUserIcon(((UserData) obj).username);
            sendObject(new Message("Icon info", img));
        } catch (Exception e) {
            sendObject(new Message("Icon info", "blad"));
        }
    }

    public void sendGetVisibility() {
        sendObject(new Message("List of user tests", DataBase.getUserVisibility(((UserData) recived.poll()).username)));
    }

    public void sendSetVisibility() {
        Object obj = recived.poll();
        int change = 0;
        if ((((UserData) obj).password).equals("Public")) {
            change = 1;
        }
        try {
            DataBase.setUserVisibility(((UserData) obj).username, change);
            sendObject(new Message("Information if visibility is changed", (Boolean) true));
        } catch (Exception e) {
            sendObject(new Message("Information if visibility is changed", (Boolean) false));
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
