package com.sushi.shop.demo.mapper;

import com.sushi.shop.demo.entity.Sushi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SushiMapper {

    @Select("select * from sushi where name = #{name}")
    public Sushi findByName(String name);
}
