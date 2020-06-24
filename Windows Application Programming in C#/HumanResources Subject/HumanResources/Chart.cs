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
    public partial class Chart : Form
    {
        private PieChartCategory[] _data;
        public PieChartCategory[] Data
        {
            get { return _data; }
            set
            {
                if (_data == value)
                    return;

                _data = value;

                //trigger the Paint event
                Invalidate();
            }
        }

        public Chart(PieChartCategory[] _data)
        {
            InitializeComponent();
            this._data = _data;
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);

            int legendWidth = 150;
            Graphics graphics = e.Graphics;
            Rectangle clipRectangle = e.ClipRectangle;

            float radius = Math.Min(clipRectangle.Height, clipRectangle.Width - legendWidth) / (float)2;

            int xCenter = (clipRectangle.Width - legendWidth) / 2;
            int yCenter = clipRectangle.Height / 2;

            float x = xCenter - radius;
            float y = yCenter - radius;

            float width = radius * 2;
            float height = radius * 2;

            float percent1 = 0;
            float percent2 = 0;
            for (int i = 0; i < Data.Length; i++)
            {
                if (i >= 1)
                    percent1 += Data[i - 1].Percentage;

                percent2 += Data[i].Percentage;

                float angle1 = percent1 / 100 * 360;
                float angle2 = percent2 / 100 * 360;

                Brush b = new SolidBrush(Data[i].Color);

                graphics.FillPie(b, x, y, width, height, angle1, angle2 - angle1);
            }

            Pen pen = new Pen(Color.Black);
            graphics.DrawEllipse(pen, x, y, width, height);

            float xpos = x + width + 20;
            float ypos = y;
            for (int i = 0; i < Data.Length; i++)
            {
                Brush b = new SolidBrush(Data[i].Color);

                graphics.FillRectangle(b, xpos, ypos, 30, 30);
                graphics.DrawRectangle(pen, xpos, ypos, 30, 30);

                Brush b2 = new SolidBrush(Color.Black);

                graphics.DrawString(
                    Data[i].Label + ": " + Data[i].Percentage + "%",
                    Font,
                    b2,
                    xpos + 35,
                    ypos + 12);

                ypos += 35;
            }
        }
    }
}

