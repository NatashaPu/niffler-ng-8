package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.user.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserdataUserDao {

    UserEntity create(UserEntity user);
    void deleteUser (UserEntity user);
    Optional<UserEntity> findUserById(UUID id);
    Optional<UserEntity> findByUsername(String username);

}
