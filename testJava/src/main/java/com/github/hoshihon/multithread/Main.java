package com.github.hoshihon.multithread;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MyRunnable runnable = new MyRunnable();

        Thread thread1 = new Thread(runnable);
        thread1.setName("[worker 1]");
        thread1.start();

        Thread thread2 = new Thread(runnable);
        thread2.setName("[worker 2]");
        thread2.start();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            runnable.taskQueue.add(() -> {
                System.out.println(Thread.currentThread().getName() + "task" + finalI);
            });
            Thread.sleep(1000 + random.nextInt(500));
        }
    }

    private static class MyRunnable implements Runnable {

        Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            for (; ; ) {
                Runnable task = taskQueue.poll();
                if (task == null) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "sleeping...");
                        Thread.sleep(2000);
                        continue;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        task.run();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
