using System;
using AddressBook.Droid;
using System.IO;
using Xamarin.Forms;


[assembly: Dependency (typeof (SQLiteDroid))]
namespace AddressBook.Droid
{
    public class SQLiteDroid : ISQLite
    { 
        public SQLite.SQLiteConnection GetConnection ()
        {
            var sqliteFilename = "TodoSQLite.db3";
            string documentsPath = Environment.GetFolderPath(System.Environment.SpecialFolder.Personal); // Documents folder
            var path = Path.Combine(documentsPath, sqliteFilename);
            // Create the connection
            var conn = new SQLite.SQLiteConnection(path);
            // Return the database connection
            return conn;
        }
    }
}

