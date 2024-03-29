package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.address.AddressCreateDto;
import com.food_delivery.g1_a_order.api.dto.address.AddressShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/address")
public class AddressController {
    private final AddressService addressService;
    @GetMapping
    public ResponseEntity<List<AddressShowDto>> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    // may be wanted
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add/address")
    public ResponseEntity<String> addAddress(@RequestBody AddressCreateDto addressCreateDto) {
        addressService.createAddress(addressCreateDto);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    @GetMapping("{id}/address")
    public ResponseEntity<AddressShowDto> getAddress(@PathVariable("id") Long addressId) {
        return ResponseEntity.ok(addressService.getAddress(addressId));
    }
}
