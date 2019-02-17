using Android.App;
using Android.Widget;
using Android.OS;

namespace IsPrime
{
	[Activity(Label = "IsPrime", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity
	{

	

		protected override void OnCreate(Bundle savedInstanceState)
		{

			int input;

			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);

			TextView result = FindViewById<TextView>(Resource.Id.resultado);
			EditText num = FindViewById<EditText>(Resource.Id.numero);
			Button button = FindViewById<Button>(Resource.Id.myButton);




			button.Click += delegate
			{

				input = int.Parse(num.Text.ToString());

				if (input == 1)
				{
					result.Text = "1 is neither a prime number nor composite number.";
				}
				if (input == 2)
				{
					result.Text = "The integer is a Prime Number.";
				}
				else {
					for (int i = 2; i < input; i++)
					{
						if (input % i == 0)
						{
							result.Text = "The integer is not a Prime Number.\nIt is a composite number and is divisible by : " + i.ToString();
							break;
						}
						else {
							result.Text = "The integer is a Prime Number.";
						}
					}
				}

			};

		}
	}
}

