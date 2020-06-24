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
    public partial class Chart : Form
    {
        List<Registration> registrations;
        List<AccessPackage> packages;
        public Chart(List<Registration> registrations, List<AccessPackage> packages)
        {
            this.registrations = registrations;
            this.packages = packages;
            InitializeComponent();
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);

            List<int> productCount = new List<int>();

            for(int i=0; i<3; i++)
            {
                int count = 0;
                foreach (Registration r in registrations)
                    if (r.AccessPackageId == packages[i].Id)
                        count++;
                productCount.Add(count);
            }

            var barWidth = e.ClipRectangle.Width / productCount.Count;
            var maxValue = productCount.Max();
            var scale = e.ClipRectangle.Height / maxValue;

            for (int i = 0; i < productCount.Count; i++)
            {
                var barHeight = productCount[i] * scale;

                e.Graphics.FillRectangle(
                    Brushes.SpringGreen,
                    i * barWidth,
                    e.ClipRectangle.Height - barHeight,
                    barWidth,
                    barHeight);

                //aici doar atat a facut dl profesor dar 
                //mai pot fi adaugate lucruri ca sa arate mai frumos chart-ul
            }
        }
    }
}
