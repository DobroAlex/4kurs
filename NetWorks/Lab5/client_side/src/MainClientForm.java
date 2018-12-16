import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                if (MessageTextField.getText().isBlank()){
                    return;
                }
                chatClient.out.println("MESSAGE:::"+ MessageTextField.getText());
                MessageTextField.setText("");
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
