
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Calculator1
{
	[Activity (Label = "MainCalculator", MainLauncher = true, Icon = "@mipmap/icon")]	
	public class MainCalculator : Activity
	{
		protected override void OnCreate (Bundle savedInstanceState)
		{base.OnCreate (savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView (Resource.Layout.CalculatorScreen);

			// Get our button from the layout resource,
			// and attach an event to it
			EditText additionEditText1 = FindViewById <EditText>(Resource.Id.editTextAddition1);
			EditText additionEditText2 = FindViewById <EditText>(Resource.Id.editTextAddition2);
			EditText additionEditTextResult = FindViewById <EditText>(Resource.Id.editTextAddition3);
			EditText subtractionEditText1 = FindViewById <EditText>(Resource.Id.editTextSubtraction1);
			EditText subtractionEditText2 = FindViewById <EditText>(Resource.Id.editTextSubtraction2);
			EditText subtractionEditTextResult = FindViewById <EditText>(Resource.Id.editTextSubtraction3);
			EditText multiplicationEditText1 = FindViewById <EditText>(Resource.Id.editTextMultiplication1);
			EditText multiplicationEditText2 = FindViewById<EditText> (Resource.Id.editTextMultiplication2);
			EditText multiplicationEditTextResult = FindViewById<EditText> (Resource.Id.editTextMultiplication3);
			EditText divideEditText1 = FindViewById<EditText>(Resource.Id.editTextDivision1);
			EditText divideEditText2 = FindViewById<EditText>(Resource.Id.editTextDivision2);
			EditText divideEditTextResult = FindViewById<EditText>(Resource.Id.editTextDivision3);
			Button button = FindViewById<Button> (Resource.Id.calculate);

			button.Click += delegate {

				if(additionEditText1.Text.ToString().Trim() == "" ||
					additionEditText2.Text.ToString().Trim() == "" ||
					subtractionEditText1.Text.ToString().Trim() == "" ||
					subtractionEditText2.Text.ToString().Trim() == "" ||
					multiplicationEditText1.Text.ToString().Trim() == "" ||
					multiplicationEditText2.Text.ToString().Trim() == "" ||
					divideEditText1.Text.ToString().Trim() == "" ||
					divideEditText2.Text.ToString().Trim() == ""){

					additionEditText1.Text = "";
					additionEditText2.Text = "";
					additionEditTextResult.Text = "0";
					subtractionEditText1.Text = "";
					subtractionEditText2.Text = "";
					subtractionEditTextResult.Text = "0";
					multiplicationEditText1.Text = "";
					multiplicationEditText2.Text = "";
					multiplicationEditTextResult.Text = "0";
					divideEditText1.Text = "";
					divideEditText2.Text = "";
					divideEditTextResult.Text = "0";
				}else{
					double additionResult = double.Parse(additionEditText1.Text.ToString()) + double.Parse(additionEditText2.Text.ToString());
					double subtractionResult = int.Parse(subtractionEditText1.Text.ToString()) - double.Parse(subtractionEditText2.Text.ToString());
					double multiplicationResult = double.Parse(multiplicationEditText1.Text.ToString()) * double.Parse(multiplicationEditText2.Text.ToString());
					double divideResult = double.Parse(divideEditText1.Text.ToString()) / double.Parse(divideEditText2.Text.ToString());

					additionEditTextResult.Text = additionResult+"";
					subtractionEditTextResult.Text = subtractionResult+"";
					multiplicationEditTextResult.Text = multiplicationResult+"";
					divideEditTextResult.Text = divideResult + "";

				}

			};
		}
	}
}

