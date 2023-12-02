package com.bo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
