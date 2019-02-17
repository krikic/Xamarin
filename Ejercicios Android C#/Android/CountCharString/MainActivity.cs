using Android.App;
using Android.Widget;
using Android.OS;
using Android.Views;
using Android.Text;




namespace CountCharString
{
	[Activity(Label = "CountCharString", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity
	{


	

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);

			// Get our button from the layout resource,
			// and attach an event to it

Button button = FindViewById<Button>(Resource.Id.myButton);
EditText frase = FindViewById<EditText>(Resource.Id.frase);
TextView result = FindViewById<TextView>(Resource.Id.resultado);







			button.Click += delegate
			{


	string s = frase.Text.ToString();


int ocurrencechar = countCharOccurrences(s);


	result.Text = ocurrencechar.ToString();

			};
		}




	public	int countCharOccurrences(string text)
		{


			int count = 0;

  char[] charArr = text.ToCharArray();

			foreach (char ch in charArr)
			{
				
				if (ch.Equals('a')) {
					count++;
				}
			}

				
			return count;	

					
				}


		}
	}

