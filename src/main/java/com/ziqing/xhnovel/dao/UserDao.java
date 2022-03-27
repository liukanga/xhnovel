package com.ziqing.xhnovel.dao;


import com.ziqing.xhnovel.bean.UserEntity;
import com.ziqing.xhnovel.model.BasePageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    UserEntity queryUserById(@Param("id") Long id);

    int addUser(UserEntity userEntity);

    int removeUser(@Param("id")Long id);

    List<UserEntity> queryAllUser();

    int updateUser(UserEntity userEntity);

    UserEntity queryByName(String name);

    List<UserEntity> pageQuery(BasePageParam param);

}
