using System;

using Xamarin.Forms;
using SQLite;

namespace myForum
{
    public class ItemData
    {
    	[PrimaryKey, AutoIncrement]
    	public int ID { get; set; }
    	public string Username { get; set; }
    	public string Password { get; set; }
    }
}

