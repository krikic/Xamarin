using Android.App;
using Android.Widget;
using Android.OS;
using ChessGame.Services;

namespace ChessGame.Activities
{
    [Activity(Label = "Chess", MainLauncher = true)]
    public class MainActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            SetContentView(Resource.Layout.Main);

            Button button = FindViewById<Button>(Resource.Id.LandingBtn);

            button.Click += (sender, e) =>
            {
                Enter();
            };
        }

        private void Enter()
        {
            var authService = ServiceProvider.GetService<AuthenticationService>();

            if (authService.LoggedUser == null)
            {
                StartActivity(typeof(LoginActivity));
                return;
            }

            StartActivity(typeof(MenuActivity));
        }
    }
}
