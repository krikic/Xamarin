using Android.App;
using Android.Widget;
using Android.Graphics;

namespace ChessGame.Util
{
    public static class ActivityUtils
    {
        public static void MakeFieldError(this Activity activity, EditText field)
        {
            field.Background.SetColorFilter(activity.Resources.GetColor(Resource.Color.ErrorEditTxt), PorterDuff.Mode.SrcIn);
        }

        public static void ClearFieldError(this Activity activity, EditText field)
        {
            field.Background.ClearColorFilter();
        }
    }
}