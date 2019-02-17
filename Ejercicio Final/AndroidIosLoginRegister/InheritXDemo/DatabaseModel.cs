using System;
using SQLite;

namespace InheritXDemo
{
	public class DatabaseModel
	{

		[PrimaryKey,AutoIncrement]
		public int id
		{
			get;
			set;
		}

		public string personName
		{
			get;
			set;
		}

		public string personEmail
		{
			get;
			set;
		}

		public string personPassword
		{
			get;
			set;
		}

		public string personMobileNumber
		{
			get;
			set;
		}




	}

}
