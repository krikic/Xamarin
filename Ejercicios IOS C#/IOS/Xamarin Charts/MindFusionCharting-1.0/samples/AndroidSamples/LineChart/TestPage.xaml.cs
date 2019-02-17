//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System.Collections.Generic;
using Xamarin.Forms;

using MindFusion.Charting;
using MindFusion.Drawing;
using System;

namespace LineChart
{
    public partial class TestPage : ContentPage
    {
        public TestPage()
        {
            InitializeComponent();

            // create line brushes
            firstBrush = Brushes.SkyBlue;
            secondBrush = Brushes.DeepPink;
            thirdBrush = Brushes.Green;

            lineChart.LegendRenderer.Background = Brushes.Khaki;
            lineChart.BackgroundColor = Colors.WhiteSmoke;

            // create sample data series
            lineChart.Series.Add(
                new Series2D(
                    new List<double> { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 },
                    new List<double> { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 },
                    labels
                )
                { Title = "Series 1" });

            lineChart.Series.Add(
                new Series2D(
                    new List<double> { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 },
                    new List<double> { 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24 },
                    labels
                )
                { Title = "Series 2" });

            lineChart.Series.Add(
                new FunctionSeries(x => x * Math.Sin(x) + x + 5, 36, 12)
                { Title = "Series 3" });

            lineChart.XAxis.Interval = 1;

            // assign one brush per series
            lineChart.Plot.SeriesStyle = new MixedSeriesStyle
            {
                CommonFills = new List<Brush>
                {
                    firstBrush, secondBrush, thirdBrush
                },
                CommonStrokes = new List<Brush>
                {
                    firstBrush, secondBrush, thirdBrush
                },
                UniformStrokeThickness = 5
            };

            // set up grid type combo box
            cbxGridType.ItemsSource = new[] { GridType.Crossed,
                GridType.Horizontal,
                GridType.None,
                GridType.Vertical };
            cbxGridType.RowHeight = 24;
            cbxGridType.SelectedItem = GridType.Crossed;
            lineChart.GridType = GridType.Crossed;

            // set up line type combo box
            cbxLineType.ItemsSource = new[] { LineType.Polyline,
                LineType.Step,
                LineType.Curve };
            cbxLineType.RowHeight = 24;
            cbxLineType.SelectedItem = LineType.Polyline;
            lineChart.LineType = LineType.Polyline;
        }

        void cbxLineType_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            lineChart.LineType = (LineType)cbxLineType.SelectedItem;
        }

        void cbxGridType_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            lineChart.GridType = (GridType)cbxGridType.SelectedItem;
        }


        List<string> labels = new List<string>
        {
            "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve"
        };

        Brush firstBrush;
        Brush secondBrush;
        Brush thirdBrush;
	}
}