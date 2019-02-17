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

		partial void Mayusculas(UIButton sender)
		{
			string fe = "";
	if (sentence.Text!=null)
	{
				fe = May(sentence.Text.ToString());
		


	}
	else
	{
		fe = "";
	}
	
	Result.Text =  fe.ToString();
		}

			


		partial void Minusculas(UIButton sender)
		{
string fe = "";
	if (sentence.Text!=null)
	{
				fe = Min(sentence.Text.ToString());
		


	}
	else
	{
		fe = "";
	}
	
	Result.Text =  fe.ToString();
		}
			



	public string May(string one)
{
	string f = one.ToUpper();
	return f;
}
	public string Min(string one)
{
	string f = one.ToLower();
	return f;
		}


		public override void DidReceiveMemoryWarning() {
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}



	}
}

