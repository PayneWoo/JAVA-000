package io.github.kimmking.gateway.router;

import java.util.List;

public interface HttpEndpointRouter {

    /**
     * 路由
     * @param endpoints 最终请求URL
     */
    String route(List<String> endpoints);
    
    // Load Balance
    // Random
    // RoundRibbon 
    // Weight
    // - server01,20
    // - server02,30
    // - server03,50
    
}
