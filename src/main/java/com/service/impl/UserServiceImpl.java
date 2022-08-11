package com.service.impl;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.bean.User;
import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.service.UserService;
import com.util.JsonToStringUtil;

public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());
	
	private final UserDao dao = new UserDaoImpl();
	
	@Override
	public Optional<User> getByUserNameAndPassword(HttpServletRequest req) throws JsonSyntaxException, IOException {
		log.info("Parse json to user");
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        User bean = (User) gsonBuilder.create().fromJson(JsonToStringUtil.format(req), User.class);
		return dao.findByNameAndPassword(bean.getUserName(), bean.getPassword());
	}
}
