using Android.App;
using Android.Widget;
using Android.OS;
using Android.Graphics;
using Android.Views;


namespace AnimatedRBGColors
{
    [Activity(Label = "AnimatedRBGColors", MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity
    {
        Button btnred, btngreen, btnblue;
        View mainLayout;
       

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);


            btnred = FindViewById<Button>(Resource.Id.button1);
            btngreen = FindViewById<Button>(Resource.Id.button2);
            btnblue = FindViewById<Button>(Resource.Id.button3);
            mainLayout =FindViewById<View>(Resource.Id.mainlayout);


            btnred.Click += (sender, e) =>
            {
                mainLayout.SetBackgroundColor(Color.Red);
            };

            btngreen.Click += (sender, e) =>
            {
                mainLayout.SetBackgroundColor(Color.Green);
            };

            btnblue.Click += (sender, e) =>
            {
                mainLayout.SetBackgroundColor(Color.Blue);
            };

        }
    }
}



            /*  @Override
              public boolean onCreateOptionsMenu(Menu menu)
          {
              // Inflate the menu; this adds items to the action bar if it is present.
              getMenuInflater().inflate(R.menu.main, menu);
              return true;
          }


              public boolean onOptionsItemSelected(MenuItem item)
          {
              // Handle action bar item clicks here. The action bar will
              // automatically handle clicks on the Home/Up button, so long
              // as you specify a parent activity in AndroidManifest.xml.
              int id = item.getItemId();
              if (id == R.id.action_settings)
              {
                  return true;
              }
              return super.onOptionsItemSelected(item);
          }
                  }
              }
              */
        



