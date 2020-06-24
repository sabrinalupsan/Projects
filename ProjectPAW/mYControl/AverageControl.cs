using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace mYControl
{
    public partial class AverageControl : UserControl
    {

        List<int> grades;

        public AverageControl()
        {
            InitializeComponent();
            grades = new List<int>();

        }



        private void tbID_Validating(object sender, CancelEventArgs e)
        {
            if (tbID.Text.Trim().All(char.IsLetter) == true || tbID.Text.Trim().Count() == 0 || 
                int.Parse(tbID.Text.Trim())<0 || int.Parse(tbID.Text.Trim())>10)
            {
                errorProvider.SetError(tbID, "Wrong ID.");
                e.Cancel = true;
            }
        }

        private void tbID_Validated(object sender, EventArgs e)
        {
            errorProvider.SetError(tbID, null);

        }

        private void btnAvg_Click(object sender, EventArgs e)
        {
            grades.Add(int.Parse(tbID.Text.Trim()));
            float avg = 0;
            foreach (int x in grades)
            {
                avg += x;
            }
            avg = avg / grades.Count();
            tbAverage.Text = avg.ToString();
        }
    }
}
