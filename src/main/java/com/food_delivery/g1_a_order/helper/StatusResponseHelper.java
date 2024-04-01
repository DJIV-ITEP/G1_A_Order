package com.food_delivery.g1_a_order.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StatusResponseHelper {

    // throw exception
    public static void notFound(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    public static void notAcceptable(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, message);
    }

    public static void serverErr(String message) {
        throw new ResponseStatusException(HttpStatus.valueOf(500), message);
    }

    // return exception
    public static ResponseStatusException getServerErr(String message) {
        return new ResponseStatusException(HttpStatus.valueOf(500), message);
    }

    public static ResponseStatusException getNotFound(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    public static ResponseStatusException getNotAcceptable(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, message);
    }

}