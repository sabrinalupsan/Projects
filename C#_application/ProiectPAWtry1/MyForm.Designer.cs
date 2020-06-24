namespace ProjectPAW
{
    partial class MyForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MyForm));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.tbLastName = new System.Windows.Forms.TextBox();
            this.tbStudentID = new System.Windows.Forms.TextBox();
            this.tbFirstName = new System.Windows.Forms.TextBox();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.jSONSerializationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.btnSerializeBinary = new System.Windows.Forms.ToolStripMenuItem();
            this.btnDeserializeBinary = new System.Windows.Forms.ToolStripMenuItem();
            this.btnExportTextFile = new System.Windows.Forms.ToolStripMenuItem();
            this.removeAStudentFromDashboardToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.tsDropSubjects = new System.Windows.Forms.ToolStripMenuItem();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.btn_addGrades = new System.Windows.Forms.Button();
            this.lvStudents = new System.Windows.Forms.ListView();
            this.columnHeader1 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader2 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader3 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.btn_Details = new System.Windows.Forms.Button();
            this.btn_smallIcon = new System.Windows.Forms.Button();
            this.btn_LargeIcon = new System.Windows.Forms.Button();
            this.btn_addStudent = new System.Windows.Forms.Button();
            this.btn_deleteStudent = new System.Windows.Forms.Button();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.toolStripStatusLabel1 = new System.Windows.Forms.ToolStripStatusLabel();
            this.tpProgress = new System.Windows.Forms.ToolStripProgressBar();
            this.btn_addToDashboard = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.btnClose = new System.Windows.Forms.Button();
            this.dgvDashboards = new System.Windows.Forms.DataGridView();
            this.btnSeeGrades = new System.Windows.Forms.Button();
            this.tvGrades = new System.Windows.Forms.TreeView();
            this.btnUpdateStud = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.menuStrip1.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvDashboards)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(17, 46);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(76, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "Last Name";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(17, 80);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(76, 17);
            this.label2.TabIndex = 1;
            this.label2.Text = "First Name";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(17, 117);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(74, 17);
            this.label3.TabIndex = 2;
            this.label3.Text = "Student ID";
            // 
            // tbLastName
            // 
            this.tbLastName.Location = new System.Drawing.Point(123, 46);
            this.tbLastName.Name = "tbLastName";
            this.tbLastName.Size = new System.Drawing.Size(148, 22);
            this.tbLastName.TabIndex = 3;
            this.tbLastName.Validating += new System.ComponentModel.CancelEventHandler(this.tbLastName_Validating);
            this.tbLastName.Validated += new System.EventHandler(this.tbLastName_Validated);
            // 
            // tbStudentID
            // 
            this.tbStudentID.Location = new System.Drawing.Point(123, 117);
            this.tbStudentID.Name = "tbStudentID";
            this.tbStudentID.Size = new System.Drawing.Size(148, 22);
            this.tbStudentID.TabIndex = 4;
            this.tbStudentID.Validating += new System.ComponentModel.CancelEventHandler(this.tbStudentID_Validating);
            this.tbStudentID.Validated += new System.EventHandler(this.tbStudentID_Validated);
            // 
            // tbFirstName
            // 
            this.tbFirstName.Location = new System.Drawing.Point(123, 80);
            this.tbFirstName.Name = "tbFirstName";
            this.tbFirstName.Size = new System.Drawing.Size(148, 22);
            this.tbFirstName.TabIndex = 5;
            this.tbFirstName.Validating += new System.ComponentModel.CancelEventHandler(this.tbFirstName_Validating);
            this.tbFirstName.Validated += new System.EventHandler(this.tbFirstName_Validated);
            // 
            // errorProvider
            // 
            this.errorProvider.ContainerControl = this;
            // 
            // menuStrip1
            // 
            this.menuStrip1.BackColor = System.Drawing.Color.FloralWhite;
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.jSONSerializationToolStripMenuItem,
            this.btnExportTextFile,
            this.removeAStudentFromDashboardToolStripMenuItem,
            this.tsDropSubjects});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(880, 28);
            this.menuStrip1.TabIndex = 6;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // jSONSerializationToolStripMenuItem
            // 
            this.jSONSerializationToolStripMenuItem.Checked = true;
            this.jSONSerializationToolStripMenuItem.CheckState = System.Windows.Forms.CheckState.Checked;
            this.jSONSerializationToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.btnSerializeBinary,
            this.btnDeserializeBinary});
            this.jSONSerializationToolStripMenuItem.Name = "jSONSerializationToolStripMenuItem";
            this.jSONSerializationToolStripMenuItem.Size = new System.Drawing.Size(148, 24);
            this.jSONSerializationToolStripMenuItem.Text = "Binary Serialization";
            // 
            // btnSerializeBinary
            // 
            this.btnSerializeBinary.Name = "btnSerializeBinary";
            this.btnSerializeBinary.Size = new System.Drawing.Size(216, 26);
            this.btnSerializeBinary.Text = "Serialize";
            this.btnSerializeBinary.Click += new System.EventHandler(this.btnSerializeBinary_Click);
            // 
            // btnDeserializeBinary
            // 
            this.btnDeserializeBinary.Name = "btnDeserializeBinary";
            this.btnDeserializeBinary.Size = new System.Drawing.Size(216, 26);
            this.btnDeserializeBinary.Text = "Deserialize";
            this.btnDeserializeBinary.Click += new System.EventHandler(this.btnDeserializeBinary_Click);
            // 
            // btnExportTextFile
            // 
            this.btnExportTextFile.Name = "btnExportTextFile";
            this.btnExportTextFile.Size = new System.Drawing.Size(148, 24);
            this.btnExportTextFile.Text = "Export to a text file";
            this.btnExportTextFile.Click += new System.EventHandler(this.btnExportTextFile_Click);
            // 
            // removeAStudentFromDashboardToolStripMenuItem
            // 
            this.removeAStudentFromDashboardToolStripMenuItem.Name = "removeAStudentFromDashboardToolStripMenuItem";
            this.removeAStudentFromDashboardToolStripMenuItem.Size = new System.Drawing.Size(251, 24);
            this.removeAStudentFromDashboardToolStripMenuItem.Text = "Remove a student from dashboard";
            this.removeAStudentFromDashboardToolStripMenuItem.Click += new System.EventHandler(this.removeAStudentFromDashboardToolStripMenuItem_Click);
            // 
            // tsDropSubjects
            // 
            this.tsDropSubjects.Name = "tsDropSubjects";
            this.tsDropSubjects.Size = new System.Drawing.Size(112, 24);
            this.tsDropSubjects.Text = "Drop subjects";
            this.tsDropSubjects.Click += new System.EventHandler(this.tsDropSubjects_Click);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(61, 4);
            // 
            // btn_addGrades
            // 
            this.btn_addGrades.Location = new System.Drawing.Point(708, 46);
            this.btn_addGrades.Name = "btn_addGrades";
            this.btn_addGrades.Size = new System.Drawing.Size(145, 36);
            this.btn_addGrades.TabIndex = 9;
            this.btn_addGrades.Text = "Add &Grades";
            this.btn_addGrades.UseVisualStyleBackColor = true;
            this.btn_addGrades.Click += new System.EventHandler(this.btn_addGrades_Click);
            // 
            // lvStudents
            // 
            this.lvStudents.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.lvStudents.BackColor = System.Drawing.Color.Aquamarine;
            this.lvStudents.CheckBoxes = true;
            this.lvStudents.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3});
            this.lvStudents.FullRowSelect = true;
            this.lvStudents.GridLines = true;
            this.lvStudents.HideSelection = false;
            this.lvStudents.HoverSelection = true;
            this.lvStudents.Location = new System.Drawing.Point(5, 206);
            this.lvStudents.Name = "lvStudents";
            this.lvStudents.Size = new System.Drawing.Size(492, 190);
            this.lvStudents.SmallImageList = this.imageList1;
            this.lvStudents.TabIndex = 10;
            this.lvStudents.UseCompatibleStateImageBehavior = false;
            this.lvStudents.View = System.Windows.Forms.View.Details;
            this.lvStudents.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.lvStudents_ItemCheck);
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Last name";
            this.columnHeader1.Width = 94;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "First name";
            this.columnHeader2.Width = 107;
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Student ID";
            this.columnHeader3.Width = 74;
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "book.png");
            // 
            // btn_Details
            // 
            this.btn_Details.Location = new System.Drawing.Point(5, 165);
            this.btn_Details.Name = "btn_Details";
            this.btn_Details.Size = new System.Drawing.Size(72, 35);
            this.btn_Details.TabIndex = 11;
            this.btn_Details.Text = "Detai&ls";
            this.btn_Details.UseVisualStyleBackColor = true;
            this.btn_Details.Click += new System.EventHandler(this.btn_Details_Click);
            // 
            // btn_smallIcon
            // 
            this.btn_smallIcon.Location = new System.Drawing.Point(83, 165);
            this.btn_smallIcon.Name = "btn_smallIcon";
            this.btn_smallIcon.Size = new System.Drawing.Size(92, 35);
            this.btn_smallIcon.TabIndex = 12;
            this.btn_smallIcon.Text = "Small &Icon";
            this.btn_smallIcon.UseVisualStyleBackColor = true;
            this.btn_smallIcon.Click += new System.EventHandler(this.btn_smallIcon_Click);
            // 
            // btn_LargeIcon
            // 
            this.btn_LargeIcon.Location = new System.Drawing.Point(181, 165);
            this.btn_LargeIcon.Name = "btn_LargeIcon";
            this.btn_LargeIcon.Size = new System.Drawing.Size(90, 35);
            this.btn_LargeIcon.TabIndex = 13;
            this.btn_LargeIcon.Text = "&Large Icon";
            this.btn_LargeIcon.UseVisualStyleBackColor = true;
            this.btn_LargeIcon.Click += new System.EventHandler(this.btn_LargeIcon_Click);
            // 
            // btn_addStudent
            // 
            this.btn_addStudent.Location = new System.Drawing.Point(413, 46);
            this.btn_addStudent.Name = "btn_addStudent";
            this.btn_addStudent.Size = new System.Drawing.Size(145, 36);
            this.btn_addStudent.TabIndex = 14;
            this.btn_addStudent.Text = "Add &Student";
            this.btn_addStudent.UseVisualStyleBackColor = true;
            this.btn_addStudent.Click += new System.EventHandler(this.btn_addStudent_Click);
            // 
            // btn_deleteStudent
            // 
            this.btn_deleteStudent.Location = new System.Drawing.Point(413, 109);
            this.btn_deleteStudent.Name = "btn_deleteStudent";
            this.btn_deleteStudent.Size = new System.Drawing.Size(145, 38);
            this.btn_deleteStudent.TabIndex = 15;
            this.btn_deleteStudent.Text = "&Delete Student";
            this.btn_deleteStudent.UseVisualStyleBackColor = true;
            this.btn_deleteStudent.Click += new System.EventHandler(this.btn_deleteStudent_Click);
            // 
            // statusStrip1
            // 
            this.statusStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripStatusLabel1,
            this.tpProgress});
            this.statusStrip1.Location = new System.Drawing.Point(0, 428);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(880, 25);
            this.statusStrip1.TabIndex = 16;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // toolStripStatusLabel1
            // 
            this.toolStripStatusLabel1.Name = "toolStripStatusLabel1";
            this.toolStripStatusLabel1.Size = new System.Drawing.Size(63, 20);
            this.toolStripStatusLabel1.Text = "Loading";
            // 
            // tpProgress
            // 
            this.tpProgress.BackColor = System.Drawing.Color.Coral;
            this.tpProgress.Name = "tpProgress";
            this.tpProgress.Size = new System.Drawing.Size(100, 19);
            // 
            // btn_addToDashboard
            // 
            this.btn_addToDashboard.Location = new System.Drawing.Point(126, 405);
            this.btn_addToDashboard.Name = "btn_addToDashboard";
            this.btn_addToDashboard.Size = new System.Drawing.Size(145, 23);
            this.btn_addToDashboard.TabIndex = 17;
            this.btn_addToDashboard.Text = "Add To A &Group";
            this.btn_addToDashboard.UseVisualStyleBackColor = true;
            this.btn_addToDashboard.Click += new System.EventHandler(this.btn_addToDashboard_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(500, 186);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(78, 17);
            this.label4.TabIndex = 20;
            this.label4.Text = "Dashboard";
            // 
            // btnClose
            // 
            this.btnClose.BackColor = System.Drawing.Color.CornflowerBlue;
            this.btnClose.ForeColor = System.Drawing.Color.Snow;
            this.btnClose.Location = new System.Drawing.Point(637, 402);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(89, 26);
            this.btnClose.TabIndex = 21;
            this.btnClose.Text = "&Close";
            this.btnClose.UseVisualStyleBackColor = false;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // dgvDashboards
            // 
            this.dgvDashboards.BackgroundColor = System.Drawing.Color.Aquamarine;
            this.dgvDashboards.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvDashboards.Location = new System.Drawing.Point(545, 206);
            this.dgvDashboards.Name = "dgvDashboards";
            this.dgvDashboards.RowTemplate.Height = 24;
            this.dgvDashboards.Size = new System.Drawing.Size(323, 190);
            this.dgvDashboards.TabIndex = 22;
            // 
            // btnSeeGrades
            // 
            this.btnSeeGrades.Cursor = System.Windows.Forms.Cursors.Hand;
            this.btnSeeGrades.Location = new System.Drawing.Point(708, 109);
            this.btnSeeGrades.Name = "btnSeeGrades";
            this.btnSeeGrades.Size = new System.Drawing.Size(145, 38);
            this.btnSeeGrades.TabIndex = 23;
            this.btnSeeGrades.Text = "See Dash&board";
            this.btnSeeGrades.UseVisualStyleBackColor = true;
            this.btnSeeGrades.Click += new System.EventHandler(this.btnSeeGrades_Click);
            // 
            // tvGrades
            // 
            this.tvGrades.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.tvGrades.Location = new System.Drawing.Point(503, 206);
            this.tvGrades.Name = "tvGrades";
            this.tvGrades.Size = new System.Drawing.Size(365, 190);
            this.tvGrades.TabIndex = 24;
            // 
            // btnUpdateStud
            // 
            this.btnUpdateStud.Location = new System.Drawing.Point(576, 80);
            this.btnUpdateStud.Name = "btnUpdateStud";
            this.btnUpdateStud.Size = new System.Drawing.Size(126, 30);
            this.btnUpdateStud.TabIndex = 25;
            this.btnUpdateStud.Text = "&Update Student";
            this.btnUpdateStud.UseVisualStyleBackColor = true;
            this.btnUpdateStud.Click += new System.EventHandler(this.btnUpdateStud_Click);
            // 
            // MyForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoValidate = System.Windows.Forms.AutoValidate.EnableAllowFocusChange;
            this.ClientSize = new System.Drawing.Size(880, 453);
            this.Controls.Add(this.btnUpdateStud);
            this.Controls.Add(this.tvGrades);
            this.Controls.Add(this.btnSeeGrades);
            this.Controls.Add(this.dgvDashboards);
            this.Controls.Add(this.btnClose);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.btn_addToDashboard);
            this.Controls.Add(this.lvStudents);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.btn_deleteStudent);
            this.Controls.Add(this.btn_addStudent);
            this.Controls.Add(this.btn_LargeIcon);
            this.Controls.Add(this.btn_smallIcon);
            this.Controls.Add(this.btn_Details);
            this.Controls.Add(this.btn_addGrades);
            this.Controls.Add(this.tbFirstName);
            this.Controls.Add(this.tbStudentID);
            this.Controls.Add(this.tbLastName);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.menuStrip1);
            this.ForeColor = System.Drawing.Color.DarkMagenta;
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "MyForm";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvDashboards)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox tbLastName;
        private System.Windows.Forms.TextBox tbStudentID;
        private System.Windows.Forms.TextBox tbFirstName;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem jSONSerializationToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem btnSerializeBinary;
        private System.Windows.Forms.ToolStripMenuItem btnDeserializeBinary;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.Button btn_addGrades;
        private System.Windows.Forms.ToolStripMenuItem btnExportTextFile;
        private System.Windows.Forms.Button btn_LargeIcon;
        private System.Windows.Forms.Button btn_smallIcon;
        private System.Windows.Forms.Button btn_Details;
        private System.Windows.Forms.ListView lvStudents;
        private System.Windows.Forms.Button btn_addStudent;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.Button btn_deleteStudent;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.Button btn_addToDashboard;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel1;
        private System.Windows.Forms.ToolStripProgressBar tpProgress;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.DataGridView dgvDashboards;
        private System.Windows.Forms.Button btnSeeGrades;
        private System.Windows.Forms.TreeView tvGrades;
        private System.Windows.Forms.Button btnUpdateStud;
        private System.Windows.Forms.ToolStripMenuItem removeAStudentFromDashboardToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem tsDropSubjects;
    }
}

