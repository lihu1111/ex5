package ex5.server;

import java.util.HashMap;

/**
 * 该类用于管理和客户端通信的线程
 */
public class ManageClientThread {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();
    public static void addClientThread(String id, ServerConnectClientThread s) {
        hm.put(id, s);
    }
    public static ServerConnectClientThread getClientThread(String id) {
        return hm.get(id);
    }
    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }
    public static void remove(String id) {
        hm.remove(id);
    }
}
