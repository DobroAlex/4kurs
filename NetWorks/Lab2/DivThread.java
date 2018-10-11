/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DobroAlex
 */
import java.util.*;
public class DivThread extends Thread /*Создаем свой собственный потоковый класс, выполняющий указанную функцию. 
        Для этого имплиментируем интерфейс Runnable*/
{
	public TreeSet<Integer>  denoms;    /*Делители, делимость на которые проверяем */
	private int start, end; /*Начало-конец отрезка */
	public  int  amountOfFinded;
	private final long threadId;
	private  long startTime;
	public DivThread(int Start, int End, TreeSet<Integer> Denoms)
	{
		start = Start;
		end = End;
		denoms = Denoms;
		amountOfFinded= 0;
		threadId = Globals.amountOfThreads++;
		if (start > end)
		{
			int tmp = end;
			end = start;
			start = tmp;
		}
	}
	@Override
	public void run()
	{
		startTime = System.nanoTime();
		/*for (Integer I : Main.getSomeSpecificNumbers(start,end, denoms))
		{
			//Globals.AllSelectedNumbers.add(I);
			//System.out.print(threadId + ">"+I+'\t');
			amountOfFinded++;
		}*/
		for (int i = start; i <= end;i++)
		{
			if (Main.isNumDevidedByAllNumbers(i, denoms))
			{
				System.out.print(threadId + ">"+i+'\t');
				amountOfFinded++;
			}
		}
		System.out.println("\nТред " + threadId + " нашел " + amountOfFinded + " чисел за " + (System.nanoTime() - startTime)*1E-6 + "мс");
		Globals.totalThreadsTime += (System.nanoTime() - startTime)*1E-6;
	}
	
}