using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DevConference
{
    public class AccessPackage
    {
        public int Id;
        public string Name;
        public double Price;

        public AccessPackage(int id, string name, double price)
        {
            Id = id;
            Name = name;
            Price = price;
        }
    }
}
