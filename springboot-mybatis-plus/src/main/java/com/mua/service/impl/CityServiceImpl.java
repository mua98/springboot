package com.mua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mua.entity.CityEntity;
import com.mua.mapper.CityMapper;
import com.mua.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-07 下午 5:56
 * @Comment:
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, CityEntity> implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<CityEntity> getCity() {
        QueryWrapper<CityEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("parent_id");
        List<CityEntity> cityEntities = cityMapper.selectList(wrapper);
        return buildCity(cityEntities);
    }

    public static List<CityEntity> buildCity(List<CityEntity> cityEntities) {
        List<CityEntity> finalNode = new ArrayList<>();
        for (CityEntity city : cityEntities) {
            if (city.getParentId() == 0) {
                finalNode.add(selectChildren(city,cityEntities));
            }
        }
        return finalNode;
    }

    private static CityEntity selectChildren(CityEntity city, List<CityEntity> cityEntities) {
        city.setChildren(new ArrayList<CityEntity>());
        for (CityEntity entity : cityEntities) {
            if (Objects.equals(city.getId(),entity.getParentId())){
                if (city.getChildren() == null) {
                    city.setChildren(new ArrayList<CityEntity>());
                }
                city.getChildren().add(selectChildren(entity, cityEntities));
            }
        }
        return city;
    }
}
