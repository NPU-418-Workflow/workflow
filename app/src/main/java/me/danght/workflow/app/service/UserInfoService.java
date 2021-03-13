package me.danght.workflow.app.service;

import me.danght.workflow.app.dto.UserInfoDTO;

public interface UserInfoService {
    UserInfoDTO validateUser(String uiName);

    UserInfoDTO queryUser(String id);
}
