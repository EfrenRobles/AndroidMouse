namespace VirtualGDT.source
{
	partial class mainForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(mainForm));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.txtMRX = new System.Windows.Forms.TextBox();
            this.txtMRY = new System.Windows.Forms.TextBox();
            this.txtARY = new System.Windows.Forms.TextBox();
            this.txtARX = new System.Windows.Forms.TextBox();
            this.txtORY = new System.Windows.Forms.TextBox();
            this.txtORX = new System.Windows.Forms.TextBox();
            this.lblStatus = new System.Windows.Forms.Label();
            this.tmrCheck = new System.Windows.Forms.Timer(this.components);
            this.tmrPointer = new System.Windows.Forms.Timer(this.components);
            this.label5 = new System.Windows.Forms.Label();
            this.lblColor = new System.Windows.Forms.Label();
            this.colorDialog1 = new System.Windows.Forms.ColorDialog();
            this.picLogo = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.picLogo)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 49);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(95, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Monitor Resolution";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 83);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(96, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "Android Resolution";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(12, 119);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(88, 13);
            this.label3.TabIndex = 2;
            this.label3.Text = "Offset Resolution";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(11, 19);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(37, 13);
            this.label4.TabIndex = 3;
            this.label4.Text = "Status";
            // 
            // txtMRX
            // 
            this.txtMRX.Enabled = false;
            this.txtMRX.Location = new System.Drawing.Point(152, 46);
            this.txtMRX.MaxLength = 5;
            this.txtMRX.Name = "txtMRX";
            this.txtMRX.Size = new System.Drawing.Size(59, 20);
            this.txtMRX.TabIndex = 0;
            this.txtMRX.Text = "0";
            // 
            // txtMRY
            // 
            this.txtMRY.Enabled = false;
            this.txtMRY.Location = new System.Drawing.Point(217, 46);
            this.txtMRY.MaxLength = 5;
            this.txtMRY.Name = "txtMRY";
            this.txtMRY.Size = new System.Drawing.Size(59, 20);
            this.txtMRY.TabIndex = 1;
            this.txtMRY.Text = "0";
            // 
            // txtARY
            // 
            this.txtARY.Enabled = false;
            this.txtARY.Location = new System.Drawing.Point(217, 83);
            this.txtARY.MaxLength = 5;
            this.txtARY.Name = "txtARY";
            this.txtARY.Size = new System.Drawing.Size(59, 20);
            this.txtARY.TabIndex = 3;
            this.txtARY.Text = "0";
            // 
            // txtARX
            // 
            this.txtARX.Enabled = false;
            this.txtARX.Location = new System.Drawing.Point(152, 83);
            this.txtARX.MaxLength = 5;
            this.txtARX.Name = "txtARX";
            this.txtARX.Size = new System.Drawing.Size(59, 20);
            this.txtARX.TabIndex = 2;
            this.txtARX.Text = "0";
            // 
            // txtORY
            // 
            this.txtORY.Enabled = false;
            this.txtORY.Location = new System.Drawing.Point(217, 119);
            this.txtORY.MaxLength = 5;
            this.txtORY.Name = "txtORY";
            this.txtORY.Size = new System.Drawing.Size(59, 20);
            this.txtORY.TabIndex = 5;
            this.txtORY.Text = "0";
            // 
            // txtORX
            // 
            this.txtORX.Enabled = false;
            this.txtORX.Location = new System.Drawing.Point(152, 119);
            this.txtORX.MaxLength = 5;
            this.txtORX.Name = "txtORX";
            this.txtORX.Size = new System.Drawing.Size(59, 20);
            this.txtORX.TabIndex = 4;
            this.txtORX.Text = "0";
            // 
            // lblStatus
            // 
            this.lblStatus.AutoSize = true;
            this.lblStatus.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.lblStatus.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblStatus.ForeColor = System.Drawing.Color.DarkRed;
            this.lblStatus.Location = new System.Drawing.Point(175, 19);
            this.lblStatus.Name = "lblStatus";
            this.lblStatus.Size = new System.Drawing.Size(87, 15);
            this.lblStatus.TabIndex = 8;
            this.lblStatus.Text = "Disconnected";
            this.lblStatus.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tmrCheck
            // 
            this.tmrCheck.Enabled = true;
            this.tmrCheck.Interval = 1000;
            this.tmrCheck.Tick += new System.EventHandler(this.tmrCheck_Tick);
            // 
            // tmrPointer
            // 
            this.tmrPointer.Enabled = true;
            this.tmrPointer.Interval = 1;
            this.tmrPointer.Tick += new System.EventHandler(this.tmrPointer_Tick);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(11, 152);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(98, 13);
            this.label5.TabIndex = 9;
            this.label5.Text = "Color para el marco";
            // 
            // lblColor
            // 
            this.lblColor.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.lblColor.Location = new System.Drawing.Point(149, 152);
            this.lblColor.Name = "lblColor";
            this.lblColor.Size = new System.Drawing.Size(127, 20);
            this.lblColor.TabIndex = 11;
            this.lblColor.Click += new System.EventHandler(this.lblColor_Click);
            // 
            // picLogo
            // 
            this.picLogo.Image = global::VirtualGDT.Properties.Resources.Icon;
            this.picLogo.Location = new System.Drawing.Point(316, 19);
            this.picLogo.Name = "picLogo";
            this.picLogo.Size = new System.Drawing.Size(141, 153);
            this.picLogo.TabIndex = 12;
            this.picLogo.TabStop = false;
            // 
            // mainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(469, 190);
            this.Controls.Add(this.picLogo);
            this.Controls.Add(this.lblColor);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.lblStatus);
            this.Controls.Add(this.txtORY);
            this.Controls.Add(this.txtORX);
            this.Controls.Add(this.txtARY);
            this.Controls.Add(this.txtARX);
            this.Controls.Add(this.txtMRY);
            this.Controls.Add(this.txtMRX);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Cursor = System.Windows.Forms.Cursors.Arrow;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "mainForm";
            this.Text = "Virtual Graphics Drawing Tablet";
            this.Load += new System.EventHandler(this.mainForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.picLogo)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.TextBox txtMRX;
		private System.Windows.Forms.TextBox txtMRY;
		private System.Windows.Forms.TextBox txtARY;
		private System.Windows.Forms.TextBox txtARX;
		private System.Windows.Forms.TextBox txtORY;
		private System.Windows.Forms.TextBox txtORX;
		private System.Windows.Forms.Label lblStatus;
		private System.Windows.Forms.Timer tmrCheck;
		private System.Windows.Forms.Timer tmrPointer;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Label lblColor;
		private System.Windows.Forms.ColorDialog colorDialog1;
        private System.Windows.Forms.PictureBox picLogo;
	}
}