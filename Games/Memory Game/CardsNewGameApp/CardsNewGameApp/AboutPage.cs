using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Emit;
using System.Text;

using Xamarin.Forms;

namespace CardsNewGameApp
{
    public class AboutPage : ContentPage
    {
        public AboutPage()
        {
            Padding = new Thickness(0, 10, 0, 10);
            Title = "My memory game";

            var vContentStack = new StackLayout
            {
                HorizontalOptions=LayoutOptions.CenterAndExpand, 
                VerticalOptions = LayoutOptions.CenterAndExpand,
               BackgroundColor =Color.Black,

                Children = {
                       new Label { Text = "Name : Cards", TextColor=Color.White },
                       new Label { Text = "Programmer: Eng. Issa Mohamad", TextColor=Color.White },
                       new Label { Text = "Platform: Xamarin.forms Visual Studio.net", TextColor=Color.White },
                       new Label { Text = "Place : Sweden", TextColor=Color.White },
                       new Label { Text = "Twitter : @IssaMohamad", TextColor=Color.White },
                       new Label { Text = "Blog : Programmerare", TextColor=Color.White},
                }
            };
            
            Content = vContentStack;
        }
    }
}
