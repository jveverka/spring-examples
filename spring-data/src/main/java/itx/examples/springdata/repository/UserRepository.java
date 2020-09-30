package itx.examples.springdata.repository;

import itx.examples.springdata.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("select user from UserEntity user where user.email = :email")
    List<UserEntity> findByEmail(@Param("email") String email);

    @Query("select user from UserEntity user where user.name = :name")
    List<UserEntity> findByName(@Param("name") String name);

}
