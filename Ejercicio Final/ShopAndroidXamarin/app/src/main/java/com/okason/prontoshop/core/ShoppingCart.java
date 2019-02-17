package com.okason.prontoshop.core;

import android.content.SharedPreferences;
import android.util.Log;

import com.okason.prontoshop.core.events.CustomerSelectedEvent;
import com.okason.prontoshop.core.events.OnCartItemsChangeEvent;
import com.okason.prontoshop.models.Customer;
import com.okason.prontoshop.models.LineItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentine on 4/18/2016.
 */
public class ShoppingCart {
    private final SharedPreferences sharedPreferences;
    private Customer selectedCustomer;
    private List<LineItem> shoppingCart;

    private final static String LOG_TAG = ShoppingCart.class.getSimpleName();
    private static boolean DEBUG = true;

    public ShoppingCart(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        initShoppingCart();
    }


    private void initShoppingCart() {
        shoppingCart = new ArrayList<>();
        selectedCustomer = new Customer();
        populateToolbar();
    }

    public void addItemToCart(LineItem item) {
        boolean isItemInCart = false;
        int itemPosition = 0;
        for (LineItem tempItem : shoppingCart) {
            if (tempItem.getId() == item.getId()) {
                itemPosition = shoppingCart.indexOf(tempItem);
                LineItem selectedItem = shoppingCart.get(itemPosition);
                selectedItem.setQuantity(tempItem.getQuantity() + item.getQuantity());
                shoppingCart.set(itemPosition, selectedItem);
                isItemInCart = true;
                break;
            }
        }
        if (!isItemInCart) {
            shoppingCart.add(item);
        }
        populateToolbar();
    }

    public void clearShoppingCart(){
        if (DEBUG) {
            Log.d(LOG_TAG, "Clear Cart Called");
        }
        shoppingCart.clear();
        selectedCustomer = null;
        populateToolbar();
        ProntoShopApplication.getInstance()
                .getBus().post(new CustomerSelectedEvent("Customer Name"));
    }

    public void removeItemFromCart(LineItem item){
        shoppingCart.remove(item);
        if (shoppingCart.size() == 0){
            ProntoShopApplication.getInstance()
                    .getBus().post(new CustomerSelectedEvent("Customer Name"));
        }
        populateToolbar();
    }


    public void completeCheckout(){
        shoppingCart.clear();
        populateToolbar();
        ProntoShopApplication.getInstance()
                .getBus().post(new CustomerSelectedEvent("Customer Name"));
    }

    private void populateToolbar() {
        double total = 0;
        int qty = 0;

        for (LineItem lineItem: getShoppingCart()){
            total += lineItem.getSumPrice();
            qty += lineItem.getQuantity();
        }

        String selectedCustomerName = "";
        if (selectedCustomer != null && selectedCustomer.getCustomerName() != null && !selectedCustomer.getCustomerName().equals("")){
            selectedCustomerName = selectedCustomer.getCustomerName();
        }

        ProntoShopApplication.getInstance()
                .getBus().post(new CustomerSelectedEvent(selectedCustomerName));
        ProntoShopApplication.getInstance().getBus()
                .post(new OnCartItemsChangeEvent(total, qty));

    }

    public List<LineItem> getShoppingCart() {
        return shoppingCart;
    }

    public Customer getSelectedCustomer(){
        return selectedCustomer;
    }

    public void setCustomer(Customer customer){
        selectedCustomer = customer;
        ProntoShopApplication.getInstance()
                .getBus().post(new CustomerSelectedEvent(customer.getCustomerName()));

    }



    public void updateItemQty(LineItem item, int qty) {
        boolean itemAlreadyInCart = false;
        int position = 0;

        for (LineItem tempItem : shoppingCart) {
            if (tempItem.getId() == item.getId()) {
                position = shoppingCart.indexOf(tempItem);
                LineItem itemInCart = shoppingCart.get(position);
                itemInCart.setQuantity(qty);
                shoppingCart.set(position, itemInCart);
                itemAlreadyInCart = true;
                break;
            }
        }

        if (!itemAlreadyInCart) {
            item.setQuantity(qty);
            shoppingCart.add(item);
        }
        populateToolbar();
    }


}
