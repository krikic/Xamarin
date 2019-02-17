package com.okason.prontoshop.fragments;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.okason.prontoshop.core.listeners.OnProductSelectedListener;
import com.okason.prontoshop.data.SampleProductData;
import com.okason.prontoshop.data.sqlite.DatabaseHelper;
import com.okason.prontoshop.models.LineItem;
import com.okason.prontoshop.models.Product;
import com.okason.prontoshop.adapter.ProductListAdapter;
import com.okason.prontoshop.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment
        implements OnProductSelectedListener {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;
    private static final String LOG_TAG = ProductListFragment.class.getSimpleName();


    private View mRootView;
    private ProductListAdapter mAdapter;
    private AddProductDialogFragment mAddProductDialogFragment;
    private SharedPreferences sharedPreferences;


    @Inject ShoppingCart mCart;
    @BindView(R.id.product_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.empty_text) TextView mEmptyTextView;
    @BindView(R.id.fab) FloatingActionButton mFab;


    public ProductListFragment() {
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
        mRootView = inflater.inflate(R.layout.fragment_products_list, container, false);
        ButterKnife.bind(this, mRootView);
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAddProductForm();
            }
        });


        //setup RecyclerView
        List<Product> tempProducts = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ProductListAdapter(tempProducts, getContext(), this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        List<Product> availableProducts = getAllProductsFromDatabase();
        if (availableProducts != null && availableProducts.size() > 0){
            hideEmptyText();
            mAdapter.replaceData(availableProducts);

        }else {
            showEmptyText();
        }

    }

    private List<Product> getAllProductsFromDatabase() {
        //initialize an empty list of customers
        List<Product> products = new ArrayList<>();

        //sql command to select all Products;
        String selectQuery = "SELECT * FROM " + Constants.PRODUCT_TABLE;

        //make sure the database is not empty
        if (mDatabase != null) {

            //get a cursor for all products in the database
            Cursor cursor = mDatabase.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //get each product in the cursor
                    products.add(Product.getProductFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }

        if (products.size() < 1){
            products = SampleProductData.getSampleProducts();
            for (Product product: products){
                saveProductToDatbase(product);
            }
        }

        return products;
    }


    public void showAddProductForm() {
        mAddProductDialogFragment = AddProductDialogFragment.newInstance(0);
        mAddProductDialogFragment.show(getActivity().getFragmentManager(), "Dialog");

    }

    public void showEditProductForm(Product product) {
        AddProductDialogFragment dialog = AddProductDialogFragment.newInstance(product.getId());
        dialog.show(getActivity().getFragmentManager(), "Dialog");
    }

   public void showDeleteProductPrompt(final Product product) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View titleView = (View)inflater.inflate(R.layout.dialog_title, null);
        TextView titleText = (TextView)titleView.findViewById(R.id.text_view_dialog_title);
        titleText.setText("Delete Product?");
        alertDialog.setCustomTitle(titleView);

        alertDialog.setMessage("Delete " + product.getProductName());
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct(product);
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void deleteProduct(Product product) {
        //Todo - Delete product
    }



    public void showEmptyText() {
        mEmptyTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }


    public void hideEmptyText() {
        mEmptyTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showToastMessage(String message){
        Snackbar.make(mRootView.getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectProduct(Product selectedProduct) {
        LineItem item = new LineItem(selectedProduct, 1);
        mCart.addItemToCart(item);
    }

    @Override
    public void onLongClickProduct(Product clickedProduct) {
        showProductContextMenu(clickedProduct);
    }

    private void showProductContextMenu(final Product clickedProduct) {
        final  String[] sortOptions = { getString(R.string.edit), getString(R.string.delete), getString(R.string.add_to_cart), getString(R.string.google_search)};

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.dialog_list, null);
        alertDialog.setView(convertView);

        View titleView = (View)inflater.inflate(R.layout.dialog_title, null);
        TextView titleText = (TextView)titleView.findViewById(R.id.text_view_dialog_title);
        if (clickedProduct.getProductName() != null) {
            titleText.setText(clickedProduct.getProductName());
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
                        showEditProductForm(clickedProduct);
                        dialog.dismiss();
                        break;
                    case 1:
                        showDeleteProductPrompt(clickedProduct);
                        dialog.dismiss();
                        break;
                    case 2:
                        onSelectProduct(clickedProduct);
                        dialog.dismiss();
                        break;
                }
            }
        });

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
