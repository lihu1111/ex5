package ex5;

import java.io.Serializable;

public class User implements Serializable {
    private String ip;
    private int port;
    private String id;

    public User() {
    }

    public User(String ip, int port, String id) {
        this.ip = ip;
        this.port = port;
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
