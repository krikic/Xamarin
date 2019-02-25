//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Diagramming;


namespace StressTest
{
	public class App : Application
	{
		public App()
		{
			dview = new DiagramView();
			dview.HeightRequest = 400;
			dview.WidthRequest = 500;

			dview.Diagram.LinkSelected += diagram_LinkSelected;

			tbNodes = new Entry();
			tbNodes.Text = "500";
			tbNodes.WidthRequest = 100;
			tbLinks = new Entry();
			tbLinks.Text = "1000";
			tbLinks.WidthRequest = 100;

			cbRouteLinks = new Switch();

			lbTimeEllapsed = new Label();
			lbTimeEllapsed.Text = "00:00:00";

			var btnGenerate = new Button();
			btnGenerate.Text = "Generate";
			btnGenerate.BorderColor = Colors.Black;
			btnGenerate.BackgroundColor = Colors.Silver;
			btnGenerate.WidthRequest = 110;
			btnGenerate.Clicked += btnGenerate_Click;

			// The root page of your application
			MainPage = new ContentPage {
				Content = new StackLayout {
					VerticalOptions = LayoutOptions.Center,
					Children = {
						new Label {
							HorizontalTextAlignment = TextAlignment.Center,
							Text = "Stress Test"
						},
						dview,
						new StackLayout{
							Orientation = StackOrientation.Horizontal,
							HorizontalOptions = LayoutOptions.Center,
							Padding = 5,
							Spacing = 5,
							Children = {
								new Label{
									Text = "Nodes: "
								},
								tbNodes,
								new Label{
									Text = "Links: "
								},
								tbLinks
							}
						},
						new StackLayout{
							Orientation = StackOrientation.Horizontal,
							HorizontalOptions = LayoutOptions.Center,
							Padding = 5,
							Spacing = 5,
							Children = {
								new Label{
									Text = "Route Links: "
								},
								cbRouteLinks
							}
						},
						new StackLayout{
							Orientation = StackOrientation.Horizontal,
							HorizontalOptions = LayoutOptions.Center,
							Padding = 5,
							Spacing = 5,
							Children = {
								lbTimeEllapsed
							}
						},
						new StackLayout{
							Orientation = StackOrientation.Horizontal,
							HorizontalOptions = LayoutOptions.Center,
							Padding = 5,
							Spacing = 5,
							Children = {
								btnGenerate
							}
						}
					}
				}
			};
		}

		void btnGenerate_Click(object sender, EventArgs e)
		{
			DateTime start = DateTime.Now;

			var r = new Random();

			// adding items is slower when the diagram is shown inside a view
			// so we create a new diagram here
			var diagram = dview.Diagram;
			diagram.ClearAll();
			diagram.DefaultShape = Shapes.Rectangle;
			diagram.LinkHeadShape = ArrowHeads.None;
			diagram.LinkSelected += diagram_LinkSelected;
			diagram.LinkDeselected += diagram_LinkDeselected;

			// adding a lot of items is faster when these properties are set to false
			diagram.ValidityChecks = false;
			diagram.SelectAfterCreate = false;

			// this shows the new link router implemented in version 5.3
			var router = new QuickRouter(diagram);
			router.Granularity = Granularity.CoarseGrained;
			diagram.LinkRouter = router;

			int nodes = 500;
			int links = 1000;
			try
			{
				nodes = int.Parse(tbNodes.Text);
				links = int.Parse(tbLinks.Text);
			}
			catch
			{
			}

			for (int i = 0; i < nodes; ++i)
			{
				diagram.Factory.CreateShapeNode(r.Next(1000), r.Next(1000), 20, 10);
			}

			for (int i = 0; i < links; ++i)
			{
				diagram.Factory.CreateDiagramLink(
					diagram.Nodes[r.Next(nodes - 1)],
					diagram.Nodes[r.Next(nodes - 1)]);
			}

			if (cbRouteLinks.IsToggled)
				diagram.RouteAllLinks();

			TimeSpan ellapsed = DateTime.Now - start;
			lbTimeEllapsed.Text = ellapsed.ToString();

			// finally show the diagram inside the view
			diagram.Bounds = new Rectangle(0, 0, 1050, 1050);
		}

		static void diagram_LinkDeselected(object sender, LinkEventArgs e)
		{
			e.Link.Pen = new Pen(Color.Black, 0);
		}

		static void diagram_LinkSelected(object sender, LinkEventArgs e)
		{
			e.Link.Pen = new Pen(Color.Green, 1);
		}


		protected override void OnStart()
		{
			// Handle when your app starts
		}

		protected override void OnSleep()
		{
			// Handle when your app sleeps
		}

		protected override void OnResume()
		{
			// Handle when your app resumes
		}


		Entry tbNodes;
		Entry tbLinks;
		Switch cbRouteLinks;
		Label lbTimeEllapsed;
		DiagramView dview;
	}
}