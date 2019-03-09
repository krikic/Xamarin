using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Java.IO;
using Android.Preferences;
using System.IO;
using Java.Lang;
using System.Diagnostics;

namespace Sudoku
{
	[Activity (Label = "Game")]			
	public class Game : Activity
	{

		private long startTime;
		private long time;
		private Handler timerHandler = new Handler();
		private IRunnable timerRunnable;

		class TimerRunnable :  Java.Lang.Object, Java.Lang.IRunnable{
			private Game container;

			public TimerRunnable (Game container)
			{
				this.container = container;
			}

			public void Run(){
				container.time = Java.Lang.JavaSystem.CurrentTimeMillis() - container.startTime;
				int seconds = (int) (container.time / 1000);
				int minutes = seconds / 60;
				seconds = seconds % 60;

				((TextView)container.FindViewById(Resource.Id.time_text_view)).Text = (Java.Lang.String.Format("%d:%02d",
					minutes, seconds));

				container.timerHandler.PostDelayed(this, 500);
			}
		}

		protected override void OnStart(){
			base.OnStart();
			startTime = Java.Lang.JavaSystem.CurrentTimeMillis();
			timerHandler.PostDelayed(timerRunnable, 0);
			ISharedPreferences sRef = PreferenceManager.GetDefaultSharedPreferences(this);
			sRef.RegisterOnSharedPreferenceChangeListener(new OptionsListener(this));
		}

		private void startGame(){
			ISharedPreferences sPref = PreferenceManager.GetDefaultSharedPreferences(this);
			string diff = sPref.GetString("difficultyPref", "");
			Stream ist = null;
			if (diff.Equals("Medium")){
				ist = Resources.OpenRawResource(Resource.Raw.medium);
			} else if (diff.Equals("Hard")){
				ist = Resources.OpenRawResource(Resource.Raw.hard);
			} else {
				ist = Resources.OpenRawResource(Resource.Raw.easy);
			}
			GameController.getInstance().setInitial(SudokuFileReader.getRandSudoku(ist));
			GameController.getInstance().clean();
			FindViewById (Resource.Id.game_field).Invalidate();
		}

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			timerRunnable = new TimerRunnable (this);
			SetContentView (Resource.Layout.Game);
			FindViewById(Resource.Id.button_0).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_1).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_2).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_3).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_4).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_5).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_6).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_7).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_7).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_8).Click += (sender, e) => {
				OnClick((View)sender);
			};
			FindViewById(Resource.Id.button_9).Click += (sender, e) => {
				OnClick((View)sender);
			};
			startGame();
		}

		public void OnClick(View v){
			int num = 0;
			switch (v.Id) {
			case Resource.Id.button_1:
				num = 1;
				break;
			case Resource.Id.button_2:
				num = 2;
				break;
			case Resource.Id.button_3:
				num = 3;
				break;
			case Resource.Id.button_4:
				num = 4;
				break;
			case Resource.Id.button_5:
				num = 5;
				break;
			case Resource.Id.button_6:
				num = 6;
				break;
			case Resource.Id.button_7:
				num = 7;
				break;
			case Resource.Id.button_8:
				num = 8;
				break;
			case Resource.Id.button_9:
				num = 9;
				break;
			}
			((SudokuFieldView)FindViewById(Resource.Id.game_field)).onDigitsPress(num);
			if (GameController.getInstance().isSolved()){
				timerHandler.RemoveCallbacks(timerRunnable);
				DatabaseController dbc = new DatabaseController();
				ISharedPreferences sPref = PreferenceManager.GetDefaultSharedPreferences(this);
				dbc.putRecord(time, sPref.GetString("difficultyPref", ""), ApplicationContext);
			}

		}

		public class OptionsListener : Java.Lang.Object, ISharedPreferencesOnSharedPreferenceChangeListener{
			private Game container;

			public OptionsListener (Game container)
			{
				this.container = container;
			}
			

			public void OnSharedPreferenceChanged(ISharedPreferences sharedPreferences, string s) {
				container.startGame();
			}
		}
	}
}

