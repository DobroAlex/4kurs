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
public class DivThread implements Runnable /*Создаем свой собственный потоковый класс, выполняющий указанную функцию. 
        Для этого имплиментируем интерфейс Runnable*/
{
	public TreeSet<Integer>  denoms;    /*Делители, делимость на которые проверяем */
	private int start, end; /*Начало-конец отрезка */
	@Override
	public void run()
	{
		for (Integer I : Main.getSomeSpecificNumbers(start,end, denoms))
		{
			Globals.AllSelectedNumbers.add(I);
			//System.out.println(I);
		}
	}
	public DivThread(int Start, int End, TreeSet<Integer> Denoms)
	{
		start = Start;
		end = End;
		denoms = Denoms;
		if (start > end)
		{
			var tmp = end;
			end = start;
			start = tmp;
		}
	}
}