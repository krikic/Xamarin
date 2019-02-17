using Android.App;
using Android.Widget;
using Android.OS;
using System;

namespace NumerosPrimos
{
	[Activity(Label = "NumerosPrimos", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity
	{
		

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);

			Button button = FindViewById<Button>(Resource.Id.button1);
			EditText hasta = FindViewById<EditText>(Resource.Id.editText2);


			TextView result = FindViewById<TextView>(Resource.Id.textView1);

			button.Click += delegate
			{

				int num1 = int.Parse(hasta.Text.ToString());
				string primos = PrimeList(num1);


				result.Text = primos.ToString();






			};
		}
		public static string PrimeList(int num)
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

	}
}



