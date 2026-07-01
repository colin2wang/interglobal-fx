package com.globalfx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.ResultCode;
import com.globalfx.system.dto.LoginDTO;
import com.globalfx.system.dto.RefreshTokenDTO;
import com.globalfx.system.entity.User;
import com.globalfx.system.mapper.UserMapper;
import com.globalfx.system.security.JwtUtil;
import com.globalfx.system.service.AuthService;
import com.globalfx.system.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Override
    public LoginVO login(LoginDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException(ResultCode.ACCOUNT_FROZEN);
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getTenantId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        redisTemplate.opsForValue().set(
                "token:" + user.getId(),
                accessToken,
                jwtUtil.getAccessTokenExpiration(),
                TimeUnit.MILLISECONDS
        );

        return LoginVO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtUtil.getAccessTokenExpiration() / 1000)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    @Override
    public LoginVO refreshToken(RefreshTokenDTO dto) {
        String refreshToken = dto.getRefreshToken();
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new BusinessException(ResultCode.REFRESH_TOKEN_INVALID);
        }

        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        String username = jwtUtil.getUsernameFromToken(refreshToken);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, userId);
        User user = userMapper.selectOne(wrapper);
        if (user == null || user.getStatus() != 1) {
            throw new BusinessException(ResultCode.ACCOUNT_FROZEN);
        }

        String newAccessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getTenantId());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        redisTemplate.opsForValue().set(
                "token:" + user.getId(),
                newAccessToken,
                jwtUtil.getAccessTokenExpiration(),
                TimeUnit.MILLISECONDS
        );

        return LoginVO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(jwtUtil.getAccessTokenExpiration() / 1000)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    @Override
    public void logout(String token) {
        if (token != null && jwtUtil.validateToken(token)) {
            Long userId = jwtUtil.getUserIdFromToken(token);
            redisTemplate.delete("token:" + userId);
        }
    }
}
