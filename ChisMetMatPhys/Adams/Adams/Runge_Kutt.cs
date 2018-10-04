using System;
using System.Collections.Generic;

namespace lab1RK
{
	public class Runge_Kutt
	{
		public Runge_Kutt ( )
		{

		}
		public static List<double> calculateRunge_Kutt (double x0, double X, double y0, double n)
		{
			List <double> retVal = new List<double> ();
			double h = (Math.Abs(X - x0) / n);
			double yi = y0;
			double xi = x0;
			for(int i = 0;i<=n;i++)
			{


				double k1 = h * Function(xi, yi);
				double k2 = h * Function(xi + h / 2, yi +k1/2);
				double k3 = h * Function(xi + h / 2, yi + k2 / 2 );
				double k4 = h * Function(xi + h, yi +  k3);
				double deltay = (k1 + 2 * k2 + 2 * k3 + k4) / 6;
				#if DBG_PRINT
				Console.WriteLine ("Шаг" + i);
				Console.WriteLine ("X" + i + " = " + xi + "   Y" + i + " = " + yi + ".Точное решение = " + ExactSolution (xi));
				#endif
				yi += deltay;
				xi += h;
				retVal.Add (yi);
			}
			#if DBG_PRINT
			Console.WriteLine("Answer is Y="+yi);
			#endif
			return retVal;
		}

		public static double Function(double x, double y)
		{
			//return 2.0 * Math.Exp(-x) + x - 1.0;
			return -x*y;
		}
		public static double ExactSolution (double x)
		{
			return Math.Exp (-(x * x) / 2);
		}
	}
}