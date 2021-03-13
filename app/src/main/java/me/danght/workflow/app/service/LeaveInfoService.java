package me.danght.workflow.app.service;

import me.danght.workflow.app.dto.LeaveInfoDTO;

public interface LeaveInfoService {
    LeaveInfoDTO addLeave(LeaveInfoDTO leaveInfoDTO);

    LeaveInfoDTO selectById(String id);
}
