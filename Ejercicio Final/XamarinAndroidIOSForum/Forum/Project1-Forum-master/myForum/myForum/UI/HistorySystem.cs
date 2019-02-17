using System;

using Xamarin.Forms;

namespace myForum
{
    public class HistorySystem : ContentPage
    {
        public HistorySystem()
        {
			//Set up background color 
			BackgroundColor = Color.FromHex("#f5e7b2");


            Label textLabel = new Label
            {
                FontAttributes = FontAttributes.Bold,
                FontSize = 24,
                TextColor = Color.FromHex("#fc7865"),
                Text = "Post topic:"
            };

            //Set up the post topic label
            Label topicLabel = new Label
            {
				FontAttributes = FontAttributes.Bold,
				FontSize = 24,
                TextColor = Color.FromHex("#fc7865")
            };
			topicLabel.SetBinding(Label.TextProperty, "Topic");

			//Create frame for topic
			Frame containerTopic = new Frame
			{
				BackgroundColor = Color.FromHex("#fcf5c3"),
				HasShadow = false,
				VerticalOptions = LayoutOptions.Start,
				//Create view for container
				Content = new StackLayout
				{
					Orientation = StackOrientation.Horizontal,
					//Append input text to container
					Children = { textLabel, topicLabel }
				}
			};

			//Set up title label
			Label titleLabel = new Label
			{
                HorizontalOptions = LayoutOptions.StartAndExpand,
				FontAttributes = FontAttributes.Bold,
				FontSize = 24
			};

			titleLabel.SetBinding(Label.TextProperty, "Text");


			//Create frame for title
			Frame containerTitle = new Frame
			{
				BackgroundColor = Color.FromHex("#fcf5c3"),
				HasShadow = false,

				VerticalOptions = LayoutOptions.Start,
				//Create view for container
				Content = new StackLayout
				{
					//Append input text to container
					Children = { titleLabel }
				}
			};

			//Set up content label
			Label contentLabel = new Label
			{
				TextColor = Color.FromHex("#503026"),
                FontSize = 20
			};
			contentLabel.SetBinding(Label.TextProperty, "Description");


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
				HorizontalOptions = LayoutOptions.CenterAndExpand
			};


			//Create frame for hot topics
			Frame containerName = new Frame
			{
				BackgroundColor = Color.FromHex("#fcf5c3"),
				HasShadow = false,

				//Create view for container
				Content = new StackLayout
				{
                    Spacing = 5,
					//Append input text to container
					Children = {timeLabel, nameLabel , speakButton }
				}
			};


			//Speak button clicked
			speakButton.Clicked += (sender, e) =>
			{
                var item = (UserPost)BindingContext;
				DependencyService.Get<ITextToSpeech>().Speak(item.Text + " " + item.Description);
			};

			//Add to view
			Content = new StackLayout
			{
				Spacing = 0,
				Children = {containerTopic , containerTitle, containerContent, containerName }
			};
		}
	}
}

