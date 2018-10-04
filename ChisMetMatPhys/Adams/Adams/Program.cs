using System;
using System.Collections.Generic;
namespace lab1RK
{
	class MainClass
	{
		public static void Main(string[] args)
		{
			Console.WriteLine("Введите сегмент  [x0,X]:");
			double x0 = double.Parse(Console.ReadLine(), System.Globalization.CultureInfo.InvariantCulture);
			double X = double.Parse(Console.ReadLine(), System.Globalization.CultureInfo.InvariantCulture);
			Console.WriteLine("Начальное условие  y(" + x0 + ")= :");
			double y0 = double.Parse(Console.ReadLine(), System.Globalization.CultureInfo.InvariantCulture);
			Console.Write("Кол-во шагов  n:");
			double n = int.Parse(Console.ReadLine());
			double h = (Math.Abs(X - x0) / n);
			Console.WriteLine("Шаг = " + h);
			List<double> Xs = new List<double>();
			Xs.Add(x0);
			for (int i = 1; i <= n; i++)
			{
				Xs.Add(Xs[i - 1] + h);
				Console.WriteLine("X[{0}] = {1}", i, Xs[i]);
			}
			Console.WriteLine("/*------------------------------------------*/");
			//Console.WriteLine("Ответ по Рунге-Кутты : " + Runge_Kutt.calculateRunge_Kutt(x0,X,y0,n)+"\tТочное="+lab1RK.Runge_Kutt.ExactSolution(X));
			Console.WriteLine("Рунге-Кутты:");
			List<double> R_K_Answers = new List<double>(Runge_Kutt.calculateRunge_Kutt(x0, X, y0, n));
			R_K_Answers.Insert(0, y0);

			R_K_Answers.RemoveAt(R_K_Answers.Count - 1);
			for (int i = 0; i < R_K_Answers.Count; i++)
			{
				//Console.WriteLine ("Y[{0}]={1}", i, R_K_Answers [i]);
			}
			Console.WriteLine("/*------------------------------------------*/");
			List<double> Adams_Y = new List<double>(Runge_Kutt.calculateRunge_Kutt(x0, X, y0, n));
			Adams_Y.Insert(0, y0);
			Console.WriteLine("Адамс-Башфорт:");
			for (int i = 0; i < 4; i++)
			{
				//Console.WriteLine ("Y[{0}] = {1}", i, Adams_Y[i]);
			}

			for (int i = 4; i <= n; i++)
			{
				Adams_Y[i] = Adams_Y[i - 1] + h / 24.0 * (55 * Runge_Kutt.Function(Xs[i - 1], Adams_Y[i - 1]) - 59 * Runge_Kutt.Function(Xs[i - 2], Adams_Y[i - 2]) + 37 * Runge_Kutt.Function(Xs[i - 3], Adams_Y[i - 3]) - 9 * Runge_Kutt.Function(Xs[i - 4], Adams_Y[i - 4]));
				//Console.WriteLine ("Y[{0}]={1}", i, Adams_Y [i]);
			}
			for (int i = 0; i <= n; i++)
			{
				//Console.WriteLine ("Точное в точке {0} = {1}", Xs [i], Runge_Kutt.ExactSolution (Xs [i]));
			}
			for (int i = 0; i <= n; i++)
			{
				//Console.WriteLine("R_K[{0}]={1} \t A_B[{0}]={2}", i, R_K_Answers[i], Adams_Y[i]);
			}
			List<double> defaultY = new List<double>(Runge_Kutt.calculateRunge_Kutt(x0, X, y0, n));

			List<double> Adams_Moulton = new List<double>(Runge_Kutt.calculateRunge_Kutt(x0, X, y0, n));
			Adams_Moulton.Insert(0, y0);
			Adams_Moulton.RemoveAt(Adams_Moulton.Count - 1);
			for (int i = 4; i <= n; i++)
			{
				double yStar = Adams_Moulton[i - 1] + h / 24.0 * (55 * Runge_Kutt.Function(Xs[i - 1], Adams_Moulton[i - 1]) - 59 * Runge_Kutt.Function(Xs[i - 2], Adams_Moulton[i - 2]) + 37 * Runge_Kutt.Function(Xs[i - 3], Adams_Moulton[i - 3]) - 9 * Runge_Kutt.Function(Xs[i - 4], Adams_Moulton[i - 4]));

				Adams_Moulton[i] = Adams_Moulton[i - 1] + h / 24.0 * (9 * Runge_Kutt.Function(Xs[i], yStar) + 19 * Runge_Kutt.Function(Xs[i - 1], Adams_Moulton[i - 1]) - 5 * Runge_Kutt.Function(Xs[i - 2], Adams_Moulton[i - 2]) + Runge_Kutt.Function(Xs[i - 3], Adams_Moulton[i - 3]));
			}
			for (int i = 0; i < Adams_Moulton.Count; i++)
			{
				//Console.WriteLine("A_M[{0}]={1}", i, Adams_Moulton[i]);
			}
			for (int i = 0; i <= n; i++)

			{
				Console.WriteLine(@"R_K[{0}]={1} A_B[{0}]={2} A_M[{0}]={3} Точ={4}", i, R_K_Answers[i], Adams_Y[i], Adams_Moulton[i], Runge_Kutt.ExactSolution(Xs[i]));
			}
		}
	}
}