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

        /**
         * 当线程池中的线程数量等于corePoolSize 时，
         * 如果继续提交任务，该任务会被添加到阻塞队列workQueue中，
         * 当阻塞队列也满了之后，则线程池会新建线程执行任务直到maximumPoolSize。
         * 由于FixedThreadPool使用的是“无界队列”LinkedBlockingQueue，
         * 那么maximumPoolSize参数无效，同时指定的拒绝策略AbortPolicy也将无效。
         */
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
