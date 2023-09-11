package org.example;

import java.util.HashMap;
import java.util.Map;

public class MyResults {
    private static final Map<Integer, Boolean> results = new HashMap<>();

    public static synchronized void put(int key, boolean value) {results.put(key,value);
    }
}
