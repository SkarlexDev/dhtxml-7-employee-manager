package com.service;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bean.User;
import com.google.gson.JsonSyntaxException;

public interface UserService {

    Optional<User> getByUserNameAndPassword(HttpServletRequest req) throws JsonSyntaxException, IOException;

}
