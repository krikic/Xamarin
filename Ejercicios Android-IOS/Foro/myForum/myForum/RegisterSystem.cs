using System;

using Xamarin.Forms;

namespace myForum
{
    public class RegisterSystem : ContentPage
    {
        //Set up the entry for the register page to handle the user info.
        Entry usernameEntry, passwordEntry, confirmEntry;

        public RegisterSystem()
        {
            //Set the navigation title
            Title = "Sign up";
			//Set background colour
			BackgroundColor = Color.FromHex("#fcf0cd");


            //Label for sign up page
            Label label = new Label
            {
                Text = "Sign up",
                TextColor = Color.DarkGreen,
				FontSize = 35,
				FontAttributes = FontAttributes.Bold,
				HorizontalOptions = LayoutOptions.Center

			};

            //register page username text input
            usernameEntry = new Entry
            {
                Placeholder = "Username",
                Keyboard = Keyboard.Create(KeyboardFlags.None),
                HeightRequest = 40
            };

			//register page password text input
			passwordEntry = new Entry
            {
                Placeholder = "Password",
                IsPassword = true,
				HeightRequest = 40
            };

			//register page password confirm text input
			confirmEntry = new Entry
            {
                Placeholder = "Confirm",
                IsPassword = true,
				HeightRequest = 40
            };


            //Text input container
            Frame container = new Frame
            {
				BackgroundColor = Color.FromHex("#fcf0cd"),
				HasShadow = false,

				//Create view for container
				Content = new StackLayout
				{
					Orientation = StackOrientation.Vertical,
					Spacing = 0,

					//Append input text to container
                    Children = { usernameEntry, passwordEntry, confirmEntry}
				}
            };


			//Create login button
			Button registerButton = new Button
			{
				Text = "Sign up",
				TextColor = Color.White,
				FontSize = 20,
				FontAttributes = FontAttributes.Bold,
				BackgroundColor = Color.FromHex("#fa9b2f"),
				HorizontalOptions = LayoutOptions.FillAndExpand
			};

            registerButton.Clicked += registerFunction;

            //Add to view 
			Content = new StackLayout
            {
				Padding = 20,
				Spacing = 30,
                Children = {label, container,registerButton}
            };
        }

        //After sign up
        async void registerFunction (object sender, System.EventArgs e)
        {
            await Navigation.PushModalAsync(new NaviationTab());
        }
    }
}

