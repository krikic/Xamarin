using Android.App;
using Android.Widget;
using Android.OS;

namespace Factorial
{
	[Activity(Label = "Factorial", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity
	{
		int count = 1;
		private TextView txtanswer;
		private Button button;
		private EditText text;

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);

			// Get our button from the layout resource,
			// and attach an event to it

			txtanswer = FindViewById<TextView>(Resource.Id.txtanswer);
       
			 button = FindViewById<Button>(Resource.Id.myButton);
			 text = FindViewById<EditText>(Resource.Id.text);
			string number = text.ToString();

			button.Click += delegate { 
				txtanswer.Text= "Factorial of " + number + " is : " + calcFactorial().ToString();
			};
		}
		private int calcFactorial()
		{

			int factorial = 1;

			factorial = int.Parse(text.Text.ToString());
			for (int i = factorial - 1; i > 0; i--)
				{
					factorial = i * factorial;
				}

			return factorial;
		}
	}

}

