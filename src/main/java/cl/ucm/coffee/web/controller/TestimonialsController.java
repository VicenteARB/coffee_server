package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.service.ITestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
public class TestimonialsController {

    @Autowired
    private ITestimonialsService testimonialsService;

    @PostMapping("/create")
    public ResponseEntity<?> createTestimonial(@RequestBody TestimonialsEntity testimonialsEntity) {
        try {
            TestimonialsEntity createdTestimonial = testimonialsService.create(testimonialsEntity);
            return ResponseEntity.ok(createdTestimonial);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/findByCoffeeId/{coffeeId}")
    public ResponseEntity<?> findByCoffeeId(@PathVariable int coffeeId) {
        try {
            List<TestimonialsEntity> testimonials = testimonialsService.findByCoffeeId(coffeeId);
            return ResponseEntity.ok(testimonials);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
