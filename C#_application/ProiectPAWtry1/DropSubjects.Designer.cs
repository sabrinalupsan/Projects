namespace ProjectPAW
{
    partial class DropSubjects
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
            this.tbID = new System.Windows.Forms.TextBox();
            this.btnSeeSubjects = new System.Windows.Forms.Button();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.btnDrop = new System.Windows.Forms.Button();
            this.checkSubjects = new System.Windows.Forms.CheckedListBox();
            this.btnOK = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(22, 28);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(74, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "Student ID";
            // 
            // tbID
            // 
            this.tbID.Location = new System.Drawing.Point(102, 29);
            this.tbID.Name = "tbID";
            this.tbID.Size = new System.Drawing.Size(100, 22);
            this.tbID.TabIndex = 1;
            this.tbID.Validating += new System.ComponentModel.CancelEventHandler(this.tbID_Validating);
            this.tbID.Validated += new System.EventHandler(this.tbID_Validated);
            // 
            // btnSeeSubjects
            // 
            this.btnSeeSubjects.Location = new System.Drawing.Point(299, 28);
            this.btnSeeSubjects.Name = "btnSeeSubjects";
            this.btnSeeSubjects.Size = new System.Drawing.Size(130, 36);
            this.btnSeeSubjects.TabIndex = 2;
            this.btnSeeSubjects.Text = "See subjects";
            this.btnSeeSubjects.UseVisualStyleBackColor = true;
            this.btnSeeSubjects.Click += new System.EventHandler(this.btnSeeSubjects_Click);
            // 
            // errorProvider
            // 
            this.errorProvider.ContainerControl = this;
            // 
            // btnDrop
            // 
            this.btnDrop.Location = new System.Drawing.Point(299, 120);
            this.btnDrop.Name = "btnDrop";
            this.btnDrop.Size = new System.Drawing.Size(130, 29);
            this.btnDrop.TabIndex = 4;
            this.btnDrop.Text = "Drop selected subjects";
            this.btnDrop.UseVisualStyleBackColor = true;
            this.btnDrop.Click += new System.EventHandler(this.btnDrop_Click);
            // 
            // checkSubjects
            // 
            this.checkSubjects.FormattingEnabled = true;
            this.checkSubjects.Location = new System.Drawing.Point(25, 79);
            this.checkSubjects.Name = "checkSubjects";
            this.checkSubjects.Size = new System.Drawing.Size(177, 157);
            this.checkSubjects.TabIndex = 5;
            // 
            // btnOK
            // 
            this.btnOK.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.btnOK.Location = new System.Drawing.Point(299, 190);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(130, 29);
            this.btnOK.TabIndex = 6;
            this.btnOK.Text = "Done";
            this.btnOK.UseVisualStyleBackColor = true;
            // 
            // DropSubjects
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(511, 261);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.checkSubjects);
            this.Controls.Add(this.btnDrop);
            this.Controls.Add(this.btnSeeSubjects);
            this.Controls.Add(this.tbID);
            this.Controls.Add(this.label1);
            this.Name = "DropSubjects";
            this.Text = "DropSubjects";
            this.Load += new System.EventHandler(this.DropSubjects_Load);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox tbID;
        private System.Windows.Forms.Button btnSeeSubjects;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.Button btnDrop;
        private System.Windows.Forms.CheckedListBox checkSubjects;
        private System.Windows.Forms.Button btnOK;
    }
}