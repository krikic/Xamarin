using System;
using SQLite;
using Xamarin.Forms;
using System.Collections.Generic;
using System.Linq;

namespace AddressBook
{
    public class ContactDataService : IContactDataService
    {
        readonly SQLiteConnection data;
        public ContactDataService()
        {
            data = DependencyService.Get<ISQLite>().GetConnection();
            data.CreateTable<Contact>();
        }
        public void AddContact(string  firstName, string  lastName, string mobileNumber, string homeNumber, string notes)
        {
            var contact = new Contact {FirstName = firstName, LastName = lastName, MobileNumber = mobileNumber,HomeNumber = homeNumber, Notes = notes }; 
            data.Insert(contact);
        } 

        public void DeleteContact(int id)
        {
            data.Delete<Contact>(id);
        }

        public void UpdateContact(Contact contact)
        {
            data.Update(contact);
        }

        public List<Contact> GetContact()
        {                
            return data.Table<Contact>().ToList();
        } 

        public List<Contact> Filtering (string characters)
        { 
         return data.Table<Contact>().ToList().FindAll(
                    i => i.FirstName.StartsWith(characters, StringComparison.CurrentCulture));
        }
    }
}

