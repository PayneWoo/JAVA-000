package io.github.kimmking.gateway.inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
public class HttpInboundServer {

    private int port;
    
    private List<String> proxyServers;

    public HttpInboundServer(int port, List<String> proxyServers) {
        this.port=port;
        this.proxyServers = proxyServers;
    }

    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 128)
                    // 开启 Nagle 算法，高并发下，关闭 Nagle 算法，性能更好
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 keepalive(长连接)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 重用地址（四次挥手的 CLOSE_WAIT状态时）
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    // 接收缓冲区，32K
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    // 发送缓冲区，32K
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // 重用端口（四次挥手的 CLOSE_WAIT状态时）
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 配置内存池 byteBuffer
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 加上 NioServerSocketChannel
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    // 设置 pipeLine
                    .childHandler(new HttpInboundInitializer(this.proxyServers));

            Channel ch = b.bind(port).sync().channel();
            System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
