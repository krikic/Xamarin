using System;
using ControlLibrary.Models;
using Xamarin.Forms;

namespace ControlLibrary.Prototypes
{
    public class ItemsView : ContentView
    {
        public ItemsView(Item item)
        {
            StackLayout layout = new StackLayout();
            layout.Children.Add(new Label { Text = item.Title } );
            layout.Children.Add(new Label { Text = item.Text });
            layout.Children.Add(new Label { Text = "No. " + item.Count.ToString() });
            layout.Children.Add(new Label { Text = "Price: " + item.Price.ToString() });

            this.BackgroundColor = Color.WhiteSmoke;
            Content = layout;
        }
    }
}

