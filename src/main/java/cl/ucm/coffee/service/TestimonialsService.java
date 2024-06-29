package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.persitence.repository.TestimonialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialsService implements ITestimonialsService {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Override
    public TestimonialsEntity create(TestimonialsEntity testimonialsEntity) {
        // Validación básica
        if (testimonialsEntity.getTestimonial() == null || testimonialsEntity.getTestimonial().isEmpty()) {
            throw new IllegalArgumentException("Testimonial cannot be null or empty");
        }
        if (testimonialsEntity.getUsername() == null || testimonialsEntity.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (testimonialsEntity.getIdCoffee() <= 0) {
            throw new IllegalArgumentException("Invalid coffee ID");
        }

        return testimonialsRepository.save(testimonialsEntity);
    }

    @Override
    public List<TestimonialsEntity> findByCoffeeId(int coffeeId) {
        return testimonialsRepository.findByIdCoffee(coffeeId);
    }
}
