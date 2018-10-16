import java.util.*;
public class CalcUtils
{
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