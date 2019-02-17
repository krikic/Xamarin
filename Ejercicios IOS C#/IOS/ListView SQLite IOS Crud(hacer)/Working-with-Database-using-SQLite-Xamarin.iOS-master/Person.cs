using System;
using UIKit;
using SQLite;


namespace _25SqliteiOS
{
	public class Person
	{
	

		[PrimaryKey, AutoIncrement]
		public int ID { get; set; }
		public string FirstName { get; set; }
		public string LastName { get; set; }
	}
}

