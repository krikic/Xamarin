using Android.App;
using Android.Widget;
using Android.OS;
using Android.Views;

namespace CountCharString
{
	[Activity(Label = "CountCharString", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity
	{

		private EditText frase;
		private EditText car;
		private TextView result;
		private Button button;

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);

			// Get our button from the layout resource,
			// and attach an event to it
			button = FindViewById<Button>(Resource.Id.myButton);
			frase = FindViewById<EditText>(Resource.Id.frase);
			car = FindViewById<EditText>(Resource.Id.caracter);
			result = FindViewById<TextView>(Resource.Id.resultado);


			string f = frase.ToString();
			string c = car.ToString();
			char myChar = c[0];


			button.Click += delegate
			{


				int numberOfLetterC = MainActivity.countCharOccurrences(f, myChar);


				result.Text = numberOfLetterC.ToString();

			};
		}

		public static int countCharOccurrences(string text, char c)
		{
			int charCount = 0;
			for (int i = 0; i < text.Length; i++)
			{
				char caract = text[i];
				if (caract == c)
				{
					charCount++;
				}
			}
			return charCount;

		}
	}
}

