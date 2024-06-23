package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;

import java.util.List;
import java.util.Optional;

public interface ICoffeeService {

    List<CoffeeEntity> getCoffeeList();
    CoffeeEntity save(CoffeeEntity coffeeEntity);

    Optional<CoffeeEntity> findByName(String name);
    CoffeeEntity updateCoffee(CoffeeEntity coffeeEntity);
    void deleteCoffee(int idCoffee);
}
