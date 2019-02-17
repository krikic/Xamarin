using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace InheritXDemo
{
	public partial class NavigationDrawerDetailForm : MasterDetailPage
	{

		String strLoginStatus;
		String strUserName;

		public NavigationDrawerDetailForm()
		{
			InitializeComponent();

			strLoginStatus = SharedPreference.GetLoginStatus;




			if (strLoginStatus.ToLower().Equals("false"))
			{
				Navigation.PushModalAsync(new LoginForm());
			}
			else
			{
				DatabaseModel mDatabaseModel = App.Database.getUserData(SharedPreference.GetMobileNumber).Result;
				strUserName = mDatabaseModel.personName;

				lableUserName.Text = "Welcome, " + strUserName;

				changeNavigationPage(new HomeForm());
			}
		}





		void openHome(object sender, System.EventArgs e)
		{
			changeNavigationPage(new HomeForm());
		}

		void openChangePassword(object sender, System.EventArgs e)
		{
			changeNavigationPage(new ChangePasswordForm());
		}

		void openAboutUs(object sender, System.EventArgs e)
		{
			changeNavigationPage(new AboutUsForm());
		}

		async void openLogout(object sender, System.EventArgs e)
		{
			changeNavigationPage(new LoginForm());

			var result = await DisplayAlert(Constant.APP_NAME ,Constant.LOGOUT_MESSAGE,Constant.NO,Constant.YES);

			if (result == true)
			{
				SharedPreference.GetLoginStatus = "false";
				await Navigation.PopAsync();
			}


		}

		private void changeNavigationPage(ContentPage contentPage)
		{
			Detail = new NavigationPage(contentPage);
		}
	}
}
