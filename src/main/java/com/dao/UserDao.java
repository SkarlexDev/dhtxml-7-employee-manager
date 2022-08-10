package com.dao;

import java.util.Optional;

import com.bean.User;

public interface UserDao {

    Optional<User> findByNameAndPassword(String userName, String password);

}
