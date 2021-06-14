package me.danght.workflow.scheduler.service.impl;


import me.danght.workflow.scheduler.convert.LeaveInfoConvert;
import me.danght.workflow.scheduler.dao.LeaveInfoRepository;
import me.danght.workflow.scheduler.dao.UserInfoRepository;
import me.danght.workflow.scheduler.dataobject.LeaveInfoDO;
import me.danght.workflow.scheduler.dataobject.UserInfoDO;
import me.danght.workflow.scheduler.dto.LeaveInfoDTO;
import me.danght.workflow.scheduler.service.LeaveInfoService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 请假信息接口实现类
 * @author wenxiang
 * @author DangHT
 */
@Singleton
public class LeaveInfoServiceImpl implements LeaveInfoService {

    @Inject
    LeaveInfoRepository leaveInfoRepository;

    @Inject
    UserInfoRepository userInfoRepository;

    @Override
    public LeaveInfoDTO addLeave(LeaveInfoDTO leaveInfoDTO) {
        LeaveInfoDO leaveInfoDO = LeaveInfoConvert.INSTANCE.convertDTOToDO(leaveInfoDTO);
        leaveInfoRepository.save(leaveInfoDO);
        return LeaveInfoConvert.INSTANCE.convertDOToDTO(leaveInfoDO);
    }

    @Override
    public LeaveInfoDTO getLeaveInfoById(String id) {
        LeaveInfoDO leaveInfoDO = leaveInfoRepository.findById(id).orElse(null);
        if (leaveInfoDO == null) return null;
        LeaveInfoDTO leaveInfoDTO = LeaveInfoConvert.INSTANCE.convertDOToDTO(leaveInfoDO);
        UserInfoDO userInfoDO = userInfoRepository.findById(leaveInfoDO.getUiId()).orElse(null);
        if (userInfoDO == null) return null;
        leaveInfoDTO.setUiName(userInfoDO.getUiName());
        return leaveInfoDTO;
    }
}
