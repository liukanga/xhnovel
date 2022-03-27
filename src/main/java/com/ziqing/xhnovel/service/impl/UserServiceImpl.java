package com.ziqing.xhnovel.service.impl;
import com.alibaba.fastjson.JSON;
import com.ziqing.xhnovel.bean.UserEntity;
import com.ziqing.xhnovel.dao.UserDao;
import com.ziqing.xhnovel.exception.XHNDBException;
import com.ziqing.xhnovel.model.*;
import com.ziqing.xhnovel.service.NovelService;
import com.ziqing.xhnovel.service.UserService;
import com.ziqing.xhnovel.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NovelService novelService;

    @Override
    public User queryUserById(Long id) {
        UserEntity userEntity = userDao.queryUserById(id);

        return toModel(userEntity);
    }

    @Override
    public int removeUser(Long id) {
        return userDao.removeUser(id);
    }

    @Override
    public Result<User> login(Long id, String password, int status) {

        UserEntity userEntity = userDao.queryUserById(id);
        Result<User> result = new Result<>();

        if(!userEntity.getPassword().equals(DigestUtils.md5Hex(password))){
            result.setCode("601");
            result.setMessage("密码错误");
            result.setSuccess(false);
            return result;
        }

        if(userEntity.getStatus()!=status){
            result.setCode("605");
            result.setMessage("身份验证错误");
            result.setSuccess(false);
            return result;
        }

        result.setSuccess(true);
        result.setData(toModel(userEntity));

        return result;
    }

    @Override
    public Result<User> register(User user) {

        Result<User> result = new Result<>();

        if(user.getName().isEmpty()){
            result.setCode("600");
            result.setMessage("用户名不能为空");
            result.setSuccess(false);
            return result;
        }
        if(user.getPassword().isEmpty()){
            result.setCode("601");
            result.setMessage("密码不能为空");
            result.setSuccess(false);
            return result;
        }

        String md5HexPwd = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(md5HexPwd);
        UserEntity userEntity = toDO(user);
        try{
            int i = userDao.addUser(userEntity);
            if(i==0){
                throw new XHNDBException("作者信息添加到数据库发生错误，注册作者失败！");
            }
        }catch (XHNDBException e){
            log.error("作者信息添加到数据库发生错误，注册作者失败！",e);
            result.setSuccess(false);
        }
        user.setId(userEntity.getId());
        result.setSuccess(true);
        result.setData(user);

        return result;
    }

    @Override
    public List<User> queryAllUser() {

        List<UserEntity> userEntities = userDao.queryAllUser();
        List<User> users = new ArrayList<>();
        for(UserEntity userEntity : userEntities){
            users.add(toModel(userEntity));
        }
        return users;
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(toDO(user));
    }

    @Override
    public User queryByName(String name) {
        UserEntity userEntity = userDao.queryByName(name);
        if(Objects.isNull(userEntity)){
            return null;
        }
        return toModel(userEntity);
    }

    @Override
    public Paging<User> pageQuery(BasePageParam param) {
        int totalCount = userDao.queryAllUser().size();
        List<UserEntity> userEntities = userDao.pageQuery(param);
        List<User> users = new ArrayList<>();
        if(!CollectionUtils.isEmpty(userEntities)){
            for(UserEntity userEntity : userEntities){
                users.add(toModel(userEntity));
            }
        }
        return new Paging<>(param.getPagination(), param.getPageSize(), totalCount, users);
    }


    private User toModel(UserEntity userEntity){

        User user = new User();

        if(userEntity.getId()!=null){
            user.setId(userEntity.getId());
        }

        user.setName(userEntity.getName());
        user.setPassword(userEntity.getPassword());
        if(!StringUtils.isEmpty(userEntity.getDetails())){
            user.setDetails(userEntity.getDetails());
        }
        user.setStatus(userEntity.getStatus());
        user.setDuration(userEntity.getDuration());
        String novelIds = userEntity.getNovelIds();
        List<Long> nIds = JSON.parseObject(novelIds, Constants.TRE_Long);
        List<Novel> novels = new ArrayList<>();
        if(!CollectionUtils.isEmpty(nIds)){
            for(Long id : nIds){
                Novel novel = novelService.queryNovelById(id);
                novels.add(novel);
            }
        }
        user.setNovels(novels);

        user.setGmtCreated(userEntity.getGmtCreated());
        user.setGmtModified(userEntity.getGmtModified());

        return user;
    }


    private UserEntity toDO(User user){

        UserEntity userEntity = new UserEntity();

        if(user.getId()!=null){
            userEntity.setId(user.getId());
        }

        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setStatus(user.getStatus());
        userEntity.setDuration(user.getDuration());
        if(!StringUtils.isEmpty(user.getDetails())){
            userEntity.setDetails(user.getDetails());
        }

        List<Novel> novels = user.getNovels();
        List<Long> nIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(novels)){
            for(Novel novel : novels){
                nIds.add(novel.getId());
            }
        }
        String novelIds = JSON.toJSONString(nIds);
        userEntity.setNovelIds(novelIds);

        userEntity.setGmtCreated(user.getGmtCreated());
        userEntity.setGmtModified(user.getGmtModified());

        return userEntity;
    }

}
