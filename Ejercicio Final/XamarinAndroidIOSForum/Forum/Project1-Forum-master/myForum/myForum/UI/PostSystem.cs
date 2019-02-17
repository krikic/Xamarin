using System;
using Xamarin.Forms;
using System.Collections.Generic;
using System.Diagnostics;


namespace myForum
{
    public class PostSystem : ContentPage
    {
        Entry titleEntry;
        Editor contentEditor;

        public PostSystem()
        {
            //Set page title title
            Title = "Post";
            //Set background color
            BackgroundColor = Color.FromHex("#fcf0cd");

            //Add item button in navigation bar
            ToolbarItem toolbarItem = new ToolbarItem
            {
                Text = "Post"
            };
            //button click to post function
            toolbarItem.Clicked += post;
            //Add to navigation view
            ToolbarItems.Add(toolbarItem);
            //Title entry
            titleEntry = new Entry
            {
                Placeholder = "Title(Required)",
                HeightRequest = 40
            };
            //Content editor
            contentEditor = new Editor
            {
                HeightRequest = 230
            };

            //Add view to Content page
			Content = new StackLayout
			{
                //Layout
                Padding = new Thickness(5,10),
				VerticalOptions = LayoutOptions.StartAndExpand,
                //Add view to content page
                Children = {titleEntry, new Label { Text = "Content", FontAttributes = FontAttributes.Bold}, contentEditor }
			};
        }

		//After post
		async void post(object sender, EventArgs e)
        {
             string postTitle = titleEntry.Text;
             string postContent = contentEditor.Text;
		   	//Set the current time
             string now = DateTime.Now.ToLocalTime().ToString();
             //Initial connection
             Connection connection = new Connection();
             //Initial JsonStringPost
             JsonStringPost jsonPost = new JsonStringPost();
            //Initial JsonUserPost
            JsonUserPost jsonUserPost = new JsonUserPost();
            //Get the topic value from user click
            string topics = ((App)App.Current).TopicName;
			//Load user loggin info from local database
			ItemData item = await App.Database.Load();
			//Set the username from data
			var postUser = item.Username;
			//return result
			string result = await connection.LoadPost(topics);
            //return user
            string user = await connection.LoadUserPost(postUser);
             //The user post data
            Post postData = new Post(postTitle, postContent, postUser,now);
            //User post history
            UserPost userPost = new UserPost(topics, postTitle, postContent, postUser,now);
            //Check the title is not empty
            if(string.IsNullOrWhiteSpace(postTitle)){
                //Alert for the empty title
                await DisplayAlert("Post Error", "Title cannot be empty.", "Ok");

            }else{
                
				//Check the cloud storage 
                if(user != null)
                {
				     //User is exist the json will append to the clould
					string json = jsonUserPost.ToJsonString(userPost);
					//Post for per user
					connection.UserPost(postUser, json);
                }
                else
                {
					//Create list
					List<UserPost> list = new List<UserPost>();
					//Add data to list
					list.Add(userPost);
					//Encode from list to json
                    string json = jsonUserPost.ListToJson(list);
					//Post for per user
					connection.UserPost(postUser, json);
                }

                //Check the cloud storage 
				if (result != null)
				{
					//Topic is exist the json will append to the clould
					string json = jsonPost.ToJsonString(postData);
					connection.NewPost(topics, json);
				}
				else
				{
					//Create list
					List<Post> list = new List<Post>();
					//Add data to list
					list.Add(postData);
					//Encode from list to json
					string json = jsonPost.ListToJson(list);
					//post the json list
					connection.NewPost(topics, json);
				}

				await Navigation.PopAsync();
            }

           
        }
    }
}