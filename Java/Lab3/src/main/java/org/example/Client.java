package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Client {
    //private static final Integer MESSAGES_AMOUNT = 5;
    public static void main(String[] args) {
        final String serverAddress = "localhost";
        final int serverPort = 1234;

        try(Socket socket = new Socket(serverAddress,serverPort);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server");

            Scanner scanner = new Scanner(System.in);
            int messagesAmount;

            System.out.println("Enter messages amount:");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number:");
                scanner.next(); // Consuming invalid input, to avoid infinite loop
            }

            messagesAmount = scanner.nextInt();
            System.out.println("You entered: " + messagesAmount);

            sleep(500);

            Object receivedObject = in.readObject();
            System.out.println("Received: " + receivedObject);

            sleep(500);

            out.writeObject(messagesAmount);
            out.flush();
            System.out.println("Sending messages amount: " + messagesAmount);

            sleep(500);

            receivedObject = in.readObject();
            System.out.println("Received: " + receivedObject);

            sleep(500);

            for(int i = 0; i < messagesAmount; i++) {
                Message message = new Message(i, "Hello there");
                out.writeObject(message);
                out.flush();
                System.out.println("Sending: " + message);
                sleep(500);
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
