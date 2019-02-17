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
        UIKit.UIButton Calculate { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextView Result { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField sentence { get; set; }

        [Action ("Calcular:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void Calcular (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (Calculate != null) {
                Calculate.Dispose ();
                Calculate = null;
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