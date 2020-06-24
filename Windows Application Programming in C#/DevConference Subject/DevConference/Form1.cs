using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml.Serialization;

namespace DevConference
{
    public partial class Form1 : Form
    {
        List<AccessPackage> packages;
        List<Registration> registrations;

        public Form1()
        {
            packages = new List<AccessPackage>();
            registrations = new List<Registration>();
            InitializeComponent();
        }

        private void LoadAccessPackages()
        {
            using (StreamReader reader = File.OpenText("TextFile1.txt"))
            {
                string line = null;

                while ((line = reader.ReadLine()) != null)
                {
                    var tokens = line.Split(',');
                    var pack = new AccessPackage(int.Parse(tokens[0]), tokens[1], float.Parse(tokens[2]));
                    packages.Add(pack);
                }
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            LoadAccessPackages();
            try { deserialize(); } catch(IOException ex) { }
            
        }

        private void DisplayRegistrations()
        {
            lvRegistrations.Items.Clear();
            foreach (Registration reg in registrations)
            {
                var listViewItem = new ListViewItem(reg.CompanyName);
                listViewItem.SubItems.Add(reg.NoOfPasses.ToString());
                listViewItem.SubItems.Add(reg.AccessPackageId.ToString());
                listViewItem.Tag = reg;
                lvRegistrations.Items.Add(listViewItem);
            }
        }

        private void btnAddRegistration_Click(object sender, EventArgs e)
        {
            AddRegistration form = new AddRegistration(packages);
            if(form.ShowDialog() == DialogResult.OK)
            {
                registrations.Add(form.reg);
                DisplayRegistrations();
            }
            double t = (double)this;
            tpPrice.Text = t.ToString();
        }

        public static explicit operator double(Form1 v)
        {
            double total = 0.0;
            foreach (Registration r in v.registrations)
            {
                AccessPackage p = v.packages.Find(x => x.Id == r.AccessPackageId);
                total += r.NoOfPasses * p.Price;
            }
            return total;
        }

        private void serialize()
        {
            XmlSerializer serializer = new XmlSerializer(typeof(List<Registration>));

            using (FileStream stream = File.Create("serialized.xml"))
            {
                serializer.Serialize(stream, registrations);
            }
        }

        private void deserialize()
        {
            XmlSerializer serializer = new XmlSerializer(typeof(List<Registration>));

            using (FileStream stream = File.OpenRead("serialized.xml"))
            {
                registrations = (List<Registration>)serializer.Deserialize(stream);
                DisplayRegistrations();
            }
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            serialize();
        }

        private void btnSortOverName_Click(object sender, EventArgs e)
        {
            registrations.Sort();
            DisplayRegistrations();
        }

        private void btnSortOverID_Click(object sender, EventArgs e)
        {
            for (int j = 0; j < registrations.Count-1; j++)
                for (int i=j+1; i<registrations.Count;i++)
                if(registrations[j].AccessPackageId > registrations[i].AccessPackageId)
                {
                    Registration r = registrations[i];
                    registrations[i] = registrations[j];
                    registrations[j] = r;
                }

            DisplayRegistrations();
        }

        private void deleteToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(lvRegistrations.SelectedItems.Count == 1)
            {
                ListViewItem lvi = lvRegistrations.SelectedItems[0];
                Registration r =(Registration)lvi.Tag;
                registrations.Remove(registrations.Find(x => x.CompanyName == r.CompanyName && x.AccessPackageId == r.AccessPackageId));
                DisplayRegistrations();
            }
        }

        private void modifyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (lvRegistrations.SelectedItems.Count == 1)
            {
                ListViewItem lvi = lvRegistrations.SelectedItems[0];
                Registration r = (Registration)lvi.Tag;
                AddRegistration form = new AddRegistration(packages);
                if (form.ShowDialog() == DialogResult.OK)
                {
                    registrations.Remove(r);
                    registrations.Add(form.reg);
                    DisplayRegistrations();
                }
            }
        }

        private void btnStatistics_Click(object sender, EventArgs e)
        {
            Chart form = new Chart(registrations, packages);
            form.ShowDialog();
        }
    }
}
