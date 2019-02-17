using System;

using Xamarin.Forms;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading.Tasks;

namespace myForum
{
    public class App : Application
    {
		//Initial SQLite database
		static SqliteDatabase database;
        // Set the topic name
        public string TopicName { get; set; }
        //Set the id
        public int IndexId { get; set; }
            
        public App()
        {
            MainPage = new NaviationTab();
        }

        //Set the database path
		public static SqliteDatabase Database
		{
			get
			{
				if (database == null)
				{
					database = new SqliteDatabase(DependencyService.Get<IFileHelper>().GetLocalFilePath("myForum.db3"));
				}
				return database;
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
    }
}
