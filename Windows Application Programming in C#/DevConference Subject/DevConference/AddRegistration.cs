using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DevConference
{
    public partial class AddRegistration : Form
    {
        public Registration reg;
        List<int> packages;

        public AddRegistration(List<AccessPackage> packages)
        {
            this.packages = new List<int>();
            foreach(AccessPackage p in packages)
            {
                this.packages.Add(p.Id);
            }
            InitializeComponent();
        }

        private void AddRegistration_Load(object sender, EventArgs e)
        {
            comboBoxAccess.DataSource = packages;
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            string compName = tbName.Text.Trim();
            int noPasses = int.Parse(tbNoPasses.Text.Trim());
            int AccessPackID = (int)comboBoxAccess.SelectedItem;
            reg = new Registration(compName, noPasses, AccessPackID);
        }
    }
}
