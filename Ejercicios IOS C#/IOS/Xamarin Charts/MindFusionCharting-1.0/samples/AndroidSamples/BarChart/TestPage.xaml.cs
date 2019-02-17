//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using Xamarin.Forms;

using MindFusion.Charting;
using MindFusion.Drawing;

namespace BarChart
{
    public partial class TestPage : ContentPage
    {
        public TestPage()
        {
            InitializeComponent();

            // create bar brushes
            firstBrush = new LinearGradientBrush(Colors.LightGreen, Colors.LightBlue, 0);
            secondBrush = new LinearGradientBrush(Colors.Yellow, Colors.Red, 0);
            thirdBrush = new SolidBrush(Colors.Khaki);

            barChart.BackgroundColor = Colors.White;

            // create sample data series
            barChart.Series = new ObservableCollection<Series>
            {
                new BarSeries(
                    new List<double> { 1,2,3,4,5,6,7,8,9,10,11,12 },
                    labels, null
                )
                { Title = "Series 1" },

                new BarSeries(
                    new List<double> { 2,4,6,8,10,12,14,16,18,20,22,24 },
                    labels, null
                )
                { Title = "Series 2" },

                new BarSeries(
                    new List<double> { 2,8,13,15,13,8,2,8,13,15,13,8 },
                    labels, null
                )
                { Title = "Series 3" }
            };
            barChart.XAxis.Interval = 1;

            // assign one brush per series
            barChart.Plot.SeriesStyle = new PerSeriesStyle()
            {
                Fills = new List<MindFusion.Drawing.Brush>()
                {
                    firstBrush, secondBrush, thirdBrush
                }
            };

            // set up bar layout combo box
            cbxBarLayout.ItemsSource = new[] {
                BarLayout.Overlay,
                BarLayout.SideBySide,
                BarLayout.Stack };
            cbxBarLayout.RowHeight = 32;
            cbxBarLayout.SelectedItem = BarLayout.SideBySide;

            // set up grid type combo box
            /*cbxGridType.Items.Add(GridType.Crossed);
            cbxGridType.Items.Add(GridType.Horizontal);
            cbxGridType.Items.Add(GridType.None);
            cbxGridType.Items.Add(GridType.Vertical);
            cbxGridType.SelectedIndex = 2;*/
        }

        void cbxBarLayout_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            barChart.BarLayout = (BarLayout)cbxBarLayout.SelectedItem;
        }

        void chbHorizontalBars_Toggled(object sender, ToggledEventArgs e)
        {
            barChart.HorizontalBars = !barChart.HorizontalBars;
            firstBrush.Angle += (angle * 90);
            secondBrush.Angle += (angle * 90);
            angle *= -1;
            barChart.Invalidate();
        }

        LinearGradientBrush firstBrush;
        LinearGradientBrush secondBrush;
        SolidBrush thirdBrush;

        int angle = 1;

        List<string> labels = new List<string>()
        {
            "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve"
        };
	}
}