package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.address.AddressCreateDto;
import com.food_delivery.g1_a_order.api.dto.address.AddressShowDto;
import com.food_delivery.g1_a_order.config.mapper.AddressMapper;
import com.food_delivery.g1_a_order.persistent.entity.Address;
import com.food_delivery.g1_a_order.persistent.repository.AddressRepository;
import com.food_delivery.g1_a_order.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService extends BaseService {
    @Autowired
    AddressMapper addressMapper;

    private final AddressRepository addressRepository;
    
    @Qualifier("customerServiceWebClient")
    @Autowired
    private  WebClient customerEndpoint;

    public AddressShowDto createAddress(AddressCreateDto addressCreateDto) {
        Address address = addressMapper.toAddress(addressCreateDto);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toAddressShowDto(savedAddress);
    }

    @Transactional
    public List<AddressShowDto> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addressMapper.toAddressShowDto(addresses);
    }

    public AddressShowDto getAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> handleNotFound("Address not found with id: " + addressId));
        return addressMapper.toAddressShowDto(address);
    }

    public List<AddressShowDto> getAddressesByCustomerId(Long customerId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId)
                .orElseThrow(() -> handleNotFound("No addresses found for this customer"));
        return addressMapper.toAddressShowDto(addresses);
    }

    public AddressShowDto getFirstAddressByCustomerIdAndLocation(Long customerId, Double latitude, Double longitude) {
        Address address = addressRepository
                .findFirstByCustomerIdAndLatitudeAndLongitude(customerId, latitude, longitude)
                .orElseThrow(() -> handleServerError("No address found for this customer at the given location"));
        return addressMapper.toAddressShowDto(address);
    }

    public void deleteAddressesByCustomerId(Long customerId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId).get();

        if (addresses.isEmpty())
            handleNotFound("No addresses found for this customer");
            
        addressRepository.deleteAll(addresses);
    }

    public boolean addressExists(Long addressId) {
        return addressRepository.existsById(addressId);
    }

}
