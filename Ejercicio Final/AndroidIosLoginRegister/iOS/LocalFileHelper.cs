using System;
using System.IO;
using System.Runtime.CompilerServices;
using InheritXDemo.iOS;

[assembly: Xamarin.Forms.Dependency(typeof(LocalFileHelper))]

namespace InheritXDemo.iOS
{
	
	public class LocalFileHelper : iLocalFileHelper
	{
		public string GetLocalFilePath(string fileName)
		{

			string docFolder = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
			string libFolder = Path.Combine(docFolder,"..","Library", "Database");

			if (!Directory.Exists(libFolder)) {
				Directory.CreateDirectory(libFolder);
			}

			return Path.Combine(libFolder, fileName);

		}
	}
}
