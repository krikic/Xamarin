package com.okason.prontoshop.core.events;

/**
 * Created by vokafor on 11/19/2016.
 */

public class OnCartItemsChangeEvent {

    private final double totalAmount;
    private final int numberOfItems;

    public OnCartItemsChangeEvent(double totalAmount, int numberOfItems) {
        this.totalAmount = totalAmount;
        this.numberOfItems = numberOfItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }
}
