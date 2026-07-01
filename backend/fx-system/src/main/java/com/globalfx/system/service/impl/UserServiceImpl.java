package com.globalfx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.common.util.IdGenerator;
import com.globalfx.system.dto.UserDTO;
import com.globalfx.system.entity.Role;
import com.globalfx.system.entity.User;
import com.globalfx.system.entity.UserRole;
import com.globalfx.system.mapper.RoleMapper;
import com.globalfx.system.mapper.UserMapper;
import com.globalfx.system.mapper.UserRoleMapper;
import com.globalfx.system.service.UserService;
import com.globalfx.system.vo.RoleVO;
import com.globalfx.system.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResult<UserVO> pageList(int pageNum, int pageSize, String username, Integer status) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(username != null && !username.isBlank(), User::getUsername, username)
                .eq(status != null, User::getStatus, status)
                .orderByDesc(User::getCreatedTime);
        Page<User> result = userMapper.selectPage(page, wrapper);

        List<UserVO> voList = result.getRecords().stream().map(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            List<Role> roles = userMapper.selectRolesByUserId(user.getId());
            vo.setRoles(roles.stream().map(r -> {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(r, roleVO);
                return roleVO;
            }).collect(Collectors.toList()));
            return vo;
        }).collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public UserVO getDetail(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        List<Role> roles = userMapper.selectRolesByUserId(id);
        vo.setRoles(roles.stream().map(r -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(r, roleVO);
            return roleVO;
        }).collect(Collectors.toList()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_CONFLICT, "用户名已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setUserNo(IdGenerator.nextAccountNo());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        userMapper.insert(user);

        saveUserRoles(user.getId(), dto.getRoleIds(), user.getTenantId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserDTO dto) {
        User existing = userMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            user.setPassword(null);
        }
        userMapper.updateById(user);

        if (dto.getRoleIds() != null) {
            userRoleMapper.deleteByUserId(user.getId());
            saveUserRoles(user.getId(), dto.getRoleIds(), existing.getTenantId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
        userRoleMapper.deleteByUserId(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        user.setStatus(status);
        userMapper.updateById(user);
    }

    @Override
    public void resetPassword(Long id, String newPassword) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateById(user);
    }

    private void saveUserRoles(Long userId, List<Long> roleIds, Long tenantId) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setTenantId(tenantId);
            userRole.setCreatedBy(userId);
            userRole.setCreatedTime(LocalDateTime.now());
            userRoleMapper.insert(userRole);
        }
    }
}
