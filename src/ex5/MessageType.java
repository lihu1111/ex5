package ex5;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//登录成功
    String MESSAGE_LOGIN_FAILED = "2";//登录失败
    String MESSAGE_COMM = "3";//普通
    String MESSAGE_GET_ONLINE = "4";//获取聊天者
    String MESSAGE_KICK_EXIT = "5";//返回在线列表
    String MESSAGE_CLIENT_EXIT = "6";//退出
    String END = "7";
}
