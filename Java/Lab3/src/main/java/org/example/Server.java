package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Server {
    public static void main(String[] args) {
        final int port = 1234;
        List<Thread> threadList = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                //System.out.println("A");

                Socket clientSocket = serverSocket.accept();

                // Tworzenie nowego wątku dla każdego klienta
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
                threadList.add(clientThread);

                /*try (Socket clientSocket = serverSocket.accept();
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                    sleep(500);


                    String msg = "Ready";

                    out.writeObject(msg);
                    out.flush();

                    System.out.println("Sending: " + msg);


                    sleep(500);


                    Object receivedObject = in.readObject();
                    int messagesAmount = (int) receivedObject;
                    System.out.println("Received messages amount: " + messagesAmount);


                    sleep(500);


                    msg = "Ready for messages";
                    out.writeObject(msg);
                    out.flush();
                    System.out.println("Sending: " + msg);


                    sleep(500);


                    for (int i = 0; i < messagesAmount; i++) {

                        receivedObject = in.readObject();
                        System.out.println("Received: " + receivedObject);
                        sleep(500);

                    }


                } catch (ClassNotFoundException | InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
