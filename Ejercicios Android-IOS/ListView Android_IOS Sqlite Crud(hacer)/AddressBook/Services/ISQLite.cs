using SQLite;

namespace AddressBook
{
    public interface ISQLite
    {
        SQLiteConnection GetConnection();
    }
}

