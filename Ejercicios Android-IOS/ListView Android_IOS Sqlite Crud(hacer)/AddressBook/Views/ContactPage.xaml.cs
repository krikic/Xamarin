using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace AddressBook
{
    public partial class ContactPage : ContentPage
    {
        public ContactPage( )
        {
            InitializeComponent();
            BindingContext = new ContactViewModel ( this.Navigation); // HERE

        }
        public ContactPage(Contact contact)
        {
            InitializeComponent();
            BindingContext = new ContactViewModel ( this.Navigation, contact); // HERE

        }
    }
}

