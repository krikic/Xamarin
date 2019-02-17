using System;

using Xamarin.Forms;
using System.Collections.Generic;
using System.Threading.Tasks;
using SQLite;

namespace myForum
{
    public class SqliteDatabase
    {
            readonly SQLiteAsyncConnection database;

            public SqliteDatabase(string dbPath)
            {
                database = new SQLiteAsyncConnection(dbPath);
                database.CreateTableAsync<ItemData>().Wait();
            }

            public Task<List<ItemData>> GetItemsAsync()
            {
                return database.Table<ItemData>().ToListAsync();
            }
           public Task<ItemData> Load(){

            return database.Table<ItemData>().FirstOrDefaultAsync();
           }

            public Task<int> SaveItemAsync(ItemData item)
            {
                if (item.ID != 0)
                {
                    return database.UpdateAsync(item);
                }
                else
                {
                    return database.InsertAsync(item);
                }
            }

            public void DeleteItemAsync()
            {
			  string deleteTable = "DELETE FROM ItemData;";
			  database.ExecuteAsync(deleteTable);
            }
        }
    }

