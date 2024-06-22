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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam("image64") MultipartFile imageFile) throws IOException {

        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName(name);
        coffeeEntity.setDescription(description);
        coffeeEntity.setPrice(price);

        String base64Image = Base64.getEncoder().encodeToString(imageFile.getBytes());
        coffeeEntity.setImage64(base64Image);

        CoffeeEntity createdCoffee = coffeeService.save(coffeeEntity);
        return ResponseEntity.ok(createdCoffee);
    }
}
