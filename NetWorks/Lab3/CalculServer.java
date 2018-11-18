import java.io.*;   /*input output*/
import java.net.*;  /*управление сетевыми коннектами*/
import java.util.*; /*трисеты и прочие структуры данных*/
import java.lang.*; /*хэндл консольного ввода вывода*/
public class CalculServer
{
     public static void main(String[] args) throws Exception
    {
        int step;
        ServerSocket listener;   /*сокет, который будет слушать конннекты*/

        ArrayList<Integer> denomList = new ArrayList<>();
        if ( args == null || args.length == 0 )
	{
		denomList.add(11);
		denomList.add(13);
		denomList.add(17);
		System.out.println("Введите шаг");
		Scanner scan = new Scanner (System.in);
        step = Integer.parseInt(scan.nextLine());
        scan.close(); 
	}
	else
	{
        System.out.println(args.length);
		for (int i = 0; i < args.length-1; i++)
		{
			denomList.add(Integer.parseInt(args[i]));
        }
        step = Integer.parseInt(args[args.length-1]);
	}
	
        int clientNumber = 0;
        try
        {
            listener = new ServerSocket(9898);   /*пробуем запустить слушатель на этом порте*/
        }
        catch(IOException ex)
        {
            System.out.println("не удалось запустить слушатель на сокете 9898 " + ex.getMessage());
            return;
        }
	
	System.out.println("Вычислительный сервер запущен и ожидает коннекта. Адрес = " +  InetAddress.getLocalHost());        
        int start = 0;
        try
        {
            while(true)
            {
                Divisor calc = new Divisor(listener.accept(), clientNumber++, denomList, start, start+step);   /*Запускаем параллельный поток, работающий на сокете, 
                                                                           который вернёт accept и присваиваем клиенту номер*/
                calc.start();
                start+=step;
                //calc.join();
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
    //private static class Divisor extends Thread   /*поток слушатель-отправитель*/
    private static class Divisor extends Thread
    {
        private Socket socket;
        private int clientNumber;
        private ArrayList<Integer> denoms;
        private int start;
        private int end;
	private long calculationTime;
	private int amountOfFinded;
        public Divisor(Socket socket, int clientNumber, ArrayList<Integer> denoms, int start, int end)
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
            System.out.println("Новый коннект #"+this.clientNumber+" на порте " + this.socket+"\nПоиск на интервале["+this.start+";"+this.end+"]");
        }
        @Override
        public void run()
        {
            try 
            {   
                
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(clientNumber);	/*Отправляем номер клиента клиенту */
                out.println("Ваш интервал поиска: " + this.start + ";" + this.end);
                out.println(this.start);
                out.println(this.end);
                //System.out.println("Отправляем " + this.denoms.size() + "делителей");
                out.println(this.denoms.size());
                for (int i = 0; i < this.denoms.size(); i++)
                {
                    //System.out.println("Отправляем делитель #"+i+" = " + denoms.get(i));
                    out.println(denoms.get(i));
                }
                System.out.println("Ждем ответ от #"+this.clientNumber);
                this.amountOfFinded = Integer.parseInt(in.readLine());
                this.calculationTime = Long.parseLong(in.readLine());
		        //System.out.println(amountOfFinded+"\t"+calculationTime);
                System.out.println("Клиент #"+clientNumber+"нашел " + this.amountOfFinded + " за " + this.calculationTime + "мс");
                in.close();
                out.close();
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