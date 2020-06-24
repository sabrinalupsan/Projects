namespace ProjectPAW
{
    partial class AddToDashboard
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
            this.label1 = new System.Windows.Forms.Label();
            this.tbClassName = new System.Windows.Forms.TextBox();
            this.btn_AddDashboard = new System.Windows.Forms.Button();
            this.btn_ShowClass = new System.Windows.Forms.Button();
            this.lvClass = new System.Windows.Forms.ListView();
            this.columnHeader1 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader2 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader3 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.control1 = new System.Windows.Forms.Control();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 26);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(87, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "Group name";
            // 
            // tbClassName
            // 
            this.tbClassName.Location = new System.Drawing.Point(99, 26);
            this.tbClassName.Name = "tbClassName";
            this.tbClassName.Size = new System.Drawing.Size(100, 22);
            this.tbClassName.TabIndex = 1;
            this.tbClassName.Validating += new System.ComponentModel.CancelEventHandler(this.tbClassName_Validating);
            this.tbClassName.Validated += new System.EventHandler(this.tbClassName_Validated);
            // 
            // btn_AddDashboard
            // 
            this.btn_AddDashboard.Location = new System.Drawing.Point(197, 231);
            this.btn_AddDashboard.Name = "btn_AddDashboard";
            this.btn_AddDashboard.Size = new System.Drawing.Size(75, 23);
            this.btn_AddDashboard.TabIndex = 2;
            this.btn_AddDashboard.Text = "&OK";
            this.btn_AddDashboard.UseVisualStyleBackColor = true;
            this.btn_AddDashboard.Click += new System.EventHandler(this.btn_AddDashboard_Click);
            // 
            // btn_ShowClass
            // 
            this.btn_ShowClass.Location = new System.Drawing.Point(260, 26);
            this.btn_ShowClass.Name = "btn_ShowClass";
            this.btn_ShowClass.Size = new System.Drawing.Size(139, 23);
            this.btn_ShowClass.TabIndex = 3;
            this.btn_ShowClass.Text = "&Add the students";
            this.btn_ShowClass.UseVisualStyleBackColor = true;
            this.btn_ShowClass.Click += new System.EventHandler(this.btn_ShowClass_Click);
            // 
            // lvClass
            // 
            this.lvClass.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.lvClass.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3});
            this.lvClass.HideSelection = false;
            this.lvClass.Location = new System.Drawing.Point(12, 71);
            this.lvClass.Name = "lvClass";
            this.lvClass.Size = new System.Drawing.Size(442, 129);
            this.lvClass.TabIndex = 4;
            this.lvClass.UseCompatibleStateImageBehavior = false;
            this.lvClass.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Last name";
            this.columnHeader1.Width = 101;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "First name";
            this.columnHeader2.Width = 113;
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Student ID";
            this.columnHeader3.Width = 125;
            // 
            // errorProvider
            // 
            this.errorProvider.ContainerControl = this;
            // 
            // control1
            // 
            this.control1.Location = new System.Drawing.Point(68, 216);
            this.control1.Name = "control1";
            this.control1.Size = new System.Drawing.Size(75, 54);
            this.control1.TabIndex = 5;
            this.control1.Text = "control1";
            // 
            // AddToDashboard
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoValidate = System.Windows.Forms.AutoValidate.EnableAllowFocusChange;
            this.ClientSize = new System.Drawing.Size(471, 282);
            this.Controls.Add(this.control1);
            this.Controls.Add(this.lvClass);
            this.Controls.Add(this.btn_ShowClass);
            this.Controls.Add(this.btn_AddDashboard);
            this.Controls.Add(this.tbClassName);
            this.Controls.Add(this.label1);
            this.Name = "AddToDashboard";
            this.Text = "AddToDashboard";
            this.Load += new System.EventHandler(this.AddToDashboard_Load);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox tbClassName;
        private System.Windows.Forms.Button btn_AddDashboard;
        private System.Windows.Forms.Button btn_ShowClass;
        private System.Windows.Forms.ListView lvClass;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.Control control1;
    }
}