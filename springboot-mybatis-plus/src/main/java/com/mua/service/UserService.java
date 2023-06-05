package com.mua.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mua.entity.UserEntity;

import java.util.List;

/**
 * @author ASUS
 * @description 针对表【USER】的数据库操作Service
 * @createDate 2023-03-24 09:13:59
 */
public interface UserService extends IService<UserEntity> {
    Page<UserEntity> pageList(UserEntity userEntity,Integer pageNum, Integer pageSize);
}
