namespace ProjectPAW
{
    partial class GradesForm
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
            this.label2 = new System.Windows.Forms.Label();
            this.tbSubject = new System.Windows.Forms.TextBox();
            this.tbGrade = new System.Windows.Forms.TextBox();
            this.btnAddGrade = new System.Windows.Forms.Button();
            this.tvGrades = new System.Windows.Forms.TreeView();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.btnOK = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.averageControl1 = new mYControl.AverageControl();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(11, 25);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(94, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "Subject name";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(21, 66);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(48, 17);
            this.label2.TabIndex = 1;
            this.label2.Text = "Grade";
            // 
            // tbSubject
            // 
            this.tbSubject.Location = new System.Drawing.Point(127, 25);
            this.tbSubject.Name = "tbSubject";
            this.tbSubject.Size = new System.Drawing.Size(100, 22);
            this.tbSubject.TabIndex = 2;
            this.tbSubject.Validating += new System.ComponentModel.CancelEventHandler(this.tbSubject_Validating);
            this.tbSubject.Validated += new System.EventHandler(this.tbSubject_Validated);
            // 
            // tbGrade
            // 
            this.tbGrade.Location = new System.Drawing.Point(127, 63);
            this.tbGrade.Name = "tbGrade";
            this.tbGrade.Size = new System.Drawing.Size(100, 22);
            this.tbGrade.TabIndex = 3;
            this.tbGrade.Validating += new System.ComponentModel.CancelEventHandler(this.tbGrade_Validating);
            this.tbGrade.Validated += new System.EventHandler(this.tbGrade_Validated);
            // 
            // btnAddGrade
            // 
            this.btnAddGrade.Location = new System.Drawing.Point(249, 39);
            this.btnAddGrade.Name = "btnAddGrade";
            this.btnAddGrade.Size = new System.Drawing.Size(75, 23);
            this.btnAddGrade.TabIndex = 6;
            this.btnAddGrade.Text = "&Add Grade";
            this.btnAddGrade.UseVisualStyleBackColor = true;
            this.btnAddGrade.Click += new System.EventHandler(this.btnAddGrade_Click);
            // 
            // tvGrades
            // 
            this.tvGrades.FullRowSelect = true;
            this.tvGrades.HideSelection = false;
            this.tvGrades.Location = new System.Drawing.Point(12, 108);
            this.tvGrades.Name = "tvGrades";
            this.tvGrades.Size = new System.Drawing.Size(321, 170);
            this.tvGrades.TabIndex = 9;
            // 
            // errorProvider
            // 
            this.errorProvider.ContainerControl = this;
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(127, 297);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(75, 23);
            this.btnOK.TabIndex = 10;
            this.btnOK.Text = "&Ok";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(427, 45);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(154, 17);
            this.label3.TabIndex = 12;
            this.label3.Text = "See hypothetical grade";
            // 
            // averageControl1
            // 
            this.averageControl1.BackColor = System.Drawing.Color.Coral;
            this.averageControl1.Location = new System.Drawing.Point(395, 96);
            this.averageControl1.Name = "averageControl1";
            this.averageControl1.Size = new System.Drawing.Size(208, 192);
            this.averageControl1.TabIndex = 11;
            // 
            // GradesForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoValidate = System.Windows.Forms.AutoValidate.EnableAllowFocusChange;
            this.ClientSize = new System.Drawing.Size(628, 345);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.averageControl1);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.tvGrades);
            this.Controls.Add(this.btnAddGrade);
            this.Controls.Add(this.tbGrade);
            this.Controls.Add(this.tbSubject);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "GradesForm";
            this.Text = "GradesForm";
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox tbSubject;
        private System.Windows.Forms.TextBox tbGrade;
        private System.Windows.Forms.Button btnAddGrade;
        private System.Windows.Forms.TreeView tvGrades;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.Label label3;
        private mYControl.AverageControl averageControl1;
    }
}