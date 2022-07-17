package ex5.server;

import ex5.Message;
import ex5.MessageType;
import ex5.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server{
    ServerInstructions serverInstructions = null;
    private ServerSocket serverSocket = null;
    //private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();//模拟数据库
    static int count = 0;
    public Server() {
        try {
            System.out.println("服务端正在监听9999端口......");
            serverSocket = new ServerSocket(9999);
            //开启指令线程进行指令操作
            serverInstructions = new ServerInstructions(serverSocket);
            serverInstructions.start();
            while(true) {
                //一直监听
                Socket socket = serverSocket.accept();
                //收到登录信息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User)ois.readObject();
                //回送信息
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                // String sender, String receiver, String content, String mesTime, String mesType
                if(!ManageClientThread.getHm().containsKey(u.getId())) { //合法
                    //validUsers.put(u.getId(), u);
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED); //发送信息
                    oos.writeObject(message);
                    //开启线程与客户端连接
                    ServerConnectClientThread serverThread = new ServerConnectClientThread(socket, u.getId());
                    serverThread.start();//开启线程
                    count++;
                    //加入线程
                    ManageClientThread.addClientThread(u.getId(), serverThread);
                } else {
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(message);
                    //如果登录失败，关闭socket
                    socket.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new Server();
    }

//    public static void remove(String id) {
//        validUsers.remove(id);
//    }

    public static int getCount() {
        return count;
    }
    public static void CountMinus() {
        count--;
    }
//    public static ConcurrentHashMap<String, User> getValidUsers() {
//        return validUsers;
//    }
    public static void quit() {

        System.exit(0);
    }

}
