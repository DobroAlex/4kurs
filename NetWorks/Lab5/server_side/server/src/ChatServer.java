import  java.io.*;
import  java.util.*;
import java.net.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {
    private  static  final  int PORT =  9898;
    private static HashSet<String> names = new HashSet<>();
    private  static HashSet<PrintWriter> writers = new HashSet<>();
    private static Logger logger;
    public static void main(String[] args){
        logger = Logger.getLogger(ChatServer.class.getName());
        System.out.println("Trying to connect to server at port:" + PORT);
        ServerSocket listener;
        try {
            listener = new ServerSocket(PORT);
            System.out.println("Chat is running at: " + listener.getInetAddress() + " : " + listener.getLocalPort());
            logger.info("Chat has been activated and running at " + listener);
             while (true){

                 new Handler(listener.accept()).start();

             }
        }
        catch (Exception ex)
        {
            System.out.println( "Oh my, you somehow managed to get ports locked. Consider killing all apps listening to this PORT = " + PORT);
            return;
        }
    }
    private static  class  Handler extends  Thread{
        private  String name;
        private Socket socket;
        boolean isHeartBeatRecived = false;
        private  BufferedReader in;
        private PrintWriter out;
        public  Handler (Socket s){

            this.socket = s;
            logger.info("Received new connection:" + socket);
        }
        @Override
        public void run(){
            logger.info("Now trying to run at:" + socket);
            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()
                ));
                out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                logger.info(socket + ": Trying to get unique name, sending GET_NAME::: request");
                out.println("GET_NAME:::");
                this.name = in.readLine();
                if (name == null) {
                    logger.info(socket + "received null-answer, aborting connection, terminating thread");
                    return;
                }

                logger.info(socket + ": Received name: " + name);
                synchronized (ChatServer.names) {

                    if (!names.contains(name)) {
                        logger.info(socket + ": Name : " + name + " is unique, adding to list of names\nNow will be addressing " + socket + " as " + name);
                        names.add(name);
                        break;
                    }
                    else {
                        logger.info(socket+": Name " + name + " is not unique, sending NAME_IS_NOT_UNIQUE::: signal");
                        out.println("NAME_IS_NOT_UNIQUE:::");
                    }
                }
            }
            writers.add(out);
            logger.info(name+": Sending NAME_ACCEPTED::: signal");
            out.println("NAME_ACCEPTED:::");

            while (true) {
                    logger.info(name + ": awaiting message  from" + name);
                   String input = in.readLine();
                   if (input == null)
                   {
                       logger.info(name + ": Received null-message, client is probably dead, disconnecting");
                       throw new Exception("Client " + name + " send null-response / is dead.");
                   }
                   if (input.startsWith("CLIENT_IS_DISCONNECTING:::")){
                       names.remove(name);
                       writers.remove(out);
                       throw new RuntimeException(socket + " : " + name + " disconnected");
                   }
                   if(input.compareTo("MESSAGE:::") == 0)
                   {
                       continue;
                   }
                   for (PrintWriter writer : writers){
                       input = input.split(":::")[1]; //received message looks like MESSAGE::: + content, so we
                       // want to broadcast only message
                       writer.println("MESSAGE:::"+name+ ": s"+input);
                   }
                }
            }
            catch (Exception ex) {
                System.out.println("name:"+ex);
                names.remove(name);
                writers.remove(out);
            }
            finally {
                if (name != null){
                    names.remove(name);
                }
                if (out != null){
                    writers.remove(out);
                }
                try {
                    socket.close();
                }
                catch (Exception ex)
                {
                    System.out.println("Wow, can't close socket " + socket);
                }
            }
        }
    }
}