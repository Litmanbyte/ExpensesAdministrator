package com.expensesadministrator.expenses.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.expensesadministrator.expenses.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByLogin(String login);

    Optional<User> findByName(String name);
}
