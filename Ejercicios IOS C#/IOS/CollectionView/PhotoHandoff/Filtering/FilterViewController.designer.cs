// WARNING
//
// This file has been generated automatically by Visual Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;

namespace PhotoHandoff
{
    [Register ("FilterViewController")]
    partial class FilterViewController
    {
        void ReleaseDesignerOutlets ()
        {
            if (activeSwitch != null) {
                activeSwitch.Dispose ();
                activeSwitch = null;
            }

            if (activityIndicator != null) {
                activityIndicator.Dispose ();
                activityIndicator = null;
            }

            if (navigationBar != null) {
                navigationBar.Dispose ();
                navigationBar = null;
            }

            if (slider != null) {
                slider.Dispose ();
                slider = null;
            }
        }
    }
}