package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import cl.ucm.coffee.persitence.repository.UserRepository;
import cl.ucm.coffee.persitence.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    @Transactional
    public UserEntity registerUser(UserEntity userEntity) {
        try {
            Map<String, String> errors = validateUser(userEntity);
            if (!errors.isEmpty()) {
                throw new RuntimeException(errors.toString());
            }

            if (userRepository.existsById(userEntity.getUsername())) {
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

    private Map<String, String> validateUser(UserEntity userEntity) {
        Map<String, String> errors = new HashMap<>();

        if (userEntity.getPassword() == null || userEntity.getPassword().isEmpty()) {
            errors.put("password", "El campo Password no es valido.");
        }
        if (userEntity.getEmail() == null || userEntity.getEmail().isEmpty()) {
            errors.put("email", "El campo email no es valido.");
        }
        if (userEntity.getLocked() == null) {
            errors.put("locked", "El campo locked no es valido.");
        }
        if (userEntity.getDisabled() == null) {
            errors.put("disabled", "El campo disable no es valido.");
        }

        return errors;
    }
}
