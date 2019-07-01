using System;
using System.Net;
using System.Net.Sockets;

namespace Engine {

	public class myNetworkPool {
		private string encryptionKey = "MZygpewJsCpRrfOr";
		private myAESPool AES;

		public enum CONNECTION_STATUS {
			READY,
			CONNECTED,
			DISCONECTED
		}

		private const int CPORT = 1800;
		private const int CBUFFER_SIZE = 25;
		private const int CBUFFER_RETURN_SIZE = 1;

		private byte[] Buffer = new byte[CBUFFER_SIZE];
		private byte[] buffReturn = new byte[CBUFFER_RETURN_SIZE] { 0 };
		private String Result = "";
		private Socket sck, acc;

		private CONNECTION_STATUS p_statusConecction = CONNECTION_STATUS.DISCONECTED;

		private static myNetworkPool s_instance;

		public static myNetworkPool GetInstance() {
			if (s_instance == null) {
				s_instance = new myNetworkPool();
				s_instance.AES = new myAESPool(s_instance.encryptionKey);
            }
			return s_instance;
		}

		public static void FreeInstance() {
			s_instance = null;
		}

		public void launchEngine() {
			p_statusConecction = CONNECTION_STATUS.READY;

			sck = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
			sck.Bind(new IPEndPoint(0, CPORT));
			getIP();
			Console.WriteLine("Awaiting Connection");
			sck.Listen(100);

			acc = sck.Accept();
			Console.WriteLine(" >> Accept connection from client");
			p_statusConecction = CONNECTION_STATUS.CONNECTED;
			acc.SendTimeout = 500;


			while (acc.Connected) {
				System.Threading.Thread.Sleep(10);
				try {
					acc.Receive(Buffer, 0, Buffer.Length, 0);
					Result = AES.DecryptFromBase64(Buffer);
					Console.WriteLine(Result);
					acc.Send(buffReturn);
				} catch (Exception e) {
					acc.Close();
					sck.Close();
					Console.WriteLine(" --- connection is closed --- " + System.Text.Encoding.Default.GetString(Buffer)); 
				}

			}

			p_statusConecction = CONNECTION_STATUS.DISCONECTED;
		}

		public string dataRaw() {
			//return Buffer != null ? System.Text.Encoding.UTF8.GetString(Buffer) : "null";
			if (Buffer != null) {
				//return System.Text.Encoding.UTF8.GetString(Buffer);
				return Result;
            } else {
				return "null";
			}
		}

		public void getIP() {
			string hostName = System.Net.Dns.GetHostName();
			IPHostEntry ipEntry = System.Net.Dns.GetHostEntry(hostName);
			IPAddress[] addr = ipEntry.AddressList;

			for (int i = 0; i < addr.Length; i++) {
				Console.WriteLine("Your local IP address is: " + addr[i].ToString());
			}
		}

		public CONNECTION_STATUS getConecctionStatus() {
			return p_statusConecction;
		}

	}
}
