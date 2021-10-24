package me.danght.workflow.app.service.impl;

import com.google.common.annotations.VisibleForTesting;
import me.danght.workflow.app.convert.UserInfoConvert;
import me.danght.workflow.app.dao.UserInfoRepository;
import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.UserInfoDTO;
import me.danght.workflow.app.service.UserInfoService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * 用户信息服务接口实现类
 * @author wenxiang
 * @author DangHT
 */
@ApplicationScoped
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

    @VisibleForTesting
    public void setUserInfoRepository(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
}
