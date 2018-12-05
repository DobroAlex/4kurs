import java.io.*;   /*input output*/
import java.net.*;  /*управление сетевыми коннектами*/
import java.util.*; /*трисеты и прочие структуры данных*/
import java.lang.*; /*хэндл консольного ввода вывода*/
import java.time.*;
public class CalculServer
{
     public static void main(String[] args) throws Exception
    {
        int step = 0;   //инициализируем шаг чтоб не получить ошибку о нецинициализированной перменной 
        ServerSocket listener;   /*сокет, который будет слушать конннекты*/

        ArrayList<Integer> denomList = new ArrayList<>(Arrays.asList(11,13,17));    //список делителей прибит гвоздями к коду по требованию В.И.М
        if (args.length == 0)   //если запущено без аргументов 
	{
		System.out.println("Введите шаг:");
		step = Integer.parseInt(System.console().readLine());   //читаем шаг с клавиатуры ручками
	}
	else    //если запущено с аргументами
	{
		step = Integer.parseInt(args[0]); // берем первый из них и парсим
	}
        int clientNumber = 0;
        try
        {
            listener = new ServerSocket(9898);   /*пробуем запустить слушатель на этом порте*/
        }
        catch(IOException ex)
        {
            /*такая ситуация не должна возникать, если этот сокет не занят
            *Т.е возникает она, только если вы по тупости детской не закрыли приложение по человечески */
            System.out.println("не удалось запустить слушатель на сокете 9898 " + ex.getMessage()); 
            return; //выходим из мэйна
        }
	LocalDateTime localDateTime = LocalDateTime.now(); //получаем время  и дату сервера  https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
	System.out.println("Сервер запущен " + localDateTime + ". По Адресу = " +  InetAddress.getLocalHost() + " : " + listener.getLocalPort() + "\nЖдет коннекта");        
        int start = 0;
        try
        {
            while(true)
            {
                Divisor calc = new Divisor(listener.accept(), clientNumber++, denomList, start, start+step);   /*Запускаем параллельный поток, работающий на сокете, 
                                                                           который вернёт accept и присваиваем клиенту номер*/
                calc.start();   //запускаем в параллель
                start+=step;
            }
        }
        catch (Exception ex)  
        {
            // в нормальной ситуации не должно возникать такой ситуации, но на всякий случай хэндлим 
            System.out.println(ex.getMessage() + ex.getStackTrace());
            listener.close();   // пытаемся подчистить за собой ресуры, занятые сервером 
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
    //private static class Divisor extends Thread   /*поток слушатель-отправитель*/
    private static class Divisor extends Thread
    {
        private Socket socket;  //сокет, на котром будем работать 
        private int clientNumber;   //номер клиента 
        private ArrayList<Integer> denoms;  // делители в виде массива
        private int start;  //начало интревала поиска
        private int end;    //конец интревала поиска
	    private long calculationTime;   //время работы (предпологается принимать с клиента)
	    private int amountOfFinded; //сколько чисел нашли 
	
        public Divisor(Socket socket, int clientNumber, ArrayList<Integer> denoms, int start, int end)  //конструктор
        {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.denoms = denoms;
            this.start = start;
            this.end = end;
            if (start > end)
            {
                    int tmp = start;
                    start = end;
                    end = tmp;
            }
            System.out.println(this.clientNumber+"#:" + 
            "Новый коннект # "+this.clientNumber+", порт " + this.socket
            +"\n" + this.clientNumber+"#:" 
            + "Поиск на интервале["+this.start+";"+this.end+"]");   //Писать сообщения в конструкторе -- то еще нарушение ООП
        }
	
        @Override
        public void run()
        {
            try 
            {                   
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //цепляем к этому потоку ввода поток прочитанных данных нашего сокета и будем принимать сюда данные
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  //аналогично цепляем сюда вывод нашего сервера   
                out.println(clientNumber);	/*Отправляем номер клиента клиенту */
                out.println(this.start);    // отправляем клиенту начало интревала
                out.println(this.end);      //отправляем клиенту конец интервала
                out.println(this.denoms.size());    // отпраляем кол-во делителей
                for (int i = 0; i < this.denoms.size(); i++)
                {
                    out.println(denoms.get(i));     //отпраялем сами делители
                }
                this.amountOfFinded = Integer.parseInt(in.readLine());  //читаем от клиента кол-во найденных чисел
                this.calculationTime = Long.parseLong(in.readLine());   // и время вычилений клиента
                System.out.println(this.clientNumber+"#:" + "Клиент # "+clientNumber+" нашел " + this.amountOfFinded + " чисел на интрвале [" + this.start + " ; " + this.end + "] за " + this.calculationTime + "мс");
                in.close(); //закрываем потоки ввода вывода
                out.close();
            }
            catch (Exception ex)
            {
                //Не должно возникать в нормальных ситуациях, только если с клиента не пришел мусор
                System.out.println(ex.getMessage());    
            }
            //выполнится, независимо от возникновения исключений 
            finally 
            {
                try
                {
                    socket.close(); //пытаемся закрыть сокет и почистить за собой 
                }
                catch (IOException ex)
                {
                    System.out.println(this.clientNumber+"#:" + "Ошибка при закрытии клиента #"+this.clientNumber);
                }
                System.out.println(this.clientNumber+"#:" + "Закрыто соединение с #" + this.clientNumber);
                
            }
        }

    }
}