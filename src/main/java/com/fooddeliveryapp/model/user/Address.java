package com.fooddeliveryapp.model.user;

public class Address {
//    private int id;
    private String addressName;
    private int customerId;
    private int counter = 0;

    public Address(String addressName, int customerId) {
//        this.id = ++counter;
        this.addressName = addressName;
        this.customerId = customerId;
    }

    public String getAddressName() {
        return addressName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCounter() {
        return counter;
    }

//    public int getId() {
//        return id;
//    }
}
