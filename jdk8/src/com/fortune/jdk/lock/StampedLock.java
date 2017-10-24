package com.fortune.jdk.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 有戳的锁，可以做乐观锁
 *
 * @author fortune.wu
 * @date 17/10/24
 */
public class StampedLock {
    public void testStampedLock() {
        ThreadFactory factory = new Synchronize.DemoThreadFactory();
        java.util.concurrent.locks.StampedLock stampedLock = new java.util.concurrent.locks.StampedLock();
        Map<String, String> map = new HashMap<>(3);

        ExecutorService executor = new ThreadPoolExecutor(2, 5, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5), factory, new ThreadPoolExecutor.AbortPolicy());
        executor.submit(() -> {
            long writeLock = stampedLock.writeLock();
            try {
                Thread.sleep(10000);
                map.put("foo", "bar");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlockWrite(writeLock);
            }
        });
        Runnable readTask = () -> {
            long stamp = stampedLock.readLock();
            try {
                System.out.println(map.get("foo"));
            } finally {
                stampedLock.unlockRead(stamp);
            }
        };
        executor.submit(readTask);
        executor.submit(readTask);
        Synchronize.stop(executor);
    }

    /**
     * 乐观锁
     */
    public void testOptimisticRead() {
        ThreadFactory factory = new Synchronize.DemoThreadFactory();
        java.util.concurrent.locks.StampedLock stampedLock = new java.util.concurrent.locks.StampedLock();

        ExecutorService executor = new ThreadPoolExecutor(2, 5, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5), factory, new ThreadPoolExecutor.AbortPolicy());
        executor.submit(() -> {
            long stamp = stampedLock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + stampedLock.validate(stamp));
                Thread.sleep(1500);
                System.out.println("Optimistic Lock Valid: " + stampedLock.validate(stamp));
                Thread.sleep(3500);
                System.out.println("Optimistic Lock Valid: " + stampedLock.validate(stamp));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlockRead(stamp);
            }
        });
        executor.submit(() -> {
            long writeLock = stampedLock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Write done");
                stampedLock.unlockWrite(writeLock);
            }
        });
        Synchronize.stop(executor);
    }

    public static void main(String[] args) {
        StampedLock stampedLock = new StampedLock();
        stampedLock.testStampedLock();

        StampedLock stampedLock1 = new StampedLock();
        stampedLock1.testOptimisticRead();
    }
}
