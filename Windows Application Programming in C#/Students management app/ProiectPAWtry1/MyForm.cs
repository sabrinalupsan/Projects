using ProjectPAW;
using ProjectPAW;
using ProjectPAW.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SQLite;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ProjectPAW
{
    public partial class MyForm : Form
    {
        private List<Student> students { get; set; }
        private List<Dashboard> dashboards { get; set; }
        private List<Student> selectedStudents;

        private const string connectionString = "Data source=database.db";


        public MyForm()
        {
            InitializeComponent();
            students = new List<Student>();
            dashboards = new List<Dashboard>();
            selectedStudents = new List<Student>();
            tpProgress.Value = 100;
        }

        private void tbLastName_Validating(object sender, CancelEventArgs e)
        {
            if (tbLastName.Text.Trim().Length < 1 || tbLastName.Text.Trim().All(char.IsDigit) == true)
            {
                errorProvider.SetError(tbLastName, "You need to input at least 1 character.");
                e.Cancel = true;
            }
        }

        private void tbLastName_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbLastName, null);
        }

        private void tbFirstName_Validating(object sender, CancelEventArgs e)
        {
            if (tbFirstName.Text.Trim().Length < 1 || tbFirstName.Text.Trim().All(char.IsDigit) == true)
            {
                errorProvider.SetError(tbFirstName, "You need to input at least 1 character.");
                e.Cancel = true;
            }
        }

        private void tbFirstName_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbFirstName, null);
        }

        private void tbStudentID_Validating(object sender, CancelEventArgs e)
        {

            if (string.IsNullOrWhiteSpace(tbStudentID.ToString()) || tbStudentID.Text.Trim().All(char.IsLetter) == true)
                {
                    errorProvider.SetError(tbStudentID, "That is not a valid student ID");
                    e.Cancel = true;
                }
            
        }

        private void tbStudentID_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbStudentID, null);
        }

        public void DisplayStudents()
        {
            lvStudents.Items.Clear();
            foreach (Student stud in students)
            {

                var listViewItem = new ListViewItem(stud.lastName);
                listViewItem.SubItems.Add(stud.firstName);
                listViewItem.SubItems.Add(stud.studentID.ToString());
                if(stud.grades!=null)
                foreach(string subject in stud.grades.Keys)
                {
                    string s = "";
                    foreach(int x in stud.grades[subject])
                    {
                        s +=x.ToString();
                    }
                    listViewItem.SubItems.Add(s);
                }
                listViewItem.ImageKey = "book.png";
                listViewItem.Tag = stud;
                lvStudents.Items.Add(listViewItem);

            }
        }

        private void btn_addStudent_Click(object sender, EventArgs e)
        {
            if (!ValidateChildren())
                MessageBox.Show("There's an error in the input", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            else
            {
                try
                {
                    string lastName = tbLastName.Text.Trim();
                    string firstName = tbFirstName.Text.Trim();
                    int id = int.Parse(tbStudentID.Text);

                    foreach(Student student in students)
                    {
                        if (student.studentID == id)
                            throw new InvalidIDException(id);
                    }

                    Student stud = new Student(lastName, firstName, id);
                    //students.Add(stud);
                    AddStudent(stud);
                    DisplayStudents();
                }
                catch (InvalidIDException inv)
                {
                    MessageBox.Show("Wrong ID");
                }
                catch(SQLiteException exception)
                {
                    MessageBox.Show("Database violation. A student with that ID already is in the database.");
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Wrong data");
                }

            }
        }

        private void AddStudent(Student student)
        {
            var query = @"insert into Student(Lastname, Firsname, Id) values (@lastName, @firstName, @studentID);";

            using (SQLiteConnection connection = new SQLiteConnection(connectionString))
            {
                connection.Open();


                SQLiteCommand command = new SQLiteCommand(query, connection);
                command.Parameters.AddWithValue("@lastName", student.lastName);
                command.Parameters.AddWithValue("@firstName", student.firstName);
                command.Parameters.AddWithValue("@studentID", student.studentID);
                command.ExecuteNonQuery();
                
                students.Add(student);
            }
        }

        

        private void btn_deleteStudent_Click(object sender, EventArgs e)
        {
            if (lvStudents.SelectedItems.Count != 1)
                MessageBox.Show("Choose one student!");
            else
                if (MessageBox.Show("Are you sure?", "Delete", MessageBoxButtons.OKCancel,
                    MessageBoxIcon.Warning) == DialogResult.OK)
                {
                    ListViewItem lvi = lvStudents.SelectedItems[0];
                    Student stud = (Student)lvi.Tag;
                    foreach (Dashboard board in dashboards)
                    {
                        if (board.students.Contains(stud) == true)
                            board.students.Remove(stud);
                    }
                    students.Remove(stud);
                    DisplayStudents();
                    btnSeeGrades.PerformClick();
                }
        }

        private void btnSerializeBinary_Click(object sender, EventArgs e)
        {
            BinaryFormatter formatter = new BinaryFormatter();

            using (FileStream stream = File.Create("serialized.bin"))
            {
                formatter.Serialize(stream, students);
            }
        }

        private void btnDeserializeBinary_Click(object sender, EventArgs e)
        {
            BinaryFormatter formatter = new BinaryFormatter();
            try
            {
                using (FileStream stream = File.OpenRead("serialized.bin"))
                {
                    students = (List<Student>)formatter.Deserialize(stream);
                    DisplayStudents();
                }
            }
            catch (Exception d)
            {
                MessageBox.Show("There was an error! Try serializing existing data before!");
            }
        }

        private void btn_addGrades_Click(object sender, EventArgs e)
        {
            if (lvStudents.SelectedItems.Count != 1)
                MessageBox.Show("Choose a student");
            else
            {
                ListViewItem lvi = lvStudents.SelectedItems[0];
                Student student = (Student)lvi.Tag;
                GradesForm form = new GradesForm(student);
                if (form.ShowDialog() == DialogResult.OK)
                    DisplayStudents();
            }
        }

        private void btnExportTextFile_Click(object sender, EventArgs e)
        {
            using (System.IO.StreamWriter stream = new System.IO.StreamWriter("textfile.txt"))
            {
                if (dashboards.Count == 0)
                    MessageBox.Show("All students must be included in the dashboard!");
                else
                {
                    foreach (Dashboard dashboard in dashboards)
                    {
                        stream.WriteLine("Name of class: " + dashboard.nameOfClass);
                        stream.WriteLine("Its students: ");
                        foreach (Student student in dashboard.students)
                        {
                            stream.WriteLine(student.lastName + " " + student.firstName + " "+student.studentID);
                            try
                            {
                                foreach (string subject in student.grades.Keys)
                                {
                                    stream.WriteLine("Subject: " + subject);
                                    stream.WriteLine("The grades: ");
                                    foreach (int x in student.grades[subject])
                                    {
                                        stream.WriteLine(x);
                                    }
                                }
                            }
                            catch (Exception y)
                            {
                                stream.WriteLine("No grades");
                            }
                            
                        }
                    }
                    MessageBox.Show("Export complete.");
                }
            }
        }

        private void btn_addToDashboard_Click(object sender, EventArgs e)
        {
            try
            {
                if (lvStudents.CheckedItems.Count == 0)
                    MessageBox.Show("You need to check at least a student!");
                else
                {
                    selectedStudents.Clear();
                    foreach (var listviewitem in lvStudents.CheckedItems)
                    {
                        selectedStudents.Add((Student)((ListViewItem)listviewitem).Tag);
                        foreach (Dashboard dashboard in dashboards)
                        {
                            if (dashboard.students.Exists(studentID => selectedStudents.Contains(studentID)))
                            {
                                MessageBox.Show("At least one of the students selected is already enrolled in a class.");
                                throw new Exception();
                            }
                        }
                    }
                    AddToDashboard form = new AddToDashboard(selectedStudents);


                    if (form.ShowDialog() == DialogResult.OK)
                        DisplayStudents();
                    Dashboard board = (Dashboard)form.Dashboard.Clone();
                    board.nameOfClass = form.Dashboard.nameOfClass;
                    bool ok = false; // the board is not already in dashboards
                    foreach(Dashboard aBoard in dashboards)
                    {
                        if (aBoard.nameOfClass == board.nameOfClass)
                        {
                            foreach(Student stud in board.students)
                            {
                                aBoard.students.Add(stud);
                            }
                            ok = true;
                            break;
                        }
                    }
                    if(ok==false)
                        dashboards.Add(board);
                }
            }
            catch (Exception f) { }
            foreach(ListViewItem item in lvStudents.CheckedItems)
            {
                item.Checked = false;
            }
        }

        private void btn_smallIcon_Click(object sender, EventArgs e)
        {
            lvStudents.View = View.SmallIcon;
        }

        private void btn_Details_Click(object sender, EventArgs e)
        {
            lvStudents.View = View.Details;
        }

        private void btn_LargeIcon_Click(object sender, EventArgs e)
        {
            lvStudents.View = View.LargeIcon;
        }

        private void lvStudents_ItemCheck(object sender, ItemCheckEventArgs e)
        {
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void btnSeeGrades_Click(object sender, EventArgs e)
        {
            if (dashboards.Count < 1)
                MessageBox.Show("There needs to be at least 1 dashboard!");
            else
            {
                TreeNode tnode;
                tvGrades.Nodes.Clear();
                int y = 0;
                int z = 0;
                foreach (Dashboard dashboard in dashboards)
                {
                    if (dashboard.students.Count == 0)
                    {
                        tvGrades.Nodes.Clear();
                        y--;
                    }
                    else
                    {
                        z = 0;
                        tnode = tvGrades.Nodes.Add(dashboard.nameOfClass);
                        tvGrades.Nodes[y].Nodes.Clear();
                        foreach (Student stud in dashboard.students)
                        {
                            tvGrades.Nodes[y].Nodes.Add(stud.lastName + " " + stud.firstName + " " + stud.studentID);
                            tnode.Expand();
                            if (stud.grades != null)
                            {

                                foreach (string subject in stud.grades.Keys)
                                {
                                    string s = subject + ": ";
                                    foreach (int x in stud.grades[subject])
                                    {
                                        s += x + " ";
                                    }
                                    tvGrades.Nodes[y].Nodes[z].Nodes.Add(s);
                                    tnode.Expand();
                                }
                                z++;
                            }
                        }
                    }
                    y++;
                }
            }
        }

        private void btnUpdateStud_Click(object sender, EventArgs e)
        {
            if (lvStudents.SelectedItems.Count != 1)
                MessageBox.Show("Choose a student");
            else
            {
                ListViewItem lvi = lvStudents.SelectedItems[0];
                Student student = (Student)lvi.Tag;
                students.Remove(student);
                UpdateStudent form = new UpdateStudent(student, students);
                students.Add(student);                

                if (form.ShowDialog() == DialogResult.OK)
                    DisplayStudents();
            }
        }

        private void removeAStudentFromDashboardToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RemoveStudent form = new RemoveStudent();
            bool isFound = false;
            bool wasIntroduced = false;
            DialogResult result= form.ShowDialog();
            if (result == DialogResult.OK)
            {
                int id = form.id;
                if (id >= 0)
                    wasIntroduced = true;
                foreach (Dashboard board in dashboards)
                {
                    foreach (Student stud in board.students)
                    {
                        if (stud.studentID == id)
                        {
                            isFound = true;
                            board.students.Remove(stud);
                            break;
                        }
                    }
                }
                btnSeeGrades.PerformClick();
            }
            if(isFound==false && result==DialogResult.OK)
            {
                MessageBox.Show("There is no such student in the dashboard.");
            }
        }

        private void tsDropSubjects_Click(object sender, EventArgs e)
        {
            if (students.Count < 1)
                MessageBox.Show("There is no student. You can't drop subjects.");
            else
            {
                DropSubjects form = new DropSubjects(dashboards);
                if(form.ShowDialog()==DialogResult.OK)
                {
                    students.Remove(form.student);
                    students.Add(form.student);
                }
            }

        }

    }
}

