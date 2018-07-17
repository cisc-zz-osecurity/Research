package com.fortune.spring.aspect;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author fortune.wu
 * @date 2018/7/16
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScans(@ComponentScan(basePackages = "com.fortune.spring.aspect"))
public class AopConfiguration {
}
