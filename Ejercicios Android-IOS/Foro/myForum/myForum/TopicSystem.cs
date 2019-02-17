using System;

using Xamarin.Forms;

namespace myForum
{
    public class TopicSystem : ContentPage
    {
        public TopicSystem()
        {
            //Set the title for topic page
            Title = "Topics";
            //Background colour
            BackgroundColor = Color.FromHex("#fcf0cd");
          
            // Head for hot topic label
            Label hot = new Label
            {
                Text = "Hot Topics",
                HorizontalOptions = LayoutOptions.FillAndExpand,
                VerticalOptions = LayoutOptions.Start,
                BackgroundColor = Color.FromHex("#fc7865"),
                TextColor = Color.White,
                FontAttributes = FontAttributes.Bold
            };

			// Head for other topic label
			Label other = new Label
            {
				Text = "Other Topics",
				HorizontalOptions = LayoutOptions.FillAndExpand,
				VerticalOptions = LayoutOptions.Start,
				BackgroundColor = Color.FromHex("#a6fe73"),
				TextColor = Color.White,
				FontAttributes = FontAttributes.Bold
            };


            // Zelda button
			var zelda = new Frame
            {
                OutlineColor = Color.Accent,
                BackgroundColor = Color.FromHex("#6efa8e"),
                HorizontalOptions = LayoutOptions.End,
				VerticalOptions = LayoutOptions.CenterAndExpand,
				Content = new Label
				{
					Text = "Zelda",
                    FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label))
				}
			};


            //LOL button
            var lol = new Frame
            {
                OutlineColor = Color.Accent,
                BackgroundColor = Color.FromHex("#6efa8e"),
                HorizontalOptions = LayoutOptions.Start,
                VerticalOptions = LayoutOptions.CenterAndExpand,
                Content = new Label
                {
                    Text = "LOL",
                    FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label))
                }

            };

            // WOW button 
			var wow = new Frame
			{
				OutlineColor = Color.Accent,
				BackgroundColor = Color.FromHex("#6efa8e"),
                HorizontalOptions = LayoutOptions.Center,
				VerticalOptions = LayoutOptions.CenterAndExpand,
				Content = new Label
				{
					Text = "WoW",
                    FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label))
				}

			};

            // Others topic button
			var Others = new Frame
			{
				OutlineColor = Color.Accent,
				BackgroundColor = Color.FromHex("#6efa8e"),
				HorizontalOptions = LayoutOptions.Center,
				VerticalOptions = LayoutOptions.CenterAndExpand,
				Content = new Label
				{
					Text = "Others",
					FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label))
				}

			};

            //Create frame for hot topics
            Frame containerhot = new Frame
            {
				BackgroundColor = Color.FromHex("#f8f8f8"),
				HasShadow = false,

				//Create view for container
				Content = new StackLayout
				{
                    Orientation = StackOrientation.Horizontal,
					Spacing = 10,

					//Append input text to container
                    Children = {zelda, lol, wow}
				}
            };

            //Create frame for other topics
			Frame containerother = new Frame
			{
				BackgroundColor = Color.FromHex("#f8f8f8"),
				HasShadow = false,

				//Create view for container
				Content = new StackLayout
				{
					Orientation = StackOrientation.Horizontal,
					Spacing = 10,

					//Append input text to container
                    Children = {Others}
				}
			};

            // make the frame clickable
			var tapGestureRecognizer = new TapGestureRecognizer();
            // When click 
			tapGestureRecognizer.Tapped += OnTapGestureRecognizerTapped;
            // Click zelda
            zelda.GestureRecognizers.Add(tapGestureRecognizer);
            //Click lol
			lol.GestureRecognizers.Add(tapGestureRecognizer);
            //Click wow
            wow.GestureRecognizers.Add(tapGestureRecognizer);
            //Click others
            Others.GestureRecognizers.Add(tapGestureRecognizer);

            //Add all components to view
			Content = new StackLayout
			{
                Spacing = 0,
                Children ={hot,containerhot,other,containerother }
			};
		}
        //frame clicked 
		void OnTapGestureRecognizerTapped(object sender, EventArgs args)
		{
            // Navigate to forum page
            Navigation.PushAsync(new ForumSystem());
		}
	}
}
