package com.globalfx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.system.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Delete("DELETE FROM usr_user_role WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
}
