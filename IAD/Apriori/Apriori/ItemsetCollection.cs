﻿//ItemsetCollection.cs

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Apriori.DataStructures;

namespace Apriori.DataStructures
{
    public class ItemsetCollection : List<Itemset>
    {
        #region Methods

        public Itemset GetUniqueItems()
        {
            Itemset unique = new Itemset();

            foreach (Itemset itemset in this)
            {
                unique.AddRange(from item in itemset
                                where !unique.Contains(item)
                                select item);
            }

            return (unique);
        }

        public double FindSupport(string item)
        {
            int matchCount = (from itemset in this
                              where itemset.Contains(item)
                              select itemset).Count();

            double support = ((double)matchCount / (double)this.Count) * 100.0;
            return (support);
        }

        public double FindSupport(Itemset itemset)
        {
            /*int matchCount = (from i in this //обыкновеныый саппорт
                              where i.Contains(itemset)
                              select i).Count();

            double support = ((double)matchCount / (double)this.Count) * 100.0;*/
            /*
             * return (support);
             */
            int matchCount = (from i in this //обыкновеныый саппорт
                              where i.Contains(itemset)
                              select i).Count();

            return matchCount; //~~suppport~~ -- сколько раз встречается данный товар всего 
                    }

        public override string ToString()
        {
            return (string.Join("\r\n", (from itemset in this select itemset.ToString()).ToArray()));
        }

        #endregion
    }
}