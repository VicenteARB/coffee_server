package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService implements ICoffeeService{

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public List<CoffeeEntity> getCoffeeList() {
        return coffeeRepository.findAll();
    }

    @Override
    public CoffeeEntity save(CoffeeEntity coffeeEntity) {
        return coffeeRepository.save(coffeeEntity);
    }

    @Override
    public Optional<CoffeeEntity> findByName(String name) {
        try {
            return coffeeRepository.findByName(name);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar el coffee con el nombre: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error", e);
        }
    }

    @Override
    public CoffeeEntity updateCoffee(CoffeeEntity coffeeEntity) {
        try {
            Optional<CoffeeEntity> existingCoffeeOptional = coffeeRepository.findById(coffeeEntity.getIdCoffee());

            if (!existingCoffeeOptional.isPresent()) {
                throw new RuntimeException("Coffee no encontrado.");
            }

            CoffeeEntity existingCoffee = existingCoffeeOptional.get();

            if (coffeeEntity.getName() != null) {
                existingCoffee.setName(coffeeEntity.getName());
            }
            if (coffeeEntity.getDescription() != null) {
                existingCoffee.setDescription(coffeeEntity.getDescription());
            }
            if (coffeeEntity.getPrice() != 0) {
                existingCoffee.setPrice(coffeeEntity.getPrice());
            }
            if (coffeeEntity.getImage64() != null) {
                existingCoffee.setImage64(coffeeEntity.getImage64());
            }

            return coffeeRepository.save(existingCoffee);
        } catch (RuntimeException e) {
            System.err.println("Error updating coffee: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error", e);
        }
    }

    @Override
    public void deleteCoffee(int idCoffee) {
        try {
            if (!coffeeRepository.existsById(idCoffee)) {
                throw new RuntimeException("Coffee no encontrado.");
            }
            coffeeRepository.deleteById(idCoffee);
        } catch (RuntimeException e) {
            System.err.println("Error deleting coffee: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error", e);
        }
    }


}
