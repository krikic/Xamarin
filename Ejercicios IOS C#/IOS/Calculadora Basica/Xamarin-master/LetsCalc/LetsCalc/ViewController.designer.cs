// WARNING
//
// This file has been generated automatically by Visual Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;

namespace LetsCalc
{
    [Register ("ViewController")]
    partial class ViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton addButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel answerLabel { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton DivideButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton MultiplyButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton SubtractButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField text1 { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField text2 { get; set; }

        [Action ("AddButton_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void AddButton_TouchUpInside (UIKit.UIButton sender);

        [Action ("DivideButton_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void DivideButton_TouchUpInside (UIKit.UIButton sender);

        [Action ("MultiplyButton_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void MultiplyButton_TouchUpInside (UIKit.UIButton sender);

        [Action ("SubtractButton_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void SubtractButton_TouchUpInside (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (addButton != null) {
                addButton.Dispose ();
                addButton = null;
            }

            if (answerLabel != null) {
                answerLabel.Dispose ();
                answerLabel = null;
            }

            if (DivideButton != null) {
                DivideButton.Dispose ();
                DivideButton = null;
            }

            if (MultiplyButton != null) {
                MultiplyButton.Dispose ();
                MultiplyButton = null;
            }

            if (SubtractButton != null) {
                SubtractButton.Dispose ();
                SubtractButton = null;
            }

            if (text1 != null) {
                text1.Dispose ();
                text1 = null;
            }

            if (text2 != null) {
                text2.Dispose ();
                text2 = null;
            }
        }
    }
}