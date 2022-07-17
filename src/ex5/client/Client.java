package ex5.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Client extends JFrame  {
    static boolean kickOut = false;
    ClientConnectServer ccs = new ClientConnectServer();
    private static JTextArea jTextArea1 = new JTextArea(); //大屏幕
    private JTextArea jTextArea2 = new JTextArea(5,40); // 消息发送框
    private JButton jb = new JButton("发送"); //发送按钮
    private JPanel bottom = new JPanel();
    JLabel jLabel1 = new JLabel("ip");
    JTextField field1 = new JTextField(5);
    JLabel jLabel2 = new JLabel("port");
    JTextField field2 = new JTextField(3);
    JLabel jLabel3 = new JLabel("id");
    JTextField field3 = new JTextField(3);
    JButton jb1 = new JButton("登录");
    JButton jb2 = new JButton("退出");
    JPanel north = new JPanel();
    public Client() {
        jTextArea1.setEnabled(false);
        jb2.setEnabled(false);
        field2.setText("9999");
        field2.setEnabled(false);
        north.add(jLabel1); north.add(field1);
        north.add(jLabel2); north.add(field2);
        north. add(jLabel3);north. add(field3);
        north.add(jb1); north.add(jb2);
        //可换行
        jTextArea1.setLineWrap(true);
        jTextArea2.setLineWrap(true);
        bottom.add(jTextArea2); bottom.add(jb);
        addListener();
        //jb.addActionListener(event -> jTextArea1.append());
        add(north, BorderLayout.NORTH);
        add(jTextArea1, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        setSize(500,500);
        setLocationByPlatform(true);// 根据平台选择合适的位置
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("多人聊天室 -- author 李虎");
    }
    public static void main(String[] args) {
        Client client = new Client();
//        while(true) {
//            if(kickOut) {
//                System.out.println("true");
//            }
//            if(kickOut) {
//                JOptionPane.showMessageDialog(client, "你已被踢出群聊", "消息", JOptionPane.WARNING_MESSAGE);
//                System.exit(0);
//            }
//        }
    }

    public static void setKickOut(boolean kickOut) {
        Client.kickOut = kickOut;
    }

    private class Actions implements KeyListener, ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().getClass() == JButton.class) {
                JButton jButton = (JButton) e.getSource();
                if(jButton.getText().equals("登录")) {
                    String ip = field1.getText();
                    int port = Integer.parseInt(field2.getText());
                    String id = field3.getText();
                    //登录成功
                    if(ccs.check(ip, port, id)) {
                        ccs.login();
                        jb2.setEnabled(true);
                        jb1.setEnabled(false);
                        field1.setEnabled(false);
                        field2.setEnabled(false);
                        field3.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(Client.this, "群中已有相同昵称，请修改", "消息",JOptionPane.WARNING_MESSAGE);
                    }
                }
                if(jButton.getText().equals("退出")) {
                    ccs.logout();
                    System.exit(0);
                }
                if(jButton.getText().equals("发送")){
                    //通信  要获得Message对象
//                    jTextArea1.append(field3.getText() + ":" + jTextArea2.getText());//名字和
                    MessageClientService.sendMessage(jTextArea2.getText(), field3.getText(), "all");

                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                //通信.........  要获得Message对象
                MessageClientService.sendMessage(jTextArea2.getText(), field3.getText(), "all");
                jTextArea2.setText("");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            jTextArea2.setText("");
        }
    }

    void addListener() {
        Actions actions = new Actions();
        jb1.addActionListener(actions);
        jb2.addActionListener(actions);
        jb.addActionListener(actions);
        jTextArea2.addKeyListener(actions);
    }
    public static JTextArea getJTextArea1() {
        return jTextArea1;
    }
}