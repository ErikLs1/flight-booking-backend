package com.demo.flight_booking.service;

import java.util.List;

public interface BasicService <T, ID> {
    T create(T dto);
    T update(ID id, T dto);
    T getById(ID id);
    List<T> getAll();
    void delete(ID id);
}
