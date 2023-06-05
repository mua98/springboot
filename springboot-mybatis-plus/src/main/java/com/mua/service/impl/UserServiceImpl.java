package com.mua.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mua.entity.BookEntity;
import com.mua.entity.UserEntity;
import com.mua.mapper.UserMapper;
import com.mua.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ASUS
 * @description 针对表【USER】的数据库操作Service实现
 * @createDate 2023-03-24 09:13:59
 */
@DS("oracle")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<UserEntity> pageList(UserEntity userEntity, Integer pageNum, Integer pageSize) {
        Page<UserEntity> result = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userEntity.getName()))
            lambdaQueryWrapper.like(UserEntity::getName, userEntity.getName());
        return userMapper.selectPage(result,lambdaQueryWrapper );
    }
}




