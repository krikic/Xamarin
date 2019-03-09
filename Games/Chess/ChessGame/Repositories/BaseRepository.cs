using SQLite;
using System.IO;

namespace ChessGame.Repositories
{
    class BaseRepository<T>
        where T : class, new()
    {
        protected SQLiteConnection Connection { get; }
        protected TableQuery<T> Table { get; }

        private static string Folder { get; } = System.Environment.GetFolderPath(System.Environment.SpecialFolder.ApplicationData);

        public BaseRepository(string fileName)
        {
            fileName = Path.Combine(Folder, fileName);
            Connection = new SQLiteConnection(fileName, true);
            Connection.CreateTable<T>();
            Table = Connection.Table<T>();
            Seed();
        }

        protected virtual void Seed()
        {
        }
    }
}