//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Xml.Linq;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Diagramming;


namespace DecisionLayout
{
	public class App : Application
	{
		public App()
		{
			Init();

			var btnLoad = new Button();
			btnLoad.Text = "Load";
			btnLoad.BorderColor = Colors.Black;
			btnLoad.BackgroundColor = Colors.Silver;
			btnLoad.WidthRequest = 110;
			btnLoad.Clicked += loadSampleButton_Click;

			var btnLayout = new Button();
			btnLayout.Text = "Layout";
			btnLayout.BorderColor = Colors.Black;
			btnLayout.BackgroundColor = Colors.Silver;
			btnLayout.WidthRequest = 110;
			btnLayout.Clicked += layoutButton_Click;

			var btnCreateRandom = new Button();
			btnCreateRandom.Text = "Create Random";
			btnCreateRandom.BorderColor = Colors.Black;
			btnCreateRandom.BackgroundColor = Colors.Silver;
			btnCreateRandom.WidthRequest = 110;
			btnCreateRandom.Clicked += createRandomButton_Click;

			// The root page of your application
			MainPage = new ContentPage {
				Content = new StackLayout {
					VerticalOptions = LayoutOptions.Center,
					Children = {
						new Label {
							HorizontalTextAlignment = TextAlignment.Center,
							Text = "Decision layout"
						},
						dview,
						new StackLayout {
							Orientation = StackOrientation.Horizontal,
							HorizontalOptions = LayoutOptions.Center,
							Spacing = 5,
							Children = {
								btnLoad,
								btnLayout,
								btnCreateRandom
							},
							Padding = 5
						}
					}
				}
			};
		}

		void loadSampleButton_Click(object sender, EventArgs e)
		{
			var assembly = typeof(App).GetTypeInfo().Assembly;
			Stream stream = assembly.GetManifestResourceStream("DecisionLayout.flowchart.xml");

			string text;
			using (var reader = new StreamReader(stream))
			{
				text = reader.ReadToEnd();
			}

			// Load the graph xml
			XDocument document = XDocument.Parse(text);

			dview.Diagram.LoadFromXml(document);
		}

		void layoutButton_Click(object sender, EventArgs e)
		{
			Arrange();
		}

		void createRandomButton_Click(object sender, EventArgs e)
		{
			dview.Diagram.ClearAll();

			var startNode = dview.Diagram.Factory.CreateShapeNode(0, 0, 30, 10);
			startNode.Brush = terminalBrush;
			startNode.Pen = terminalPen;
			startNode.Text = "START";
			startNode.Tag = "start";
			startNode.Shape = Shapes.Ellipse;

			var endNode = dview.Diagram.Factory.CreateShapeNode(0, 0, 30, 10);
			endNode.Brush = terminalBrush;
			endNode.Pen = terminalPen;
			endNode.Text = "END";
			endNode.Shape = Shapes.Ellipse;

			const int maxLength = 10;
			var all = new List<ShapeNode>();
			var remaining = new Queue<List<ShapeNode>>();

			remaining.Enqueue(new List<ShapeNode>(new [] { startNode }));

			var random = new Random(DateTime.Now.Millisecond);
			int length = 0;
			while (remaining.Count > 0)
			{
				var newNodes = new List<ShapeNode>();
				bool createdNew = false;

				var next = remaining.Dequeue();
				for (var n = 0; n < next.Count; n++)
				{
					var node = next[n];
					var isDecision = node.Tag is bool;

					ShapeNode previousChoice = null;
					int outLinks = isDecision ? 2 : 1;
					string[] linkTexts = isDecision ? new [] { "YES", "NO" } : new [] { "" };
					for (int i = 0; i < outLinks; i++)
					{
						// Create new or link to an existing node;
						// Create at least one new node for each level
						bool createNew = random.Next(maxLength) > length;
						if (!createdNew && n == next.Count - 1)
							createNew = true;
						if (length == maxLength)
							createNew = false;

						DiagramLink link;
						if (createNew)
						{
							// New decision or an operation node
							bool newDecision = random.Next(3) > 0 && !isDecision;
							var newNode = dview.Diagram.Factory.CreateShapeNode(0, 0, 30, 10);
							if (newDecision)
							{
								newNode.Shape = Shapes.Decision;
								newNode.AnchorPattern = AnchorPattern.Decision2In2Out;
								newNode.Brush = decisionBrush;
								newNode.Text = "Decision";
								newNode.Tag = true;
							}
							else
							{
								newNode.Shape = Shapes.Rectangle;
								newNode.Brush = normalBrush;
								newNode.Text = "Operation";
							}

							link = dview.Diagram.Factory.CreateDiagramLink(node, newNode);
							newNodes.Add(newNode);
							createdNew = true;
						}
						else
						{
							// Link to an existing node. If length == maxLength, link to the end node
							if (length == maxLength || remaining.Count == 0)
							{
								link = dview.Diagram.Factory.CreateDiagramLink(node, endNode);
							}
							else
							{
								// Make sure both choices don't lead to the same node
								ShapeNode choice = null;
								while (choice == previousChoice)
									choice = all[random.Next(all.Count)];
								link = dview.Diagram.Factory.CreateDiagramLink(node, choice);
								previousChoice = choice;
							}
						}

						link.Text = linkTexts[i];
					}

					all.Add(node);
				}

				if (newNodes.Count > 0)
					remaining.Enqueue(newNodes);

				length++;
			}

			Arrange();
		}

		void Init()
		{
			dview = new DiagramView();
			dview.WidthRequest = 500;
			dview.HeightRequest = 500;

			terminalBrush = new LinearGradientBrush(
				Color.FromRgb(0xFF, 0xFF, 0xC0), Color.FromRgb(0xBF, 0xBF, 0x90), 90);
			decisionBrush = new LinearGradientBrush(
				Color.FromRgb(0xF0, 0xFF, 0xFF), Color.FromRgb(0x7F, 0xDF, 0x8F), 90);
			normalBrush = new LinearGradientBrush(
				Color.FromRgb(0xE0, 0xFF, 0xFF), Color.FromRgb(0xA8, 0xBF, 0xBF), 90);
			terminalPen = new Pen(Color.Black);
			terminalPen.Width = 0.6f;

			dview.Diagram.DiagramStyle.ShadowBrush = new SolidBrush(Color.FromRgba(0, 0, 0, 0x6E));
			dview.Diagram.DiagramStyle.FontFamily = "Arial";
			dview.Diagram.DiagramStyle.FontSize = 3.0;
			dview.Diagram.ShadowOffsetX = 2;
			dview.Diagram.ShadowOffsetY = 2;
			dview.Diagram.DiagramLinkStyle.Brush = new SolidBrush(Color.FromRgb(0x41, 0x69, 0xE1));
			dview.Diagram.DiagramLinkStyle.ShadowBrush = new SolidBrush(Color.Transparent);
			dview.Diagram.DiagramLinkStyle.TextBrush = new SolidBrush(Color.FromRgb(0x00, 0x00, 0x8B));
		
			dview.Diagram.DiagramLinkStyle.FontStyle = FontAttributes.Bold;
			dview.Diagram.LinkHeadShape = ArrowHeads.Triangle;
			dview.Diagram.LinkHeadShapeSize = 3.5;
			dview.Diagram.ShapeNodeStyle.Effects.Add(new GlassEffect());
		}

		void Arrange()
		{
			var layout = new MindFusion.Diagramming.Layout.DecisionLayout();
			layout.HorizontalPadding = 10;
			layout.VerticalPadding = 10;
			layout.StartNode = dview.Diagram.FindNode("start");
			layout.Arrange(dview.Diagram);
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


		DiagramView dview;
		Brush terminalBrush;
		Brush decisionBrush;
		Brush normalBrush;
		Pen terminalPen;
	}
}