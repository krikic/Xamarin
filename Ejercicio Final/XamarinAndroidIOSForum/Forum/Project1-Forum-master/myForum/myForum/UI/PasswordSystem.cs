using System;

using Xamarin.Forms;

namespace myForum
{
    public class PasswordSystem : ContentPage
    {
        Entry oldPasswordEntry, newPasswordEntry, confirmPasswordEntry;
        public PasswordSystem()
        {
            Title = "Change Password";
            BackgroundColor = Color.FromHex("#fcf0cd");


            ToolbarItem toolbarItem = new ToolbarItem
            {
                Text = "Cancel"
            };
            ToolbarItems.Add(toolbarItem);
            toolbarItem.Clicked += (object sender, EventArgs e) => {
                Navigation.PopModalAsync();
            };

            //Old password label
            Label old = new Label
            {
                Text = "Old Password",
                FontSize = Device.GetNamedSize(NamedSize.Small, typeof(Label)),
                FontAttributes = FontAttributes.Bold,
                VerticalOptions = LayoutOptions.Center
            };
            //old password entry
            oldPasswordEntry = new Entry
            {
				IsPassword = true
            };

			//new password label 
			Label change = new Label
			{
				Text = "New Password",
				FontSize = Device.GetNamedSize(NamedSize.Small, typeof(Label)),
				FontAttributes = FontAttributes.Bold,
				VerticalOptions = LayoutOptions.Center
			};

			//new password entry
            newPasswordEntry = new Entry
			{
				IsPassword = true
			};

			//confirm label
			Label confirm = new Label
			{
				Text = "Confirm Password",
				FontSize = Device.GetNamedSize(NamedSize.Small, typeof(Label)),
				FontAttributes = FontAttributes.Bold,
				VerticalOptions = LayoutOptions.Center
			};

			//confirm password entry
            confirmPasswordEntry = new Entry
			{
				IsPassword = true
			};

            //Change button Button 
            Button changePassword = new Button
            {
                Text = "Change Password",
                TextColor = Color.White,
                BackgroundColor = Color.FromHex("#fa9b2f")
            };

            changePassword.Clicked += savePassword;

            //Use grid layout to build the password entry field
            Grid grid = new Grid { HorizontalOptions = LayoutOptions.FillAndExpand, VerticalOptions = LayoutOptions.FillAndExpand };
			grid.RowDefinitions.Add(new RowDefinition { Height = new GridLength(50) });
			grid.RowDefinitions.Add(new RowDefinition { Height = new GridLength(50) });
			grid.RowDefinitions.Add(new RowDefinition { Height = new GridLength(50) });
			grid.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(80) });
			grid.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(1, GridUnitType.Star) });
            //Add the element to the grid
            grid.Children.Add(old, 0,0);
            grid.Children.Add(change, 0, 1);
            grid.Children.Add(confirm, 0, 2);
            grid.Children.Add(oldPasswordEntry, 1, 0);
            grid.Children.Add(newPasswordEntry, 1, 1);
            grid.Children.Add(confirmPasswordEntry, 1, 2);
			grid.Children.Add(changePassword, 1, 3);
            //Add to the content
			var layout = new StackLayout { Padding = new Thickness(10, 50) };
            layout.Children.Add(new Frame { Content = grid });
			this.Content = layout;
        }

        async void  savePassword (object sender, EventArgs e)
        {
            //Get input value from user
            string oldPassword = oldPasswordEntry.Text;
            string newPassword = newPasswordEntry.Text;
            string confirmPassword = confirmPasswordEntry.Text;
            //Check the input
            if(string.IsNullOrWhiteSpace(oldPassword) || string.IsNullOrWhiteSpace(newPassword)|| string.IsNullOrWhiteSpace(confirmPassword))
            {
                await DisplayAlert("Error", "All field must be entered.", "Ok");
            }
            else
            {
                //If new pass and confirm pass not match
                if(newPassword !=confirmPassword)
                {
					await DisplayAlert("Error", "New password and confirm password must be matched.", "Ok");
                }
                else
                {
                    checkPassword();
                }

            }
        }

        async void checkPassword()
        {
            //Password input by user
            string oldPassword = oldPasswordEntry.Text;
			//Load the username from local database
			ItemData item = await App.Database.Load();
            string username = item.Username;
			//Encode to object
			JsonStringUser Jsonuser = new JsonStringUser();
			// Send input info request to server
			string result = await Connection.LoadUser(username);
			//Return the result to User class
			User data = Jsonuser.ToObject(result);
            //The password in cloud
            string currentPassword = data.password;
            //Check the password
            if(currentPassword != oldPassword)
            {
                await DisplayAlert("Error","Old password doesn't correct,please try again.","Ok");
            }
            else
            {
                string newPassword = newPasswordEntry.Text;
				//The username and password encode to Json 
				User password = new User(username, newPassword);
				//Convert to json
				JsonStringUser user = new JsonStringUser();
                string changedPassword = user.ToJsonString(password);
    			//Create user by Json
    			await Connection.CreateUser(username, changedPassword);

				//Delete login data
				App.Database.DeleteItemAsync();
				await DisplayAlert("Password Changed", "You have changed your password please login again with your new password!", "Ok");
				await Navigation.PushModalAsync(new NaviationTab());
			}
		}
    }
}

