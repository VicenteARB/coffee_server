package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

    @Override
    public UserEntity registerUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
