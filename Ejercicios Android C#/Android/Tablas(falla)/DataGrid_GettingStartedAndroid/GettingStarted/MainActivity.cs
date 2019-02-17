using Android.App;
using Android.Widget;
using Android.OS;
using Syncfusion.SfDataGrid;
using Android.Views;

namespace GettingStarted
{
	[Activity (Label = "GettingStarted", MainLauncher = true, Icon = "@drawable/icon")]
	public class MainActivity : Activity
	{
		SfDataGrid sfGrid;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			SetContentView (Resource.Layout.Main);
			ActionBar.SetDisplayShowHomeEnabled (false);

			RelativeLayout layout = (RelativeLayout)FindViewById (Resource.Id.Relative);
			sfGrid = new SfDataGrid (BaseContext);
			sfGrid.ItemsSource = (new OrderInfoRepository ().OrderInfoCollection);
            sfGrid.AllowSorting = true;

            sfGrid.GroupColumnDescriptions.Add(new GroupColumnDescription() { ColumnName = "ShipCountry" }); 

			sfGrid.AutoGeneratingColumn += HandleAutoGeneratingColumn;
			layout.AddView (sfGrid);
		}

        void HandleAutoGeneratingColumn(object sender, AutoGeneratingColumnArgs e)
        {
            if (e.Column.MappingName == "CustomerID")
                e.Column.TextAlignment = GravityFlags.CenterVertical;
            else if (e.Column.MappingName == "CustomerName")
                e.Column.TextAlignment = GravityFlags.CenterVertical;
        }
	}
}


