package io.github.kimmking.gateway.router;

import java.util.List;

/**
 * 请求路由----轮询算法
 * @author Payne
 * @date 2021/2/17
 */
public class RoundRibbonHttpEndpointRouter implements HttpEndpointRouter {

    /**
     * 请求计数器
     */
    private static int flag = 0;

    @Override
    public String route(List<String> urls) {

        int size = urls.size();
        String url = urls.get(flag % size);
        flag ++;
        return url;
    }
}
