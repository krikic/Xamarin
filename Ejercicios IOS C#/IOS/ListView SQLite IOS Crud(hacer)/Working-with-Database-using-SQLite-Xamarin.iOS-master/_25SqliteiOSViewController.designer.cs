// WARNING
//
// This file has been generated automatically by Visual Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;
using UIKit;

namespace _25SqliteiOS
{
    [Register ("_25SqliteiOSViewController")]
    partial class _25SqliteiOSViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btn_Clear { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btn_Createdb { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btn_display { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btn_insertdb { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btn_Update { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextView txt_Display { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txt_Fname { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txt_Lname { get; set; }

        [Action ("btn_Clear_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void btn_Clear_TouchUpInside (UIKit.UIButton sender);

        [Action ("btn_Createdb_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void btn_Createdb_TouchUpInside (UIKit.UIButton sender);

        [Action ("btn_display_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void btn_display_TouchUpInside (UIKit.UIButton sender);

        [Action ("btn_insertdb_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void btn_insertdb_TouchUpInside (UIKit.UIButton sender);

        [Action ("btn_Update_TouchUpInside:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void btn_Update_TouchUpInside (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (btn_Clear != null) {
                btn_Clear.Dispose ();
                btn_Clear = null;
            }

            if (btn_Createdb != null) {
                btn_Createdb.Dispose ();
                btn_Createdb = null;
            }

            if (btn_display != null) {
                btn_display.Dispose ();
                btn_display = null;
            }

            if (btn_insertdb != null) {
                btn_insertdb.Dispose ();
                btn_insertdb = null;
            }

            if (btn_Update != null) {
                btn_Update.Dispose ();
                btn_Update = null;
            }

            if (txt_Display != null) {
                txt_Display.Dispose ();
                txt_Display = null;
            }

            if (txt_Fname != null) {
                txt_Fname.Dispose ();
                txt_Fname = null;
            }

            if (txt_Lname != null) {
                txt_Lname.Dispose ();
                txt_Lname = null;
            }
        }
    }
}