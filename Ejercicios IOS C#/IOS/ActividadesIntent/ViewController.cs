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
	double fe = 0.0;
double fact = Convert.ToDouble(sentence.Text.ToString());

			if (fact >=0.0)
	{
				
		fe = Factorial(fact);


	}
	else
	{
		fe = 0.0;
	}
	
	Result.Text =  fe.ToString();
		}




public double Factorial(double number)
{

	if (number <= 1.0)
	{

		number = 1.0;
	}
	else
	{
		number = number * Factorial(number - 1.0);
	}


			return number;	}

		public override void DidReceiveMemoryWarning() {
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}



	}
}

