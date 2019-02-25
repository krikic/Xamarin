//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using System.Collections.Generic;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Scheduling;


namespace CustomDrawElements
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			var options = new List<string>();
			var values = Enum.GetValues(typeof(MindFusion.Scheduling.CustomDrawElements));
			foreach (MindFusion.Scheduling.CustomDrawElements value in values)
			{
				if (value == MindFusion.Scheduling.CustomDrawElements.None)
					continue;
				options.Add(value.ToString());
			}

			optionList.ItemsSource = options.ToArray();
			optionList.RowHeight = 24;
			optionList.ItemSelected += OnItemSelected;

			calendar.MonthSettings.WeekHeaderAlignment = Alignment.Near;
			calendar.ListViewSettings.CellUnits = TimeUnit.Week;
			calendar.ListViewSettings.HeaderStyle |= ListViewHeaderStyles.Subheader |
				ListViewHeaderStyles.MainHeader | ListViewHeaderStyles.Footer;
			calendar.ListViewSettings.MainHeaderSize = 32;
			calendar.ListViewSettings.FooterSize = 32;
			calendar.ListViewSettings.Orientation = Orientation.Horizontal;
			calendar.ListViewSettings.RotateHeaderTexts = State.Disabled;
			calendar.TimetableSettings.ShowInfoHeader = State.Enabled;
			calendar.TimetableSettings.VisibleColumns = 3;
			calendar.Draw += OnDraw;

			// Add some resources
			var r1 = new MindFusion.Scheduling.Resource();
			r1.Name = "Resource #1";
			calendar.Resources.Add(r1);

			var r2 = new MindFusion.Scheduling.Resource();
			r2.Name = "Resource #2";
			calendar.Resources.Add(r2);

			calendar.GroupType = GroupType.GroupByResources;

			// Add some items
			Appointment a;

			a = new Appointment();
			a.StartTime = DateTime.Today;
			a.EndTime = DateTime.Today.AddDays(3);
			a.HeaderText = "Item #1";
			calendar.Schedule.Items.Add(a);

			a = new Appointment();
			a.StartTime = DateTime.Today.AddDays(2);
			a.EndTime = DateTime.Today.AddDays(4);
			a.HeaderText = "Item #2";
			calendar.Schedule.Items.Add(a);

			a = new Appointment();
			a.StartTime = DateTime.Today.AddHours(4);
			a.EndTime = DateTime.Today.AddHours(6.5);
			a.HeaderText = "Item #3";
			a.Resources.Add(r1);
			calendar.Schedule.Items.Add(a);

			a = new Appointment();
			a.StartTime = DateTime.Today.AddHours(6);
			a.EndTime = DateTime.Today.AddHours(10);
			a.HeaderText = "Item 43";
			a.Resources.Add(r1);
			calendar.Schedule.Items.Add(a);

			a = new Appointment();
			a.StartTime = DateTime.Today;
			a.EndTime = DateTime.Today.AddDays(1);
			a.HeaderText = "Item #5";
			a.Resources.Add(r2);
			calendar.Schedule.Items.Add(a);
		}

		void OnItemSelected(object sender, SelectedItemChangedEventArgs e)
		{
			MindFusion.Scheduling.CustomDrawElements selected;
			if (!Enum.TryParse(e.SelectedItem.ToString(), out selected))
				return;
			
			switch (selected)
			{

			case MindFusion.Scheduling.CustomDrawElements.CalendarItem:
			case MindFusion.Scheduling.CustomDrawElements.CellContents:
			case MindFusion.Scheduling.CustomDrawElements.CellHeader:
				// The current view does not change
				break;

			case MindFusion.Scheduling.CustomDrawElements.ListViewHeader:
			case MindFusion.Scheduling.CustomDrawElements.ListViewHeaderCell:
			case MindFusion.Scheduling.CustomDrawElements.ListViewSubHeader:
			case MindFusion.Scheduling.CustomDrawElements.ListViewSubHeaderCell:
				calendar.CurrentView = CalendarView.List;
				break;

			case MindFusion.Scheduling.CustomDrawElements.MonthContents:
			case MindFusion.Scheduling.CustomDrawElements.MonthMainHeader:
			case MindFusion.Scheduling.CustomDrawElements.MonthSubHeader:
			case MindFusion.Scheduling.CustomDrawElements.MonthSubHeaderCell:
			case MindFusion.Scheduling.CustomDrawElements.MonthWeekNumbersHeader:
			case MindFusion.Scheduling.CustomDrawElements.MonthWeekNumbersHeaderCell:
				calendar.CurrentView = CalendarView.Month;
				break;

			case MindFusion.Scheduling.CustomDrawElements.TimetableItem:
			case MindFusion.Scheduling.CustomDrawElements.TimetableCell:
			case MindFusion.Scheduling.CustomDrawElements.TimetableColumnHeader:
			case MindFusion.Scheduling.CustomDrawElements.TimetableGroupHeader:
			case MindFusion.Scheduling.CustomDrawElements.TimetableGroupHeaderCell:
			case MindFusion.Scheduling.CustomDrawElements.TimetableInfoHeader:
			case MindFusion.Scheduling.CustomDrawElements.TimetableTimeline:
			case MindFusion.Scheduling.CustomDrawElements.TimetableTimelineCell:
			case MindFusion.Scheduling.CustomDrawElements.TimetableTimelineHourCell:
			case MindFusion.Scheduling.CustomDrawElements.TimetableWholeDayHeader:
				calendar.CurrentView = CalendarView.Timetable;
				break;

			}

			calendar.CustomDraw = selected;
		}

		static void OnDraw(object sender, DrawEventArgs e)
		{
			var pen = new Pen(Colors.DarkGoldenrod);
			var brush = new SolidBrush(Color.FromRgba(Colors.Goldenrod.R,
				Colors.Goldenrod.G, Colors.Goldenrod.B, 0.3));

			e.Graphics.FillRectangle(brush, e.Bounds);
			e.Graphics.DrawRectangle(pen, e.Bounds);
		}
	}
}