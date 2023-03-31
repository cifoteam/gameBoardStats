package org.teamcifo.tindergames.userEntity;

import org.springframework.data.repository.CrudRepository;
import org.teamcifo.tindergames.userEntity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}