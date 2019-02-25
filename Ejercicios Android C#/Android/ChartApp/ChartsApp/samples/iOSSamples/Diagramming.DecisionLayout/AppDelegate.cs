//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using Foundation;
using UIKit;

using MindFusion.Diagramming;


namespace DecisionLayout
{
	[Register ("AppDelegate")]
	public class AppDelegate : global::Xamarin.Forms.Platform.iOS.FormsApplicationDelegate
	{
		public override bool FinishedLaunching(UIApplication uiApplication, NSDictionary launchOptions)
		{
			var notUsed = new IOSDiagramRenderer();

			global::Xamarin.Forms.Forms.Init();

			LoadApplication(new App());

			return base.FinishedLaunching(uiApplication, launchOptions);
		}
	}
}