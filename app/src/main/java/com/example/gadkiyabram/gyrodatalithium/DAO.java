package com.example.gadkiyabram.gyrodatalithium;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(int _id);
    List<T> getAllBatteries();
}
