//
// Copyright (c) 2017, MindFusion LLC - Bulgaria.
//

using System;
using System.ComponentModel;
using Xamarin.Forms;

using MindFusion.Scheduling;


namespace ResourceView
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			DateTime firstDate = DateTime.Today;

			calendar.BeginInit();

			calendar.Resources.Add(new MindFusion.Scheduling.Resource { Name = "Resource #1" });
			calendar.Resources.Add(new MindFusion.Scheduling.Resource { Name = "Resource #2" });
			calendar.Resources.Add(new MindFusion.Scheduling.Resource { Name = "Resource #3" });
			calendar.GroupType = GroupType.GroupByResources;

			// Create some test resources
			Appointment app;
			Random r = new Random();

			for (int i = 0; i < 20; i++)
			{
				app = new Appointment();
				app.StartTime = calendar.Date.AddDays(r.Next(10) + 3);
				app.EndTime = app.StartTime + TimeSpan.FromHours(24 + r.Next(24));
				app.HeaderText = "Resource " + i.ToString();
				app.Resources.Add(calendar.Resources[r.Next(3)]);
				calendar.Schedule.Items.Add(app);
			}

			calendar.EndInit();
		}
	}
}