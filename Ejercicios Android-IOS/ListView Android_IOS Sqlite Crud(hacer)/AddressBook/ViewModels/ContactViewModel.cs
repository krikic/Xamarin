using System;
using System.Windows.Input;
using Xamarin.Forms;

namespace AddressBook
{
    public class ContactViewModel : BaseViewModel
    { 
        string _firstName;
        string _lastName;
        string _mobNumber;
        string _homeNumber;
        string _notes;
        readonly IContactDataService _contactData;
        INavigation _navigation;


        public ContactViewModel( INavigation navigation)
        {
            _navigation = navigation;
            _contactData = new ContactDataService();
            AddContactCommand = new Command(NewContact); 
        }

        public ContactViewModel (INavigation navigation, Contact contact) 
        {
            _navigation = navigation;
            _contactData = new ContactDataService();
            FillingCurrentContact(contact);
            AddContactCommand = new Command(() => NewContact(contact)); 
        }

        public ICommand AddContactCommand { get; private set;}
 
        public string FirstName
        {
            get  { return _firstName; }
            set  {
                if (_firstName != value)
                {
                    _firstName = value; 
                    RaisePropertyChanged();
                }
            } 
        }

        public string LastName
        {
            get  { return _lastName; }
            set  {
                if (_lastName != value)
                {
                    _lastName = value; 
                    RaisePropertyChanged();
                }
            } 
        }

        public string MobNumber
        {
            get  {  return _mobNumber;  }
            set  {
                if (_mobNumber != value)
                {
                    _mobNumber = value; 
                    RaisePropertyChanged();
                }
            } 
        }

        public string HomeNumber
        {
            get  { return _homeNumber;  }
            set  {
                if (_homeNumber != value)
                {
                    _homeNumber = value; 
                    RaisePropertyChanged();
                }
            } 
        }

        public string Notes
        {
             get  { return _notes;  }
            set  {
                if (_notes != value)
                {
                    _notes = value; 
                    RaisePropertyChanged();
                }
            } 
        }

        void NewContact()
        { 
            _contactData.AddContact(FirstName, LastName, MobNumber, HomeNumber, Notes); 
            _navigation.PopAsync(true);
        }

        void NewContact(Contact contact)
        { 
            contact.FirstName = FirstName;
            contact.LastName = LastName;
            contact.MobileNumber =  MobNumber;
            contact.HomeNumber =  HomeNumber;
            contact.Notes = Notes;
            _contactData.UpdateContact(contact);  
            _navigation.PopAsync(true);

        }  

        void FillingCurrentContact (Contact contact)
        {
            FirstName = contact.FirstName;
            LastName = contact.LastName;
            MobNumber = contact.MobileNumber;
            HomeNumber = contact.HomeNumber;
            Notes = contact.Notes; 
        }
    }
} 