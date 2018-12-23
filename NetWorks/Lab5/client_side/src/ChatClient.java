import  java.io.*;
import  java.net.*;
import java.util.logging.Logger;
import javax.swing.*;
public class ChatClient extends  Thread {
    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    BufferedReader in;

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    PrintWriter out;
    String name = null;
    String serverAddress;
    public String receivedMessage = null;
    int PORT;
    public  ChatClient.CustomOutputStream outputStream;
    public Logger logger =  Logger.getLogger(ChatClient.class.getName());
    public boolean isNameAccepted = false;
    private  boolean isClientToBeStopped = false;
    public ChatClient(String name, String serverAddress, int PORT,JTextArea jTextArea){
        this.name = name;
        this.serverAddress = serverAddress;
        this.PORT = PORT;
        logger.info(String.format("name = %s, serverAddress = %s, PORT = %d", name, serverAddress, PORT));
        outputStream = new CustomOutputStream(jTextArea);
    }

    public  void sendMessage(String str){
        if (this.isNameAccepted && this.out != null){
            this.out.println("MESSAGE:::" + str);
        }
        return;
    }
    public void sendDisconnectMessage(){
        if (this.isNameAccepted && this.out != null){
            this.out.println("CLIENT_IS_DISCONNECTING:::");
            this.isClientToBeStopped = true;
        }
        return;
    }
    private void sendHeartbeat(){
        out.println("HEARTBEAT:::");
    }
    private void closeIOSockets()  {
        try {
            this.isNameAccepted = false;
            if (out != null) {
                out.close();
            }

            if (in != null) {
                in.close();
            }
            return;
        }
        catch (Exception ex){
            logger.info("Cant close IO stream");
            return;
        }

    }

    @Override
    public void  run() {
        try {
            Socket socket = new Socket(serverAddress, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true){
                if (this.isClientToBeStopped){
                    throw new RuntimeException("isClientToBeStopped occurred, probably pushed \"Disconnect\"");
                }
                String line = in.readLine();
                if (line.startsWith("GET_NAME:::")) {
                    out.println(this.name);
                    logger.info("Received GET_NAME:::, responding with" + this.name);
                }
                //out.println("MESSAGE:::");
                else if (line.startsWith("NAME_IS_NOT_UNIQUE:::"))
                {
                    logger.info("Received NAME_IS_NOT_UNIQUE");
                    throw  new RuntimeException("Name " + name +" is not unique");
                }
                else if (line.startsWith("NAME_ACCEPTED:::"))
                {
                    logger.info("Received NAME_ACCEPTED");
                    this.isNameAccepted = true;

                }
                else if (line.startsWith("MESSAGE:::"))
                {
                    line = line.replaceFirst("MESSAGE:::", "");
                    logger.info("MESSAGE::: = " + line);
                    System.out.println(line);
                    outputStream.write(line);
                }
                else if (line.startsWith("HEARTBEAT:::")){
                    logger.info("Received HEARTBEAT request form server, responding with same ");
                    this.sendHeartbeat();
                    this.isNameAccepted = true;
                }
            }
        }
        catch (Exception ex){
            logger.info(ex + "\t" + ex.getStackTrace());
        }
        finally {
            closeIOSockets();
            logger.info("Closing and leaving");
            return;
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
