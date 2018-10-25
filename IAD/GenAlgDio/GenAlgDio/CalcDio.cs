using System;
namespace GenAlgDio
{
    public class CalcDio
    {
        public CalcDio()
        {
            Gene[] genes = new GenAlgDio.Gene[4];


        }
        public static int CalcFitness(Gene  g)
        {
            return MainClass.Equation(g.Allele[0], g.Allele[1], g.Allele[2], g.Allele[3]);
        }
    }
}
