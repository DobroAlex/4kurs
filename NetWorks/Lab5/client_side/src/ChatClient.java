import  java.io.*;
import  java.net.*;
import java.util.logging.Logger;
import javax.swing.*;
public class ChatClient extends  Thread {
    BufferedReader in;
    PrintWriter out;
    String name;
    String serverAddress;
    public String receivedMessage = null;
    int PORT;
    public  ChatClient.CustomOutputStream outputStream;
    public Logger logger =  Logger.getLogger(ChatClient.class.getName());

    public ChatClient(String name, String serverAddress, int PORT,JTextArea jTextArea){
        this.name = name;
        this.serverAddress = serverAddress;
        this.PORT = PORT;
        logger.info(String.format("name = %s, serverAddress = %s, PORT = %d", name, serverAddress, PORT));
        outputStream = new CustomOutputStream(jTextArea);
    }

    @Override
    public void  run() {
        try {
            Socket socket = new Socket(serverAddress, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true){
                String line = in.readLine();
                if (line.startsWith("GET_NAME:::")) {
                    out.println(this.name);
                    logger.info("Received GET_NAME:::, responding with" + this.name);
                }
                //out.println("MESSAGE:::");
                if (line.startsWith("NAME_IS_NOT_UNIQUE:::"))
                {
                    logger.info("Received NAME_IS_NOT_UNIQUE");
                    throw  new RuntimeException("Name " + name +" is not unique");
                }
                if (line.startsWith("NAME_ACCEPTED:::"))
                {
                    logger.info("Received NAME_ACCEPTED");
                }
                if (line.startsWith("MESSAGE:::"))
                {
                    line = line.replaceFirst("MESSAGE:::", "");
                    logger.info("MESSAGE::: = " + line);
                    System.out.println(line);
                    outputStream.write(line);
                }
            }
        }
        catch (Exception e){
            try {
                out.close();
                in.close();
                return;
            }
            catch (IOException e1) {
                return;
            }


        }
        finally {
            try {
                out.close();
                in.close();
                return;
            }
            catch (Exception e){
                return;
            }
        }
    }
    private    class  CustomOutputStream extends  OutputStream{
        private JTextArea textArea;
        public CustomOutputStream(JTextArea textArea){
            this.textArea = textArea;
        }
        @Override
        public void write(int b)
        {
            textArea.append(String.valueOf(b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
        public void write(String s){
            textArea.append(s + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
