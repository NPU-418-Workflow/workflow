package me.danght.workflow.app.service.impl;

import me.danght.workflow.app.convert.UserInfoConvert;
import me.danght.workflow.app.dao.UserInfoRepository;
import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.UserInfoDTO;
import me.danght.workflow.app.service.UserInfoService;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public UserInfoDTO queryUser(String id) {
        UserInfoDO userInfoDO = userInfoRepository.findById(id).orElse(null);
        return UserInfoConvert.INSTANCE.convertDOToDTO(userInfoDO);
    }
}