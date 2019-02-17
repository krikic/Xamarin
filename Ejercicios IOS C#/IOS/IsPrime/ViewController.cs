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

		partial void Calcular(UIButton sender)
		{
	
			int fe = Convert.ToInt32(sentence.Text.ToString());

		
				
			string sentences = IsPrime(fe);


	
	
	Result.Text =  sentences.ToString();
		}




public string IsPrime(int input)
{
	
			string result = "";

	if (input == 1)
	{
		result = "1 is neither a prime number nor composite number.";
	}
	if (input == 2)
	{
		result = "Yes";
	}
	else
	{
		for (int i = 2; i < input; i++)
		{
			if (input % i == 0)
			{
						result = "NO ";
				break;
			}
			else
			{
				result = "The integer is a Prime Number.";
			}
		}
	}
	return result;
}

	

		public override void DidReceiveMemoryWarning() {
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}



	}
}

