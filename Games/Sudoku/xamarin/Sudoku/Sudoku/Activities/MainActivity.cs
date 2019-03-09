using System;
using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Android.Preferences;

namespace Sudoku
{
	[Activity (Label = "Sudoku", MainLauncher = true)]
	public class MainActivity : Activity
	{
		protected override void OnStart(){
			base.OnStart();
			PreferenceManager.SetDefaultValues(this, Resource.Xml.Preferences , false);
		}

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			SetContentView (Resource.Layout.Main);
			FindViewById(Resource.Id.new_game_button).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.options_button).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.statistics_button).Click += (sender, e) => {
				OnClick((View)sender);
			};
		}

		public void OnClick(View v){
			Intent intent = null;
			switch (v.Id) {
			case Resource.Id.new_game_button:
				intent = new Intent (this, typeof(Game));
				break;
			case Resource.Id.options_button:
				intent = new Intent (this, typeof(Options));
				break;
			case Resource.Id.statistics_button:
				intent = new Intent (this, typeof(Statistics));
				break;
			}
			if (intent != null) {
				StartActivity (intent);
			}
				
		}
	}
}


