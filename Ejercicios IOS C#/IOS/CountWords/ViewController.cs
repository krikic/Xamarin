using System;

using UIKit;

namespace TipCalculator {
	public partial class ViewController : UIViewController {

		string frase;


		protected ViewController(IntPtr handle) : base(handle) {
			// Note: this .ctor should not contain any initialization logic.
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
		
		}


partial void Calcular_TouchUpInside(UIButton sender)
{
	int fe = 0;
	if (sentence.Text!=null)
	{
		frase = sentence.Text.ToString();
				fe = CountWords(frase);


	}
	else
	{
		fe = 0;
	}
	
	Result.Text =  fe.ToString();
		}

public static int CountWords(string s)
{

	int result = 0;

	//Trim whitespace from beginning and end of string
	s = s.Trim();

	//Necessary because foreach will execute once with empty string returning 1
	if (s == "")
		return 0;

	//Ensure there is only one space between each word in the passed string
	while (s.Contains("  "))
		s = s.Replace("  ", " ");

	//Count the words
	foreach (string y in s.Split(' '))
		result++;

	return result;
}
	

	
	

		public override void DidReceiveMemoryWarning() {
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}



	}
}

