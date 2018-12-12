package com.cn.dao;

import com.cn.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapping {
     void insertone(@Param("user") User user);

     List<User> listall();

     void deleteById(int id);

     User findById(String id);
}
