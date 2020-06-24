using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjectPAW.Entities
{
    [Serializable]
    public class Student
    {
        private string LastName;
        public string lastName
        {
            get { return LastName; }
            set
            {
                if(value.Length>0)
                    LastName = value;
                
            }
        }
        private string FirstName;
        public string firstName
        {
            get { return FirstName; }
            set
            {
                if (value.Length > 0)
                    FirstName = value;
            }
        }
        private int StudentID;
        public int studentID
        {
            get { return StudentID; }
            set
            {
                if (value < 0)
                    throw new InvalidIDException(value);
                StudentID = value;
            }
        }

        private Dictionary<string, List<int>> Grades;
        public Dictionary<string, List<int>> grades
        {
            get { return Grades; }
            set
            {
                Grades = new Dictionary<string, List<int>>();
                Grades = value;
            }
        }

        private List<Subject> Subjects;
        public List<Subject> subjects { get { return Subjects; }
            set {
                Subjects = new List<Subject>();
                Subjects = value;
            } }

        public Student(string lastName, string firstName, int studentID, Dictionary<Subject, List<int>> grades)
        {
            Subjects = new List<Subject>();
            this.lastName = lastName;
            this.firstName = firstName;
            this.studentID = studentID;
            //this.grades = grades;
        }

        public Student(string lastName, string firstName, int studentID)
        {
            Subjects = new List<Subject>();
            this.lastName = lastName;
            this.firstName = firstName;
            this.studentID = studentID;
        }

        public Student()
        {
            Subjects = new List<Subject>();
        }

     
    }
}
