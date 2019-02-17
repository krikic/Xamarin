using System;
using CoreGraphics;

using Foundation;
using UIKit;

namespace XamarinCalculator
{
	public partial class XamarinCalculatorViewController : UIViewController
	{
		private string Firstinput { get; set;}
		private string Secondinput { get; set;}
		private double number1 =0;
		private double number2 =0;
		private double result =0;
		private bool choice = false;
		private bool div = false;
		private bool plus = false;
		private bool mult = false;
		private bool sub = false;
		static bool UserInterfaceIdiomIsPhone {
			get { return UIDevice.CurrentDevice.UserInterfaceIdiom == UIUserInterfaceIdiom.Phone; }
		}

		public XamarinCalculatorViewController (IntPtr handle) : base (handle)
		{
		}

		public override void DidReceiveMemoryWarning ()
		{
			// Releases the view if it doesn't have a superview.
			base.DidReceiveMemoryWarning ();
			
			// Release any cached data, images, etc that aren't in use.
		}

		#region View lifecycle

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();
			One.TouchDown += NumberButtonClick;
			Two.TouchDown += NumberButtonClick;
			Three.TouchDown += NumberButtonClick;
			Four.TouchDown += NumberButtonClick;
			Five.TouchDown += NumberButtonClick;
			Six.TouchDown += NumberButtonClick;
			Seven.TouchDown += NumberButtonClick;
			Eight.TouchDown += NumberButtonClick;
			Nine.TouchDown += NumberButtonClick;
			Zero.TouchDown += NumberButtonClick;

			Equal.TouchDown += OperatorButtonClick;
			Divided.TouchDown += OperatorButtonClick;
			Plus.TouchDown += OperatorButtonClick;
			Subtracted.TouchDown += OperatorButtonClick;
			Multiplied.TouchDown += OperatorButtonClick;
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
		}

		public override void ViewDidAppear (bool animated)
		{
			base.ViewDidAppear (animated);
		}

		public override void ViewWillDisappear (bool animated)
		{
			base.ViewWillDisappear (animated);
		}

		public override void ViewDidDisappear (bool animated)
		{
			base.ViewDidDisappear (animated);
		}

		#endregion

		public void NumberButtonClick(object sender, EventArgs args)
		{
			var numButton = (UIButton)sender;
			if (choice == false) {
				if (numButton == One) {
					Firstinput += "1";
				} else if (numButton == Two) {
					Firstinput += "2";
				} else if (numButton == Three) {
					Firstinput += "3";
				} else if (numButton == Four) {
					Firstinput += "4";
				} else if (numButton == Five) {
					Firstinput += "5";
				} else if (numButton == Six) {
					Firstinput += "6";
				} else if (numButton == Seven) {
					Firstinput += "7";
				} else if (numButton == Eight) {
					Firstinput += "8";
				} else if (numButton == Nine) {
					Firstinput += "9";
				} else if (numButton == Zero) {
					Firstinput += "0";
				}
				Screen.Text = String.Empty;
				Screen.Text = Firstinput;
			} else {
				if (numButton == One) {
					Secondinput += "1";
				} else if (numButton == Two) {
					Secondinput += "2";
				} else if (numButton == Three) {
					Secondinput += "3";
				} else if (numButton == Four) {
					Secondinput += "4";
				} else if (numButton == Five) {
					Secondinput += "5";
				} else if (numButton == Six) {
					Secondinput += "6";
				} else if (numButton == Seven) {
					Secondinput += "7";
				} else if (numButton == Eight) {
					Secondinput += "8";
				} else if (numButton == Nine) {
					Secondinput += "9";
				} else if (numButton == Zero) {
					Secondinput += "0";
				}
				Screen.Text = String.Empty;
				Screen.Text = Secondinput;
			}
		}

		public void OperatorButtonClick(object sender , EventArgs args)
		{
			choice = true;
			if (sender == Divided) {
				NumberButtonClick (sender, args);
				div = true;
			//	result = Divide (Firstinput, Secondinput);
			} else if (sender == Subtracted) {
				NumberButtonClick (sender, args);
				sub = true;
			//	result = Subtract (Firstinput, Secondinput);
			} else if (sender == Plus) {
				NumberButtonClick (sender, args);
				plus = true;
			//	result = Add (Firstinput, Secondinput);
			} else if (sender == Multiplied) {
				NumberButtonClick (sender, args);
				mult = true;
			//	result = Mulitply (Firstinput, Secondinput);
			} else if (sender == Equal) {
				result = CalculateResult (div, mult, plus, sub);
				Screen.Text = result.ToString ();
			}
		}

		public double CalculateResult(bool div, bool mul, bool plus, bool sub)
		{
			if (div == true) {
				return result = Divide (Firstinput, Secondinput);
			} else if (sub == true) {
				return result = Subtract (Firstinput, Secondinput);
			} else if (plus == true) {
				return result = Add (Firstinput, Secondinput);
			} else if (mul == true) {
				return result = Mulitply (Firstinput, Secondinput);
			} else
				return result = 0;
		}
		public double Mulitply(string one, string two)
		{
			number1 = Convert.ToDouble (one);
			number2 = Convert.ToDouble (two);

			return number1 * number2;
		}
		public double Divide(string one, string two)
		{
			number1 = Convert.ToDouble (one);
			number2 = Convert.ToDouble (two);
			return number1 / number2;
		}
		public double Add(string one, string two)
		{
			number1 = Convert.ToDouble (one);
			number2 = Convert.ToDouble (two);
			return number1 + number2;
		}
		public double Subtract(string one, string two)
		{
			number1 = Convert.ToDouble (one);
			number2 = Convert.ToDouble (two);
			return number1 - number2;
		}

		partial void Clear_TouchUpInside (UIButton sender)
		{
			Screen.Text = string.Empty;
			Firstinput = string.Empty;
			Secondinput = string.Empty;
			choice = false;
			div = false;
			plus = false;
			mult = false;
			sub = false;
		}
	}
}

