using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace AddressBook
{
    public partial class ContactListPage : ContentPage
    {
        ContactListViewModel ViewModel
        {
            get { return (ContactListViewModel)BindingContext; }
        }

        public ContactListPage()
        {
            InitializeComponent();
            BindingContext = new ContactListViewModel ( this.Navigation); 

        }
        protected override void OnAppearing()
        {
            ViewModel.ReloadDataCommand.Execute(null);
        } 

        public void OnEdit (object sender, EventArgs e) {
            var contact = (Contact)((MenuItem)sender).CommandParameter;
            Navigation.PushAsync(new ContactPage(contact));
                
        }

        public void OnDelete (object sender, EventArgs e) {
            var contact = (Contact)((MenuItem)sender).CommandParameter;
            ViewModel.DeleteCommand.Execute(contact.Id);
        }
    }
}

