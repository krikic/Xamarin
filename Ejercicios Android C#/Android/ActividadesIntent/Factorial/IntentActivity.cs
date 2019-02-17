using Android.App;
using Android.Widget;
using Android.OS;
using Android.Content;
using Android.Views;


namespace Factorial
{
[Activity(Label = "IntentActivity", MainLauncher = true, Icon = "@mipmap/icon")]
	public class IntentActivity : Activity, Android.Views.View.IOnClickListener
	{


		Button Button1;

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Principal);
			initialize();
		}
		public void initialize()
		{
			Button1 = (Button)FindViewById(Resource.Id.Botton1);


			Button1.SetOnClickListener(this);


		}

		public void OnClick(View v)
		{
			switch (v.Id)
			{
				case Resource.Id.Botton1:
					var i = new Intent(this, typeof(MainActivity));
					StartActivity(i);
					break;

			}
		}
	}
}



