package com.sun.hotelproject.utils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author sun
 * Created by a'su's on 2018/6/26.
 * 单利任务调度线程池
 */

public class ThreadScheduledExecutorUtil {

    private static volatile ThreadScheduledExecutorUtil mThreadScheduledExecutorUtil = null;

    private static ScheduledThreadPoolExecutor service;

    private ThreadScheduledExecutorUtil() {
        service = new ScheduledThreadPoolExecutor(10);
    }

    public static ThreadScheduledExecutorUtil getInstance() {
        synchronized (ThreadScheduledExecutorUtil.class) {
            if (mThreadScheduledExecutorUtil == null) {
                mThreadScheduledExecutorUtil = new ThreadScheduledExecutorUtil();
            }
        }
        return mThreadScheduledExecutorUtil;
    }

    public ScheduledExecutorService getService() {
        return service;
    }


    public static void shutdown() {
        if (!service.isShutdown()) {
            service.shutdown();
        }
    }
}
