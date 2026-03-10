package com.fooddeliveryapp.repository;

import com.fooddeliveryapp.model.user.Address;

import java.util.List;
import java.util.Map;

public interface IAddressRepository {
    List<Address> getAddressesByCustomerId(int id);
    Map<Integer,List<Address>> getAllAddresses();
    void save(Address address,int id);
    void deleteAllByCustomerId(int id);
}
