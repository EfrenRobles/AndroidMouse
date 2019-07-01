using System;
using System.Windows.Forms;



/*
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;
*/
using Engine;



namespace tabletmouse.source {
	public partial class mainForm : Form {
		//myAESPool AES = new myAESPool();

		/*
				[DllImport("core.dll", CallingConvention = CallingConvention.Cdecl)]
				public static extern bool getConeccionStatus();

				[DllImport("core.dll", CallingConvention = CallingConvention.Cdecl)]
				public static extern int getMonitorResolution(short[] buffer);

				[DllImport("core.dll", CallingConvention = CallingConvention.Cdecl)]
				public static extern int getAndroidResolution(short[] buffer);

				[DllImport("core.dll", CallingConvention = CallingConvention.Cdecl)]
				public static extern int getOffsetResolution(short[] buffer);
		*/
		public mainForm() {
			InitializeComponent();
		}

		private void tmrCheck_Tick(object sender, EventArgs e) {
			//clsEngine.setName("sdf");
			//lblStatus.Text = clsEngine.getIP();

			/*
			String X = txtORX.Text;
			String Y = txtORY.Text;

			short[] buffer = { short.Parse(X), short.Parse(Y) };
			getOffsetResolution(buffer);
			*/

			if (myNetworkPool.GetInstance().getConecctionStatus() == myNetworkPool.CONNECTION_STATUS.CONNECTED) {
				lblStatus.Text = "Connected";
				lblStatus.ForeColor = System.Drawing.Color.Green;
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

		private void mainForm_Load(object sender, EventArgs e) {

			short[] buffer = { 0, 0 };

			//getMonitorResolution(buffer);
			txtMRX.Text = buffer[0].ToString();
			txtMRY.Text = buffer[1].ToString();

			//getAndroidResolution(buffer);
			txtARX.Text = buffer[0].ToString();
			txtARY.Text = buffer[1].ToString();


		}

		private void tmrPointer_Tick(object sender, EventArgs e) {
			lblRawData.Text = myNetworkPool.GetInstance().dataRaw();
		}
	}
}