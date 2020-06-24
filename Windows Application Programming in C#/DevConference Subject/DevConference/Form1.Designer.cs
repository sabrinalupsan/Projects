namespace DevConference
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.btnAddRegistration = new System.Windows.Forms.Button();
            this.lvRegistrations = new System.Windows.Forms.ListView();
            this.columnHeader1 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader2 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader3 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.tpPrice = new System.Windows.Forms.ToolStripStatusLabel();
            this.btnSortOverName = new System.Windows.Forms.Button();
            this.btnSortOverID = new System.Windows.Forms.Button();
            this.contextMenuStrip = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.deleteToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.modifyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.btnStatistics = new System.Windows.Forms.Button();
            this.statusStrip1.SuspendLayout();
            this.contextMenuStrip.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnAddRegistration
            // 
            this.btnAddRegistration.Location = new System.Drawing.Point(14, 255);
            this.btnAddRegistration.Name = "btnAddRegistration";
            this.btnAddRegistration.Size = new System.Drawing.Size(143, 32);
            this.btnAddRegistration.TabIndex = 0;
            this.btnAddRegistration.Text = "Add registration";
            this.btnAddRegistration.UseVisualStyleBackColor = true;
            this.btnAddRegistration.Click += new System.EventHandler(this.btnAddRegistration_Click);
            // 
            // lvRegistrations
            // 
            this.lvRegistrations.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3});
            this.lvRegistrations.ContextMenuStrip = this.contextMenuStrip;
            this.lvRegistrations.FullRowSelect = true;
            this.lvRegistrations.GridLines = true;
            this.lvRegistrations.HideSelection = false;
            this.lvRegistrations.Location = new System.Drawing.Point(14, 72);
            this.lvRegistrations.Name = "lvRegistrations";
            this.lvRegistrations.Size = new System.Drawing.Size(389, 177);
            this.lvRegistrations.TabIndex = 1;
            this.lvRegistrations.UseCompatibleStateImageBehavior = false;
            this.lvRegistrations.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Company Name";
            this.columnHeader1.Width = 112;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Number of passes";
            this.columnHeader2.Width = 129;
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Category ID";
            this.columnHeader3.Width = 130;
            // 
            // statusStrip1
            // 
            this.statusStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.tpPrice});
            this.statusStrip1.Location = new System.Drawing.Point(0, 302);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(422, 25);
            this.statusStrip1.TabIndex = 2;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // tpPrice
            // 
            this.tpPrice.Name = "tpPrice";
            this.tpPrice.Size = new System.Drawing.Size(145, 20);
            this.tpPrice.Text = "Price of registrations";
            // 
            // btnSortOverName
            // 
            this.btnSortOverName.Location = new System.Drawing.Point(38, 22);
            this.btnSortOverName.Name = "btnSortOverName";
            this.btnSortOverName.Size = new System.Drawing.Size(153, 32);
            this.btnSortOverName.TabIndex = 3;
            this.btnSortOverName.Text = "Sort over name";
            this.btnSortOverName.UseVisualStyleBackColor = true;
            this.btnSortOverName.Click += new System.EventHandler(this.btnSortOverName_Click);
            // 
            // btnSortOverID
            // 
            this.btnSortOverID.Location = new System.Drawing.Point(214, 22);
            this.btnSortOverID.Name = "btnSortOverID";
            this.btnSortOverID.Size = new System.Drawing.Size(162, 32);
            this.btnSortOverID.TabIndex = 4;
            this.btnSortOverID.Text = "Sort over package ID";
            this.btnSortOverID.UseVisualStyleBackColor = true;
            this.btnSortOverID.Click += new System.EventHandler(this.btnSortOverID_Click);
            // 
            // contextMenuStrip
            // 
            this.contextMenuStrip.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.contextMenuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.deleteToolStripMenuItem,
            this.modifyToolStripMenuItem});
            this.contextMenuStrip.Name = "contextMenuStrip";
            this.contextMenuStrip.Size = new System.Drawing.Size(126, 52);
            // 
            // deleteToolStripMenuItem
            // 
            this.deleteToolStripMenuItem.Name = "deleteToolStripMenuItem";
            this.deleteToolStripMenuItem.Size = new System.Drawing.Size(125, 24);
            this.deleteToolStripMenuItem.Text = "Delete";
            this.deleteToolStripMenuItem.Click += new System.EventHandler(this.deleteToolStripMenuItem_Click);
            // 
            // modifyToolStripMenuItem
            // 
            this.modifyToolStripMenuItem.Name = "modifyToolStripMenuItem";
            this.modifyToolStripMenuItem.Size = new System.Drawing.Size(125, 24);
            this.modifyToolStripMenuItem.Text = "Modify";
            this.modifyToolStripMenuItem.Click += new System.EventHandler(this.modifyToolStripMenuItem_Click);
            // 
            // btnStatistics
            // 
            this.btnStatistics.Location = new System.Drawing.Point(214, 255);
            this.btnStatistics.Name = "btnStatistics";
            this.btnStatistics.Size = new System.Drawing.Size(162, 32);
            this.btnStatistics.TabIndex = 6;
            this.btnStatistics.Text = "See statistics";
            this.btnStatistics.UseVisualStyleBackColor = true;
            this.btnStatistics.Click += new System.EventHandler(this.btnStatistics_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(422, 327);
            this.Controls.Add(this.btnStatistics);
            this.Controls.Add(this.btnSortOverID);
            this.Controls.Add(this.btnSortOverName);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.lvRegistrations);
            this.Controls.Add(this.btnAddRegistration);
            this.Name = "Form1";
            this.Text = "Form1";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.contextMenuStrip.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnAddRegistration;
        private System.Windows.Forms.ListView lvRegistrations;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel tpPrice;
        private System.Windows.Forms.Button btnSortOverName;
        private System.Windows.Forms.Button btnSortOverID;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip;
        private System.Windows.Forms.ToolStripMenuItem deleteToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem modifyToolStripMenuItem;
        private System.Windows.Forms.Button btnStatistics;
    }
}

