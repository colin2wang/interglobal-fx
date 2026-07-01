package com.globalfx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.system.dto.UserDTO;
import com.globalfx.system.entity.User;
import com.globalfx.system.vo.UserVO;

public interface UserService extends IService<User> {

    PageResult<UserVO> pageList(int pageNum, int pageSize, String username, Integer status);

    UserVO getDetail(Long id);

    void createUser(UserDTO dto);

    void updateUser(UserDTO dto);

    void deleteUser(Long id);

    void updateStatus(Long id, Integer status);

    void resetPassword(Long id, String newPassword);
}
