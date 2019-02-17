using Android.App;
using Android.Widget;
using Android.OS;
using Android.Views;
using System;
using Java.Lang;
using Android.Content;
using System.Net;
using System.Collections.Specialized;
using System.Text;
using Org.Json;

namespace XamarinLogin
{
	[Activity(Label = "XamarinLogin", MainLauncher = true, Icon = "@mipmap/icon")]
	public class MainActivity : Activity, Android.Views.View.IOnClickListener
	{
		
		TextView signUp;
		EditText email, password;
		Button signIn;
		signInAsync sn;

		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Set our view from the "main" layout resource
			SetContentView(Resource.Layout.Main);
			initialize();
		}
		public void initialize()
		{
			signUp = (TextView)FindViewById(Resource.Id.signUpTxt);
			email = (EditText)FindViewById(Resource.Id.userEmail);
			password = (EditText)FindViewById(Resource.Id.userPass);
			signIn = (Button)FindViewById(Resource.Id.signInBtn);
			signUp.SetOnClickListener(this);
			signIn.SetOnClickListener(this);
		}

		public void OnClick(View v)
		{
			switch (v.Id)
			{
				case Resource.Id.signInBtn:
					sn = new signInAsync(this);
					sn.Execute();
					break;
				case Resource.Id.signUpTxt:
					Intent up = new Intent(this, typeof(SignUp));
					StartActivity(up);
					Finish();
					break;
			}
		}

		public class signInAsync : AsyncTask<Java.Lang.Object, Java.Lang.Object, Java.Lang.Object>
		{
			MainActivity mainActivity;

			public signInAsync(MainActivity mainActivity)
			{
				this.mainActivity = mainActivity;
			}
			string userEmail, userPassword;
			protected override void OnPreExecute()
			{
				base.OnPreExecute();

				userEmail = mainActivity.email.Text;
				userPassword = mainActivity.password.Text;
			}
			protected override Java.Lang.Object RunInBackground(params Java.Lang.Object[] @params)
			{
				
				WebClient client = new WebClient();
				Uri uri = new Uri("http://192.168.1.100/Login/xamarinsignIn.php");
				NameValueCollection parameters = new NameValueCollection();
				parameters.Add("uemail", userEmail);
				parameters.Add("pass", userPassword);
				var response = client.UploadValues(uri, parameters);
				var responseString = Encoding.Default.GetString(response);
				JSONObject ob = new JSONObject(responseString);
				if (ob.OptString("success").Equals("1"))
				{
					mainActivity.RunOnUiThread(() =>
					Toast.MakeText(mainActivity, "You Successfully LogIn", ToastLength.Short).Show());
				}
				else {
					mainActivity.RunOnUiThread(() =>
					                           Toast.MakeText(mainActivity, "InValid Email or Password", ToastLength.Short).Show());

				}

				return null;
			}
		}

	}
}


