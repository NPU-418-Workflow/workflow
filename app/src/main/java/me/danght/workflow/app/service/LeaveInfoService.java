package me.danght.workflow.app.service;

import me.danght.workflow.app.dto.LeaveInfoDTO;

/**
 * 请假信息服务
 * @author wenxiang
 * @author DangHT
 */
public interface LeaveInfoService {
    /**
     * 添加新请假申请
     * @param leaveInfoDTO 请假申请数据
     * @return 请假申请信息
     */
    LeaveInfoDTO addLeave(LeaveInfoDTO leaveInfoDTO);

    /**
     * 通过id获取请假信息
     * @param id 请假申请id
     * @return 请假申请信息
     */
    LeaveInfoDTO getLeaveInfoById (String id);
}
