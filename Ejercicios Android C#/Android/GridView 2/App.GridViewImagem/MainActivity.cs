using Android.App;
using Android.OS;
using Android.Widget;
using System.Collections.Generic;

namespace App.GridViewImagem
{
    [Activity(Label = "App.GridViewImagem", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {
        GridView gv;
        private BandeiraAdapter adapter;
        private List<Bandeira> bandeiras;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            gv = FindViewById<GridView>(Resource.Id.gvDados);
            adapter = new BandeiraAdapter(GetBandeiras(), this);
            gv.Adapter = adapter;

            gv.ItemClick += Gv_ItemClick;

        }

        private void Gv_ItemClick(object sender, AdapterView.ItemClickEventArgs e)
        {
            Toast.MakeText(this, bandeiras[e.Position].Nome, ToastLength.Short).Show();
        }

        private List<Bandeira> GetBandeiras()
        {
            bandeiras = new List<Bandeira>();

            Bandeira band;

            band = new Bandeira("Brasil", Resource.Drawable.brasil);
            bandeiras.Add(band);

            band = new Bandeira("França", Resource.Drawable.franca);
            bandeiras.Add(band);

            band = new Bandeira("India", Resource.Drawable.India);
            bandeiras.Add(band);

            band = new Bandeira("Itália", Resource.Drawable.italian);
            bandeiras.Add(band);

            band = new Bandeira("Japão", Resource.Drawable.japao);
            bandeiras.Add(band);

            band = new Bandeira("México", Resource.Drawable.mexico);
            bandeiras.Add(band);

            band = new Bandeira("USA", Resource.Drawable.usa1);
            bandeiras.Add(band);

            return bandeiras;

        }
    }
}

