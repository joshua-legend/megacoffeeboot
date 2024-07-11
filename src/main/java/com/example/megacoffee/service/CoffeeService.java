package com.example.megacoffee.service;

import com.example.megacoffee.model.Coffee;
import com.example.megacoffee.status.ResultStatus;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {
    ResultStatus addCoffee(Coffee coffee);
    List<Coffee> getAllCoffees();
    List<Coffee> getCoffeesByPriceRange(int minPrice, int maxPrice);
    List<Coffee> getCoffeesByName(String name);
    Optional<Coffee> getCoffeeById(Integer id);
}
