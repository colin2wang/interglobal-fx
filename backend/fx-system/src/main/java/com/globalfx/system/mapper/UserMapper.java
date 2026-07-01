package com.globalfx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT r.* FROM usr_role r INNER JOIN usr_user_role ur ON r.id = ur.role_id WHERE ur.user_id = #{userId} AND r.is_deleted = 0")
    List<com.globalfx.system.entity.Role> selectRolesByUserId(@Param("userId") Long userId);

    @Select("SELECT p.* FROM usr_permission p INNER JOIN usr_role_permission rp ON p.id = rp.permission_id INNER JOIN usr_user_role ur ON rp.role_id = ur.role_id WHERE ur.user_id = #{userId} AND p.is_deleted = 0")
    List<com.globalfx.system.entity.Permission> selectPermissionsByUserId(@Param("userId") Long userId);
}
