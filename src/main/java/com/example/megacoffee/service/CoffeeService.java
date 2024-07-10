package com.example.megacoffee.service;

import com.example.megacoffee.model.Coffee;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {
    String addCoffee(Coffee coffee);
    List<Coffee> getAllCoffees();
    List<Coffee> getCoffeesByPriceRange(int minPrice, int maxPrice);
    List<Coffee> getCoffeesByName(String name);
    Optional<Coffee> getCoffeeById(Integer id);
}
