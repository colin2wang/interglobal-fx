package com.globalfx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.globalfx.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT p.id FROM usr_permission p INNER JOIN usr_role_permission rp ON p.id = rp.permission_id WHERE rp.role_id = #{roleId} AND p.is_deleted = 0")
    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);
}
