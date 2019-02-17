using System;
using System.Collections.Generic;
using ControlLibrary.Models;
using Xamarin.Forms;

namespace ControlLibrary.Prototypes
{
    /// <summary>
    /// Horizontal scrolling grid made to accomodate a dynamic list of items.
    /// Cells are created for each item in the list. The cells are populated with a content view each 
    /// that presents data in the items. 
    /// </summary>
    public partial class ScrollingGridView : ContentView
    {

        public ScrollingGridView(List<Item> items)
        {
            InitializeComponent();
            AddItemsToGrid(items);

        }

        void AddItemsToGrid(List<Item> items)
        {
            int columns = items.Count;

            for (int i = 0; i < items.Count; i++)
            {
                gridView.ColumnDefinitions.Add(new ColumnDefinition());

                var view = new ContentView
                {
                    Content = new ItemsView(items[i])
                };

                gridView.Children.Add(view, i, 0);
            }

        }


    }
}
