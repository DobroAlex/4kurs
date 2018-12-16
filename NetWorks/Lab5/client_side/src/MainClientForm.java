import javax.swing.*;

public class MainClientForm {
    private JPanel panel1;
    private JTextField ServerIPAddressTextField;
    private JTextField ServerPortTextField;
    private JTextField NickNameTextField;
    private JButton ConnectToServerButton;
    private JTextField MessageTextField;
    private JButton SendMessageButton;
    private JTextArea textArea1;
    private JButton DisconnectButton;
    private JLabel ServerIPAddressLabel;
    private JLabel ServerPortLabel;
    private JLabel NickNameLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainClientForm");
        frame.setContentPane(new MainClientForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
