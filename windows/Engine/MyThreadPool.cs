using System.Collections.Generic;
using System.Threading;


namespace Engine {
	public class myThreadPool {

		private IList<Thread> _threads;
		private readonly int MAX_THREADS = 1;

		public myThreadPool() {
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
			myNetworkPool.GetInstance().launchEngine();
		}
	}
}


