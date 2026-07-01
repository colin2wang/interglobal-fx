package com.globalfx.system.service;

import com.globalfx.system.dto.LoginDTO;
import com.globalfx.system.dto.RefreshTokenDTO;
import com.globalfx.system.vo.LoginVO;

public interface AuthService {

    LoginVO login(LoginDTO dto);

    LoginVO refreshToken(RefreshTokenDTO dto);

    void logout(String token);
}
