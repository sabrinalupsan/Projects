using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SQLite;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml.Serialization;
using static HumanResources.Employee;

namespace HumanResources
{
    public partial class Form1 : Form
    {
        Department d1;
        Department d2;
        Department d3;
        List<Employee> employees;

        private string connectionString = "Data Source=database.db";
    
        public Form1()
        {
            d1 = new Department(1, "HR");
            d2 = new Department(2, "IT");
            d3 = new Department(3, "Finance");
            employees = new List<Employee>();
            InitializeComponent();
        }

        private void btnAddEmployee_Click(object sender, EventArgs e)
        {
            List<int> departmentIds = new List<int>();
            departmentIds.Add(d1.Id);
            departmentIds.Add(d2.Id);
            departmentIds.Add(d3.Id);

            AddEmployees form = new AddEmployees(departmentIds);
            if(form.ShowDialog() == DialogResult.OK)
            {
                employees.Add(form.employee);
            }
            DisplayParticipants();
        }

        private void DisplayParticipants()
        {
            dgvEmployees.Rows.Clear();
            foreach (Employee e in employees)
            {
                int index = dgvEmployees.Rows.Add();

                dgvEmployees.Rows[index].Cells[0].Value = e.Id;
                dgvEmployees.Rows[index].Cells[1].Value = e.Name;
                dgvEmployees.Rows[index].Cells[2].Value = e.Birthdate;
                dgvEmployees.Rows[index].Cells[3].Value = e.Wage;
                dgvEmployees.Rows[index].Cells[4].Value = e.IdDepartment;
            }
        }

        private void btnBool_Click(object sender, EventArgs e)
        {
            bool ok = true;
            foreach(Employee emp in employees)
            {
                if ((bool)emp == false)
                    ok = false;
            }
            if (ok == false)
                MessageBox.Show("Not all employees have a department.");
            else
                MessageBox.Show("All employees have a department.");

        }

        private void exportToolStripMenuItem_Click(object sender, EventArgs e)
        {

            XmlSerializer serializer = new XmlSerializer(typeof(List<Employee>));

            SaveFileDialog dialog = new SaveFileDialog();
            dialog.FileName = "serialized.xml";
            if (dialog.ShowDialog() == DialogResult.OK)
            {
                using (FileStream stream = File.Create(dialog.FileName))
                {
                    serializer.Serialize(stream, employees);
                }
            }


        }

        private void sortOverNameToolStripMenuItem_Click(object sender, EventArgs e)
        {
            employees.Sort(new EmployeeNameComparer());
            DisplayParticipants();
        }

        private void sortOverBirthdateToolStripMenuItem_Click(object sender, EventArgs e)
        {
            employees.Sort(new EmployeeBdayComparer());
            DisplayParticipants();
        }

        private void pageSetupToolStripMenuItem_Click(object sender, EventArgs e)
        {
            pageSetupDialog.PageSettings = printDocument.DefaultPageSettings;
            if(pageSetupDialog.ShowDialog() == DialogResult.OK)
            {
                printDocument.DefaultPageSettings = pageSetupDialog.PageSettings;
            }
        }

        int _currentParticipantIndex = 0;

        private void printDocument_PrintPage(object sender, System.Drawing.Printing.PrintPageEventArgs e)
        {
            Font font = new Font("Calibri", 24);

            var pageSettings = e.PageSettings;

            var printAreaHeight = e.MarginBounds.Height;
            var printAreaWidth = e.MarginBounds.Width;


            var marginLeft = pageSettings.Margins.Left;
            var marginTop = pageSettings.Margins.Top;


            const int rowHeight = 40;
            var columnWidth = printAreaWidth / 3;

            StringFormat fmt = new StringFormat(StringFormatFlags.LineLimit);
            fmt.Trimming = StringTrimming.EllipsisCharacter;

            var currentY = marginTop;
            while (_currentParticipantIndex < employees.Count)
            {
                var currentX = marginLeft;

                e.Graphics.DrawRectangle(
                    Pens.Black,
                    currentX,
                    currentY,
                    columnWidth,
                    rowHeight);
                
                e.Graphics.DrawString(
                    employees[_currentParticipantIndex].Id.ToString(),
                    font,
                    Brushes.Black,
                    new RectangleF(currentX, currentY, columnWidth, rowHeight),
                    fmt);

                currentX += columnWidth;

                e.Graphics.DrawRectangle(
                    Pens.Black,
                    currentX,
                    currentY,
                    columnWidth,
                    rowHeight);
                e.Graphics.DrawString(
                    employees[_currentParticipantIndex].Name,
                    font,
                    Brushes.Black,
                    currentX,
                    currentY,
                    fmt);
                currentX += columnWidth;

                e.Graphics.DrawRectangle(
                    Pens.Black,
                    currentX,
                    currentY,
                    columnWidth,
                    rowHeight);
                e.Graphics.DrawString(
                    employees[_currentParticipantIndex].Birthdate.ToShortDateString(),
                    font,
                    Brushes.Black,
                    currentX,
                    currentY,
                    fmt);

                _currentParticipantIndex++;
                currentY += rowHeight;

                if (currentY + rowHeight > printAreaHeight)
                {
                    e.HasMorePages = true;
                    break;
                }
            }
        }

        private void printPreviewToolStripMenuItem_Click(object sender, EventArgs e)
        {
            printPreviewDialog.ShowDialog();
        }

        private void printDocument_BeginPrint(object sender, System.Drawing.Printing.PrintEventArgs e)
        {
            _currentParticipantIndex = 0;

        }

        private void LoadEmployees()
        {
            var query = "SELECT * FROM Employees";

            using (var connection = new SQLiteConnection(connectionString))
            {
                var command = new SQLiteCommand(query, connection);

                connection.Open();
                using (var reader = command.ExecuteReader()) //retrieving multiple rows
                {
                    while (reader.Read())
                    {
                        long id = (long)reader["Id"];
                        string name = (string)reader["Name"];
                        DateTime bday = (DateTime)(reader["Birthday"]);
                        double wage = (double)reader["Wage"];
                        long depID = (long)reader["DepartmentId"];

                        var emp = new Employee((int)id, name, bday, wage, (int)depID);
                        employees.Add(emp);
                    }
                }
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
            
        }

        private void btnChart_Click(object sender, EventArgs e)
        {
            List<int> count = new List<int>();
            for(int i=1; i<4; i++)
            {
                int x = 0;
                foreach(Employee emp in employees)
                {
                    if (emp.IdDepartment == i)
                        x++;
                }
                count.Add(x);
            }
            int scalingFactor = employees.Count;
            scalingFactor = 100/scalingFactor;

            PieChartCategory[] pieCategories = {
                new PieChartCategory("Department 1", count[0]*scalingFactor, Color.Red),
                new PieChartCategory("Department 2", count[1]*scalingFactor, Color.Blue),
                new PieChartCategory("Department 3", count[2]*scalingFactor, Color.Magenta),
            };
            Chart chart = new Chart(pieCategories);
            chart.Data = pieCategories;
            chart.ShowDialog();
        }
    }
}
