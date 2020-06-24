using ProjectPAW.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ProjectPAW
{
    public partial class DropSubjects : Form
    {
        public Student student;
        public List<Dashboard> dashboards;

        public DropSubjects(List<Dashboard> dashboards)
        {
            InitializeComponent();
            student = new Student();
            student.subjects = new List<Subject>();
            this.dashboards = dashboards;
            
        }

        private void DropSubjects_Load(object sender, EventArgs e)
        {
            
        }

        private void btnSeeSubjects_Click(object sender, EventArgs e)
        {
            int index = int.Parse(tbID.Text.Trim());
            bool ok = false;
            foreach (Dashboard board in dashboards)
            {
                if (ok == true)
                    break;
                foreach (Student student in board.students)
                {
                    if (student.studentID == index)
                    {
                        this.student = student;
                        ok = true;
                        break;
                    }
                }
            }
            if (ok == false)
                MessageBox.Show("There is no student with that ID in a dashboard.");

            if (student.grades == null)
                MessageBox.Show("The student is not enrolled in any course this year.");
            else
            {
                foreach (string s in student.grades.Keys)
                {
                    checkSubjects.Items.Add(s);
                }
            }

        }

        private void tbID_Validating(object sender, CancelEventArgs e)
        {
            bool ok = false;
            foreach(Dashboard board in dashboards)
            {
                if (ok == true)
                    break;
                foreach(Student student in board.students)
                {
                    if(student.studentID == int.Parse(tbID.Text.Trim()))
                    {
                        ok = true;
                        break;
                    }
                }
            }
            if(ok==false)
            {
                errorProvider.SetError(tbID, "There is no student with that ID in the dashboard.");
                e.Cancel = true;
            }
        }

        private void tbID_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbID, null);
        }

        private void btnDrop_Click(object sender, EventArgs e)
        {
            foreach (var item in checkSubjects.CheckedItems)
            {
                student.grades.Remove((string)item);
            }
        }

        
    }
}
