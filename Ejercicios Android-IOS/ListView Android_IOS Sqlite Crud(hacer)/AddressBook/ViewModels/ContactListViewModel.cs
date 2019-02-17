using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Input;
using Xamarin.Forms;

namespace AddressBook
{
    public class ContactListViewModel : BaseViewModel
    {
        INavigation _navigation;
        readonly IContactDataService _contactData;
        List<Contact> _contactList;
        string filter = "";

        public ContactListViewModel (INavigation navigation)
        {
            _navigation = navigation;
            _contactData = new ContactDataService();
            OpenContactPageCommand = new Command(OpenContactPage);
            DeleteCommand = new Command<int>(Delete);
            ReloadDataCommand = new Command(ReloadData);
         }
        public ICommand OpenContactPageCommand { get; private set; } 
        public ICommand DeleteCommand { get; private set; } 
        public ICommand ReloadDataCommand { get; private set; } 

        public List<Contact> ContactList
        {
            get 
            {
                return _contactList;
            }
            set 
            {
                if (_contactList != value)
                {
                    _contactList = value; 
                    RaisePropertyChanged();
                }
            } 
        }

        public string Filter
        {
            get 
            {
                return filter;
            }
            set 
            {
                if (filter != value)
                {
                    filter = value; 
                    RaisePropertyChanged();
                    Filtering(filter);
                }
            } 
        }

        void ReloadData ()
        { 
            ContactList = _contactData.GetContact();
        }


        async void OpenContactPage()
        {
            await _navigation.PushAsync(new ContactPage()); 
        }

        void Delete (int id)
        {
            _contactData.DeleteContact(id);
            ReloadData ();
        } 

        void Filtering (string characters)
        { 
            ContactList = _contactData.Filtering(characters);
        }
    }
}

