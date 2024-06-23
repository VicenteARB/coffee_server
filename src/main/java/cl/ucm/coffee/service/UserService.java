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
import java.util.Optional;

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
        try {
            Optional<UserEntity> existingUser = userRepository.findById(userEntity.getUsername());
            if (existingUser.isPresent()) {
                throw new RuntimeException("Usuario existente.");
            }
            UserEntity savedUser = userRepository.save(userEntity);

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUsername(savedUser.getUsername());
            userRole.setRole("CUSTOMER");
            userRole.setGrantedDate(LocalDateTime.now());
            userRoleRepository.save(userRole);

            return savedUser;
        } catch (RuntimeException e) {
            System.err.println("Error registering user: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error", e);
        }
    }

    @Override
    @Transactional
    public UserEntity updateUser(String username, UserEntity userEntity) {
        try {
            Optional<UserEntity> existingUserOptional = userRepository.findById(username);
            if (existingUserOptional.isEmpty()) {
                throw new RuntimeException("Usuario no encontrado.");
            }

            UserEntity existingUser = existingUserOptional.get();

            if (userEntity.getPassword() != null) {
                existingUser.setPassword(userEntity.getPassword());
            }
            if (userEntity.getEmail() != null) {
                existingUser.setEmail(userEntity.getEmail());
            }
            if (userEntity.getLocked() != null) {
                existingUser.setLocked(userEntity.getLocked());
            }
            if (userEntity.getDisabled() != null) {
                existingUser.setDisabled(userEntity.getDisabled());
            }

            return userRepository.save(existingUser);
        } catch (RuntimeException e) {
            System.err.println("Error updating user: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error", e);
        }
    }

    @Override
    public Optional<UserEntity> getUser(String username) {
        try {
            return userRepository.findById(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error", e);
        }
    }
}
