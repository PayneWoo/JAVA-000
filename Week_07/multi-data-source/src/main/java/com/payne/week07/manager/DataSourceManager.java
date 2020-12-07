package com.payne.week07.manager;



/**
 * 数据源管理类
 * @author Payne
 * @date 2020/12/6
 */
public class DataSourceManager {

    private static final ThreadLocal<String> ROUTE_KEY = new ThreadLocal<>();

    /**
     * 获取当前线程的数据源路由的key
     */
    public static String getRouteKey() {
        String key = ROUTE_KEY.get();
        return key;
    }

    /**
     * 绑定当前线程数据源路由的key
     * 使用完成后必须调用removeRouteKey()方法删除
     */
    public static void  setRouteKey(String key) {
        ROUTE_KEY.set(key);
    }

    /**
     * 删除与当前线程绑定的数据源路由的key
     */
    public static void removeRouteKey() {
        ROUTE_KEY.remove();
    }
}
