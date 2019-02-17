using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace InheritXDemo
{
	public partial class ChangePasswordForm : ContentPage
	{

		String strMobileNumber;
		String strOldPassword, strNewPassword, strNewConfirmPassword;

		public ChangePasswordForm()
		{
			InitializeComponent();

			strMobileNumber = SharedPreference.GetMobileNumber;

			lableMobileNumber.Text = "You're logged in with " + strMobileNumber;

		}

		void clickChangePassword(object sender, System.EventArgs e)
		{

			strOldPassword = entryOldPassword.Text;
			strNewPassword = entryNewPassword.Text;
			strNewConfirmPassword = entryNewConfirmPassword.Text;

			if (isDataValidate())
			{

				if (App.Database.isUserExists(strMobileNumber))
				{
					Boolean isUpdated = App.Database.updatePassword(strMobileNumber, strNewPassword);

					if (isUpdated)
					{
						DisplayAlert(Constant.APP_NAME, "Password changed successfully", Constant.OK);
						entryOldPassword.Text = "";
						entryNewPassword.Text = "";
						entryNewConfirmPassword.Text = "";
					}
					else
					{
						DisplayAlert(Constant.APP_NAME, "Password is NOT changed", Constant.OK);
					}

				}

			}

		}

		private Boolean isDataValidate()
		{
			if (strOldPassword.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_PASSWORD, Constant.OK);
				return false;
			}
			else if (strNewPassword.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_PASSWORD, Constant.OK);
				return false;
			}
			else if (strNewConfirmPassword.Length == 0)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_CONFIRM_PASSWORD, Constant.OK);
				return false;
			}
			else if (strOldPassword.Length < 6 || strNewConfirmPassword.Length < 6 || strNewPassword.Length < 6)
			{
				DisplayAlert(Constant.APP_NAME, Constant.ENTER_VALID_PASSWORD, Constant.OK);
				return false;
			}
			else if (!strNewPassword.ToLower().Equals(strNewConfirmPassword.ToLower()))
			{
				DisplayAlert(Constant.APP_NAME, Constant.PASSWORD_NOT_MATCH, Constant.OK);
				return false;
			}

			return true;

		}

	}
}
