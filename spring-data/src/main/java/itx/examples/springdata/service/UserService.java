package itx.examples.springdata.service;

import itx.examples.springdata.entity.UserEntity;

import java.util.Collection;

public interface UserService {

    String createUser(String name, String email);

    Collection<UserEntity> getAll();

    void updateUser(String id, String name, Boolean enabled);

    void deleteUser(String id);

}
