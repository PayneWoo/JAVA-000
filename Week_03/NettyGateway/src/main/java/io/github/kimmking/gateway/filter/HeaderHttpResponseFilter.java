package io.github.kimmking.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;


/**
 * 响应过滤器
 * @author payne
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("kk", "java-1-nio");
    }
}
