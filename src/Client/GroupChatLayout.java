package Client;

import DrawBoard.Brush;
import DrawBoard.ColorChooser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GroupChatLayout extends JFrame{
    String nickName, ipAddress;
    int portNum;
    private JPanel GroupChatPanel;
    private JTextField ChatField;
    private JButton SendButton;
    private JButton DeleteButton;
    private JButton EraserButton;
    private JButton ColorButton;
    private JButton DrawButton;
    private JSpinner LineSpinner;
    private JTextPane ChatTextPane;
    private JPanel DrawPanel;
    JTextArea UserList;
    private JTextArea ChatTextArea;
    private JLabel Label;
    private JButton WeatherButton;
    GroupChatBack groupChatBack = new GroupChatBack();
    BufferedImage imgBuff;
    Brush brush;
    ColorChooser colorChooser;
    Color tmpColor = Color.BLACK;

    public GroupChatBack getGroupChatBack() {
        return groupChatBack;
    }

    public GroupChatLayout(String nickName, String roomName, String ipAddress, int portNum) {
        this.nickName = nickName;
        this.portNum = portNum;
        setContentPane(GroupChatPanel);
        setSize(1280, 720);
        setTitle(roomName);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = ChatField.getText().trim();
                if (Message.length() > 0) {
                    groupChatBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });
        ChatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = ChatField.getText().trim();
                if (Message.length() > 0) {
                    groupChatBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });

        DrawPanel.setBackground(Color.WHITE);
        DrawPanel.setOpaque(true);

        imgBuff = new BufferedImage(DrawPanel.getWidth(), DrawPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);

        JLabel drawLabel = new JLabel(new ImageIcon(imgBuff));
        drawLabel.setSize(DrawPanel.getSize());

        brush = new Brush();
        brush.getSize(DrawPanel.getSize());

        DrawPanel.add(drawLabel);
        DrawPanel.add(brush);

        drawLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public synchronized void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                brush.setX(e.getX());
                brush.setY(e.getY());
                groupChatBack.sendMessage("!Drawing" + brush.x + ":" + brush.y);
                brush.repaint();
                brush.printAll(imgBuff.getGraphics());
            }
        });
        ColorButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                colorChooser = new ColorChooser();
                colorChooser.setGroupChatLayout(GroupChatLayout.this);
            }
        });
        DrawButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                groupChatBack.sendMessage("!ColorChanged" + tmpColor.getRGB());
            }
        });
        EraserButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                tmpColor = brush.getColor();
                groupChatBack.sendMessage("!ColorChanged" + Color.WHITE.getRGB());
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                tmpColor = brush.getColor();
                groupChatBack.sendMessage("!SetClear");
                groupChatBack.sendMessage("!ColorChanged" + Color.BLACK.getRGB());
            }
        });

        groupChatBack.setGui(this);
        groupChatBack.setUserInfo(nickName, roomName, ipAddress, portNum);
        groupChatBack.start(); // 채팅창이 켜짐과 동시에 접속을 실행해줍니다.
        LineSpinner.setValue(10);
        LineSpinner.addChangeListener(new ChangeListener() {
            @Override
            public synchronized void stateChanged(ChangeEvent e) {
                brush.setSize((Integer) LineSpinner.getValue());
                groupChatBack.sendMessage("!ThickChanged" + (Integer) LineSpinner.getValue());
                if ((Integer) LineSpinner.getValue() < 1) {
                    LineSpinner.setValue(1);
                } else if ((Integer) LineSpinner.getValue() > 50) {
                    LineSpinner.setValue(50);
                }
            }
        });
        WeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WeatherLayout();
            }
        });
    }


    public void appendMessage(String Message) {
        ChatTextArea.append(Message);
    }

    public void resetUserListArea(ArrayList<String> nickNameList) {
        // 유저목록을 유저리스트에 띄워줍니다.
        for (String nickName : nickNameList) {
            UserList.append(nickName + "\n");
        }
    }

    public synchronized Brush getBrush() {
        return this.brush;
    }

    public synchronized void brushBuff() {
        brush.printAll(imgBuff.getGraphics());
    }
}
