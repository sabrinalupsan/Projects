using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectPAW.Entities
{
    public class Dashboard : ICloneable
    {

        private string NameOfClass;
        public string nameOfClass
        {
            get { return NameOfClass; }
            set
            {
                if (value.Length < 1)
                    throw new Exception();
                NameOfClass = value;
            }
        }
        private List<Student> Students;
        public List<Student> students
        {
            get { return Students; }
            set
            {
                Students = new List<Student>();
                foreach(Student stud in value)
                {
                    if (Students != null)
                    {
                        if (Students.Contains(stud) != true)
                            Students.Add(stud);
                    }
                    else
                        Students.Add(stud);
                }
            }
        }
        public Dashboard()
        {

        }
        public Dashboard(List<Student> students, string nameOfClass)
        {
            this.nameOfClass = nameOfClass;
            this.students = students;
        }

        public object Clone()
        {
            List<Student> studs = new List<Student>();
            if (students != null)
                for (int i = 0; i < students.Count; i++)
                    studs.Add(students[i]);
            Dashboard board = new Dashboard(studs, nameOfClass);
            return board;
        }
    }
}
