package ex5.client;


import ex5.Message;
import ex5.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Chat extends Thread{
    Socket socket;
    public Chat(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                //要把内容加到聊天框里不急
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE)) {
                    Client.getJTextArea1().append("[" + message.getMesTime()+ "]: " + message.getSender() +"已进入聊天室\n");
                } else if(message.getMesType().equals(MessageType.MESSAGE_COMM)) {
                    Client.getJTextArea1().append("[" + message.getMesTime() + ": " + message.getSender() + "]: "
                            + message.getContent() + "\n");
//                    System.out.println("[" + message.getSender() + message.getMesTime() + "]:"
//                            + message.getContent());
                } else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    Client.getJTextArea1().append(message.getSender() + "退出聊天室");
                    ManageClientConnectServerThread.remove(message.getSender());
                    socket.close();
                    break;
                } else if(message.getMesType().equals(MessageType.MESSAGE_KICK_EXIT)) {
                    ObjectOutputStream oos =
                            new ObjectOutputStream(ManageClientConnectServerThread.getCurrentClient(message.getReceiver()).getSocket().getOutputStream());
                    Message message1 = new Message();
                    message1.setSender(message.getReceiver());
                    message1.setMesType("5");
                    oos.writeObject(message1);
                    ManageClientConnectServerThread.remove(message.getReceiver());
                    socket.close();
                    System.exit(0);
                    Client.kickOut = true;
                    break;
                } else if(message.getMesType().equals(MessageType.END)) {
                    ObjectOutputStream oos =
                            new ObjectOutputStream(ManageClientConnectServerThread.getCurrentClient(message.getReceiver()).getSocket().getOutputStream());
                    Message message1 = new Message();
                    message1.setMesType("7");
                    message1.setSender(message.getReceiver());
                    oos.writeObject(message1);
                    ManageClientConnectServerThread.remove(message.getReceiver());
                    socket.close();
                    System.exit(0);
                    Client.kickOut = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
