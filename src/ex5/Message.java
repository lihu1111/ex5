package ex5;

import java.io.Serializable;

public class Message implements Runnable, Serializable {
    private String sender;//发送者
    private String receiver;//接收者
    private String content;//内容
    private String mesTime;//消息时间
    private String mesType;//消息类型

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public Message() {
    }

    public Message(String sender, String receiver, String content, String mesTime, String mesType) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.mesTime = mesTime;
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMesTime() {
        return mesTime;
    }

    public void setMesTime(String mesTime) {
        this.mesTime = mesTime;
    }

    @Override
    public void run() {

    }
}
