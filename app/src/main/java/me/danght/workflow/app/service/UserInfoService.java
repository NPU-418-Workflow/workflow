package me.danght.workflow.app.service;

import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.UserInfoDTO;

/**
 * 用户信息服务
 * @author wenxiang
 * @author DangHT
 */
public interface UserInfoService {
    /**
     * 校验用户登录
     * @param uiName 用户名
     * @return 用户信息，非空则为成功
     */
    UserInfoDTO validateUser(String uiName);

    /**
     * 通过id查询用户
     * @param id 用户id
     * @return 用户信息
     */
    UserInfoDTO getUserById(String id);

    /**
     * 获取全部用户信息
     * @return 全部用户信息
     */
    Iterable<UserInfoDO> getAllUsers();
}
