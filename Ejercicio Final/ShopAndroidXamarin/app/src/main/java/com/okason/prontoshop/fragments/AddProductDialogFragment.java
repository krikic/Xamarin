package com.okason.prontoshop.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.okason.prontoshop.R;
import com.okason.prontoshop.data.sqlite.DatabaseHelper;
import com.okason.prontoshop.models.Product;
import com.okason.prontoshop.util.Constants;
import com.okason.prontoshop.util.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddProductDialogFragment extends DialogFragment {


    private boolean mInEditMode = false;
    private static final String LOG_TAG = AddProductDialogFragment.class.getSimpleName();

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    @BindView(R.id.edit_text_product_name) EditText mNameEditText;
    @BindView(R.id.edit_text_product_description) EditText mDescriptionEditText;
    @BindView(R.id.edit_text_product_promo_message) EditText mPromoMessageEditText;
    @BindView(R.id.edit_text_product_sale_price) EditText mSalePriceEditText;
    @BindView(R.id.edit_text_product_purchase_price) EditText mPurchasePriceEditText;
    @BindView(R.id.auto_complete_category) AutoCompleteTextView mCategoryAutoComplete;
    @BindView(R.id.edit_text_product_image_path) EditText mImagePathEditText;




    public AddProductDialogFragment() {
        // Required empty public constructor
    }

    public static AddProductDialogFragment newInstance(long id){
        AddProductDialogFragment fragment = new AddProductDialogFragment();
        if (id > 0){
            Bundle args = new Bundle();
            args.putLong(Constants.COLUMN_ID, id);
            fragment.setArguments(args);
        }
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHelper = DatabaseHelper.newInstance(getActivity());
        mDatabase = mDBHelper.getWritableDatabase();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogFragment = new AlertDialog.Builder(getActivity());
        if (savedInstanceState == null){
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View rootView = inflater.inflate(R.layout.fragment_add_product, null);
            dialogFragment.setView(rootView);
            ButterKnife.bind(this, rootView);

            if (getArguments() != null && getArguments().containsKey(Constants.COLUMN_ID)){
                checkStatus(getArguments().getLong(Constants.COLUMN_ID));
            }

            View titleView = inflater.inflate(R.layout.dialog_title, null);
            TextView titleText = (TextView)titleView.findViewById(R.id.text_view_dialog_title);
            titleText.setText(mInEditMode ? "Update Product" : "Add Product");
            dialogFragment.setCustomTitle(titleView);

            initAutoComplete(getCategoryNames());

            dialogFragment.setPositiveButton(mInEditMode ? "Update" : "Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogFragment.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        }




        return dialogFragment.create();
    }

    private List<String> getCategoryNames() {
        return null;
    }

    private void checkStatus(long id) {

    }

    private void initAutoComplete(List<String> categoryNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, categoryNames);
        mCategoryAutoComplete.setAdapter(adapter);
    }



    public void populateForm(Product product) {
        mNameEditText.setText(product.getProductName());
        mDescriptionEditText.setText(product.getDescription());
        mPromoMessageEditText.setText(product.getPromoMessage());
        mCategoryAutoComplete.setText(product.getCategoryName());
        mSalePriceEditText.setText(Formatter.formatCurrency(product.getSalePrice()));
        mPurchasePriceEditText.setText(Formatter.formatCurrency(product.getPurchasePrice()));
        mImagePathEditText.setText(product.getImagePath());
    }


    public void displayMessage(String message) {

    }


    public void setEditMode(boolean editMode) {
        mInEditMode = editMode;
    }


    public void clearForm() {

    }

    private boolean validateInputs() {
        if (mNameEditText.getText().toString().isEmpty())
        {
            mNameEditText.setError(getString(R.string.name_is_required));
            mNameEditText.requestFocus();
            return false;
        }

        if (mSalePriceEditText.getText().toString().isEmpty())
        {
            mSalePriceEditText.setError(getString(R.string.price_is_required));
            mSalePriceEditText.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog)getDialog();

        if (d != null){
            Button positiveButton = (Button)d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean readyToCloseDialog = false;
                    if (validateInputs()) {
                        saveProduct();
                        readyToCloseDialog = true;
                    }
                    if (readyToCloseDialog)
                        dismiss();
                }
            });
        }
    }


    private void saveProduct() {
        NumberFormat baseFormat = NumberFormat.getCurrencyInstance();

        Product product = new Product();
        product.setProductName(mNameEditText.getText().toString());
        product.setDescription(mDescriptionEditText.getText().toString());
        product.setPromoMessage(mPromoMessageEditText.getText().toString());
        product.setCategoryName(mCategoryAutoComplete.getText().toString());
        product.setImagePath(mImagePathEditText.getText().toString());

        String salePriceText = mSalePriceEditText.getText().toString();
        double price = Double.parseDouble(TextUtils.isEmpty(salePriceText) ? (String) "0.00" : salePriceText);
        product.setSalePrice(price);

        String costPriceText = mPurchasePriceEditText.getText().toString();
        double cost = Double.parseDouble(TextUtils.isEmpty(costPriceText) ? (String) "0.00" : costPriceText);
        product.setPurchasePrice(cost);



        saveProductToDatbase(product);
    }

    private void saveProductToDatbase(Product product) {
        //ensure that the database exists
        if (mDatabase != null) {
            //prepare the transaction information that will be saved to the mDa
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_NAME, product.getProductName());
            values.put(Constants.COLUMN_DESCRIPTION, product.getDescription());
            values.put(Constants.COLUMN_PRICE, product.getSalePrice());
            values.put(Constants.COLUMN_PURCHASE_PRICE, product.getPurchasePrice());
            values.put(Constants.COLUMN_IMAGE_PATH, product.getImagePath());
           // values.put(Constants.COLUMN_CATEGORY_ID, createOrGetCategoryId(product.getCategoryName()));
            values.put(Constants.COLUMN_CATEGORY_NAME, product.getCategoryName());
            values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
            try {
                long id = mDatabase.insertOrThrow(Constants.PRODUCT_TABLE, null, values);
                Log.d(LOG_TAG, "Product Added");

            } catch (SQLException e) {
                Log.d(LOG_TAG, e.getCause() + " " + e.getMessage());
            }
        }

    }
}
