using System;
namespace GenAlgDio
{
    public struct Gene
    {
        public int[] Allele;  //аллель хромосом, состовляет один ген
        int fitness;    //фитнесс, т.е приспособленность данного гена к решению
        double likelhood;   //TODO : комментарий
        public Gene(Gene g)
        {
            this.Allele = new int[g.Allele.Length];
            g.Allele.CopyTo(this.Allele, 0);    //клонируем все данные сюда
            fitness = g.fitness;
            likelhood = g.likelhood;
        }
        public Gene(int size = 4, int targetVal = 30)
        {
            fitness = 0;
            likelhood = 0;
            Allele = new int[size];
            Allele = GenerateRandomAllele(size, targetVal);
            fitness = CalcDio.CalcFitness(this);
        }
        public int CalcFitness ()
        {
            return MainClass.Equation(Allele[0], Allele[1], Allele[2], Allele[3]);
        }
        public int[] GenerateRandomAllele(int size = 4, int maxVal = 30)
        {
            int[] randAllele = new int[size];
            Random rnd = new Random(DateTime.Now.Millisecond);
            for (int i = 0; i < size; i++)
            {
                randAllele[i] = rnd.Next(0, maxVal);    //генерирует случайный int[0;30]
            }
            return randAllele;
        }
        public   bool IsEqual (Gene g2) //проверяем два гена на равенство по аллелям
        {
            for (int i = 0; i < this.Allele.Length; i++)
            {
                if (this.Allele[i] != g2.Allele[i])
                {
                    return false;
                }
            }
            return true;
        }

    }
}
