using Android.App;
using Android.Widget;
using Android.OS;

namespace CountWord
{
	[Activity(Label = "CountWord", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity
	{
		int count = 1;

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);

			// Get our button from the layout resource,
			// and attach an event to it
			Button button = FindViewById<Button>(Resource.Id.myButton);
			TextView resultado = FindViewById<TextView>(Resource.Id.resultado);
			EditText frase = FindViewById<EditText>(Resource.Id.numberEditText);

			button.Click += delegate { 
			 string s = frase.Text.ToString();


				int palabras = countWords(s);


				resultado.Text = palabras.ToString();
			};
		}
		public static int countWords(string s)
		{

			int result = 0;

			//Trim whitespace from beginning and end of string
			s = s.Trim();

			//Necessary because foreach will execute once with empty string returning 1
			if (s == "")
				return 0;

			//Ensure there is only one space between each word in the passed string
			while (s.Contains("  "))
				s = s.Replace("  ", " ");

			//Count the words
			foreach (string y in s.Split(' '))
				result++;

			return result;
		}
		}

	}


