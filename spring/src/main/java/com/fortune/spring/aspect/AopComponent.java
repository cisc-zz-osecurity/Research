package com.fortune.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author fortune.wu
 * @date 2018/7/16
 */
@Component
@Aspect
public class AopComponent {
    @Pointcut("execution(* com.fortune.spring.aspect.AopProxyImpl.*(..))")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public void around(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String name = signature.getMethod().getName();
        System.out.println(name + " process starting ...");
        try {
            point.proceed();
        } catch (Throwable throwable) {

        } finally {
            System.out.println(name + " process ended ...");
        }
    }
}
