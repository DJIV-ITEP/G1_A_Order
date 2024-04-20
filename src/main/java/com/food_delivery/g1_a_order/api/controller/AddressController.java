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

import com.food_delivery.g1_a_order.api.dto.response.ErrResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/customer/{customerId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressShowDto.class)))),
            @ApiResponse(responseCode = "404", description = "No address found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
    })
    public ResponseEntity<List<AddressShowDto>> getAddressesByCustomerId(@PathVariable("customerId") Long customerId) {
        List<AddressShowDto> addresses = addressService.getAddressesByCustomerId(customerId);
        return ResponseEntity.ok(addresses);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ResponseEntity<AddressShowDto> createAddress(@RequestBody AddressCreateDto addressCreateDto) {
        AddressShowDto address = addressService.createAddress(addressCreateDto);
        return ResponseEntity.status(201).body(address);
    }

    @DeleteMapping("/customer/{customerId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "No address found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
    })
    public ResponseEntity<String> deleteAddressesByCustomerId(@PathVariable("customerId") Long customerId) {
        addressService.deleteAddressesByCustomerId(customerId);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    @GetMapping("/{addressId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AddressShowDto.class))),
            @ApiResponse(responseCode = "404", description = "No address found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
    })
    public ResponseEntity<AddressShowDto> getAddress(@PathVariable("addressId") Long addressId) {
        AddressShowDto address = addressService.getAddress(addressId);
        return ResponseEntity.ok(address);
    }

    // may be wanted
    public ResponseEntity<String> addAddress(@RequestBody AddressCreateDto addressCreateDto) {
        addressService.createAddress(addressCreateDto);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }
}
