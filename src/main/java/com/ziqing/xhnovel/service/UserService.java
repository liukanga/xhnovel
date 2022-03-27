package com.ziqing.xhnovel.service;


import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Paging;
import com.ziqing.xhnovel.model.Result;
import com.ziqing.xhnovel.model.User;

import java.util.List;

public interface UserService {

    User queryUserById(Long id);

    int removeUser(Long id);

    Result<User> login(Long id, String password, int status);

    Result<User> register(User user);

    List<User> queryAllUser();

    int updateUser(User user);

    User queryByName(String name);

    Paging<User> pageQuery(BasePageParam param);

}
