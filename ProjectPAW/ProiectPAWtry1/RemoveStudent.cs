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
    public partial class RemoveStudent : Form
    {
        private int ID;
        public int id
        {
            get { return ID; }
            set
            {
                if (value > 0)
                    ID = value;
            }
        }

        public RemoveStudent()
        {
            InitializeComponent();
            ID = id;
        }

        private void tbIDDelete_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbIDDelete, null);
        }

        private void tbIDDelete_Validating(object sender, CancelEventArgs e)
        {
            if(tbIDDelete.Text.Trim().Length<1 || tbIDDelete.Text.Trim().All(char.IsLetter)==true)
            {
                errorProvider.SetError(tbIDDelete, "You need to input a number.");
                e.Cancel = true;
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                ID = int.Parse(tbIDDelete.Text.Trim());
            }
            catch(Exception x)
            {
                MessageBox.Show("Wrong ID");
            }
        }
    }
}
