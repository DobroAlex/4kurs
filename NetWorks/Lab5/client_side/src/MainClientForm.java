import javax.swing.*;
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
                logger.info("Sending MESSAGE:::" + MessageTextField.getText());
                chatClient.out.println("MESSAGE:::" + MessageTextField.getText());
                MessageTextField.setText("");
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
                if (chatClient.out != null){
                    try {
                        chatClient.out.println("CLIENT_IS_DISCONNECTING:::");
                        chatClient.out.close();
                        chatClient.in.close();
                    }
                    catch (Exception ex)
                    {
                        chatClient = null;
                        return;
                    }
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
