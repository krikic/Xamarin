//
// Copyright (c) 2017, MindFusion LLC - Bulgaria.
//

using System;
using System.ComponentModel;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Scheduling;


namespace MilestoneItems
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			calendar.Theme = ThemeType.Dark;
			calendar.InteractiveItemType = typeof(MilestoneItem);
			calendar.ListViewSettings.EnableMilestoneMode = State.Enabled;
			calendar.ListViewSettings.Orientation = Orientation.Horizontal;
			calendar.ListViewSettings.NumberOfCells = 3;
			calendar.ListViewSettings.FreeDrag = State.Enabled;
			calendar.ItemSettings.Size = 32;
			calendar.ItemSettings.Style.Font = calendar.ItemSettings.Style.Font.Value.WithAttributes(FontAttributes.Bold);
			calendar.ItemSettings.SelectedItemStyle.Font = calendar.ItemSettings.Style.Font.Value.WithAttributes(FontAttributes.Bold);
			calendar.ItemSettings.PointedItemStyle.Font = calendar.ItemSettings.Style.Font.Value.WithAttributes(FontAttributes.Bold);
			calendar.ItemSettings.PointedSelectedItemStyle.Font = calendar.ItemSettings.Style.Font.Value.WithAttributes(FontAttributes.Bold);

			// Create some milestone items
			DateTime baseDate = calendar.GetFirstDate();

			var colors = new[]
			{
				Colors.Red,
				Colors.Blue,
				Colors.Magenta,
				Colors.DarkViolet
			};

			var random = new Random(DateTime.Now.Millisecond);
			for (int i = 0; i < 50; i++)
			{
				var item = new MilestoneItem();

				item.StartTime = baseDate.Add(TimeSpan.FromDays(random.Next() % 7));
				item.EndTime = item.StartTime;
				item.HeaderText = i.ToString();
				item.Color = colors[random.Next() % colors.Length];

				calendar.Schedule.Items.Add(item);

				calendar.SetItemListLane(item, random.Next() % 15);
			}

			calendar.CustomDraw = CustomDrawElements.CalendarItem;

			calendar.ItemDrawing += (s, e) =>
			{
				var item = e.Item as MilestoneItem;

				Rectangle b = e.Bounds;

				var pts = new[]
				{
					new Point(b.Left + b.Width / 2, b.Top),
					new Point(b.Right, b.Top + b.Height / 2),
					new Point(b.Left + b.Width / 2, b.Bottom),
					new Point(b.Left, b.Top + b.Height / 2),
					new Point(b.Left + b.Width / 2, b.Top)
				};

				var path = GraphicsFactory.CreatePath();
				path.AddLines(pts);

				var brush = new SolidBrush(item.Color);

				e.Graphics.FillPath(brush, path);
				e.Graphics.DrawPath(new Pen(e.Style.LineColor), path);

				var format = new StringFormat();
				format.HorizontalAlignment = HorizontalAlignment.Center;
				format.VerticalAlignment = VerticalAlignment.Center;
				b = b.Offset(1, 1);
				e.Graphics.DrawString(e.Item.HeaderText, e.Style.Font.Value, Brushes.Black, b, format);
				b = b.Offset(-1, -1);
				e.Graphics.DrawString(e.Item.HeaderText, e.Style.Font.Value, Brushes.White, b, format);

				e.Handled = true;
			};
		}
	}

	class MilestoneItem : Appointment
	{
		public MilestoneItem()
		{
			Color = Colors.Black;
		}

		public override bool AllowResize
		{
			get { return false; }
		}

		public Color Color
		{
			get;
			set;
		}
	}
}