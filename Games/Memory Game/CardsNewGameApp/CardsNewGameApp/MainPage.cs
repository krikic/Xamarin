using System;
using Xamarin.Forms;

namespace CardsNewGameApp
{
    public class MainPage : ContentPage
    {
        Button btn;
        public MainPage()
        {
            Title = "Main page";

            Padding = new Thickness(5, Device.OnPlatform(20, 0, 0), 5, 0);

            btn = new Button
            {          
                Text = "New Game",
                WidthRequest=150,
                BackgroundColor = Color.Red,
                TextColor = Color.White,
                HorizontalOptions = LayoutOptions.CenterAndExpand

            };
            var AboutBtn = new Button
            {
                
                Text ="  About ",
                WidthRequest=150,
                BackgroundColor = Color.Red,
                TextColor = Color.White,
                HorizontalOptions = LayoutOptions.CenterAndExpand
            };

            AboutBtn.Clicked += AboutBtn_Clicked;
            btn.Clicked += Btn_Clicked;
            Content = new StackLayout
            {
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center,

                Children =
                   {
                        new StackLayout
                        {      Spacing=30,
                               Orientation=StackOrientation.Vertical,
                               Children = {
                               btn,AboutBtn
                        }
                    }
                }
            };
        }

        private async void AboutBtn_Clicked(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new AboutPage());
        }

        private async void Btn_Clicked(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new CardViewPage());
        }
    }
}
