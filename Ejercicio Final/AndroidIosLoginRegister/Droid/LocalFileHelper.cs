using System;
using System.IO;
using InheritXDemo.Droid;
using Xamarin.Forms;

[assembly: Dependency(typeof(LocalFileHelper))]
namespace InheritXDemo.Droid
{
	public class LocalFileHelper : iLocalFileHelper
	{


		public string GetLocalFilePath(string filePath)
		{
			string docFolder = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
			string libFolder = Path.Combine(docFolder, "..", "Library", "Database");

			if (!Directory.Exists(libFolder))
			{
				Directory.CreateDirectory(libFolder);
			}

			return Path.Combine(libFolder, filePath);
		}
	}
}
