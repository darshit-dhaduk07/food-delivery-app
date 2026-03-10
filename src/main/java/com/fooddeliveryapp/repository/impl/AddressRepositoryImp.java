package com.fooddeliveryapp.repository.impl;

import com.fooddeliveryapp.model.user.Address;
import com.fooddeliveryapp.repository.IAddressRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressRepositoryImp implements IAddressRepository {
    private final Map<Integer,List<Address>> customerIdToAddressMap;
    private static AddressRepositoryImp addressRepositoryImp;

    private AddressRepositoryImp()
    {
        customerIdToAddressMap = new HashMap<>();
    }

    public static AddressRepositoryImp getInstance()
    {
        if(addressRepositoryImp == null)
        {
            addressRepositoryImp = new AddressRepositoryImp();
        }
        return addressRepositoryImp;
    }

    @Override
    public List<Address> getAddressesByCustomerId(int id) {
        return customerIdToAddressMap.get(id);
    }

    @Override
    public Map<Integer, List<Address>> getAllAddresses() {
        return customerIdToAddressMap;
    }

    @Override
    public void save(Address address, int id) {
        customerIdToAddressMap.get(id).add(address);
    }

    @Override
    public void deleteAllByCustomerId(int id) {
        customerIdToAddressMap.get(id).clear();
    }
}
