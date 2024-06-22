package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import cl.ucm.coffee.persitence.repository.UserRepository;
import cl.ucm.coffee.persitence.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserEntity> getUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

//    @Override
//    public UserEntity registerUser(UserEntity userEntity) {
//        return userRepository.save(userEntity);
//    }

    @Transactional
    public UserEntity registerUser(UserEntity userEntity) {
        UserEntity savedUser = userRepository.save(userEntity);

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUsername(savedUser.getUsername());
        userRole.setRole("CUSTOMER");
        userRole.setGrantedDate(LocalDateTime.now());
        userRoleRepository.save(userRole);

        return savedUser;
    }
}
