package com.mua.service;

import com.mua.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-02 下午 4:09
 * @Comment:
 */
public interface UserService {

    Long save(User user);

    User getById(Long id);

    void deleteById(Long id);

    List<User> getAll();

    List<User> getListOrderByAgeDesc();

    List<User> getListOrderByAgeAsc();

    Page<User> getPage(Integer pageNum, Integer pageSize);

    List<User> getListByName(String name);

    List<User> getListByNameAndAge(String name, Integer age);

    List<User> getListGreaterThanAge(Integer age);

    List<User> getListLessThanAge(Integer age);

    List<User> getListGreaterThanEqualAge(Integer age);

    List<User> getListLessThanEqualAge(Integer age);

    List<SearchHit<User>> getOne(String name, Integer pageNum, Integer pageSize);
}
