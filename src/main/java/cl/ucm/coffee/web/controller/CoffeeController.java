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
    public ResponseEntity<List<CoffeeEntity>> getcoffes(){
        return ResponseEntity.ok(coffeeService.getCoffeeList());
    }

    @PostMapping("/save")
    public ResponseEntity<?> setcoffe(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("image64") String image64) throws IOException {

        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName(name);
        coffeeEntity.setDescription(description);
        coffeeEntity.setPrice(price);
        coffeeEntity.setImage64(image64);

//        String base64Image = Base64.getEncoder().encodeToString(imageFile.getBytes());
//        coffeeEntity.setImage64(base64Image);

        CoffeeEntity createdCoffee = coffeeService.save(coffeeEntity);
        return ResponseEntity.ok(createdCoffee);
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

    @PostMapping("/update")
    public ResponseEntity<?> updateCoffee(@RequestBody CoffeeEntity coffeeEntity) {
        try {
            CoffeeEntity updatedCoffee = coffeeService.updateCoffee(coffeeEntity);
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
