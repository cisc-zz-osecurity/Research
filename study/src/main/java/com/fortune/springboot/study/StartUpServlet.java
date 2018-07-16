package com.fortune.springboot.study;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 一般伴随着ServletContainerInitializer一起使用的还有HandlesTypes注解，
 * 通过HandlesTypes可以将感兴趣的一些类注入到ServletContainer Initializer的onStartup方法作为参数传入。
 *
 * 只有用容器启动才会有用
 *
 * @author fortune.wu
 * @date 17/10/27
 */
public class StartUpServlet implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("Spring start--------");
    }
}
