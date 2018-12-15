import  java.io.*;
import  java.util.*;
import java.net.*;
import java.util.logging.Handler;

public class ChatServer {
    private  static  final  int PORT =  9898;
    private static HashSet<String> names = new HashSet<>();
    private  static HashSet<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args){
        System.out.println("Server has been activated");
        ServerSocket listener;
        try {
            listener = new ServerSocket(PORT);
            System.out.println("Chat is activated at: " + listener.getInetAddress() + " : " + listener.getLocalPort());
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
        }
        @Override
        public void run(){
            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()
                ));
                out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                out.println("GET_NAME:::");
                name = in.readLine();
                if (name == null) {
                    return;
                }
                synchronized (ChatServer.names) {
                    if (!names.contains(name)) {
                        names.add(name);
                        break;
                    }
                }
            }
            writers.add(out);
            out.println("NAME_ACCEPTED:::");

            while (true) {
                   String input = in.readLine();
                   if (input == null)
                   {
                       return;
                   }
                   for (PrintWriter writer : writers){
                       writer.println("MESSAGE:::"+"name"+input);
                   }
                }
            }
            catch (Exception ex) {
                System.out.println("name:"+ex);
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