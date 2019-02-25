//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Diagramming;


namespace FractalLayout
{
	public class App : Application
	{
		public App()
		{
			dview = new DiagramView();
			dview.WidthRequest = 500;
			dview.HeightRequest = 500;

			Button btnRandom = new Button();
			btnRandom.Text = "Random";
			btnRandom.BorderColor = Colors.Black;
			btnRandom.BackgroundColor = Colors.Silver;
			btnRandom.WidthRequest = 110;
			btnRandom.Clicked += OnRandomTree;

			bounds = new Rectangle(0, 0, 20, 20);

			brushes = new Brush[] {
				new LinearGradientBrush(Colors.LightSteelBlue, Colors.BlueViolet, 0),
				new LinearGradientBrush(Colors.White, Colors.LightBlue, 0),
				new LinearGradientBrush(Colors.White, Colors.DeepSkyBlue, 0),
				new LinearGradientBrush(Colors.LimeGreen, Colors.Green, 0)
			};

			random = new Random();

			// The root page of your application
			MainPage = new ContentPage {
				Content = new StackLayout {
					VerticalOptions = LayoutOptions.Center,
					Children = {
						new Label {
							XAlign = TextAlignment.Center,
							Text = "Fractal Layout"
						},
						dview,
						new StackLayout{
							Children = 
							{
								btnRandom
							},
							HorizontalOptions = LayoutOptions.Center
						}
					}
				}
			};
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

		void OnRandomTree(object sender, EventArgs e)
		{
			dview.Diagram.ClearAll();

			ShapeNode root = dview.Diagram.Factory.CreateShapeNode(bounds);
			RandomTree(root, 5, 4);
			Arrange(root);
		}

		static void Arrange(DiagramNode root)
		{
			var layout = new MindFusion.Diagramming.Layout.FractalLayout();
			layout.Root = root;
			layout.Arrange(root.Parent);
			root.Parent.ZoomFactor = 10;
		}

		void RandomTree(DiagramNode node, int depth, int minChildren)
		{
			if (depth <= 0)
				return;

			Diagram diagram = node.Parent;
			int children = random.Next(depth) - 1 + minChildren;

			if (diagram.Nodes.Count < 3 && children < 2)
				children = 2;

			for (int i = 0; i < children; ++i)
			{
				// create child node and link
				ShapeNode child = diagram.Factory.CreateShapeNode(bounds);
				child.Brush = brushes[depth % brushes.Length];
				diagram.Factory.CreateDiagramLink(node, child);

				// build child branch
				RandomTree(child, depth - 1, minChildren);
			}
		}


		readonly DiagramView dview;
		readonly Rectangle bounds;
		readonly Brush[] brushes;
		readonly Random random;
	}
}