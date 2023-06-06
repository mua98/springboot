package com.mua.controller;

import com.mua.entity.User;
import com.mua.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-02 下午 4:07
 * @Comment:
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增文档
     */
    @PostMapping("save")
    public String save(@RequestBody User user) {
        // 根据时间戳生成id
        long id = System.currentTimeMillis();
        user.setId(id);
        userService.save(user);
        return "新增成功,id为：" + id;
    }

    /**
     * 通过id更新文档
     */
    @PostMapping("/update")
    public String update(@RequestBody User user) {
        userService.save(user);
        return "修改成功";
    }

    /**
     * 通过id查询文档
     */
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    /**
     * 通过id删除文档
     */
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "成功删除id为" + id + "的文档";
    }

    /**
     * 查询所有文档
     */
    @GetMapping("/getAll")
    public List<User> all() {
        return userService.getAll();
    }

    /**
     * 分页查询（降序 通过年龄）
     */
    @GetMapping("/listOrderByAgeDesc")
    public List<User> listOrderByAgeDesc() {
        return userService.getListOrderByAgeDesc();
    }

    /**
     * 分页查询（升序 通过年龄）
     */
    @GetMapping("/listOrderByAgeAsc")
    public List<User> listOrderByAgeAsc() {
        return userService.getListOrderByAgeAsc();
    }

    /**
     * 分页查询 (查询全部)
     *
     * @param pageNum  注意，这里的pageNum是从0开始的，如果要查询第1页数据，pageNum应该为0
     * @param pageSize
     * @return Page<User>
     */
    @GetMapping("/page")
    public Page<User> page(Integer pageNum, Integer pageSize) {
        return userService.getPage(pageNum, pageSize);
    }

    /**
     * 条件查询（单个条件）
     * 如：查询name包含"阿伟"的数据
     */
    @GetMapping("/listByName")
    public List<User> listByName(String name) {
        return userService.getListByName(name);
    }

    /**
     * 条件查询（多条件）
     * 如：查询 name包含"阿伟"，年龄为8的数据
     */
    @GetMapping("/listByNameAndAge")
    public List<User> listByNameAndAge(String name, Integer age) {
        return userService.getListByNameAndAge(name, age);
    }

    /**
     * 范围查询（ > ）
     */
    @GetMapping("/listGreaterThanAge")
    public List<User> listGreaterThanAge(Integer age) {
        return userService.getListGreaterThanAge(age);
    }

    /**
     * 范围查询（ < ）
     */
    @GetMapping("/listLessThanAge")
    public List<User> listLessThanAge(Integer age) {
        return userService.getListLessThanAge(age);
    }

    /**
     * 范围查询（ >= ）
     */
    @GetMapping("/listGreaterThanEqualAge")
    public List<User> listGreaterThanEqualAge(Integer age) {
        return userService.getListGreaterThanEqualAge(age);
    }

    /**
     * 范围查询（ <= ）
     */
    @GetMapping("/listLessThanEqualAge")
    public List<User> listLessThanEqualAge(Integer age) {
        return userService.getListLessThanEqualAge(age);
    }

    /**
     * 自定义查询
     * 条件查询 + 关键字高亮度 + 分页
     * 如：查询name等于"阿伟"的数据
     */
    @GetMapping("/one")
    public List<SearchHit<User>> one(String name,
                                     @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<SearchHit<User>> list = userService.getOne(name, pageNum, pageSize);

        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("查询时间--------->{}", totalTimeSeconds);
        return list;
    }

}
