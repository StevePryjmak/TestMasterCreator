package client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import QuestionData.TestData;
import QuestionData.MyClass;

import java.util.List;
import java.util.ArrayList;

public class Client {
    
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private List<Object> recived = new ArrayList<>();
    private ObjectInputStream objectInputStream;

    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            listenForObjects();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void sendMessages() {
        try {
            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()) {
                String message = scanner.nextLine();
                writer.write(message);
                writer.newLine();
                writer.flush();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // try {
                //     while (!socket.isClosed()) {
                //         //String message = reader.readLine();
                //         //System.out.println("Server: " + message);
                //     }
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
            }
        }).start();
    }

    public void listenForObjects() {
        new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    Object receivedObject = objectInputStream.readObject(); 
                    System.err.println("Received object: ");

                    if (receivedObject instanceof TestData) {
                        TestData testData = (TestData) receivedObject;
                        recived.add(testData);
                        System.out.println(recived.size());
                        System.out.println("Received a TestData object");
                    } else {
                        System.out.println("Received an unknown type of object");
                    }
                    // if (receivedObject instanceof MyClass) {
                    //     MyClass myObject = (MyClass) receivedObject;
                    //     System.out.println("Received object: " + myObject);
                    //     System.out.println("Name: " + myObject.getName());
                    //     System.out.println("Value: " + myObject.getValue());
                    // } else {
                    //     System.out.println("Unexpected object type received.");
                    // }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    

    // public void listenForObjects() {
    //     new Thread(new Runnable() {
    //         @Override
    //         public void run() {
    //             try {

    //                 while (!socket.isClosed()) {
                    
    //                     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        
    //                     Object receivedObject = ois.readObject(); // Read and deserialize the object
    //                     System.err.println("Received object: " + receivedObject);
    //                     // Cast the received object back to MyClass
    //                     if (receivedObject instanceof MyClass) {
    //                         MyClass myObject = (MyClass) receivedObject;
    //                         System.out.println("Received object: " + myObject);
    //                         System.out.println("Name: " + myObject.getName());
    //                         System.out.println("Value: " + myObject.getValue());
    //                     } else {
    //                         System.out.println("Unexpected object type received.");
    //                     }

    //                 // ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
    //                 // Object receivedObject = objectInputStream.readObject();
    //                 // recived.add(receivedObject);
    //                 // if(receivedObject instanceof TestData) {
    //                 //     TestData testData = (TestData) receivedObject;
    //                 //     System.out.println("Received a TestData object");
                        
    //                 // }

    //                 // if (receivedObject instanceof SingleChoiceQuestion) {
    //                 //     SingleChoiceQuestion question = (SingleChoiceQuestion) receivedObject;
    //                 //     System.out.println("Received a SingleChoiceQuestion");
    //                 // } else if (receivedObject instanceof MultipleChoiceQuestion) {
    //                 //     MultipleChoiceQuestion question = (MultipleChoiceQuestion) receivedObject;
    //                 //     System.out.println("Received a MultipleChoiceQuestion");
    //                 // } else {
    //                 //     System.out.println("Received an unknown type of object");
    //                 // }
    //             }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }}).start();
    // }
    
    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getOneRecivedObject () {
        while(true) {
            System.out.println("Waiting for object");
            if (recived.size() > 0) {
                System.out.println("Object received");
                return recived.remove(0);
            }
        }
        //return null;
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
