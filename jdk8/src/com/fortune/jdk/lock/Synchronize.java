package com.fortune.jdk.lock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * the synchronized
 *
 * @author fortune.wu
 * @date 17/10/19
 */
public class Synchronize {
    public int count = 0;

    public synchronized void incrementSync() {
        count = count + 1;
    }

    public void increment() {
        count = count + 1;
    }

    public void testIncrement() {
        ThreadFactory nameThreadFactory = new DemoThreadFactory();

        ExecutorService executor = new ThreadPoolExecutor(1, 10000, 1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), nameThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0, 10000)
                .forEach(i -> executor.execute(this::increment));

        stop(executor);
        System.out.println(count);
    }

    public void testIncrementSync() {
        ThreadFactory nameFactory = new DemoThreadFactory();

        ExecutorService executor = new ThreadPoolExecutor(1, 1000, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), nameFactory, new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0, 1000)
                .forEach(i -> executor.execute(this::increment));
        stop(executor);
        System.out.println(count);
    }

    public static void main(String[] args) {
        Synchronize synchronize = new Synchronize();
        synchronize.testIncrement();

        Synchronize synchronizes = new Synchronize();
        synchronizes.testIncrementSync();
    }

    void stop(ExecutorService threadPoolExecutor) {
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!threadPoolExecutor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            threadPoolExecutor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    class DemoThreadFactory implements ThreadFactory {
        /**
         * Constructs a new {@code Thread}.  Implementations may also initialize
         * priority, name, daemon status, {@code ThreadGroup}, etc.
         *
         * @param r a runnable to be executed by new thread instance
         * @return constructed thread, or {@code null} if the request to
         * create a thread is rejected
         */
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }

    /**
     * this function is error;
     * foreach can remove item;
     * if you must remove, user iterator;
     */
    public void testForeach() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        //error
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item);
            }
        }
        //yes
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        System.out.println(list.size());
    }
}

