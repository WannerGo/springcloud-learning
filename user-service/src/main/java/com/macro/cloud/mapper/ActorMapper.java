package com.macro.cloud.mapper;

import com.macro.cloud.domain.Actor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Actor record);

    int insertBatch(@Param("list") List<Actor> record);

    int insertSelective(Actor record);

    Actor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Actor record);

    int updateByPrimaryKey(Actor record);
}