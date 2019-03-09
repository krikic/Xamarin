using Android.App;
using Android.OS;
using Android.Widget;
using ChessGame.Services;

namespace ChessGame.Activities
{
    [Activity(Label = "Menu")]
    public class MenuActivity : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            SetContentView(Resource.Layout.Menu);

            Button logoutButton = FindViewById<Button>(Resource.Id.MenuLogout);
            Button newGameButton = FindViewById<Button>(Resource.Id.MenuNewGame);
            Button continueGameButton = FindViewById<Button>(Resource.Id.MenuContinue);
            Button saveGameButton = FindViewById<Button>(Resource.Id.MenuSaveGame);

            newGameButton.Click += (sender, e) =>
            {
                StartActivity(typeof(GameActivity));
            };

            continueGameButton.Click += (sender, e) =>
            {
                StartActivity(typeof(GameActivity));
            };

            saveGameButton.Click += (sender, e) =>
            {
                Toast.MakeText(this, Resource.String.MenuSaveSuccess, ToastLength.Short).Show();
            };

            logoutButton.Click += (sender, e) =>
            {
                ServiceProvider.GetService<AuthenticationService>().LogOut();
                Finish();
            };
        }
    }
}