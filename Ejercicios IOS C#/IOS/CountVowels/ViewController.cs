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
		fe = CountVowels(frase);


	}
	else
	{
		fe = 0;
	}
	
	Result.Text =  fe.ToString();
		}


public int CountVowels(string text)
{
	int count = 0; // start the count at zero
				   // change the string to lowercase
	text = text.ToLower();

	for (int i = 0; i < text.Length; i++)
	{

		char c = text[i];
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
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

