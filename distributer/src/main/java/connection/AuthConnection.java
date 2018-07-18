package connection;

import code.PacketDecoder;
import code.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IceDarron 20180718.
 */
public class AuthConnection {
    private static final Logger logger = LoggerFactory.getLogger(AuthConnection.class);

    public static void startGateAuthConnection(String ip, int port) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel)
                            throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();

                        pipeline.addLast("MessageDecoder", new PacketDecoder());
                        pipeline.addLast("MessageEncoder", new PacketEncoder());
                        pipeline.addLast("GateAuthConnectionHandler", new GateAuthConnectionHandler());  //Auth -> gate
                    }
                });

        bootstrap.connect(ip, port);
    }
}
