package itx.examples.springdata.service;

import itx.examples.springdata.entity.AddressEntity;
import itx.examples.springdata.entity.UserEntity;
import itx.examples.springdata.repository.AddressRepository;
import itx.examples.springdata.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserServiceImpl(UserRepository userRepository,
                           AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    @Override
    public String createUser(String name, String email) {
        String id = UUID.randomUUID().toString();
        AddressEntity address = createAddress();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setAddress(address);
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setEnabled(true);
        addressRepository.save(address);
        userRepository.save(userEntity);
        return id;
    }

    @Override
    public Collection<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userRepository.delete(userEntity);
    }

    private AddressEntity createAddress() {
        String id = UUID.randomUUID().toString();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet("street");
        addressEntity.setCity("city");
        return addressEntity;
    }

}
