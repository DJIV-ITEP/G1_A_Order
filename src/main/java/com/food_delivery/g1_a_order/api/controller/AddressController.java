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

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AddressShowDto>> getAddressesByCustomerId(@PathVariable Long customerId) {
        List<AddressShowDto> addresses = addressService.getAddressesByCustomerId(customerId);
        return ResponseEntity.ok(addresses);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ResponseEntity<AddressShowDto> createAddress(@RequestBody AddressCreateDto addressCreateDto) {
        AddressShowDto address = addressService.createAddress(addressCreateDto);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteAddressesByCustomerId(@PathVariable Long customerId) {
        addressService.deleteAddressesByCustomerId(customerId);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressShowDto> getAddress(@PathVariable Long addressId) {
        AddressShowDto address = addressService.getAddress(addressId);
        return ResponseEntity.ok(address);
    }

    // may be wanted
    public ResponseEntity<String> addAddress(@RequestBody AddressCreateDto addressCreateDto) {
        addressService.createAddress(addressCreateDto);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }
}
