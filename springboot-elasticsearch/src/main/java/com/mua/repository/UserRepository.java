package com.mua.repository;

import com.mua.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-02 下午 4:10
 * @Comment:
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<User, Long> {

    List<User> findByOrderByAgeDesc();

    List<User> findByOrderByAgeAsc();

    List<User> findByName(String name);

    List<User> findByNameAndAge(String name, Integer age);

    List<User> findByAgeGreaterThan(Integer age);

    List<User> findByAgeLessThan(Integer age);

    List<User> findByAgeGreaterThanEqual(Integer age);

    List<User> findByAgeLessThanEqual(Integer age);

    @Highlight(
            fields = {
                    @HighlightField(name = "name"),
            },
            parameters = @HighlightParameters(
                    preTags = "<strong><font style='color:red'>",
                    postTags = "</font></strong>",
                    fragmentSize = 500,
                    numberOfFragments = 3
            )
    )
    @Query("{\"match\":{\"name\":\"?0\"}}")
    List<SearchHit<User>> getOne(String name, Pageable pageable);
}
