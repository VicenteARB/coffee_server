package cl.ucm.coffee.web.controller;


import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.service.ICoffeeService;
import cl.ucm.coffee.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/coffee")
public class CoffeeController {

    @Autowired
    private ICoffeeService coffeeService;

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public ResponseEntity<?> getcoffes(){
        try {
            return ResponseEntity.ok(coffeeService.getCoffeeList());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> setCoffee(@RequestBody CoffeeEntity coffeeEntity) {
        try {
            CoffeeEntity createdCoffee = coffeeService.save(coffeeEntity);
            return ResponseEntity.ok(createdCoffee);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        try {
            Optional<CoffeeEntity> coffee = coffeeService.findByName(name);
            if (coffee.isPresent()) {
                return ResponseEntity.ok(coffee.get());
            } else {
                return ResponseEntity.status(404).body("Coffee not found");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateCoffee(@PathVariable int id, @RequestBody CoffeeEntity coffeeEntity) {
        try {
            CoffeeEntity updatedCoffee = coffeeService.updateCoffee(id, coffeeEntity);
            return ResponseEntity.ok(updatedCoffee);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoffee(@PathVariable int id) {
        try {
            coffeeService.deleteCoffee(id);
            return ResponseEntity.ok("Coffee deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
