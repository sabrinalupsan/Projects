using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace HumanResources
{
    public partial class AddEmployees : Form
    {
        public Employee employee;
        List<int> departmentIDs;

        public AddEmployees(List<int> departmentIDs)
        {
            this.departmentIDs = departmentIDs;
            InitializeComponent();
        }

        private void tbName_Validating(object sender, CancelEventArgs e)
        {
            if(tbName.Text.Trim().Count() < 3)
            {
                errorProvider.SetError(tbName, "More chars");
                e.Cancel = true;
            }
        }

        private void tbName_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbName, null);
        }

        private void dtpBirthday_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(dtpBirthday, null);
        }

        private void dtpBirthday_Validating(object sender, CancelEventArgs e)
        {
            TimeSpan t = DateTime.Today - dtpBirthday.Value ;
            if(t.TotalDays > 36500 || t.TotalDays < 16*365)
            {
                errorProvider.SetError(dtpBirthday, "wrong age");
                e.Cancel = true;
            }
        }

        private void tbWage_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbWage, null);
        }

        private void tbWage_Validating(object sender, CancelEventArgs e)
        {
            if(double.Parse(tbWage.Text.Trim())<0)
            {
                errorProvider.SetError(tbWage, "wrong wage");
                e.Cancel = true;
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            int id = int.Parse(tbID.Text.Trim());
            string name = tbName.Text.Trim();
            DateTime dateTime = dtpBirthday.Value;
            double wage = double.Parse(tbWage.Text.Trim());
            int depId = int.Parse(lbID.SelectedItem.ToString());
            employee = new Employee(id, name, dateTime, wage, depId);
        }

        private void AddEmployees_Load(object sender, EventArgs e)
        {
            lbID.DataSource = departmentIDs;
        }
    }
}
