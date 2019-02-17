using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace NavigationTransitions
{
    public partial class MainPage : ContentPage
    {
        void FirstPageButton_OnClicked(object sender, System.EventArgs e)
        {
            Navigation.PushAsync(new SecondPage());
        }

        void SecondPageButton_OnClicked(object sender, System.EventArgs e)
        {
            Navigation.PushAsync(new ThirdPage());
        }

        public MainPage()
        {
            InitializeComponent();
            this.BindingContext = new MainPageModel();
        }
    }
}

