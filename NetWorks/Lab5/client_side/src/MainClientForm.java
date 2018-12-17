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
    public ChatClient chatClient;
    public Logger logger = Logger.getLogger(MainClientForm.class.getName());
    public MainClientForm() {
        ConnectToServerButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatClient.name == NickNameTextField.getText() && chatClient.isNameAccepted){
                    logger.info("Stopped attempt to reconnect with existing and accepted name");
                    return;
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
                    LEDMayISendMessageLabel.setForeground(Color.GREEN);

                    logger.info("Sending MESSAGE:::" + MessageTextField.getText());
                    chatClient.sendMessage(MessageTextField.getText());
                    MessageTextField.setText("");
                }
                else {
                    LEDMayISendMessageLabel.setForeground(Color.RED);
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
                chatClient.sendDisconnectMessage();
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
