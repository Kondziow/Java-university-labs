package org.example;

public class MyThread implements Runnable{
    private final int MAX_SLEEP_TIME = 5000;
    private boolean shouldStop = false;
    private MyResource myResource;

    public MyThread(MyResource myResource) {
        this.myResource = myResource;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && !shouldStop) {
            int key = 0;
            try {
                key = this.myResource.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ponowne ustawienie flagi przerwania
                break; // Zakończ pętlę
                //throw new RuntimeException(e);
            }
            boolean value = false;
            try {
                value = isPrimitive(key);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ponowne ustawienie flagi przerwania
                break; // Zakończ pętlę
                //throw new RuntimeException(e);
            }

            MyResults.put(key, value);

            System.out.println(Thread.currentThread().getName() + " -> number: " + key + ", is primitive: " + value);
        }
    }

    private boolean isPrimitive(int key) throws InterruptedException {
        boolean isPrimitive = true;
        if(key == 1)
            isPrimitive = false;
        for(int i = 2; i <= Math.sqrt(key); i++) {
            if (key % i == 0) {
                isPrimitive = false;
                break;
            }
        }
        int sleepTime = key*100;
        if(sleepTime > MAX_SLEEP_TIME)
            sleepTime = MAX_SLEEP_TIME;
        Thread.sleep(sleepTime);
        return isPrimitive;
    }

    public void stopThread() {
        shouldStop = true;
    }
}
