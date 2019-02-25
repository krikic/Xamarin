//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Xml.Linq;
using Xamarin.Forms;

using MindFusion.Diagramming;
using MindFusion.Diagramming.Layout;


namespace Tutorial2
{
	public class App : Application
	{
		public App()
		{
			dview = new DiagramView();
			dview.WidthRequest = 500;
			dview.HeightRequest = 500;

			bounds = new Rectangle(0, 0, 24, 6);
			var root = dview.Diagram.Factory.CreateShapeNode(bounds);
			root.Text = "Project";

			var assembly = typeof(App).GetTypeInfo().Assembly;
			Stream stream = assembly.GetManifestResourceStream("Tutorial2.SampleTree.xml");
			string text;
			using (var reader = new StreamReader(stream)) {
				text = reader.ReadToEnd ();
			}

			// Load the graph xml
			XDocument document = XDocument.Parse(text);
			CreateChildren (root, document.Root);

			var layout = new TreeLayout ();
			layout.Type = TreeLayoutType.Cascading;
			layout.Direction = TreeLayoutDirections.LeftToRight;
			layout.LinkStyle = TreeLayoutLinkType.Cascading2;
			layout.NodeDistance = 3;
			layout.LevelDistance = -8;
			layout.Arrange(dview.Diagram);

			// The root page of your application
			MainPage = new ContentPage {
				Content = new StackLayout {
					VerticalOptions = LayoutOptions.Center,
					Children = {
						new Label {
							XAlign = TextAlignment.Center,
							Text = "MindFusion Tutorial 2"
						},
						dview
					}
				}
			};
		}

		void CreateChildren(DiagramNode parentDiagNode, XElement parentXmlNode)
		{
			var activities = parentXmlNode.Elements();
			foreach (var ac in activities) {
				if (ac.Name == "Activity") {
					var node = dview.Diagram.Factory.CreateShapeNode (bounds);
					node.Text = ac.Attribute (XName.Get ("Name")).Value;
					dview.Diagram.Factory.CreateDiagramLink (parentDiagNode, node);
					CreateChildren (node, ac);
				}
			}
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
		Rectangle bounds;
	}
}