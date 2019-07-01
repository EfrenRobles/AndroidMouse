using System;
using System.Collections.Generic;
using System.Linq;
//using System.Threading.Tasks;
using System.Windows.Forms;
using VirtualGDT.source;
//using System.Threading;

using System.Runtime.InteropServices;

namespace VirtualGDT {
	/*
	public class MyThreadPool {

		[DllImport("core.dll", CallingConvention = CallingConvention.Cdecl)]
		public static extern bool startApp();

		private IList<Thread> _threads;
		private readonly int MAX_THREADS = 1;

		public MyThreadPool() {
			_threads = new List<Thread>();
		}

		public void LaunchThreads() {
			for (int i = 0; i < MAX_THREADS; i++) {
				Thread thread = new Thread(ThreadEntry);
				thread.IsBackground = true;
				thread.Name = string.Format("MyThread{0}", i);

				_threads.Add(thread);
				thread.Start();
			}
		}

		public void KillThread(int index) {
			string id = string.Format("MyThread{0}", index);
			foreach (Thread thread in _threads) {
				if (thread.Name == id)
					thread.Abort();
			}
		}

		void ThreadEntry() {
			startApp();
		}
	}
		*/

	static class Program {
		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() {
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);

			//MyThreadPool test = new MyThreadPool();
			//test.LaunchThreads();

			Application.Run(new mainForm());

			//test.KillThread(0);
		}
	}
}
