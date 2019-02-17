using Android.App;
using Android.Widget;
using Android.OS;
using Android.Content;
using Android.Views;


namespace Factorial
{
	[Activity(Label = "Factorial")]
	public class MainActivity : Activity,Android.Views.View.IOnClickListener
	{
		
		 TextView txtanswer;
		Button volver1;
		Button Fact1;
	    EditText text;

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);


			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main1);
			initialize();
		}


		public void initialize()
		{
			Fact1 = (Button)FindViewById(Resource.Id.myButton);
			Fact1.SetOnClickListener(this);

			volver1 = (Button)FindViewById(Resource.Id.volver);
			volver1.SetOnClickListener(this);
			txtanswer = FindViewById<TextView>(Resource.Id.txtanswer);


			text = FindViewById<EditText>(Resource.Id.text);
			string number = text.ToString();

		}
public void OnClick(View v)
{
	switch (v.Id)
	{
				case Resource.Id.volver:
			var i = new Intent(this, typeof(IntentActivity));
			StartActivity(i);
					break;
				case Resource.Id.myButton:
			txtanswer.Text = "The Factorial is : " + calcFactorial().ToString();
			break;
			}
				


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

