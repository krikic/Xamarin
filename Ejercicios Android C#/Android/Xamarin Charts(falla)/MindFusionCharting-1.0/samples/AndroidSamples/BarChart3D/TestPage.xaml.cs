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

            barChart.Title = "Agricultural produce by type";

            // create sample data
            barChart.Series = GetSeriesCollection();

            // set one color per series
            barChart.Theme.CommonSeriesFills = barChart.Theme.CommonSeriesStrokes = new List<Brush> {
                Brushes.ForestGreen,
                Brushes.LemonChiffon,
                Brushes.LawnGreen
            };

            // background
            barChart.BackgroundColor = Colors.WhiteSmoke;

            // axis appearance
            barChart.XAxis.Title = "";
            barChart.YAxis.Title = "Quantity in tons";
            barChart.Theme.AxisStroke = barChart.Theme.AxisLabelsBrush = Brushes.DimGray;
            barChart.Theme.AxisStrokeThickness = 1;

            // legend appearance
            barChart.LegendTitle = "Agricultural type";
            barChart.Theme.LegendBackground = Brushes.Wheat;
            barChart.Theme.LegendBorderStroke = Brushes.SandyBrown;
            barChart.Theme.LegendBorderStrokeThickness = 1;
            barChart.LegendMargin = new Margins(80, 5, 5, 5);

            barChart.LayoutPanel.Margin = new Margins(10);

            SetupControls();
        }

        void SetupControls()
        {
            cbxBarLayout.ItemsSource = new[] {
                BarLayout.Overlay,
                BarLayout.SideBySide,
                BarLayout.Stack };
            cbxBarLayout.RowHeight = 32;
            cbxBarLayout.SelectedItem = barChart.BarLayout;
            /*cbShowScatter.Checked = barChart.ShowScatter;
            cbImage.Checked = true;
            cbShowX.Checked = barChart.ShowXCoordinates;
            cbShowY.Checked = barChart.ShowYCoordinates;
            cbShowXTicks.Checked = barChart.ShowXTicks;
            cbShowYTicks.Checked = barChart.ShowYTicks;
            cbAllowPan.Checked = barChart.AllowPan;
            cbMoveLegend.Checked = barChart.AllowMoveLegend;
            cbHorAlign.SelectedIndex = (int)barChart.LegendHorizontalAlignment;
            cbVertAlign.SelectedIndex = (int)barChart.LegendVerticalAlignment;
            cbZoom.Checked = barChart.AllowZoom;
            tbMarginLeft.Minimum = 0;
            tbMarginLeft.Maximum = barChart.Width - 100;
            tbMarginLeft.Value = (int)barChart.LegendMargin.Left;
            tbMarginTop.Minimum = 0;
            tbMarginTop.Maximum = barChart.Height - 100;
            tbMarginTop.Value = (int)barChart.LegendMargin.Top;*/
        }

        ObservableCollection<Series> GetSeriesCollection()
        {
            var labels = new [] { "Tomatoes", "Cucumbers", "Peppers", "Lettuce" };
            var collection = new ObservableCollection<Series>();
            for (int i = 0; i < 3; i++)
            {
                if (i == 0)
                {
                    collection.Add(new Series2D(new double[] { 10, 20, 30, 40 },
                        new double[] { 50, 40, 50, 5 },
                        labels)
                    { Title = "Traditional" });
                }
                if (i == 1)
                {
                    collection.Add(new Series2D(new double[] { 10, 20, 30, 40 },
                       new double[] { 60, 10, 20, 80 },
                       labels)
                    { Title = "Urban" });
                }
                if (i == 2)
                {
                    collection.Add(new Series2D(new double[] { 10, 20, 30, 40 },
                       new double[] { 0, 60, 0, 90 },
                       labels)
                    { Title = "Hydroponics" });
                }
            }
            return collection;
        }

        void cbxBarLayout_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            barChart.BarLayout = (BarLayout)cbxBarLayout.SelectedItem;
        }
	}
}