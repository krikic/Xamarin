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
		fe = countCharOccurrences(frase);


	}
	else
	{
		fe = 0;
	}
	
	Result.Text =  fe.ToString();
		}


public int countCharOccurrences(string text)
{


	int count = 0;

	foreach (char c in text)
	{

		if (c.Equals('a'))
		{
			count++;
		}
	}


	return count;


}

	
	

		public override void DidReceiveMemoryWarning() {
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}



	}
}

