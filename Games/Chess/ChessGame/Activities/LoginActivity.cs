using Android.App;
using Android.Content.Res;
using Android.OS;
using Android.Widget;
using ChessGame.Services;
using ChessGame.Util;

namespace ChessGame.Activities
{
    [Activity(Label = "Login")]
    public class LoginActivity : Activity
    {
        private TextView ErrorField { get; set; }
        private EditText UsernameField { get; set; }
        private EditText PasswordField { get; set; }
        private Button LoginButton { get; set; }
        private Button RegisterButton { get; set; }

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            SetContentView(Resource.Layout.Login);

            ErrorField = FindViewById<TextView>(Resource.Id.TxtViewLoginErrors);
            UsernameField = FindViewById<EditText>(Resource.Id.EditTxtUsername);
            PasswordField = FindViewById<EditText>(Resource.Id.EditTxtPassword);
            LoginButton = FindViewById<Button>(Resource.Id.BtnLogin);
            RegisterButton = FindViewById<Button>(Resource.Id.BtnRegister);

            LoginButton.Click += (sender, e) => TryLogin();

            RegisterButton.Click += (sender, e) => StartActivity(typeof(RegisterActivity));
        }

        private void TryLogin()
        {
            this.ClearFieldError(UsernameField);
            this.ClearFieldError(PasswordField);

            var username = UsernameField.Text;
            var password = PasswordField.Text;

            if (string.IsNullOrWhiteSpace(username) ||
                string.IsNullOrEmpty(password))
            {
                ErrorField.Text = Resources.GetText(Resource.String.LoginErrorRequired);
                if (string.IsNullOrWhiteSpace(username))
                {
                    UsernameField.Text = "";
                    this.MakeFieldError(UsernameField);
                }
                if (string.IsNullOrEmpty(password))
                {
                    this.MakeFieldError(PasswordField);
                }
                return;
            }

            var service = ServiceProvider.GetService<AuthenticationService>();
            var user = service.LogIn(username, password);
            if (user == null)
            {
                ErrorField.Text = Resources.GetText(Resource.String.LoginErrorWrong);
                this.MakeFieldError(UsernameField);
                PasswordField.Text = "";
                this.MakeFieldError(PasswordField);
                return;
            }

            StartActivity(typeof(MenuActivity));
            Finish();
        }
    }
}