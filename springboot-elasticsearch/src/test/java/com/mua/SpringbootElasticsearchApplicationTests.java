package com.mua;

import com.mua.entity.User;
import com.mua.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.ArrayList;

@SpringBootTest
class SpringbootElasticsearchApplicationTests {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    void contextLoads() {
    }

    @Test
    void addUser() {
        ArrayList<User> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("阿伟" + i);
            user.setAge(i);
            user.setSex("男");
            user.setAddress("安徽");
            list.add(user);
        }
        Iterable<User> users = elasticsearchOperations.save(list);
        users.forEach(System.out::println);
    }

}
