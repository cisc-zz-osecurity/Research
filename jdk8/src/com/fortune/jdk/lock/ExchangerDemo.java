package com.fortune.jdk.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Exchange demo
 *
 * @author fortune wu
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
        new Thread(() -> {
            List<String> buffer = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                System.out.println("生产者第" + i + "次提供");
                for (int j = 1; j < 3; j++) {
                    System.out.println("生产者装入" + i  + "--" + j);
                    buffer.add("buffer：" + i + "--" + j);
                }

                System.out.println("生产者装满，等待与消费者交换...");
                try {
                    exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            List<String> buffer = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                System.out.println("消费者第" + i + "次提取");
                try {
                    buffer = exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int j = 1; j < 3; j++) {
                    System.out.println("消费者 : " + buffer.get(0));
                    buffer.remove(0);
                }
            }
        }).start();
    }
}
