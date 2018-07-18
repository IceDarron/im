package logic;

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import org.apache.thrift.TException;

/**
 * Created by IceDarron 20180718.
 */
public abstract class IMHandler {
    protected final String _userid;
    protected final long  _netid;
    protected final Message _msg;
    protected ChannelHandlerContext _ctx;

    protected IMHandler(String userid, long netid, Message msg, ChannelHandlerContext ctx) {
        _userid = userid;
        _netid = netid;
        _msg = msg;
        _ctx = ctx;
    }

    protected abstract void excute(Worker worker) throws TException;
}
