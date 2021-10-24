package me.danght.workflow.app.service.impl;

import com.google.common.annotations.VisibleForTesting;
import me.danght.workflow.app.convert.LeaveInfoConvert;
import me.danght.workflow.app.dao.LeaveInfoRepository;
import me.danght.workflow.app.dao.UserInfoRepository;
import me.danght.workflow.app.dataobject.LeaveInfoDO;
import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.LeaveInfoDTO;
import me.danght.workflow.app.service.LeaveInfoService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * 请假信息接口实现类
 * @author wenxiang
 * @author DangHT
 */
@ApplicationScoped
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

    @VisibleForTesting
    public void setLeaveInfoRepository(LeaveInfoRepository leaveInfoRepository) {
        this.leaveInfoRepository = leaveInfoRepository;
    }

    @VisibleForTesting
    public void setUserInfoRepository(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
}
