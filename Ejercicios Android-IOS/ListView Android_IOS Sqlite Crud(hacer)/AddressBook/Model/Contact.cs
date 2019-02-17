using SQLite;

namespace AddressBook
{
    public class Contact
    {
        [PrimaryKey,AutoIncrement]
        public int Id { get; set;}
        public string FirstName { get; set;}
        public string LastName { get; set;}
        public string MobileNumber { get; set;}
        public string HomeNumber { get; set;}
        public string Notes { get; set;} 
 
    }
}

