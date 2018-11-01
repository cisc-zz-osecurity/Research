package com.fortune.jdk.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author fortune.wu
 * @date 17/10/24
 */
public class ReadWriteLock {

    public void testReadWriteLock() {
        ThreadFactory factory = new Synchronize.DemoThreadFactory();
        Map<String, String> map = new HashMap<>(2);
        java.util.concurrent.locks.ReadWriteLock lock = new ReentrantReadWriteLock();

        ExecutorService executors = new ThreadPoolExecutor(2, 5, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3), factory, new ThreadPoolExecutor.AbortPolicy());
        executors.submit(() -> {
            System.out.println("write starting ...");
            lock.writeLock().lock();
            try {
                Thread.sleep(1000);
                map.put("foo", "bar");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
                System.out.println("write ended ...");
            }
        });
        Runnable runnable = () -> {
            System.out.println("read starting ...");
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
                System.out.println("read ended ...");
            }
        };
        executors.submit(runnable);
        executors.submit(runnable);
        Synchronize.stop(executors);
    }

    public static void main(String[] args) {
        ReadWriteLock lock = new ReadWriteLock();
        lock.testReadWriteLock();
    }
}
