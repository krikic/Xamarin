using System;

using UIKit;

namespace LetsCalc
{
	public partial class ViewController : UIViewController
	{
		protected ViewController(IntPtr handle) : base(handle)
		{
			// Note: this .ctor should not contain any initialization logic.
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}


		partial void AddButton_TouchUpInside(UIButton sender)
		{
			double number1 = double.Parse(text1.Text);
			double number2 = double.Parse(text2.Text);

			double answer = number1 + number2;

			answerLabel.Text = "Result: " + answer.ToString();
		}


		partial void SubtractButton_TouchUpInside(UIButton sender)
		{
			double number1 = double.Parse(text1.Text);
			double number2 = double.Parse(text2.Text);

			double answer = number1 - number2;

			answerLabel.Text = "Result: " + answer.ToString();
		}

		partial void DivideButton_TouchUpInside(UIButton sender)
		{
			double number1 = double.Parse(text1.Text);
			double number2 = double.Parse(text2.Text);

			double answer = number1 / number2;

			answerLabel.Text = "Result: " + answer.ToString();
		}

		partial void MultiplyButton_TouchUpInside(UIButton sender)
		{
			double number1 = double.Parse(text1.Text);
			double number2 = double.Parse(text2.Text);

			double answer = number1 * number2;

			answerLabel.Text = "Result: " + answer.ToString();
		}
	}
}
