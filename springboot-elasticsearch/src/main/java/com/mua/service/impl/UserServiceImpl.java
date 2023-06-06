package com.mua.service.impl;

import com.mua.entity.User;
import com.mua.repository.UserRepository;
import com.mua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-02-28 下午 2:26
 * @Comment:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long save(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public User getById(Long id) {
        Optional<User> find = userRepository.findById(id);
        return find.orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<User> userList = new ArrayList<>();
        users.forEach(userList::add);
        return userList;
    }

    @Override
    public List<User> getListOrderByAgeDesc() {
        return userRepository.findByOrderByAgeDesc();
    }

    @Override
    public List<User> getListOrderByAgeAsc() {
        return userRepository.findByOrderByAgeAsc();
    }

    @Override
    public Page<User> getPage(Integer pageNum, Integer pageSize) {
        // 注意，这里的pageNum是从0开始的，如果要查询第1页数据，pageNum应该为0
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> getListByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> getListByNameAndAge(String name, Integer age) {
        return userRepository.findByNameAndAge(name, age);
    }

    @Override
    public List<User> getListGreaterThanAge(Integer age) {
        return userRepository.findByAgeGreaterThan(age);
    }

    @Override
    public List<User> getListLessThanAge(Integer age) {
        return userRepository.findByAgeLessThan(age);
    }

    @Override
    public List<User> getListGreaterThanEqualAge(Integer age) {
        return userRepository.findByAgeGreaterThanEqual(age);
    }

    @Override
    public List<User> getListLessThanEqualAge(Integer age) {
        return userRepository.findByAgeLessThanEqual(age);
    }

    @Override
    public List<SearchHit<User>> getOne(String name, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userRepository.getOne(name, pageable);
    }
}
