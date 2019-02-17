using System;
using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using System.Collections.Generic;

namespace FirstApp
{
    [Activity(Label = "Contact Book")]
    public class MainActivity : Activity
    {
        Button btnAdd, btnSearch;
        EditText txtSearch;
        ListView lv;
        IList<AddressBook> listItsms = null;
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            btnAdd = FindViewById<Button>(Resource.Id.contactList_btnAdd);
            btnSearch = FindViewById<Button>(Resource.Id.contactList_btnSearch);
            txtSearch = FindViewById<EditText>(Resource.Id.contactList_txtSearch);
            lv = FindViewById<ListView>(Resource.Id.contactList_listView);

            btnAdd.Click += delegate
            {
                var activityAddEdit = new Intent(this, typeof(AddEditAddressBookActivity));
                StartActivity(activityAddEdit);

            };

            btnSearch.Click += delegate
              {
                  LoadContactsInList();
              };

            LoadContactsInList();

        }

        private void LoadContactsInList()
        {
            AddressBookDbHelper dbVals = new AddressBookDbHelper(this);
            if (txtSearch.Text.Trim().Length < 1)
            {
                listItsms = dbVals.GetAllContacts();
            }
            else {

                listItsms = dbVals.GetContactsBySearchName(txtSearch.Text.Trim());
            }


            lv.Adapter = new ContactListBaseAdapter(this, listItsms);

            lv.ItemLongClick += lv_ItemLongClick;
        }

        private void lv_ItemLongClick(object sender, AdapterView.ItemLongClickEventArgs e)
        {
            AddressBook o = listItsms[e.Position];

            //  Toast.MakeText(this, o.Id.ToString(), ToastLength.Long).Show();

            var activityAddEdit = new Intent(this, typeof(AddEditAddressBookActivity));
            activityAddEdit.PutExtra("ContactId", o.Id.ToString());
            activityAddEdit.PutExtra("ContactName", o.FullName);
            StartActivity(activityAddEdit);
        }

    }
}

