package ex5.client;

import ex5.Message;
import ex5.MessageType;
import ex5.client.ManageClientConnectServerThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 该类提供发消息的方法
 */
public class MessageClientService {
    public static void sendMessage(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setSender(senderId);
        message.setReceiver(getterId);
        message.setContent(content);
        message.setMesType(MessageType.MESSAGE_COMM);
        message.setMesTime(new Date().toString());
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getCurrentClient(senderId).getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
