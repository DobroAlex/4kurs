import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class CalculClient
{
    private BufferedReader in;
    private PrintWriter out;
    private int start, end;
    private TreeSet<Integer> denoms;
    private String serverAdress;
    private int port;
    public CalculClient (String serverAdress, int port)
    {
        this.serverAdress = serverAdress;
        this.port = port;
        denoms = new TreeSet<Integer>();
    } 
        public void ConnectToServer () throws IOException
    {
        Socket socket = new Socket(this.serverAdress, this.port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        for (int i = 0; i < 3; i++) //поглощаем строки приветствия и строки start/end с сервера
        {
            System.out.println(in.readLine());
        }
        this.start = Integer.parseInt(in.readLine().trim());
        System.out.println("С сервера пришло: start = " + this.start);
        this.end = Integer.parseInt(in.readLine());
        System.out.println("С сервера пришло: end = " + this.end);
        
        int amountOfDenoms = Integer.parseInt(in.readLine());
        System.out.println("Ожидаем " + amountOfDenoms + " делителей с сервера");
        for (int i = 0; i < amountOfDenoms; i++)
        {
            denoms.add(Integer.parseInt(in.readLine()));
        }
        System.out.println("Делители:");
        for (Integer  I : denoms) 
        {
            System.out.println(I);
        }
        
        }
        
    
     public static void main(String[] args) 
    {
        try
        {
            System.out.println("Введите адрес сервера:");
            Scanner scan = new Scanner(System.in);
            String serverAdress = scan.nextLine();
            CalculClient client = new CalculClient( serverAdress, 9898);
            client.ConnectToServer();
            client.out.println((CalcUtils.getSomeSpecificNumbers(client.start, client.end, client.denoms)).size() );
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage() + "\n" + ex.getStackTrace());
            return;
        }
    }
}
