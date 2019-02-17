using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace InheritXDemo
{
	public partial class HomeForm : ContentPage
	{

		private String strMobileNumber;
		private String strUserName;

		public HomeForm()
		{
			InitializeComponent();

			String isLoggedIn = SharedPreference.GetLoginStatus;

			strMobileNumber = SharedPreference.GetMobileNumber;


			if (isLoggedIn.Equals("false"))
			{
				startLoginScreen();
			}



		}

		async private void startLoginScreen()
		{
			await Navigation.PushModalAsync(new LoginForm());
		}

		async void showDisplayAlert(String strMessage)
		{
			await DisplayAlert("", strMessage, "OK");
		}

	}


}
