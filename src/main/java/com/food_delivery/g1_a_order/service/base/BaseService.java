package com.food_delivery.g1_a_order.service.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class BaseService {
    protected ResponseStatusException handleNotFound(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
    protected ResponseStatusException handleNotAcceptable(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, message);
    }
    protected ResponseStatusException handleServerError(String message) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
    protected static void throwHandleNotAcceptable(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, message);
    }

}
