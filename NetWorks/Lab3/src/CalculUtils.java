/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author myfamily
 */
public class CalculUtils 
{
    public static TreeSet<Integer> getSomeSpecificNumbers(int  start, int end, TreeSet<Integer> denoms)
	{
		TreeSet<Integer> goodNums = new TreeSet<>();
		for (int i = start; i < end;i++)
		{
			if (isNumDevidedByAllNumbers(i,  denoms))
			{
				goodNums.add(i);
			}
		}
		return goodNums;
}
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
