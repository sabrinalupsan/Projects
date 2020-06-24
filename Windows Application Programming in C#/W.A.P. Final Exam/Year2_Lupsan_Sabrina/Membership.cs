using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Year2_Lupsan_Sabrina
{
    public class Membership : IComparable
    {
        private int id;
        public int Id { get { return id; } set { id = value; } }
        private string type; //as in silver, gold
        public string Type { get { return type; } set { type = value; } }
        private float price;
        public float Price { get { return price; }  set { price = value; } }

        public Membership(int id, string type, float price)
        {
            this.id = id;
            this.type = type;
            this.price = price;
        }

        public Membership()
        {
        }

        public int CompareTo(object obj)
        {
            Membership m = (Membership)obj;
            //if((price).CompareTo(m.price)==0)
            //{
            //    return type.CompareTo(m.type);
            //}
            return (price).CompareTo(m.price);
        }
    }
}
