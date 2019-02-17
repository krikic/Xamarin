package com.okason.prontoshop.fragments;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.okason.prontoshop.R;
import com.okason.prontoshop.core.ProntoShopApplication;
import com.okason.prontoshop.core.ShoppingCart;
import com.okason.prontoshop.core.listeners.CartActionsListener;
import com.okason.prontoshop.data.sqlite.DatabaseHelper;
import com.okason.prontoshop.models.LineItem;
import com.okason.prontoshop.adapter.CheckoutAdapter;
import com.okason.prontoshop.models.SalesTransaction;
import com.okason.prontoshop.util.Constants;
import com.okason.prontoshop.util.Formatter;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckoutFragment extends Fragment implements
        CartActionsListener {

    private static String LOG_TAG = AddCustomerDialogFragment.class.getSimpleName();

    private View mRootView;
    private CheckoutAdapter mAdapter;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;


    @BindView(R.id.cart_list_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.empty_text) TextView mEmptyTextView;
    @BindView(R.id.clear_cart_button) Button mClearButton;
    @BindView(R.id.check_out_button) Button mCheckoutButton;
    @BindView(R.id.text_view_sub_total) TextView mSubTotalTextView;
    @BindView(R.id.text_view_total_amount) TextView mTotalTextView;
    @BindView(R.id.text_view_tax_rate) TextView mTotalTaxRate;
    @BindView(R.id.text_view_tax_value) TextView mTotalTaxValue;
    @BindView(R.id.radio_group_payment_type)     RadioGroup mRadioGroup;

    @Inject Bus mBus;
    @Inject ShoppingCart mCart;

    private double subtotal, total, tax;
    private double taxRate = 0.08;
    private String selectedPaymentType = "";
    private boolean paid = false;

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    private String paymentType;


    public CheckoutFragment() {
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
        mRootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, mRootView);
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
        

        List<LineItem> tempProducts = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CheckoutAdapter(tempProducts, getContext(), this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

    @Override
    public void onResume() {
        loadLineItems();
        super.onResume();
        try {
            mBus.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadLineItems() {
        List<LineItem> availableProducts = mCart.getShoppingCart();
        calculateTotals(availableProducts);
        if (availableProducts != null && availableProducts.size() > 0){
            hideEmptyText();
            mAdapter.replaceData(availableProducts);
        }else {
            showEmptyText();
        }
    }

    private void calculateTotals(List<LineItem> availableProducts) {
        subtotal = 0.0;
        total = 0.0;
        tax = 0.0;

        for (LineItem item: availableProducts){
            subtotal += item.getSumPrice();
        }

        tax = subtotal * taxRate;
        total = tax + subtotal;
        showCartTotals(tax, subtotal, total);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBus != null){
            try {
                mBus.unregister(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnCheckedChanged(R.id.button_cash) void onCheckedCash(boolean checked){
        if (checked){
            setPaymentType(getString(R.string.payment_type_cash));
            setPaid(false);
        }
    }

    @OnCheckedChanged(R.id.button_card)
    void onCheckedCard(boolean checked) {
        if (checked) {
            setPaymentType(getString(R.string.payment_type_card));
            setPaid(true);
        }
    }

    @OnCheckedChanged(R.id.button_paypal) void onCheckedPaypal(boolean checked){
        if (checked){
            setPaymentType(getString(R.string.payment_type_paypal));
            setPaid(true);
        }
    }


    @OnClick(R.id.check_out_button)
    public void onClickCheckoutButton(View view){
        showConfirmCheckout();
    }

    @OnClick(R.id.clear_cart_button)
    public void onClickClearCart(View view){
        showConfirmClearCart();
    }


    public void showEmptyText() {
        mEmptyTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }


    public void showCartTotals(double tax, double subtotal, double total) {
        mTotalTextView.setText(Formatter.formatCurrency(total));
        mSubTotalTextView.setText(Formatter.formatCurrency(subtotal));
        mTotalTaxValue.setText(Formatter.formatCurrency(tax));
    }



    public void showConfirmCheckout() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View titleView = (View)inflater.inflate(R.layout.dialog_title, null);
        TextView titleText = (TextView)titleView.findViewById(R.id.text_view_dialog_title);
        titleText.setText("Complete Checkout?");
        dialog.setCustomTitle(titleView);

        dialog.setMessage("Are you ready to complete Checkout");
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkout();

            }
        });
        dialog.show();

    }

    private void checkout() {
        SalesTransaction transaction = new SalesTransaction();
        transaction.setCustomerId(mCart.getSelectedCustomer().getId());
        transaction.setLineItems(mCart.getShoppingCart());
        transaction.setTaxAmount(tax);
        transaction.setSubTotalAmount(subtotal);
        transaction.setTotalAmount(total);
        transaction.setPaymentType(selectedPaymentType);
        transaction.setPaid(paid);

        List<LineItem> lineItems = mCart.getShoppingCart();

        //Save the Transaction to the database and obtain the Id
        long id = saveTransactionToDatabase(transaction);

        //Now save the LineItems
        if (id != -1) {
            for (LineItem lineItem: lineItems){
                saveLineItemToDatabase(id, lineItem);
            }
        }
        mCart.clearShoppingCart();

    }

    private void saveLineItemToDatabase(long id, LineItem lineItem) {
        if (mDatabase != null) {
            //prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_QUANTITY, lineItem.getQuantity());
            values.put(Constants.COLUMN_TRANSACTION_ID, id);
            values.put(Constants.COLUMN_PRODUCT_ID, lineItem.getId());
            values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
            try {
                mDatabase.insertOrThrow(Constants.LINEITEM_TABLE, null, values);
                Log.d(LOG_TAG, "LineItem Added");
            } catch (SQLException e) {
                Log.d(LOG_TAG, (e.getCause() + " " + e.getMessage()));
            }
        }

    }

    private long saveTransactionToDatabase(SalesTransaction transaction) {
        //ensure that the database exists
        long result = -1;
        if (mDatabase != null) {
            //prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_CUSTOMER_ID, transaction.getCustomerId());
            values.put(Constants.COLUMN_PAYMENT_STATUS, transaction.isPaid());
            values.put(Constants.COLUMN_PAYMENT_TYPE, transaction.getPaymentType());
            values.put(Constants.COLUMN_TAX_AMOUNT, transaction.getTaxAmount());
            values.put(Constants.COLUMN_SUB_TOTAL_AMOUNT, transaction.getSubTotalAmount());
            values.put(Constants.COLUMN_TOTAL_AMOUNT, transaction.getTotalAmount());
            values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
            try {
                result = mDatabase.insertOrThrow(Constants.TRANSACTION_TABLE, null, values);
                Log.d(LOG_TAG, "Transaction saved");
            } catch (SQLException e) {
                Log.d(LOG_TAG, e.getCause() + " " + e.getMessage());
            }
        }
        return result;
    }


    public void showConfirmClearCart() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View titleView = (View)inflater.inflate(R.layout.dialog_title, null);
        TextView titleText = (TextView)titleView.findViewById(R.id.text_view_dialog_title);
        titleText.setText("Clear Shopping Cart?");
        dialog.setCustomTitle(titleView);

        dialog.setMessage("Are you ready to clear all items from shopping checkout?");
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCart.clearShoppingCart();
            }
        });
        dialog.show();

    }


    public void hideEmptyText() {
        mEmptyTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }


    @Override
    public void onItemDeleted(LineItem item) {
        onItemDeleted(item);
    }

    @Override
    public void onItemQtyChange(LineItem item, int qty) {
        mCart.updateItemQty(item, qty);
    }

    private void showToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
