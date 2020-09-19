package itx.examples.springdata.service;

import itx.examples.springdata.entity.UserEntity;
import itx.examples.springdata.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public String createUser(String name, String email) {
        String id = UUID.randomUUID().toString();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        return id;
    }

    @Override
    public Collection<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void updateUser(String id, String name, Boolean enabled) {

    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userRepository.delete(userEntity);
    }

}
