package cl.ucm.coffee.persitence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testimonials")
@Getter
@Setter
@NoArgsConstructor
public class TestimonialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_testimonial", nullable = false)
    private int idTestimonials;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String testimonial;

    @Column(name="id_coffee", nullable = false, length = 30)
    private int idCoffee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_coffee", referencedColumnName = "id_coffee", insertable = false, updatable = false)
    @JsonBackReference("coffee-testimonials")
    private CoffeeEntity coffee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username", referencedColumnName = "username",  insertable = false, updatable = false, nullable = false)
    @JsonBackReference("user-testimonials")
    private UserEntity user;
}
