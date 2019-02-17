using System;

using UIKit;

namespace TipCalculator {
	public partial class ViewController : UIViewController {




		protected ViewController(IntPtr handle) : base(handle) {
			// Note: this .ctor should not contain any initialization logic.
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
		
		}

		partial void Calcular(UIButton sender)
		{
			int fe = 0;
			 fe = Convert.ToInt32(sentence.Text.ToString());

		
				
			string sentences = PrimeList(fe);


	
	
	Result.Text =  sentences.ToString();
		}



public string PrimeList(int num)
{
	string isPrime = "true";
	string resultado = "";
	for (int i = 0; i <= num; i++)
	{
		for (int j = 2; j <= num; j++)
		{
			if (i != j && i % j == 0)
			{
				isPrime = "false";
				break;
			}
		}
		if (isPrime == "true")
		{
			resultado = resultado + ";" + i.ToString();
		}
		isPrime = "true";
	}
	return resultado;
}	

		public override void DidReceiveMemoryWarning() {
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}



	}
}

