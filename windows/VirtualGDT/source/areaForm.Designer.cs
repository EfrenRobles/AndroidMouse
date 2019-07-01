namespace VirtualGDT.source {
	partial class areaForm {
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing) {
			if (disposing && (components != null)) {
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent() {
			this.lblLef = new System.Windows.Forms.Label();
			this.lblBut = new System.Windows.Forms.Label();
			this.lblRig = new System.Windows.Forms.Label();
			this.lblTop = new System.Windows.Forms.Label();
			this.lblBackground = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// lblLef
			// 
			this.lblLef.BackColor = System.Drawing.Color.DarkOliveGreen;
			this.lblLef.Dock = System.Windows.Forms.DockStyle.Left;
			this.lblLef.Location = new System.Drawing.Point(0, 0);
			this.lblLef.Name = "lblLef";
			this.lblLef.Size = new System.Drawing.Size(10, 39);
			this.lblLef.TabIndex = 1;
			this.lblLef.MouseDown += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseDown);
			this.lblLef.MouseMove += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseMove);
			this.lblLef.MouseUp += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseUp);
			// 
			// lblBut
			// 
			this.lblBut.BackColor = System.Drawing.Color.DarkOliveGreen;
			this.lblBut.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.lblBut.Location = new System.Drawing.Point(10, 29);
			this.lblBut.Name = "lblBut";
			this.lblBut.Size = new System.Drawing.Size(170, 10);
			this.lblBut.TabIndex = 2;
			this.lblBut.MouseDown += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseDown);
			this.lblBut.MouseMove += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseMove);
			this.lblBut.MouseUp += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseUp);
			// 
			// lblRig
			// 
			this.lblRig.BackColor = System.Drawing.Color.DarkOliveGreen;
			this.lblRig.Dock = System.Windows.Forms.DockStyle.Right;
			this.lblRig.Location = new System.Drawing.Point(170, 0);
			this.lblRig.Name = "lblRig";
			this.lblRig.Size = new System.Drawing.Size(10, 29);
			this.lblRig.TabIndex = 3;
			this.lblRig.MouseDown += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseDown);
			this.lblRig.MouseMove += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseMove);
			this.lblRig.MouseUp += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseUp);
			// 
			// lblTop
			// 
			this.lblTop.BackColor = System.Drawing.Color.DarkOliveGreen;
			this.lblTop.Dock = System.Windows.Forms.DockStyle.Top;
			this.lblTop.Font = new System.Drawing.Font("Microsoft Sans Serif", 13F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lblTop.ForeColor = System.Drawing.Color.WhiteSmoke;
			this.lblTop.Location = new System.Drawing.Point(10, 0);
			this.lblTop.Name = "lblTop";
			this.lblTop.Size = new System.Drawing.Size(160, 24);
			this.lblTop.TabIndex = 4;
			this.lblTop.Text = "Area para dibujar";
			this.lblTop.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			this.lblTop.MouseDown += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseDown);
			this.lblTop.MouseMove += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseMove);
			this.lblTop.MouseUp += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseUp);
			// 
			// lblBackground
			// 
			this.lblBackground.BackColor = System.Drawing.Color.White;
			this.lblBackground.Location = new System.Drawing.Point(10, 24);
			this.lblBackground.Name = "lblBackground";
			this.lblBackground.Size = new System.Drawing.Size(81, 71);
			this.lblBackground.TabIndex = 0;
			// 
			// areaForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = System.Drawing.Color.White;
			this.CausesValidation = false;
			this.ClientSize = new System.Drawing.Size(180, 39);
			this.ControlBox = false;
			this.Controls.Add(this.lblTop);
			this.Controls.Add(this.lblRig);
			this.Controls.Add(this.lblBut);
			this.Controls.Add(this.lblLef);
			this.Controls.Add(this.lblBackground);
			this.Cursor = System.Windows.Forms.Cursors.Arrow;
			this.DoubleBuffered = true;
			this.ForeColor = System.Drawing.SystemColors.Highlight;
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "areaForm";
			this.Opacity = 0.9D;
			this.ShowIcon = false;
			this.ShowInTaskbar = false;
			this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
			this.StartPosition = System.Windows.Forms.FormStartPosition.Manual;
			this.Text = "Area para dibujar";
			this.TopMost = true;
			this.TransparencyKey = System.Drawing.Color.White;
			this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseDown);
			this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseMove);
			this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.areaForm_MouseUp);
			this.ResumeLayout(false);

		}

		#endregion
		private System.Windows.Forms.Label lblLef;
		private System.Windows.Forms.Label lblBut;
		private System.Windows.Forms.Label lblRig;
		private System.Windows.Forms.Label lblTop;
		private System.Windows.Forms.Label lblBackground;
	}
}