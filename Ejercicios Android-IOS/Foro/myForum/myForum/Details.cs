using System;

using Xamarin.Forms;

namespace myForum
{
    public class Details : ContentPage
    {
        public Details(object detail)
        {
            Title = detail as string;



            Label label = new Label
            {
                HorizontalOptions = LayoutOptions.CenterAndExpand,
                VerticalOptions = LayoutOptions.Center,
                Text ="Hello there!"

            };


            Content = new StackLayout
            {
                Children ={label }
            };
        }
    }
}

