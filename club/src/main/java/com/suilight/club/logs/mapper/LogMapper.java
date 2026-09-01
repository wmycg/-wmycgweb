package com.suilight.club.logs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suilight.club.logs.entity.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<Log> {
}
