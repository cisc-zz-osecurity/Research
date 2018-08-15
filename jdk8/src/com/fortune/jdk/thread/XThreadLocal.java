package com.fortune.jdk.thread;

/**
 * @author fortune wu
 */
public class XThreadLocal {


    private final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    private static XThreadLocal xThreadLocal = new XThreadLocal();

    private XThreadLocal() {

    }

    public final static XThreadLocal getInstance() {
        if (xThreadLocal == null) {
            xThreadLocal = new XThreadLocal();
        }
        return xThreadLocal;
    }

    public void setCurrentUser(String userInfo) {
        threadLocal.set(userInfo);
    }

    public String getCurrentUser() {
        return threadLocal.get();

    }

}