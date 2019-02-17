using System;
using Xamarin.Forms;
using SQLite;
using AddressBook.iOS;
using System.IO;


[assembly: Dependency(typeof(SQLiteIOS))] 
namespace AddressBook.iOS
{
    public class SQLiteIOS : ISQLite
    {
        SQLiteConnection ISQLite.GetConnection()
        {
            var sqliteFilename = "databaseSqlite.db3";
            var documentsPath = Environment.GetFolderPath(Environment.SpecialFolder.Personal); // Documents folder
            var libraryPath = Path.Combine(documentsPath, "..", "Library"); // Library folder
            var path = Path.Combine(libraryPath, sqliteFilename);
            // Create the connection
            var conn = new SQLiteConnection(path);
            // Return the database connection 
            return conn;
        }
    }
} 