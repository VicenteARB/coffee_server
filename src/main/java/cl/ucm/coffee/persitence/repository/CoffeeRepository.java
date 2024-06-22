package cl.ucm.coffee.persitence.repository;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Integer> {
}
