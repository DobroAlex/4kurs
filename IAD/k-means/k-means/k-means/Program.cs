using System;
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
        }
    }
}
