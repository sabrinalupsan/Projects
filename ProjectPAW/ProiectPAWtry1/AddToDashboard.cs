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
    public partial class AddToDashboard : Form
    {
        public Dashboard Dashboard { get; set; }

        public AddToDashboard(List<Student> selectedStudents)
        {
            InitializeComponent();
            Dashboard = new Dashboard();

            Dashboard.students = new List<Student>();
            Dashboard.students = selectedStudents;

        }

        //private void AddToDashboard_Load(object sender, EventArgs e)
        //{
       //     this.dashboard.students = dashboard.students;
        //}

        public void DisplayStudents()
        {
            //lvStudentsCount.Text = students.Count.ToString();
            lvClass.Items.Clear();

            foreach (Student stud in Dashboard.students)
            {

                var listViewItem = new ListViewItem(stud.lastName);
                listViewItem.SubItems.Add(stud.firstName);
                listViewItem.SubItems.Add(stud.studentID.ToString());
                //foreach(int x in stud.grades)
                //    listViewItem.SubItems.Add(x.ToString()); this is to add after data biding
                //listViewItem.Tag = stud;
                lvClass.Items.Add(listViewItem);
            }

        }

        private void btn_ShowClass_Click(object sender, EventArgs e)
        {
                DisplayStudents();
        }

        private void AddToDashboard_Load(object sender, EventArgs e)
        {
            //dashboard.students = selectedStudents;
            int x;
        }

        private void btn_AddDashboard_Click(object sender, EventArgs e)
        {
            Dashboard.nameOfClass = tbClassName.Text;
            Close();
        }

        public string getName()
        {
            return Dashboard.nameOfClass;
        }

        private void tbClassName_Validating(object sender, CancelEventArgs e)
        {
            if(tbClassName.Text.Trim().Length<1)
            {
                errorProvider.SetError(tbClassName, "Wrong class name.");
                e.Cancel = true;
            }
        }

        private void tbClassName_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbClassName, null);
        }

        
    }
}
