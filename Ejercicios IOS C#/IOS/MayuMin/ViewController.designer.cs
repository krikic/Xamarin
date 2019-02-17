// WARNING
//
// This file has been generated automatically by Xamarin Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;
using UIKit;

namespace TipCalculator
{
    [Register ("ViewController")]
    partial class ViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton Mayus { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton Minus { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel Result { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField sentence { get; set; }

        [Action ("Mayusculas:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void Mayusculas (UIKit.UIButton sender);

        [Action ("Minusculas:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void Minusculas (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (Mayus != null) {
                Mayus.Dispose ();
                Mayus = null;
            }

            if (Minus != null) {
                Minus.Dispose ();
                Minus = null;
            }

            if (Result != null) {
                Result.Dispose ();
                Result = null;
            }

            if (sentence != null) {
                sentence.Dispose ();
                sentence = null;
            }
        }
    }
}