//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using Foundation;
using UIKit;

using MindFusion.Scheduling.iOS;


namespace Effects
{
	[Register("AppDelegate")]
	public partial class AppDelegate : global::Xamarin.Forms.Platform.iOS.FormsApplicationDelegate
	{
		public override bool FinishedLaunching(UIApplication uiApplication, NSDictionary launchOptions)
		{
			var window = new UIWindow(UIScreen.MainScreen.Bounds);

			// Make the window visible
			window.RootViewController = new UIViewController();
			window.MakeKeyAndVisible();

			// Make sure the assembly is loaded...
			var notUsed = new CalendarRenderer();

			global::Xamarin.Forms.Forms.Init();
			LoadApplication(new App());

			return base.FinishedLaunching(uiApplication, launchOptions);
		}

		public override void OnResignActivation(UIApplication uiApplication)
		{
			// Invoked when the application is about to move from active to inactive state.
			// This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) 
			// or when the user quits the application and it begins the transition to the background state.
			// Games should use this method to pause the game.
		}

		public override void DidEnterBackground(UIApplication uiApplication)
		{
			// Use this method to release shared resources, save user data, invalidate timers and store the application state.
			// If your application supports background exection this method is called instead of WillTerminate when the user quits.
		}

		public override void WillEnterForeground(UIApplication uiApplication)
		{
			// Called as part of the transiton from background to active state.
			// Here you can undo many of the changes made on entering the background.
		}

		public override void OnActivated(UIApplication uiApplication)
		{
			// Restart any tasks that were paused (or not yet started) while the application was inactive. 
			// If the application was previously in the background, optionally refresh the user interface.
		}

		public override void WillTerminate(UIApplication uiApplication)
		{
			// Called when the application is about to terminate. Save data, if needed. See also DidEnterBackground.
		}
	}
}