package ex5.client;

import ex5.server.ManageClientThread;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    //把所有客户端线程放入
    private static HashMap<String, Chat> hm = new HashMap<>();
    public static void add(String id,Chat chat) {
        hm.put(id, chat);
    }
    public static Chat getCurrentClient(String id) {
        return hm.get(id);
    }
    public static void remove(String id) {
        hm.remove(id);
    }
}
