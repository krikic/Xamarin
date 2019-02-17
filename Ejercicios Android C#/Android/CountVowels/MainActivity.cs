using Android.App;
using Android.Widget;
using Android.OS;

namespace CountVowels
{
	[Activity(Label = "CountVowels", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity
	{
		

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);

			// Get our button from the layout resource,
			// and attach an event to it
			Button button = FindViewById<Button>(Resource.Id.charButton);
			EditText frase = FindViewById<EditText>(Resource.Id.numberEditText);
			TextView result = FindViewById<TextView>(Resource.Id.resultado);

			button.Click += delegate { 
				string s = frase.Text.ToString();


				int vowels = countVowels(s);


				result.Text = vowels.ToString();
			};
		}
		public int countVowels(string text)
		{
			int count = 0; // start the count at zero
						   // change the string to lowercase
			text = text.ToLower();

			for (int i = 0; i < text.Length; i++)
			{
				
				char c = text[i];
				if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
				{
					count++;
				}
			}
			return count;
		}
	}
}

