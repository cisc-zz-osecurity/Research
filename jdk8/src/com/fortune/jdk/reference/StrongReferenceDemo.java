package com.fortune.jdk.reference;

/**
 * 强引用
 *
 * @author fortune wu
 * @date 2018/8/15
 */
public class StrongReferenceDemo {

    /**
     *只要某个对象有强引用与之关联，JVM必定不会回收这个对象，即使在内存不足的情况下，
     * JVM宁愿抛出OutOfMemory错误也不会回收这种对象.
     *
     * 如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，
     * 这样一来的话，JVM在合适的时间就会回收该对象。
     *
     * @param args
     */
    public static void main(String[] args) {
        new StrongReferenceDemo().fun1();
    }

    public void fun1() {
        Object object = new Object();
        /**
         * 如果内存不足，JVM会抛出OOM错误也不会回收object指向的对象。
         * 不过要注意的是，当fun1运行完之后，object和objArr都已经不存在了，所以它们指向的对象都会被JVM回收。
         */
        Object[] objArr = new Object[1000];
    }
}
