package com.bo.service;

import com.bo.domain.ResponseResult;
import com.bo.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
