package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> getUsers();
    UserEntity registerUser(UserEntity userEntity);
}
