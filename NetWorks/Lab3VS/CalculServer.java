import java.io.*;   /*input output*/
import java.net.*;  /*управление сетевыми коннектами*/
import java.util.*; /*трисеты и прочие структуры данных*/
import java.lang.*; /*хэндл консольного ввода вывода*/
public class CalculServer
{
     public static void main(String[] args) 
    {
        System.out.println("Вычислительный сервер запущен и ожидает коннекта");
        int clientNumber = 0;
        ServerSocket listener;   /*сокет, который будет слушать конннекты*/
        System.out.println("ВВедите ваши делители. Пустая строка для окончания ввода");
        ArrayList<Integer> denomList = new ArrayList<>();
        Scanner scan =  new Scanner(System.in);
        String input = scan.nextLine();
        input =  input.replaceAll("[\\s]{2,}", " ");
        for (String item : input.split(" "))
        {
            denomList.add(Integer.parseInt(item));
        }
        try
        {
            listener = new ServerSocket(9898);   /*пробуем запустить слушатель на этом порте*/
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
                Divisor calc = new Divisor(listener.accept(), clientNumber++, denomList);   /*Запускаем параллельный поток, работающий на сокете, 
                                                                           который вернёт accept и присваиваем клиенту номер*/
                calc.run();
                
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
                return;
            }
            catch (IOException ex)   /*не должно наступать в нормальных условиях*/
            {
                System.out.println("Ого, нельзя закрыть слушатель" + ex.getMessage());
            }
            
        }
    }
    private static class Divisor //extends Thread   /*поток слушатель-отправитель*/
    {
        private Socket socket;
        private int clientNumber;
        private ArrayList<Integer> denoms;
        public Divisor(Socket socket, int clientNumber, ArrayList<Integer> denoms)
        {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.denoms = denoms;
            System.out.println("Новыый коннект #"+this.clientNumber+" на порте " + this.socket);
        }
        //@Override
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
                System.out.println("Отправляем " + this.denoms.size() + "делителей");
                out.println(this.denoms.size());
                for (int i = 0; i < this.denoms.size(); i++)
                {
                    System.out.println("Отправляем делитель #"+i+" = " + denoms.get(i));
                    out.println(denoms.get(i));
                }

                System.out.println("Клиент #"+clientNumber+"нашел " + in.readLine());
                in.close();
                out.close();
                scan.close();
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
