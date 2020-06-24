using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Year2_Lupsan_Sabrina
{
    public partial class Add_Memberships : Form
    {
        public Membership m;

        public Add_Memberships(Membership m)
        {
            this.m = m;
            InitializeComponent();
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            if (!ValidateChildren())
                MessageBox.Show("There's an error in the input", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            else
            {
                int id = int.Parse(tbID.Text.Trim());
                string type = tbType.Text.Trim();
                float price = float.Parse(tbPrice.Text.Trim());
                m.Id = id;
                m.Type = type;
                m.Price = price;
            }
        }

        private void tbID_Validating(object sender, CancelEventArgs e)
        {
            if (tbID.Text.Trim().All(char.IsNumber) == false || string.IsNullOrEmpty(tbID.Text.Trim()) || string.IsNullOrWhiteSpace(tbID.Text.Trim()))
            {
                errorProvider.SetError(tbID, "That's not a number!");
                e.Cancel = true;
            }
        }

        private void tbID_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbID, null);
        }

        private void tbType_Validating(object sender, CancelEventArgs e)
        {
            if(string.IsNullOrEmpty(tbType.Text.Trim()) || string.IsNullOrWhiteSpace(tbType.Text.Trim()))
            {
                errorProvider.SetError(tbType, "You need to input a string");
                e.Cancel = true;
            }
        }

        private void tbType_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbType, null);
        }

        private void tbPrice_Validating(object sender, CancelEventArgs e)
        {
            if (tbPrice.Text.Trim().All(char.IsNumber) == false || string.IsNullOrEmpty(tbPrice.Text.Trim()) || string.IsNullOrWhiteSpace(tbPrice.Text.Trim()))
            {
                errorProvider.SetError(tbID, "That's not a number!");
                e.Cancel = true;
            }
        }

        private void tbPrice_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbPrice, null);
        }
    }
}
