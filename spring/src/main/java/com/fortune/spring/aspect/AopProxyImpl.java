package com.fortune.spring.aspect;

import org.springframework.stereotype.Service;

/**
 * @author fortune.wu
 * @date 2018/7/16
 */
@Service
public class AopProxyImpl implements AopProxy {
    @Override
    public void getA() {
        System.out.println("a process....");
        getB();
    }

    @Override
    public void getB() {
        System.out.println("b process....");
    }

    @Override
    public void getC() {
        System.out.println("c process....");
        getB();
    }
}
