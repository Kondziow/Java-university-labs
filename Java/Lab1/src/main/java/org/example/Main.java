package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final String[] names = new String[]{"Alfred", "Bart", "Donald", "Emanuel", "Merlin", "Marcin", "Saruman"};

    public static void main(String[] args) {
        String sort;
        if (args.length > 0)
            sort = args[0];
        else
            sort = null;
        Mage mainMage = new Mage("Gandalf", 500, 10000, sort);

        /*Random random = new Random();

        for (int i = 0; i < random.nextInt(10 - 5 + 1) + 5; i++) {
            int name = random.nextInt(names.length);
            int level = random.nextInt(20 - 0 + 1) + 5;
            //int level = 10;
            int power = random.nextInt(level * 10 - level * 5 + 1) + level * 5;

            Mage mage = new Mage(names[name], level, power, sort);
            mainMage.getApprentices().add(mage);
        }
*/
        generateMages(mainMage, 7, sort);
        /*System.out.println(mainMage);
        for (Mage mage1 : mainMage.getApprentices()) {
            System.out.print("-");
            System.out.println(mage1);
            for (Mage mage2 : mage1.getApprentices()) {
                System.out.print("--");
                System.out.println(mage2);
            }
        }*/
        printMages(mainMage);
        System.out.println();
        printAllApprenticesNumber(mainMage, 0);
    }

    private static void printAllApprenticesNumber(Mage rootMage, int depth) {
        int allApprenticesNumber = countAllApprentices(rootMage);
        for(int i = 0; i < depth; i++) {
            System.out.print("-");
        }
        System.out.println(rootMage + " -> " + allApprenticesNumber + " apprentices");
        for(Mage mage: rootMage.getApprentices()) {
            printAllApprenticesNumber(mage, depth+1);
        }
    }

    private static int countAllApprentices(Mage rootMage) {
        int counter = 0;
        counter += rootMage.getApprentices().size();
        for(Mage mage: rootMage.getApprentices()) {
            counter += countAllApprentices(mage);
        }
        return counter;
    }

    private static void generateMages(Mage rootMage, int maxApprenticesNumber, String sort) {
        Random random = new Random();

        int apprenticesNumber = random.nextInt(maxApprenticesNumber / 2 + 1) + (maxApprenticesNumber / 2) - 1;

        for (int i = 0; i < apprenticesNumber; i++) {
            int nameNumber = random.nextInt(names.length);
            int level = random.nextInt(rootMage.getLevel() / 2) + rootMage.getLevel() / 2;
            double power = random.nextInt((int) (rootMage.getPower() / 2)) + rootMage.getPower() / 2;

            Mage mage = new Mage(names[nameNumber], level, power, sort);
            generateMages(mage, apprenticesNumber, sort);
            rootMage.getApprentices().add(mage);
        }
    }

    private static void printMages(Mage mainMage) {
        System.out.println(mainMage);
        printApprentices(mainMage, 1);
    }

    private static void printApprentices(Mage rootMage, int depth) {
        for (Mage mage : rootMage.getApprentices()) {
            for (int i = 0; i < depth; i++)
                System.out.print("-");
            System.out.println(mage);
            printApprentices(mage, depth + 1);
        }
    }
}