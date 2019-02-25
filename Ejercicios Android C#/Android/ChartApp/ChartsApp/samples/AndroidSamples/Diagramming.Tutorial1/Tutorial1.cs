//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Reflection;
using System.Xml.Linq;
using Xamarin.Forms;

using MindFusion.Diagramming;
using MindFusion.Diagramming.Layout;


namespace Tutorial1
{
	public class App : Application
	{
		public App()
		{
			var dview = new DiagramView();
			dview.WidthRequest = 500;
			dview.HeightRequest = 500;

			var nodeMap = new Dictionary<string, DiagramNode>();
			var bounds = new Rectangle(0, 0, 18, 6);

			var assembly = typeof(App).GetTypeInfo().Assembly;
			Stream stream = assembly.GetManifestResourceStream("Tutorial1.SampleGraph.xml");
			string text;
			using (var reader = new StreamReader(stream))
			{
				text = reader.ReadToEnd ();
			}

			// Load the graph xml
			XDocument document = XDocument.Parse(text);//"SampleGraph.xml");
			Debug.WriteLine (document.Root.Name);
			var nodes = document.Descendants("Node");
			foreach (var node in nodes)
			{
				var diagramNode = dview.Diagram.Factory.CreateShapeNode (bounds);
				nodeMap [node.Attribute("id").Value] = diagramNode;
				diagramNode.Text = node.Attribute("name").Value; 
			}

			var links = document.Descendants("Link");
			foreach (var link in links)
			{
				dview.Diagram.Factory.CreateDiagramLink(
					nodeMap[link.Attribute("origin").Value],
					nodeMap[link.Attribute("target").Value]);
			}

			var layout = new LayeredLayout();
			layout.LayerDistance = 12;
			layout.Arrange(dview.Diagram);

			// The root page of your application
			MainPage = new ContentPage {
				Content = new StackLayout {
					VerticalOptions = LayoutOptions.Center,
					Children = {
						new Label {
							XAlign = TextAlignment.Center,
							Text = "Welcome to Xamarin Forms!"
						},
						dview
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
	}
}