package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyResource {
    private final List<Integer> myArray;

    public MyResource() {
        this.myArray = new ArrayList<>();
    }

    public synchronized int take() throws InterruptedException {
        while (this.myArray.isEmpty())
            wait();
        return (int) myArray.remove(0);
    }

    public synchronized void put(int value) {
        this.myArray.add(value);
        notifyAll();
    }
}
