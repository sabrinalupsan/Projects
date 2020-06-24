using ProjectPAW.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SQLite;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ProjectPAW
{
    public partial class GradesForm : Form
    {
        public Student student;
        private const string connectionString = "Data source=database2.db";


        public GradesForm(Student student)
        {
            InitializeComponent();
            this.student = student;
            if (student.grades==null || student.grades.Count == 0)
                student.grades = new Dictionary<string, List<int>>();
            student.subjects = new List<Subject>();
        }

        private void addSubject(Student student, string subject)
        {
            var querys = @"insert into Subject (Name) values (@name);";

            using (SQLiteConnection connections = new SQLiteConnection(connectionString))
            {
                connections.Open();


                SQLiteCommand commands = new SQLiteCommand(querys, connections);
                commands.Parameters.AddWithValue("@name", subject);

                commands.ExecuteNonQuery();
                Subject Subject = new Subject(subject);
                Subject.name = subject;
                student.subjects.Add(Subject);
            }
        }

        private void tbSubject_Validating(object sender, CancelEventArgs e)
        {
            if(string.IsNullOrWhiteSpace(tbSubject.Text) || tbSubject.Text.Trim().All(char.IsDigit) == true)
            {
                errorProvider.SetError(tbSubject, "Mandatory");
                e.Cancel = true;
            }
        }

        private void tbSubject_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbSubject, null);
        }

        private void tbGrade_Validating(object sender, CancelEventArgs e)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(tbGrade.Text.ToString()) || tbGrade.Text.Trim().All(char.IsLetter) == true
                || int.Parse(tbGrade.Text) > 10 || int.Parse(tbGrade.Text) < 1)
                {
                    errorProvider.SetError(tbGrade, "Mandatory");
                    e.Cancel = true;
                }
            }catch(Exception x)
            {
                MessageBox.Show("Wrong data.");
            }
        }

        private void tbGrade_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbGrade, null);
        }

        private void btnAddGrade_Click(object sender, EventArgs e)
        { 
            string subject = tbSubject.Text.Trim();
            int AGrade = int.Parse(tbGrade.Text.Trim());
            if (student.grades.ContainsKey(subject)== false)
                student.grades.Add(subject, null);

            if (student.grades[subject] == null)
            {
                List<int> grade = new List<int>();
                grade.Add(AGrade);
                student.grades[subject]=grade;
            }
            else
                student.grades[subject].Add(AGrade);
            addSubject(student, subject);

            DisplayGrades();
        }

        public void DisplayGrades()
        {
            TreeNode tnode;
            tvGrades.Nodes.Clear();
            int y = 0;
            foreach (string subject in student.grades.Keys)
            {
                tnode = tvGrades.Nodes.Add(subject);
                foreach (int x in student.grades[subject])
                {
                    tvGrades.Nodes[y].Nodes.Add(x.ToString());
                    tnode.Expand();
                }
                y++;
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
