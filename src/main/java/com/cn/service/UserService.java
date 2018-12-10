package com.cn.service;


import com.cn.model.User;

import java.util.List;

public interface UserService {
    void insertone(User user);

    List<User> findall();

    void deleteById(int id);
}
