using Android.App;
using Android.Widget;
using Android.OS;

namespace CountLetters
{
	[Activity(Label = "CountLetters", MainLauncher = true, Icon = "@mipmap/icon")]
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
			Button button = FindViewById<Button>(Resource.Id.charButton);
			EditText frase = FindViewById<EditText>(Resource.Id.numberEditText);
			TextView result = FindViewById<TextView>(Resource.Id.resultado);

			button.Click += delegate { 
			
				string s = frase.Text.ToString();


				int letters = countLetters(s);


				result.Text = letters.ToString();
			};
		}

		static int countLetters(string srt)
		{
			int count = 0;
			bool lastWasSpace = false;

			foreach (char c in srt)
			{
				if (char.IsWhiteSpace(c))
				{
					// A.
					// Only count sequential spaces one time.
					if (lastWasSpace == false)
					{
						
					}
					lastWasSpace = true;
				}
				else
				{
					// B.
					// Count other characters every time.
					count++;
					lastWasSpace = false;
				}
			}
			return count;

		}
	}
}

