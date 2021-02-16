package io.github.kimmking.gateway.outbound.okhttp;

import io.github.kimmking.gateway.filter.HeaderHttpResponseFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import java.util.List;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * Okhttp 请求处理 Handler
 * @author payne
 */
public class OkhttpOutboundHandler {

    /**
     * 请求过滤器
     */
    HttpResponseFilter filter = new HeaderHttpResponseFilter();

    /**
     * 请求路由器--随机算法
     */
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    private final List<String> backendUrls;

    public OkhttpOutboundHandler(List<String> backends) {

        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());
    }


    /**
     * 处理请求
     * @param fullRequest 请求
     * @param ctx ChannelHandlerContext
     * @param filter 请求过滤器
     */
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {

        // 请求路由，获取真实的后端服务地址
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();

        // 过滤请求
        filter.filter(fullRequest, ctx);

        // 请求真实的后端服务来处理请求
        executeRequest(fullRequest, ctx, url);
    }


    /**
     * 通过 OkHttp 请求真实的后端服务，并将响应返回给调用方
     * @param fullRequest 请求
     * @param ctx ChannelHandlerContext
     * @param url 真实的后端服务 URL
     */
    private void executeRequest(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String url) {

        FullHttpResponse response = null;
        try {

            // 请求真实的后端服务来处理请求
            String result = OkHttpClientUtils.doGet(url);

            byte[] body = result.getBytes();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            response.headers().set("Content-Type", "application/json");

            // 过滤响应
            filter.filter(response);

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    // 响应给调用方
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
        ctx.flush();
    }


    /**
     * 格式化 URL
     */
    private String formatUrl(String backend) {
        return backend.endsWith("/") ? backend.substring(0,backend.length()-1) : backend;
    }


    /**
     * 异常处理
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
