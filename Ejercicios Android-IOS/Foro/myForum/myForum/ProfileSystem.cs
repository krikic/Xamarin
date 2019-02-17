using System;

using Xamarin.Forms;

namespace myForum
{
    public class ProfileSystem : ContentPage
    {
        public ProfileSystem()
        {
            //Set title for profile page
            Title = "Profile";

            //Button in navigation bar for sign in and sign out
            if (App.IsUserLoggedIn == false)
            {
                //Background color
                BackgroundColor = Color.FromHex("#fcf0cd");

                // for User image
                Button button = new Button
                {
                    BorderRadius = 50,
                    HeightRequest = 100,
                    WidthRequest = 100,
                    BackgroundColor = Color.FromHex("#c8cbca"),
                    HorizontalOptions = LayoutOptions.Center,
                    VerticalOptions = LayoutOptions.Start
                };

                //Name label for username
                Label label = new Label
                {
                    Text = "Username",
                    TextColor = Color.FromHex("#ff9130"),
                    FontSize = 28,
                    HorizontalOptions = LayoutOptions.Center,
                    VerticalOptions = LayoutOptions.Center,
                    FontAttributes = FontAttributes.Bold
                };


                // name and image frame
                Frame container = new Frame
                {
					BackgroundColor = Color.FromHex("#daf1ee"),
					HasShadow = false,

					//Create view for container
					Content = new StackLayout
					{
						//Append input text to container
                        Children = {button ,label}
					}
                };

                Content = new StackLayout
                {
                    Children = {container}
                };


                 //item bar button
                ToolbarItem signIn = new ToolbarItem
                {
                    Text = "Sign in"
                };
                ToolbarItems.Add(signIn);
                signIn.Clicked += showLogin;
            }
            else
            {
                //After login
                //Backgound colour 
				BackgroundColor = Color.FromHex("#fcf0cd");


                //User profile image 
				Button button = new Button
				{
					BorderRadius = 50,
					HeightRequest = 100,
					WidthRequest = 100,
					BackgroundColor = Color.FromHex("#e176fc"),
					HorizontalOptions = LayoutOptions.Center,
					VerticalOptions = LayoutOptions.Start
				};

                //Username label
				Label label = new Label
				{
					Text = "Test",
					TextColor = Color.FromHex("#ff9130"),
					FontSize = 28,
					HorizontalOptions = LayoutOptions.Center,
					VerticalOptions = LayoutOptions.Center,
					FontAttributes = FontAttributes.Bold
				};

                // Username and image frame
				Frame container = new Frame
				{
					BackgroundColor = Color.FromHex("#daf1ee"),
					HasShadow = false,

					//Create view for container
					Content = new StackLayout
					{
						//Append input text to container
						Children = { button, label }
					}
				};


                //Add to view
				Content = new StackLayout
				{
					Children = { container }
				};



				// After sign in
				ToolbarItem signOut = new ToolbarItem
                {
                    Text = "Sign out"
                };
                ToolbarItems.Add(signOut);
                signOut.Clicked += logout;
            }

            //Login
            async void showLogin(object sender, System.EventArgs e)
            {
                await Navigation.PushAsync(new LoginSystem());
            }

            //Logout
            async void logout(object sender, System.EventArgs e)
            {
                App.IsUserLoggedIn = false;
                await DisplayAlert("Logout", "Logout success!", "Ok");
                await Navigation.PushModalAsync(new NaviationTab());
            }
        }
    }
}

