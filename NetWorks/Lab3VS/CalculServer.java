import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
public class CalculServer
{
     public static void main(String[] args) 
    {
        System.out.println("Вычислительный сервер запущен и ожидает коннекта");
        int clientNumber = 0;
        ServerSocket listener;
        try
        {
            listener  = new ServerSocket(9898);
        }
        catch(IOException ex)
        {
            System.out.println("не удалось запустить слушатель на сокете 9898 " + ex.getMessage());
            return;
        }        
        
        try
        {
            while(true)
            {
                new Divisor(listener.accept(), clientNumber++).start();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + ex.getStackTrace());
            return ;
        }
        finally
        {
            try
            {
                listener.close();
            }
            catch (IOException ex)
            {
                System.out.println("Ого, нельзя закрыть слушатель" + ex.getMessage());
            }
        }
    }
    private static class Divisor extends Thread
    {
        private Socket socket;
        private int clientNumber;
        
        public Divisor(Socket socket, int clientNumber)
        {
            this.socket = socket;
            this.clientNumber = clientNumber;
            System.out.println("Новыый коннект #"+this.clientNumber+" на порте " + this.socket);
        }
        @Override
        public void run()
        {
            try 
            {    
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Вы клиент #" + clientNumber + ".");
                out.println("Ctrl+C для выхода");
                System.out.println("Введите начало отрезка, на котором хотите искать числа:");
                Scanner scan = new Scanner(System.in);
                int start = Integer.parseInt(scan.nextLine());
                System.out.println("Введите конец отрезка, на котором хотите искать числа:");
                int end = Integer.parseInt(scan.nextLine());
                
                if (start > end)
                {
                    int tmp = start;
                    start = end;
                    end = tmp;
                }
                out.println("Ваш интервал поиска:");
                out.println(start);
                out.println(end);
                System.out.println("Сколько делитей будет отправлено?");
                int amountOfDenoms = Integer.parseInt(scan.nextLine());
                System.out.println("Отправляем " + amountOfDenoms + "делителей");
                out.println(amountOfDenoms);
                for (int i = 0; i < amountOfDenoms; i++)
                {
                    System.out.println("Введите делитель #"+i+" для отправки на клиент");
                    out.println(Integer.parseInt(scan.next()));
                }

                scan.close();
                System.out.println("Клиент #"+clientNumber+"нашел " + in.readLine());
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            finally
            {
                try
                {
                    socket.close();
                }
                catch (IOException ex)
                {
                    System.out.println("Ошибка при закрытии клиента #"+this.clientNumber);
                }
                System.out.println("Закрыто соединение с #" + this.clientNumber);
            }
        }

    }
}