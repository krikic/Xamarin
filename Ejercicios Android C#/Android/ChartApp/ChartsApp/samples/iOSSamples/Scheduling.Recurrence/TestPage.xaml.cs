//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Scheduling;


namespace Recurrence
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			// Set the displayed date
			DateTime today = DateTime.Today;
			calendar.Date = today;

			// Create the recurrence pattern (The third friday each month)
			recurrence = new MindFusion.Scheduling.Recurrence();
			recurrence.Pattern = RecurrencePattern.Monthly;
			recurrence.MonthlyRecurrence = MonthlyRecurrence.ByDayType;
			recurrence.Occurrence = Occurrence.Third;
			recurrence.Day = DayOfWeekType.Friday;
			recurrence.StartDate = new DateTime(today.Year, today.Month, 1);
			recurrence.RecurrenceEnd = RecurrenceEnd.Never;

			// We have to associate the recurrence with an Item-derived
			// object to tell it what kind of objects to create in
			// GenerateItems method. For this purpose we use the
			// worker object below
			worker = new RecurringDay();
			worker.Recurrence = recurrence;

			calendar.MonthSettings.DaySettings.HeaderSize = 0;

			calendar.CustomDraw = CustomDrawElements.CellHeader;
			calendar.Draw += calendar_Draw;
		}

		void calendar_Draw(object sender, DrawEventArgs e)
		{
			if (e.Element == CustomDrawElements.CellHeader)
			{
				DateTime date = e.Date;

				// Check if this day falls within our recurrence pattern
				ItemCollection items = recurrence.GenerateItems(date, date);

				bool isOccurrence = false;
				foreach (Item item in items)
				{
					if (item.StartTime.Date == date)
					{
						isOccurrence = true;
						break;
					}
				}

				if (isOccurrence)
					e.Graphics.DrawRectangle(new Pen(Colors.Red, 0), e.Bounds);
			}
		}


		MindFusion.Scheduling.Recurrence recurrence;
		RecurringDay worker;
	}

	/// <summary>
	/// An item subclass used to generate occurrences upon.
	/// </summary>
	public class RecurringDay : Item
	{
		public RecurringDay()
		{
		}

		public override Item Clone()
		{
			return null;
		}

		public override bool AllDayEvent
		{
			get { return false; }
			set { }
		}

		public override ResourceCollection<Contact> Contacts
		{
			get { return EmptyContacts; }
		}

		public override string Subject
		{
			get { return ""; }
			set { }
		}

		public override string DescriptionText
		{
			get { return ""; }
			set { }
		}

		public override string Details
		{
			get { return ""; }
			set { }
		}

		public override string HeaderText
		{
			get { return ""; }
			set { }
		}

		public override DateTime StartTime
		{
			get { return _startTime; }
			set { _startTime = value; }
		}

		public override DateTime EndTime
		{
			get { return _endTime; }
			set { _endTime = value; }
		}

		public override string Id
		{
			get { return ""; }
			set { }
		}

		public override Location Location
		{
			get { return null; }
			set { }
		}

		public override Task Task
		{
			get { return null; }
			set { }
		}

		public override Reminder Reminder
		{
			get { return null; }
			set { }
		}

		public override bool Locked
		{
			get { return false; }
			set { }
		}

		public override MindFusion.Scheduling.Style Style
		{
			get { return null; }
			set { }
		}

		public override bool Visible
		{
			get { return false; }
			set { }
		}

		public override object Tag
		{
			get { return null; }
			set { }
		}

		public override MindFusion.Scheduling.Style SelectedStyle
		{
			get { return null; }
			set { }
		}

		public override ResourceCollection<MindFusion.Scheduling.Resource> Resources
		{
			get { return EmptyResources; }
		}

		public override bool AllowResize
		{
			get { return true; }
			set { }
		}

		public override bool AllowMove
		{
			get { return true; }
			set { }
		}

		public override MindFusion.Scheduling.Style PointedStyle
		{
			get { return null; }
			set { }
		}

		public override MindFusion.Scheduling.Style PointedSelectedStyle
		{
			get { return null; }
			set { }
		}


		readonly static ResourceCollection<Contact> EmptyContacts = new ResourceCollection<Contact>();
		readonly static ResourceCollection<MindFusion.Scheduling.Resource> EmptyResources = new ResourceCollection<MindFusion.Scheduling.Resource>();

		DateTime _startTime;
		DateTime _endTime;
	}
}