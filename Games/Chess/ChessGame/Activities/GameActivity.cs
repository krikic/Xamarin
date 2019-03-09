using Android.App;
using Android.OS;
using Android.Widget;

namespace ChessGame.Activities
{
    [Activity(Label = "Game")]
    public class GameActivity : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            SetContentView(Resource.Layout.Game);

            var undoBtn = FindViewById<Button>(Resource.Id.GameUndo);
            var menuBtn = FindViewById<Button>(Resource.Id.GameMenu);

            undoBtn.Click += (sender, e) => Toast.MakeText(this, Resource.String.GameUndoDone, ToastLength.Short).Show();
            menuBtn.Click += (sender, e) => Finish();
        }
    }
}