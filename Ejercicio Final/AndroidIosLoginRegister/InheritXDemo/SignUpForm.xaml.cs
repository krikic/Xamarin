using System;
using System.Collections.Generic;
using Xamarin.Forms;

namespace InheritXDemo
{
	public partial class SignUpForm : ContentPage
	{
		String strName = "", strEmail = "", strPassword = "", strConfirmPassword = ""
			, strMobileNumber = "";

		public SignUpForm()
		{
			InitializeComponent();
		}

		 void SignupClick(object sender, System.EventArgs e)
		{

			strName = entryName.Text;
			strEmail = entryEmail.Text;
			strPassword = entryPassword.Text;
			strConfirmPassword = entryConfirmPassword.Text;
			strMobileNumber = entryMobile.Text;

			if (isDataValidate()){

				DatabaseModel mDatabaseModel = new DatabaseModel();
				mDatabaseModel.personEmail = strEmail;
				mDatabaseModel.personName = strName;
				mDatabaseModel.personPassword = strPassword;
				mDatabaseModel.personMobileNumber = strMobileNumber;

				int intSaveResult = App.Database.SaveDataIntoDatabase(mDatabaseModel);
				openActionDialog(intSaveResult);

			}

		}

		async private void openActionDialog(int intResult)
		{
			if (intResult == 1){
				await DisplayAlert(Constant.APP_NAME , Constant.SIGNUP_SUCCESS , null,Constant.OK);
				SharedPreference.GetLoginStatus = "true";
				SharedPreference.GetEmailAddress = strEmail;
				SharedPreference.GetUsername = strName;
				SharedPreference.GetMobileNumber = strMobileNumber;

				App.Instance.ClearNavigationAndGoToPage(new NavigationDrawerDetailForm());

			}
			else{
				await DisplayAlert(Constant.APP_NAME, Constant.SIGNUP_USER_EXIST,Constant.OK);
			}
		}


		private Boolean isDataValidate()
		{
			if (strName.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_NAME, Constant.OK);
				return false;
			}
			else if (strMobileNumber.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_MOBILE, Constant.OK);
				return false;
			}
			else if (strMobileNumber.Length < 10)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_VALID_MOBILE, Constant.OK);
				return false;
			}
			else if (strEmail.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_EMAIL, Constant.OK);
				return false;
			}
			else if (strPassword.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_PASSWORD, Constant.OK);
				return false;
			}
			else if (strConfirmPassword.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_CONFIRM_PASSWORD, Constant.OK);
				return false;
			}
			else if (strPassword.Length < 6 || strConfirmPassword.Length < 6)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_VALID_PASSWORD, Constant.OK);
				return false;
			}
			else if (!strPassword.ToLower().Equals(strConfirmPassword.ToLower()))
			{
				DisplayAlert(Constant.APP_NAME, Constant.PASSWORD_NOT_MATCH, Constant.OK);
				return false;
			}

			

			return true;
		}
	}
}
