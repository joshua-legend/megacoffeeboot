package com.example.megacoffee.controller;

import com.example.megacoffee.dto.ApiResponse;
import com.example.megacoffee.model.Coffee;
import com.example.megacoffee.service.CoffeeService;
import com.example.megacoffee.status.ResponseStatus;
import com.example.megacoffee.status.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")

public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;


    @GetMapping("/coffees")
    public ResponseEntity<ApiResponse<List<Coffee>>> getAllCoffees() {
        List<Coffee> coffees = coffeeService.getAllCoffees();
        ApiResponse<List<Coffee>> response = new ApiResponse<>(ResponseStatus.SUCCESS, "Fetched all coffees", coffees);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Coffee>>> getCoffeesByName(@RequestParam String name) {
        List<Coffee> coffees = coffeeService.getCoffeesByName(name);
        ApiResponse<List<Coffee>> response = new ApiResponse<>(ResponseStatus.SUCCESS, "Fetched coffees by name", coffees);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/price")
    public ResponseEntity<ApiResponse<?>> getCoffeesByPriceRange(@RequestParam(required = true) int min,
                                                    @RequestParam(required = true) int max) {
        if (min < 0) {
            ApiResponse<String> response = new ApiResponse<>(ResponseStatus.FAIL, "Bad Request", "Success");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<Coffee> coffees = coffeeService.getCoffeesByPriceRange(min, max);
        ApiResponse<List<Coffee>> response = new ApiResponse<>(ResponseStatus.SUCCESS, "SUCCESS", coffees);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addCoffee(@RequestBody Coffee coffee) {
        ResultStatus result = coffeeService.addCoffee(coffee);
        if(ResultStatus.FAIL.equals(result)){
            ApiResponse<String> response = new ApiResponse<>(ResponseStatus.FAIL, "Bad Request", "Success");
            return ResponseEntity.ok(response);
        }
        ApiResponse<String> response = new ApiResponse<>(ResponseStatus.SUCCESS, "Coffee added successfully", "Success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/coffee/{id}")
    public ResponseEntity<ApiResponse<Coffee>> getCoffeeById(@PathVariable Integer id) {
        Optional<Coffee> coffee = coffeeService.getCoffeeById(id);
        if (coffee.isPresent()) {
            ApiResponse<Coffee> response = new ApiResponse<>(ResponseStatus.SUCCESS, "Fetched coffee by id", coffee.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Coffee> response = new ApiResponse<>(ResponseStatus.NOT_FOUND, "Coffee not found", null);
            return ResponseEntity.status(ResponseStatus.NOT_FOUND.getCode()).body(response);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<Object> getTest() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("엄준식");
    }

    @GetMapping("/example")
    public ResponseEntity<ApiResponse<String>> getExample() {
        ApiResponse<String> apiResponse = new ApiResponse<>(ResponseStatus.ERROR, "Bad request", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse<String> apiResponse = new ApiResponse<>(ResponseStatus.ERROR, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
