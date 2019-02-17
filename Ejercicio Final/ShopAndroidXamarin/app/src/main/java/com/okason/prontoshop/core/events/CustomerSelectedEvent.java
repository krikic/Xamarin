package com.okason.prontoshop.core.events;

import com.okason.prontoshop.models.Customer;

/**
 * Created by Valentine on 4/10/2016.
 */
public class CustomerSelectedEvent {
    private final String customerName;


    public CustomerSelectedEvent(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }


}
