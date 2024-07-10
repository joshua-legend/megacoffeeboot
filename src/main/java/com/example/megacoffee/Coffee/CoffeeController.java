package com.example.megacoffee.Coffee;

import com.example.megacoffee.ResponseMessage;
import com.example.megacoffee.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// http: request & response
// request: 메소드[get/post/put/delete/...]

// controller[경로 잡아주기 ]
// repository[데이터 가져오기 ]


@RestController
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("/all")
    public ResponseEntity<ResponseMessage<List<Coffee>>> getAllCoffees() {
        List<Coffee> coffees = coffeeRepository.findAll();
        ResponseMessage<List<Coffee>> response = new ResponseMessage<>(Status.OK, "성공 코드", coffees);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/coffee")
    public List<Coffee> getCoffee(@RequestParam String name) {
        return coffeeRepository.findByName(name);
    }

    @GetMapping("/coffeePrice")
    public List<Coffee> getCoffeeByPrice(@RequestParam int min, @RequestParam int max) {
        return coffeeRepository.findByPrice(min, max);
    }


    @PostMapping("/add")
    public String addCoffee(@RequestBody Coffee coffee) {
        coffeeRepository.save(coffee);
        return "성공";
    }

}
