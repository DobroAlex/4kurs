import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class CalculClient
{
    private BufferedReader in;
    public PrintWriter out;
    public Socket socket;
    private int start, end;
    private ArrayList<Integer> denoms;
    private String serverAdress;
    private int port;
    private int selfNum; 
    public  CalculClient (String serverAdress, int port)
    {
        this.serverAdress = serverAdress;
        this.port = port;
        denoms = new ArrayList<Integer>();
    }
    public  String GetSelfNumberAsString()
    {
	return ("[" + selfNum + "]");
    } 
        public void ConnectToServer () throws IOException
    {
        this.socket = new Socket(this.serverAdress, this.port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.selfNum = Integer.parseInt(in.readLine());	/*Поглощаем номер клиента с сервера*/
	System.out.print(GetSelfNumberAsString());
	System.out.println(in.readLine());
        this.start = Integer.parseInt(in.readLine().trim());
        System.out.println(GetSelfNumberAsString() + "С сервера пришло: start = " + this.start);
        this.end = Integer.parseInt(in.readLine());
        System.out.println(GetSelfNumberAsString() + "С сервера пришло: end = " + this.end);
        
        int amountOfDenoms = Integer.parseInt(in.readLine());
        //System.out.println("Ожидаем " + amountOfDenoms + " делителей с сервера");
        for (int i = 0; i < amountOfDenoms; i++)
        {
            denoms.add(Integer.parseInt(in.readLine()));
        }
        /*System.out.println("Делители:");
        for (Integer  I : denoms) 
        {
            System.out.println(I);
        }*/
        
    }
        
    
     public static void main(String[] args) 
    {
        try
        {
            String serverAdress;
            int port;
            if (args.length == 0 || args == null)
            {

                System.out.println("Введите адрес сервера:");
                Scanner scan = new Scanner(System.in);
                serverAdress = scan.nextLine();
                System.out.println("Введите порт сервера:");
                port = Integer.parseInt(scan.nextLine());
                scan.close();                
            }
            else 
            {
                serverAdress = args[0];
                port = Integer.parseInt(args[1]);
                System.out.println("Подключаемся к " + serverAdress + " : " + port);
            }
	    long startTime = java.lang.System.currentTimeMillis();
            CalculClient client = new CalculClient(serverAdress, port);
            client.ConnectToServer();
            
            ArrayList<Integer> ans = CalcUtils.getSomeSpecificNumbers(client.start, client.end, client.denoms);
            if (ans.size() > 0)
            {
                System.out.println(client.GetSelfNumberAsString() + "Нашли " + ans.size() + " подходящих чисел и отправляем");
            
                for (Integer goodNum : ans) 
                {
                    //System.out.print(goodNum+"  ");
                }
            }
            else
            {
                System.out.println(client.GetSelfNumberAsString() + "Не нашли подходящих чиел и отправляем");
            }
	    client.out.println(ans.size());
	    long elapsedTime = java.lang.System.currentTimeMillis() - startTime;
	    System.out.println("Вычисление заняло " + elapsedTime + " мс");
	    client.out.print(elapsedTime);
	    System.out.println(client.GetSelfNumberAsString() + "Клиент отключается");
            client.in.close();
            client.out.close();
            client.socket.close();;
            return;
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage() + "\n" + ex.getStackTrace());
            return;
            
        }
        finally
        {
            
        }
    }
}