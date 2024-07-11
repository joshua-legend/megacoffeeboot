package com.example.megacoffee.service;

import com.example.megacoffee.model.Coffee;
import com.example.megacoffee.repository.CoffeeRepository;
import com.example.megacoffee.status.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public ResultStatus addCoffee(Coffee coffee) {
        if (coffee.getName() == null || coffee.getName().isEmpty()) {
            return ResultStatus.FAIL;
        }
        if (coffee.getPrice() < 0) {
            return ResultStatus.FAIL;
        }
        coffeeRepository.save(coffee);
        return ResultStatus.SUCCESS;
    }

    @Override
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    @Override
    public List<Coffee> getCoffeesByPriceRange(int minPrice, int maxPrice) {
        return coffeeRepository.findByPrice(minPrice, maxPrice);
    }

    @Override
    public List<Coffee> getCoffeesByName(String name) {
        return coffeeRepository.findByName(name);
    }

    public Optional<Coffee> getCoffeeById(Integer id) {
        return coffeeRepository.findById(id);
    }
}
