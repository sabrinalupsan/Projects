using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Year2_Lupsan_Sabrina
{
    public class Gym
    {
        private List<Membership> memberships;
        public List<Membership> Memberships
        {
            get { return memberships; }
            set { memberships = value; }
        }
        public Gym() { }

        public Gym(List<Membership> memberships)
        {
            this.memberships = memberships;
        }
    }
}
