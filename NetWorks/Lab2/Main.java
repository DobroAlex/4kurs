/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DobroAlex
 */
import java.io.IOException;
import java.util.*;
public  class Main
{
	public static void main(String [] args)
	{
		long startTime;
		int start = 1000000, end = 4000000;   /*Начало и конец отрезка поиска*/
		TreeSet<Integer> denoms = new TreeSet<>(); /*Древовидная структуру с гарантированным логарифмическим временем доступа  к элементу*/
		
		System.out.println("Введите делители в одну строку через пробелы");
		Scanner scan= new Scanner(System.in);
		String input = scan.nextLine(); /*парсим строку с делителями*/
		input =  input.replaceAll("[\\s]{2,}", " ");/*удаляем лишение пробелы*/
		try
                {
                    for (String item : input.split(" "))/*разбиваем строку на отдельные слова из чисел*/
                    {
                         denoms.add(Integer.parseInt(item));   /*и добавляем в делители*/
                    }
                }
                catch (Exception ex)
                {
                    System.out.println("В делителях есть нечисловой или нецелочисленный  ввод, отмена." + ex.getMessage());
                }
                
        System.out.println("Одним потоком?	Y / N ");
        if (scan.nextLine().toLowerCase().contains("y"))   /*если строка ответа содержит 'y'*/
        {
        	startTime = System.nanoTime();/*измеряем время в начале*/
        	for(Integer I : getSomeSpecificNumbers(start,end, denoms))   /*foreach. Для каждого элемента, который вернёт функция*/
        	{
        		System.out.println(I);
        	}
        }
        else
        {
        	System.out.println("На сколько участков разбить отрезок?");
                int steps = 0;
        	try
                {
                     steps = scan.nextInt();
                    if (steps <= 0)
                    {
                        throw new  IOException("Число отрезков не может быть <=0");
                    }
                }
                catch (Exception ex)
                {
                    System.out.println("При вводе числа потоков что-то пошло не так");
                }
        	startTime = System.nanoTime(); /*Засекаем время старта*/
        	Runnable[] myThreads = new DivThread[steps];
        	int i = 0;
        	for (int smallStart = start; smallStart <= end - Math.abs(end-start)/steps; smallStart +=Math.abs(end-start)/steps)
        	{
        		myThreads[i] = new DivThread(smallStart, smallStart + Math.abs(end-start)/steps, denoms);
        		myThreads[i].run();
        	}
        	for (Integer I : Globals.AllSelectedNumbers)
        	{
        		System.out.println(I);
        	}
        	
        }
        System.out.println("Прошло времени "+ (System.nanoTime() - startTime)*1E-6 + "мс");
	}
        /**
         * Поиск всех чисел в интервале от start до end, каждое из которых делится на делители из denom 
         * @param start Начало отрезка поиска. Правильный порядок поиска гарантирован, даже если start > end  
         * @param end Конец  отрезка поиска. Правильный порядок поиска гарантирован, даже если   end  <  start
         * @param denoms TreeSet делителей, делимость на каждый из которых проверяем 
         * @return Набор всех чисел на [start ; end], каждое из которых делится на все числа из denom
         */
	public static TreeSet<Integer> getSomeSpecificNumbers(int  start, int end, TreeSet<Integer> denoms)
	{
		TreeSet<Integer> goodNums = new TreeSet<Integer>(); /*Все числа, удовлетворяющие условию*/
		for (int i = start; i <= end;i++)
		{
			if (isNumDevidedByAllNumbers(i,  denoms)) /*Если данное число прошло проверку на делимость, добавляем в массив*/
			{
				goodNums.add(i);
			}
		}
		return goodNums; /*Empty, если хороших чисел не нашлось*/
	}
	/**
	*Проверяет, делится ли данное число на каждое число из набора делителей
	*@param num Делимое, делимость которого проверяем
	*@param denoms Делители, делимость на которые проверяемо*@return true, если num делится на все числа из denom; иначе false
	*/
	public static boolean isNumDevidedByAllNumbers(int num, TreeSet<Integer> denoms)
	{
		for (Integer I : denoms)
		{
			if (num % I == 0)
			{
				continue;
			}
			else
			{
				return false;
			}
		}
		return true;
	}
}
