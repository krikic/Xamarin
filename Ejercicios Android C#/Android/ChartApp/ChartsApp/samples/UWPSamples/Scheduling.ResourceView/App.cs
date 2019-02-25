//
// Copyright (c) 2017, MindFusion LLC - Bulgaria.
//

using Xamarin.Forms;


namespace ResourceView
{
	public class XamarinApp : Xamarin.Forms.Application
	{
		public XamarinApp()
		{
			// The root page of your application
			MainPage = new NavigationPage(new TestPage());
		}

		protected override void OnStart ()
		{
			// Handle when your app starts
		}

		protected override void OnSleep ()
		{
			// Handle when your app sleeps
		}

		protected override void OnResume ()
		{
			// Handle when your app resumes
		}
	}
}