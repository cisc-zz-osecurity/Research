package com.fortune.spring.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author fortune.wu
 * @date 2018/7/17
 */
public class JdkDynamicProxyHandler implements InvocationHandler {
    private Object targetSource;

    public JdkDynamicProxyHandler(Object targetSource) {
        this.targetSource = targetSource;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk dynamic proxy " + method.getName() + " starting ...");
        Object invoke = method.invoke(targetSource, args);
        System.out.println("jdk dynamic proxy " + method.getName() + " ended ...");
        return invoke;
    }
}
