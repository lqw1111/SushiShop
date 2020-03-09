package com.sushi.shop.demo.mapper;

import com.sushi.shop.demo.entity.SushiOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SushiOrderMapper {

    @Insert("insert into sushi_order (id,status_id,sushi_id,createdat) values (#{id},#{statusId},#{sushiId},#{createdAt})")
    void insert(SushiOrder sushiOrder);

    @Update("update sushi_order set status_id=#{statusId} where id=#{id}")
    void updateStatus(SushiOrder sushiOrder);

    @Select("select * from sushi_order where id=#{orderId}")
    SushiOrder findById(Integer orderId);

    @Select("select * from sushi_order")
    List<SushiOrder> findAll();

    @Select("select * from sushi_order where createdat=#{createdAt}")
    SushiOrder findSushiOrder(SushiOrder order);
}
