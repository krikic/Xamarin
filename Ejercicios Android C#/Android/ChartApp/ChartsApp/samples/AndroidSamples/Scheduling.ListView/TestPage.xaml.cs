//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using System.ComponentModel;
using Xamarin.Forms;

using MindFusion.Scheduling;


namespace ListView
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			DateTime firstDate = DateTime.Today;
			while (firstDate.DayOfWeek != calendar.DateTimeFormat.FirstDayOfWeek)
				firstDate = firstDate.AddDays(-1);

			calendar.BeginInit();
			calendar.ListViewSettings.Orientation = Orientation.Horizontal;
			calendar.ListViewSettings.CellUnits = TimeUnit.Week;
			calendar.ListViewSettings.EnableSnapping = State.Enabled;
			calendar.ListViewSettings.SnapUnit = TimeUnit.Day;
			calendar.ListViewSettings.NumberOfCells = 4;
			calendar.ListViewSettings.Style.HeaderFont = calendar.ListViewSettings.Style.HeaderFont.Value.WithSize(14);
			calendar.ListViewSettings.CellSettings.HeaderPosition = Position.None;
			calendar.ListViewSettings.HeaderSize = 48;
			calendar.ListViewSettings.RotateHeaderTexts = State.Disabled;
			calendar.ItemSettings.Size = 40;

			// Initialize appointments
			Appointment app;

			app = new Appointment();
			app.StartTime = firstDate;
			app.EndTime = firstDate.AddDays(7);
			app.HeaderText = "Naruto #35\nM 10/27 - F 11/10";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate;
			app.EndTime = firstDate.AddDays(7);
			app.HeaderText = "Dragon Ball #10\nM 10/27 - F 11/10";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate;
			app.EndTime = firstDate.AddDays(7);
			app.HeaderText = "Hikaru No Go #38\nM 10/27 - F 11/10";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate;
			app.EndTime = firstDate.AddDays(7);
			app.HeaderText = "MAR #5\nM 10/27 - F 11/10";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate;
			app.EndTime = firstDate.AddDays(7);
			app.HeaderText = "Pokemnon: Johto...\nM 10/27 - F 11/10";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate;
			app.EndTime = firstDate.AddDays(7);
			app.HeaderText = "One Piece\nM 10/27 - F 11/10";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(7);
			app.EndTime = firstDate.AddDays(14);
			app.HeaderText = "Naruto #36\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(7);
			app.EndTime = firstDate.AddDays(14);
			app.HeaderText = "Dragon Ball #11\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(7);
			app.EndTime = firstDate.AddDays(14);
			app.HeaderText = "Hikaru No Go\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(7);
			app.EndTime = firstDate.AddDays(14);
			app.HeaderText = "MAR #39\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(7);
			app.EndTime = firstDate.AddDays(14);
			app.HeaderText = "Pokemon: Johto...\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(7);
			app.EndTime = firstDate.AddDays(14);
			app.HeaderText = "One Piece\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(7);
			app.EndTime = firstDate.AddDays(14);
			app.HeaderText = "Dragon Ball\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(14);
			app.EndTime = firstDate.AddDays(21);
			app.HeaderText = "Hikaru No Go\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(14);
			app.EndTime = firstDate.AddDays(21);
			app.HeaderText = "MAR\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(14);
			app.EndTime = firstDate.AddDays(21);
			app.HeaderText = "Pokemon: Johto...\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = firstDate.AddDays(14);
			app.EndTime = firstDate.AddDays(21);
			app.HeaderText = "One Piece\nM 11/6 - F 11/17";
			calendar.Schedule.Items.Add(app);

			calendar.EndInit();

			calendar.ItemCreated += (s, e) => {
				e.Item.EndTime = e.Item.StartTime.AddDays(7);
			};

			calendar.CustomizeText += (s, e) => {
				if (e.Element == CalendarTextElements.ListViewSubHeader)
				{
					e.Text = e.Date.ToString("ddd").Substring(0, 1);
				}
			};
		}

		void OnFreeDragChanged(object sender, PropertyChangedEventArgs e)
		{
			if (e.PropertyName == "IsToggled")
			{
				if (freeDragSwitch.IsToggled)
					calendar.ListViewSettings.FreeDrag = State.Enabled;
				else
					calendar.ListViewSettings.FreeDrag = State.Disabled;
			}
		}

		void OnSubheadersChanged(object sender, PropertyChangedEventArgs e)
		{
			if (e.PropertyName == "IsToggled")
			{
				ListViewHeaderStyles style = calendar.ListViewSettings.HeaderStyle;
				if (subheadersSwitch.IsToggled)
					style |= ListViewHeaderStyles.Subheader;
				else
					style &= ~ListViewHeaderStyles.Subheader;
				calendar.ListViewSettings.HeaderStyle = style;
			}
		}

		void OnHeaderChanged(object sender, PropertyChangedEventArgs e)
		{
			if (e.PropertyName == "IsToggled")
			{
				ListViewHeaderStyles style = calendar.ListViewSettings.HeaderStyle;
				if (headerSwitch.IsToggled)
					style |= ListViewHeaderStyles.MainHeader;
				else
					style &= ~ListViewHeaderStyles.MainHeader;
				calendar.ListViewSettings.HeaderStyle = style;
			}
		}
	}
}