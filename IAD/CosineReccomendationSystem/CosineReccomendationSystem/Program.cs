using System;
using System.Collections.Generic;
using System.Linq;
namespace CosineReccomendationSystem
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Сколько человек будет в выборке?");
            int height = int.Parse(Console.ReadLine());
            Console.WriteLine("Сколько товаров будет в выборке?");
            int width = int.Parse(Console.ReadLine());
            List<double[]> Marks = new List<double[]>(height);  //Список  дабл-массивов (очень спорное инженерное решение по памяти)-  
            for (int i = 0; i < height; i++)
            {
                    Marks.Add(new double[width]);   //добавляем height эл-ов в список и сразу аллоцируем память

            }
            Console.WriteLine("Введите {0} эл-ов, разделенных пробелом. Некупленный товар - или 0.", width);
            for (int i = 0; i < height; i++)
            {
                String input = Console.ReadLine();
                Console.WriteLine("{0} : ", Names.MaleNames[i]);
                string[] splitedInput = input.Trim().Split(' ');    //Удаляем из исходной строки ведущие и конечные пробелы и разбиваем на новые строки по пробелам
                for (int j = 0; j < width; j++)
                {
                    if (splitedInput[j] == "-")     //оценка - считается как 0 
                    {
                        Marks[i][j] = 0.0d;
                    }
                    else
                    {
                        Marks[i][j] = double.Parse(splitedInput[j], System.Globalization.CultureInfo.InvariantCulture); //парсим остальные оценки
                    }
                    Console.Write("{0}  ", Marks[i][j]);
                }
                Console.WriteLine();

            }
            Console.WriteLine("Для человека с каким номером ищем рекомендацию?");

            int Person = int.Parse(Console.ReadLine());
            //double[] TargetPersonMarks = new double[width];
            //Marks[Person].CopyTo(TargetPersonMarks, 0);
            //Marks.RemoveAt(Person);
            List<double> CosMeasure = new List<double>();   //список косинусных мер, считаем для всех. Меру целевого человека позже не будем учитывать 
            var CosSum = 0d; 
            Console.WriteLine("Таблица косинусных мер#1:");
            for (int i = 0; i < Marks.Count; i++)
            {
                
                
                //if (i != Person)

                    CosMeasure.Add(CalcCosMeasure(Marks[Person], Marks[i]));    //добавляем в список косинусную меру между целью и текущим человеком
                    Console.WriteLine("Cos({0},{1}) = {2} ", Names.MaleNames[Person], Names.MaleNames[i], CosMeasure[i]);
                    CosSum += CosMeasure[i];    //наращиваем коснусную меру 


            }
            CosSum -= CosMeasure[Person];   //вычитаем косинусную меру цели
            Console.WriteLine("Косинусная сумма = {0}", CosSum);
            double[] waveSum = new double[width];   //~~sum~~
            Console.WriteLine("Пересчитанная таблица с учетом косинусных мер");

            for (int i = 0; i < Marks.Count; i++)   //пересчитываем все значения в оценках с учетом косинусной меры
            {

                Console.Write("{0}:", Names.MaleNames[i]);
                for (int j = 0; j < Marks[i].Length; j++) 
                {
                    
                    Marks[i][j] *= CosMeasure[i];
                    Console.Write(Marks[i][j] + " ");
                }
                Console.WriteLine();
            }

            for (int i = 0; i < width; i++)     //считаем ~~sum~~ как сумму каждого столбца
            {
                for (int j = 0; j < height; j++)
                {
                    waveSum[i] += Marks[j][i];
                }
            }
            for (int i = 0; i < waveSum.Length; i++)    //вычитаем оценки товаров, купленных целью
            {
                waveSum[i] -= Marks[Person][i];
            }
            Console.WriteLine("~~sum~~:");
            foreach(var sum in waveSum)
            {
                Console.Write(sum + " ");
            }
            Console.WriteLine();
            double[] L = new double[waveSum.Length];    //оценки L; L[i] = ~sum~[i]/CosSum
            for (int i = 0; i < waveSum.Length; i++)
            {
                L[i] = (waveSum[i] / CosSum);
                Console.WriteLine("L{0} = {1} ", i, L[i]);
            }
            List<double> targetL = new List<double>();  //интересующие нас L -- те, которые не купила персона
            for (int i = 0; i < Marks[Person].Length; i++)
            {
                if (Double.Equals(Marks[Person][i], 0.0d))      //если цель не купила этот товар, добавляем его в целевыеL
                {
                    targetL.Add(L[i]);
                }
            }
            foreach(var i in targetL)
            {
                Console.WriteLine("TargetL = {0}", i);
            }
            List<double> SortedTargetL = targetL.OrderByDescending(o => o).ToList();    //сортируем по убыванию
            foreach(var item in SortedTargetL)
            {
                Console.WriteLine("SortedTargetL={0}", item);
            }

            for (int i = 0; i < SortedTargetL.Count; i++) 
            {
                Console.WriteLine("Answer[{0}]={1}({2})", i, Array.IndexOf(L, SortedTargetL[i]), Array.IndexOf(L, SortedTargetL[i]) + 1);   //находим позицию целевых товаров в исходном массиве L
            }

        }
        /// <summary>
        /// Вычисляет косинусную меру 
        /// </summary>
        /// <returns>Косинусная мера как скалярное произвденеие деленное на произведение векторных норм.</returns>
        /// <param name="x">Вектор X.</param>
        /// <param name="y">Вектор Y</param>
        public static double CalcCosMeasure(double[] x, double[] y)
        {
            return DotProduct(x, y) / (VectorNorm(x) * VectorNorm(y));
        }
        /// <summary>
        /// Скалярное произведение двух векторов по длинне первого 
        /// </summary>
        /// <returns>Скалярное произведение.</returns>
        /// <param name="x">Вектор X.</param>
        /// <param name="y">Вектор Y.</param>
        public static double DotProduct(double[] x, double[] y) 
        {
            var retVal = 0d;
            for (int i = 0; i < x.Length; i++)
            {
                retVal += x[i] * y[i];
            }
            return retVal;
        }
        /// <summary>
        /// Вычисляет норму вектора
        /// </summary>
        /// <returns>Норма вектора.</returns>
        /// <param name="x">вектор как массив.</param>

        public static double VectorNorm (double [] x)
        {
            var retVal = 0d;
            foreach (var element in x)
            {
                retVal += Math.Pow(element, 2);
            }
            return Math.Sqrt(retVal);
        }

    }


}
