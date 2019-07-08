using System;
using System.Runtime.InteropServices;

namespace Engine {
	public class myInterfacePool {

		[DllImport("User32.Dll")]
		public static extern long SetCursorPos(int x, int y);

		private static short[] dRes = { 0, 0 };
		private static short[] oRes = { 0, 0 };

		public short deviceResolution(bool data) {
			if (data) {
				return dRes[0];
			} else {
				return dRes[1];
			}
		}

		public void setOffsetResolution(string X, string Y) {
			Int16.TryParse(X, out oRes[0]);
			Int16.TryParse(Y, out oRes[1]);
		}

		public void moveMouse(string data) {

			string[] delimiters = { "," };
			string[] pieces = data.Split(delimiters, StringSplitOptions.None);

			short x = 0;
			short y = 0;
			short z = 0;

			Int16.TryParse(pieces[0], out x);
			Int16.TryParse(pieces[1], out y);
			Int16.TryParse(pieces[2], out z);

			if (z == 2) {
				dRes[0] = x;
				dRes[1] = y;
				//leftClick(1);
			} else if (z == 1) {
				SetCursorPos(oRes[0] + x, oRes[1] + y + 4);
				//leftClick(1);
			} else {
				//leftClick(0);
			}
		}


	}
}

