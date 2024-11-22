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

    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                try {
                    while (!socket.isClosed()) {
                        String message = reader.readLine();
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    public void closeConnection() {
        try {
            if (socket != null)
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
