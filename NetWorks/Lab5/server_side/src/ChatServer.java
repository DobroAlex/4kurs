import  java.io.*;
import java.time.*;
import java.time.format.*;
import  java.util.*;
import java.net.*;
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
            System.out.println("Chat is running at " + InetAddress.getLocalHost() + " : " + PORT);
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
        long lastTimeHeartBeatReceived;
        boolean isHeartBeatRecived = false;
        boolean isNameAccepted = false;
        private  BufferedReader in;
        private PrintWriter out;
        private boolean isTreadShouldBeStopped = false;
        public  Handler (Socket s){

            this.socket = s;
            logger.info("Received new connection:" + socket);
        }
        public  void closeConnectionAndDeleteNameAndWriter(){
            try {
                this.isTreadShouldBeStopped = true;
                logger.info(name+" : " + socket + " : Closing connection and deleting name and writer");
                if (this.name != null){
                    names.remove(name);
                    logger.info(name+" : " + socket +": was removed from names");
                }
                if (this.out != null){
                    writers.remove(this.out);
                    logger.info(name+" : " + socket +": was removed from list of  writers");
                }
                out.close();
                in.close();
                logger.info(name+" : " + socket +": Connection successfully closed, I/O streams are closed");
                return;
            }
            catch (Exception ex){
                logger.info(name+" : " + socket +": error occurred during I/O closing");
                return;
            }
        }
        public void closeConnectionWithoutDeletingNameAndWriter(){
            try {
                this.isTreadShouldBeStopped = true;
                out.close();
                in.close();
                logger.info(name+" : " + socket +": Connection successfully closed, I/O streams are closed");
                return;
            }
            catch (Exception ex){
                logger.info(name+" : " + socket +": error occurred during I/O closing");
                return;
            }

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
                    logger.info( socket + "received null-answer, aborting connection, terminating thread");
                    throw  new RuntimeException(socket + ": Null-response  received, client is probably dead");
                }

                logger.info(socket + ": Received name: " + name);
                synchronized (ChatServer.names) {

                    if (!names.contains(name)) {
                        logger.info(socket + ": Name : " + name + " is unique, adding to list of names\nNow will be addressing " + socket + " as " + name);
                        names.add(name);
                        this.isNameAccepted = true;
                        lastTimeHeartBeatReceived = System.currentTimeMillis();
                        break;
                    }
                    else {
                        logger.info(socket+": Name " + name + " is not unique, sending NAME_IS_NOT_UNIQUE::: signal and closing");
                        out.println("NAME_IS_NOT_UNIQUE:::");
                        throw new RuntimeException(name + " : " + socket + " : " + "Name is not unique, message was sent");
                    }
                }
            }
            writers.add(out);
            logger.info(name+": Sending NAME_ACCEPTED::: signal");
            out.println("NAME_ACCEPTED:::");

            while (true) {
                if(isTreadShouldBeStopped == true){
                    logger.info(name + ": " + socket + " :  shouldBeStoppedFlagOccurred, trying to close and delete name and writer" );
                    throw new RuntimeException(name + " : " + socket + " : " + "isThreadShouldBeStopped occurred");

                }
                logger.info(name + " : " + socket + " : Sending heartbeat to client");
                out.println("HEARTBEAT:::");    //sending HEARTBEAT::: to client to check if haven't lost connection
                if (Math.abs(System.currentTimeMillis() - lastTimeHeartBeatReceived) >= 1E3){
                    logger.info(name + " : " + socket + " : Client didn't respond with HEARTBEAT::: for more than 1 second, probably dead and will be disconnected");
                    throw new RuntimeException(name + " : " + socket + " : Client didn't respond with HEARTBEAT::: for more than 1 second, probably dead");
                }
                logger.info(name + ": awaiting message  from" + name);
                String input = in.readLine();
                if (input == null)
                   {
                       logger.info(name +": " + socket + ": Received null-message, client is probably dead, calling closeConnectionAndDeleteName()");
                       throw  new RuntimeException(name + " : " + socket + " : Null-response  received, client is probably dead");
                   }
                else if (input.startsWith("CLIENT_IS_DISCONNECTING:::")){
                       logger.info(name + " : " + " : " + socket + " : sent CLIENT_IS_DISCONNECTING::: message, disconnecting this client");
                       for (PrintWriter writer : writers)
                       {
                           writer.println("MESSAGE:::" + name + " is leaving us");
                       }
                    throw  new RuntimeException(name + " : " + socket + " : Client has disconnected");
                   }
                else if (input.startsWith("MESSAGE:::")) {
                       logger.info(name + " : " + socket + " : received message :  " + input);
                       for (PrintWriter writer : writers) {
                           input = input.replaceFirst("MESSAGE:::", "");
                           logger.info(name + " : " + socket + " : Broadcasting message MESSAGE:::" + input );
                           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                           String messageDateTime = LocalDateTime.now().format(formatter);
                           writer.println("MESSAGE:::" + name + "["+ messageDateTime +"]: " + input);
                       }
                   }
                else if (input.startsWith("HEARTBEAT:::")){
                    lastTimeHeartBeatReceived = System.currentTimeMillis();
                }
                else {
                    logger.info(name + " : " + socket + " : received unusual signal " + input + " closing connection and thread");
                    throw new RuntimeException(name + " : " + socket + " : received unusual signal " + input + " closing connection and thread");
                }
                }
            }
            catch (Exception ex){
                logger.info(name + " : " + socket +" : " + ex + "\t" + ex.getStackTrace());
                logger.info(name + " : " + socket + " : Something went wrong(see up above, aborting tread execution: ");

            }
            finally {
                if (isNameAccepted){
                    closeConnectionAndDeleteNameAndWriter();
                }
                else {
                    closeConnectionWithoutDeletingNameAndWriter();
                }
                logger.info(name + " : " + socket + " : cleansed, leaving this thread and client ");
                return;
            }
        }
    }
}