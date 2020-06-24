using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectPAW.Entities
{
    [Serializable]
    public class Subject
    {
        private string Name;
        public string name
        {
            get { return Name; }
            set
            {
                if (value.Length > 0)
                    Name = value;
            }
        }

        public Subject(string name)
        {
            Name = name;
        }
    }
}
