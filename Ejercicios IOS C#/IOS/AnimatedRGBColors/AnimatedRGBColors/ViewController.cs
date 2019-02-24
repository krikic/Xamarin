using System;

using UIKit;

namespace AnimatedRGBColors
{
    public partial class ViewController : UIViewController
    {
        protected ViewController(IntPtr handle) : base(handle) { }
       



        public override void ViewDidLoad()
        {
            base.ViewDidLoad();
            // Perform any additional setup after loading the view, typically from a nib.
        }

        partial void BtnRed_TouchUpInside(UIButton sender)
        {
            View.BackgroundColor = UIColor.Red;
        }

        partial void BtnGreen_TouchUpInside(UIButton sender)
        {
            View.BackgroundColor = UIColor.Green;
        }

        partial void BtnBlue_TouchUpInside(UIButton sender)
        {
            View.BackgroundColor = UIColor.Blue;
        }


        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
            // Release any cached data, images, etc that aren't in use.
        }



       
    }
}
