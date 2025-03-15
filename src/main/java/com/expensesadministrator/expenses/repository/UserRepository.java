package com.expensesadministrator.expenses.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.expensesadministrator.expenses.entity.User;

public interface UserRepository extends MongoRepository<User,String>{
    Optional<User> findByName(String name);
}
