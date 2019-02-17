using System;
using System.Collections.Generic;
using ControlLibrary.Models;
using ControlLibrary.Prototypes;
using Xamarin.Forms;

namespace ControlLibrary.Pages
{
    public partial class StackScrollGridPage : ContentPage
    {
        public StackScrollGridPage()
        {
            List<Item> items = new List<Item> {
                new Item("Carrot", "This is a vegetable", 26, 0.99m),
                new Item("Orange Juice", "A tasty beverage", 12, 1.99m),
                new Item("Apple", "This is a fruit", 54, 0.50m),
                new Item("Bread", "Made from wholemeal flour", 8, 2.49m)
            };

            StackLayout stackLayout = new StackLayout();
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));
            stackLayout.Children.Add(new ScrollingGridView(items));



            ScrollView verticalScrollView = new ScrollView()
            {
                Orientation = ScrollOrientation.Vertical,
                Content = stackLayout

            };

            Frame outerFrame = new Frame()
            {
                Margin = 5,
                Content = verticalScrollView
            };

            Content = outerFrame;
            InitializeComponent();
        }
    }
}
