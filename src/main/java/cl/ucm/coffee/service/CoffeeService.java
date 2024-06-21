package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService implements ICoffeeService{

    @Autowired
    private CoffeeRepository repository;

    @Override
    public List<CoffeeEntity> getCoffeeList() {
        return repository.findAll();
    }

    @Override
    public CoffeeEntity save(CoffeeEntity coffeeEntity) {
        return repository.save(coffeeEntity);
    }
}
