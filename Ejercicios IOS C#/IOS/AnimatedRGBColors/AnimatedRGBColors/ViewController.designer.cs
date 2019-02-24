// WARNING
//
// This file has been generated automatically by Visual Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;

namespace AnimatedRGBColors
{
    [Register ("ViewController")]
    partial class ViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton Btn_Green { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton BtnBlue { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton BtnRed { get; set; }

        [Action ("BtnBlue_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void BtnBlue_TouchUpInside (UIKit.UIButton sender);

        [Action ("BtnGreen_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void BtnGreen_TouchUpInside (UIKit.UIButton sender);

        [Action ("BtnRed_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void BtnRed_TouchUpInside (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (Btn_Green != null) {
                Btn_Green.Dispose ();
                Btn_Green = null;
            }

            if (BtnBlue != null) {
                BtnBlue.Dispose ();
                BtnBlue = null;
            }

            if (BtnRed != null) {
                BtnRed.Dispose ();
                BtnRed = null;
            }
        }
    }
}