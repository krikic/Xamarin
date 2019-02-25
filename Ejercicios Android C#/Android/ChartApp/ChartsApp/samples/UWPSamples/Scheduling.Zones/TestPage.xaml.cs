//
// Copyright (c) 2017, MindFusion LLC - Bulgaria.
//

using System;
using System.Collections.Generic;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Scheduling;


namespace Zones
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			calendar.InteractiveItemType = typeof(ZoneEvent);
			calendar.DragBehavior = DragBehavior.Select;
			calendar.Theme = ThemeType.Dark;

			calendar.TimetableSettings.Dates.Clear();
			calendar.TimetableSettings.Dates.Add(DateTime.Today);
			calendar.TimetableSettings.Dates.Add(DateTime.Today.AddDays(1));
			calendar.TimetableSettings.ItemOffset = 30;
			calendar.TimetableSettings.VisibleColumns = 2;
			calendar.TimetableSettings.ShowItemSpans = State.Disabled;
			calendar.TimetableSettings.SnapInterval = TimeSpan.FromMinutes(1);

			calendar.CustomDraw = CustomDrawElements.TimetableCell;
			calendar.Draw += calendar_Draw;
			calendar.ItemModifying += calendar_ItemModifying;
			calendar.ItemCreating += calendar_ItemCreating;
			calendar.ItemCreated += calendar_ItemCreated;
		}

		void defineZoneA_Clicked(object sender, EventArgs e)
		{
			AddZone(true);
		}

		void defineZoneB_Clicked(object sender, EventArgs e)
		{
			AddZone(false);
		}

		void deleteZone_Clicked(object sender, EventArgs e)
		{
			for (int j = 0; j < zones.Count;)
			{
				Zone z = zones[j];

				// Check if zone is selected
				bool found = false;
				DateTime start = calendar.Selection.StartTime;
				DateTime end = calendar.Selection.EndTime;
				if (Intersect(z.Start, z.End, start, end))
				{
					zones.RemoveAt(j);
					found = true;

					// Remove all items from the zone
					for (int k = 0; k < calendar.Schedule.Items.Count;)
					{
						Item item = calendar.Schedule.Items[k];

						if (Intersect(z.Start, z.End, item.StartTime, item.EndTime))
						{
							calendar.Schedule.Items.RemoveAt(k);
							continue;
						}

						k++;
					}

					break;
				}

				if (!found)
					j++;
			}

			calendar.Selection.Reset();
		}

		void calendar_Draw(object sender, DrawEventArgs e)
		{
			if (e.Element == CustomDrawElements.TimetableCell)
			{
				DateTime cellStart = e.Date + e.StartTime;
				DateTime cellEnd = cellStart + calendar.TimetableSettings.CellTime;
				bool zone = false;
				bool zoneStart = false;
				bool zoneEnd = false;
				bool type = false;

				foreach (Zone z in zones)
				{
					if (z.Start == cellStart)
					{
						zone = true;
						type = z.Type;
						zoneStart = true;
					}

					if (z.End == cellEnd)
					{
						zone = true;
						type = z.Type;
						zoneEnd = true;
					}

					if (z.Start < cellStart && cellStart < z.End ||
						z.Start < cellEnd && cellEnd < z.End)
					{
						zone = true;
						type = z.Type;
					}
				}

				bool selected = false;
				if (calendar.Selection.Contains(cellStart))
					selected = true;

				Rectangle b = e.Bounds;
				IGraphics g = e.Graphics;
				var p = new Pen(e.Style.LineColor, 0);
				if (zone)
				{
					Brush b2 = new SolidBrush(Color.FromRgba(255, 255, 255, 20));
					Brush b4;
					if (type)
						b4 = new SolidBrush(Color.FromRgba(75,
							Colors.Goldenrod.R, Colors.Goldenrod.G, Colors.Goldenrod.B));
					else
						b4 = new SolidBrush(Color.FromRgba(75,
							Colors.LightSteelBlue.R, Colors.LightSteelBlue.G, Colors.LightSteelBlue.B));

					g.FillRectangle(b2, b);
					g.FillRectangle(b4, b.Left, b.Top, 30, b.Height);

					g.DrawLine(p, b.Left + 30, b.Top, b.Left + 30, b.Bottom);

					g.DrawLine(p, b.Left, b.Top, b.Left, b.Bottom);
					g.DrawLine(p, b.Right, b.Top, b.Right, b.Bottom);

					if (zoneStart)
						g.DrawLine(p, b.Left, b.Top, b.Right, b.Top);

					if (zoneEnd)
						g.DrawLine(p, b.Left, b.Bottom, b.Right, b.Bottom);
				}

				Brush b1 = new SolidBrush(Color.FromRgba(0, 0, 0, 40));
				if (selected)
				{
					bool start = false;
					bool end = false;
					DateTime istart = calendar.Selection.StartTime;
					DateTime iend = calendar.Selection.EndTime;

					if (cellStart == istart)
						start = true;
					if (cellEnd == iend)
						end = true;

					g.FillRectangle(b1, b);

					g.DrawLine(p, b.Left, b.Top, b.Left, b.Bottom);
					g.DrawLine(p, b.Right, b.Top, b.Right, b.Bottom);

					if (start)
						g.DrawLine(p, b.Left, b.Top, b.Right, b.Top);

					if (end)
						g.DrawLine(p, b.Left, b.Bottom, b.Right, b.Bottom);
				}
			}
		}

		void calendar_ItemCreating(object sender, ItemConfirmEventArgs e)
		{
			DateTime start = e.Item.StartTime;
			DateTime end = e.Item.EndTime;

			bool inZone = false;
			foreach (Zone z in zones)
			{
				if (z.Start <= start && end <= z.End)
				{
					inZone = true;
					break;
				}
			}

			if (!inZone)
			{
				// TODO:
				//MessageBox.Show("Events cannot be created outside zones.");
				e.Confirm = false;
			}
		}

		void calendar_ItemModifying(object sender, ItemModifyConfirmEventArgs e)
		{
			var item = e.Item as ZoneEvent;
			DateTime start = e.NewStartTime;
			DateTime end = e.NewEndTime;

			bool inZone = false;
			foreach (Zone z in zones)
			{
				if (z.Start <= start && end <= z.End)
				{
					if (z.Type == item.ZoneType)
					{
						inZone = true;
						break;
					}
				}
			}

			if (!inZone)
			{
				e.Confirm = false;
			}
		}

		void calendar_ItemCreated(object sender, ItemEventArgs e)
		{
			var item = e.Item as ZoneEvent;

			e.Item.EndTime = e.Item.StartTime.AddMinutes(30);

			foreach (Zone z in zones)
			{
				if (Intersect(z.Start, z.End, item.StartTime, item.EndTime))
				{
					item.ZoneType = z.Type;
					break;
				}
			}
		}


		void AddZone(bool type)
		{
			DateTime start = calendar.Selection.StartTime;
			DateTime end = calendar.Selection.EndTime;

			// Check for zone intersection
			bool inter = false;
			foreach (Zone z in zones)
			{
				if (Intersect(z.Start, z.End, start, end))
				{
					inter = true;
					break;
				}
			}

			if (inter)
			{
				// TODO:
				//MessageBox.Show("Cannot create intersecting zones.");
			}
			else
			{
				// Define a new zone
				zones.Add(new Zone(start, end, "hey", type));

				calendar.Selection.Reset();
			}
		}

		/// <summary>
		/// Checks whether the specified time intervals intersect.
		/// </summary>
		static bool Intersect(DateTime start1, DateTime end1, DateTime start2, DateTime end2)
		{
			if (end2 < start1 || end1 <= start2)
				return false;

			return true;
		}


		/// <summary>
		/// A list with all defined zones.
		/// </summary>
		List<Zone> zones = new List<Zone>();
	}

	/// <summary>
	/// A definition of a zone.
	/// </summary>
	public class Zone
	{
		public Zone(DateTime start, DateTime end, string name, bool type)
		{
			Start = start;
			End = end;
			Name = name;
			Type = type;
		}

		public DateTime Start
		{
			get;
			private set;
		}

		public DateTime End
		{
			get;
			private set;
		}

		public string Name
		{
			get;
			private set;
		}

		public bool Type
		{
			get;
			private set;
		}
	}

	/// <summary>
	/// Our custom 'zoned' appointment.
	/// </summary>
	public class ZoneEvent : Appointment
	{
		/// <summary>
		/// Always define empty constructor for custom items.
		/// </summary>
		public ZoneEvent()
		{
			ZoneType = false;
		}

		/// <summary>
		/// Appointment.Clone override.
		/// </summary>
		public override Item Clone()
		{
			var clone = new ZoneEvent();

			clone.AllDayEvent = AllDayEvent;
			clone.AllowResize = AllowResize;
			clone.AllowMove = AllowMove;
			clone.DescriptionText = DescriptionText;
			clone.EndTime = EndTime;
			clone.HeaderText = HeaderText;
			clone.Location = Location;
			clone.Locked = Locked;
			clone.PointedSelectedStyle = PointedSelectedStyle.Clone();
			clone.PointedStyle = PointedStyle.Clone();
			clone.Priority = Priority;
			clone.Reminder = Reminder;
			clone.SelectedStyle = SelectedStyle.Clone();
			clone.StartTime = StartTime;
			clone.Style = Style.Clone();
			clone.Tag = Tag;
			clone.Task = Task;
			clone.Visible = Visible;

			foreach (MindFusion.Scheduling.Resource resource in Resources)
				clone.Resources.Add(resource);

			foreach (Contact contact in Contacts)
				clone.Contacts.Add(contact);

			clone.ZoneType = ZoneType;

			return clone;
		}


		/// <summary>
		/// Gets or sets the zone this item is aligned to.
		/// </summary>
		public bool ZoneType
		{
			get;
			set;
		}
	}
}