package com.edu.miu.repository;

import com.edu.miu.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CassandraRepository<User, Long> {
    @AllowFiltering
    Optional<User> findByEmail(String email);
//    @AllowFiltering
//    Optional<User> findByUsernameOrEmail(String username, String email);
    @AllowFiltering
    List<User> findByIdIn(List<Long> userIds);
    @AllowFiltering
    Optional<User> findByUsername(String username);
    @AllowFiltering
    Boolean existsByUsername(String username);
    @AllowFiltering
    Boolean existsByEmail(String email);
}
