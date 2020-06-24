using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DevConference
{
    public class Registration : IComparer
    {
        public string CompanyName;
        public int NoOfPasses;
        public int AccessPackageId;

        public Registration(string companyName, int noOfPasses, int accessPackageId)
        {
            CompanyName = companyName;
            NoOfPasses = noOfPasses;
            AccessPackageId = accessPackageId;
        }

        public Registration()
        {

        }


        public int Compare(object x, object y)
        {
            Registration r1 = (Registration)x;
            Registration r2 = (Registration)y;
            return (r1.CompanyName).CompareTo(r2.CompanyName);
        }
    }
}
