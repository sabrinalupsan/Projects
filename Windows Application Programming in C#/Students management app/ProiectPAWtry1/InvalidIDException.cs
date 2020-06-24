using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectPAW
{
    class InvalidIDException : Exception
    {
        public int id { get; set; }
        
        public InvalidIDException(int id)
        {
            this.id = id;
        }
    }
}
