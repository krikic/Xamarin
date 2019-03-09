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
using Android.Preferences;

namespace Sudoku
{
	[Activity (Label = "Options")]
	public class Options : Activity
	{
		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			FragmentManager.BeginTransaction()
				.Replace(Android.Resource.Id.Content, new OptionsFragment())
				.Commit();
		}

		class OptionsFragment : PreferenceFragment{
			public override void OnCreate(Bundle savedInstanceState) {
				base.OnCreate(savedInstanceState);
				AddPreferencesFromResource(Resource.Xml.Preferences);
			}
		}
	}
}

