package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            sleep(500);


            String msg = "Ready";

            out.writeObject(msg);
            out.flush();

            System.out.println(Thread.currentThread().getName() + ": Sending: " + msg);


            sleep(500);


            Object receivedObject = in.readObject();
            int messagesAmount = (int) receivedObject;
            System.out.println(Thread.currentThread().getName() + ": Received messages amount: " + messagesAmount);


            sleep(500);


            msg = "Ready for messages";
            out.writeObject(msg);
            out.flush();
            System.out.println(Thread.currentThread().getName() + ": Sending: " + msg);


            sleep(500);


            for (int i = 0; i < messagesAmount; i++) {

                receivedObject = in.readObject();
                System.out.println(Thread.currentThread().getName() + ": Received: " + receivedObject);
                sleep(500);

            }

        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }
}
