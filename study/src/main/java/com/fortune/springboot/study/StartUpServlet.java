package com.fortune.springboot.study;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * 一般伴随着ServletContainerInitializer一起使用的还有HandlesTypes注解，
 * 通过HandlesTypes可以将感兴趣的一些类注入到ServletContainer Initializer的onStartup方法作为参数传入。
 *
 * @author fortune.wu
 * @date 17/10/27
 */
@HandlesTypes({})
public class StartUpServlet implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("自动测试servlet容器");
    }
}
