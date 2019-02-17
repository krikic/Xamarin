using Android.App;
using Android.Content;
using Android.OS;
using Android.Views;
using Android.Widget;
using System;

namespace CarouselImagenes
{
    [Activity(Label = "CarouselImagenes", MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity
    {
        ImageView imageView;
        int index;
        int[] images;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Main);
            LinearLayout container = FindViewById<LinearLayout>(Resource.Id.container);
            // To instantiate your image array
            loadImages();
            imageView = FindViewById<ImageView>(Resource.Id.imageView);
            index = 0;
            // Set the first image to imageview
            imageView.SetBackgroundResource(images[0]);
            setSlideShow(); // Make the timer to change image
            hideNavigationBar();// To hide navigation bar
        }
        void loadImages()
        {
            images = new int[]{
Resource.Drawable.img1,//index= 0
Resource.Drawable.img2,//index= 1
Resource.Drawable.img3//index= 2
};//images.length=3;
        }
        void setSlideShow()
        {
            System.Timers.Timer timer = new System.Timers.Timer();
            timer.Interval = 4000; // Interval to change image (4000 = 4 second per image)
            timer.Elapsed += (sender, e) => {
                showNextImage();
            };
            timer.Start();
            timer.AutoReset = true;
        }

     

        void showNextImage()
        {
            index++;
            if (index == images.Length) { index = 0; }
            RunOnUiThread(() => imageView.SetBackgroundResource(images[index]));
        }
        void hideNavigationBar()
        {
            View decorView = Window.DecorView;
            var uiOptions = (int)decorView.SystemUiVisibility;
            var newUiOptions = uiOptions;
            newUiOptions |= (int)SystemUiFlags.HideNavigation;
            decorView.SystemUiVisibility = (StatusBarVisibility)newUiOptions;
        }
    }

    }


