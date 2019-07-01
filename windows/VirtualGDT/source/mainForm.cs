using System;
using Engine;
using System.Windows.Forms;
using System.Drawing;

namespace VirtualGDT.source {
	public partial class mainForm : Form {
		areaForm frm = new areaForm();
		myInterfacePool myfp = new myInterfacePool();

		public mainForm() {
			InitializeComponent();
		}

		//to know if client is conected with server
		private void tmrCheck_Tick(object sender, EventArgs e) {

			myfp.deviceResolution(true);

			txtARX.Text = "" + myfp.deviceResolution(true);
			txtARY.Text = "" + myfp.deviceResolution(false);
		

			if (myNetworkPool.GetInstance().getConecctionStatus() == myNetworkPool.CONNECTION_STATUS.CONNECTED) {
				lblStatus.Text = "Connected";
				lblStatus.ForeColor = System.Drawing.Color.Green;
				frm.setSize(myfp.deviceResolution(true), myfp.deviceResolution(false));
				myfp.setOffsetResolution(txtORX.Text, txtORY.Text);
			} else if (myNetworkPool.GetInstance().getConecctionStatus() == myNetworkPool.CONNECTION_STATUS.DISCONECTED) {
				lblStatus.Text = "Disconnected";
				lblStatus.ForeColor = System.Drawing.Color.DarkRed;
				myThreadPool test = new myThreadPool();
				test.LaunchThreads();
			} else {
				lblStatus.Text = "Ready";
				lblStatus.ForeColor = System.Drawing.Color.DarkOrange;
			}

		}

		//to update data in the form screen
		private void mainForm_Load(object sender, EventArgs e) {
			frm.Show();
			txtMRX.Text = SystemInformation.VirtualScreen.Width.ToString();
			txtMRY.Text = SystemInformation.VirtualScreen.Height.ToString();
			txtARX.Text = "0";
			txtARY.Text = "0";
			frm.setBorderColor(lblColor.BackColor);
		}

		private void tmrPointer_Tick(object sender, EventArgs e) {
			txtORX.Text = frm.offsetResolution(true).ToString();
			txtORY.Text = frm.offsetResolution(false).ToString();
		}


		private void lblColor_Click(object sender, EventArgs e) {
			if(colorDialog1.ShowDialog() == DialogResult.OK) {
				lblColor.BackColor = colorDialog1.Color;
				frm.setBorderColor(lblColor.BackColor);
			}
		}
	}
}