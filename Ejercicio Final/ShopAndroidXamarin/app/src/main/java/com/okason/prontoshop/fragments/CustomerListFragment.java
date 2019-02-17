package com.okason.prontoshop.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.okason.prontoshop.R;
import com.okason.prontoshop.core.ProntoShopApplication;
import com.okason.prontoshop.core.ShoppingCart;
import com.okason.prontoshop.core.listeners.OnCustomerSelectedListener;
import com.okason.prontoshop.data.SampleCustomerData;
import com.okason.prontoshop.data.SampleProductData;
import com.okason.prontoshop.data.sqlite.DatabaseHelper;
import com.okason.prontoshop.models.Customer;
import com.okason.prontoshop.adapter.CustomerListAdapter;
import com.okason.prontoshop.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment implements
        OnCustomerSelectedListener{

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;
    private static final String LOG_TAG = CustomerListFragment.class.getSimpleName();

    private View mRootView;
    private CustomerListAdapter mAdapter;
    @Inject ShoppingCart mCart;


    @BindView(R.id.customer_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.empty_text) TextView mEmptyTextView;
    @BindView(R.id.fab ) FloatingActionButton mFab;


    public CustomerListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHelper = DatabaseHelper.newInstance(getActivity());
        mDatabase = mDBHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_customer_list, container, false);
        ButterKnife.bind(this, mRootView);
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCustomerForm();
            }
        });

        //setup RecyclerView
        List<Customer> tempCustomers = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CustomerListAdapter(tempCustomers, this, getContext() );
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCustomers();
    }

    private void loadCustomers() {
        List<Customer> availableCustomers = getAllCustomersFromDatabase();
        if (availableCustomers != null && availableCustomers.size() > 0){
            hideEmptyText();
            showCustomers(availableCustomers);
        }else {
            showEmptyText();
        }

    }

    private List<Customer> getAllCustomersFromDatabase() {
        //initialize an empty list of customers
        List<Customer> customers = new ArrayList<>();

        //sql command to select all Customers;
        String selectQuery = "SELECT * FROM " + Constants.CUSTOMER_TABLE;

        //make sure the database is not empty
        if (mDatabase != null) {

            //get a cursor for all customers in the database
            Cursor cursor = mDatabase.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //get each customer in the cursor
                    customers.add(Customer.getCustomerFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }

        if (customers.size() < 1){
            customers = SampleCustomerData.getCustomers();
            for (Customer customer: customers){
                saveCustomerToDatabase(customer);
            }
        }


        return customers;
    }


    public void showCustomers(List<Customer> customers) {
        mAdapter.replaceData(customers);
    }


    public void showAddCustomerForm() {
        AddCustomerDialogFragment mAddCustomerFragment = new AddCustomerDialogFragment();
        mAddCustomerFragment.show(getActivity().getFragmentManager(), "Dialog");

    }


    public void showDeleteCustomerPrompt(final Customer customer) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Delete Customer?");
        dialog.setMessage("Are you delete " + customer.getCustomerName() + " ?");
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCustomer(customer);
            }
        });
        dialog.show();
    }

    private void deleteCustomer(Customer customer) {

    }


    public void showEditCustomerForm(Customer customer) {
        AddCustomerDialogFragment fragment = AddCustomerDialogFragment.newInstance(customer.getId());
        fragment.show(getActivity().getFragmentManager(), "Dialog");
    }



    public void showEmptyText() {
        mEmptyTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }


    public void hideEmptyText() {
        mEmptyTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    public void showMessage(String message) {
        showToastMessage(message);
    }


    private void showToastMessage(String message){
        Snackbar.make(mRootView.getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectCustomer(Customer customer) {
        mCart.setCustomer(customer);
    }

    @Override
    public void onLongClickProduct(Customer clickedCustomer) {
        showCustomerContextMenu(clickedCustomer);
    }

    private void showCustomerContextMenu(final Customer clickedCustomer) {
        final  String[] sortOptions = { getString(R.string.edit), getString(R.string.delete), getString(R.string.select_customer)};

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.dialog_list, null);
        alertDialog.setView(convertView);

        View titleView = (View)inflater.inflate(R.layout.dialog_title, null);
        TextView titleText = (TextView)titleView.findViewById(R.id.text_view_dialog_title);
        if (clickedCustomer.getCustomerName() != null) {
            titleText.setText(clickedCustomer.getCustomerName());
        }
        alertDialog.setCustomTitle(titleView);

        ListView dialogList = (ListView) convertView.findViewById(R.id.dialog_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_list_item_1, sortOptions);
        dialogList.setAdapter(adapter);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final Dialog dialog = alertDialog.create();
        dialog.show();
        dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showEditCustomerForm(clickedCustomer);
                        dialog.dismiss();
                        break;
                    case 1:
                        showDeleteCustomerPrompt(clickedCustomer);
                        dialog.dismiss();
                        break;
                    case 2:
                        onSelectCustomer(clickedCustomer);
                        dialog.dismiss();
                        break;
                }
            }
        });

    }

    private void saveCustomerToDatabase(Customer customer) {
        if (mDatabase != null){
            //prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_NAME, customer.getCustomerName());
            values.put(Constants.COLUMN_EMAIL, customer.getEmailAddress());
            values.put(Constants.COLUMN_PHONE, customer.getPhoneNumber());
            values.put(Constants.COLUMN_IMAGE_PATH, customer.getProfileImagePath());
            values.put(Constants.COLUMN_STREET1, customer.getStreetAddress());
            values.put(Constants.COLUMN_STREET2, customer.getStreetAddress2());
            values.put(Constants.COLUMN_CITY, customer.getCity());
            values.put(Constants.COLUMN_STATE, customer.getCity());
            values.put(Constants.COLUMN_ZIP, customer.getPostalCode());
            values.put(Constants.COLUMN_NOTE, customer.getNote());
            values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
            try {
                mDatabase.insertOrThrow(Constants.CUSTOMER_TABLE, null, values);
                Log.d(LOG_TAG, "Customer Added");

            } catch (SQLException e) {
                Log.d(LOG_TAG, "Error " + e.getCause() + " " + e.getMessage());
            }
        }
    }
}
