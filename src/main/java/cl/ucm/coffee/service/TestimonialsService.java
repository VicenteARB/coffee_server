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
        if (testimonialsEntity.getTestimonial() == null || testimonialsEntity.getTestimonial().isEmpty()) {
            throw new IllegalArgumentException("Testimonial no puede estar vacio");
        }
        if (testimonialsEntity.getUsername() == null || testimonialsEntity.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username no puede estar vacio");
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
