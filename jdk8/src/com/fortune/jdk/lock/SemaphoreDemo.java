package com.fortune.jdk.lock;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 信号量demo
 */
public class SemaphoreDemo {

    static class Parking {
        /**
         * 信号量
         */
        private Semaphore semaphore;

        public Parking(int args) {
            this.semaphore = new Semaphore(args);
        }

        public void park() {
            try {
                /**
                 * 循环,一直阻塞
                 */
                semaphore.acquire(1);
                long time = new Random().nextInt(10);
                System.out.println(Thread.currentThread().getName() + "进入停车场，停车" + time + "秒...");
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "开出停车场...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

    static class Car implements Runnable {

        Parking parking ;

        Car(Parking parking){
            this.parking = parking;
        }

        @Override
        public void run() {
            /**
             * 停车
             */
            parking.park();
        }
    }

    public static void main(String[] args) {
        Parking parking = new Parking(5);
        for (int i = 0; i < 7; i++) {
            new Thread(new Car(parking)).start();
        }
    }
}
