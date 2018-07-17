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

        ExecutorService executor = new ThreadPoolExecutor(3, 5, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5), factory, new ThreadPoolExecutor.AbortPolicy());
        executor.submit(() -> {
            System.out.println("write starting ...");
            long writeLock = stampedLock.writeLock();
            try {
                Thread.sleep(10000);
                map.put("foo", "bar");
                System.out.println("write value to map");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlockWrite(writeLock);
                System.out.println("write ended ...");
            }
        });
        Runnable readTask = () -> {
            System.out.println("read starting ...");
            long stamp = stampedLock.readLock();
            try {
                System.out.println(map.get("foo"));
            } finally {
                stampedLock.unlockRead(stamp);
                System.out.println("read ended ...");
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
            /**
             * tryOptimisticRead是一个乐观的读，使用这种锁的读不阻塞写
             * 每次读的时候得到一个当前的stamp值（类似时间戳的作用）
             */
            /**
             * 如果读取的时候发生了写，则stampedLock的stamp属性值会变化，此时需要重读，
             * 在重读的时候需要加读锁（并且重读时使用的应当是悲观的读锁，即阻塞写的读锁）
             * 如果stamp是不可用的,则意味着在读取的过程中,可能被其他线程改写了数据,
             * 因此,有可能出现脏读,如果如果出现这种情况,我们可以像CAS操作那样在一个死循环中一直使用乐观锁,直到成功为止
             */
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
