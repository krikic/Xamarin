
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Java.Lang;
using NUnit.Framework;

namespace XamarinLogin
{
	[Activity(Label = "SignUp")]
	public class SignUp : Activity, View.IOnClickListener
	{
		EditText userName, userEmail, userPassword;
		Button signUp;


		protected override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Create your application here
			SetContentView(Resource.Layout.sign_up);
			initialize();
		}
		public void initialize()
		{
			userName = (EditText)FindViewById(Resource.Id.newUserName);
			userEmail = (EditText)FindViewById(Resource.Id.newUserEmail);
			userPassword = (EditText)FindViewById(Resource.Id.newUserPassword);
			signUp = (Button)FindViewById(Resource.Id.signUpBtn);
			signUp.SetOnClickListener(this);
		}

		public void OnClick(View v)
		{
			switch (v.Id)
			{
				case Resource.Id.signUpBtn:
					if (userName.Text.Equals("") && userEmail.Text.Equals("") && userPassword.Text.Equals(""))
					{
						Toast.MakeText(this, "Empty Fields Found", ToastLength.Short).Show();
					}
					else {

						signUpAsync s = new signUpAsync(this);
						s.Execute();
					}
					break;
			}
		}

		public class signUpAsync : AsyncTask<Java.Lang.Object, Java.Lang.Object, Java.Lang.Object>
		{

			SignUp signObj;

			public signUpAsync(SignUp signObj)
			{
				this.signObj = signObj;
			}
			string name,email,pass;

			protected override void OnPreExecute()
			{
				base.OnPreExecute();
				name = signObj.userName.Text;
				email = signObj.userEmail.Text;
				pass = signObj.userPassword.Text;
			}

			protected override Java.Lang.Object RunInBackground(params Java.Lang.Object[] @params)
			{
				WebClient client = new WebClient();
				Uri uri = new Uri("http://your_server_ip/Login/xamarinsignUp.php");
				NameValueCollection parameters = new NameValueCollection();
				parameters.Add("uName", name);
				parameters.Add("uEmail", email);
				parameters.Add("uPass", pass);
				client.UploadValuesAsync(uri, parameters);

				return null;
			}

		}

	}
}

