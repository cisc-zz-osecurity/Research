package com.fortune.spring.aspect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author fortune.wu
 * @date 2018/7/17
 */
public class JdkDynamicProxyTest {
    @Test
    public void getCOne() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取动态代理类
        Class<?> proxyClass = Proxy.getProxyClass(ClassLoader.getSystemClassLoader(), AopProxy.class);
        //获得代理类的构造函数，并传入参数类型InvocationHandler.class
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        //通过构造函数来创建动态代理对象，将自定义的InvocationHandler实例传入
        AopProxy aopProxy = (AopProxy) constructor.newInstance(new JdkDynamicProxyHandler(new AopProxyImpl()));
        //通过代理对象调用目标方法
        aopProxy.getC();
    }
    @Test
    public void getCTwo() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        JdkDynamicProxyHandler jdkDynamicProxyHandler = new JdkDynamicProxyHandler(new AopProxyImpl());
        AopProxy aopProxy = (AopProxy) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{AopProxy.class}, jdkDynamicProxyHandler);
        aopProxy.getC();
    }
}
