package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserEntity> getUsers();
    UserEntity registerUser(UserEntity userEntity);
    UserEntity updateUser(String username, UserEntity userEntity);
    Optional<UserEntity> getUser(String username);
}
