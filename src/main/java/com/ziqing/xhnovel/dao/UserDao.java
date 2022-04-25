package com.ziqing.xhnovel.dao;


import com.ziqing.xhnovel.bean.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    UserEntity queryUserById(@Param("id") Long id);

    int addUser(UserEntity userEntity);

    int removeUser(@Param("id")Long id);

    List<UserEntity> queryAllUser();

    int updateUser(UserEntity userEntity);

    UserEntity queryByName(String name);

    List<UserEntity> pageQuery(Map<String, Object> paramMap);

    Integer pageQueryAll(Map<String, Object> paramMap);

}
