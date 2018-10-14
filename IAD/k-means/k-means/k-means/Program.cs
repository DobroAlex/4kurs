using System;
using System.Collections.Generic;
using KMeans.DataStructures;
namespace KMeans
{
    class MainClass
    {


        public static void Main(string[] args)
        {
            PointCollection AllPoints = new PointCollection();
            AllPoints.Add(new KMeans.DataStructures.Point(0, 1, 1.5));
            AllPoints.Add(new KMeans.DataStructures.Point(1, 5, 4));
            AllPoints.Add(new KMeans.DataStructures.Point(2, 2, 9));
            AllPoints.Add(new KMeans.DataStructures.Point(3, 4, 3));
            AllPoints.Add(new KMeans.DataStructures.Point(4, 4, 4));
            AllPoints.Add(new KMeans.DataStructures.Point(5, 6, 1.8));
            AllPoints.Add(new KMeans.DataStructures.Point(6, 7, 13));
            List<PointCollection> KMeansResult = KMeans.DataStructures.KMeans.DoKMeans(AllPoints, 2);
            foreach (var PC in KMeansResult)
            {

                foreach (var point in PC)
                {
                    Console.WriteLine("Centr = [{0};{1}] point {2}", PC.Centroid.X, PC.Centroid.Y, point);
                }
            }
            Console.WriteLine("Введите точку, принадлежность которой хотите проверить");
            Console.WriteLine("Принадлежит кластеру {0}", BelongsToCluster(KMeansResult, new Point(7, double.Parse(Console.ReadLine(), System.Globalization.CultureInfo.InvariantCulture),double.Parse(Console.ReadLine(), System.Globalization.CultureInfo.InvariantCulture))));
        }

        public static Point BelongsToCluster(List<PointCollection> Points, Point point)
        {
            double minDist = KMeans.DataStructures.Point.FindDistance(Points[0].Centroid, point);
            Point closerCentroid = Points[0].Centroid;
            foreach (var varPo in Points)
            {
                if (Point.FindDistance(varPo.Centroid, point) < minDist)
                {
                    minDist = Point.FindDistance(varPo.Centroid, point);
                    closerCentroid = varPo.Centroid;
                }
            }
            return closerCentroid;
        }
    }
}
