using System;

using Xamarin.Forms;
using System.Diagnostics;

namespace myForum
{
    public class LoginSystem : ContentPage
    {
        //Set up the entry to check the user login info.
        Entry usernameEntry, passwordEntry;

        public LoginSystem()
        {
            //Set navigation bar title
            Title = "Login";
            //Set background colour
            BackgroundColor = Color.FromHex("#fcf0cd");


            //Add item button in navigation bar
            ToolbarItem toolbarItem = new ToolbarItem
            {
                Text = "Cancel"
            };
            toolbarItem.Clicked += Cancel;
            //Add button to navigation
            ToolbarItems.Add(toolbarItem);


            //Create Label for the login page
            Label loginLabel = new Label
            {
                Text = "Login",
                TextColor = Color.DarkRed,
                FontSize = 35,
                FontAttributes = FontAttributes.Bold,
                HorizontalOptions = LayoutOptions.Center
            };

            //Create text input to handle username
            usernameEntry = new Entry
            {
                Placeholder = "Username",
                HeightRequest = 40,
                Keyboard = Keyboard.Create(KeyboardFlags.None)

            };
            //Set the property for for the username entry
			usernameEntry.SetBinding(Entry.TextProperty, "Username");

			//Create text input to hanlde password
		    passwordEntry = new Entry
            {
                Placeholder = "Password",
                HeightRequest = 40,
                IsPassword = true
            };
			//Set the property for for the password entry
			passwordEntry.SetBinding(Entry.TextProperty, "Password");

            //Create container for text input
            Frame container = new Frame
            {
                BackgroundColor = Color.FromHex("#fcf0cd"),
                HasShadow = false,

                //Create view for container
                Content = new StackLayout
                {
                    Orientation = StackOrientation.Vertical,
                    Spacing = 10,

                    //Append input text to container
                    Children = { usernameEntry, passwordEntry }
                }

            };

            //Create login button
            Button loginButton = new Button
            {
                Text = "Login",
                TextColor = Color.White,
                FontSize = 20,
                FontAttributes = FontAttributes.Bold,
                BackgroundColor = Color.FromHex("#fa9b2f"),
                HorizontalOptions = LayoutOptions.FillAndExpand
            };

            //Create register button
            Button registerButton = new Button
            {
                Text = "Register",
            };

            //When click login button
            loginButton.Clicked += LoggedIn;
            registerButton.Clicked += register;

            //Append all view to the login page
            Content = new StackLayout
            {
                Padding = 20,
                Spacing = 20,
                Children = {loginLabel, container, loginButton, registerButton }
            };
        }



		//public string SHA256Hash(string input)
		//{
		//	byte[] sign_byte = Encoding.UTF8.GetBytes(input);
		//	var sha2 = SHA256.Create();
		//	sign_byte = sha2.ComputeHash(sign_byte);
		//	return Encoding.UTF8.GetString(sign_byte);
		//}


		//Check login
		async void LoggedIn(object sender, EventArgs e) 
        {
            //Get input info
            string username = usernameEntry.Text;
            string password = passwordEntry.Text;

            if(string.IsNullOrWhiteSpace(username) || string.IsNullOrWhiteSpace(password)){
                
                await DisplayAlert("Login Error", "All input field must be entered!", "Ok");
            }
            else
            {
				CheckUser();
			}
        }

		//Check username and password
		async void CheckUser()
		{
			//Initialise the username and password.
			string username = usernameEntry.Text;
			string password = passwordEntry.Text;

			//Retrieve username list from server
            string userList = await Connection.GetList();

			//Check the username in the database or not
            if (userList.Contains(username + ".user"))
			{
                //Encode to object
                JsonStringUser Jsonuser = new JsonStringUser();
                // Send input info request to server
                string result = await Connection.LoadUser(username);
                //Return the result to User class
                User data = Jsonuser.ToObject(result);
				// Retrieve data for the username and password from server
				string user = data.username;
				string pass = data.password;
				//Check the password is correct or not.
				if (pass != password)
				{
					//Show Error message if the password is wrong.
					await DisplayAlert("Login Error", "Password is not correct, please try again.", "Ok");
				}
				else
				{
					//Password is correct
					await DisplayAlert("Logged in", "Welcome back " + user , "Ok");
					//Save username and password to local database
					var item = (ItemData)BindingContext;
					await App.Database.SaveItemAsync(item);

	         		await Navigation.PushModalAsync(new NaviationTab());
				}
			}
			else
			{
				//Show the error message when username is not correct.
				await DisplayAlert("Login Error", "Username does not exist, please try again!", "Ok");
			}
		}

		//register
        async void register(object sender, EventArgs e)
        {
            Dismiss();
            //register page   
            await Navigation.PushModalAsync(new NavigationPage(new RegisterSystem()
            {
                 BindingContext = new ItemData()
            }));
        }

        //The Navigation bar cancel button
        async void Cancel(object sender, EventArgs e){
            //Cancel the page
            await Navigation.PopModalAsync();
        }

        //Dismiss currect page
		void Dismiss()
		{
			//Cancel the currect page
			Navigation.PopModalAsync();
		}
	}
}

