package com.fortune.spring.aspect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fortune.wu
 * @date 2018/7/16
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfiguration.class)
public class AopProxyTest {
    @Autowired
    private AopProxy aopProxy;

    @Test
    public void getA() {
        aopProxy.getA();
    }
}
