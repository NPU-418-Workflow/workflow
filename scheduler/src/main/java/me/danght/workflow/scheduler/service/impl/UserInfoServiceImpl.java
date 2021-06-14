package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.convert.UserInfoConvert;
import me.danght.workflow.scheduler.dao.UserInfoRepository;
import me.danght.workflow.scheduler.dataobject.UserInfoDO;
import me.danght.workflow.scheduler.dto.UserInfoDTO;
import me.danght.workflow.scheduler.service.UserInfoService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 用户信息服务接口实现类
 * @author wenxiang
 * @author DangHT
 */
@Singleton
public class UserInfoServiceImpl implements UserInfoService {

    @Inject
    UserInfoRepository userInfoRepository;

    @Override
    public UserInfoDTO validateUser(String uiName) {
        UserInfoDO userInfoDO = userInfoRepository.findByUiName(uiName).orElse(null);
        return UserInfoConvert.INSTANCE.convertDOToDTO(userInfoDO);
    }

    @Override
    public UserInfoDTO getUserById(String id) {
        UserInfoDO userInfoDO = userInfoRepository.findById(id).orElse(null);
        return UserInfoConvert.INSTANCE.convertDOToDTO(userInfoDO);
    }

    @Override
    public Iterable<UserInfoDO> getAllUsers() {
        return userInfoRepository.findAll();
    }
}
