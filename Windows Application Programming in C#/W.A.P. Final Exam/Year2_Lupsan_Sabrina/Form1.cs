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

namespace Year2_Lupsan_Sabrina
{
    public partial class Form1 : Form
    {
        Gym gym;
        private string connectionString = "Data Source=database.db";

        public Form1()
        {
            gym = new Gym();
            gym.Memberships = new List<Membership>();
            InitializeComponent();
        }

        private void btnAddMembership_Click(object sender, EventArgs e)
        {
            Membership m = new Membership();
            Add_Memberships form  = new Add_Memberships(m);
            if(form.ShowDialog() == DialogResult.OK)
            {
                gym.Memberships.Add(m);
            }
            gym.Memberships.Sort();
            DisplayMemberships();
        }

        public void DisplayMemberships()
        {
            lvMemberships.Items.Clear();
            gym.Memberships.Sort();
            foreach (Membership m in gym.Memberships)
            {

                var listViewItem = new ListViewItem(m.Id.ToString());
                listViewItem.SubItems.Add(m.Type);
                listViewItem.SubItems.Add(m.Price.ToString());
                listViewItem.Tag = m;
                lvMemberships.Items.Add(listViewItem);

            }
        }


        private void serialize()
        {
            XmlSerializer serializer = new XmlSerializer(typeof(Gym));

            using (FileStream stream = File.Create("serialized.xml"))
            {
                serializer.Serialize(stream, gym);
            }
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            serialize();
        }

        private void deserialize()
        {
            XmlSerializer serializer = new XmlSerializer(typeof(Gym));

            using (FileStream stream = File.OpenRead("serialized.xml"))
            {
                gym = (Gym)serializer.Deserialize(stream);
                gym.Memberships.Sort();
                DisplayMemberships();
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                //deserialize();
            }
            catch(Exception f)
            { }
        }

        private void editToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(lvMemberships.SelectedItems.Count!=1)
                MessageBox.Show("Choose one membership!");
            else
                if (MessageBox.Show("Are you sure?", "Delete", MessageBoxButtons.OKCancel,
                        MessageBoxIcon.Warning) == DialogResult.OK)
                {

                    ListViewItem lvi = lvMemberships.SelectedItems[0];
                    Membership member = (Membership)lvi.Tag;
                    Membership member2 = member;
                    Add_Memberships form = new Add_Memberships(member);
                if (form.ShowDialog() == DialogResult.OK)
                    {
                        gym.Memberships.Remove(member2);
                        gym.Memberships.Add(form.m);
                        gym.Memberships.Sort();
                        DisplayMemberships();
                    }
                }

        }

        private void btnDatabase_Click(object sender, EventArgs e)
        {
            var query = "INSERT INTO Memberships(Id, Type, Price) " +
                        "VALUES (@id, @type, @price);";
            try
            {
                using (var connection = new SQLiteConnection(connectionString))
                {

                    connection.Open();

                    foreach (Membership m in gym.Memberships)
                    {
                        try
                        {
                            var command = new SQLiteCommand(query, connection);
                            command.Parameters.AddWithValue("@id", (long)m.Id);
                            command.Parameters.AddWithValue("@type", m.Type);
                            command.Parameters.AddWithValue("@price", m.Price);


                            command.ExecuteNonQuery();
                        }
                        catch (Exception) { }
                    }
                }
            }
            catch (Exception) {}
            

        }
    }
}
