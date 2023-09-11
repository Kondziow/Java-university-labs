package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final int TASKS_AMOUNT = 25;
    public static void main(String[] args) throws InterruptedException {
        int threadAmount = 3;

        List<Thread> myThreads = new ArrayList<>();
        MyResource myResource = new MyResource();

        for (int i = 0; i < TASKS_AMOUNT; i++) {
            myResource.put(i+1);
        }

        for (int i = 0; i < threadAmount; i++) {
            Thread thread = new Thread(new MyThread(myResource));
            myThreads.add(thread);
            thread.start(); // Uruchom nowy wątek
            /*myThreads.add(new MyThread(myResource));*/
        }

        Scanner scanner = new Scanner(System.in);
        String input = "";
        int number;

        input = scanner.next();
        while(!Objects.equals(input, "q")) {
            try {
                number = Integer.parseInt(input);
                myResource.put(number);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number to process or 'q' to quit");
            }
            input = scanner.next();
        }

        for (Thread thread : myThreads) {
            thread.interrupt();
            System.out.println("Interrupting thread " + thread.getName());
        }

        for (Thread thread : myThreads) {
            thread.join();
            System.out.println("Joining thread " + thread.getName());
        }

    }
}