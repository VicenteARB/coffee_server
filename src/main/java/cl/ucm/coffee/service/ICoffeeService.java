package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;

import java.util.List;

public interface ICoffeeService {

    List<CoffeeEntity> getCoffeeList();
    CoffeeEntity save(CoffeeEntity coffeeEntity);
}
