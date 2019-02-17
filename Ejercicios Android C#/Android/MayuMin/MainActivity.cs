using Android.App;
using Android.Widget;
using Android.OS;

namespace MayuMin
{
	[Activity(Label = "MayuMin", MainLauncher = true, Icon = "@mipmap/icon")]
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
			Button buttonMay = FindViewById<Button>(Resource.Id.MayButton);
			Button buttonMin = FindViewById<Button>(Resource.Id.MinButton);
			EditText frase = FindViewById<EditText>(Resource.Id.frase);
			TextView result = FindViewById<TextView>(Resource.Id.resultTextView);


			buttonMay.Click += delegate {
				result.Text = getMayusculas(frase.Text.ToString());
			};
			buttonMin.Click += delegate
			{
				result.Text = getMinusculas(frase.Text.ToString());
			};
		}
		public string getMayusculas(string s)
		{

			s = s.ToUpper();

			return s;
		}




		public string getMinusculas(string s)
		{

			s = s.ToLower();

			return s;
		}

	}
}

