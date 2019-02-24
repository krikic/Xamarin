using Android.App;
using Android.Widget;
using Android.OS;
using Android.Content;
using Android.Graphics;
using Android.Support.V7.App;
using Android.Util;
using Android.Views;
using Android.Webkit;

namespace RGBColor
{
    [Activity(Label = "RGBColor", MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity
    {
        int count = 1;
        SeekBar skRed;
        SeekBar skGreen;
        SeekBar skBlue;

     

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);



            skRed = FindViewById<SeekBar>(Resource.Id.redBar);
            skGreen = FindViewById<SeekBar>(Resource.Id.greenBar);
            skBlue = FindViewById<SeekBar>(Resource.Id.blueBar);

            WebView myView = FindViewById<WebView>(Resource.Id.customView);

            Button colorChange = FindViewById<Button>(Resource.Id.myButton);



            colorChange.setOnClickListener(new View.OnClickListener() {
         
            public void onClick(View v)
            {




                myView.setDrawingCacheEnabled(true);

                myView.buildDrawingCache();


                // bm is your required bitmap
                Bitmap bm = myView.getDrawingCache();

                Toast.makeText(MainActivity.this, "Bitmap Ready", Toast.LENGTH_LONG).show();

            }
        });


        skRed.setOnSeekBarChangeListener(changeListener);
        skGreen.setOnSeekBarChangeListener(changeListener);
        skBlue.setOnSeekBarChangeListener(changeListener);
    }

    SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
        
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        myView.setColor(skRed.getProgress(), skGreen.getProgress(), skBlue.getProgress());
    }

  
        public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

 
        public void onStopTrackingTouch(SeekBar seekBar)
    {

    }
};


        }
    }
}

