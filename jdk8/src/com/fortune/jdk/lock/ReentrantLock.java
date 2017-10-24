package com.fortune.jdk.lock;

/**
 * the Lock
 *
 * @author fortune.wu
 * @date 17/10/18
 */
public class ReentrantLock {
    private java.util.concurrent.locks.ReentrantLock lock = new java.util.concurrent.locks.ReentrantLock();
    private int count = 0;

    public void increment() {
        lock.lock();
        try {
            count ++;
            System.out.println("count:" + count + " and lock is open");
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("count:" + count + " and lock is released");
        }
    }

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.increment();
        lock.increment();
    }

}
