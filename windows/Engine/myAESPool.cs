using System;
using System.Text;
using System.Security.Cryptography;
using System.Text.RegularExpressions;

namespace Engine {
	public class myAESPool {

		private ICryptoTransform rijndaelDecryptor;
		// Replace me with a 16-byte key, share between Java and C#
		private static byte[] rawSecretKey = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

		public myAESPool(string passphrase) {
			byte[] passwordKey = encodeDigest(passphrase);
			RijndaelManaged rijndael = new RijndaelManaged();
			rijndaelDecryptor = rijndael.CreateDecryptor(passwordKey, rawSecretKey);
		}

		public string Decrypt(byte[] encryptedData) {
			byte[] newClearData = rijndaelDecryptor.TransformFinalBlock(encryptedData, 0, encryptedData.Length);
			return Encoding.ASCII.GetString(newClearData);
		}

		public string DecryptFromBase64(string encryptedBase64) {
			return Decrypt(Convert.FromBase64String(encryptedBase64));
		}

		public string DecryptFromBase64(byte[] encryptedBase64) {
			string data = System.Text.Encoding.Default.GetString(encryptedBase64);
			string result = Regex.Replace(data, @"\0|\n|\t", "");
			return DecryptFromBase64(result);
		}


		private byte[] encodeDigest(string text) {
			MD5CryptoServiceProvider x = new System.Security.Cryptography.MD5CryptoServiceProvider();
			byte[] data = Encoding.ASCII.GetBytes(text);
			return x.ComputeHash(data);
		}


	}
}
