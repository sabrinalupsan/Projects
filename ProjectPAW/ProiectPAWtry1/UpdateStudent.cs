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
    public partial class UpdateStudent : Form
    {
        public Student student;
        public List<Student> students;

        public UpdateStudent(Student student, List<Student> students)
        {
            InitializeComponent();
            this.student = student;
            this.students = students;
        }

        private void tbStudentID_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbStudentID, null);
        }

        private void tbStudentID_Validating(object sender, CancelEventArgs e)
        {
            if (string.IsNullOrWhiteSpace(tbStudentID.ToString()) || tbStudentID.Text.Trim().All(char.IsLetter) == true)
            {
                errorProvider.SetError(tbStudentID, "That is not a valid student ID");
                e.Cancel = true;
            }
        }

        private void tbLastName_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbLastName, null);
        }

        private void tbLastName_Validating(object sender, CancelEventArgs e)
        {
            if (tbLastName.Text.Trim().Length < 1 || tbLastName.Text.Trim().All(char.IsDigit) == true)
            {
                errorProvider.SetError(tbLastName, "You need to input at least 1 character.");
                e.Cancel = true;
            }
        }

        private void tbFirstName_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbFirstName, null);
        }

        private void tbFirstName_Validating(object sender, CancelEventArgs e)
        {
            if (tbFirstName.Text.Trim().Length < 1 || tbFirstName.Text.Trim().All(char.IsDigit) == true)
            {
                errorProvider.SetError(tbFirstName, "You need to input at least 1 character.");
                e.Cancel = true;
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            
            int x = int.Parse(tbStudentID.Text.Trim());
            try
            {
                if(x!=student.studentID)
                    foreach (Student student in students)
                    { 
                        if (student.studentID == x)
                            throw new InvalidIDException(x);
                    }
                student.studentID = x;

                student.lastName = tbLastName.Text.Trim();
                student.firstName = tbFirstName.Text.Trim();
            }
            catch (InvalidIDException inv)
            {
                MessageBox.Show("Wrong ID");
            }
        
            DialogResult = DialogResult.OK;
            
        }

        private void UpdateStudent_Load(object sender, EventArgs e)
        {
            tbLastName.Text = student.lastName;
            tbFirstName.Text = student.firstName;
            tbStudentID.Text = student.studentID.ToString();
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
