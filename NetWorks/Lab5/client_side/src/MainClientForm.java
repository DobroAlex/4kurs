import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class MainClientForm {
    private JPanel panel1;
    private JTextField ServerIPAddressTextField;
    private JTextField ServerPortTextField;
    private JTextField NickNameTextField;
    private JButton ConnectToServerButton;
    private JTextField MessageTextField;
    private JButton SendMessageButton;
    private JTextArea ChatTextArea;
    private JButton DisconnectButton;
    private JLabel ServerIPAddressLabel;
    private JLabel ServerPortLabel;
    private JLabel NickNameLabel;
    private JLabel ChatLabel;
    private JLabel mayISendMessageLabel;
    private JLabel LEDMayISendMessageLabel;
    private JScrollPane ChatTextScrollPane;
    public ChatClient chatClient = null;
    public Logger logger = Logger.getLogger(MainClientForm.class.getName());
    public MainClientForm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    logger.info("Permission checker is alive");
                    if (chatClient != null){
                        if (chatClient.isNameAccepted && chatClient.isAlive() && chatClient.getOut() != null){
                            LEDMayISendMessageLabel.setForeground(Color.GREEN);
                        }
                        else{
                            LEDMayISendMessageLabel.setForeground(Color.RED);
                        }
                    }
                }
            }
        }).start();
        ConnectToServerButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (chatClient !=  null){
                    if (chatClient.name ==  NickNameTextField.getText() && chatClient.isNameAccepted ){
                        logger.info(chatClient.name + " is already connected to server, aborting attempt to reconnect");
                        return;
                    }
                }
                chatClient = new ChatClient(
                        NickNameTextField.getText(),
                        ServerIPAddressTextField.getText(),
                        Integer.parseInt(ServerPortTextField.getText()),
                        ChatTextArea);
                chatClient.start();
                logger.info("Created new chatClient :" + chatClient);


            }
        });
        SendMessageButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatClient.isNameAccepted) {
                    //LEDMayISendMessageLabel.setForeground(Color.GREEN);

                    logger.info("Sending MESSAGE:::" + MessageTextField.getText());
                    chatClient.sendMessage(MessageTextField.getText());
                    MessageTextField.setText("");
                }
                else {
                    //LEDMayISendMessageLabel.setForeground(Color.RED);
                    return;
                }
            }
        });
        DisconnectButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatClient != null) {
                    chatClient.sendDisconnectMessage();
                }
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("MainClientForm");
        frame.setContentPane(new MainClientForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
