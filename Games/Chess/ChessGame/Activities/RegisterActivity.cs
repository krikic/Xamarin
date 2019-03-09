using Android.App;
using Android.OS;
using Android.Widget;
using ChessGame.Util;
using ChessGame.Services;
using ChessGame.Entities;

namespace ChessGame.Activities
{
    [Activity(Label = "Register")]
    public class RegisterActivity : Activity
    {
        private TextView ErrorField { get; set; }
        private EditText NameField { get; set; }
        private EditText UsernameField { get; set; }
        private EditText PasswordField { get; set; }
        private Button ConfirmButton { get; set; }
        private Button BackButton { get; set; }

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            SetContentView(Resource.Layout.Register);

            ErrorField = FindViewById<TextView>(Resource.Id.RegisterErrors);
            NameField = FindViewById<EditText>(Resource.Id.RegisterName);
            UsernameField = FindViewById<EditText>(Resource.Id.RegisterUsername);
            PasswordField = FindViewById<EditText>(Resource.Id.RegisterPassword);
            ConfirmButton = FindViewById<Button>(Resource.Id.RegisterConfirm);
            BackButton = FindViewById<Button>(Resource.Id.RegisterBack);

            ConfirmButton.Click += (sender, e) => TryRegister();
            BackButton.Click += (sender, e) => Finish();
        }

        private void TryRegister()
        {
            var name = NameField.Text;
            var username = UsernameField.Text;
            var password = PasswordField.Text;

            this.ClearFieldError(NameField);
            this.ClearFieldError(UsernameField);
            this.ClearFieldError(PasswordField);
            ErrorField.Text = "";

            if (string.IsNullOrWhiteSpace(name))
            {
                ErrorField.Text = Resources.GetText(Resource.String.LoginErrorRequired);
                NameField.Text = "";
                this.MakeFieldError(NameField);
            }

            if (string.IsNullOrWhiteSpace(username))
            {
                ErrorField.Text = Resources.GetText(Resource.String.LoginErrorRequired);
                UsernameField.Text = "";
                this.MakeFieldError(UsernameField);
            }

            if (string.IsNullOrEmpty(password))
            {
                ErrorField.Text = Resources.GetText(Resource.String.LoginErrorRequired);
                this.MakeFieldError(PasswordField);
            }

            if (!string.IsNullOrEmpty(ErrorField.Text)) return;

            var authService = ServiceProvider.GetService<AuthenticationService>();
            var user = authService.GetUserByUsername(username);

            if (user != null)
            {
                ErrorField.Text = Resources.GetText(Resource.String.RegisterErrorExists);
                this.MakeFieldError(UsernameField);
                return;
            }

            authService.Register(new User
            {
                Name = name,
                Username = username,
                Password = password
            });

            Finish();
        }
    }
}