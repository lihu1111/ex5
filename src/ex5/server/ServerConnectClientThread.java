package ex5.server;

import ex5.Message;
import ex5.MessageType;
import ex5.client.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;

/**
 * 用于于客户端通信
 */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String id;//发送信息的用户id
    public ServerConnectClientThread(Socket socket, String id) {
        this.socket = socket;
        this.id = id;
    }
    public Socket getSocket() {
        return socket;
    }

    //发送接受消息(客户端）
    @Override
    public void run() {
        while(true) {
            try {
//                System.out.println("服务器和客户端" + id +  "保持通讯，读取数据....");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
//                    ObjectOutputStream oos =
//                        new ObjectOutputStream(ManageClientThread.getClientThread(message.getSender()).getSocket().getOutputStream());
//                    oos.writeObject(message);
//                    ManageClientThread.remove(message.getSender());
                    for(ServerConnectClientThread serverConnectClientThread :
                            ManageClientThread.getHm().values()) {
                        ObjectOutputStream oos =
                                new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                    }
                    //人数减一
                    ManageClientThread.remove(message.getSender());

                    Server.CountMinus();
                    socket.close();
                    break;
                } else if(message.getMesType().equals(MessageType.MESSAGE_COMM)) {
                    //获得所有socket,写给所有客户端
                    for(ServerConnectClientThread serverConnectClientThread :
                            ManageClientThread.getHm().values()) {
                        ObjectOutputStream oos =
                                new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());

                        oos.writeObject(message);
                    }
                } else if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE)) {
                    Message message1 = new Message();
                    message1.setSender(message.getSender());
                    message1.setMesType(MessageType.MESSAGE_GET_ONLINE);
                    Date d = new Date();
                    message1.setMesTime(d.toString());
                    for(ServerConnectClientThread serverConnectClientThread :
                            ManageClientThread.getHm().values()) {
                        ObjectOutputStream oos =
                                new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message1);
                    }
                } else if(message.getMesType().equals(MessageType.MESSAGE_KICK_EXIT)) {
                    ManageClientThread.remove(message.getSender());
                    //人数减一
                    Server.CountMinus();
                    socket.close();
                    break;
                } else if (message.getMesType().equals(MessageType.END)){
                    ManageClientThread.remove(message.getSender());
                    //人数减一
                    Server.CountMinus();
                    socket.close();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
