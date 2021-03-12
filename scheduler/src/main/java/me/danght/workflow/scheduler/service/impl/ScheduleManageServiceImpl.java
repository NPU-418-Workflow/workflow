package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.convert.WfActivityInstanceConvert;
import me.danght.workflow.scheduler.dao.WfActivityHistoryInstanceMapper;
import me.danght.workflow.scheduler.dataobject.WfActivityHistoryInstanceDO;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.service.ScheduleManageService;
import me.danght.workflow.scheduler.element.Process;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class ScheduleManageServiceImpl implements ScheduleManageService {

    @Inject
    WfActivityHistoryInstanceMapper wfActivityHistoryInstanceMapper;

    @Override
    public UserTask findUserTaskByNo(String no, Process process) {
        for(UserTask userTask : process.getUserTaskList()){
            if(userTask.getNo().equals(no))
                return userTask;
        }
        return null;
    }

    @Override
    public void recordActivityHistory(ActivityInstanceDTO wfActivityInstanceDTO) {
        WfActivityHistoryInstanceDO wfActivityHistoryInstanceDO = WfActivityInstanceConvert.INSTANCE.convertRunToHistoryDO(wfActivityInstanceDTO);
        wfActivityHistoryInstanceDO.setCreatetime(new Date());
        wfActivityHistoryInstanceDO.setUpdatetime(wfActivityHistoryInstanceDO.getCreatetime());
        wfActivityHistoryInstanceMapper.save(wfActivityHistoryInstanceDO);
    }


}
