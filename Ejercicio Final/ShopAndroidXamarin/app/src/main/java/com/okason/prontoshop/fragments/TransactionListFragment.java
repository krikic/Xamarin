package com.okason.prontoshop.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.okason.prontoshop.R;
import com.okason.prontoshop.core.ProntoShopApplication;
import com.okason.prontoshop.core.ShoppingCart;
import com.okason.prontoshop.core.listeners.OnTransactionSelectedListener;
import com.okason.prontoshop.data.sqlite.DatabaseHelper;
import com.okason.prontoshop.models.Customer;
import com.okason.prontoshop.models.SalesTransaction;
import com.okason.prontoshop.adapter.TransactionAdapter;

import com.okason.prontoshop.util.Constants;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionListFragment extends Fragment
        implements OnTransactionSelectedListener{
    private View mRootView;
    private TransactionAdapter mAdapter;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;


    @BindView(R.id.transaction_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.empty_text) TextView mEmptyTextView;
    @Inject ShoppingCart mCart;
    @Inject Bus mBus;

    public TransactionListFragment() {
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
        mRootView = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        ButterKnife.bind(this, mRootView);
        ProntoShopApplication.getInstance().getAppComponent().inject(this);

        List<SalesTransaction> transactions = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new TransactionAdapter(transactions, this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSalesTransactions();
        try {
            mBus.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSalesTransactions() {
        List<SalesTransaction> transactions = getAllSalesTransactions();
        if (transactions != null && transactions.size() > 0){
            hideEmptyText();
            showSalesTransaction(transactions);
        }else {
            showEmptyText();
        }

    }

    private List<SalesTransaction> getAllSalesTransactions() {
        //initialize an empty list of transactions
        List<SalesTransaction> transactions = new ArrayList<>();

        //sql command to select all SalesTransactions;
        String selectQuery = "SELECT * FROM " + Constants.TRANSACTION_TABLE;

        //make sure the database is not empty
        if (mDatabase != null) {

            //get a cursor for all lineItems in the database
            Cursor cursor = mDatabase.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //get each transaction in the cursor
                    transactions.add(SalesTransaction.getSalesTransactionFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return transactions;
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mBus.unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelectTransaction(SalesTransaction transaction) {

    }

    @Override
    public Customer getCustomer(long id) {
        //Get the cursor representing the Customer
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + Constants.CUSTOMER_TABLE + " WHERE " +
                Constants.COLUMN_ID + " = '" + id + "'", null);

        //Create a variable of data type Customer
        Customer customer;
        if (cursor.moveToFirst()){
            customer = Customer.getCustomerFromCursor(cursor);
        }else {
            customer = null;
        }

        cursor.close();

        //Return result: either a valid customer or null
        return  customer;
    }

    public void showSalesTransaction(List<SalesTransaction> transactions) {
        mAdapter.replaceData(transactions);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }




}
