using System;
using System.Collections.Generic;

namespace AddressBook
{
    public interface IContactDataService
    {
        void AddContact(string  firstName, string  lastName, string mobileNumber, string homeNumber, string notes);
        void DeleteContact(int id);
        void UpdateContact(Contact contact);
        List<Contact> GetContact();
        List<Contact> Filtering (string characters);

    }
}

