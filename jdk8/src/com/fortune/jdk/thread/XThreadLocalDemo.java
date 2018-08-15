package com.fortune.jdk.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fortune wu
 * @date 2018/8/15
 */
public class XThreadLocalDemo {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 30,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3), new ThreadPoolExecutor.AbortPolicy());
        poolExecutor.execute(() ->  {
            XThreadLocal.getInstance().setCurrentUser("jack");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        poolExecutor.execute(() -> {
            XThreadLocal.getInstance().setCurrentUser("fortune");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        XThreadLocal.getInstance().setCurrentUser("beijing");
        Thread.sleep(3000);
        System.out.println("Thread Local test end...");
    }
}
