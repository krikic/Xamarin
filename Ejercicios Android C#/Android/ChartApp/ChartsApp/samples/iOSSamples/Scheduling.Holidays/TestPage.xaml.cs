//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using System.Text;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.HolidayProviders;
using MindFusion.Scheduling;


namespace Holidays
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			calendar.Date = DateTime.Today;
			calendar.MonthSettings.DaySettings.HeaderSize = 0;
			calendar.AllowContextMenu = false;
			calendar.MonthSettings.DaySettings.Style.HeaderTextAlignment = MindFusion.Drawing.TextAlignment.MiddleCenter;

			label = new Label()
			{
				BackgroundColor = Color.FromRgba(0, 0, 0, 0.5),
				FontSize = 9,
				FontAttributes = FontAttributes.Bold,
				TextColor = Colors.White
			};

			calendar.DateClick += (s, e) => {
				var text = new StringBuilder();
				if (holidays != null && holidays.Length > 0)
				{
					foreach (Holiday holiday in holidays)
					{
						if (holiday.Date <= e.Date && e.Date <= holiday.EndDate)
						{
							if (text.Length > 0)
								text.AppendLine();
							text.Append(holiday.Title);
						}
					}
				}

				label.Text = text.ToString();

				if (label.Text.Length == 0)
				{
					calendar.Children.Remove(label);
				}
				else
				{
					const double width = 100;
					const double height = 40;

					var bounds = calendar.GetElementBounds(CalendarElement.Cell, e.Index);
					if (bounds.Y - height > 0)
						bounds = new Rectangle(bounds.Center.X - width / 2, bounds.Top - height, width, height);
					else
						bounds = new Rectangle(bounds.Center.X - width / 2, bounds.Bottom, width, height);
					if (bounds.X < 0)
						bounds.X = 0;
					if (bounds.Right > calendar.Width)
						bounds.X = calendar.Width - bounds.Width;

					AbsoluteLayout.SetLayoutFlags(label, AbsoluteLayoutFlags.None);
					AbsoluteLayout.SetLayoutBounds(label, bounds);
					calendar.Children.Add(label);
				}
			};

			calendarList.ItemsSource = new []
			{
				"Australia",
				"France",
				"Germany",
				"Russia",
				"UK",
				"US"
			};
			calendarList.RowHeight = 24;
			calendarList.ItemSelected += (s, e) => {
				calendar.Children.Remove(label);
				calendarName = e.SelectedItem.ToString();
				UpdateHolidays();
			};

			calendarList.SelectedItem = "US";
		}

		void calendar_Draw(object sender, DrawEventArgs e)
		{
			if (e.Element == CustomDrawElements.CellHeader)
			{
				Rectangle bounds = e.Bounds;
				if (e.Date.Date == DateTime.Today)
				{
					var brush = new SolidBrush(Color.FromRgba(
						Color.White.R, Color.White.G, Color.White.B, 0.4));
					e.Graphics.FillRectangle(brush, bounds);

					var pen = new Pen(Color.Red, 5);
					e.Graphics.DrawRectangle(pen, bounds);
				}
				else
				{
					if (holidays != null && holidays.Length > 0)
					{
						bool isHoliday = false;
						foreach (Holiday holiday in holidays)
						{
							if (holiday.Date <= e.Date.Date && e.Date.Date <= holiday.EndDate)
							{
								isHoliday = true;
								break;
							}
						}

						if (isHoliday)
						{
							var brush = new SolidBrush(Color.FromRgba(
								Colors.LightSteelBlue.R, Colors.LightSteelBlue.G, Colors.LightSteelBlue.B, 0.5));
							e.Graphics.FillRectangle(brush, bounds);

							var pen = new Pen(Color.FromRgba(
								Colors.SlateGray.R, Colors.SlateGray.G, Colors.SlateGray.B, 0.8), 5);
							e.Graphics.DrawRectangle(pen, bounds);

							var format = new StringFormat();
							format.HorizontalAlignment = HorizontalAlignment.Center;
							format.VerticalAlignment = VerticalAlignment.Center;

							brush = new SolidBrush(Color.FromRgb(192, 0, 0));
							e.Graphics.DrawString(e.Text, e.Style.HeaderFont.Value, brush,
								new Rectangle(bounds.X, bounds.Y, bounds.Width, bounds.Height), format);
						}
					}
				}
			}
		}

		void calendar_VisibleDateChanged(object sender, DateChangedEventArgs e)
		{
			calendar.Children.Remove(label);
			UpdateHolidays();
		}

		void UpdateHolidays()
		{
			HolidayProvider provider = null;
			switch (calendarName)
			{
			case "Australia":
				provider = new AustraliaHolidayProvider();
				break;

			case "France":
				provider = new FranceHolidayProvider();
				break;

			case "Germany":
				provider = new GermanyHolidayProvider();
				break;

			case "Russia":
				provider = new RussiaHolidayProvider();
				break;

			case "UK":
				provider = new UKHolidayProvider();
				break;

			case "US":
				provider = new USHolidayProvider();
				break;
			}

			DateTime date = calendar.Date;
			holidays = provider.GetHolidays(
				new DateTime(date.Year, date.Month, 1),
				new DateTime(date.Year, date.Month, DateTime.DaysInMonth(date.Year, date.Month)));

			calendar.Invalidate();
		}


		string calendarName;
		Holiday[] holidays;
		Label label;
	}
}