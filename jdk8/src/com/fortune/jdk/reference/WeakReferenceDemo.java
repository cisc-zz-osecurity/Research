package com.fortune.jdk.reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 *
 * @author fortune wu
 * @date 2018/8/15
 */
public class WeakReferenceDemo {

    /**
     * 弱引用也是用来描述非必需对象的，
     * 当JVM进行垃圾回收时，无论内存是否充足，
     * 都会回收被弱引用关联的对象
     * @param args
     */
    public static void main(String[] args) {

        WeakReference<String> sr = new WeakReference<String>(new String("hello"));

        /**
         * 第二个输出结果是null，这说明只要JVM进行垃圾回收，被弱引用关联的对象必定会被回收掉。
         * 不过要注意的是，这里所说的被弱引用关联的对象是指只有弱引用与之关联，
         * 如果存在强引用同时与之关联，则进行垃圾回收时也不会回收该对象（软引用也是如此-->内存不足）。
         */
        System.out.println(sr.get());
        System.gc();                  //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());
    }
}
