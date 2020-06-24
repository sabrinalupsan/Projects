using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HumanResources
{
    public class Department
    {
        public int Id;
        public string Name;

        public Department(int id, string name)
        {
            Id = id;
            Name = name;
        }
    }
}
