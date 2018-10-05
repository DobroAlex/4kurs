using System;
using System.Collections.Generic;
using Apriori.DataStructures;
namespace Apriori
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            //string Items = String.Empty;
            ItemsetCollection db = new ItemsetCollection();
            Itemset Items = new Itemset();
            HashSet<string> ItemsSet = new HashSet<string>();

            Console.WriteLine("Введите кол-во транзакций:");
            int amount = int.Parse(Console.ReadLine());
            for (int i = 0; i < amount;i++)
            {
                Console.WriteLine("Введите транзакцию номер {0}, по одному товару в ряд. Пустая строка для конца ввода", i);
                Itemset transac = new Itemset();
                for (;;)
                {
                    string item = Console.ReadLine().ToLower();
                    if (item == "" || item == String.Empty)
                    {
                        break;
                    }
                    transac.Add(item);
                }

                db.Add(transac);
            }
            foreach(Itemset transac in db)
            {
                Console.WriteLine(transac);
            }
            foreach(Itemset transac in db)
            {
                foreach (string item in transac)
                {
                    if (!IsHashSetContainsThisString(ItemsSet, item))
                    {
                        ItemsSet.Add(item);
                        Items.Add(item);
                    }
                }
            }
            Console.Write("Все уникальные товары:{");
            foreach(var item in Items)
            {
                Console.Write(item + '\t');
            }
            Console.WriteLine("}");
            Console.WriteLine("Введите поддержку:");
            double support = double.Parse(Console.ReadLine(), System.Globalization.CultureInfo.InvariantCulture);
            Console.WriteLine("Введите уверенность:");
            double confidence = double.Parse(Console.ReadLine(), System.Globalization.CultureInfo.InvariantCulture);
            DoApriori(support, confidence, Items, db);
        }
        public static void DoApriori(double support, double confidence,Itemset _items, ItemsetCollection _db  )
        {
            Itemset uniqueItems = _db.GetUniqueItems();
            ItemsetCollection L = AprioriMining.DoApriori(_db, support);
            Console.WriteLine((L.Count + " Large Itemsets (by Apriori)\n"));
            foreach (Itemset itemset in L)
            {
                Console.WriteLine(itemset.ToString() + " Длина " + itemset.Count);
            }
            Console.WriteLine("\n");
            int maxLSize = L[0].Count;
            Console.WriteLine("maxLSize=" + maxLSize);
            foreach (Itemset itemset in L)
            {
                if (maxLSize < itemset.Count)
                {
                    maxLSize = itemset.Count;
                }
            }

            Console.WriteLine("maxLSize=" + maxLSize);
            foreach (Itemset itemset in L)
            {
                if (itemset.Count < maxLSize)
                {
                    itemset.Clear();
                }
            }

            foreach (Itemset itemset in L)
            {
                Console.WriteLine(itemset.ToString() + " Длина " + itemset.Count);
            }
            List<AssociationRule> allRules = AprioriMining.Mine(_db, L, confidence);

            Console.WriteLine(allRules.Count + " Association Rules\n");
            foreach (AssociationRule rule in allRules)
            {
                Console.WriteLine(rule.ToString()+ "Длина="+rule.getTotalLength()+"\n");
            }
        }
        public static bool IsHashSetContainsThisString(HashSet<string> hashset, string str)
        {
            foreach(var item in hashset)
            {
                if (item == str)
                {
                    return true;
                }
            }
            return false;
        }
         
    }
}
