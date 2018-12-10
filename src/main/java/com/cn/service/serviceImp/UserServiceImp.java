package com.cn.service.serviceImp;

import com.cn.dao.UserMapping;
import com.cn.model.User;
import com.cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserMapping userMapping;
    public void insertone(User user) {
        System.out.println("serviceImp...");
        userMapping.insertone(user);
    }

    public List<User> findall() {
        return userMapping.listall();
    }

    public void deleteById(int id) {
        userMapping.deleteById(id);
    }
}
