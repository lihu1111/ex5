package ex5.client;

import ex5.Message;
import ex5.MessageType;
import ex5.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnectServer {
    Socket socket;
    Message message;
    User u = new User();
    static boolean c = false;
    boolean check(String ip, int port, String id) {
        u.setIp(ip);
        u.setPort(port);
        u.setId(id);
        try {
            //传输用户信息
            socket = new Socket(InetAddress.getByName(ip), port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(u);
//            socket.shutdownOutput();

            //接受回送信息
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            message = (Message)objectInputStream.readObject();
            if(message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) { //登录成功
                // 创建聊天线程
                Chat chat = new Chat(socket);
                chat.start(); // 开启线程

                ManageClientConnectServerThread.add(id,chat);
                c = true;
            } else {
                socket.close();
                c= false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
    //编写退出客户端发送消息
    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getId());
        try{
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getCurrentClient(u.getId()).socket.getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getId() + "退出系统");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void login() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE);
        message.setSender(u.getId());
        try{
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getCurrentClient(u.getId()).socket.getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getId() + "登录");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
