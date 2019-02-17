using System;
namespace ControlLibrary.Models
{
    /// <summary>
    /// A generic item to represent something from somewhere. Could be anything !
    /// </summary>
    public class Item
    {
        public string Title { get; set; }
        public string Text { get; set; }
        public int Count { get; set; }
        public decimal Price { get; set; }

        public Item(string title, string text, int count, decimal price = 0)
        {
            Title = title;
            Text = text;
            Count = count;
            Price = price;
        }
    }
}
