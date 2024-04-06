package com.food_delivery.g1_a_order.config.mapper;

import com.food_delivery.g1_a_order.api.dto.address.*;
import com.food_delivery.g1_a_order.persistent.entity.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    // address create dto
    Address toAddress(AddressCreateDto addressCreateDto);
    AddressCreateDto toAddressCreateDto(Address address);

    // address show dto
    Address toAddress(AddressShowDto addressShowDto);
    AddressShowDto toAddressShowDto(Address address);
    List<AddressShowDto> toAddressShowDto(List<Address> address);
    List<Address> toAddress(List<AddressShowDto> addressShowDto);
}
