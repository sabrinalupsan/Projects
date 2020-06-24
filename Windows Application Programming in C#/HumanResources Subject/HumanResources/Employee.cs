using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HumanResources
{
    public class Employee
    {
        public int Id;
        public string Name;
        public DateTime Birthdate;
        public double Wage;
        public int IdDepartment;

        public Employee(int id, string name, DateTime birthdate, double wage, int idDepartment)
        {
            Id = id;
            Name = name;
            Birthdate = birthdate;
            Wage = wage;
            IdDepartment = idDepartment;
        }

        public static explicit operator bool(Employee e)
        {
            return e.IdDepartment >= -1;
        }

        public Employee()
        {
        }

        public class EmployeeNameComparer : IComparer<Employee>
        {
            public int Compare(Employee x, Employee y)
            {
                return (x.Name).CompareTo(y.Name);
            }
        }

        public class EmployeeBdayComparer : IComparer<Employee>
        {
            public int Compare(Employee x, Employee y)
            {
                return (y.Birthdate).CompareTo(x.Birthdate);
            }
        }
    }


}
