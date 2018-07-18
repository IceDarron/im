package gate;

import protobuf.generate.cli2srv.chat.Chat;
import protobuf.generate.cli2srv.login.Auth;

import java.io.IOException;

/**
 * Created by IceDarron 20180718.
 */
public class TransferHandlerMap {
    public static void initRegistry() throws IOException {
        ClientMessage.registerTranferHandler(1000, ClientMessage::transfer2Auth, Auth.CLogin.class);
        ClientMessage.registerTranferHandler(1001, ClientMessage::transfer2Auth, Auth.CRegister.class);
        ClientMessage.registerTranferHandler(1003, ClientMessage::transfer2Logic, Chat.CPrivateChat.class);
    }
}
