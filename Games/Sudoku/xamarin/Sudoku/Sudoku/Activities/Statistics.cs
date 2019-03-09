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

namespace Sudoku
{
	[Activity (Label = "Statistics")]			
	public class Statistics : Activity
	{
		protected override void OnResume() {
			base.OnResume();
			updateRecords();
		}
		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			SetContentView (Resource.Layout.Statistics);
			FindViewById(Resource.Id.clear_statistics).Click += (sender, e) => {
				onClear();
			};
			updateRecords ();
		}

		public void onClear(){
			new DatabaseController().clearDB(ApplicationContext);
			updateRecords();
		}

		private void updateRecords(){
			RecordInfo easy = new DatabaseController().getRecordsInfo("Easy", ApplicationContext);
			RecordInfo medium = new DatabaseController().getRecordsInfo("Medium", ApplicationContext);
			RecordInfo hard = new DatabaseController().getRecordsInfo("Hard", ApplicationContext);

			((TextView)FindViewById(Resource.Id.text_easy)).Text = easy.ToString();
			((TextView)FindViewById(Resource.Id.text_medium)).Text = (medium.ToString());
			((TextView)FindViewById(Resource.Id.text_hard)).Text = (hard.ToString());
		}
	}
}

