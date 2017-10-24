package com.fortune.jdk.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
            lock.writeLock().lock();
            try {
                Thread.sleep(1000);
                map.put("foo", "bar");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        });
        Runnable runnable = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
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
