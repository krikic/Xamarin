using System;

using Android.App;
using Android.Util;
using Android.Widget;
using Android.OS;
using Android.Views;
using System.Collections.Generic;
using Video.Droid.Model;

namespace Video.Droid
{
    [Activity (Label = "Video.Droid", MainLauncher = true, Icon = "@drawable/icon")]
	public class MainActivity : Activity
	{
        public List<Propaganda> Propagandas { get; set; }
        public VideoView vwVideo { get; set; }
        public int count = 0;
        public DisplayMetrics dm;
        public MediaController media_controller;

        //Sample: https://forums.xamarin.com/discussion/6671/example-code-about-playing-a-video-from-an-asset-with-videoview
        protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate(bundle);
            
            PopularPropagandas();
            
			SetContentView(Resource.Layout.Teste);
            vwVideo = FindViewById<VideoView>(Resource.Id.videos);
            media_controller = new MediaController(this);
            //Pegar informações da tela - Resources.DisplayMetrics
            dm = Resources.DisplayMetrics;
            vwVideo.SetMinimumWidth(dm.WidthPixels);
            vwVideo.SetMinimumHeight(dm.HeightPixels);
            vwVideo.SetMediaController(media_controller);
            vwVideo.Touch += PassarVideo;
            vwVideo.Completion += AoConcluirVideo;
            
            var uri = Android.Net.Uri.Parse("https://scontent-gru2-1.cdninstagram.com/t50.2886-16/12452786_1107179715983693_1797117636_n.mp4");
            vwVideo.SetVideoURI(uri);
            vwVideo.Start();
		}

        private void PopularPropagandas()
        {
            Propagandas = new List<Propaganda> {
                            new Propaganda {
                                Url = "https://scontent-gru2-1.cdninstagram.com/t50.2886-16/11765220_1614686585438231_194010058_n.mp4",
                            },
                            new Propaganda {
                                Url = "https://scontent-gru2-1.cdninstagram.com/t50.2886-16/12452786_1107179715983693_1797117636_n.mp4",
                            }
            };
        }

        private void PassarVideo(object sender, EventArgs e)
        {
            ProximoVideo();
        }

        private void AoConcluirVideo(object sender, EventArgs e)
        {
            ProximoVideo();
        }

        private void ProximoVideo()
        {
            if (count < Propagandas.Count)
            {
                var uriClick = Android.Net.Uri.Parse(Propagandas[count].Url);
                vwVideo.SetVideoURI(uriClick);
                vwVideo.Start();
                count++;
            }
            else
                count = 0;

        }
    }
}


