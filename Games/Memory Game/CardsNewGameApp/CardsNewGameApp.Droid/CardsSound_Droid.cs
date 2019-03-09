using Android.Widget;
using Xamarin.Forms;

[assembly: Dependency(typeof(CardsNewGameApp.Droid.CardsSound_Droid))]
namespace CardsNewGameApp.Droid
{
    class CardsSound_Droid : ICardsSound
    {
        public void ShowToast(string message)
        {
            Toast.MakeText(Android.App.Application.Context, message, ToastLength.Long).Show();
        }

        public void Vibration(string file)
        {
            Android.Media.MediaPlayer _player;

            try
            {
                var player = new Android.Media.MediaPlayer();
                var fd = global::Android.App.Application.Context.Assets.OpenFd(file);
                player.Prepared += (s, e) =>
                {
                    player.Start();
                };
                player.SetDataSource(fd.FileDescriptor, fd.StartOffset, fd.Length);
                player.Looping = false;
                player.Prepare();
                //_player = Android.Media.MediaPlayer.Create(this,Resource.Raw.test);
                //_player.Start();
            }

            catch (System.Exception)
            {

            }

        }
    }
}