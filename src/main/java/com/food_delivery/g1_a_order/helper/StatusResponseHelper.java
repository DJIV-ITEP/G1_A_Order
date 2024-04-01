package com.food_delivery.g1_a_order.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StatusResponseHelper {

    public static void notFound(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    public static void notAcceptable(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, message);
    }

    public static void serverErr2(String message) {
      throw new ResponseStatusException(HttpStatus.valueOf(500), message);
    }
    public static ResponseStatusException serverErr(String message) {
        return new ResponseStatusException(HttpStatus.valueOf(500), message);
    }

}