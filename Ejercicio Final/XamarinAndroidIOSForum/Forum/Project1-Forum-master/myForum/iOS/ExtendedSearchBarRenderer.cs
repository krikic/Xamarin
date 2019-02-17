using System;

using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using UIKit;
using System.Diagnostics;

[assembly: ExportRenderer(typeof(SearchBar), typeof(myForum.iOS.Renderers.ExtendedSearchBarRenderer))]
namespace myForum.iOS.Renderers
{
    public class ExtendedSearchBarRenderer : SearchBarRenderer
    {
		protected override void OnElementPropertyChanged(object sender, System.ComponentModel.PropertyChangedEventArgs e)
		{
			base.OnElementPropertyChanged(sender, e);

			if (e.PropertyName == "Text")
			{
				Control.ShowsCancelButton = false;
			}
		}
    }
}

