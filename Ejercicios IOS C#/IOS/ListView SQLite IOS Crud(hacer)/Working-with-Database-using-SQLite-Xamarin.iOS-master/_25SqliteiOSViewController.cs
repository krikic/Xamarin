using System;
using System.Drawing;
using System.IO;
using Foundation;
using UIKit;
using System.Collections.Generic;
using System.Linq;



namespace _25SqliteiOS
{
	public partial class _25SqliteiOSViewController : UIViewController
	{




		private string _pathToDatabase;
		string documents;



		public _25SqliteiOSViewController (IntPtr handle) : base (handle)
		{
		}

		public override void DidReceiveMemoryWarning ()
		{
			// Releases the view if it doesn't have a superview.
			base.DidReceiveMemoryWarning ();
			
			// Release any cached data, images, etc that aren't in use.
		}

		#region View lifecycle

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();

			documents = Environment.GetFolderPath (Environment.SpecialFolder.Personal);
			_pathToDatabase = Path.Combine(documents, "db_sqlite-net.db");



			
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
		}

		public override void ViewDidAppear (bool animated)
		{
			base.ViewDidAppear (animated);
		}

		public override void ViewWillDisappear (bool animated)
		{
			base.ViewWillDisappear (animated);
		}

		public override void ViewDidDisappear (bool animated)
		{
			base.ViewDidDisappear (animated);
		}


		partial void btn_Createdb_TouchUpInside (UIButton sender)
		{


			using (var conn= new SQLite.SQLiteConnection(_pathToDatabase))
			{
				conn.CreateTable<Person>();
			}


			// Let the user know that the database was created, and disable the button
			// to prevent double clicks.


			txt_Display.Text="Created new database at" + _pathToDatabase;
			btn_Createdb.Enabled=false;

			btn_insertdb.Enabled=true;
		}

		partial void btn_insertdb_TouchUpInside (UIButton sender)
		{

			var person = new Person { FirstName =txt_Fname.Text, LastName = txt_Lname.Text};
			using (var db = new SQLite.SQLiteConnection(_pathToDatabase ))
			{
				db.Insert(person);
			}

			txt_Display.Text += String.Concat(Environment.NewLine, "Inserted a new person into the database: ", person.FirstName, " ", person.LastName);
		}

		partial void btn_Clear_TouchUpInside (UIButton sender)
		{
			txt_Display.Text="";

		}



		partial void btn_Update_TouchUpInside (UIButton sender)
		{
			var person = new Person { FirstName =txt_Fname.Text, LastName = txt_Lname.Text};
			using (var db = new SQLite.SQLiteConnection(_pathToDatabase ))
			{
				db.Update(person);
			}

			txt_Display.Text += String.Concat(Environment.NewLine, "Updated person into the database: ", person.FirstName, " ", person.LastName);
		}

		partial void btn_display_TouchUpInside (UIButton sender)
		{
			List<Person> p;
			string temp="";
			p= AllRecord();
			foreach(Person data in p)
			{
				temp= temp + "\n" +  data.FirstName.ToString();
			}
			txt_Display.Text="";
			txt_Display.Text=temp;
		}
		public List<Person> AllRecord(){
			List<Person> p;

			using (var db = new SQLite.SQLiteConnection(_pathToDatabase ))
			{
				p=db.Query<Person> ("select * from Person");
				return p;
			}
		}


		#endregion
	}
}

