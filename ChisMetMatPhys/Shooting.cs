using System;
using System.Runtime;
namespace Shooting
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			int n = 10;
			double[] y = new double[n + 1];
			double[] z = new double[n + 1];
            Tuple <double,double> DYDZ;
			double dy, dz, a = 0, b = 1, h = Math.Abs(b - a) / n;
            double yA = 0;
            double yB = Math.PI / 2.0;
            double eps = double.Epsilon;
            double v1 = 0; // Недолёт
            double v2 = 10; // Перелёт
            double v;
            double r = 1;
            int k=0; //Счётчик
            y[0]=yA;
            while (Math.Abs(r)>eps) 
            {
                k++;
                v=(v1+v2)/2;
                z[0]=v;
                double x=a;
                for (int i=1; i<=n; i++) 
                {
                    DYDZ = RK(x,y[i-1],z[i-1],h);
                    dy = DYDZ.Item1;
                    dz = DYDZ.Item2;
                    y[i]=y[i-1]+dy;
                    z[i]=z[i-1]+dz;
                    x=x+h;
                }
                r=y[n]-yB;
                Console.WriteLine ("Шаг{0}\tyB={1}\tyN={2}\tr={3}\tv={4}", k, yB, y [n], r, v);
                if (r > 0) 
                {
                    v2 = v;
                } 
                else 
                {
                    v1 = v;
                }
            }
            double X = a;
            for (int i =0; i <=n; i++)
            {
                Console.WriteLine("X={0}\tYi={1}", X, y[i]);
                X += h;
            }
		}
        static double   f1(double x, double y, double z)
		{
			return z;
		}
		static double  f2(double x, double y, double z)
		{
			return -0.98*Math.Sin(y);
		}
        public static Tuple<double,double> RK(double x, double y, double z, double h)
        {
            double k1=h*f1(x,y,z); 
            double l1=h*f2(x,y,z);
            double k2=h*f1(x+h/2,y+k1/2,z+l1/2);
            double l2=h*f2(x+h/2,y+k1/2,z+l1/2);
            double k3=h*f1(x+h/2,y+k2/2,z+l2/2);
            double l3=h*f2(x+h/2,y+k2/2,z+l2/2);
            double k4=h*f1(x+h,y+k3,z+l3);
            double l4=h*f2(x+h,y+k3,z+l3);
            double dy=(k1+2*k2+2*k3+k4)/6;
            double dz=(l1+2*l2+2*l3+l4)/6;
            return Tuple.Create (dy, dz);
        }
	}
}