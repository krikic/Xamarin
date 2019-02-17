using System;

using Xamarin.Forms;

namespace myForum
{
    public class PostSystem : ContentPage
    {
        public PostSystem()
        {
            //Set page title title
            Title = "New Post";
            //Set background color
            BackgroundColor = Color.FromHex("#eee");

			//Add item button in navigation bar
			ToolbarItem toolbarItem = new ToolbarItem
			{
				Text = "Post"
			};
            //button click to post function
			toolbarItem.Clicked += post;
            //Add to navigation view
			ToolbarItems.Add(toolbarItem);

            //Create table view
			TableView tableView = new TableView
            {
                Root = new TableRoot(){
                    new TableSection(){
                        // title entry
                        new EntryCell{
                            Label = "Title:",
                            Placeholder = "Title text here...",
                            Keyboard = Keyboard.Default
                        },
                        //content entry
                        new EntryCell{
                            Label = "Content:",
                            Placeholder = "Content text here...."
                        }
                    }
                }
            };


            //Add view to Content page
            Content = new StackLayout
            {
                Children = {tableView}
            };
        }


		//After post
		async void post(object sender, EventArgs e)
		{
          //Not login
			if(App.IsUserLoggedIn == false){
                await  DisplayAlert("You must Login first","You haven't login yet.","Ok");
                await Navigation.PushAsync(new LoginSystem());
            }else{
				//Forum main page
                await Navigation.PushModalAsync(new NaviationTab());
            }
		}
    }
}

