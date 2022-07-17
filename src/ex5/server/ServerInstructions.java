package ex5.server;

import ex5.Message;
import ex5.MessageType;
import ex5.client.ManageClientConnectServerThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Scanner;

public class ServerInstructions extends Thread{
    Scanner in = new Scanner(System.in);
    ServerSocket serverSocket;
    public ServerInstructions(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("请输入指令， end--结束程序; count--聊天者数量; " +
                    "chatters--列出所有聊天者; kickout--空格 + 昵称--提出聊天室");
            String inst = in.next();
            if(inst.equals("end")) {
                Message message = new Message();
                message.setMesType(MessageType.END);
                try {

                    ManageClientThread.getHm().forEach((id, thread) -> {
                        message.setReceiver(id);
                        ObjectOutputStream oos =
                                null;
                        try {
                            oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
                            oos.writeObject(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Server.quit();//退出
            } else if(inst.equals("count")) {
                System.out.println("共有" + Server.count + "个聊天者");
            } else if(inst.equals("chatters")) {
                if(Server.getCount() == 0) {
                    System.out.println("目前还没有人进入群聊");
                } else {
                    for (String userId : ManageClientThread.getHm().keySet()) {
                        System.out.print(userId + " ");
                    }
                    System.out.println();
                }
            } else if(inst.equals("kickout")) {
                String id = in.next();
                try {
                    Message message = new Message();
                    message.setMesType(MessageType.MESSAGE_KICK_EXIT);
                    message.setReceiver(id);
                    ObjectOutputStream oos =
                            new ObjectOutputStream(ManageClientThread.getClientThread(id).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
