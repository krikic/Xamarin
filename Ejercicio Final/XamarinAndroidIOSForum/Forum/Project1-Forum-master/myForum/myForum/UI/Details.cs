using System;

using Xamarin.Forms;
using System.Collections.Generic;

namespace myForum
{
    public class Details : ContentPage
    {
	    //Initial the listview
		ListView listView;

		//Initial List 
        List<Reply> list = new List<Reply>();

		//View Cell customer
		public class PostCell : ViewCell
		{
			public PostCell()
			{
				// Create post title for cell
				Label titleLabel = new Label();
				titleLabel.SetBinding(Label.TextProperty, "UserReply");
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

		public Details(int id)
        {
            //Set up background color 
            BackgroundColor = Color.FromHex("#f5e7b2");

            //Set the id
			((App)App.Current).IndexId = id;

            ToolbarItem toolbarItem = new ToolbarItem
            {
                Text = "Reply"
            };
            ToolbarItems.Add(toolbarItem);

            toolbarItem.Clicked += (object sender, EventArgs e) => {
                Navigation.PushModalAsync(new NavigationPage(new ReplySystem(id)));
            };

            //Set up title label
            Label titleLabel = new Label
            {
                HorizontalOptions = LayoutOptions.Center,
                FontAttributes = FontAttributes.Bold,
                FontSize = 24
            };

            titleLabel.SetBinding(Label.TextProperty, "Text");


			//Set up content label
			Label contentLabel = new Label
            {

                TextColor = Color.FromHex("#503026")
              

            };
			contentLabel.SetBinding(Label.TextProperty, "Description");


            //Set up username label
            Label nameLabel = new Label
            {
                TextColor = Color.Orange,
                FontSize = 15,
                HorizontalOptions = LayoutOptions.End
   
            };
            nameLabel.SetBinding(Label.TextProperty, "Username");


            //Set the time label
            Label timeLabel = new Label
            {
				TextColor = Color.Orange,
				FontSize = 15,
                HorizontalOptions = LayoutOptions.End
            };
            timeLabel.SetBinding(Label.TextProperty, "Time");


            //Button for the speak
			var speakButton = new Button
			{
				Text = "Speak",
                TextColor = Color.White,
                BackgroundColor = Color.FromHex("#fdbe68"),
				BorderRadius = 30,
				HeightRequest = 60,
				WidthRequest = 60,
                HorizontalOptions =  LayoutOptions.CenterAndExpand
			};

			//Speak button clicked
			speakButton.Clicked += (sender, e) =>
			{
				var item = (Post)BindingContext;
				DependencyService.Get<ITextToSpeech>().Speak(item.Text + " " + item.Description);
			};

			//Create frame for hot topics
			Frame containerTitle = new Frame
			{
				BackgroundColor = Color.FromHex("#fcf5c3"),
				HasShadow = false,

				VerticalOptions = LayoutOptions.Center,
				//Create view for container
				Content = new StackLayout
				{
					//Append input text to container
					Children = { titleLabel }
				}
			};


            //Create frame for hot topics
            Frame containerContent = new Frame
            {
                BackgroundColor = Color.FromHex("#fcf5c3"),
                HasShadow = false,

                //Create view for container
                Content = new StackLayout
                {
					//Append input text to container
                    Children = { contentLabel }
				}
			};


			//Create frame for hot topics
			Frame containerName = new Frame
			{
				BackgroundColor = Color.FromHex("#fcf5c3"),
				HasShadow = false,

				//Create view for container
				Content = new StackLayout
				{
					//Append input text to container
					Children = { nameLabel,timeLabel, speakButton }
				}
			};

      
			// Head for other topic label
			Label comments = new Label
			{
				Text = "Comments",
				HorizontalOptions = LayoutOptions.FillAndExpand,
				VerticalOptions = LayoutOptions.Start,
				BackgroundColor = Color.FromHex("#50bffe"),
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
					Children = { containerTitle, containerContent, containerName, comments, listView }
				}
			};

            //Add to view
			Content = new StackLayout
			{
                Spacing = 0,
                Children ={ scrollView }
			};
		}

		//Load Data from could storage
		protected override async void OnAppearing()
		{
			base.OnAppearing();
			Connection connection = new Connection();
			//Get the result from the server
			string result = await connection.LoadReply(((App)App.Current).IndexId);

			//Initial the GetTopic class
			JsonStringPost jsonPost = new JsonStringPost();
			//Check whatever the list is exist
			if (result != null)
			{
				//Set the data from the cloud to the list
				list = JsonStringReply.ToList(result);
				//Add the list to the listview
				listView.ItemsSource = list;
			}
		}

		//Listview  cell select
		void ItemSelected(object sender, SelectedItemChangedEventArgs e)
		{
			if (e.SelectedItem == null)
			{
				return;
			}

			//Deselect row
			listView.SelectedItem = null;
		}
	}
}


