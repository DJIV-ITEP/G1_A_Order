package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.address.AddressCreateDto;
import com.food_delivery.g1_a_order.api.dto.address.AddressShowDto;
import com.food_delivery.g1_a_order.config.mapper.AddressMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.Address;
import com.food_delivery.g1_a_order.persistent.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService {
    @Autowired
    AddressMapper addressMapper;

    private final AddressRepository addressRepository;
    private final WebClient customerEndpoint;

    @Transactional
    public List<AddressShowDto> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addressMapper.toAddressShowDto(addresses);
    }
    @Transactional
    public boolean createAddress(AddressCreateDto AddressCreateDto) {
        Address address = addressMapper.toAddress(AddressCreateDto);
        return addressRepository.save(address).equals(address);
    }

    @Transactional
    public AddressShowDto getAddress(Long addressId) {
        Address address = null;
        try {
            address = addressRepository.findById(addressId).get();
        } catch (Exception e) {
            System.out.println(e);
            StatusResponseHelper.notFound("no address found");
        }
        return addressMapper
                .toAddressShowDto(address);
    }
}
