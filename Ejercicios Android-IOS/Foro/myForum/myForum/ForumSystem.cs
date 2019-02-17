﻿using System;
using System.Collections.Generic;
using Xamarin.Forms;

namespace myForum
{
    public class ForumSystem : ContentPage
    {
        //Initialise listview
        ListView listView;

		class Post
		{
			public Post(string title, string detail, string time)
			{
				this.PostTitle = title;
				this.PostDetail = detail;
				this.PostTime = time;
			}

			public string PostTitle { private set; get; }
			public string PostDetail { private set; get;}
			public string PostTime { private set; get; }

		}

		public class PostCell : ViewCell
		{

			public PostCell()
			{
				// Create post title for cell
				Label titleLabel = new Label();
				titleLabel.SetBinding(Label.TextProperty, "PostTitle");

                //Create post detail label for the cell
				Label detailLabel = new Label();
				detailLabel.SetBinding(Label.TextProperty, "PostDetail");

                //Create time label for the cell
				Label time = new Label();
				StackLayout horizontal = new StackLayout();
				time.SetBinding(Label.TextProperty, "PostTime");
				horizontal.Orientation = StackOrientation.Horizontal;
				time.HorizontalOptions = LayoutOptions.EndAndExpand;

				//the cell for each views
				StackLayout cellWrapper = new StackLayout();

				//Set cell design
				cellWrapper.BackgroundColor = Color.FromHex("#fcf0cd");
				titleLabel.TextColor = Color.FromHex("#f35e20");
				detailLabel.TextColor = Color.FromHex("#75ebf9");
				time.TextColor = Color.FromHex("503026");


				//Create boxView
				BoxView box = new BoxView
				{
					WidthRequest = 5
				};

                //Costumer cell
				StackLayout cells = new StackLayout
				{
					Padding = new Thickness(0, 10),
					Orientation = StackOrientation.Horizontal,
					Children =
								{
									new StackLayout
									{
										VerticalOptions = LayoutOptions.Center,
										Spacing = 5,
										Children ={titleLabel,detailLabel}
									}
								}
                };

				//add views to the view hierarchy
				View = cellWrapper;
                horizontal.Children.Add(box);
                horizontal.Children.Add(cells);
                horizontal.Children.Add(time);
                cellWrapper.Children.Add(horizontal);
			}
		}
        
        public ForumSystem()
        {

            Title = "Forum";
            //Navigation bar item
            ToolbarItem newPost = new ToolbarItem
            {
                Text = "New post"
            };

            ToolbarItems.Add(newPost);
            newPost.Clicked += myPost;

			//Create Search bar on forum page
			SearchBar searchBar = new SearchBar
			{
				Placeholder = "Search"
			};

        
            //Create data for post
            List<Post> posts = new List<Post>
            {
				new Post("Hello there!", "wooooooooooo", "8:30"),
				new Post("Hello there!", "wooooooooooo", "8:36"),
				new Post("Hello there!", "wooooooooooo", "9:32"),
				new Post("Hello there!", "wooooooooooo", "10:17"),
				new Post("Hello there!", "wooooooooooo", "10:23"),
				new Post("Hello there!", "wooooooooooo", "10:34"),
				new Post("Hello there!", "wooooooooooo", "10:37"),
				new Post("Hello there!", "wooooooooooo", "11:20"),
				new Post("Hello there!", "wooooooooooo", "11:38"),
				new Post("Hello there!", "wooooooooooo", "12:30"),
				new Post("Hello there!", "wooooooooooo", "12:45"),
				new Post("Hello there!", "wooooooooooo", "12:50"),
				new Post("Hello there!", "wooooooooooo", "13:10"),
				new Post("Hello there!", "wooooooooooo", "13:31"),
				new Post("Hello there!", "wooooooooooo", "13:40"),
				new Post("Hello there!", "wooooooooooo", "14:09"),
				new Post("Hello there!", "wooooooooooo", "15:20"),
				new Post("Hello there!", "wooooooooooo", "15:30"),
				new Post("Hello there!", "wooooooooooo", "15:35"),
				new Post("Hello there!", "wooooooooooo", "16:37"),
				new Post("Hello there!", "wooooooooooo", "18:30"),
				new Post("Hello there!", "wooooooooooo", "19:30"),
				new Post("Hello there!", "wooooooooooo", "20:30")
            };

            //Create ListView
            listView = new ListView
            {
                ItemsSource = posts,
                ItemTemplate = new DataTemplate(typeof(PostCell)),
                RowHeight = 70
            };
            //ListView Cell selected
            listView.ItemSelected += ItemSelected;
         
            //Add scrollview makes suer the search bar can scroll up
            ScrollView scrollView = new ScrollView
            {
                Content = new StackLayout
                {
                    Children = {searchBar, listView}
                }
            };

            //Add View to forum page
            Content = new StackLayout
            {
                Children = {scrollView}
            };
        }
        //Login clicked
        async void myPost(object sender, System.EventArgs e)
		{
            await Navigation.PushAsync(new PostSystem());
		}
        //Listview  cell select
        async void ItemSelected (object sender, SelectedItemChangedEventArgs e)
        {
            if (e.SelectedItem == null)
            {
                return;
            }

             //Deselect row
            listView.SelectedItem = null;

			//Show page title
            await Navigation.PushAsync(new Details(e.SelectedItem));
        }
    }
}

