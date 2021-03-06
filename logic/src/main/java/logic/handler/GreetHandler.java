package logic.handler;

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import logic.IMHandler;
import logic.Worker;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.generate.internal.Internal;

/**
 * Created by IceDarron 20180718.
 */
public class GreetHandler extends IMHandler {
    private static final Logger logger = LoggerFactory.getLogger(GreetHandler.class);

    public GreetHandler(String userid, long netid, Message msg, ChannelHandlerContext ctx) {
        super(userid, netid, msg, ctx);
    }

    @Override
    protected void excute(Worker worker) throws TException {
        Internal.Greet msg = (Internal.Greet)_msg;
        Internal.Greet.From from = msg.getFrom();

        if(from == Internal.Greet.From.Auth) {
            LogicServerHandler.setAuthLogicConnection(_ctx);
            logger.info("[Auth-Logic] connection is established");
        } else if(from == Internal.Greet.From.Gate){
            LogicServerHandler.setGateLogicConnection(_ctx);
            logger.info("[Gate-Logic] connection is established");

        }
    }
}
