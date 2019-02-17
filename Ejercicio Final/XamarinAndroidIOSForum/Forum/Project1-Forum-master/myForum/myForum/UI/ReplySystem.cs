using System;

using Xamarin.Forms;
using System.Collections.Generic;
using System.Diagnostics;

namespace myForum
{
    public class ReplySystem : ContentPage
    {
        //Initial reply editor
        Editor replyEditor;

        public ReplySystem(int id)
        {
            //Set the title
            Title = "Reply";
			//Set up background color 
			BackgroundColor = Color.FromHex("#f5e7b2");

			ToolbarItem toolbarItem = new ToolbarItem
			{
				Text = "Cancel"
			};
			ToolbarItems.Add(toolbarItem);

			toolbarItem.Clicked += (object sender, EventArgs e) => {
                Navigation.PopModalAsync();
			};
            //Create text editor
            replyEditor = new Editor
            {
                HeightRequest = 160
            };

            Button replyButton = new Button
            {
                Text = "Reply",
				TextColor = Color.White,
				BackgroundColor = Color.FromHex("#fa9b2f")
            };

            replyButton.Clicked += reply;

			//Add view to Content page
			Content = new StackLayout
			{
				//Layout
				Padding = new Thickness(0, 0),
				VerticalOptions = LayoutOptions.StartAndExpand,
                Spacing = 50,
				//Add view to content page
				Children = { replyEditor , replyButton }
			};

			//Reply button clicked
			async void reply(object sender, EventArgs e)
			{
				//Get user input
				string replyInput = replyEditor.Text;
				//Set the current time
				string now = DateTime.Now.ToLocalTime().ToString();
				//Load user loggin info from local database
				ItemData item = await App.Database.Load();
				//Set the username from data
				var replytUser = item.Username;
				//Initial connection
				Connection connection = new Connection();
				//return result
                string result = await connection.LoadReply(id);
                //Set the reply message
                Reply userReply = new Reply (replyInput, replytUser, now);
                //Set the Json converter
                JsonStringReply jsonReply = new JsonStringReply();

				//Check not null
				if (string.IsNullOrWhiteSpace(replyInput))
				{
					await DisplayAlert("Error", "Reply text field can not be empty.", "Ok");
				}
				else
				{
                    if(result != null)
                    {
                        string convert = jsonReply.ToJsonString(userReply);
                        await connection.SendReply(id,convert);
                    }
                    else
                    {
                        List<Reply> list = new List<Reply>();
                        list.Add(userReply);
                        string convertList = jsonReply.ListToJson(list);
                        await connection.SendReply(id,convertList);
                    }
					await Navigation.PopModalAsync();
				}
			}

        }

    }
}

