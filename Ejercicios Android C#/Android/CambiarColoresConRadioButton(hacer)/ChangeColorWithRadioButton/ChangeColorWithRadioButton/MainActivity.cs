using Android.App;
using Android.Widget;
using Android.OS;
using Android.Views;


namespace ChangeColorWithRadioButton
{
    [Activity(Label = "ChangeColorWithRadioButton", MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity
    {
       

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);
            LinearLayout linearLayout = FindViewById<LinearLayout>(Resource.Id.linearLayout1);
            RadioGroup radioGroup = FindViewById<RadioGroup>(Resource.Id.radioGroup);

            RadioButton redRadioButton = FindViewById<RadioButton>(Resource.Id.redRadioButton);

            RadioButton greenRadioButton = FindViewById<RadioButton>(Resource.Id.redRadioButton);

            RadioButton blueRadioButton = FindViewById<RadioButton>(Resource.Id.blueRadioButton);

            redRadioButton.SetOnClickListener((Android.Views.View.IOnClickListener)this);
            greenRadioButton.SetOnClickListener((Android.Views.View.IOnClickListener)this);
            blueRadioButton.SetOnClickListener((Android.Views.View.IOnClickListener)this);

            redRadioButton.Click += delegate
            {
                linearLayout.SetBackgroundColor(Android.Graphics.Color.Red);

            };

           greenRadioButton.Click += delegate
            {
                linearLayout.SetBackgroundColor(Android.Graphics.Color.Green);

            };

            blueRadioButton.Click += delegate
            {
                linearLayout.SetBackgroundColor(Android.Graphics.Color.Blue);

            };
        }
    }
   

}

