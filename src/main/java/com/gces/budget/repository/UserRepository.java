package com.gces.budget.repository;

import com.gces.budget.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data MongoDB Repository for User Entity
 * Created by minamrosh on 11/19/15.
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User findOneByUsername(String username);

    public Optional<User> findOneByEmail(String email);

    public User findOneById(String userId);

    @Override
    void delete(User user);
}
