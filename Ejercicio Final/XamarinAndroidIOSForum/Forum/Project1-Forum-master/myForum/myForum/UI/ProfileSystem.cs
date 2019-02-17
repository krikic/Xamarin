using System;

using Xamarin.Forms;
using System.Collections.Generic;

namespace myForum
{
    public class ProfileSystem : ContentPage
    {
        public ProfileSystem()
        {
            //Set title for profile page
            Title = "Profile";
			//Backgound colour 
			BackgroundColor = Color.FromHex("#fcf0cd");

            checkLogin();
        }

		//Initialise listview
		ListView listView;

		//View Cell customer
		public class PostCell : ViewCell
		{
			public PostCell()
			{
				// Create post title for cell
				Label titleLabel = new Label();
				titleLabel.SetBinding(Label.TextProperty, "Text");
                //Set the line break
                titleLabel.LineBreakMode = LineBreakMode.MiddleTruncation;

				//Set the horizontal orientation
				StackLayout horizontal = new StackLayout();
				horizontal.Orientation = StackOrientation.Horizontal;

				//the cell for each views
				StackLayout cellWrapper = new StackLayout();

				//Set cell design
				cellWrapper.BackgroundColor = Color.FromHex("#fcf0cd");
				titleLabel.TextColor = Color.FromHex("#f35e20");


				//Costumer cell
				StackLayout cells = new StackLayout
				{
					Padding = new Thickness(20, 15),
					Orientation = StackOrientation.Horizontal,
					Children =
								{
									new StackLayout
									{
										VerticalOptions = LayoutOptions.StartAndExpand,
										Spacing = 30,
										Children ={titleLabel}
									}
								}
				};

				//add views to the view hierarchy
				View = cellWrapper;
				horizontal.Children.Add(cells);
				cellWrapper.Children.Add(horizontal);
			}
		}

		//Load
		public  async void checkLogin()
		{
			//Load database
			List<ItemData> list = await App.Database.GetItemsAsync();
            //Check the database is not null
            if (list.Count != 0)
            {
                //Load the username from local database
                ItemData item = await App.Database.Load();

				//User profile image 
                Image profileImage = new Image
				{
					HeightRequest = 100,
					WidthRequest = 100,
					BackgroundColor = Color.FromHex("#fca5eb"),
					HorizontalOptions = LayoutOptions.Center,
					VerticalOptions = LayoutOptions.Start
				};

				//Username label
				Label usernameLabel = new Label
				{
					TextColor = Color.FromHex("#ff9130"),
					FontSize = 28,
					HorizontalOptions = LayoutOptions.Center,
					VerticalOptions = LayoutOptions.Center,
					FontAttributes = FontAttributes.Bold
				};
				usernameLabel.Text = item.Username;

		       //Set the button let user can change them password
                Button changeButton = new Button
                {
                    Text = "Change Password"
                };

                //Change password clicked
                changeButton.Clicked += (sender, e) => {
                    Navigation.PushModalAsync(new NavigationPage(new PasswordSystem()));
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
						Children = { profileImage, usernameLabel, changeButton }
					}
				};


				// Head for other topic label
				Label history = new Label
				{
					Text = "Post Hitotry",
					HorizontalOptions = LayoutOptions.FillAndExpand,
					VerticalOptions = LayoutOptions.Start,
					BackgroundColor = Color.FromHex("#a6fe73"),
					TextColor = Color.White,
					FontAttributes = FontAttributes.Bold
				};

				//List view
				listView = new ListView
				{
					ItemTemplate = new DataTemplate(typeof(PostCell))
				};
                //Click the cell
                listView.ItemSelected += ItemSelected;


				//Add scroll view
				ScrollView scrollView = new ScrollView
				{
					Content = new StackLayout
					{
						Spacing = 0,
						Children = { container, history, listView}
					}
				};

				//Add to view
				Content = new StackLayout
				{
					Spacing = 0,
					Children = { scrollView }
				};

				//After login
				ToolbarItem logOut = new ToolbarItem
				{
					Text = "Logout"
				};
				ToolbarItems.Add(logOut);
				logOut.Clicked += logout;

            }
            else
            {
				//User profile image 
				Image profileImage = new Image
				{
					HeightRequest = 100,
					WidthRequest = 100,
					BackgroundColor = Color.FromHex("#d8d6d8"),
					HorizontalOptions = LayoutOptions.Center,
					VerticalOptions = LayoutOptions.Start
				};

				//Name label for username
				Label label = new Label
				{
					Text = "Start to post within your  account.",
                    TextColor = Color.Gray,
					FontSize = 28,
                    HorizontalOptions = LayoutOptions.StartAndExpand,
					FontAttributes = FontAttributes.Bold
				};

				// name and image frame
				Frame container = new Frame
				{
					BackgroundColor = Color.FromHex("#daf1ee"),
					HasShadow = false,
                    HeightRequest = 150,

					//Create view for container
					Content = new StackLayout
					{
						//Append input text to container
						Children = {profileImage, label }
					}
				};

				Content = new StackLayout
				{
					Children = { container }
				};


				//item bar button
				ToolbarItem signIn = new ToolbarItem
				{
					Text = "Login"
				};
				ToolbarItems.Add(signIn);
				signIn.Clicked += showLogin;
                
            }
		}

        //Load Data from could storage
        protected override async void OnAppearing()
        {
            base.OnAppearing();

			//Load database
			List<ItemData> list = await App.Database.GetItemsAsync();

			//Check the database is not null
			if (list.Count != 0)
			{
				//Load the username from local database
				ItemData item = await App.Database.Load();
				//initial the post class
				Connection connection = new Connection();
				//Get the result from the server
				string result = await connection.LoadUserPost(item.Username);
				//Initial the GetTopic class
				JsonUserPost jsonPost = new JsonUserPost();

				if (result != null)
				{
					//Set the data from the cloud to the list
					List<UserPost> userpostlist = jsonPost.ToList(result);
					//Add to list view
					listView.ItemsSource = userpostlist;
				}
			}
        }

		//Logout
		async void logout(object sender, System.EventArgs e)
		{
            //Delete login data
            App.Database.DeleteItemAsync();

			await DisplayAlert("Logout", "Logout success!", "Ok");
          
			await Navigation.PushModalAsync(new NaviationTab());
		}

		//Login
		async void showLogin(object sender, System.EventArgs e)
		{
			await Navigation.PushModalAsync(new NavigationPage(new LoginSystem
			{
				BindingContext = new ItemData()
			}));
		}

		//Listview  cell select
		async void ItemSelected(object sender, SelectedItemChangedEventArgs e)
		{
			if (e.SelectedItem == null)
			{
				return;
			}

			//Deselect row
			listView.SelectedItem = null;

			//To the detail page
			await Navigation.PushAsync(new HistorySystem
			{
				//Set the navigation title name from the list view
                Title = (e.SelectedItem as UserPost).Text,
				//Set the data to pass to the new page
				BindingContext = e.SelectedItem as UserPost
			});
		}
    }
}

